package psoft.lab02.comparators;

import psoft.lab02.entities.Disciplina;

import java.util.Comparator;

public class ComparadorLikeDisciplina implements Comparator<Disciplina> {

    @Override
    public int compare(Disciplina disciplina, Disciplina t1) {
        if (disciplina.getLikes() < t1.getLikes()) {
            return 1;
        } else if (disciplina.getLikes() > t1.getLikes()) {
            return -1;
        } else {
            return 0;
        }
    }
}
