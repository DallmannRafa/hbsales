package br.com.hbsis.categoriaProdutos;


import javax.persistence.*;

@Entity
@Table(name = "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_categoria", length = 12)
    private String codigoCategoria;
    @Column(name = "fornecedor_categoria", length = 100)
    private String fornecedorCategoria;
    @Column(name = "nome_categoria", length = 100)
    private String nomeCategoria;

    public Long getId() {
        return id;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
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

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", codigoCategoria='" + codigoCategoria + '\'' +
                ", fornecedorCategoria='" + fornecedorCategoria + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}
