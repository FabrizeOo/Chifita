package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.dao.impl.UsuarioDAOImpl;
import pe.edu.utp.dao.SesionUsuarioDAO; // Asegúrate de importar el DAO
import pe.edu.utp.dao.impl.SesionUsuarioDAOImpl; // Asegúrate de importar la implementación
import pe.edu.utp.modelos.SesionUsuario;
import pe.edu.utp.modelos.Usuario;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel; // Si quieres mostrar mensajes al usuario

    // Método que maneja el evento de clic en el botón "Iniciar Sesión"
    @FXML
    protected void handleLogin() {
        String nombreUsuario = usernameField.getText();
        String contrasena = passwordField.getText();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Usuario usuario = usuarioDAO.validarInicioSesion(nombreUsuario, contrasena);

        if (usuario != null) {
            // Inicio de sesión exitoso
            SesionUsuario sesion = SesionUsuario.getInstance();
            sesion.setIdUsuario(usuario.getId_usuario());
            sesion.setNombreUsuario(usuario.getNombreUsuario());
            sesion.setCargo(usuario.getCargo());

            // Cambiar estado de actividad a true
            usuarioDAO.cambiarEstadoActividad(usuario.getId_usuario(), true);

            // Registrar inicio de sesión
            SesionUsuarioDAO sesionDAO = new SesionUsuarioDAOImpl();
            sesionDAO.registrarInicioSesion(usuario.getId_usuario());

            String cargo = usuario.getCargo();
            switch (cargo) {
                case "Administrador":
                    abrirVentana("administrador-view.fxml");
                    break;
                case "Mozo":
                    abrirVentana("mozo-view.fxml");
                    break;
                case "Recepcionista":
                    abrirVentana("recepcionista-view.fxml");
                    break;
                default:
                    showAlert("Error", "Cargo no reconocido", AlertType.ERROR);
                    break;
            }
        } else {
            // Inicio de sesión fallido
            showAlert("Error", "Usuario o contraseña incorrectos", AlertType.ERROR);
        }
    }

    private void abrirVentana(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana de inicio de sesión si es necesario
             Stage currentStage = (Stage) usernameField.getScene().getWindow();
             currentStage.close();

        } catch (Exception e) {
            showAlert("Error", "No se pudo abrir la ventana: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
