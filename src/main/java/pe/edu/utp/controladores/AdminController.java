package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AdminController {

    @FXML
    private Button empleadosButton;
    @FXML
    private Button btn_salir;

    @FXML
    private void handleEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/control-empleado-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) empleadosButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Control de Empleados");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/inventario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inventario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/ventas.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel de Ventas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
