package br.edu.ifsp;
import br.edu.ifsp.TreinoSecao.RepositorioTreinos;
import br.edu.ifsp.TreinoSecao.Treino;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class TelaTreinos extends VBox {

    private static final String CARD =
            "-fx-background-color: white; -fx-background-radius: 14;"
                    + "-fx-border-color: #ececE6; -fx-border-radius: 14;";
    private static final String CARD_SELECIONADO =
            "-fx-background-color: #e4ecdf; -fx-background-radius: 14;"
                    + "-fx-border-color: #9db87f; -fx-border-radius: 14; -fx-border-width: 1.5;";
    private static final String TRACEJADO =
            "-fx-border-color: #c9d4c3; -fx-border-style: dashed; -fx-border-radius: 14;"
                    + "-fx-background-radius: 14;";

    private final RepositorioTreinos repo = RepositorioTreinos.get();
    private Treino selecionado;

    public TelaTreinos() {
        super(24);
        mostrarLista();
    }

    /** Monta (ou remonta) a tela de listagem. Chame depois de qualquer alteração. */
    private void mostrarLista() {
        List<Treino> treinos = repo.getTreinos();
        if (selecionado == null || !treinos.contains(selecionado)) {
            selecionado = treinos.isEmpty() ? null : treinos.get(0);
        }
        getChildren().setAll(criarCabecalho(), criarCorpo());
    }

    // ---------------- Cabeçalho ----------------
    private Node criarCabecalho() {
        Label eyebrow = new Label("SUA JORNADA, SUA EVOLUÇÃO");
        eyebrow.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b726d;");
        Label titulo = new Label("Feito para o seu ritmo.");
        titulo.setStyle("-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label("Organize seus treinos. Construa força, um exercício de cada vez.");
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");

        Button novo = botaoVerde("+  Novo treino");
        novo.setOnAction(e -> criarTreino());

        HBox cab = new HBox(new VBox(6, eyebrow, titulo, sub), espaco(), novo);
        cab.setAlignment(Pos.TOP_LEFT);
        return cab;
    }

    private Node criarCorpo() {
        Node lista = criarLista();
        Node detalhe = criarDetalhe();
        HBox corpo = new HBox(24, lista, detalhe);
        HBox.setHgrow(detalhe, Priority.ALWAYS);
        return corpo;
    }

    // ---------------- Lista da esquerda ----------------
    private Node criarLista() {
        VBox coluna = new VBox(14);
        coluna.setMinWidth(290);
        coluna.setPrefWidth(290);
        coluna.setMaxWidth(290);

        List<Treino> treinos = repo.getTreinos();
        for (int i = 0; i < treinos.size(); i++) {
            coluna.getChildren().add(criarItemTreino(treinos.get(i), (char) ('A' + i)));
        }

        Label add = new Label("+   Adicione seu treino");
        add.setStyle("-fx-font-size: 11px; -fx-text-fill: #4a6b47;");
        StackPane addBox = new StackPane(add);
        addBox.setPadding(new Insets(22));
        addBox.setStyle(TRACEJADO);
        addBox.setCursor(Cursor.HAND);
        addBox.setOnMouseClicked(e -> criarTreino());
        coluna.getChildren().add(addBox);

        return coluna;
    }

    private Node criarItemTreino(Treino t, char letra) {
        boolean ativo = t == selecionado;

        Label topo = new Label("Treino " + letra + " · " + t.getGrupo());
        topo.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #4a6b47;");
        Label nome = new Label(t.getNome());
        nome.setStyle("-fx-font-size: 15px; -fx-text-fill: #1f2d24;");
        Label meta = new Label(t.getExercicios().size() + " exercícios · " + t.getDuracaoMin() + " min");
        meta.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");

        VBox textos = new VBox(5, topo, nome, meta);
        if (repo.concluidoHoje(t.getId())) {
            Label ok = new Label("✓ Concluído hoje");
            ok.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #3d6b4f;");
            textos.getChildren().add(ok);
        }

        Label seta = new Label("›");
        seta.setStyle("-fx-font-size: 20px; -fx-text-fill: #8b918c;");

        HBox card = new HBox(textos, espaco(), seta);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle(ativo ? CARD_SELECIONADO : CARD);
        card.setCursor(Cursor.HAND);
        card.setOnMouseClicked(e -> {
            selecionado = t;
            mostrarLista();
        });
        return card;
    }

    // ---------------- Detalhe da direita ----------------
    private Node criarDetalhe() {
        VBox painel = new VBox(18);
        painel.setPadding(new Insets(28));
        painel.setStyle(CARD);

        if (selecionado == null) {
            Label vazio = new Label("Você ainda não tem treinos. Clique em \"Novo treino\" para criar o primeiro!");
            vazio.setStyle("-fx-font-size: 13px; -fx-text-fill: #8b918c;");
            painel.getChildren().add(vazio);
            return painel;
        }

        int indice = repo.getTreinos().indexOf(selecionado);
        Label tag = new Label("TREINO " + (char) ('A' + indice));
        tag.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #6b726d;");
        Label nome = new Label(selecionado.getNome());
        nome.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");
        Label sub = new Label("Seu plano, exercício por exercício.");
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: #8b918c;");

        Button comecar = botaoVerde("Começar treino  →");
        comecar.setOnAction(e -> comecarTreino());

        Label excluir = new Label("Excluir treino");
        excluir.setStyle("-fx-font-size: 10px; -fx-text-fill: #b5473a;");
        excluir.setCursor(Cursor.HAND);
        excluir.setOnMouseClicked(e -> excluirTreino());

        VBox acoes = new VBox(8, comecar, excluir);
        acoes.setAlignment(Pos.TOP_RIGHT);
        if (repo.concluidoHoje(selecionado.getId())) {
            Label ok = new Label("✓ Treino de hoje concluído");
            ok.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #3d6b4f;"
                    + "-fx-background-color: #e4ecdf; -fx-background-radius: 8; -fx-padding: 6 12;");
            acoes.getChildren().add(0, ok);
        }

        HBox cab = new HBox(new VBox(6, tag, nome, sub), espaco(), acoes);
        painel.getChildren().add(cab);

        // Exercícios
        VBox exercicios = new VBox();
        List<String> lista = selecionado.getExercicios();
        for (int i = 0; i < lista.size(); i++) {
            exercicios.getChildren().add(criarLinhaExercicio(i + 1, lista.get(i)));
        }
        painel.getChildren().add(exercicios);

        Label add = new Label("+   Adicione seu exercício");
        add.setStyle("-fx-font-size: 11px; -fx-text-fill: #4a6b47;");
        StackPane addBox = new StackPane(add);
        addBox.setPadding(new Insets(22));
        addBox.setStyle(TRACEJADO);
        addBox.setCursor(Cursor.HAND);
        addBox.setOnMouseClicked(e -> adicionarExercicio());
        painel.getChildren().add(addBox);

        return painel;
    }

    private Node criarLinhaExercicio(int numero, String nomeExercicio) {
        Label n = new Label(String.format("%02d", numero));
        n.setStyle("-fx-font-size: 10px; -fx-text-fill: #8b918c;");
        StackPane caixa = new StackPane(n);
        caixa.setMinSize(40, 40);
        caixa.setMaxSize(40, 40);
        caixa.setStyle("-fx-background-color: #f2f4ef; -fx-background-radius: 10;");

        Label nome = new Label(nomeExercicio);
        nome.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1f2d24;");

        Label remover = new Label("✕");
        remover.setStyle("-fx-font-size: 12px; -fx-text-fill: #b0b5b1;");
        remover.setCursor(Cursor.HAND);
        remover.setOnMouseClicked(e -> {
            repo.removerExercicio(selecionado, nomeExercicio);
            mostrarLista();
        });

        HBox linha = new HBox(18, caixa, nome, espaco(), remover);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setPadding(new Insets(14, 0, 14, 0));
        linha.setStyle("-fx-border-color: #ececE6; -fx-border-width: 0 0 1 0;");
        return linha;
    }

    // ---------------- Ações ----------------
    private void criarTreino() {
        Dialog<Treino> dialogo = new Dialog<>();
        dialogo.setTitle("Novo treino");
        dialogo.setHeaderText("Crie o seu treino");

        ButtonType criar = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(criar, ButtonType.CANCEL);

        TextField nome = new TextField();
        nome.setPromptText("Ex: Costas e bíceps");
        ComboBox<String> grupo = new ComboBox<>(
                FXCollections.observableArrayList("Superior", "Inferior", "Full body", "Cardio"));
        grupo.setValue("Superior");
        Spinner<Integer> duracao = new Spinner<>(10, 180, 60, 5);

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.setPadding(new Insets(16));
        form.addRow(0, new Label("Nome"), nome);
        form.addRow(1, new Label("Grupo"), grupo);
        form.addRow(2, new Label("Duração (min)"), duracao);
        dialogo.getDialogPane().setContent(form);

        Node botaoCriar = dialogo.getDialogPane().lookupButton(criar);
        botaoCriar.setDisable(true);
        nome.textProperty().addListener((o, a, b) -> botaoCriar.setDisable(b.trim().isEmpty()));

        dialogo.setResultConverter(bt -> bt == criar
                ? new Treino(nome.getText().trim(), grupo.getValue(), duracao.getValue())
                : null);

        dialogo.showAndWait().ifPresent(t -> {
            repo.adicionarTreino(t);
            selecionado = t;
            mostrarLista();
        });
    }

    private void adicionarExercicio() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Novo exercício");
        dialogo.setHeaderText("Adicionar exercício em \"" + selecionado.getNome() + "\"");
        dialogo.setContentText("Nome:");
        dialogo.showAndWait().ifPresent(texto -> {
            if (!texto.isBlank()) {
                repo.adicionarExercicio(selecionado, texto.trim());
                mostrarLista();
            }
        });
    }

    private void excluirTreino() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir o treino \"" + selecionado.getNome() + "\"?", ButtonType.OK, ButtonType.CANCEL);
        alerta.setHeaderText(null);
        alerta.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.OK) {
                repo.removerTreino(selecionado.getId());
                selecionado = null;
                mostrarLista();
            }
        });
    }

    private void comecarTreino() {
        if (selecionado.getExercicios().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Adicione pelo menos um exercício antes de começar.");
            alerta.setHeaderText(null);
            alerta.showAndWait();
            return;
        }
        // troca o conteúdo desta tela pela tela de execução; ao sair, volta para a lista
        getChildren().setAll(new TelaExecucaoTreino(selecionado, this::mostrarLista));
    }

    // ---------------- Auxiliares ----------------
    private static Button botaoVerde(String texto) {
        Button b = new Button(texto);
        b.setCursor(Cursor.HAND);
        b.setStyle("-fx-background-color: #3d6b4f; -fx-text-fill: white; -fx-font-weight: bold;"
                + "-fx-background-radius: 8; -fx-padding: 14 22 14 22;");
        return b;
    }

    private static Region espaco() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }
}