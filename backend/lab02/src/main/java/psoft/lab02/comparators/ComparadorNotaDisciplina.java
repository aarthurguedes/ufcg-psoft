package psoft.lab02.comparators;

import psoft.lab02.entities.Disciplina;

import java.util.Comparator;

public class ComparadorNotaDisciplina implements Comparator<Disciplina> {

    @Override
    public int compare(Disciplina disciplina, Disciplina t1) {
        if (disciplina.getNota() < t1.getNota()) {
            return 1;
        } else if (disciplina.getNota() > t1.getNota()) {
            return -1;
        } else {
            return 0;
        }
    }
}
