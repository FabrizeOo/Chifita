package pe.edu.utp.modelos.hijos;

import pe.edu.utp.modelos.Producto;

class Plato extends Producto {
    private String id_plato;

    public Plato(int id) {
        super(id, "Plato", 0.0);
        this.id_plato = "id_plato_" + id;
    }

    public String getIdPlato() {
        return id_plato;
    }

    @Override
    public String toString() {
        return "ID: " + id_plato + " | " + getNombre() + " - $" + String.format("%.2f", getPrecio());
    }
}