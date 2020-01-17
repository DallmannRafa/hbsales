package br.com.hbsis.pedido;

import br.com.hbsis.email.EmailSender;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.item.Item;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.periodoVendas.PeriodoVendasService;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final IPedidoRepository iPedidoRepository;
    private final PeriodoVendasService periodoVendasService;
    private final FornecedorService fornecedorService;
    private final FuncionarioService funcionarioService;
    private final ProdutoService produtoService;
    private final EmailSender emailSender;

    public PedidoService(IPedidoRepository iPedidoRepository, PeriodoVendasService periodoVendasService, FornecedorService fornecedorService, FuncionarioService funcionarioService, ProdutoService produtoService, EmailSender emailSender) {
        this.iPedidoRepository = iPedidoRepository;
        this.periodoVendasService = periodoVendasService;
        this.fornecedorService = fornecedorService;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
        this.emailSender = emailSender;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) throws MessagingException {

        pedidoDTO.setFornecedor(fornecedorService.findFornecedorById(pedidoDTO.getFornecedor().getId()));
        pedidoDTO.setPeriodoVendas(periodoVendasService.findPeriodoVendasById(pedidoDTO.getPeriodoVendas().getId()));
        pedidoDTO.setFuncionario(funcionarioService.findFuncionarioById(pedidoDTO.getFuncionario().getId()));
        this.calculaTotal(pedidoDTO);

        this.validate(pedidoDTO);
        this.validateItens(pedidoDTO.getItens(), pedidoDTO.getFornecedor());
        this.validatePeriodo(pedidoDTO.getPeriodoVendas(), pedidoDTO.getFornecedor());

        Pedido pedido = new Pedido();

        pedido.setItens(this.setItens(pedidoDTO.getItens(), pedido));
        pedido.setPeriodoVendas(pedidoDTO.getPeriodoVendas());
        pedido.setFornecedor(pedidoDTO.getFornecedor());
        pedido.setFuncionario(pedidoDTO.getFuncionario());
        pedido.setCodigo(this.codeGenerator(pedidoDTO.getCodigo()));
        pedido.setDataCadastro(LocalDate.now());
        pedido.setStatusPedido(this.validateStatus(pedidoDTO.getStatusPedido().toString()));
        pedido.setTotal(pedidoDTO.getTotal());

        this.invoiceValidarPedido(pedido);

        pedido = this.iPedidoRepository.save(pedido);

        emailSender.enviarEmail(pedido, pedido.getItens());

        return PedidoDTO.of(pedido);
    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            pedidoDTO.setFornecedor(fornecedorService.findFornecedorById(pedidoDTO.getFornecedor().getId()));
            pedidoDTO.setPeriodoVendas(periodoVendasService.findPeriodoVendasById(pedidoDTO.getPeriodoVendas().getId()));
            pedidoDTO.setFuncionario(funcionarioService.findFuncionarioById(pedidoDTO.getFuncionario().getId()));
            this.calculaTotal(pedidoDTO);

            this.validate(pedidoDTO);
            this.validateItens(pedidoDTO.getItens(), pedidoDTO.getFornecedor());
            this.validatePeriodo(pedidoDTO.getPeriodoVendas(), pedidoDTO.getFornecedor());

            Pedido pedido = pedidoOptional.get();

            this.statusValidate(pedido.getStatusPedido());

            pedido.updateItens(this.setItens(pedidoDTO.getItens(), pedido));
            pedido.setPeriodoVendas(pedidoDTO.getPeriodoVendas());
            pedido.setFornecedor(pedidoDTO.getFornecedor());
            pedido.setFuncionario(pedidoDTO.getFuncionario());
            pedido.setCodigo(this.codeGenerator(pedidoDTO.getCodigo()));
            pedido.setTotal(pedidoDTO.getTotal());

            pedido = this.iPedidoRepository.save(pedido);

            return PedidoDTO.of(pedido);
        }

        throw new IllegalArgumentException("ID não existe");
    }

    public PedidoDTO cancelaPedido(Long idPedido) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(idPedido);

        if (pedidoOptional.isPresent()
                && pedidoOptional.get().getStatusPedido().equals("ATIVO")
                && !pedidoOptional.get().getPeriodoVendas().getDataFimVendas().isBefore(LocalDate.now())) {

            Pedido pedido = pedidoOptional.get();

            pedido.setStatusPedido("CANCELADO");

            this.iPedidoRepository.save(pedido);
            return PedidoDTO.of(pedido);
        }

        throw new IllegalArgumentException("Não foi possivel cancelar o pedido");
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return PedidoDTO.of(pedidoOptional.get());
        }

        throw new IllegalArgumentException("ID não existe");
    }

    public Optional<Pedido> findByIdOptional(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return pedidoOptional;
        }

        throw new IllegalArgumentException("Id não existe");
    }

    public List<PedidoDTO> findPedidosByFuncionario(Long idFuncionario) {
        Optional<Funcionario> funcionarioOptional = funcionarioService.findByIdOptional(idFuncionario);
        List<PedidoDTO> pedidosDTO = new ArrayList<>();

        if (funcionarioOptional.isPresent()) {
            List<Pedido> pedidos = this.iPedidoRepository.findByFuncionario(funcionarioOptional.get());

            for (Pedido pedido : pedidos) {
                if (!pedido.getStatusPedido().equals("CANCELADO")) {
                    pedidosDTO.add(PedidoDTO.of(pedido));
                }
            }

            return pedidosDTO;

        }

        throw new IllegalArgumentException("Id não existe");

    }

    public void delete(Long id) {
        this.iPedidoRepository.deleteById(id);
    }

    private void validate(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getCodigo())) {
            throw new IllegalArgumentException("Codigo não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getFornecedor().toString())) {
            throw new IllegalArgumentException("Fornecedor não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getFuncionario().toString())) {
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getPeriodoVendas().toString())) {
            throw new IllegalArgumentException("Periodo de vendas não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getItens().toString())) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getStatusPedido().toString())) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
    }

    private void validateItens(List<ItemDTO> itens, Fornecedor fornecedor) {

        for (ItemDTO itemDTO : itens) {

            Long idFornecedorItem = produtoService.findProdutoById(itemDTO.getProduto().getId()).getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getFornecedor().getId();

            if (!idFornecedorItem.equals(fornecedor.getId())) {
                throw new IllegalArgumentException("Este item não pertence ao fornecedor informado");
            }
        }

    }

    private List<Item> setItens(List<ItemDTO> itens, Pedido pedido) {

        List<Item> itensSetados = new ArrayList<>();

        for (ItemDTO itemDTO : itens) {
            Item item = new Item();

            item.setProduto(produtoService.findProdutoById(itemDTO.getProduto().getId()));
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPedido(pedido);

            itensSetados.add(item);
        }

        return itensSetados;
    }

    private void validatePeriodo(PeriodoVendas periodoVendas, Fornecedor fornecedor) {

        if (!periodoVendas.getFornecedor().getId().equals(fornecedor.getId())) {
            throw new IllegalArgumentException("Periodo de vendas não corresponde ao fornecedor informado");
        }

        if (periodoVendas.getDataFimVendas().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Periodo de vendas não está em vigência");
        }

    }

    private String validateStatus(String status) {

        status = status.toUpperCase();

        if (status.equals("CANCELADO") || status.equals("ATIVO") || status.equals("RETIRADO")) {
            return status;
        }

        throw new IllegalArgumentException("Status não válido");
    }

    private void calculaTotal(PedidoDTO pedidoDTO) {

        pedidoDTO.setTotal(new BigDecimal(0));

        for (ItemDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoService.findProdutoById(itemDTO.getProduto().getId());

            pedidoDTO.setTotal(pedidoDTO.getTotal().add(new BigDecimal(produto.getPrecoProduto().doubleValue() * itemDTO.getQuantidade())));

        }

    }

    public String codeGenerator(String codigoInformado) {
        codigoInformado = codigoInformado.toUpperCase();

        if (codigoInformado.length() >= 10) {
            codigoInformado = codigoInformado.substring(codigoInformado.length() - 10);
        } else {
            while (codigoInformado.length() < 10) {
                codigoInformado = "0" + codigoInformado;
            }

        }

        return codigoInformado;
    }

    private void statusValidate(String pedidoEnum) {
        if (!(pedidoEnum.equals("ATIVO"))) {
            throw new IllegalArgumentException("pedido não pode mais ser alterado pois está cancelado/retirado");
        }
    }

    private void invoiceValidarPedido(Pedido pedido) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f59ff402-1b67-11ea-978f-2e728ce88125");
        HttpEntity<InvoiceDTO> httpEntity = new HttpEntity<>(InvoiceDTO.parse(pedido), headers);
        ResponseEntity<InvoiceDTO> responseEntity = restTemplate.exchange("http://10.2.54.25:9999/v2/api-docs", HttpMethod.POST, httpEntity, InvoiceDTO.class);
        if (!(responseEntity.getStatusCodeValue() == 200 || responseEntity.getStatusCodeValue() == 201)) {
            throw new IllegalArgumentException("Não foi possivel fazer a validação na Api" + responseEntity.getStatusCodeValue());
        }

    }

}
