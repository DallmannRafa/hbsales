package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.FornecedorDTO;

public class CategoriaDTO {
    private long id;
    private String codCategoria;
    private String nomeCategoria;
    private FornecedorDTO fornecedorDTO;

    public CategoriaDTO() {
    }

    public CategoriaDTO(long id, String codCategoria, String nomeCategoria, FornecedorDTO fornecedorDTO) {
        this.id = id;
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedorDTO = fornecedorDTO;
    }

    public static CategoriaDTO of(Categoria categoria, FornecedorDTO fornecedorDTO) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getCodigoCategoria(),
            categoria.getNomeCategoria(),
            fornecedorDTO
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

    public FornecedorDTO getFornecedorDTO() {
        return fornecedorDTO;
    }

    public void setFornecedorDTO(FornecedorDTO fornecedorDTO) {
        this.fornecedorDTO = fornecedorDTO;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
