package senai.mercado.controller;

import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import senai.mercado.entity.Usuario;
import senai.mercado.service.UsuarioService;
import static senai.mercado.service.UsuarioService.sha256;

import java.util.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")

public class UsuarioController {
    @Autowired
    UsuarioService service;

    //Método de login
    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario, HttpSession session) throws Exception{
        Optional<Usuario> usuarioTemp = this.service.findByEmail(usuario.getEmail()); //UsuarioTempo utiliza o método findByEmail para encontrar se existe um email.
        if (usuarioTemp.isPresent()){//Checa se está presente no banco
            session.setAttribute("usuario", usuarioTemp);

            if ((sha256(usuario.getSenha()).equals(usuarioTemp.get().getSenha()))){//criptografa a senha inserida(pois não é possível descriptografar o hash) e compara com a senha no banco.
                return ResponseEntity.status(HttpStatus.OK).body("Login efetuado com sucesso.");
            } else {//caso a senha não esteja presente no banco, o login é recusado.
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A senha está incorreta.");
            }
        } else {//caso não seja encontrado o email inserido no banco o login é recusado.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não foi possível encontrar um usuário com esse e-mail.");
        }
    }

    //Método de logout
    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        //Pega a sessão presente
        HttpSession session = request.getSession(false);
        if (session != null){//Caso encontrem uma sessão presente ela é encerrada.
            session.invalidate();
        } else {//Caso não seja encontrada uma sessão presente, nada acontece, apenas um aviso.
            return ResponseEntity.badRequest().body("Não foi possível encerrar a sessão.");
        }

        //Sessão abaixo limpa todos os cookies da sessão.
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        //Mensagem de sucesso
        return ResponseEntity.ok("Sessão encerrada com suesso.");
    }

    @PostMapping
    public ResponseEntity<Usuario> adicionar(@RequestBody Usuario usuario) throws Exception {
        this.service.adicionar(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public List<Usuario> listar(){
        return this.service.listar();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@RequestBody Usuario usuario, @PathVariable Long id) throws Exception {
        this.service.atualizar(usuario, id);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        this.service.deletar(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }
}
