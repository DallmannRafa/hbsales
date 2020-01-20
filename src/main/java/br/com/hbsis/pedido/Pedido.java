package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.item.Item;
import br.com.hbsis.periodoVendas.PeriodoVendas;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "status")
    private String statusPedido;
    @Column(name = "data")
    private LocalDate dataCadastro;
    @Column(name = "total")
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @ManyToOne
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    private PeriodoVendas periodoVendas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true)
    private List<Item> itens;

    public Pedido() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public PeriodoVendas getPeriodoVendas() {
        return periodoVendas;
    }

    public void setPeriodoVendas(PeriodoVendas periodoVendas) {
        this.periodoVendas = periodoVendas;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public void updateItens(List<Item> itens) {
        this.itens.clear();
        this.itens.addAll(itens);
    }


    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", statusPedido=" + statusPedido +
                ", dataCadastro=" + dataCadastro +
                ", total=" + total +
                ", funcionario=" + funcionario +
                ", fornecedor=" + fornecedor +
                ", periodoVendas=" + periodoVendas +
                ", itens=" + itens +
                '}';
    }
}
