package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoReoository iProdutoReoository;
    private final LinhaDeCategoriaService linhaDeCategoriaService;

    public ProdutoService(IProdutoReoository iProdutoReoository, LinhaDeCategoriaService linhaDeCategoriaService) {
        this.iProdutoReoository = iProdutoReoository;
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
        produto.setUnidadeMedidaPeso(this.filtroUnidadeMedida(produtoDTO.getUnidadeMedidaPeso()));
        produto.setUnidadePorCaixa(produtoDTO.getUnidadePorCaixa());
        produto.setValidade(produtoDTO.getValidade());

        produto = this.iProdutoReoository.save(produto);

        return ProdutoDTO.of(produto);

    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoOptional = this.iProdutoReoository.findById(id);

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
            produto.setUnidadeMedidaPeso(this.filtroUnidadeMedida(produtoDTO.getUnidadeMedidaPeso()));
            produto.setUnidadePorCaixa(produtoDTO.getUnidadePorCaixa());
            produto.setValidade(produtoDTO.getValidade());

            produto = this.iProdutoReoository.save(produto);

            return ProdutoDTO.of(produto);

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public List<Produto> findAll() {
        return this.iProdutoReoository.findAll();
    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoReoository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Produto de ID: [{}]", id);

        this.iProdutoReoository.deleteById(id);
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

    public String filtroUnidadeMedida(String unidadeMedida) {
        unidadeMedida = unidadeMedida.toLowerCase();

        if (unidadeMedida.equals("kg") || unidadeMedida.equals("g") || unidadeMedida.equals("mg")) {
            if (unidadeMedida.equals("kg")) {
                return "Kg";
            } else {
                return unidadeMedida;
            }
        }

        throw new IllegalArgumentException("Unidade de medida inválida, somente mg, g ou kg");
    }

}
