package pe.edu.utp.modelos.hijos;

import pe.edu.utp.modelos.Producto;

class Guarnicion extends Producto {
    private String id_guarnicion;

    public Guarnicion(int id) {
        super(id, "Guarnicion", 0.0);
        this.id_guarnicion = "id_guarnicion_" + id;
    }

    public String getIdGuarnicion() {
        return id_guarnicion;
    }

    @Override
    public String toString() {
        return "ID: " + id_guarnicion + " | " + getNombre() + " - $" + String.format("%.2f", getPrecio());
    }
}