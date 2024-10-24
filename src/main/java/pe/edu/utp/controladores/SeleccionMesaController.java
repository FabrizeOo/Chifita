package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pe.edu.utp.dao.MesaDAO;
import pe.edu.utp.dao.impl.MesaDAOImpl;
import pe.edu.utp.modelos.Mesa;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;

public class SeleccionMesaController {

    @FXML
    private Button mesa1Button;
    @FXML
    private Button mesa2Button;
    @FXML
    private Button mesa3Button;
    @FXML
    private Button mesa4Button;
    @FXML
    private Button mesa5Button;
    @FXML
    private Button mesa6Button;
    @FXML
    private Button mesa7Button;
    @FXML
    private Button mesa8Button;

    @FXML
    private Button volverButton;

    private MesaDAO mesaDAO;

    public SeleccionMesaController() {
        mesaDAO = new MesaDAOImpl(new Conexionsecu());
    }

    @FXML
    private void initialize() {
        List<Mesa> mesas = mesaDAO.obtenerMesasLibres(null);

        Button[] buttons = {mesa1Button, mesa2Button, mesa3Button, mesa4Button, mesa5Button, mesa6Button, mesa7Button, mesa8Button};

        for (int i = 0; i < buttons.length; i++) {
            if (i < mesas.size()) {
                Mesa mesa = mesas.get(i);
                buttons[i].setStyle(mesa.getEstado() ? "-fx-background-color: rgb(255, 128, 128);" : "-fx-background-color: rgb(157, 198, 117);");

                if (!mesa.getEstado()) { // Solo asignar evento si la mesa está libre
                    final int mesaId = mesa.getId_mesa(); // Almacena el ID de la mesa
                    buttons[i].setOnAction(event -> handleMesaSeleccionada(mesaId));
                } else {
                    buttons[i].setDisable(true); // Desactiva el botón si la mesa no está libre
                }
            } else {
                buttons[i].setStyle("-fx-background-color: rgb(200, 200, 200);");
                buttons[i].setDisable(true); // Desactiva el botón si no hay mesa
            }
        }
    }

    private void handleMesaSeleccionada(int mesaId) {
        // Lógica para navegar a la vista de pedidos
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/pedido-view.fxml"));
            Parent root = loader.load();

            // Si necesitas pasar el ID de la mesa a la siguiente vista
            PedidoController pedidoController = loader.getController();
            pedidoController.setMesaId(mesaId);

            Stage stage = (Stage) mesa1Button.getScene().getWindow(); // Puede ser cualquier botón
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de errores
        }
    }

    @FXML
    private void handleVolver() {
        try {
            // Cargar la vista del panel principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/mozo-view.fxml"));
            Parent root = loader.load();
            // Obtener la escena actual
            Stage stage = (Stage) volverButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de errores
        }
    }

}
