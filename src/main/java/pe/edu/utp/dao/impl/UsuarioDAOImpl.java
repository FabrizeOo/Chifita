package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.UsuarioDAO;
import pe.edu.utp.modelos.Usuario;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Conexionsecu conexion;

    public UsuarioDAOImpl() {
        this.conexion = new Conexionsecu(); // Crear instancia de Conexionsecu
    }

    @Override
    public Usuario validarInicioSesion(String nombreUsuario, String contrasena) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?";

        Connection conn = null;

        try {
            conn = conexion.conectar(); // Obtén la nueva conexión
            if (conn == null) {
                System.err.println("No se pudo establecer conexión a la base de datos.");
                return null; // O lanza una excepción
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombreUsuario);
                pstmt.setString(2, contrasena);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("nombreUsuario"),
                            rs.getString("contrasena"),
                            rs.getString("cargo"),
                            rs.getBoolean("actividad")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        } finally {
            conexion.desconectar(conn); // Desconectar la conexión
        }

        return usuario; // Retorna el usuario o null si no se encontró
    }


    @Override
    public List<Usuario> obtenerUsuariosPorCargo(String cargoUsuario) {
        return List.of();
    }
    @Override
    public String obtenerCargoSiValido(String nombreUsuario, String contrasena) {
        Usuario usuario = validarInicioSesion(nombreUsuario, contrasena);
        if (usuario != null) {
            return usuario.getCargo(); // Retorna el cargo del usuario
        } else {
            return null; // O una cadena vacía, dependiendo de tus necesidades
        }
    }
    @Override
    public void cambiarEstadoActividad(int idUsuario, Boolean actividad) {
        String sql = "UPDATE usuarios SET actividad = ? WHERE id_usuario = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, actividad);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al cambiar el estado de actividad: " + e.getMessage());
        }
    }

}
