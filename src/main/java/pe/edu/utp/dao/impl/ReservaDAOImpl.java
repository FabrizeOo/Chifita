package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.ReservaDAO;
import pe.edu.utp.modelos.Reserva;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {
    private final Conexionsecu conexion;

    public ReservaDAOImpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public void agregarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (id_cliente, id_usuario, fecha, id_mesa) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getId_cliente());
            stmt.setInt(2, reserva.getId_usuario());
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getFecha()));
            stmt.setInt(4, reserva.getId_mesa());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarReserva(int idReserva) {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reserva obtenerReserva(int idReserva) {
        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_mesa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reserva> buscarReservasPorCliente(int idCliente) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE id_cliente = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_mesa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public List<Reserva> buscarReservasPorMesa(int idMesa) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE id_mesa = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMesa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_mesa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public List<Reserva> buscarReservasPorFecha(LocalDateTime fecha) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE DATE(fecha) = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(fecha.toLocalDate()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_mesa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public List<Reserva> obtenerTodasLasReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reservas.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("id_mesa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
}
