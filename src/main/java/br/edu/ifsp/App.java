package br.edu.ifsp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {


        BorderPane root = new BorderPane();


        BorderPane mainArea = new BorderPane();

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(255);
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
        logoPonto.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #789366;");
        HBox logo = new HBox(logoTexto, logoPonto);
        // Itens Side Bar Menu

        Label secaoSideBar = new Label("SEU ESPAÇO");
        secaoSideBar.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");
        secaoSideBar.setPadding(new Insets(24, 0, 8, 8));
        sidebar.getChildren().addAll(
                logo,
                secaoSideBar,
                createNavItem("▦", "Visão geral", true),
                createNavItem("🏋", "Meus treinos", false),
                createNavItem("📈", "Evolução", false),
                createNavItem("🏅", "Conquistas", false),
                createNavItem("♡", "Meu corpo", false)
        );


        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 640, 480);

        stage.setScene(scene);
        stage.setTitle("BorderExamples");
        stage.show();
    }

    private final List<HBox> itensMenu = new ArrayList<>();

    private void aplicarEfeito( HBox item, boolean ativo){
        Label texto = (Label) item.getChildren().get(1);
        Label bolinha =  (Label) item.getChildren().get(3);

        bolinha.setVisible(ativo);
            if (ativo) {
                item.setStyle("-fx-background-color: #e4ecdf; -fx-background-radius: 10;");
                texto.setStyle("-fx-font-weight: bold;");
            } else {
                item.setStyle("");
                texto.setStyle("");
            }
    }
    private HBox createNavItem(String icone, String texto1, boolean ativo){
        Label iconeLabel = new Label(icone);          // o ícone
        Label botoes = new Label(texto1);             // o texto
        Label bolinha = new Label("●");               // a bolinha
        bolinha.setStyle("-fx-font-size: 8px; -fx-text-fill: #3d6b4f;");

        Region espaco = new Region();                 // empurra a bolinha p/ a direita
        HBox.setHgrow(espaco, Priority.ALWAYS);

        HBox item = new HBox(14, iconeLabel, botoes, espaco, bolinha);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(0, 14, 0, 14));
        item.setMinHeight(46);

        itensMenu.add(item);
        aplicarEfeito(item, ativo);

        item.setOnMouseClicked(event -> {
            for (HBox itemBox : itensMenu) {
                aplicarEfeito(itemBox, itemBox == item);
            }
        });
        return item;

    }

    public static void main(String[] args) {
        launch();

    }

}