package br.edu.ifsp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        BorderPane mainArea = new BorderPane();

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(120);
        sidebar.setStyle("-fx-background-color:#ffffff; -fx-border-color:#ececE6; -fx-border-width: 0 1 0 0;");
        sidebar.setPadding(new Insets(5));


        HBox nav = new HBox();
        nav.setPrefHeight(78);
        nav.setStyle("-fx-background-color:#ffffff;");

        VBox content = new VBox();
        content.setPadding(new Insets(40));
        content.setStyle("-fx-background-color: #f6f6f3;");

        // Adicionando os elementos ao BorderPane
        mainArea.setCenter(content);
        mainArea.setTop(nav);
        root.setLeft(sidebar);
        root.setCenter(mainArea);

        // Criação da Logo
        Label logoTexto = new Label("forma");
        logoTexto.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label logoPonto =new Label(".");
        logoPonto.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        HBox logo = new HBox(logoTexto, logoPonto);
        // Itens Side Bar Menu
        sidebar.getChildren().addAll(
                logo,
                createNavItem("Visão Geral"),
                createNavItem("Meus treinos"),
                createNavItem("Evolução"),
                createNavItem("Conquista"),
                createNavItem("Meu corpo")
        );






        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 640, 480);

        stage.setScene(scene);
        stage.setTitle("BorderExamples");
        stage.show();
    }

    private HBox createNavItem(String texto1){
        Label botoes = new Label(texto1);
        HBox item = new HBox (botoes);
        item.setPadding(new Insets(12));
        return item;
    }

    public static void main(String[] args) {
        launch();
    }

    private Button createButton(String str) {
        Button button = new Button(str);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setMinSize(100, 100);
        BorderPane.setMargin(button, new Insets(5));
        BorderPane.setAlignment(button, Pos.CENTER);

        return button;
    }
}