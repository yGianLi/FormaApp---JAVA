package br.edu.ifsp.Cards;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import java.net.URL;
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

        setMinHeight(245);
        setPrefHeight(245);
        setStyle("-fx-background-color: #17251f; -fx-background-radius: 18;");

//  cantos arredondados também na imagem
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        clip.setArcWidth(36);
        clip.setArcHeight(36);
        setClip(clip);

//  imagem de fundo (se não achar o arquivo, fica o verde escuro)
        URL url = getClass().getResource("/img/halteres.jpg");
        if (url != null) {
            ImageView bg = new ImageView(new Image(url.toExternalForm()));
            bg.setPreserveRatio(false);
            bg.setManaged(false);
            bg.fitWidthProperty().bind(widthProperty());
            bg.fitHeightProperty().bind(heightProperty());
            getChildren().add(bg);
        }

//  escurece a esquerda para o texto ficar legível
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: linear-gradient(to right, #17251fee 30%, #17251f66 100%);");
        getChildren().add(overlay);

        getChildren().addAll(textos, badge);   // a ordem importa: imagem, overlay, textos, badge
    }
}
