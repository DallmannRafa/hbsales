package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_periodo_vendas")
public class PeriodoVendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "inicio_vendas")
    private LocalDate dataInicioVendas;
    @Column(name = "fim_vendas")
    private LocalDate dataFimVendas;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @Column(name = "retirada_pedido")
    private LocalDate dataRetiradaPedido;
    @Column(name = "descricao", length = 50)
    private String descricao;

    public PeriodoVendas() { }

    public PeriodoVendas(Long id, LocalDate dataInicioVendas, LocalDate dataFimVendas, Fornecedor fornecedor, LocalDate dataRetiradaPedido, String descricao) {
        this.id = id;
        this.dataInicioVendas = dataInicioVendas;
        this.dataFimVendas = dataFimVendas;
        this.fornecedor = fornecedor;
        this.dataRetiradaPedido = dataRetiradaPedido;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataInicioVendas() {
        return dataInicioVendas;
    }

    public void setDataInicioVendas(LocalDate dataInicioVendas) {
        this.dataInicioVendas = dataInicioVendas;
    }

    public LocalDate getDataFimVendas() {
        return dataFimVendas;
    }

    public void setDataFimVendas(LocalDate dataFimVendas) {
        this.dataFimVendas = dataFimVendas;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataRetiradaPedido() {
        return dataRetiradaPedido;
    }

    public void setDataRetiradaPedido(LocalDate dataRetiradaPedido) {
        this.dataRetiradaPedido = dataRetiradaPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "PeriodoVendas{" +
                "id=" + id +
                ", dataInicioVendas=" + dataInicioVendas +
                ", dataFimVendas=" + dataFimVendas +
                ", fornecedor=" + fornecedor +
                ", dataRetiradaPedido=" + dataRetiradaPedido +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
