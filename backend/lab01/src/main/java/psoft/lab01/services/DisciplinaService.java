package psoft.lab01.services;

import org.springframework.stereotype.Service;
import psoft.lab01.comparators.ComparadorNotaDisciplina;
import psoft.lab01.enities.Disciplina;
import psoft.lab01.enities.DisciplinaDTO;

import java.util.*;

@Service
public class DisciplinaService {

    private Map<Integer, Disciplina> disciplinas = new HashMap<>();
    private int idAtual = 0;

    public Disciplina adicionaDisciplina(DisciplinaDTO novaDisciplina) {
        Disciplina disciplina = new Disciplina(++idAtual, novaDisciplina.getNome(), novaDisciplina.getNota());
        disciplinas.put(idAtual, disciplina);
        return disciplina;
    }

    public Collection<Disciplina> getDisciplinas() {
        return disciplinas.values();
    }

    private void validaExistenciaDisciplina(int id) {
        if (!disciplinas.containsKey(id)) {
            throw new NoSuchElementException(String.format("Disciplina %i não encontrada", id));
        }
    }

    public Disciplina getDisciplina(int id) {
       this.validaExistenciaDisciplina(id);
       return disciplinas.get(id);
    }

    public Disciplina atualizaNomeDisciplina(int id, String novoNome) {
        this.validaExistenciaDisciplina(id);
        disciplinas.get(id).setNome(novoNome);
        return disciplinas.get(id);
    }

    public Disciplina atualizaNotaDisciplina(int id, double novaNota) {
        this.validaExistenciaDisciplina(id);
        disciplinas.get(id).setNota(novaNota);
        return disciplinas.get(id);
    }

    public Disciplina deletaDisciplina(int id) {
        this.validaExistenciaDisciplina(id);

        Disciplina disciplina = disciplinas.get(id);
        disciplinas.remove(id);
        return disciplina;
    }

    public List<Disciplina> exibeRankingDisciplinas() {
        List<Disciplina> ranking = new ArrayList<>(disciplinas.values());
        Collections.sort(ranking, new ComparadorNotaDisciplina());
        return ranking;
    }
}
