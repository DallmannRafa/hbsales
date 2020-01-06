package br.com.hbsis.pedido;

import br.com.hbsis.produto.Produto;

public class ProdutoPedidoDTO {

    private int quantidade;
    private String nomeProduto;

    public ProdutoPedidoDTO() {
    }

    public ProdutoPedidoDTO(int quantidade, String nomeProduto) {
        this.quantidade = quantidade;
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(Produto produto) {
        this.nomeProduto = produto.getNomeProduto();
    }
}
