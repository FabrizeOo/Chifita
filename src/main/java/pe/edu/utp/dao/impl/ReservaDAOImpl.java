package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.ReservaDAO;
import pe.edu.utp.modelos.Reserva;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {
    private Conexionsecu conexion;

    public ReservaDAOImpl() {
        this.conexion = new Conexionsecu(); // Inicializa tu conexión
    }

    @Override
    public void agregarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (id_cliente, id_usuario, fecha, id_mesa) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getId_cliente());
            stmt.setInt(2, reserva.getId_usuario());
            stmt.setTimestamp(3, new Timestamp(reserva.getFecha().getTime())); // Convertir Date a Timestamp
            stmt.setInt(4, reserva.getId_mesa());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editarReserva(Reserva reserva) {
        String sql = "UPDATE reservas SET id_cliente = ?, id_usuario = ?, fecha = ?, id_mesa = ? WHERE id_reserva = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getId_cliente());
            stmt.setInt(2, reserva.getId_usuario());
            stmt.setTimestamp(3, new Timestamp(reserva.getFecha().getTime())); // Convertir Date a Timestamp
            stmt.setInt(4, reserva.getId_mesa());
            stmt.setInt(5, reserva.getId_reserva());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarReserva(int idReserva) {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reserva> obtenerReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id_reserva, id_cliente, id_usuario, fecha, id_mesa FROM reservas";

        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int id_reserva = resultSet.getInt("id_reserva");
                int id_cliente = resultSet.getInt("id_cliente");
                int id_usuario = resultSet.getInt("id_usuario");
                Date fecha = resultSet.getTimestamp("fecha");
                int id_mesa = resultSet.getInt("id_mesa");

                reservas.add(new Reserva(id_reserva, id_cliente, id_usuario, fecha, id_mesa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
}
