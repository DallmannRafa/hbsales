package br.com.hbsis.produto;

import br.com.hbsis.arquivoCSV.CSVProduto.CSVProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);

    private final ProdutoService produtoService;
    private final CSVProdutoService csvProdutoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService, CSVProdutoService csvProdutoService) {
        this.produtoService = produtoService;
        this.csvProdutoService = csvProdutoService;
    }

    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo solicitação de persistência de produto...");
        LOGGER.debug("Payaload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }

    @GetMapping("/{id}")
    public ProdutoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.produtoService.findById(id);
    }

    @GetMapping("/listar")
    public List<Produto> findProdutos() {
        return this.produtoService.findAll();
    }

    @PutMapping("/{id}")
    public ProdutoDTO update(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo Update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.update(produtoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria de ID: {}", id);

        this.produtoService.delete(id);
    }

    @GetMapping("/files/export_produtos")
    public void exportCategorias(HttpServletResponse response) throws Exception {
        csvProdutoService.manyToCSV(response);
    }

    @GetMapping("/files/export_produto/{id}")
    public void exportCategoria(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        this.csvProdutoService.oneToCSV(response, id);
    }

    @PostMapping(value = "/files/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        this.csvProdutoService.readCSV(file);
    }

    @PostMapping(value = "/files/upload/{id}", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        this.csvProdutoService.readProdutoPorFornecedor(file, id);
    }
}
