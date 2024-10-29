package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
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

public class MozoController {

    @FXML
    private Button btn_salir;

    @FXML
    private Button btn_verCocina;

    @FXML
    private Button btn_verMesas;

    @FXML
    public void initialize() {
        btn_salir.setOnAction(event -> handleSalir());
        btn_verMesas.setOnAction(event -> handleVerMesas());
        btn_verCocina.setOnAction(event -> handleverCocina());
    }

    private void handleSalir() {
        SesionUsuario sesion = SesionUsuario.getInstance();
        SesionUsuarioDAO sesionDAO = new SesionUsuarioDAOImpl();
        sesionDAO.registrarCierreSesion(sesion.getIdUsuario());

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        usuarioDAO.cambiarEstadoActividad(sesion.getIdUsuario(), false);

        showAlert("Cierre de sesión", "Sesión cerrada correctamente.", AlertType.INFORMATION);
        System.exit(0);
    }

    private void handleverCocina() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pe/edu/utp/fxml/cocina-view.fxml"));
            Stage stage = (Stage) btn_verCocina.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("PEDIDOS COCINA");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de ver pedidos.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleVerMesas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pe/edu/utp/fxml/seleccion-mesas-view.fxml"));
            Stage stage = (Stage) btn_verMesas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleccionar Mesas");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de selección de mesas.", AlertType.ERROR);
        }
    }
}
