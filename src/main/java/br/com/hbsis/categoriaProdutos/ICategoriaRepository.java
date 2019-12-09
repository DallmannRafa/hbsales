package br.com.hbsis.categoriaProdutos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByCodigoCategoria(String codigoCategoria);

    Boolean existsByCodigoCategoria(String codigoCategoria);

}

