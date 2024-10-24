package pe.edu.utp.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.edu.utp.dao.impl.EmpleadoDAOImpl;
import pe.edu.utp.modelos.Empleado;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;

public class EmpleadoController {
    @FXML
    private TableView<Empleado> tableView;
    @FXML
    private TableColumn<Empleado, Integer> idColumn;
    @FXML
    private TableColumn<Empleado, String> nombreColumn;
    @FXML
    private TableColumn<Empleado, String> apellidoColumn;
    @FXML
    private TableColumn<Empleado, String> nombreUsuarioColumn;
    @FXML
    private TableColumn<Empleado, String> cargoColumn;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField nombreUsuarioField;
    @FXML
    private TextField contrasenaField;
    @FXML
    private TextField cargoField;

    private EmpleadoDAOImpl empleadoDAO;

    public EmpleadoController() {
        Conexionsecu conexion = new Conexionsecu(); // Usa tu conexión
        empleadoDAO = new EmpleadoDAOImpl(conexion);
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        nombreUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        cargarEmpleados();
    }

    private void cargarEmpleados() {
        List<Empleado> empleados = empleadoDAO.leerTodosEmpleados();
        System.out.println("Número de empleados cargados: " + empleados.size()); // Debug
        tableView.getItems().setAll(empleados);
    }


    @FXML
    private void agregarEmpleado() {
        try {
            Empleado nuevoEmpleado = new Empleado(
                    obtenerNuevoId(),
                    nombreField.getText(),
                    apellidoField.getText(),
                    nombreUsuarioField.getText(),
                    contrasenaField.getText(),
                    cargoField.getText(),
                    false // o false
            );
            empleadoDAO.crearEmpleado(nuevoEmpleado);
            System.out.println("Empleado agregado: " + nuevoEmpleado.getNombre()); // Debug
            cargarEmpleados();
        } catch (IllegalArgumentException e) {
            mostrarMensajeDeError(e.getMessage());
        }
    }


    @FXML
    private void editarEmpleado() {
        Empleado empleadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            // Implementa la lógica para editar el empleado
            // Similar al método agregarEmpleado
        } else {
            mostrarMensajeDeError("Selecciona un empleado para editar.");
        }
    }

    @FXML
    private void eliminarEmpleado() {
        Empleado empleadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            empleadoDAO.eliminarEmpleado(empleadoSeleccionado.getId_usuario());
            cargarEmpleados();
        } else {
            mostrarMensajeDeError("Selecciona un empleado para eliminar.");
        }
    }

    private int obtenerNuevoId() {
        // Lógica para generar un nuevo ID, por ejemplo, el ID más alto + 1
        return empleadoDAO.leerTodosEmpleados().size() + 1; // Simple, mejora para producción
    }

    private void mostrarMensajeDeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
