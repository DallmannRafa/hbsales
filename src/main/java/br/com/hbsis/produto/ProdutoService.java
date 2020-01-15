package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final LinhaDeCategoriaService linhaDeCategoriaService;

    public ProdutoService(IProdutoRepository iProdutoRepository, LinhaDeCategoriaService linhaDeCategoriaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        produtoDTO.setLinhaDeCategoria(this.linhaDeCategoriaService.findLinhaDeCategoriaById(produtoDTO.getLinhaDeCategoria().getId()));

        Produto produto = new Produto();

        produto.setCodigoProduto(this.codeGenerator(produtoDTO.getCodigoProduto()));
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto().setScale(2, BigDecimal.ROUND_HALF_UP));
        produto.setLinhaDeCategoria(produtoDTO.getLinhaDeCategoria());
        produto.setPesoUnidade(produtoDTO.getPesoUnidade().setScale(3, BigDecimal.ROUND_HALF_UP));
        produto.setUnidadeMedidaPeso(this.unidadeMedidaGenerator(produtoDTO.getUnidadeMedidaPeso()));
        produto.setUnidadePorCaixa(produtoDTO.getUnidadePorCaixa());
        produto.setValidade(produtoDTO.getValidade());

        produto = this.iProdutoRepository.save(produto);

        return ProdutoDTO.of(produto);

    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {

            Produto produto = produtoOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produto.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produto);

            produtoDTO.setLinhaDeCategoria(linhaDeCategoriaService.findLinhaDeCategoriaById(produto.getLinhaDeCategoria().getId()));

            produto.setCodigoProduto(this.codeGenerator(produtoDTO.getCodigoProduto()));
            produto.setNomeProduto(produtoDTO.getNomeProduto());
            produto.setPrecoProduto(produtoDTO.getPrecoProduto().setScale(2, BigDecimal.ROUND_HALF_UP));
            produto.setLinhaDeCategoria(produtoDTO.getLinhaDeCategoria());
            produto.setPesoUnidade(produtoDTO.getPesoUnidade().setScale(3, BigDecimal.ROUND_HALF_UP));
            produto.setUnidadeMedidaPeso(this.unidadeMedidaGenerator(produtoDTO.getUnidadeMedidaPeso()));
            produto.setUnidadePorCaixa(produtoDTO.getUnidadePorCaixa());
            produto.setValidade(produtoDTO.getValidade());

            produto = this.iProdutoRepository.save(produto);

            return ProdutoDTO.of(produto);

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public List<Produto> findAll() {
        return this.iProdutoRepository.findAll();
    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Produto findProdutoById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }

        throw new IllegalArgumentException("Id não existe");
    }

    public Optional<Produto> findByIdOptional(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional;
        }

        throw new IllegalArgumentException("Id não existe");
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);

        this.iProdutoRepository.deleteById(id);
    }

    public String[][] stringfyToCsvById (Long id) {
        String[] header = new String[] {
                "código_produto", "nome_produto", "preço", "unidade_por_caixa", "peso_unidade", "validade",
                "codigo_linha_categoria", "nome_linha_categoria", "código_categoria", "nome_categoria",
                "CNPJ_fornecedor", "razão_social_fornecedor"};
        String[][] dados = new String [2][12];
        dados[0] = header;

        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();

            String valor = String.valueOf(produto.getPrecoProduto()).replaceAll("[^0-9]", "");
            valor = "R$" + valor.substring(0,valor.length()-2) + "," + valor.substring(valor.length()-2);

            String peso = String.valueOf(produto.getPesoUnidade()).replaceAll("[^0-9]", "");
            peso = peso.substring(0,peso.length()-3) + "," + peso.substring(peso.length()-3) + produto.getUnidadeMedidaPeso();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String vencimento = format.format(produto.getValidade());

            String CNPJ = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getFornecedor().getCnpj();

            dados[1][0] = produto.getCodigoProduto();
            dados[1][1] = produto.getNomeProduto();
            dados[1][2] = valor;
            dados[1][3] = String.valueOf(produto.getUnidadePorCaixa());
            dados[1][4] = peso;
            dados[1][5] = vencimento;
            dados[1][6] = produto.getLinhaDeCategoria().getCodigoLinhaCategoria();
            dados[1][7] = produto.getLinhaDeCategoria().getNomeLinhaCategoria();
            dados[1][8] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getCodigoCategoria();
            dados[1][9] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getNomeCategoria();
            dados[1][10] = CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14);
            dados[1][11] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getFornecedor().getRazaoSocial();

            return dados;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public String[][] stringfyAllToCsv () {
        String[] header = new String[] {
                "código_produto", "nome_produto", "preço", "unidade_por_caixa", "peso_unidade", "validade",
                "codigo_linha_categoria", "nome_linha_categoria", "código_categoria", "nome_categoria",
                "CNPJ_fornecedor", "razão_social_fornecedor"};
        List<Produto> produtos = this.iProdutoRepository.findAll();
        String[][] dados = new String [produtos.size() + 1][12];
        dados[0] = header;

        int contador = 1;

        for (Produto produto : produtos) {

            String valor = String.valueOf(produto.getPrecoProduto()).replaceAll("[^0-9]", "");
            valor = "R$" + valor.substring(0,valor.length()-2) + "," + valor.substring(valor.length()-2);

            String peso = String.valueOf(produto.getPesoUnidade()).replaceAll("[^0-9]", "");
            peso = peso.substring(0,peso.length()-3) + "," + peso.substring(peso.length()-3) + produto.getUnidadeMedidaPeso();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String vencimento = format.format(produto.getValidade());

            String CNPJ = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getFornecedor().getCnpj();

            dados[contador][0] = produto.getCodigoProduto();
            dados[contador][1] = produto.getNomeProduto();
            dados[contador][2] = valor;
            dados[contador][3] = String.valueOf(produto.getUnidadePorCaixa());
            dados[contador][4] = peso;
            dados[contador][5] = vencimento;
            dados[contador][6] = produto.getLinhaDeCategoria().getCodigoLinhaCategoria();
            dados[contador][7] = produto.getLinhaDeCategoria().getNomeLinhaCategoria();
            dados[contador][8] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getCodigoCategoria();
            dados[contador][9] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getNomeCategoria();
            dados[contador][10] = CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14);
            dados[contador][11] = produto.getLinhaDeCategoria().getCategoriaDaLinhaCategoria().getFornecedor().getRazaoSocial();

            contador++;
        }

        return dados;
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

    public String unidadeMedidaGenerator(String unidadeMedida) {
        unidadeMedida = unidadeMedida.toLowerCase();
        unidadeMedida = unidadeMedida.replaceAll("[^A-z]", "");

        if (unidadeMedida.equals("kg") || unidadeMedida.equals("g") || unidadeMedida.equals("mg")) {
            if (unidadeMedida.equals("kg")) {
                return "Kg";
            } else {
                return unidadeMedida;
            }
        }

        throw new IllegalArgumentException("Unidade de medida inválida, somente mg, g ou kg");
    }

    public BigDecimal valorGenerator(String valor) {
        valor = valor.replaceAll("[^.|,|0-9]", "");
        valor = valor.replaceAll("[,]", ".");
        BigDecimal preco = new BigDecimal(valor);
        preco = preco.setScale(2, BigDecimal.ROUND_HALF_UP);

        return preco;
    }

    public BigDecimal pesoGenerator(String peso) {
        peso = peso.replaceAll("[^.|,|0-9]", "");
        peso = peso.replaceAll("[,]", ".");
        BigDecimal pesoBigDecimal = new BigDecimal(peso);
        pesoBigDecimal = pesoBigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);

        return pesoBigDecimal;
    }

    public Date dateGenerator(String dataString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        return format.parse(dataString);
    }

    public Optional<Produto> findByCodigoProduto(String codigoProduto) {
        return this.iProdutoRepository.findByCodigoProduto(codigoProduto);
    }

}
