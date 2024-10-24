package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.DetallePedidoDAO;
import pe.edu.utp.modelos.DetallePedido;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {
    private Conexionsecu conexion;

    public DetallePedidoDAOImpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crearDetallePedido(DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdPedido());
            stmt.setInt(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DetallePedido> leerDetallesPorPedido(int idPedido) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedidos WHERE id_pedido = ?"; // Asegúrate de que el nombre de la columna sea correcto
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                detalles.add(new DetallePedido(
                        rs.getInt("id_detallepedido"), // Cambia 'idPedido' por 'id_detallepedido'
                        rs.getInt("id_producto"),      // Cambia 'idProducto' por 'id_producto'
                        rs.getInt("cantidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @Override
    public void eliminarDetallePedido(int idDetalle) {
        String sql = "DELETE FROM detalle_pedidos WHERE id_detalle = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

