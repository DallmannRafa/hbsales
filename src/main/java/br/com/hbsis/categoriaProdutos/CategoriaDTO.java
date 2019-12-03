package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.Fornecedor;

public class CategoriaDTO {
    private long id;

    private String codCategoria;
    private String nomeCategoria;
    private Fornecedor fornecedor;



    public CategoriaDTO() {
    }

    public CategoriaDTO(String codCategoria, String nomeCategoria, Fornecedor fornecedor) {
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
    }

    public CategoriaDTO(long id, String codCategoria, String nomeCategoria, Fornecedor fornecedor) {
        this.id = id;
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getCodigoCategoria(),
                categoria.getNomeCategoria(),
                categoria.getFornecedor()
        );
    }

    public Long getId() {
        return id;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", codCategoria='" + codCategoria + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}
