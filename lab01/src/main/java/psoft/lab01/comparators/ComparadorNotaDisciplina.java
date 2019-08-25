package psoft.lab01.comparators;

import psoft.lab01.enities.Disciplina;

import java.util.Comparator;

public class ComparadorNotaDisciplina implements Comparator<Disciplina> {

    @Override
    public int compare(Disciplina disciplina1, Disciplina disciplina2) {
        if (disciplina1.getNota() < disciplina2.getNota()) {
            return 1;
        } else if (disciplina1.getNota() > disciplina2.getNota()) {
            return -1;
        } else {
            return 0;
        }
    }
}
