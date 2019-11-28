package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Entity
@Table(name = "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @Column(name = "codigo_categoria", length = 12)
    @CsvBindByPosition(position = 1)
    private String codigoCategoria;
    @Column(name = "nome_categoria", length = 100)
    @CsvBindByPosition(position = 2)
    private String nomeCategoria;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    @CsvBindByPosition(position = 3)
    private Fornecedor fornecedor;

    public Categoria() {
    }

    public Categoria(Long id, String codigoCategoria, String nomeCategoria, Fornecedor fornecedor) {
        this.id = id;
        this.codigoCategoria = codigoCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
    }

    public Categoria(String codigoCategoria, String nomeCategoria, Fornecedor fornecedor) {
        this.codigoCategoria = codigoCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
    }

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
                ", fornecedor=" + fornecedor +
                '}';
    }
}
