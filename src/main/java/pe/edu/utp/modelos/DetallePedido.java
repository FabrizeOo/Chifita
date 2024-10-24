package pe.edu.utp.modelos;
//table=detalle_pedidos
/*
CREATE TABLE detalle_pedidos (
    id_detallepedido INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
 */
public class DetallePedido {
    private int idPedido;
    private int idProducto;
    private int cantidad;
    // Constructor vacío
    public DetallePedido() {}

    // Constructor con parámetros
    public DetallePedido(int idPedido, int idProducto, int cantidad) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public int getIdPedido() {

        return idPedido;
    }

    public void setIdPedido(int idPedido) {

        this.idPedido = idPedido;
    }

    public int getIdProducto() {

        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

