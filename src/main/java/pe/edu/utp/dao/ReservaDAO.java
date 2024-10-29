package pe.edu.utp.dao;

import pe.edu.utp.modelos.Reserva;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaDAO {
    void agregarReserva(Reserva reserva);
    void eliminarReserva(int idReserva);
    Reserva obtenerReserva(int idReserva);
    List<Reserva> buscarReservasPorCliente(int idCliente);
    List<Reserva> buscarReservasPorMesa(int idMesa);
    List<Reserva> buscarReservasPorFecha(LocalDateTime fecha);
    List<Reserva> obtenerTodasLasReservas();
}