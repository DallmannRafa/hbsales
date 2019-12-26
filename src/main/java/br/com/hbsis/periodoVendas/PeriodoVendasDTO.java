package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;

import java.time.LocalDate;

public class PeriodoVendasDTO {

    private Long id;
    private LocalDate dataInicioVendas;
    private LocalDate dataFimVendas;
    private Fornecedor fornecedor;
    private LocalDate dataRetiradaPedido;
    private String descricao;

    public PeriodoVendasDTO() {
    }

    public PeriodoVendasDTO(LocalDate dataInicioVendas, LocalDate dataFimVendas, Fornecedor fornecedor, LocalDate dataRetiradaPedido, String descricao) {
        this.dataInicioVendas = dataInicioVendas;
        this.dataFimVendas = dataFimVendas;
        this.fornecedor = fornecedor;
        this.dataRetiradaPedido = dataRetiradaPedido;
        this.descricao = descricao;
    }

    public static PeriodoVendasDTO of(PeriodoVendas periodoVendas) {
        return new PeriodoVendasDTO(
                periodoVendas.getDataInicioVendas(),
                periodoVendas.getDataFimVendas(),
                periodoVendas.getFornecedor(),
                periodoVendas.getDataRetiradaPedido(),
                periodoVendas.getDescricao()
        );
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
        return "PeriodoVendasDTO{" +
                "id=" + id +
                ", dataInicioVendas=" + dataInicioVendas +
                ", dataFimVendas=" + dataFimVendas +
                ", fornecedor=" + fornecedor +
                ", dataRetiradaPedido=" + dataRetiradaPedido +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
