package pe.edu.utp.modelos;

public class Mesa {
    private int id_mesa;
    private int capacidad;
    private Boolean estado;

    // Constructor
    public Mesa(int id_mesa, int capacidad, Boolean estado) {
        this.id_mesa = id_mesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters
    public int getId_mesa() {
        return id_mesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    // Setters
    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

