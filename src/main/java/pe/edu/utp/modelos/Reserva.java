package pe.edu.utp.modelos;

import java.time.LocalDateTime;
import java.util.Date;

public class Reserva {
    private int id_reserva;
    private int id_cliente;
    private int id_usuario;
    private LocalDateTime fecha; // Cambiado a LocalDateTime
    private int id_mesa;

    // Constructor
    public Reserva(int id_reserva, int id_cliente, int id_usuario, LocalDateTime fecha, int id_mesa) {
        this.id_reserva = id_reserva;
        this.id_cliente = id_cliente;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.id_mesa = id_mesa;
    }

    public Reserva(int idReserva, int idCliente, int idUsuario, Date date, int idMesa) {
    }

    // Getters
    public int getId_reserva() {
        return id_reserva;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public LocalDateTime getFecha() {
        return fecha; // Cambiado a LocalDateTime
    }

    public int getId_mesa() {
        return id_mesa;
    }

    // Setters
    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setFecha(LocalDateTime fecha) { // Cambiado a LocalDateTime
        this.fecha = fecha;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_reserva=" + id_reserva +
                ", id_cliente=" + id_cliente +
                ", id_usuario=" + id_usuario +
                ", fecha=" + fecha +
                ", id_mesa=" + id_mesa +
                '}';
    }
}