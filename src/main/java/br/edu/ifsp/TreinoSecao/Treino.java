package br.edu.ifsp.TreinoSecao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Um treino criado pelo usuário (ex: "Costas e bíceps") com sua lista de exercícios. */
public class Treino {
    private String id = UUID.randomUUID().toString();
    private String nome;
    private String grupo;          // "Superior", "Inferior"...
    private int duracaoMin;
    private List<String> exercicios = new ArrayList<>();

    public Treino(String nome, String grupo, int duracaoMin) {
        this.nome = nome;
        this.grupo = grupo;
        this.duracaoMin = duracaoMin;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getGrupo() { return grupo; }
    public int getDuracaoMin() { return duracaoMin; }
    public List<String> getExercicios() { return exercicios; }

    public void setNome(String nome) { this.nome = nome; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public void setDuracaoMin(int duracaoMin) { this.duracaoMin = duracaoMin; }
}