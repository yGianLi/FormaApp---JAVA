package br.edu.ifsp.Cards;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatCard extends VBox {

    public StatCard(String titulo, String valor, String subtitulo) {
        super(6); // espacamento entre os filhos do VBox

        Label tituloLabel = new Label(titulo); // titulo da caixa
        tituloLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b726d;");

        Label valorLabel = new Label(valor); // numero da sequencia ou valor
        valorLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Label subtituloLabel = new Label(subtitulo); // subtitulo da caixa
        subtituloLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");

        getChildren().addAll(tituloLabel, valorLabel, subtituloLabel);

        setPadding(new Insets(20));
        setStyle("-fx-background-color: white; -fx-background-radius: 12;"
                + "-fx-border-color: #ececE6; -fx-border-radius: 12;");
    }
}