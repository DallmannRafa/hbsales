package br.com.hbsis.linhaDeCategoria;

import br.com.hbsis.arquivoCSV.CSVLinhaCategoria.CSVLinhaCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/linhadecategorias")
public class LinhaDeCategoriaRest {

    private final LinhaDeCategoriaService linhaDeCategoriaService;
    private final CSVLinhaCategoriaService csvLinhaCategoriaService;

    @Autowired
    public LinhaDeCategoriaRest(LinhaDeCategoriaService linhaDeCategoriaService, CSVLinhaCategoriaService csvLinhaCategoriaService) {
        this.linhaDeCategoriaService = linhaDeCategoriaService;
        this.csvLinhaCategoriaService = csvLinhaCategoriaService;
    }

    @PostMapping
    public LinhaDeCategoriaDTO save(@RequestBody LinhaDeCategoriaDTO linhaDeCategoriaDTO) {
        return this.linhaDeCategoriaService.save(linhaDeCategoriaDTO);
    }

    @GetMapping("/{id}")
    public LinhaDeCategoriaDTO find(@PathVariable("id") Long id) {
        return this.linhaDeCategoriaService.findById(id);
    }

    @GetMapping("/listar")
    public List<LinhaDeCategoria> findAll() {
        return this.linhaDeCategoriaService.findAll();
    }

    @PutMapping("/{id}")
    public LinhaDeCategoriaDTO update(@PathVariable Long id, @RequestBody LinhaDeCategoriaDTO linhaDeCategoriaDTO) {
        return this.linhaDeCategoriaService.update(linhaDeCategoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.linhaDeCategoriaService.delete(id);
    }

    @GetMapping("/files/export_linhas_cat")
    public void exportLinhasCategoria(HttpServletResponse response) throws Exception {
        this.csvLinhaCategoriaService.manyToCSV(response);
    }

    @GetMapping("/files/export_linha_cat/{id}")
    public void exportLinhacategoria(HttpServletResponse response, @PathVariable Long id) throws Exception {
        this.csvLinhaCategoriaService.oneToCSV(response, id);
    }

    @PostMapping(value = "/files/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) {
        this.csvLinhaCategoriaService.readCSV(file);
    }
}
