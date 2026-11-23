package br.edu.ifsp;

import br.edu.ifsp.TreinoSecao.RepositorioTreinos;
import br.edu.ifsp.model.Conquista;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class TelaConquistas extends VBox {

    private List<Conquista> conquistas = RepositorioTreinos.get().getConquistas();
    private final List<String> filtros = List.of("Todas", "Treinos", "Constância", "Objetivos");

    private final HBox barraFiltros = new HBox(10);
    private final GridPane grade = new GridPane();
    private String filtroAtual = "Todas";

    public TelaConquistas() {
        super(24);
        grade.setHgap(16);
        grade.setVgap(16);
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);                       // duas colunas de 50%
        grade.getColumnConstraints().addAll(col, col);

        getChildren().addAll(criarCabecalho(), criarObjetivo(), criarCartaoConquistas());
        atualizar();
    }

    /** Relê as conquistas do repositório e redesenha a tela. */
    public void atualizar() {
        conquistas = RepositorioTreinos.get().getConquistas();

        barraFiltros.getChildren().clear();
        for (String f : filtros) {
            barraFiltros.getChildren().add(criarChip(f));
        }

        grade.getChildren().clear();
        int i = 0;
        for (Conquista c : conquistas) {
            if (filtroAtual.equals("Todas") || c.getCategoria().equals(filtroAtual)) {
                grade.add(criarCartaoConquista(c), i % 2, i / 2);   // coluna, linha
                i++;
            }
        }
    }

    private Node criarCabecalho() {
        Label tag = new Label("SUA JORNADA, SUA EVOLUÇÃO");
        tag.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");
        Label titulo = new Label("O esforço vira conquista.");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label("Celebre cada passo e encontre motivação para o próximo.");
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b918c;");
        return new VBox(6, tag, titulo, sub);      // sem botão, só os textos
    }

    private Node criarObjetivo() {
        Label icone = new Label("◎");
        icone.setMinSize(64, 64);
        icone.setAlignment(Pos.CENTER);
        icone.setStyle("-fx-font-size: 24px; -fx-text-fill: #6b8a5c;"
                + "-fx-background-color: #d9e5cf; -fx-background-radius: 14;");

        Label tag = new Label("SEU OBJETIVO");
        tag.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");
        Label meta = new Label("Ganhar massa muscular");
        meta.setStyle("-fx-font-size: 22px; -fx-text-fill: #1f2d24;");
        Label frase = new Label("Consistência transforma intenção em resultado.");
        frase.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b8a5c;");
        VBox textos = new VBox(4, tag, meta, frase);

        Button editar = new Button("Editar objetivo  ⚙");
        editar.setStyle("-fx-background-color: #f4f8ef; -fx-text-fill: #3d6b4f;"
                + "-fx-border-color: #c9d4b8; -fx-border-radius: 8;"
                + "-fx-background-radius: 8; -fx-cursor: hand;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        HBox card = new HBox(20, icone, textos, espaco, editar);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(32));
        card.setStyle("-fx-background-color: #e8f0e2; -fx-background-radius: 16;");
        return card;
    }

    private Node criarCartaoConquistas() {
        Label titulo = new Label("Suas conquistas");
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label("Cada objetivo alcançado merece ser celebrado.");
        sub.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");

        VBox cartao = new VBox(16, new VBox(4, titulo, sub), barraFiltros, grade);
        cartao.setPadding(new Insets(28));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 16;"
                + "-fx-border-color: #ececE6; -fx-border-radius: 16;");
        return cartao;
    }

    private Node criarChip(String nome) {
        boolean ativo = nome.equals(filtroAtual);
        Label chip = new Label(nome);
        chip.setCursor(Cursor.HAND);
        chip.setStyle("-fx-font-size: 10px; -fx-padding: 8 16 8 16; -fx-background-radius: 8;"
                + "-fx-background-color: " + (ativo ? "#e4ecdf" : "#f4f5ef") + ";"
                + "-fx-text-fill: " + (ativo ? "#3d6b4f" : "#8b918c") + ";");
        chip.setOnMouseClicked(e -> {
            filtroAtual = nome;
            atualizar();
        });
        return chip;
    }

    private Node criarCartaoConquista(Conquista c) {
        Polygon hexagono = new Polygon(28, 0, 56, 16, 56, 48, 28, 64, 0, 48, 0, 16);
        hexagono.setFill(Color.web(c.getCor()));
        Label icone = new Label(c.getIcone());
        icone.setStyle("-fx-font-size: 20px;");
        StackPane emblema = new StackPane(hexagono, icone);

        Label titulo = new Label(c.getTitulo());
        titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label desc = new Label(c.getDescricao());
        desc.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");

        // trilha (fundo cinza) com a parte verde por dentro
        StackPane trilha = new StackPane();
        trilha.setAlignment(Pos.CENTER_LEFT);
        trilha.setMinHeight(4);
        trilha.setPrefHeight(4);
        trilha.setMaxHeight(4);
        trilha.setStyle("-fx-background-color: #ececE6; -fx-background-radius: 2;");

        double progresso = (double) c.getAtual() / c.getMeta();
        Region preenchimento = new Region();
        preenchimento.setStyle("-fx-background-color: #6b8a5c; -fx-background-radius: 2;");
        preenchimento.maxWidthProperty().bind(trilha.widthProperty().multiply(progresso));
        trilha.getChildren().add(preenchimento);

        Label contador = new Label(c.getAtual() + "/" + c.getMeta());
        contador.setStyle("-fx-font-size: 9px; -fx-text-fill: "
                + (c.isConcluida() ? "#3d6b4f; -fx-font-weight: bold;" : "#8b918c;"));

        HBox linhaProgresso = new HBox(12, trilha, contador);
        linhaProgresso.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(trilha, Priority.ALWAYS);   // a trilha ocupa o espaço que sobrar

        VBox textos = new VBox(6, titulo, desc, linhaProgresso);
        HBox.setHgrow(textos, Priority.ALWAYS);

        HBox cartao = new HBox(20, emblema, textos);
        cartao.setAlignment(Pos.CENTER_LEFT);
        cartao.setPadding(new Insets(20));
        cartao.setStyle("-fx-background-color: white; -fx-background-radius: 12;"
                + "-fx-border-color: #ececE6; -fx-border-radius: 12;");
        return cartao;
    }
}