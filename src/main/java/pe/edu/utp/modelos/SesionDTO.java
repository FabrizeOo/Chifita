package pe.edu.utp.modelos;

public class SesionDTO {
    private int totalSesiones;   // Total de sesiones del usuario en un mes
    private int totalDuracion;    // Duración total en segundos de las sesiones

    // Constructor
    public SesionDTO(int totalSesiones, int totalDuracion) {
        this.totalSesiones = totalSesiones;
        this.totalDuracion = totalDuracion;
    }

    // Getters y Setters
    public int getTotalSesiones() {
        return totalSesiones;
    }

    public void setTotalSesiones(int totalSesiones) {
        this.totalSesiones = totalSesiones;
    }

    public int getTotalDuracion() {
        return totalDuracion;
    }

    public void setTotalDuracion(int totalDuracion) {
        this.totalDuracion = totalDuracion;
    }
}
