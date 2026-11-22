package br.edu.ifsp;

import br.edu.ifsp.Cards.StatCard;
import br.edu.ifsp.Cards.TreinoCard;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TelaVisaoGeral extends VBox {
    private final Runnable aoIrParaTreinos;

    public TelaVisaoGeral(Runnable aoIrParaTreinos) {
        super(24);
        this.aoIrParaTreinos = aoIrParaTreinos;

        Label titulo = new Label("Visão geral");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        getChildren().addAll(
                criarSaudacao(),
                criarLinhaDeCards(),
                criarParteDeBaixo()
        );
    }

// Node é uma classe base de tudo que aparece na tela em JavaFX
    private Node criarSaudacao() {
        Label titulo = new Label("Vamos nessa @usuario!");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label subtitulo = new Label("Cada treino é um passo mais perto da sua melhor versão");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #1f2d24;");

        VBox saudacao = new VBox(4, titulo , subtitulo);
        return saudacao;
    }
    private Node criarLinhaDeCards() {
        HBox linha = new HBox(16,
            new StatCard("Treinos concluídos", "0", "0 esta semana"),
            new StatCard("Sequência atual", "0", "Vamos começar?"),
            new StatCard("Meta da semana", "0", "0% da meta alcançada"),
            new StatCard("Conquistas desbloqueadas", "0", "Seu esforço vale a pena")
        );
        VBox Card = new VBox(4, linha);
        return Card;
    }
    private Node criarParteDeBaixo() {
        Label secao = new Label("Seus treinos");
        secao.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Label verTodos = new Label("Ver todos →");
        verTodos.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b726d;");
        verTodos.setCursor(Cursor.HAND);
        verTodos.setOnMouseClicked(e -> aoIrParaTreinos.run());

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        HBox cabecalho = new HBox(secao, espaco, verTodos);
        cabecalho.setAlignment(Pos.CENTER_LEFT);

        TreinoCard card = new TreinoCard("A", "Costas e bíceps",
                "Foco, força e um pouco mais de você.",
                "5 exercícios  ·  55 min  ·  Hipertrofia",
                aoIrParaTreinos);

        VBox colunaTreinos = new VBox(12, cabecalho, card);
        HBox.setHgrow(colunaTreinos, Priority.ALWAYS);

        // aqui entra a "Sua constância" depois
        return new HBox(20, colunaTreinos); }
}
