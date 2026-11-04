package br.edu.ifsp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Button left = createButton("Left");
        Button center = createButton("Center");
        Button top = createButton("Top");

        BorderPane root = new BorderPane();

        VBox div = new VBox(10);

        Label titulo = new Label("Título da Div");
        Label texto = new Label("Este é um texto dentro do container.");

        div.getChildren().addAll(titulo, texto);

        // Adicionando os elementos ao BorderPane
        root.setLeft(left);
        root.setCenter(div);
        root.setTop(top);
        root.setRight(center);

        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 640, 480);

        stage.setScene(scene);
        stage.setTitle("BorderExamples");
        stage.show();
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