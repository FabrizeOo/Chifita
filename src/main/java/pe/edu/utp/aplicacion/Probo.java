package pe.edu.utp.aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pe.edu.utp.pruebas.HelloApplication;

import java.io.IOException;

public class Probo extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Asegúrate de que la ruta del archivo FXML sea correcta
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pe/edu/utp/fxml/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
