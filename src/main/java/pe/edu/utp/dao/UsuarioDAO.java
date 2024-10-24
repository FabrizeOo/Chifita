package pe.edu.utp.dao;

import pe.edu.utp.modelos.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario validarInicioSesion(String nombreUsuario, String contrasena);
    List<Usuario> obtenerUsuariosPorCargo(String cargoUsuario);
    String obtenerCargoSiValido(String nombreUsuario, String contrasena);
    void cambiarEstadoActividad(int idUsuario, Boolean actividad);
}


