package br.edu.ifsp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class App extends Application {

    private final Label paginaAtual = new Label();
    private final List<HBox> itensMenu = new ArrayList<>();
    private VBox content;

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(255);
        sidebar.setMinWidth(255);
        sidebar.setStyle("-fx-background-color:#ffffff; -fx-border-color:#ececE6; -fx-border-width: 0 1 0 0;");
        sidebar.setPadding(new Insets(24, 20, 20, 20));

        // Conteúdo agora dentro de um ScrollPane (a tela inteira rola)
        content = new VBox();
        content.setPadding(new Insets(28, 40, 28, 40));
        content.setStyle("-fx-background-color: #f6f6f3;");

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: #f6f6f3; -fx-background-color: transparent;");

        HBox nav = criarBarraTopo();

        BorderPane mainArea = new BorderPane();
        mainArea.setTop(nav);
        mainArea.setCenter(scroll);

        root.setLeft(sidebar);
        root.setCenter(mainArea);

        // Logo
        Label logoTexto = new Label("Forma");
        logoTexto.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label logoPonto = new Label(".");
        logoPonto.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #789366;");
        HBox logo = new HBox(logoTexto, logoPonto);

        // Menu
        Label secaoSideBar = new Label("SEU ESPAÇO");
        secaoSideBar.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");
        secaoSideBar.setPadding(new Insets(24, 0, 8, 8));

        TelaTreinos telaTreinos = new TelaTreinos();
        HBox itemTreinos = createNavItem("🏋", "Meus treinos", false, telaTreinos);

        TelaVisaoGeral telaVisaoGeral =
                new TelaVisaoGeral(() -> selecionar(itemTreinos, telaTreinos));
        HBox itemVisao = createNavItem("▦", "Visão geral", true, telaVisaoGeral);

        VBox menu = new VBox(4,
                itemVisao,
                itemTreinos,
                createNavItem("📈", "Evolução", false, new TelaEvolucao()),
                createNavItem("🏅", "Conquistas", false, new TelaConquistas()),
                createNavItem("♡", "Meu corpo", false, new TelaMeuCorpo())
        );

        // Empurra o card motivacional para o fim da sidebar
        Region espacoVertical = new Region();
        VBox.setVgrow(espacoVertical, Priority.ALWAYS);

        sidebar.getChildren().addAll(
                logo,
                secaoSideBar,
                menu,
                espacoVertical,
                criarCardMotivacional()
        );

        Rectangle2D tela = Screen.getPrimary().getVisualBounds();   // área útil, sem a barra de tarefas
        double largura = Math.min(1100, tela.getWidth() * 0.9);
        double altura = Math.min(720, tela.getHeight() * 0.9);

        Scene scene = new Scene(root, largura, altura);
        stage.setMinWidth(Math.min(900, tela.getWidth()));
        stage.setMinHeight(Math.min(560, tela.getHeight()));

        stage.setScene(scene);
        stage.setTitle("Forma");
        stage.show();
    }

    // NOVO: card motivacional da sidebar
    private VBox criarCardMotivacional() {
        Label raio = new Label("⚡");
        raio.setStyle("-fx-font-size: 20px; -fx-text-fill: #4a6b47;");
        Label titulo = new Label("Seu único limite\né você.");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label("Um pouco melhor,\ntodos os dias.");
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b918c;");

        VBox card = new VBox(10, raio, titulo, sub);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #eef1e8; -fx-background-radius: 14;");
        return card;
    }

    private void aplicarEfeito(HBox item, boolean ativo) {
        Label texto = (Label) item.getChildren().get(1);
        Label bolinha = (Label) item.getChildren().get(3);

        bolinha.setVisible(ativo);
        if (ativo) {
            item.setStyle("-fx-background-color: #e4ecdf; -fx-background-radius: 10;");
            texto.setStyle("-fx-font-weight: bold;");
        } else {
            item.setStyle("");
            texto.setStyle("");
        }
    }

    private HBox createNavItem(String icone, String texto1, boolean ativo, VBox conteudoDaAba) {
        Label iconeLabel = new Label(icone);
        Label botoes = new Label(texto1);
        Label bolinha = new Label("●");
        bolinha.setStyle("-fx-font-size: 8px; -fx-text-fill: #3d6b4f;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        HBox item = new HBox(14, iconeLabel, botoes, espaco, bolinha);
        item.setUserData(texto1);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(0, 14, 0, 14));
        item.setMinHeight(46);
        item.setCursor(Cursor.HAND);

        itensMenu.add(item);
        aplicarEfeito(item, ativo);

        item.setOnMouseClicked(e -> selecionar(item, conteudoDaAba));

        if (ativo) {
            paginaAtual.setText(texto1);
            content.getChildren().setAll(conteudoDaAba);
        }

        return item;
    }

    private void selecionar(HBox item, VBox tela) {
        for (HBox i : itensMenu) {
            aplicarEfeito(i, i == item);
        }

        // Relê o .json toda vez que a aba é aberta, para o progresso aparecer atualizado
        if (tela instanceof TelaVisaoGeral) {
            ((TelaVisaoGeral) tela).atualizar();
        } else if (tela instanceof TelaConquistas) {
            ((TelaConquistas) tela).atualizar();
        }

        paginaAtual.setText((String) item.getUserData());
        content.getChildren().setAll(tela);
    }

    private HBox criarBarraTopo() {
        Label meuEspaco = new Label("Meu espaço");
        meuEspaco.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b918c;");
        Label separador = new Label("›");
        separador.setStyle("-fx-text-fill: #8b918c;");
        paginaAtual.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        HBox breadcrumb = new HBox(10, meuEspaco, separador, paginaAtual);
        breadcrumb.setAlignment(Pos.CENTER_LEFT);

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("pt-BR")));
        Label dataLabel = new Label("📅  " + data);
        dataLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b726d;");

        Label sino = new Label("🔔");
        sino.setCursor(Cursor.HAND);

        Label avatar = new Label("@");
        avatar.setMinSize(32, 32);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: #e4ecdf; -fx-background-radius: 16;"
                + "-fx-text-fill: #3d6b4f; -fx-font-weight: bold;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        HBox barra = new HBox(18, breadcrumb, espaco, dataLabel, sino, avatar);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(0, 40, 0, 40));
        barra.setPrefHeight(78);
        barra.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ececE6;"
                + "-fx-border-width: 0 0 1 0;");
        return barra;
    }

    public static void main(String[] args) {
        launch();
    }
}