package senai.mercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.mercado.entity.Estoque;
import senai.mercado.service.EstoqueService;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*")
public class EstoqueController {
    @Autowired
    EstoqueService service;

    @PostMapping
    public ResponseEntity<Estoque> adicionar(@RequestBody Estoque estoque){
        this.service.adicionar(estoque);
        return ResponseEntity.ok(estoque);
    }

    @GetMapping
    public List<Estoque> listar(){
        return this.service.listar();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@RequestBody Estoque estoque, @PathVariable Long id){
        this.service.atualizar(estoque, id);
        return ResponseEntity.ok("Estoque atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        this.service.deletar(id);
        return ResponseEntity.ok("Item deletado com sucesso!");
    }

}
