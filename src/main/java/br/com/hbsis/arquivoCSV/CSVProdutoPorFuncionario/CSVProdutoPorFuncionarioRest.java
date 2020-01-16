package br.com.hbsis.arquivoCSV.CSVProdutoPorFuncionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pedidos/files")
public class CSVProdutoPorFuncionarioRest {

    private final CSVProdutoPorFuncionarioService csvProdutoPorFuncionarioService;

    @Autowired
    public CSVProdutoPorFuncionarioRest(CSVProdutoPorFuncionarioService csvProdutoPorFuncionarioService) {
        this.csvProdutoPorFuncionarioService = csvProdutoPorFuncionarioService;
    }

    @GetMapping("/export_produtos_por_funcionario/{idFornecedor}")
    public void exportLinhasCategoria(HttpServletResponse response, @PathVariable Long idFornecedor) throws Exception{
        this.csvProdutoPorFuncionarioService.exportToCSV(response, idFornecedor);
    }
}
