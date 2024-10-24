package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import pe.edu.utp.utilidades.Conexionsecu; // Importa tu clase de conexión

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn_probarConexion; // Botón para probar conexión

    private Conexionsecu conexion; // Variable para la conexión

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onProbarConexionClick() {
        conexion = new Conexionsecu(); // Inicializa la conexión
        try (Connection conn = conexion.conectar()) { // Usar try-with-resources
            if (conn != null) {
                welcomeText.setText("Conexión exitosa!"); // Mensaje de éxito

                // Abrir el login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/login-view.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) btn_probarConexion.getScene().getWindow(); // Obtener la ventana actual
                stage.setScene(scene); // Cambiar la escena
                stage.setTitle("Login"); // Cambiar el título
                stage.show(); // Mostrar la nueva escena
            } else {
                welcomeText.setText("Error al conectar a la base de datos."); // Mensaje de error
            }
        } catch (IOException e) {
            welcomeText.setText("Error al cargar la vista de inicio de sesión."); // Mensaje amigable
            e.printStackTrace(); // Manejo de excepciones al cargar el FXML
        } catch (SQLException e) {
            welcomeText.setText("Error en la conexión a la base de datos."); // Mensaje amigable
            e.printStackTrace(); // Manejo de excepciones en la conexión
        }
    }
}
