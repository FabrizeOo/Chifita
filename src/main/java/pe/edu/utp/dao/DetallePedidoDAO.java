package pe.edu.utp.dao;

import pe.edu.utp.modelos.DetallePedido;

import java.util.List;

public interface DetallePedidoDAO {
    void crearDetallePedido(DetallePedido detalle);
    List<DetallePedido> leerDetallesPorPedido(int idPedido);
    void eliminarDetallePedido(int idDetalle);
}
