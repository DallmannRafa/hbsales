package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByCodigoCategoria(String codigoCategoria);

    Boolean existsByCodigoCategoria(String codigoCategoria);

    List<Categoria> findByFornecedor(Fornecedor fornecedor);

}

