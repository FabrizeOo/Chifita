package pe.edu.utp.modelos;

public class PedidoCocinaDTO {
    private int idPedido;

    private String descripcion; // nombre + precio del producto
    private int cantidad;
    private int idMesa;

    public PedidoCocinaDTO(int idPedido, String descripcion, int cantidad, int idMesa) {
        this.idPedido = idPedido;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.idMesa = idMesa;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }
}

