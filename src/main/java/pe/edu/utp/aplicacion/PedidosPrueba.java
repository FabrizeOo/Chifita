package pe.edu.utp.aplicacion;

import pe.edu.utp.dao.impl.DetallePedidoDAOImpl;
import pe.edu.utp.dao.impl.PedidoDAOImpl;
import pe.edu.utp.modelos.Pedido;
import pe.edu.utp.modelos.DetallePedido; // Asegúrate que esta clase tenga el nombre correcto.
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;

public class PedidosPrueba {
    public static void main(String[] args) {
        // Crear conexión
        Conexionsecu conexion = new Conexionsecu();

        // Crear DAO
        PedidoDAOImpl pedidoDAO = new PedidoDAOImpl(conexion);
        DetallePedidoDAOImpl detallePedidoDAO = new DetallePedidoDAOImpl(conexion);

        // Valores de ejemplo
        int idUsuario = 3; // ID del usuario
        int idMesa = 5; // ID de la mesa (ajusta según sea necesario)

        // Crear un nuevo pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setId_usuario(idUsuario);
        nuevoPedido.setId_mesa(idMesa);
        nuevoPedido.setEstado(false); // Estado inicial como false (no entregado)

        // Crear el pedido en la base de datos
        pedidoDAO.crearPedido(nuevoPedido);
        System.out.println("Pedido creado con ID: " + nuevoPedido.getId_pedido());

        // Verifica que el ID del pedido se haya asignado
        if (nuevoPedido.getId_pedido() == 0) {
            System.err.println("Error: El ID del pedido no se ha generado correctamente.");
            return;
        }

        // Agregar detalles al pedido
        DetallePedido detalle1 = new DetallePedido(nuevoPedido.getId_pedido(), 1, 2); // Producto ID 1, Cantidad 2
        DetallePedido detalle2 = new DetallePedido(nuevoPedido.getId_pedido(), 2, 1); // Producto ID 2, Cantidad 1

        detallePedidoDAO.crearDetallePedido(detalle1);
        detallePedidoDAO.crearDetallePedido(detalle2);

        System.out.println("Detalles del pedido agregados.");

        // Leer todos los detalles del pedido
        List<DetallePedido> detalles = detallePedidoDAO.leerDetallesPorPedido(nuevoPedido.getId_pedido());
        System.out.println("Detalles del pedido:");
        for (DetallePedido detalle : detalles) {
            System.out.println("ID Producto: " + detalle.getIdProducto() + ", Cantidad: " + detalle.getCantidad());
        }
    }
}
