
package br.edu.ifsp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Button left = createButton("Left");
        Button right = createButton("Right");

        BorderPane root = new BorderPane();

        root.setLeft(left);
        root.setRight(right);
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
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // tamanho do butao
        button.setMinSize(100, 100);
        BorderPane.setMargin(button, new Insets(5)); // margem do butao
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }
}
