package pe.edu.utp.dao;

import pe.edu.utp.modelos.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    // Implementación de obtenerTodosLosProductos()
    List<Producto> obtenerTodosLosProductos() throws SQLException;

    // Implementación de agregarProducto()
    void agregarProducto(Producto producto) throws SQLException;

    // Implementación de actualizarProducto()
    void actualizarProducto(Producto producto) throws SQLException;

    // Implementación de eliminarProducto()
    void eliminarProducto(int idProducto) throws SQLException;

    String obtenerNombrePorId(int idProducto) throws SQLException;
}
