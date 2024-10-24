package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.ProductoDAO;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAOImpl implements ProductoDAO {
    private final Conexionsecu conexion;

    public ProductoDAOImpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public String obtenerNombrePorId(int idProducto) {
        String nombre = null;
        String sql = "SELECT nombre FROM productos WHERE id_producto = ?"; // Asegúrate de que el nombre de la tabla y columna sea correcto
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
}

