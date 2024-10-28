package pe.edu.utp.modelos;

/**
 * Clase que representa a un empleado, extendiendo la clase Usuario.
 */
public class Empleado extends Usuario {

    /**
     * Constructor de la clase Empleado.
     *
     * @param id_usuario Identificador del usuario.
     * @param nombre Nombre del empleado.
     * @param apellido Apellido del empleado.
     * @param nombreUsuario Nombre de usuario.
     * @param contrasena Contraseña del usuario.
     * @param cargo Cargo del empleado.
     * @param actividad Estado de actividad del empleado.
     */
    public Empleado(int id_usuario, String nombre, String apellido,
                    String nombreUsuario, String contrasena,
                    String cargo, Boolean actividad) {
        super(id_usuario, nombre, apellido, nombreUsuario, contrasena, cargo, actividad);
        validarCargo(cargo);
    }

    /**
     * Método para validar que el cargo no sea "administrador".
     *
     * @param cargo Cargo a validar.
     * @throws IllegalArgumentException Si el cargo es "administrador".
     */
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
}
