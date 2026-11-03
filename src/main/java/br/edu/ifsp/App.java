package br.edu.ifsp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();

        Button top = createButton("Top");
        Button bottom = createButton("Bottom");
        Button center = createButton("Center");
        Button left = createButton("Left");
        Button right = createButton("Right");

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setTop(bottom);
        root.setTop(center);
        root.setTop(left);
        root.setTop(right);

    }

    public static void main(String[] args) {
        launch();
    }

}
private Button createButton(String str){
    Button button = new Button(str);
    button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    return null;
}