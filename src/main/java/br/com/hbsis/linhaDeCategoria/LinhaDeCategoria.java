package br.com.hbsis.linhaDeCategoria;

import br.com.hbsis.categoriaProdutos.Categoria;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Entity
@Table (name = "seg_linha_categorias")
public class LinhaDeCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;
    @Column(name = "nome_linha_categoria", length = 50)
    @CsvBindByPosition(position = 1)
    private String nomeLinhaCategoria;
    @Column(name = "codigo_linha_categoria", length = 10)
    @CsvBindByPosition(position = 2)
    private String codigoLinhaCategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria_da_linha", referencedColumnName = "id")
    @CsvBindByPosition(position = 3)
    private Categoria categoriaDaLinhaCategoria;

    public LinhaDeCategoria() {
    }

    public LinhaDeCategoria(Long id, String nomeLinhaCategoria, String codigoLinhaCategoria, Categoria categoriaDaLinhaCategoria) {
        this.id = id;
        this.nomeLinhaCategoria = nomeLinhaCategoria;
        this.codigoLinhaCategoria = codigoLinhaCategoria;
        this.categoriaDaLinhaCategoria = categoriaDaLinhaCategoria;
    }

    public Long getId() {
        return id;
    }

    public String getNomeLinhaCategoria() {
        return nomeLinhaCategoria;
    }

    public void setNomeLinhaCategoria(String nomeLinhaCategoria) {
        this.nomeLinhaCategoria = nomeLinhaCategoria;
    }

    public String getCodigoLinhaCategoria() {
        return codigoLinhaCategoria;
    }

    public void setCodigoLinhaCategoria(String codigoLinhaCategoria) {
        this.codigoLinhaCategoria = codigoLinhaCategoria;
    }

    public Categoria getCategoriaDaLinhaCategoria() {
        return categoriaDaLinhaCategoria;
    }

    public void setCategoriaDaLinhaCategoria(Categoria categoriaDaLinhaCategoria) {
        this.categoriaDaLinhaCategoria = categoriaDaLinhaCategoria;
    }

    @Override
    public String toString() {
        return "LinhaDeCategoria{" +
                "id=" + id +
                ", nomeLinhaCategoria='" + nomeLinhaCategoria + '\'' +
                ", codigoLinhaCategoria='" + codigoLinhaCategoria + '\'' +
                ", categoriaDaLinhaCategoria=" + categoriaDaLinhaCategoria +
                '}';
    }
}

