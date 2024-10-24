package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.PedidoDAO;
import pe.edu.utp.modelos.Pedido;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private final Conexionsecu conexion;

    public PedidoDAOImpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public int crearPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_usuario, id_mesa, estado) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getId_usuario());
            stmt.setInt(2, pedido.getId_mesa());
            stmt.setBoolean(3, pedido.isEstado()); // Estado del pedido

            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retorna el ID del nuevo pedido
                } else {
                    throw new SQLException("No se pudo obtener el ID del pedido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retorna -1 en caso de error
        }
    }

    @Override
    public Pedido leerPedido(int id) {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_usuario"),
                        0, // Placeholder para id_detallePedido
                        rs.getInt("id_mesa"),
                        rs.getBoolean("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pedido> leerTodosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Connection conn = conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_usuario"),
                        0, // Placeholder
                        rs.getInt("id_mesa"),
                        rs.getBoolean("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public List<Pedido> leerPedidosPendientes() {
        List<Pedido> pedidosPendientes = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE estado = false";
        try (Connection conn = conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pedidosPendientes.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_usuario"),
                        0, // Placeholder
                        rs.getInt("id_mesa"),
                        rs.getBoolean("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidosPendientes;
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET id_usuario = ?, id_mesa = ?, estado = ? WHERE id_pedido = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId_usuario());
            stmt.setInt(2, pedido.getId_mesa());
            stmt.setBoolean(3, pedido.isEstado());
            stmt.setInt(4, pedido.getId_pedido());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
