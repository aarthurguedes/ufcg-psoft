package psoft.lab02.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.lab02.entities.Disciplina;
import psoft.lab02.services.DisciplinasService;
import psoft.lab02.services.JWTService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DisciplinasController {

    private DisciplinasService disciplinasService;
    private JWTService jwtService;

    public DisciplinasController(DisciplinasService disciplinasService, JWTService jwtService) {
        this.disciplinasService = disciplinasService;
        this.jwtService = jwtService;
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinas(@RequestHeader("Authorization") String header) {
        try {
            if (jwtService.usuarioTemPermissao(header)) {
                return new ResponseEntity<>(disciplinasService.getDisciplinas(), HttpStatus.OK);
            }
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<Optional<Disciplina>> getDisciplina(@PathVariable ("id") Long id) {
        try {
            return new ResponseEntity<>(disciplinasService.getDisciplina(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/disciplinas/likes/{id}")
    public ResponseEntity<Disciplina> like(@PathVariable ("id") Long id, @RequestHeader("Authorization") String header) {
        try {
            if (jwtService.usuarioTemPermissao(header)) {
                return new ResponseEntity<>(disciplinasService.like(id), HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/disciplinas/nota/{id}")
    public ResponseEntity<Disciplina> atualizaNota(@PathVariable("id") Long id, @RequestBody Disciplina disciplina,
                                                   @RequestHeader("Authorization") String header) {
        try {
            if (jwtService.usuarioTemPermissao(header)) {
                return new ResponseEntity<>(disciplinasService.atualizaNota(id, disciplina.getNota()), HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/disciplinas/comentarios/{id}")
    public ResponseEntity<Disciplina> comenta(@PathVariable("id") Long id, @RequestBody Disciplina disciplina,
                                              @RequestHeader("Authorization") String header) {
        try {
            if (jwtService.usuarioTemPermissao(header)) {
                return new ResponseEntity<>(disciplinasService.comenta(id, disciplina.getComentarios()), HttpStatus.OK);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/disciplinas/ranking/notas")
    public ResponseEntity<List<Disciplina>> exibeRankingNotas() {
        return new ResponseEntity<>(disciplinasService.exibeRankingNotas(), HttpStatus.OK);
    }

    @GetMapping("/disciplinas/ranking/likes")
    public ResponseEntity<List<Disciplina>> exibeRankingLikes() {
        return new ResponseEntity<>(disciplinasService.exibeRankingLikes(), HttpStatus.OK);
    }
}
