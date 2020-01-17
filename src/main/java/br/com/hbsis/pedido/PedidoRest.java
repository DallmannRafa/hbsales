package br.com.hbsis.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoRest {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO) {
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
    public PedidoDTO update(@PathVariable Long id) {
        return this.pedidoService.cancelaPedido(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        return this.pedidoService.update(pedidoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.pedidoService.delete(id);
    }


}
