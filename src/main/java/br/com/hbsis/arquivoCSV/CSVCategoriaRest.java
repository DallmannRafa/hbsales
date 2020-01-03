package br.com.hbsis.arquivoCSV;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/categorias/files")
public class CSVCategoriaRest {

    private final CSVCategoriaService CSVCategoriaService;

    @Autowired
    public CSVCategoriaRest(CSVCategoriaService arquivoService) {
        this.CSVCategoriaService = arquivoService;
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
