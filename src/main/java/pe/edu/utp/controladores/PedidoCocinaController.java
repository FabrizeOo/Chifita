package pe.edu.utp.controladores;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pe.edu.utp.dao.PedidoCocinaDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pe.edu.utp.dao.impl.PedidoCocinaDAOimpl;
import pe.edu.utp.modelos.PedidoCocinaDTO;
import pe.edu.utp.utilidades.Conexionsecu;

public class PedidoCocinaController {

    @FXML
    private TableView<PedidoCocinaDTO> tablePedidos;
    @FXML
    private TableColumn<PedidoCocinaDTO, Integer> idPedidoColumn;
    @FXML
    private TableColumn<PedidoCocinaDTO, String> descripcionColumn;
    @FXML
    private TableColumn<PedidoCocinaDTO, Integer> cantidadColumn;
    @FXML
    private TableColumn<PedidoCocinaDTO, Integer> mesaColumn;
    @FXML
    private Button btnEnviarACaja;
    @FXML
    private Button btnVolverunu;

    private PedidoCocinaDAO pedidoCocinaDAO;

    public PedidoCocinaController() {
        // Inicializa el DAO
        this.pedidoCocinaDAO = new PedidoCocinaDAOimpl(new Conexionsecu());
    }

    @FXML
    public void initialize() {
        // Configura las columnas
        idPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        mesaColumn.setCellValueFactory(new PropertyValueFactory<>("idMesa"));
        btnVolverunu.setOnAction(event -> handleVolver());
        // Cargar datos en la tabla
        cargarPedidos();



    }
    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/mozo-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolverunu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Considerar un mejor manejo de errores aquí
        }
    }

    private void cargarPedidos() {
        ObservableList<PedidoCocinaDTO> pedidos = pedidoCocinaDAO.obtenerPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No se encontraron pedidos.");
        } else {
            System.out.println("Pedidos cargados: " + pedidos.size());
        }
        tablePedidos.setItems(pedidos);
    }
}
