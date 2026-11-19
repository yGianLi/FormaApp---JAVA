package br.edu.ifsp;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TelaTreinos extends VBox {

    public TelaTreinos() {
        super(24); // espaçamento entre os filhos (igual new VBox(24))

        Label titulo = new Label("Meus treinos");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Button novoTreino = new Button("+ Novo treino");

        HBox cards = new HBox(16,
                new CardInfo("Treinos na semana", "4"),
                new CardInfo("Tempo total", "3h 20min")
        );

        VBox painel = new VBox(8,
                new Label("Treino A - Peito e tríceps"),
                new Label("Treino B - Costas e bíceps")
        );
        painel.setPadding(new Insets(20));
        painel.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        getChildren().addAll(titulo, novoTreino, cards, painel);
    }
}