package br.com.hbsis.item;

import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.produto.Produto;

public class ItemDTO {

    private long id;
    private int quantidade;
    private Long pedido;
    private Produto produto;

    public ItemDTO() {
    }

    public ItemDTO(int quantidade, Long pedido, Produto produto) {
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.produto = produto;
    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getQuantidade(),
                item.getPedido().getId(),
                item.getProduto()
        );
    }

    public long getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
