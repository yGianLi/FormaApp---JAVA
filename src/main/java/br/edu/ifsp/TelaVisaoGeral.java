package br.edu.ifsp;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TelaVisaoGeral extends VBox {

    public TelaVisaoGeral() {
        super(24);

        Label titulo = new Label("Visão geral");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        getChildren().add(titulo);

    }
}