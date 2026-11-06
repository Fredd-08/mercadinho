package senai.mercado.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senai.mercado.entity.Estoque;
import senai.mercado.repository.EstoqueRepository;

import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository repository;

    @Transactional
    public void adicionar(Estoque estoque){
        repository.save(estoque);
    }

    public List<Estoque> listar(){
        return repository.findAll();
    }

    @Transactional
    public void atualizar(Estoque estoque,
                                            Long id) {
        Estoque estoqueAtualizado = repository.findById(id).
                orElseThrow(() -> new RuntimeException
                        ("Não foi possível encontrar esse item."));

        estoqueAtualizado.setQuantidde(estoque.getQuantidde());
        estoqueAtualizado.setTipo(estoque.getTipo());
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }
}
