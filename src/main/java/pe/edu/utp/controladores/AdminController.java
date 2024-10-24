package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class AdminController {

    @FXML
    private Button empleadosButton;
    @FXML
    private Button btn_salir;

    @FXML
    private void handleEmpleados() {
        try {
            // Cargar el FXML de control-empleado-view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/control-empleado-view.fxml"));

            Parent root = loader.load();

            // Crear una nueva escena y abrirla
            Stage stage = (Stage) empleadosButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Control de Empleados");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}
