package br.com.hbsis.categoriaProdutos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe responsável pela comunciação com o banco de dados
 */

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
}
