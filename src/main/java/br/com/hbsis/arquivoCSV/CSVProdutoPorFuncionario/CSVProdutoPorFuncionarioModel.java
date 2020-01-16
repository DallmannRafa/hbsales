package br.com.hbsis.arquivoCSV.CSVProdutoPorFuncionario;

import br.com.hbsis.arquivoCSV.CSVPedido.CSVItemModel;
import br.com.hbsis.produto.Produto;

public class CSVProdutoPorFuncionarioModel extends CSVItemModel {

    private String NomeFuncionario;

    public CSVProdutoPorFuncionarioModel(int quantidade, Produto produto, String nomeFuncionario) {
        super(quantidade, produto);
        NomeFuncionario = nomeFuncionario;
    }

    public String getNomeFuncionario() {
        return NomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        NomeFuncionario = nomeFuncionario;
    }

    @Override
    public String toString() {
        return "CSVProdutoPorFuncionarioModel{" +
                "NomeFuncionario='" + NomeFuncionario + '\'' +
                '}';
    }
}
