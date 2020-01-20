package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByPeriodoVendas(PeriodoVendas periodoVendas);

    List<Pedido> findByFornecedor(Fornecedor fornecedor);

    List<Pedido> findByFuncionario(Funcionario funcionario);
}
