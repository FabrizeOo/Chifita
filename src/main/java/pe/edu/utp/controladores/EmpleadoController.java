package pe.edu.utp.controladores;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pe.edu.utp.modelos.Empleado;
import pe.edu.utp.dao.EmpleadoDAO;
import pe.edu.utp.dao.impl.EmpleadoDAOImpl;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;

public class EmpleadoController {

    @FXML private TableView<Empleado> tableView;
    @FXML private TableColumn<Empleado, Integer> idColumn;
    @FXML private TableColumn<Empleado, String> nombreColumn;
    @FXML private TableColumn<Empleado, String> apellidoColumn;
    @FXML private TableColumn<Empleado, String> nombreUsuarioColumn;
    @FXML private TableColumn<Empleado, String> cargoColumn;
    @FXML private TableColumn<Empleado, Boolean> actividadColumn;


    @FXML private TextField nombreField;
    @FXML private TextField apellidoField;
    @FXML private TextField nombreUsuarioField;
    @FXML private TextField contrasenaField;
    @FXML private ComboBox<String> cargoComboBox;

    private EmpleadoDAO empleadoDAO;
    private ObservableList<Empleado> empleados;

    public EmpleadoController() {
        // Inicializar el DAO aquí
        this.empleadoDAO = new EmpleadoDAOImpl(new Conexionsecu());
    }

    @FXML
    public void initialize() {
        // Inicializar el ComboBox con los cargos
        cargoComboBox.getItems().addAll("Recepcionista", "Mozo", "Rey");

        // Configurar las columnas de la tabla
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_usuario()).asObject());
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        nombreUsuarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));
        cargoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCargo()));
        actividadColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getActividad()));

        // Cargar empleados en la tabla
        cargarEmpleados();

        // Configurar la selección de la tabla
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarEmpleado(newSelection);
            }
        });
    }

    private void cargarEmpleados() {
        List<Empleado> empleadoList = empleadoDAO.leerTodosEmpleados();
        empleados = FXCollections.observableArrayList(empleadoList);
        tableView.setItems(empleados);
    }

    private void mostrarEmpleado(Empleado empleado) {
        nombreField.setText(empleado.getNombre());
        apellidoField.setText(empleado.getApellido());
        nombreUsuarioField.setText(empleado.getNombreUsuario());
        contrasenaField.setText(empleado.getContrasena());
        cargoComboBox.setValue(empleado.getCargo());

    }

    @FXML
    private void agregarEmpleado() {
        int nuevoId = empleadoDAO.obtenerUltimoId() + 1; // Obtener el último ID y sumarle 1

        Empleado nuevoEmpleado = new Empleado(
                nuevoId,
                nombreField.getText(),
                apellidoField.getText(),
                nombreUsuarioField.getText(),
                contrasenaField.getText(),
                cargoComboBox.getValue(),
                true // Establecer como activo
        );
        empleadoDAO.crearEmpleado(nuevoEmpleado);
        cargarEmpleados();
        limpiarCampos();
    }


    @FXML
    private void editarEmpleado() {
        Empleado empleadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            empleadoSeleccionado.setNombre(nombreField.getText());
            empleadoSeleccionado.setApellido(apellidoField.getText());
            empleadoSeleccionado.setNombreUsuario(nombreUsuarioField.getText());
            empleadoSeleccionado.setContrasena(contrasenaField.getText());
            empleadoSeleccionado.setCargo(cargoComboBox.getValue());
            empleadoDAO.actualizarEmpleado(empleadoSeleccionado);
            cargarEmpleados();
            limpiarCampos();
        }
    }

    @FXML
    private void eliminarEmpleado() {
        Empleado empleadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            empleadoDAO.eliminarEmpleado(empleadoSeleccionado.getId_usuario());
            cargarEmpleados();
            limpiarCampos();
        }
    }

    @FXML
    private void desconectarEmpleado() {
        Empleado empleadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            empleadoDAO.desconectarEmpleado(empleadoSeleccionado.getId_usuario());
            cargarEmpleados();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        nombreUsuarioField.clear();
        contrasenaField.clear();
        cargoComboBox.setValue(null);
    }
}
