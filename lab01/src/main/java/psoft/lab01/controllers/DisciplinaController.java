package psoft.lab01.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.lab01.enities.Disciplina;
import psoft.lab01.enities.DisciplinaDTO;
import psoft.lab01.services.DisciplinaService;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/disciplinas")
    public ResponseEntity<Disciplina> adicionaDisciplina(@RequestBody DisciplinaDTO novaDisciplina) {
        return new ResponseEntity<>(disciplinaService.adicionaDisciplina(novaDisciplina), HttpStatus.CREATED);
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<Collection<Disciplina>> getDisciplinas() {
        return new ResponseEntity<>(disciplinaService.getDisciplinas(), HttpStatus.OK);
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity getDisciplina(@PathVariable(value = "id") int id) {
        try {
            return new ResponseEntity<>(disciplinaService.getDisciplina(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/disciplinas/{id}/nome")
    public ResponseEntity atualizaNomeDisciplina(@PathVariable("id")  int id, @RequestBody DisciplinaDTO novoNomeDisciplina) {
        try {
            return new ResponseEntity<>(disciplinaService.atualizaNomeDisciplina(id, novoNomeDisciplina.getNome()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/disciplinas/{id}/nota")
    public ResponseEntity atualizaNotaDisciplina(@PathVariable("id")  int id, @RequestBody DisciplinaDTO novaNotaDisciplina) {
        try {
            return new ResponseEntity<>(disciplinaService.atualizaNotaDisciplina(id, novaNotaDisciplina.getNota()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity deletaDisciplina(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(disciplinaService.deletaDisciplina(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/disciplinas/ranking")
    public ResponseEntity<List<Disciplina>> exibeRankingDisciplinas() {
        return new ResponseEntity<>(disciplinaService.exibeRankingDisciplinas(), HttpStatus.OK);
    }
}
