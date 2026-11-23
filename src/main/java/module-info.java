module br.edu.ifsp {
    requires javafx.controls;
    requires com.google.gson;

    exports br.edu.ifsp;

    opens br.edu.ifsp.TreinoSecao to com.google.gson;
    opens br.edu.ifsp.model to com.google.gson;
}