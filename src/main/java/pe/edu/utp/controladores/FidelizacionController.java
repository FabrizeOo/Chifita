package pe.edu.utp.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pe.edu.utp.dao.ClienteDAO;
import pe.edu.utp.dao.impl.ClienteDAOImpl;
import pe.edu.utp.modelos.Cliente;
import pe.edu.utp.utilidades.Conexionsecu;

public class FidelizacionController {

    // Declaraciones de FXML
    @FXML private TextField txtBuscarClienteGeneral;
    @FXML private TextField txtBuscarClienteRecompensas;
    @FXML private TextField txtPuntosAsignar;
    @FXML private Button btnAgregarCliente;
    @FXML private VBox resultadoBusqueda;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, Integer> colPuntos;
    @FXML private ListView<String> listaSugerencias;
    @FXML private Button btnSalir;
    @FXML private Label lblNombreClienteSeleccionado;
    @FXML private Label lblEmailClienteSeleccionado;
    @FXML private Label lblPuntosClienteSeleccionado;
    @FXML private ComboBox<Recompensa> comboRecompensaSeleccionada;
    @FXML private TableView<Recompensa> tablaRecompensas;
    @FXML private TableColumn<Recompensa, String> colRecompensa;
    @FXML private TableColumn<Recompensa, Integer> colPuntosRequeridos;
    @FXML private TableColumn<Recompensa, String> colDescripcion;
    @FXML private ListView<String> listaClientesConPuntos;
    @FXML
    private ListView<String> listaSugerenciasRecompensas;


    private ObservableList<Cliente> clientesData;
    private ObservableList<Recompensa> recompensasData;
    private ClienteDAO clienteDAO;

    // Constructor
    public FidelizacionController() {
        this.clienteDAO = new ClienteDAOImpl(new Conexionsecu());
    }

    // Clase interna para representar una Recompensa
    public class Recompensa {
        private String nombre;
        private int puntosRequeridos;
        private String descripcion;

        public Recompensa(String nombre, int puntosRequeridos, String descripcion) {
            this.nombre = nombre;
            this.puntosRequeridos = puntosRequeridos;
            this.descripcion = descripcion;
        }

        public String getNombre() {
            return nombre;
        }

        public int getPuntosRequeridos() {
            return puntosRequeridos;
        }

        public String getDescripcion() {
            return descripcion;
        }

        @Override
        public String toString() {
            return nombre + " (" + puntosRequeridos + " puntos)";
        }
    }

    @FXML
    public void initialize() {
        // Configuración inicial de tablas y datos
        configurarColumnas();
        configurarColumnasRecompensas();
        clientesData = FXCollections.observableArrayList();
        tablaClientes.setItems(clientesData);
        recompensasData = FXCollections.observableArrayList();
        tablaRecompensas.setItems(recompensasData);
        cargarClientes();
        cargarRecompensasPredefinidas();
        comboRecompensaSeleccionada.setItems(recompensasData);

        // Listener para sugerencias de búsqueda en el campo general
        txtBuscarClienteGeneral.textProperty().addListener((observable, oldValue, newValue) -> mostrarSugerencias());
    }

    // Configuración de columnas para la tabla de clientes
    private void configurarColumnas() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPuntos.setCellValueFactory(new PropertyValueFactory<>("puntos"));
    }

    // Configuración de columnas para la tabla de recompensas
    private void configurarColumnasRecompensas() {
        colRecompensa.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPuntosRequeridos.setCellValueFactory(new PropertyValueFactory<>("puntosRequeridos"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    // Cargar recompensas predefinidas en la tabla y el ComboBox
    private void cargarRecompensasPredefinidas() {
        recompensasData.addAll(
                new Recompensa("Descuento 10%", 100, "10% de descuento en tu próxima compra"),
                new Recompensa("Postre gratis", 150, "Un postre gratis a elección"),
                new Recompensa("2x1 en bebidas", 200, "2x1 en bebidas no alcohólicas"),
                new Recompensa("Cena gratis", 500, "Una cena gratis para dos personas")
        );
    }

    // Cargar clientes desde la base de datos
    private void cargarClientes() {
        clientesData.clear();
        clientesData.addAll(clienteDAO.leerTodosCliente());
    }

    // Mostrar sugerencias de clientes en tiempo real
    @FXML
    private void mostrarSugerencias() {
        String textoBusqueda = txtBuscarClienteGeneral.getText().trim();
        if (!textoBusqueda.isEmpty()) {
            ObservableList<String> sugerencias = FXCollections.observableArrayList();
            for (Cliente cliente : clienteDAO.leerTodosCliente()) {
                if (cliente.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        cliente.getEmail().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                    sugerencias.add(cliente.getNombre() + " - " + cliente.getEmail());
                }
            }
            listaSugerencias.setItems(sugerencias);
            listaSugerencias.setVisible(!sugerencias.isEmpty());
        } else {
            listaSugerencias.setVisible(false);
        }
    }

    // Seleccionar una sugerencia de cliente
    @FXML
    private void seleccionarSugerencia() {
        String seleccion = listaSugerencias.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] datos = seleccion.split(" - ");
            txtBuscarClienteGeneral.setText(datos[0]);
            listaSugerencias.setVisible(false);
        }
    }

    // Agregar cliente a la tabla de clientes seleccionados
    @FXML
    private void agregarCliente() {
        String textoBusqueda = txtBuscarClienteGeneral.getText().trim();
        Cliente clienteEncontrado = null;
        for (Cliente cliente : clienteDAO.leerTodosCliente()) {
            if (cliente.getNombre().equalsIgnoreCase(textoBusqueda) || cliente.getEmail().equalsIgnoreCase(textoBusqueda)) {
                clienteEncontrado = cliente;
                break;
            }
        }
        if (clienteEncontrado != null && !clientesData.contains(clienteEncontrado)) {
            clientesData.add(clienteEncontrado);
            txtBuscarClienteGeneral.clear();
            listaSugerencias.setVisible(false);
        } else {
            mostrarAlerta("Cliente ya agregado", "El cliente ya está en la lista o no existe.");
        }
    }

    // Buscar clientes con suficientes puntos para recompensas
    @FXML
    private void buscarClientesConPuntos() {
        String textoBusqueda = txtBuscarClienteRecompensas.getText().trim().toLowerCase();
        ObservableList<String> clientesConPuntos = FXCollections.observableArrayList();
        for (Cliente cliente : clienteDAO.leerTodosCliente()) {
            if ((cliente.getNombre().toLowerCase().contains(textoBusqueda) || cliente.getEmail().toLowerCase().contains(textoBusqueda)) &&
                    cliente.getPuntos() >= getMinimoPuntosParaRecompensa()) {
                clientesConPuntos.add(cliente.getNombre() + " - " + cliente.getEmail());
            }
        }
        listaClientesConPuntos.setItems(clientesConPuntos);
        listaClientesConPuntos.setVisible(!clientesConPuntos.isEmpty());
    }

    // Obtener el mínimo de puntos requeridos para una recompensa
    private int getMinimoPuntosParaRecompensa() {
        return recompensasData.stream().mapToInt(Recompensa::getPuntosRequeridos).min().orElse(0);
    }

    // Seleccionar cliente de la lista con puntos
    @FXML
    private void seleccionarClienteConPuntos() {
        String seleccion = listaClientesConPuntos.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] datos = seleccion.split(" - ");
            Cliente cliente = clienteDAO.leerClientePorNombre(datos[0]);
            if (cliente != null) {
                lblNombreClienteSeleccionado.setText(cliente.getNombre());
                lblEmailClienteSeleccionado.setText(cliente.getEmail());
                lblPuntosClienteSeleccionado.setText(String.valueOf(cliente.getPuntos()));
            }
        }
    }

    // Asignar recompensa al cliente seleccionado
    @FXML
    private void asignarRecompensa() {
        // Obtiene el nombre del cliente seleccionado en los campos de la interfaz
        String nombreCliente = lblNombreClienteSeleccionado.getText();
        // Obtiene la recompensa seleccionada en el ComboBox
        Recompensa recompensaSeleccionada = comboRecompensaSeleccionada.getValue();

        // Validación para asegurarse de que una recompensa haya sido seleccionada
        if (recompensaSeleccionada == null) {
            mostrarAlerta("Error", "Seleccione una recompensa para asignar.");
            return;
        }

        // Busca al cliente en la base de datos por nombre
        Cliente cliente = clienteDAO.leerClientePorNombre(nombreCliente);
        // Verifica que el cliente exista y tenga suficientes puntos para canjear la recompensa
        if (cliente != null && cliente.getPuntos() >= recompensaSeleccionada.getPuntosRequeridos()) {
            // Resta los puntos de la recompensa a los puntos del cliente
            int nuevosPuntos = cliente.getPuntos() - recompensaSeleccionada.getPuntosRequeridos();
            // Actualiza los puntos del cliente en la base de datos
            boolean actualizado = clienteDAO.actualizarPuntosCliente(cliente.getId_cliente(), nuevosPuntos);

            if (actualizado) {
                // Actualiza la etiqueta de puntos del cliente en la interfaz
                lblPuntosClienteSeleccionado.setText(String.valueOf(nuevosPuntos));
                // Refresca la tabla de clientes para reflejar los cambios
                cargarClientes();
                // Muestra una alerta de confirmación indicando que la recompensa fue asignada exitosamente
                mostrarAlerta("Recompensa Asignada", "La recompensa '" + recompensaSeleccionada.getNombre() + "' ha sido asignada a " + cliente.getNombre() + ".");
            } else {
                mostrarAlerta("Error", "Hubo un problema al actualizar los puntos del cliente.");
            }
        } else {
            mostrarAlerta("Puntos insuficientes", "El cliente no tiene suficientes puntos para esta recompensa.");
        }
    }


    // Eliminar cliente de la lista de la tabla
    @FXML
    private void eliminarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un cliente de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación de eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar al cliente " + clienteSeleccionado.getNombre() + " de la tabla?");
        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            clientesData.remove(clienteSeleccionado);
            mostrarAlerta("Cliente Eliminado", "El cliente " + clienteSeleccionado.getNombre() + " ha sido eliminado de la tabla.");
        }
    }

    // Mostrar una alerta
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Cerrar la aplicación
    @FXML
    private void salirAplicacion() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void asignarPuntos() {
        try {
            int puntos = Integer.parseInt(txtPuntosAsignar.getText().trim());

            // Selecciona el cliente actualmente seleccionado en la tabla
            Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();

            if (clienteSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un cliente de la tabla para asignarle puntos.");
                return;
            }

            // Actualiza los puntos del cliente seleccionado
            int nuevosPuntos = clienteSeleccionado.getPuntos() + puntos;
            boolean actualizado = clienteDAO.actualizarPuntosCliente(clienteSeleccionado.getId_cliente(), nuevosPuntos);

            if (actualizado) {
                // Actualiza el objeto en clientesData y refresca la tabla
                clienteSeleccionado.setPuntos(nuevosPuntos);
                tablaClientes.refresh(); // Refresca la vista de la tabla
                mostrarAlerta("Puntos Asignados", "Se han asignado " + puntos + " puntos a " + clienteSeleccionado.getNombre() + ".");
            } else {
                mostrarAlerta("Error", "No se pudo actualizar los puntos en la base de datos.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingresa un número de puntos válido.");
        }
    }

    @FXML
    private void mostrarSugerenciasRecompensas() {
        String textoBusqueda = txtBuscarClienteRecompensas.getText().trim();
        if (!textoBusqueda.isEmpty()) {
            ObservableList<String> sugerencias = FXCollections.observableArrayList();
            for (Cliente cliente : clienteDAO.leerTodosCliente()) {
                if (cliente.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                        cliente.getEmail().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                    sugerencias.add(cliente.getNombre() + " - " + cliente.getEmail());
                }
            }
            listaSugerenciasRecompensas.setItems(sugerencias);
            listaSugerenciasRecompensas.setVisible(!sugerencias.isEmpty());
        } else {
            listaSugerenciasRecompensas.setVisible(false);
        }
    }

    @FXML
    private void buscarClienteRecompensas() {
        String nombre = txtBuscarClienteRecompensas.getText().trim();
        Cliente clienteEncontrado = clienteDAO.leerClientePorNombre(nombre);
        if (clienteEncontrado != null) {
            lblNombreClienteSeleccionado.setText(clienteEncontrado.getNombre());
            lblEmailClienteSeleccionado.setText(clienteEncontrado.getEmail());
            lblPuntosClienteSeleccionado.setText(String.valueOf(clienteEncontrado.getPuntos()));
        } else {
            mostrarAlerta("Cliente no encontrado", "No se encontró un cliente con ese nombre.");
        }
    }

    @FXML
    private void seleccionarSugerenciaRecompensas() {
        String seleccion = listaSugerenciasRecompensas.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] datos = seleccion.split(" - ");
            txtBuscarClienteRecompensas.setText(datos[0]);
            listaSugerenciasRecompensas.setVisible(false);
        }
    }



}
