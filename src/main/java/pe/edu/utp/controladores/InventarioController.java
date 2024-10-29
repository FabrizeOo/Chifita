package pe.edu.utp.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pe.edu.utp.dao.impl.ProductoDAOImpl;
import pe.edu.utp.modelos.Producto;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.SQLException;
import java.util.List;

public class InventarioController {

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> colIDProducto;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private ObservableList<Producto> productos;
    private ProductoDAOImpl productoDAO;

    @FXML
    public void initialize() {
        // Inicializa la conexión a la base de datos y el DAO
        Conexionsecu conexion = new Conexionsecu();
        productoDAO = new ProductoDAOImpl(conexion);

        // Configura las columnas de la tabla
        colIDProducto.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Inicializa la lista de productos y carga los datos desde la base de datos
        productos = FXCollections.observableArrayList();
        cargarProductos();

        // Asigna la lista de productos a la tabla
        tablaProductos.setItems(productos);
    }

    private void cargarProductos() {
        try {
            // Llama al método obtenerTodosLosProductos para cargar los productos desde la base de datos
            List<Producto> listaProductos = productoDAO.obtenerTodosLosProductos();
            productos.setAll(listaProductos);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los productos desde la base de datos.");
        }
    }

    @FXML
    private void handleAgregar() {
        // Lógica para agregar un nuevo producto
        System.out.println("Agregar producto");
    }

    @FXML
    private void handleEditar() {
        // Lógica para editar el producto seleccionado
        System.out.println("Editar producto");
    }

    @FXML
    private void handleEliminar() {
        // Lógica para eliminar el producto seleccionado
        System.out.println("Eliminar producto");
    }

    @FXML
    private void handleRegresar() {
        // Cierra la ventana actual
        Stage stage = (Stage) tablaProductos.getScene().getWindow();
        stage.close();
    }
}
