package pe.edu.utp.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.utp.dao.ClienteDAO;
import pe.edu.utp.dao.impl.ClienteDAOImpl;
import pe.edu.utp.modelos.Cliente;
import pe.edu.utp.utilidades.Conexionsecu;

public class ClienteController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private TableView<Cliente> tableClientes;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellido;
    @FXML
    private TableColumn<Cliente, String> colTelefono;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private Button volverButton;

    private ObservableList<Cliente> listaClientes;
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAOImpl(new Conexionsecu());
        this.listaClientes = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Inicializar la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        cargarClientes();
    }

    @FXML
    public void agregarCliente() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        Cliente nuevoCliente = new Cliente(nombre, apellido, telefono, email);
        clienteDAO.crearCliente(nuevoCliente);
        listaClientes.add(nuevoCliente);
        tableClientes.setItems(listaClientes);

        limpiarCampos();
    }

    @FXML
    public void editarCliente() {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clienteSeleccionado.setNombre(txtNombre.getText());
            clienteSeleccionado.setApellido(txtApellido.getText());
            clienteSeleccionado.setTelefono(txtTelefono.getText());
            clienteSeleccionado.setEmail(txtEmail.getText());

            clienteDAO.actualizarCliente(clienteSeleccionado);
            cargarClientes(); // Recargar la tabla
        }
    }

    @FXML
    public void eliminarCliente() {
        Cliente clienteSeleccionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clienteDAO.eliminarCliente(clienteSeleccionado.getId_cliente());
            listaClientes.remove(clienteSeleccionado);
            cargarClientes(); // Recargar la tabla
        }
    }

    private void cargarClientes() {
        listaClientes.clear();
        listaClientes.addAll(clienteDAO.leerTodosCliente());
        tableClientes.setItems(listaClientes);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEmail.clear();
    }
    @FXML
    private void handleVolver() {
        try {
            // Cargar la vista del panel principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/recepcionista-view.fxml"));
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
