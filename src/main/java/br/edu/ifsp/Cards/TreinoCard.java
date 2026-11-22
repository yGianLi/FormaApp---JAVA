package br.edu.ifsp.Cards;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// StackPane deixa empilhar os filhos um em cima do outro, no msm espaço
public class TreinoCard extends StackPane {

    public TreinoCard(String letra, String nome, String descricao, String detalhes, Runnable aoComecar) {

        Label pronto = new Label("PRONTO PARA O PRÓXIMO?");
        pronto.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #a9b8ae;");

        Label titulo = new Label(nome);
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label desc = new Label(descricao);
        desc.setStyle("-fx-font-size: 12px; -fx-text-fill: #c9d4cc;");

        Label info = new Label(detalhes);
        info.setStyle("-fx-font-size: 11px; -fx-text-fill: #a9b8ae;");

        Button botao = new Button("Começar treino  →");
        botao.setStyle("-fx-background-color: #dfe9d3; -fx-text-fill: #1f2d24;"
                + "-fx-font-weight: bold; -fx-background-radius: 8;"
                + "-fx-padding: 10 18 10 18; -fx-cursor: hand;");
        botao.setOnAction(e -> aoComecar.run());   // avisa quem criou o card

        VBox textos = new VBox(8, pronto, titulo, desc, info, botao);
        textos.setAlignment(Pos.CENTER_LEFT);
        textos.setPadding(new Insets(28));

        Label badge = new Label("TREINO " + letra);
        badge.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-border-color: #5c6d62; -fx-border-radius: 6; -fx-padding: 4 8 4 8;");
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        StackPane.setMargin(badge, new Insets(16));

        setMinHeight(184);
        setStyle("-fx-background-color: #14231c; -fx-background-radius: 16;");
        getChildren().addAll(textos, badge);
    }
}
