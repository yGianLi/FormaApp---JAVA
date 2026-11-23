package br.edu.ifsp.TreinoSecao;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda treinos e histórico em ~/.forma/dados.json
 * Uso: RepositorioTreinos.get().getTreinos()
 */
public class RepositorioTreinos {

    private static class Dados {
        List<Treino> treinos = new ArrayList<>();
        List<SessaoTreino> sessoes = new ArrayList<>();
    }

    private static RepositorioTreinos instancia;

    public static RepositorioTreinos get() {
        if (instancia == null) instancia = new RepositorioTreinos();
        return instancia;
    }

    private final Path arquivo = Path.of(System.getProperty("user.home"), ".forma", "dados.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Dados dados = new Dados();

    private RepositorioTreinos() {
        boolean primeiraVez = !Files.exists(arquivo);
        carregar();
        if (primeiraVez) {
            criarTreinosIniciais();
            salvar();
        }
    }

    // ---------- Treinos ----------
    public List<Treino> getTreinos() { return dados.treinos; }

    public void adicionarTreino(Treino t) {
        dados.treinos.add(t);
        salvar();
    }

    public void removerTreino(String treinoId) {
        dados.treinos.removeIf(t -> t.getId().equals(treinoId));
        salvar();
    }

    public void adicionarExercicio(Treino t, String nomeExercicio) {
        t.getExercicios().add(nomeExercicio);
        salvar();
    }

    public void removerExercicio(Treino t, String nomeExercicio) {
        t.getExercicios().remove(nomeExercicio);
        salvar();
    }

    /** Use depois de editar nome/grupo/duração de um treino. */
    public void salvarAlteracoes() { salvar(); }

    // ---------- Sessões (histórico) ----------
    public List<SessaoTreino> getSessoes() { return dados.sessoes; }

    public void salvarSessao(SessaoTreino s) {
        dados.sessoes.add(s);
        salvar();
    }

    /** Para mostrar "treino concluído hoje" na tela. */
    public boolean concluidoHoje(String treinoId) {
        LocalDate hoje = LocalDate.now();
        return dados.sessoes.stream().anyMatch(s ->
                s.isConcluida() && s.getTreinoId().equals(treinoId) && s.getData().equals(hoje));
    }

    /** Para o card "Treinos concluídos" da visão geral. */
    public long concluidosNaSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate segunda = hoje.minusDays(hoje.getDayOfWeek().getValue() - 1L);
        return dados.sessoes.stream()
                .filter(SessaoTreino::isConcluida)
                .filter(s -> !s.getData().isBefore(segunda))
                .count();
    }

    // ---------- Arquivo ----------
    private void carregar() {
        try {
            if (Files.exists(arquivo)) {
                Dados lido = gson.fromJson(Files.readString(arquivo), Dados.class);
                if (lido != null) dados = lido;
            }
        } catch (IOException | RuntimeException e) {
            System.err.println("Não foi possível ler " + arquivo + ": " + e.getMessage());
        }
    }

    private void salvar() {
        try {
            Files.createDirectories(arquivo.getParent());
            Files.writeString(arquivo, gson.toJson(dados));
        } catch (IOException e) {
            System.err.println("Não foi possível salvar " + arquivo + ": " + e.getMessage());
        }
    }

    private void criarTreinosIniciais() {
        Treino a = new Treino("Costas e bíceps", "Superior", 55);
        a.getExercicios().addAll(List.of("Puxada frontal", "Remada baixa", "Remada Unilateral",
                "Rosca Direta", "Rosca Martelo"));
        Treino b = new Treino("Pernas e glúteos", "Inferior", 60);
        Treino c = new Treino("Peito e tríceps", "Superior", 50);
        dados.treinos.addAll(List.of(a, b, c));
    }
}