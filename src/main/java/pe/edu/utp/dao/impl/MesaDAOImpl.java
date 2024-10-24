package pe.edu.utp.dao.impl;

import pe.edu.utp.dao.MesaDAO;
import pe.edu.utp.modelos.Mesa;
import pe.edu.utp.utilidades.Conexionsecu; // Asegúrate de importar la clase de conexión

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDAOImpl implements MesaDAO {

    private Conexionsecu conexion; // Instancia de la clase de conexión

    public MesaDAOImpl(Conexionsecu conexionsecu) {
        this.conexion = new Conexionsecu(); // Inicializa tu conexión
    }

    @Override
    public List<Mesa> obtenerMesasLibres(Boolean estado) {
        List<Mesa> mesasLibres = new ArrayList<>();
        String sql = "SELECT id_mesa, capacidad, estado FROM mesas"; // Cambia a obtener todas las mesas

        try (Connection conn = conexion.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idMesa = resultSet.getInt("id_mesa");
                int capacidad = resultSet.getInt("capacidad");
                Boolean estadoMesa = resultSet.getBoolean("estado");

                // Verifica si el estadoMesa es realmente un booleano antes de agregar
                if (estadoMesa != null) {
                    mesasLibres.add(new Mesa(idMesa, capacidad, estadoMesa));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de errores
        }

        return mesasLibres;
    }

    @Override
    public void actualizarEstadoMesa(int idMesa, boolean estado) {
        String sql = "UPDATE mesas SET estado = ? WHERE id_mesa = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, estado); // true para bloqueada, false para disponible
            stmt.setInt(2, idMesa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
