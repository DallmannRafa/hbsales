package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.item.Item;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.periodoVendas.PeriodoVendas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {

    private long id;
    private String codigo;
    private PedidoEnum statusPedido;
    private Funcionario funcionario;
    private Fornecedor fornecedor;
    private PeriodoVendas periodoVendas;
    private LocalDate dataCadastro;
    private List<ItemDTO> itens;
    private BigDecimal total;

    public PedidoDTO() {
    }

    public PedidoDTO(String codigo, PedidoEnum statusPedido, Funcionario funcionario, Fornecedor fornecedor, PeriodoVendas periodoVendas, LocalDate dataCadastro, List<ItemDTO> itens, BigDecimal total) {
        this.codigo = codigo;
        this.statusPedido = statusPedido;
        this.funcionario = funcionario;
        this.fornecedor = fornecedor;
        this.periodoVendas = periodoVendas;
        this.dataCadastro = dataCadastro;
        this.itens = itens;
        this.total = total;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getCodigo(),
                pedido.getStatusPedido(),
                pedido.getFuncionario(),
                pedido.getFornecedor(),
                pedido.getPeriodoVendas(),
                pedido.getDataCadastro(),
                DTOOfItem(pedido.getItens()),
                pedido.getTotal()
                );
    }

    private static List<ItemDTO> DTOOfItem(List<Item> itens) {
        List<ItemDTO> itensDTO = new ArrayList<>();

        for (Item item : itens) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setPedido(item.getPedido().getId());
            itemDTO.setProduto(item.getProduto());
            itemDTO.setQuantidade(item.getQuantidade());

            itensDTO.add(itemDTO);
        }

        return itensDTO;

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

    public List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemDTO> itens) {
        this.itens = itens;
    }

    public PeriodoVendas getPeriodoVendas() {
        return periodoVendas;
    }

    public void setPeriodoVendas(PeriodoVendas periodoVendas) {
        this.periodoVendas = periodoVendas;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", statusPedido=" + statusPedido +
                ", funcionario=" + funcionario +
                ", fornecedor=" + fornecedor +
                ", periodoVendas=" + periodoVendas +
                ", dataCadastro=" + dataCadastro +
                ", itens=" + itens +
                ", total=" + total +
                '}';
    }
}
