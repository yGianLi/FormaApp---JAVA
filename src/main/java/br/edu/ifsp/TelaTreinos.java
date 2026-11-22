package br.edu.ifsp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import br.edu.ifsp.model.Exercicio;
import br.edu.ifsp.model.Treino;

import java.util.List;


public class TelaTreinos extends VBox {
    private final List<Treino> treinos = List.of(
            new Treino("A", "Superior", "Costas e bíceps", 55, List.of(
                    new Exercicio("Puxada frontal"),
                    new Exercicio("Remada baixa"),
                    new Exercicio("Remada Unilateral"),
                    new Exercicio("Rosca Direta"),
                    new Exercicio("Rosca Martelo")
            )),
            new Treino("B", "Inferior", "Pernas e glúteos", 60, List.of(
                    new Exercicio("Leg press"),
                    new Exercicio("Agachamento"),
                    new Exercicio("Cadeira extensora"),
                    new Exercicio("Mesa flexora")
            )),
            new Treino("C", "Superior", "Peito e tríceps", 50, List.of(
                    new Exercicio("Supino reto"),
                    new Exercicio("Supino inclinado"),
                    new Exercicio("Crucifixo"),
                    new Exercicio("Tríceps na polia")
            ))
    );

    private final VBox listaEsquerda = new VBox(12);
    private final VBox painelDireito = new VBox(16);
    private Treino selecionado = treinos.get(0);

    public TelaTreinos() {
        super(24);
        getChildren().addAll(criarCabecalho(), criarCorpo());
        atualizar();
    }

    private void atualizar() {
        listaEsquerda.getChildren().clear();
        for (Treino t : treinos) {
            listaEsquerda.getChildren().add(criarItemLista(t));
        }
        listaEsquerda.getChildren().add(criarBotaoTracejado("Adicione seu treino"));

        painelDireito.getChildren().setAll(criarPainel(selecionado));
    }

    private Node criarItemLista(Treino t) {
        Label tag = new Label("Treino " + t.getLetra() + " · " + t.getGrupo());
        tag.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #6f9a86;");

        Label nome = new Label(t.getNome());
        nome.setStyle("-fx-font-size: 15px; -fx-text-fill: #1f2d24;");

        Label info = new Label(t.getExercicios().size() + " exercícios · " + t.getMinutos() + " min");
        info.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");

        VBox textos = new VBox(4, tag, nome, info);
        HBox.setHgrow(textos, Priority.ALWAYS);

        Label seta = new Label("›");
        seta.setStyle("-fx-font-size: 18px; -fx-text-fill: #8b918c;");

        HBox item = new HBox(textos, seta);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(18));
        item.setCursor(Cursor.HAND);

        boolean ativo = (t == selecionado);
        String fundo = ativo ? "#e8f0e2" : "white";
        String borda = ativo ? "#8fae7e" : "#ececE6";
        item.setStyle("-fx-background-color: " + fundo + "; -fx-background-radius: 12;"
                + "-fx-border-color: " + borda + "; -fx-border-radius: 12;");

        item.setOnMouseClicked(e -> {
            selecionado = t;
            atualizar();
        });
        return item;
    }

    private Node criarPainel(Treino t) {
        Label tag = new Label("TREINO " + t.getLetra());
        tag.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");

        Label titulo = new Label(t.getNome());
        titulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Label sub = new Label("Seu plano, exercício por exercício.");
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b918c;");

        VBox textos = new VBox(6, tag, titulo, sub);

        Button comecar = new Button("Começar treino  →");
        comecar.setStyle("-fx-background-color: #3d6b4f; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-background-radius: 8;"
                + "-fx-padding: 10 18 10 18; -fx-cursor: hand;");

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        HBox cabecalho = new HBox(textos, espaco, comecar);
        cabecalho.setAlignment(Pos.TOP_LEFT);

        VBox linhas = new VBox();
        for (int i = 0; i < t.getExercicios().size(); i++) {
            linhas.getChildren().add(criarLinhaExercicio(i + 1, t.getExercicios().get(i)));
        }

        VBox painel = new VBox(20, cabecalho, linhas, criarBotaoTracejado("Adicione seu exercício"));
        painel.setPadding(new Insets(28));
        painel.setStyle("-fx-background-color: white; -fx-background-radius: 16;"
                + "-fx-border-color: #ececE6; -fx-border-radius: 16;");
        return painel;
    }
    private Node criarLinhaExercicio(int numero, Exercicio e) {
        Label num = new Label(String.format("%02d", numero));
        num.setMinSize(32, 32);
        num.setAlignment(Pos.CENTER);
        num.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;"
                + "-fx-background-color: #f1f3ec; -fx-background-radius: 6;");

        Label nome = new Label(e.getNome());
        nome.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Label remover = new Label("✕");
        remover.setStyle("-fx-text-fill: #b5bab6;");
        remover.setCursor(Cursor.HAND);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        HBox linha = new HBox(14, num, nome, espaco, remover);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setPadding(new Insets(14, 0, 14, 0));
        linha.setStyle("-fx-border-color: transparent transparent #ececE6 transparent;");
        return linha;
    }

    private Node criarBotaoTracejado(String texto) {
        Label l = new Label("+   " + texto);
        l.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b8a5c;");

        HBox caixa = new HBox(l);
        caixa.setAlignment(Pos.CENTER);
        caixa.setPadding(new Insets(16));
        caixa.setCursor(Cursor.HAND);
        caixa.setStyle("-fx-border-color: #c9d4b8; -fx-border-style: dashed;"
                + "-fx-border-radius: 10;");
        return caixa;
    }
private Node criarCabecalho() {
    Label tag = new Label("SUA JORNADA, SUA EVOLUÇÃO");
    tag.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #8b918c;");

    Label titulo = new Label("Feito para o seu ritmo.");
    titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

    Label sub = new Label("Organize seus treinos. Construa força, um exercício de cada vez.");
    sub.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b918c;");

    VBox textos = new VBox(6, tag, titulo, sub);

    Button novoTreino = new Button("+   Novo treino");
    novoTreino.setStyle("-fx-background-color: #3d6b4f; -fx-text-fill: white;"
            + "-fx-font-weight: bold; -fx-background-radius: 8;"
            + "-fx-padding: 10 18 10 18; -fx-cursor: hand;");

    Region espaco = new Region();
    HBox.setHgrow(espaco, Priority.ALWAYS);

    HBox cabecalho = new HBox(textos, espaco, novoTreino);
    cabecalho.setAlignment(Pos.TOP_LEFT);
    return cabecalho;
}
private Node criarCorpo() {
    listaEsquerda.setPrefWidth(230);   // largura desejada
    listaEsquerda.setMinWidth(230);    // não deixa encolher
    listaEsquerda.setMaxWidth(230);    // não deixa crescer

    HBox.setHgrow(painelDireito, Priority.ALWAYS);   // pega todo o espaço que sobrar

    HBox corpo = new HBox(20, listaEsquerda, painelDireito);
    corpo.setAlignment(Pos.TOP_LEFT);
    return corpo;
}
}
