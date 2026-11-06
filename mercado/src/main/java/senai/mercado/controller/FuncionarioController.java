package senai.mercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.mercado.entity.Funcionario;
import senai.mercado.service.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {
    @Autowired
    FuncionarioService service;

    @PostMapping
    public ResponseEntity<Funcionario> adicionar(@RequestBody Funcionario funcionario) throws Exception {
        this.service.adicionar(funcionario);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    public List<Funcionario> listar(){
        return this.service.listar();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@RequestBody Funcionario funcionario, @PathVariable Long id) throws Exception {
        this.service.atualizar(funcionario, id);
        return ResponseEntity.ok("Funcionário atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        this.service.deletar(id);
        return ResponseEntity.ok("Funcionário deletado com sucesso!");
    }
}
