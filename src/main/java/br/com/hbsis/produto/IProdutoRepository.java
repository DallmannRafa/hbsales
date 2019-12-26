package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.ILinhaDeCategoriaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigoProduto(String codigoProduto);

    Optional<Produto> findByNomeProduto (String nomeProduto);

    Boolean existsByCodigoProduto(String codigoProduto);

    Boolean existsByNomeProduto (String nomeProduto);
}
