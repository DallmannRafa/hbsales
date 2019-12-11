package br.com.hbsis.produto;

import br.com.hbsis.linhaDeCategoria.ILinhaDeCategoriaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoReoository extends JpaRepository<Produto, Long> {


}
