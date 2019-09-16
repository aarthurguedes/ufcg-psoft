package psoft.lab01.enities;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class DisciplinaDTO {

    private String nome;
    private double nota;

    @JsonCreator
    public DisciplinaDTO(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisciplinaDTO that = (DisciplinaDTO) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
