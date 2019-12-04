package br.com.hbsis.linhaDeCategoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/linhadecategorias")
public class LinhaDeCategoriaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaDeCategoriaRest.class);

    private final LinhaDeCategoriaService linhaDeCategoriaService;

    @Autowired
    public LinhaDeCategoriaRest(LinhaDeCategoriaService linhaDeCategoriaService) {
        this.linhaDeCategoriaService = linhaDeCategoriaService;
    }

    @PostMapping
    public LinhaDeCategoriaDTO save(@RequestBody LinhaDeCategoriaDTO linhaDeCategoriaDTO) {
        return this.linhaDeCategoriaService.save(linhaDeCategoriaDTO);
    }

    @GetMapping("/{id}")
    public LinhaDeCategoriaDTO find(@PathVariable("id") Long id) {
        return this.linhaDeCategoriaService.findById(id);
    }

    @RequestMapping("/listar")
    public List<LinhaDeCategoria> findAll()  {
        return this.linhaDeCategoriaService.findAll();
    }

    @PostMapping("/{id}")
    public LinhaDeCategoriaDTO update (@PathVariable Long id, @RequestBody LinhaDeCategoriaDTO linhaDeCategoriaDTO) {
        return this.linhaDeCategoriaService.update(linhaDeCategoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.linhaDeCategoriaService.delete(id);
    }
}
