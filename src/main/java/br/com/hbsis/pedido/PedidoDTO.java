package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.produto.Produto;

import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {

    private long id;
    private String codigo;
    private PedidoEnum statusPedido;
    private Funcionario funcionario;
    private Fornecedor fornecedor;
    private LocalDate dataCadastro;
    private List<Produto> produtos;

    public PedidoDTO() {
    }

    public PedidoDTO(String codigo, PedidoEnum statusPedido, Funcionario funcionario, Fornecedor fornecedor, LocalDate dataCadastro, List<Produto> produtos) {
        this.codigo = codigo;
        this.statusPedido = statusPedido;
        this.funcionario = funcionario;
        this.fornecedor = fornecedor;
        this.dataCadastro = dataCadastro;
        this.produtos = produtos;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public PedidoEnum getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(PedidoEnum statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", statusPedido=" + statusPedido +
                ", funcionario=" + funcionario +
                ", fornecedor=" + fornecedor +
                ", dataCadastro=" + dataCadastro +
                ", produtos=" + produtos +
                '}';
    }
}
