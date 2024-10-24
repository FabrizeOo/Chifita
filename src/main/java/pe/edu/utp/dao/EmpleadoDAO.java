package pe.edu.utp.dao;

import pe.edu.utp.modelos.Empleado;

import java.util.List;

public interface EmpleadoDAO {
    // Crear un nuevo empleado
    void crearEmpleado(Empleado empleado);

    // Leer un empleado por ID
    Empleado leerEmpleado(int id);

    // Leer todos los empleados
    List<Empleado> leerTodosEmpleados();

    // Actualizar un empleado
    void actualizarEmpleado(Empleado empleado);

    // Eliminar un empleado
    void eliminarEmpleado(int id);
}

