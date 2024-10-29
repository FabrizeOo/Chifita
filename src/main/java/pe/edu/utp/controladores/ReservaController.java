package pe.edu.utp.controladores;

import javafx.beans.property.SimpleIntegerProperty;
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
import pe.edu.utp.dao.ReservaDAO;
import pe.edu.utp.dao.impl.ReservaDAOImpl;
import pe.edu.utp.modelos.Reserva;
import pe.edu.utp.utilidades.Conexionsecu;
import pe.edu.utp.modelos.SesionUsuario;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReservaController {
    @FXML
    private Button volverButton;
    @FXML
    private TextField numeroMesaField;
    @FXML
    private TextField clienteField;
    @FXML
    private TextField fechaReservaField;
    @FXML
    private TableView<Reserva> tablaReservas;
    @FXML
    private TableColumn<Reserva, Integer> colIdReserva;
    @FXML
    private TableColumn<Reserva, Integer> colCliente;
    @FXML
    private TableColumn<Reserva, String> colUsuario;
    @FXML
    private TableColumn<Reserva, String> colFecha;
    @FXML
    private TableColumn<Reserva, Integer> colMesa;

    private ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();
    private ReservaDAO reservaDAO;

    public ReservaController() {
        this.reservaDAO = new ReservaDAOImpl(new Conexionsecu());
    }

    @FXML
    public void initialize() {
        colIdReserva.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_reserva()).asObject());
        colCliente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_cliente()).asObject());
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty("Usuario " + cellData.getValue().getId_usuario()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        ));
        colMesa.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_mesa()).asObject());

        cargarReservas();
    }

    @FXML
    private void handleVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/fxml/recepcionista-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) volverButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Considerar un mejor manejo de errores aquí
        }
    }

    @FXML
    private void handleAgregar() {
        try {
            int cliente = Integer.parseInt(clienteField.getText());
            LocalDateTime fecha = LocalDateTime.parse(fechaReservaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            int mesa = Integer.parseInt(numeroMesaField.getText());
            int idUsuario = obtenerIdUsuario(); // Método para obtener el ID del usuario logueado

            Reserva nuevaReserva = new Reserva(0, cliente, idUsuario, fecha, mesa);
            reservaDAO.agregarReserva(nuevaReserva);
            listaReservas.add(nuevaReserva);
            tablaReservas.setItems(listaReservas);
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace(); // Manejar excepciones adecuadamente
        }
    }

    // ReservaController
    private int obtenerIdUsuario() {
        return SesionUsuario.getInstance().getIdUsuario(); // Asegúrate de que esto retorne el ID correcto
    }

    @FXML
    private void handleEditar() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try {
                reservaSeleccionada.setId_cliente(Integer.parseInt(clienteField.getText()));
                reservaSeleccionada.setFecha(LocalDateTime.parse(fechaReservaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                reservaSeleccionada.setId_mesa(Integer.parseInt(numeroMesaField.getText()));
                reservaDAO.agregarReserva(reservaSeleccionada);
                tablaReservas.refresh();
                limpiarCampos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEliminar() {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            reservaDAO.eliminarReserva(reservaSeleccionada.getId_reserva());
            listaReservas.remove(reservaSeleccionada);
            tablaReservas.setItems(listaReservas);
        }
    }

    private void limpiarCampos() {
        numeroMesaField.clear();
        clienteField.clear();
        fechaReservaField.clear();
    }

    private void cargarReservas() {
        listaReservas.clear(); // Limpiar antes de cargar
        List<Reserva> reservas = reservaDAO.obtenerTodasLasReservas();
        listaReservas.addAll(reservas);
        tablaReservas.setItems(listaReservas);
    }
}