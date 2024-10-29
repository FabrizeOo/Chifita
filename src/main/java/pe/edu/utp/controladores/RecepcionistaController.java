package pe.edu.utp.controladores;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class RecepcionistaController {

    @FXML
    private Pane mainPane;
    @FXML
    private ImageView logoImageView; // Si deseas usarlo en el controlador

    @FXML
    public void initialize() {
        // Inicialización si es necesaria
    }

    @FXML
    public void registrarClientes() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pe/edu/utp/fxml/control-clientes-view.fxml"));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Clientes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarMensajeCaja() {
        mostrarMensaje("Función aún no implementada");
    }

    @FXML
    public void mostrarMensajeReserva() {
        try {
            // Cargar la vista del panel principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/reserva-view.fxml"));
            Parent root = loader.load();
            // Obtener la escena actual
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de errores
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void abrirVentanaFidelizacion() {
        try {
            // Cargar el archivo FXML de la interfaz de fidelización de clientes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/fidelización-de-clientes.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana (Stage) para la interfaz de fidelización
            Stage stage = new Stage();
            stage.setTitle("Fidelización de Clientes");
            stage.initModality(Modality.APPLICATION_MODAL);  // Bloquear interacción con ventana principal
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar la interfaz", "No se pudo abrir la interfaz de fidelización de clientes.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
