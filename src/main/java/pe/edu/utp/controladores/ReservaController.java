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
import pe.edu.utp.modelos.Reserva;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private TableColumn<Reserva, Integer> colCliente;
    @FXML
    private TableColumn<Reserva, String> colFecha;
    @FXML
    private TableColumn<Reserva, Integer> colMesa;

    private ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicializar las columnas de la tabla
        colCliente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_cliente()).asObject());
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(cellData.getValue().getFecha())));
        colMesa.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_mesa()).asObject());

        // Cargar reservas existentes (si las hay)
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
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgregar() {
        try {
            // Lógica para agregar una reserva
            int cliente = Integer.parseInt(clienteField.getText());
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaReservaField.getText());
            int mesa = Integer.parseInt(numeroMesaField.getText());

            Reserva nuevaReserva = new Reserva(0, cliente, 0, fecha, mesa);
            listaReservas.add(nuevaReserva);
            tablaReservas.setItems(listaReservas);

            // Limpiar campos
            limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace(); // Manejar excepciones adecuadamente
        }
    }

    @FXML
    private void handleEditar() {
        // Lógica para editar la reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            try {
                reservaSeleccionada.setId_cliente(Integer.parseInt(clienteField.getText()));
                reservaSeleccionada.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fechaReservaField.getText()));
                reservaSeleccionada.setId_mesa(Integer.parseInt(numeroMesaField.getText()));
                tablaReservas.refresh(); // Refrescar la tabla
                limpiarCampos();
            } catch (Exception e) {
                e.printStackTrace(); // Manejar excepciones adecuadamente
            }
        }
    }

    @FXML
    private void handleEliminar() {
        // Lógica para eliminar la reserva seleccionada
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
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
        // Aquí puedes cargar reservas desde la base de datos o de donde las necesites
        // Ejemplo de adición de reservas dummy
        listaReservas.add(new Reserva(1, 1, 0, new Date(), 1));
        listaReservas.add(new Reserva(2, 2, 0, new Date(), 2));
        tablaReservas.setItems(listaReservas);
    }
}
