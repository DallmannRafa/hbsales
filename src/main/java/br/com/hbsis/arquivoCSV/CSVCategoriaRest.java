package br.com.hbsis.arquivoCSV;


import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.categoriaProdutos.ICategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/categoriafiles")
public class CSVCategoriaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVCategoriaRest.class);
    private CategoriaService categoriaService;
    private final CSVCategoriaService CSVCategoriaService;

    private ICategoriaRepository repository;

    @Autowired
    public CSVCategoriaRest(CategoriaService categoriaService, CSVCategoriaService arquivoService, ICategoriaRepository repository) {
        this.categoriaService = categoriaService;
        this.CSVCategoriaService = arquivoService;
        this.repository = repository;
    }

    @GetMapping("/export_cats")
    public void exportCategorias(HttpServletResponse response) throws Exception {
        CSVCategoriaService.manyToCSV(response);
    }

    @GetMapping("/export_cat/{id}")
    public void exportCategoria(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        this.CSVCategoriaService.oneToCSV(response, id);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        this.CSVCategoriaService.readCSV(file);
    }

}
