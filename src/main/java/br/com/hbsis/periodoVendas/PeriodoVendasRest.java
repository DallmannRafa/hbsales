package br.com.hbsis.periodoVendas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periodovendas")
public class PeriodoVendasRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendasRest.class);

    private final PeriodoVendasService periodoVendasService;

    @Autowired
    public PeriodoVendasRest(PeriodoVendasService periodoVendasService) {
        this.periodoVendasService = periodoVendasService;
    }

    @PostMapping
    public PeriodoVendasDTO save(@RequestBody PeriodoVendasDTO linhaDeCategoriaDTO) {
        return this.periodoVendasService.save(linhaDeCategoriaDTO);
    }

    @GetMapping("/{id}")
    public PeriodoVendasDTO find(@PathVariable("id") Long id) {
        return this.periodoVendasService.findById(id);
    }

    @RequestMapping("/listar")
    public List<PeriodoVendas> findAll()  {
        return this.periodoVendasService.findAll();
    }

    @PutMapping("/{id}")
    public PeriodoVendasDTO update(@PathVariable Long id, @RequestBody PeriodoVendasDTO linhaDeCategoriaDTO) {
        return this.periodoVendasService.update(linhaDeCategoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.periodoVendasService.delete(id);
    }

}
