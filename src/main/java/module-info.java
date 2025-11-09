module org.example.koaladventuresapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.koaladventuresapp to javafx.fxml;
    exports org.example.koaladventuresapp;
}