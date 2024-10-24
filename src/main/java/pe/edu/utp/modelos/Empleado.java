package pe.edu.utp.modelos;

public class Empleado extends Usuario {
    public Empleado(int id_usuario, String nombre, String apellido, String nombreUsuario, String contrasena, String cargo, Boolean actividad) {
        super(id_usuario, nombre, apellido, nombreUsuario, contrasena, cargo, actividad);
        validarCargo(cargo);
    }

    // Método para validar que el cargo no sea "administrador"
    private void validarCargo(String cargo) {
        if ("administrador".equalsIgnoreCase(cargo)) {
            throw new IllegalArgumentException("El cargo no puede ser 'administrador'.");
        }
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s - %s (%s)",
                getId_usuario(),
                getNombre(),
                getApellido(),
                getNombreUsuario(),
                getCargo());
    }

    // Métodos adicionales específicos para el empleado si son necesarios
}
