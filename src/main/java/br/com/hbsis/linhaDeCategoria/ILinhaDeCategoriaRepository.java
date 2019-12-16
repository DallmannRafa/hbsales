package br.com.hbsis.linhaDeCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILinhaDeCategoriaRepository extends JpaRepository<LinhaDeCategoria, Long> {

    Optional<LinhaDeCategoria> findByCodigoLinhaCategoria (String codigoLinhaCategoria);

    Boolean existsByCodigoLinhaCategoria (String codigoLinhaCategoria);
}
