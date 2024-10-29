package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.utp.dao.DetallePedidoDAO;
import pe.edu.utp.dao.MesaDAO;
import pe.edu.utp.dao.PedidoDAO;
import pe.edu.utp.dao.ProductoDAO;
import pe.edu.utp.dao.impl.DetallePedidoDAOImpl;
import pe.edu.utp.dao.impl.MesaDAOImpl;
import pe.edu.utp.dao.impl.PedidoDAOImpl;
import pe.edu.utp.dao.impl.ProductoDAOImpl;
import pe.edu.utp.modelos.DetallePedido;
import pe.edu.utp.modelos.Pedido;
import pe.edu.utp.modelos.SesionUsuario;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PedidoController {
    @FXML
    private Button btn_procesar;
    @FXML
    private Button volverButton;

    @FXML
    private TextField product_101;
    @FXML
    private TextField product_102;
    @FXML
    private TextField product_201;
    @FXML
    private TextField product_202;
    @FXML
    private TextField product_301;
    @FXML
    private TextField product_302;
    // Agrega más TextFields según sea necesario

    private Map<Integer, TextField> productFields = new HashMap<>();
    private int mesaId;

    public void initialize() {
        // Inicializa el mapa de TextFields con los IDs de los productos
        productFields.put(101, product_101);
        productFields.put(102, product_102);
        productFields.put(201, product_201);
        productFields.put(202, product_202);
        productFields.put(301, product_301);
        productFields.put(302, product_302);

        // Agrega más productos según sea necesario
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    @FXML
    private void handleProcesar() throws SQLException {
        Map<Integer, Integer> pedidos = new HashMap<>();
        boolean hayProductos = false;

        // Crear una instancia del ProductoDAO
        ProductoDAO productoDAO = new ProductoDAOImpl(new Conexionsecu());

        // Iterar sobre el mapa de TextFields
        for (Map.Entry<Integer, TextField> entry : productFields.entrySet()) {
            TextField textField = entry.getValue();
            if (!textField.getText().trim().isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(textField.getText());
                    if (cantidad > 0) {
                        pedidos.put(entry.getKey(), cantidad); // Usa el ID del producto como clave
                        hayProductos = true;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar campos no numéricos
                }
            }
        }

        // Confirmación antes de procesar
        if (hayProductos) {
            // Crear el contenido del resumen
            StringBuilder resumen = new StringBuilder("Resumen del Pedido:\n");
            for (Map.Entry<Integer, Integer> entry : pedidos.entrySet()) {
                String nombreProducto = productoDAO.obtenerNombrePorId(entry.getKey());
                resumen.append("Producto: ").append(nombreProducto != null ? nombreProducto : "Desconocido")
                        .append(", Cantidad: ").append(entry.getValue()).append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Pedido");
            alert.setHeaderText("¿Desea confirmar el pedido?");
            alert.setContentText(resumen.toString() + "¿Desea continuar?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                procesarPedido(pedidos);
            } else {
                System.out.println("Pedido cancelado.");
            }
        } else {
            System.out.println("No se seleccionó ningún producto.");
        }
    }


    private void procesarPedido(Map<Integer, Integer> pedidos) {
        int idUsuario = SesionUsuario.getInstance().getIdUsuario();

        // Crear un nuevo pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setId_usuario(idUsuario);
        nuevoPedido.setId_mesa(mesaId);
        nuevoPedido.setEstado(false); // Estado inicial como pendiente

        PedidoDAO pedidoDAO = new PedidoDAOImpl(new Conexionsecu());
        int pedidoId = pedidoDAO.crearPedido(nuevoPedido); // Método correcto

        if (pedidoId > 0) {
            // Crear el DAO de mesa y bloquear la mesa
            MesaDAO mesaDAO = new MesaDAOImpl(new Conexionsecu());
            mesaDAO.actualizarEstadoMesa(mesaId, true); // Cambia el estado de la mesa a bloqueada

            DetallePedidoDAO detalleDAO = new DetallePedidoDAOImpl(new Conexionsecu());

            for (Map.Entry<Integer, Integer> entry : pedidos.entrySet()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdPedido(pedidoId);
                detalle.setIdProducto(entry.getKey()); // ID del producto
                detalle.setCantidad(entry.getValue()); // Cantidad del producto

                detalleDAO.crearDetallePedido(detalle); // Guardar detalle del pedido
            }
            System.out.println("Pedido procesado correctamente.");
        } else {
            System.out.println("Error al procesar el pedido.");
        }
    }


    @FXML
    private void handleVolver() {
        try {
            // Cargar la vista del panel principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/seleccion-mesas-view.fxml"));
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
