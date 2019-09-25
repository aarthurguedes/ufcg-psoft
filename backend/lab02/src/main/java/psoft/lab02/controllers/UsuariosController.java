package psoft.lab02.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.lab02.entities.Usuario;
import psoft.lab02.services.JWTService;
import psoft.lab02.services.UsuariosService;

import javax.servlet.ServletException;


@RestController
public class UsuariosController {

    private UsuariosService usuariosService;
    private JWTService jwtService;

    public UsuariosController(UsuariosService usuariosService, JWTService jwtService) {
        this.usuariosService = usuariosService;
        this.jwtService = jwtService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> adicionaUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuariosService.adicionaUsuario(usuario), HttpStatus.OK);
    }

    @DeleteMapping("/auth/usuarios/{email}")
    public ResponseEntity<Usuario> removeUsuario(@PathVariable String email, @RequestHeader("Authorization") String header) {
        if (!usuariosService.getUsuario(email).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            if (jwtService.usuarioTemPermissao(header, email)) {
                return new ResponseEntity<>(usuariosService.removeUsuario(email), HttpStatus.OK);
            }
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
