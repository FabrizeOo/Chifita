package pe.edu.utp.dao.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pe.edu.utp.dao.PedidoCocinaDAO;
import pe.edu.utp.modelos.PedidoCocinaDTO;;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoCocinaDAOimpl implements PedidoCocinaDAO {
    private final Conexionsecu conexion;

    public PedidoCocinaDAOimpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public ObservableList<PedidoCocinaDTO> obtenerPedidos() {
        ObservableList<PedidoCocinaDTO> pedidos = FXCollections.observableArrayList();
        String query = "SELECT dp.id_pedido, " +
                "GROUP_CONCAT(CONCAT(p.nombre, ' - S/', p.precio, ' x', dp.cantidad) SEPARATOR ', ') AS descripcion, " +
                "SUM(dp.cantidad) AS cantidad_total, " +
                "ped.id_mesa " +
                "FROM detalle_pedidos dp " +
                "JOIN productos p ON dp.id_producto = p.id_producto " +
                "JOIN pedidos ped ON dp.id_pedido = ped.id_pedido " +
                "GROUP BY dp.id_pedido, ped.id_mesa"; // Agrupar también por id_mesa

        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                String descripcion = rs.getString("descripcion");
                int cantidadTotal = rs.getInt("cantidad_total");
                int idMesa = rs.getInt("id_mesa"); // Obtener id_mesa

                pedidos.add(new PedidoCocinaDTO(idPedido, descripcion, cantidadTotal, idMesa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}