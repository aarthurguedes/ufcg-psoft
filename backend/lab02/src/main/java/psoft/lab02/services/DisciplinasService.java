package psoft.lab02.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psoft.lab02.comparators.ComparadorLikeDisciplina;
import psoft.lab02.comparators.ComparadorNotaDisciplina;
import psoft.lab02.daos.DisciplinasRepository;
import psoft.lab02.entities.Disciplina;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class DisciplinasService {

    @Autowired
    private DisciplinasRepository<Disciplina, Long> disciplinasDAO;

    public DisciplinasService(DisciplinasRepository<Disciplina, Long> disciplinasDAO) {
        this.disciplinasDAO = disciplinasDAO;
    }

    @PostConstruct
    public void initDisciplinas() {
        if (disciplinasDAO.count() > 0) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Disciplina>> typeReference = new TypeReference<List<Disciplina>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/disciplinas.json");
        try {
            List<Disciplina> disciplinas = mapper.readValue(inputStream, typeReference);
            this.disciplinasDAO.saveAll(disciplinas);
            System.out.println("Disciplinas salvas no BD!");
        }  catch (IOException e) {
            System.out.println("Nao foi possivel salvar as disciplinas: " + e.getMessage());
        }
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinasDAO.findAll();
    }

    public Optional<Disciplina> getDisciplina(Long id) {
        if (!disciplinasDAO.findById(id).isPresent()) {
            throw new NoSuchElementException("Disciplina nao encontrada!");
        }
        return disciplinasDAO.findById(id);
    }

    public Disciplina like(Long id) {
        if (!disciplinasDAO.findById(id).isPresent()) {
            throw new NoSuchElementException("Disciplina nao encontrada!");
        }

        Disciplina disciplina = disciplinasDAO.findById(id).get();
        disciplina.setLikes(disciplina.getLikes() + 1);
        disciplinasDAO.save(disciplina);

        return disciplina;
    }

    public Disciplina atualizaNota(Long id, double nota) {
        if (!disciplinasDAO.findById(id).isPresent()) {
            throw new NoSuchElementException("Disciplina nao encontrada!");
        }

        Disciplina disciplina = disciplinasDAO.findById(id).get();
        disciplina.setNota(nota);
        disciplinasDAO.save(disciplina);

        return disciplina;
    }

    public Disciplina comenta(Long id, String comentario) {
        if (!disciplinasDAO.findById(id).isPresent()) {
            throw new NoSuchElementException("Disciplina nao encontrada!");
        }

        Disciplina disciplina = disciplinasDAO.findById(id).get();

        if (disciplina.getComentarios() == null) {
            disciplina.setComentarios(comentario);
        } else {
            disciplina.setComentarios(disciplina.getComentarios() + System.lineSeparator() + comentario);
        }

        disciplinasDAO.save(disciplina);
        return disciplina;
    }

    public List<Disciplina> exibeRankingNotas() {
        List<Disciplina> ranking = new ArrayList<>(getDisciplinas());
        Collections.sort(ranking, new ComparadorNotaDisciplina());
        return ranking;
    }

    public List<Disciplina> exibeRankingLikes() {
        List<Disciplina> ranking = new ArrayList<>(getDisciplinas());
        Collections.sort(ranking, new ComparadorLikeDisciplina());
        return ranking;
    }
}
