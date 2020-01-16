package br.com.hbsis.arquivoCSV.CSVPedido;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pedidos/files")
public class CSVPedidoRest {

    private final CSVPedidoService csvPedidoService;

    @Autowired
    public CSVPedidoRest(CSVPedidoService csvPedidoService) {
        this.csvPedidoService = csvPedidoService;
    }

    @GetMapping("/export_pedidos_por_periodo/{idPeriodo}")
    public void exportLinhasCategoria(HttpServletResponse response, @PathVariable Long idPeriodo) throws Exception{
        this.csvPedidoService.exportToCSVByPeriodo(response, idPeriodo);
    }

}
