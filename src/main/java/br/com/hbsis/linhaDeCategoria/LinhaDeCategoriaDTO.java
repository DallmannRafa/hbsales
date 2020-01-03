package br.com.hbsis.linhaDeCategoria;

import br.com.hbsis.categoriaProdutos.Categoria;

public class LinhaDeCategoriaDTO {
    private Long id;
    private String codigoLinhaCategoria;
    private String nomeLinhaCategoria;
    private Categoria categoriaDaLinhaCategoria;

    public LinhaDeCategoriaDTO() {
    }

    public LinhaDeCategoriaDTO(Long id, String codigoLinhaCategoria, String nomeLinhaCategoria, Categoria categoriaDaLinhaCategoria) {
        this.id = id;
        this.codigoLinhaCategoria = codigoLinhaCategoria;
        this.nomeLinhaCategoria = nomeLinhaCategoria;
        this.categoriaDaLinhaCategoria = categoriaDaLinhaCategoria;
    }

    public static LinhaDeCategoriaDTO of(LinhaDeCategoria linhaDeCategoria) {
        return new LinhaDeCategoriaDTO(
            linhaDeCategoria.getId(),
            linhaDeCategoria.getCodigoLinhaCategoria(),
            linhaDeCategoria.getNomeLinhaCategoria(),
            linhaDeCategoria.getCategoriaDaLinhaCategoria()
        );
    }

    public Long getId() {
        return id;
    }

    public String getCodigoLinhaCategoria() {
        return codigoLinhaCategoria;
    }

    public void setCodigoLinhaCategoria(String codigoLinhaCategoria) {
        this.codigoLinhaCategoria = codigoLinhaCategoria;
    }

    public String getNomeLinhaCategoria() {
        return nomeLinhaCategoria;
    }

    public void setNomeLinhaCategoria(String nomeLinhaCategoria) {
        this.nomeLinhaCategoria = nomeLinhaCategoria;
    }

    public Categoria getCategoriaDaLinhaCategoria() {
        return categoriaDaLinhaCategoria;
    }

    public void setCategoriaDaLinhaCategoria(Categoria categoriaDaLinhaCategoria) {
        this.categoriaDaLinhaCategoria = categoriaDaLinhaCategoria;
    }

    @Override
    public String toString() {
        return "LinhaDeCategoriaDTO{" +
                "id=" + id +
                ", codigoLinhaCategoria='" + codigoLinhaCategoria + '\'' +
                ", nomeLinhaCategoria='" + nomeLinhaCategoria + '\'' +
                ", categoriaDaLinhaCategoria=" + categoriaDaLinhaCategoria +
                '}';
    }
}
