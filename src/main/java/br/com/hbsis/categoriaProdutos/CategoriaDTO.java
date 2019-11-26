package br.com.hbsis.categoriaProdutos;

public class CategoriaDTO {
    private long id;
    private String codCategoria;
    private String fornecedorCategoria;
    private String nomeCategoria;

    public CategoriaDTO() {
    }

    public CategoriaDTO(long id, String codCategoria, String fornecedorCategoria, String nomeCategoria) {
        this.id = id;
        this.codCategoria = codCategoria;
        this.fornecedorCategoria = fornecedorCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getCodigoCategoria(),
            categoria.getFornecedorCategoria(),
            categoria.getNomeCategoria()
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

    public String getFornecedorCategoria() {
        return fornecedorCategoria;
    }

    public void setFornecedorCategoria(String fornecedorCategoria) {
        this.fornecedorCategoria = fornecedorCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
