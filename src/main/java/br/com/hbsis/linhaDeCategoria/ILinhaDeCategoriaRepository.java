package br.com.hbsis.linhaDeCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILinhaDeCategoriaRepository extends JpaRepository<LinhaDeCategoria, Long> {

    LinhaDeCategoria findByCodigoLinhaCategoria (String codigoLinhaCategoria);

    Boolean existsByCodigoLinhaCategoria (String codigoLinhaCategoria);
}
