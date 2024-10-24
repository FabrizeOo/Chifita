package pe.edu.utp.dao;

import pe.edu.utp.modelos.Pedido;
import java.util.List;

public interface PedidoDAO {
    int crearPedido(Pedido pedido);
    Pedido leerPedido(int id);
    List<Pedido> leerTodosPedidos();
    List<Pedido> leerPedidosPendientes(); // Nuevo método
    void actualizarPedido(Pedido pedido);
    void eliminarPedido(int id);
}
