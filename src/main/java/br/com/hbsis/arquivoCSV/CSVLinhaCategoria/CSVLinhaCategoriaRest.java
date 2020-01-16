package br.com.hbsis.arquivoCSV.CSVLinhaCategoria;

import br.com.hbsis.arquivoCSV.CSVLinhaCategoria.CSVLinhaCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linhadecategorias/files")
public class CSVLinhaCategoriaRest {

    private final CSVLinhaCategoriaService csvLinhaCategoriaService;

    @Autowired
    public CSVLinhaCategoriaRest(CSVLinhaCategoriaService csvLinhaCategoriaService) {
        this.csvLinhaCategoriaService = csvLinhaCategoriaService;

    }

    @GetMapping("/export_linhas_cat")
    public void exportLinhasCategoria(HttpServletResponse response) throws Exception{
        this.csvLinhaCategoriaService.manyToCSV(response);
    }

    @GetMapping("/export_linha_cat/{id}")
    public void exportLinhacategoria(HttpServletResponse response, @PathVariable Long id) throws Exception{
        this.csvLinhaCategoriaService.oneToCSV(response, id);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        this.csvLinhaCategoriaService.readCSV(file);
    }
}
