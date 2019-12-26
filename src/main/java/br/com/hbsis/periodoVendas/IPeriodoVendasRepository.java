package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPeriodoVendasRepository extends JpaRepository<PeriodoVendas, Long> {

    List<PeriodoVendas> findByFornecedor (Fornecedor fornecedor);
}
