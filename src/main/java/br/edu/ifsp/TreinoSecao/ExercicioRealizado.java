package br.edu.ifsp.TreinoSecao;

/** O que o usuário fez em um exercício durante uma sessão: tick, carga e repetições. */
public class ExercicioRealizado {
    private String nome;
    private boolean concluido;
    private double carga;      // kg
    private int repeticoes;

    public ExercicioRealizado(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public boolean isConcluido() { return concluido; }
    public double getCarga() { return carga; }
    public int getRepeticoes() { return repeticoes; }

    public void setConcluido(boolean concluido) { this.concluido = concluido; }
    public void setCarga(double carga) { this.carga = carga; }
    public void setRepeticoes(int repeticoes) { this.repeticoes = repeticoes; }
}