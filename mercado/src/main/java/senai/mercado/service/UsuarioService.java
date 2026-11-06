package senai.mercado.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import senai.mercado.entity.Usuario;
import senai.mercado.repository.UsuarioRepository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Transactional
    public void adicionar(Usuario usuario) throws Exception {
        usuario.setSenha(sha256(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<Usuario> listar(){
        return repository.findAll();
    }

    //Método findByEmail é necessário para a verificação.
    public Optional<Usuario> findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional
    public void atualizar(Usuario usuario,
                                            Long id) throws Exception {
        Usuario usuarioAtualizado = repository.findById(id).
                orElseThrow(() -> new RuntimeException
                        ("Não foi possível encontrar esse usuário."));
        //^^^ usuarioAtualizado certifica se existe o usuário selecionado para atualizar; caso não exista joga uma exeção.
        usuarioAtualizado.setEmail(usuario.getEmail());
        usuarioAtualizado.setNome(usuario.getNome());
        usuarioAtualizado.setSenha(sha256(usuario.getSenha()));
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }


    //CRIPTOGRAFIA COM HASH UTILIZANDO SHA256
    public static String sha256(String senha) throws Exception{
        MessageDigest mensagem = MessageDigest.getInstance("SHA-256");//Seleciona o algoritmo que vai ser utilizado, nesse caso sha-256.
        mensagem.reset();//Reseta tudo para certificar que está tudo limpo antes de começar o processo de hash.
        mensagem.update(senha.getBytes(StandardCharsets.UTF_8));//Insere texto novo dentro da variável, nesse caso a senha do usuário(também mantém ela dentro do padrão UTF_8).
        return String.format("%064x", new BigInteger(1,mensagem.digest()));//Retorna a string ciptografada. "%064x" define o tamanho da criptografia.
    }
}
