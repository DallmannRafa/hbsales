package br.com.hbsis.arquivoCSV.CSVPedido;

import br.com.hbsis.produto.Produto;

public class CSVItemModel {

    private int quantidade;
    private Produto produto;

    public CSVItemModel(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "CSVItemModel{" +
                "quantidade=" + quantidade +
                ", produto=" + produto +
                '}';
    }
}
