package br.edu.ifsp.TreinoSecao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Uma execução de um treino em um dia. É isso que vai para o histórico no .json. */
public class SessaoTreino {
    private String id = UUID.randomUUID().toString();
    private String treinoId;
    private String treinoNome;
    private String data = LocalDate.now().toString();   // texto "2026-11-22" (o Gson lida bem com String)
    private boolean concluida;
    private List<ExercicioRealizado> exercicios = new ArrayList<>();

    /** Cria uma sessão nova, com um item para cada exercício do treino. */
    public static SessaoTreino iniciar(Treino treino) {
        SessaoTreino s = new SessaoTreino();
        s.treinoId = treino.getId();
        s.treinoNome = treino.getNome();
        for (String nome : treino.getExercicios()) {
            s.exercicios.add(new ExercicioRealizado(nome));
        }
        return s;
    }

    public boolean todosConcluidos() {
        return !exercicios.isEmpty() && exercicios.stream().allMatch(ExercicioRealizado::isConcluido);
    }

    /** Chame quando o usuário terminar: só conclui se todos os exercícios estiverem marcados. */
    public boolean concluir() {
        concluida = todosConcluidos();
        return concluida;
    }

    public String getId() { return id; }
    public String getTreinoId() { return treinoId; }
    public String getTreinoNome() { return treinoNome; }
    public LocalDate getData() { return LocalDate.parse(data); }
    public boolean isConcluida() { return concluida; }
    public List<ExercicioRealizado> getExercicios() { return exercicios; }
}