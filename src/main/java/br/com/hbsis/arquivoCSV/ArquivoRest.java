package br.com.hbsis.arquivoCSV;


import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.categoriaProdutos.ICategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/files")
public class ArquivoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoRest.class);
    private CategoriaService categoriaService;
    private final ArquivoService arquivoService;

    private ICategoriaRepository repository;

    @Autowired
    public ArquivoRest(CategoriaService categoriaService, ArquivoService arquivoService, ICategoriaRepository repository) {
        this.categoriaService = categoriaService;
        this.arquivoService = arquivoService;
        this.repository = repository;
    }

    @GetMapping("/export_cats")
    public void exportCategorias(HttpServletResponse response) throws Exception {
        arquivoService.manyToCSV(response);
    }

    @GetMapping("/export_cat/{id}")
    public void exportCategoria(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        this.arquivoService.oneToCSV(response, id);
    }

    @PostMapping(value = "/upload", consumes = "text/csv")
    public void uploadSimple(@RequestBody InputStream body) throws IOException {
        repository.saveAll(ArquivoService.read(Categoria.class, body));
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        repository.saveAll(ArquivoService.read(Categoria.class, file.getInputStream()));
    }

}
