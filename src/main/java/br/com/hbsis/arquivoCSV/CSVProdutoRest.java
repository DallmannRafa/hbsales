package br.com.hbsis.arquivoCSV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/produtos/files")
public class CSVProdutoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVProdutoRest.class);
    private final CSVProdutoService csvProdutoService;

    public CSVProdutoRest(CSVProdutoService csvProdutoService) {
        this.csvProdutoService = csvProdutoService;
    }

    @GetMapping("/export_produtos")
    public void exportCategorias(HttpServletResponse response) throws Exception {
        csvProdutoService.manyToCSV(response);
    }

    @GetMapping("/export_produto/{id}")
    public void exportCategoria(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        this.csvProdutoService.oneToCSV(response, id);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        this.csvProdutoService.readCSV(file);
    }

    @PostMapping(value = "/upload/{id}", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        this.csvProdutoService.readProdutoPorFornecedor(file, id);
    }

}

