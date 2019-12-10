package br.com.hbsis.categoriaProdutos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByCodigoCategoria(String codigoCategoria);

    Boolean existsByCodigoCategoria(String codigoCategoria);

}

