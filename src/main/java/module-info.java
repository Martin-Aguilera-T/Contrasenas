module com.example.contrasenas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.contrasenas to javafx.fxml;
    exports com.example.contrasenas;
}