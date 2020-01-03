package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;

import java.math.BigDecimal;
import java.util.Date;

public class ProdutoDTO {

    private Long id;
    private String codigoProduto;
    private String nomeProduto;
    private BigDecimal precoProduto;
    private LinhaDeCategoria linhaDeCategoria;
    private int unidadePorCaixa;
    private BigDecimal pesoUnidade;
    private String unidadeMedidaPeso;
    private Date validade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String codigoProduto, String nomeProduto, BigDecimal precoProduto, LinhaDeCategoria linhaDeCategoria, int unidadePorCaixa, BigDecimal pesoUnidade, String unidadeMedidaPeso, Date validade) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.linhaDeCategoria = linhaDeCategoria;
        this.unidadePorCaixa = unidadePorCaixa;
        this.pesoUnidade = pesoUnidade;
        this.unidadeMedidaPeso = unidadeMedidaPeso;
        this.validade = validade;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getCodigoProduto(),
                produto.getNomeProduto(),
                produto.getPrecoProduto(),
                produto.getLinhaDeCategoria(),
                produto.getUnidadePorCaixa(),
                produto.getPesoUnidade(),
                produto.getUnidadeMedidaPeso(),
                produto.getValidade()
        );
    }

    public Long getId() {
        return id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
    }

    public LinhaDeCategoria getLinhaDeCategoria() {
        return linhaDeCategoria;
    }

    public void setLinhaDeCategoria(LinhaDeCategoria linhaDeCategoria) {
        this.linhaDeCategoria = linhaDeCategoria;
    }

    public int getUnidadePorCaixa() {
        return unidadePorCaixa;
    }

    public void setUnidadePorCaixa(int unidadePorCaixa) {
        this.unidadePorCaixa = unidadePorCaixa;
    }

    public BigDecimal getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(BigDecimal pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public String getUnidadeMedidaPeso() {
        return unidadeMedidaPeso;
    }

    public void setUnidadeMedidaPeso(String unidadeMedidaPeso) {
        this.unidadeMedidaPeso = unidadeMedidaPeso;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", codigoProduto='" + codigoProduto + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", precoProduto=" + precoProduto +
                ", linhaDeCategoria=" + linhaDeCategoria +
                ", unidadePorCaixa=" + unidadePorCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", unidadeMedidaPeso='" + unidadeMedidaPeso + '\'' +
                ", validade=" + validade +
                '}';
    }
}
