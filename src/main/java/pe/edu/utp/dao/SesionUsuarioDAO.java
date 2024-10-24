package pe.edu.utp.dao;

import pe.edu.utp.modelos.SesionDTO; // Asegúrate de importar tu DTO

import java.util.List;

public interface SesionUsuarioDAO {
    void registrarInicioSesion(int idUsuario);
    void registrarCierreSesion(int idUsuario);
    List<SesionDTO> obtenerSesionesPorMes(int idUsuario, int mes, int anio);
}

