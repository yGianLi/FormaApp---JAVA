package br.edu.ifsp.Cards;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StatCard extends VBox {

    public StatCard(String titulo, String valor, String unidade, String icone,
                    String corIcone, String rodapeEsq, String rodapeDir) {
        super(12);

        // Topo: título + ícone colorido no canto
        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b726d;");

        Label iconeLabel = new Label(icone);
        iconeLabel.setStyle("-fx-font-size: 14px;");
        StackPane iconBox = new StackPane(iconeLabel);
        iconBox.setMinSize(34, 34);
        iconBox.setMaxSize(34, 34);
        iconBox.setStyle("-fx-background-color: " + corIcone + "; -fx-background-radius: 10;");
        iconBox.setVisible(!icone.isEmpty());

        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);
        HBox topo = new HBox(tituloLabel, sp1, iconBox);
        topo.setAlignment(Pos.TOP_LEFT);

        // Valor + unidade ("0 dias", "0 / 5 treinos")
        Label valorLabel = new Label(valor);
        valorLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label unidadeLabel = new Label(unidade);
        unidadeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");
        HBox linhaValor = new HBox(8, valorLabel, unidadeLabel);
        linhaValor.setAlignment(Pos.BASELINE_LEFT);

        // Rodapé em 2 colunas
        Label esq = new Label(rodapeEsq);
        esq.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");
        Label dir = new Label(rodapeDir);
        dir.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");
        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);
        HBox rodape = new HBox(esq, sp2, dir);

        getChildren().addAll(topo, linhaValor, rodape);

        setPadding(new Insets(18));
        setStyle("-fx-background-color: white; -fx-background-radius: 14;"
                + "-fx-border-color: #ececE6; -fx-border-radius: 14;");

        // os 4 cards ficam com a mesma largura
        setPrefWidth(10);
        setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public StatCard comProgresso(double progresso) {
        double p = Math.max(0, Math.min(1, progresso));

        Region preenchimento = new Region();
        preenchimento.setStyle("-fx-background-color: #4a6b47; -fx-background-radius: 4;");

        HBox trilho = new HBox(preenchimento);
        trilho.setMinHeight(4);
        trilho.setMaxHeight(4);
        trilho.setStyle("-fx-background-color: #e8e8e2; -fx-background-radius: 4;");
        preenchimento.prefWidthProperty().bind(trilho.widthProperty().multiply(p));

        // o rodapé é o último filho; a barra entra logo antes dele
        getChildren().add(Math.max(0, getChildren().size() - 1), trilho);
        return this;
    }
}