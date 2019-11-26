package br.com.hbsis.categoriaProdutos;


import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_categoria", length = 12)
    private String codigoCategoria;
    @Column(name = "nome_categoria", length = 100)
    private String nomeCategoria;

    @ManyToOne
    private Fornecedor fornecedor;

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
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
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}
