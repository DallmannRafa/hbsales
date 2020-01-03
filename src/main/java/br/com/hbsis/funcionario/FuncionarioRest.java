package br.com.hbsis.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioRest {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO) {
        return this.funcionarioService.save(funcionarioDTO);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO find(@PathVariable("id") Long id) {
        return this.funcionarioService.findById(id);
    }

    @GetMapping
    public List<Funcionario> findAll() {
        return this.funcionarioService.findAll();
    }

    @PutMapping("/{id}")
    public FuncionarioDTO update(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        return this.funcionarioService.update(id, funcionarioDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.funcionarioService.delete(id);
    }

}
