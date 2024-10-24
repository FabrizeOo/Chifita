package pe.edu.utp.modelos;
//table=pedidos
/*
CREATE TABLE pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_mesa INT NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
);
 */
public class Pedido {
    private int id_pedido;
    private int id_usuario;
    private int id_detallePedido;
    private int id_mesa;
    private boolean estado;

    // Constructor vacío
    public Pedido() {
    }

    // Constructor con parámetros
    public Pedido(int id_pedido, int id_usuario, int id_detallePedido, int id_mesa, boolean estado) {
        this.id_pedido = id_pedido;
        this.id_usuario = id_usuario;
        this.id_detallePedido = id_detallePedido;
        this.id_mesa = id_mesa;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_detallePedido() {
        return id_detallePedido;
    }

    public void setId_detallePedido(int id_detallePedido) {
        this.id_detallePedido = id_detallePedido;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}