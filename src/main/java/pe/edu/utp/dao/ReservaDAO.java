package pe.edu.utp.dao;

import pe.edu.utp.modelos.Reserva;

import java.util.List;

public interface ReservaDAO {
    void agregarReserva(Reserva reserva);
    void editarReserva(Reserva reserva);
    void eliminarReserva(int idReserva);
    List<Reserva> obtenerReservas();
}

