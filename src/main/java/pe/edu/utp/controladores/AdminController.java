package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import pe.edu.utp.dao.SesionUsuarioDAO;
import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.dao.impl.SesionUsuarioDAOImpl;
import pe.edu.utp.dao.impl.UsuarioDAOImpl;
import pe.edu.utp.modelos.SesionUsuario;

public class AdminController {

    @FXML
    private Button empleadosButton;
    @FXML
    private Button btn_salir1;

    @FXML
    public void initialize() {
        btn_salir1.setOnAction(event -> handleSalir());
    }
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
    private void handleSalir() {
        SesionUsuario sesion = SesionUsuario.getInstance();
        SesionUsuarioDAO sesionDAO = new SesionUsuarioDAOImpl();
        sesionDAO.registrarCierreSesion(sesion.getIdUsuario());

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        usuarioDAO.cambiarEstadoActividad(sesion.getIdUsuario(), false);

        showAlert("Cierre de sesión", "Sesión cerrada correctamente.", Alert.AlertType.INFORMATION);
        System.exit(0);
    }
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
