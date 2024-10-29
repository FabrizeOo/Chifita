package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.ProductoDAO;
import pe.edu.utp.modelos.Producto;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    private final Conexionsecu conexion;

    public ProductoDAOImpl(Conexionsecu conexion) {
        this.conexion = conexion;
    }

    // Implementación de obtenerTodosLosProductos()
    @Override
    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                ));
            }
        }
        return productos;
    }

    // Implementación de agregarProducto()
    @Override
    public void agregarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (id_producto, nombre, precio) VALUES (?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getId());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.executeUpdate();
        }
    }

    // Implementación de actualizarProducto()
    @Override
    public void actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, precio = ? WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getId());
            stmt.executeUpdate();
        }
    }

    // Implementación de eliminarProducto()
    @Override
    public void eliminarProducto(int idProducto) throws SQLException {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.executeUpdate();
        }
    }

    // Implementación de obtenerNombrePorId() si es necesario
    @Override
    public String obtenerNombrePorId(int idProducto) throws SQLException {
        String nombre = null;
        String sql = "SELECT nombre FROM productos WHERE id_producto = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        }
        return nombre;
    }
}