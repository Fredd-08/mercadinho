package senai.mercado.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senai.mercado.entity.Funcionario;
import senai.mercado.repository.FuncionarioRepository;

import java.util.List;

import static senai.mercado.service.UsuarioService.sha256;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository repository;

    @Transactional
    public void adicionar(Funcionario funcionario) throws Exception {
        funcionario.setSenha(sha256(funcionario.getSenha()));
        repository.save(funcionario);
    }

    public List<Funcionario> listar(){
        return repository.findAll();
    }

    @Transactional
    public void atualizar(Funcionario funcionario,
                                            Long id) throws Exception {
        Funcionario funcionarioAtualizado = repository.findById(id).
                orElseThrow(() -> new RuntimeException
                        ("Não foi possível encontrar esse funcionário."));
        //^^^ funcionarioAtualizado certifica se existe o funcionário selecionado para atualizar; caso não exista joga uma exeção.
        funcionarioAtualizado.setEmail(funcionario.getEmail());
        funcionarioAtualizado.setNome(funcionario.getNome());
        funcionarioAtualizado.setSenha(sha256(funcionario.getSenha()));
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }
}
