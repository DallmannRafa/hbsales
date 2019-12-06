package br.com.hbsis.arquivoCSV;

import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linhacategoriafiles")
public class CSVLinhaCategoriaRest {

    private final CSVLinhaCategoriaService csvLinhaCategoriaService;
    private final LinhaDeCategoriaService linhaDeCategoriaService;

    @Autowired
    public CSVLinhaCategoriaRest(CSVLinhaCategoriaService csvLinhaCategoriaService, LinhaDeCategoriaService linhaDeCategoriaService) {
        this.csvLinhaCategoriaService = csvLinhaCategoriaService;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
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
