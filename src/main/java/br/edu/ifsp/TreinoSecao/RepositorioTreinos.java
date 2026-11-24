package br.edu.ifsp.TreinoSecao;

import br.edu.ifsp.model.Conquista;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Guarda treinos, histórico e conquistas em ~/.forma/dados.json
 * Uso: RepositorioTreinos.get().getTreinos()
 */
public class RepositorioTreinos {

    private static class Dados {
        List<Treino> treinos = new ArrayList<>();
        List<SessaoTreino> sessoes = new ArrayList<>();
        List<Conquista> conquistas = new ArrayList<>();
    } //cria no JSON tres listas, treinos, conquistas e sessoes

    private static RepositorioTreinos instancia;

    public static RepositorioTreinos get() { //As telas não criam o repositório com new. Elas chamam RepositorioTreinos.get()
        if (instancia == null) instancia = new RepositorioTreinos();
        return instancia;
    }

    private final Path arquivo = Path.of(System.getProperty("user.home"), ".forma", "dados.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Dados dados = new Dados();

    private RepositorioTreinos() {
        boolean primeiraVez = !Files.exists(arquivo);
        carregar();
        if (primeiraVez) criarTreinosIniciais();
        if (dados.conquistas == null) dados.conquistas = new ArrayList<>();
        if (dados.conquistas.isEmpty()) criarConquistasIniciais();
        recalcularConquistas();
        salvar();
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
        recalcularConquistas();
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

    // ---------- Conquistas ----------
    public List<Conquista> getConquistas() { return dados.conquistas; }

    private void criarConquistasIniciais() {
        dados.conquistas.addAll(List.of(
                new Conquista("Primeiro passo", "Conclua seu primeiro treino", "Treinos", "🏋", "#e8f0e2", 0, 1),
                new Conquista("No ritmo", "Treine por 3 dias seguidos", "Constância", "🔥", "#fdeee2", 0, 3),
                new Conquista("Construindo força", "Complete 10 treinos", "Treinos", "⚡", "#efecf6", 0, 10),
                new Conquista("Uma nova versão", "Complete 20 treinos", "Objetivos", "◎", "#e9eff5", 0, 20)
        ));
    }

    /** Recalcula o progresso de cada conquista a partir das sessões concluídas. */
    private void recalcularConquistas() {
        int totalConcluidos = (int) dados.sessoes.stream().filter(SessaoTreino::isConcluida).count();
        int melhorSequencia = melhorSequenciaDeDias();

        for (Conquista c : dados.conquistas) {
            int valor = c.getTitulo().equals("No ritmo") ? melhorSequencia : totalConcluidos;
            c.setAtual(Math.min(valor, c.getMeta()));
        }
    }

    private int melhorSequenciaDeDias() {  //Junta os dias: percorre dados.sessoes e guarda a data de cada sessão concluída num TreeSet. Ele não aceita repetidos, então dois treinos no mesmo dia contam como um só, e mantém as datas em ordem crescente.
        Set<LocalDate> dias = new TreeSet<>();
        for (SessaoTreino s : dados.sessoes) {
            if (s.isConcluida()) dias.add(s.getData());
        }
        int melhor = 0, atual = 0;
        LocalDate anterior = null;
        for (LocalDate d : dias) {
            atual = (anterior != null && anterior.plusDays(1).equals(d)) ? atual + 1 : 1;
            melhor = Math.max(melhor, atual);
            anterior = d;
        }
        return melhor; //Guarda o recorde: melhor = Math.max(melhor, atual) mantém o maior valor visto. Sem nenhum treino concluído, devolve
    }

    // ---------- Arquivo ----------
    private void carregar() {
        try {
            if (Files.exists(arquivo)) {
                Dados lido = gson.fromJson(Files.readString(arquivo), Dados.class);
                if (lido != null) dados = lido;  // lê os dados dentro do .json
            }
        } catch (IOException | RuntimeException e) {
            System.err.println("Não foi possível ler " + arquivo + ": " + e.getMessage());
        }
    }

    private void salvar() {
        try {
            Files.createDirectories(arquivo.getParent()); // cria a pasta .forma no diretorio de usuarios
            Files.writeString(arquivo, gson.toJson(dados)); //transforma os dados e cria o arquivo .json
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
        dados.treinos.addAll(List.of(a, b, c)); // já adiciona os treinos exemplos iniciais no arquivo .json
    }

    // ---------- Estatísticas para a Visão geral ----------
    public int totalConcluidos() {
        return (int) dados.sessoes.stream().filter(SessaoTreino::isConcluida).count(); // contador de concluidos
    }

    /** Dias seguidos treinando até hoje (se ainda não treinou hoje, conta até ontem). */
    public int sequenciaAtual() {
        Set<LocalDate> dias = new TreeSet<>();
        for (SessaoTreino s : dados.sessoes) {
            if (s.isConcluida()) dias.add(s.getData());
        }
        LocalDate d = LocalDate.now();
        if (!dias.contains(d)) d = d.minusDays(1);
        int sequencia = 0;
        while (dias.contains(d)) {
            sequencia++;
            d = d.minusDays(1);
        }
        return sequencia;
    }

    public int conquistasDesbloqueadas() {
        return (int) dados.conquistas.stream().filter(Conquista::isConcluida).count(); //se a conquista foi concluida, add um no contador
    }
}