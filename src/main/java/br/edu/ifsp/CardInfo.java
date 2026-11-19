package br.edu.ifsp;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CardInfo extends VBox {

    public CardInfo(String titulo, String valor) {
        super(6);

        Label t = new Label(titulo);
        t.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");

        Label v = new Label(valor);
        v.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        setPadding(new Insets(20));
        setPrefWidth(180);
        setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        getChildren().addAll(t, v);
    }
}