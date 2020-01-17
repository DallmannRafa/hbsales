package br.com.hbsis.item;

import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.produto.Produto;

import javax.persistence.*;

@Entity
@Table (name = "seg_itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quantidade")
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produto produto;

    public Item() {
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", pedido=" + pedido +
                ", produto=" + produto +
                '}';
    }
}
