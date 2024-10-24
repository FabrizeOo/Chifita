package pe.edu.utp.modelos.hijos;

import pe.edu.utp.modelos.Producto;

class Bebida extends Producto {
    private String id_bebida;

    public Bebida(int id) {
        super(id, "Bebida", 0.0);
        this.id_bebida = "id_bebida_" + id;
    }

    public String getIdBebida() {
        return id_bebida;
    }

    @Override
    public String toString() {
        return "ID: " + id_bebida + " | " + getNombre() + " - $" + String.format("%.2f", getPrecio());
    }
}
