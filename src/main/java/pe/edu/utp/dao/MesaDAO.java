package pe.edu.utp.dao;

import pe.edu.utp.modelos.Mesa;

import java.util.List;

public interface MesaDAO {
    List<Mesa> obtenerMesasLibres(Boolean estado);
    void actualizarEstadoMesa(int idMesa, boolean estado);
}
