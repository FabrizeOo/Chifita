package pe.edu.utp.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.utp.dao.impl.DetallePedidoDAOImpl;
import pe.edu.utp.modelos.DetallePedido;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;

public class VentasController {

    @FXML
    private TableView<DetallePedido> tablaPedidos;

    @FXML
    private TableColumn<DetallePedido, Integer> colIDPedido;

    @FXML
    private TableColumn<DetallePedido, Integer> colIDProducto;

    @FXML
    private TableColumn<DetallePedido, Integer> colCantidad;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    private DetallePedidoDAOImpl detallePedidoDAO;

    private ObservableList<DetallePedido> detallesPedidosList;

    public VentasController() {
        // Inicializa la conexión y el DAO
        Conexionsecu conexion = new Conexionsecu();
        detallePedidoDAO = new DetallePedidoDAOImpl(conexion);
    }

    @FXML
    public void initialize() {
        // Configura las columnas de la tabla
        colIDPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colIDProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Cargar datos iniciales
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        List<DetallePedido> detalles = detallePedidoDAO.leerDetallesPorPedido(1); // Cambia el ID de pedido si es necesario
        detallesPedidosList = FXCollections.observableArrayList(detalles);
        tablaPedidos.setItems(detallesPedidosList);
    }

    @FXML
    private void handleEliminar() {
        DetallePedido detalleSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null) {
            detallePedidoDAO.eliminarDetallePedido(detalleSeleccionado.getIdPedido());
            detallesPedidosList.remove(detalleSeleccionado);
        } else {
            mostrarMensaje("Por favor, selecciona un pedido para eliminar.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
