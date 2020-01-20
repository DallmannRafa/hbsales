package br.com.hbsis.pedido;

import br.com.hbsis.arquivoCSV.CSVPedido.CSVPedidoService;
import br.com.hbsis.arquivoCSV.CSVProdutoPorFuncionario.CSVProdutoPorFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoRest {

    private final PedidoService pedidoService;
    private final CSVProdutoPorFuncionarioService csvProdutoPorFuncionarioService;
    private final CSVPedidoService csvPedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService, CSVProdutoPorFuncionarioService csvProdutoPorFuncionarioService, CSVPedidoService csvPedidoService) {
        this.pedidoService = pedidoService;
        this.csvProdutoPorFuncionarioService = csvProdutoPorFuncionarioService;
        this.csvPedidoService = csvPedidoService;
    }

    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO) throws MessagingException {
        return this.pedidoService.save(pedidoDTO);
    }

    @GetMapping("/{id}")
    public PedidoDTO find(@PathVariable("id") Long id) {
        return this.pedidoService.findById(id);
    }

    @GetMapping("/pedido_por_funcionario/{id}")
    public List<PedidoDTO> findByFuncionario(@PathVariable("id") Long id) {
        return this.pedidoService.findPedidosByFuncionario(id);
    }

    @PutMapping("/cancelar/{id}")
    public PedidoDTO cancelar(@PathVariable Long id) {
        return this.pedidoService.cancelaPedido(id);
    }

    @PutMapping("/retirar/{id}")
    public PedidoDTO retirar(@PathVariable Long id) {
        return this.pedidoService.retiraPedido(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        return this.pedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.pedidoService.delete(id);
    }

    @GetMapping("/files/export_produtos_por_funcionario/{idFornecedor}")
    public void exportProdutoPorFuncionario(HttpServletResponse response, @PathVariable Long idFornecedor) throws Exception {
        this.csvProdutoPorFuncionarioService.exportToCSV(response, idFornecedor);
    }

    @GetMapping("/files/export_pedidos_por_periodo/{idPeriodo}")
    public void exporPedidosPorPeriodo(HttpServletResponse response, @PathVariable Long idPeriodo) throws Exception {
        this.csvPedidoService.exportToCSVByPeriodo(response, idPeriodo);
    }

}
