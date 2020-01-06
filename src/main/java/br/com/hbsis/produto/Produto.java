package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;
import br.com.hbsis.pedido.Pedido;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "seg_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @Column(name = "codigo_produto", length = 10)
    @CsvBindByPosition(position = 1)
    private String codigoProduto;
    @Column(name = "nome_produto", length = 100)
    @CsvBindByPosition(position = 2)
    private String nomeProduto;
    @Column(name = "preco_produto")
    @CsvBindByPosition(position = 3)
    private BigDecimal precoProduto;
    @ManyToOne
    @JoinColumn(name = "id_linha_categoria", referencedColumnName = "id")
    @CsvBindByPosition(position = 4)
    private LinhaDeCategoria linhaDeCategoria;
    @Column(name = "unidade_por_caixa", length = 10)
    @CsvBindByPosition(position = 5)
    private int unidadePorCaixa;
    @Column(name = "peso_unidade")
    @CsvBindByPosition(position = 6)
    private BigDecimal pesoUnidade;
    @Column(name = "unidade_medida_peso", length = 2)
    @CsvBindByPosition(position = 7)
    private String unidadeMedidaPeso;
    @Column(name = "validade")
    @CsvBindByPosition(position = 8)
    private Date validade;

    @ManyToMany(mappedBy = "produtos")
    private Pedido pedido;

    public Produto() {
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
        return "Produto{" +
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
