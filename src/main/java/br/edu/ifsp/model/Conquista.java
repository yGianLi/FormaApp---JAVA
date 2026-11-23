package br.edu.ifsp.model;

public class Conquista {
    private final String titulo, descricao, categoria, icone, cor;
    private final int atual, meta;

    public Conquista(String titulo, String descricao, String categoria,
                     String icone, String cor, int atual, int meta) {
        this.titulo = titulo; this.descricao = descricao; this.categoria = categoria;
        this.icone = icone; this.cor = cor; this.atual = atual; this.meta = meta;
    }

    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public String getIcone() { return icone; }
    public String getCor() { return cor; }
    public int getAtual() { return atual; }
    public int getMeta() { return meta; }
}