package br.edu.ifsp;

import br.edu.ifsp.TreinoSecao.ExercicioRealizado;
import br.edu.ifsp.TreinoSecao.RepositorioTreinos;
import br.edu.ifsp.TreinoSecao.SessaoTreino;
import br.edu.ifsp.TreinoSecao.Treino;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class TelaExecucaoTreino extends VBox {

    private static final String CARD =
            "-fx-background-color: white; -fx-background-radius: 14;"
                    + "-fx-border-color: #ececE6; -fx-border-radius: 14;";
    private static final String CAMPO =
            "-fx-background-color: #f6f6f3; -fx-background-radius: 8; -fx-border-color: #ececE6;"
                    + "-fx-border-radius: 8; -fx-padding: 8 10 8 10;";

    private final Treino treino;
    private final SessaoTreino sessao;
    private final Runnable aoSair;
    private final Label progresso = new Label();

    public TelaExecucaoTreino(Treino treino, Runnable aoSair) {
        super(24);
        this.treino = treino;
        this.aoSair = aoSair;
        this.sessao = SessaoTreino.iniciar(treino);
        mostrarExecucao();
    }

    // ---------------- Durante o treino ----------------
    private void mostrarExecucao() {
        Label voltar = new Label("← Voltar");
        voltar.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b726d;");
        voltar.setCursor(Cursor.HAND);
        voltar.setOnMouseClicked(e -> aoSair.run());   // sai sem salvar nada

        Label eyebrow = new Label("TREINO EM ANDAMENTO");
        eyebrow.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b726d;");
        Label titulo = new Label(treino.getNome());
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        progresso.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");
        atualizarProgresso();

        // Cabeçalho das colunas
        Label hEx = cabecalho("EXERCÍCIO");
        Label hCarga = cabecalho("CARGA (KG)");
        Label hReps = cabecalho("REPS");
        hCarga.setPrefWidth(100);
        hReps.setPrefWidth(100);
        HBox colunas = new HBox(14, hEx, espaco(), hCarga, hReps);
        colunas.setPadding(new Insets(0, 0, 8, 38));

        VBox linhas = new VBox();
        for (ExercicioRealizado ex : sessao.getExercicios()) {
            linhas.getChildren().add(criarLinha(ex));
        }

        VBox card = new VBox(6, colunas, linhas);
        card.setPadding(new Insets(24));
        card.setStyle(CARD);

        Button concluir = new Button("Concluir treino  ✓");
        concluir.setCursor(Cursor.HAND);
        concluir.setStyle("-fx-background-color: #3d6b4f; -fx-text-fill: white; -fx-font-weight: bold;"
                + "-fx-background-radius: 8; -fx-padding: 14 26 14 26;");
        concluir.setOnAction(e -> tentarConcluir());

        getChildren().setAll(voltar, new VBox(6, eyebrow, titulo, progresso), card, concluir);
    }

    private Node criarLinha(ExercicioRealizado ex) {
        CheckBox tick = new CheckBox();

        Label nome = new Label(ex.getNome());
        nome.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        TextField carga = new TextField();
        carga.setPromptText("kg");
        carga.setPrefWidth(100);
        carga.setStyle(CAMPO);

        TextField reps = new TextField();
        reps.setPromptText("reps");
        reps.setPrefWidth(100);
        reps.setStyle(CAMPO);

        tick.selectedProperty().addListener((o, antes, marcado) -> {
            ex.setConcluido(marcado);
            nome.setStyle(marcado
                    ? "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #8b918c;"
                    : "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
            atualizarProgresso();
        });
        carga.textProperty().addListener((o, a, texto) -> ex.setCarga(lerNumero(texto)));
        reps.textProperty().addListener((o, a, texto) -> ex.setRepeticoes((int) lerNumero(texto)));

        HBox linha = new HBox(14, tick, nome, espaco(), carga, reps);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setPadding(new Insets(12, 0, 12, 0));
        linha.setStyle("-fx-border-color: #ececE6; -fx-border-width: 1 0 0 0;");
        return linha;
    }

    private void atualizarProgresso() {
        long feitos = sessao.getExercicios().stream().filter(ExercicioRealizado::isConcluido).count();
        progresso.setText(feitos + " de " + sessao.getExercicios().size() + " exercícios concluídos");
    }

    private void tentarConcluir() {
        if (!sessao.todosConcluidos()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Marque todos os exercícios antes de concluir o treino.");
            alerta.setHeaderText(null);
            alerta.showAndWait();
            return;
        }
        sessao.concluir();
        RepositorioTreinos.get().salvarSessao(sessao);   // grava no .json
        mostrarConclusao();
    }

    // ---------------- Treino concluído ----------------
    private void mostrarConclusao() {
        Label emoji = new Label("🎉");
        emoji.setStyle("-fx-font-size: 40px;");
        Label titulo = new Label("Treino de hoje concluído!");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label(treino.getNome() + " · " + sessao.getData());
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");

        VBox resumo = new VBox(10);
        for (ExercicioRealizado ex : sessao.getExercicios()) {
            Label l = new Label("✓  " + ex.getNome() + "   —   " + formatarCarga(ex.getCarga())
                    + " × " + ex.getRepeticoes() + " reps");
            l.setStyle("-fx-font-size: 13px; -fx-text-fill: #1f2d24;");
            resumo.getChildren().add(l);
        }
        resumo.setPadding(new Insets(20));
        resumo.setStyle("-fx-background-color: #f2f4ef; -fx-background-radius: 12;");

        Button voltar = new Button("Voltar aos treinos");
        voltar.setCursor(Cursor.HAND);
        voltar.setStyle("-fx-background-color: #3d6b4f; -fx-text-fill: white; -fx-font-weight: bold;"
                + "-fx-background-radius: 8; -fx-padding: 14 26 14 26;");
        voltar.setOnAction(e -> aoSair.run());

        VBox card = new VBox(16, emoji, titulo, sub, resumo, voltar);
        card.setPadding(new Insets(32));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(CARD);

        getChildren().setAll(card);
    }

    // ---------------- Auxiliares ----------------
    private static double lerNumero(String texto) {
        try {
            return Double.parseDouble(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String formatarCarga(double kg) {
        return (kg == Math.floor(kg) ? String.valueOf((int) kg) : String.valueOf(kg)) + " kg";
    }

    private static Label cabecalho(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");
        return l;
    }

    private static Region espaco() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }
}