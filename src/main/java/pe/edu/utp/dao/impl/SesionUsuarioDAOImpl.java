package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.SesionUsuarioDAO;
import pe.edu.utp.modelos.SesionDTO;
import pe.edu.utp.utilidades.Conexionsecu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SesionUsuarioDAOImpl implements SesionUsuarioDAO {

    private Conexionsecu conexion;

    public SesionUsuarioDAOImpl() {
        this.conexion = new Conexionsecu();
    }

    @Override
    public void registrarInicioSesion(int idUsuario) {
        String sql = "INSERT INTO sesion_usuario (id_usuario, hora_inicio) VALUES (?, NOW())";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar inicio de sesión: " + e.getMessage());
        }
    }

    @Override
    public void registrarCierreSesion(int idUsuario) {
        String sql = "UPDATE sesion_usuario SET hora_fin = NOW(), duracion = TIMESTAMPDIFF(SECOND, hora_inicio, NOW()) WHERE id_usuario = ? AND hora_fin IS NULL";
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar cierre de sesión: " + e.getMessage());
        }
    }

    @Override
    public List<SesionDTO> obtenerSesionesPorMes(int idUsuario, int mes, int anio) {
        List<SesionDTO> sesiones = new ArrayList<>();
        String sql = "SELECT COUNT(*) AS total_sesiones, SUM(duracion) AS total_horas " +
                "FROM sesion_usuario " +
                "WHERE id_usuario = ? AND MONTH(hora_inicio) = ? AND YEAR(hora_inicio) = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, mes);
            pstmt.setInt(3, anio);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int totalSesiones = rs.getInt("total_sesiones");
                int totalDuracion = rs.getInt("total_horas");
                sesiones.add(new SesionDTO(totalSesiones, totalDuracion));}
        } catch (SQLException e) {
            System.err.println("Error al obtener sesiones por mes: " + e.getMessage());
        }
        return sesiones;
    }
}
