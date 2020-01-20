package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPeriodoVendasRepository extends JpaRepository<PeriodoVendas, Long> {

    Page<PeriodoVendas> findByFornecedor(Fornecedor fornecedor, Pageable pageable);
}
