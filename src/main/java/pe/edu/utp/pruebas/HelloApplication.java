package pe.edu.utp.pruebas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import pe.edu.utp.utilidades.Conexionsecu;

public class HelloApplication extends Application {
    private Conexionsecu conexion; // Variable para la conexión

    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar la conexión
        conexion = new Conexionsecu();

        try (Connection conn = conexion.conectar()) { // Usar try-with-resources
            if (conn != null) {
                System.out.println("Conexión exitosa!");

                // Cargar el archivo FXML desde el paquete
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pe/edu/utp/fxml/hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // No es necesario desconectar aquí ya que estamos usando try-with-resources
    }

    public static void main(String[] args) {
        launch();
    }
}
