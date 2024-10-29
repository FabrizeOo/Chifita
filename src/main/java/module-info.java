module pe.edu.utp.aplicacion {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens pe.edu.utp.aplicacion to javafx.fxml; // Abre el paquete principal
    opens pe.edu.utp.controladores to javafx.fxml; // Abre el paquete de controladores
    opens pe.edu.utp.fxml to javafx.fxml; // Abre el paquete de FXML
    opens pe.edu.utp.modelos to javafx.base; // Abre el paquete de modelos para JavaFX

    exports pe.edu.utp.aplicacion;
    exports pe.edu.utp.controladores;
    exports pe.edu.utp.pruebas;
    opens pe.edu.utp.pruebas to javafx.fxml; // Asegúrate de exportar el paquete de controladores
}
