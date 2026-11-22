package br.edu.ifsp.model;

import java.util.List;

public class Treino {
    private final String letra;
    private final String grupo;
    private final String nome;
    private final int minutos;
    private final List<Exercicio> exercicios;

    public Treino(String letra, String grupo, String nome,
                  int minutos, List<Exercicio> exercicios) {
        this.letra = letra;
        this.grupo = grupo;
        this.nome = nome;
        this.minutos = minutos;
        this.exercicios = exercicios;
    }

    public String getLetra() { return letra; }
    public String getGrupo() { return grupo; }
    public String getNome() { return nome; }
    public int getMinutos() { return minutos; }
    public List<Exercicio> getExercicios() { return exercicios; }
}