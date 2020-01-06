package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.produto.Produto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "status")
    private PedidoEnum statusPedido;
    @Column(name = "data")
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "id")
    private Fornecedor fornecedor;

    @ManyToMany
    @JoinTable(name = "seg_pedidos_produtos",
                joinColumns = @JoinColumn(name = "pedido_id"),
                inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

}
