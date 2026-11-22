module br.edu.ifsp {
    requires javafx.controls;
    requires com.google.gson;

    exports br.edu.ifsp;
    exports br.edu.ifsp.Cards;
    exports br.edu.ifsp.TreinoSecao;

    opens br.edu.ifsp.TreinoSecao to com.google.gson;
}