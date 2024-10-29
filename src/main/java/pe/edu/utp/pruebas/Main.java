package pe.edu.utp.pruebas;

import pe.edu.utp.dao.impl.EmpleadoDAOImpl;
import pe.edu.utp.modelos.Empleado;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear instancia de Conexionsecu
        Conexionsecu conexion = new Conexionsecu();
        EmpleadoDAOImpl empleadoDAO = new EmpleadoDAOImpl(conexion);
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Inicializar la lista de empleados
        List<Empleado> empleados = empleadoDAO.leerTodosEmpleados();

        do {
            System.out.println("\n--- Menú de Empleados ---");
            System.out.println("1. Listar todos los empleados");
            System.out.println("2. Agregar un empleado");
            System.out.println("3. Editar un empleado");
            System.out.println("4. Eliminar un empleado");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Listar todos los empleados
                    empleados = empleadoDAO.leerTodosEmpleados(); // Actualizar la lista
                    System.out.println("Lista de empleados:");
                    for (Empleado emp : empleados) {
                        System.out.println(emp.getId_usuario() + ": " + emp.getNombre() + " " + emp.getApellido());
                    }
                    break;

                case 2:
                    // Agregar un empleado
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Nombre de usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    System.out.print("Cargo: ");
                    String cargo = scanner.nextLine();
                    System.out.print("Actividad (true/false): ");
                    boolean actividad = scanner.nextBoolean();

                    int nuevoId = empleados.size() + 1; // ID simple, se debe mejorar en producción
                    Empleado nuevoEmpleado = new Empleado(nuevoId, nombre, apellido, nombreUsuario, contrasena, cargo, actividad);
                    empleadoDAO.crearEmpleado(nuevoEmpleado);
                    System.out.println("Empleado agregado: " + nuevoEmpleado.getNombre());
                    break;

                case 3:
                    // Editar un empleado
                    System.out.print("Ingrese el ID del empleado a editar: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Empleado empleadoEditar = empleadoDAO.leerEmpleado(idEditar);
                    if (empleadoEditar != null) {
                        System.out.print("Nuevo nombre (" + empleadoEditar.getNombre() + "): ");
                        String nuevoNombre = scanner.nextLine();
                        if (!nuevoNombre.isEmpty()) empleadoEditar.setNombre(nuevoNombre);

                        System.out.print("Nuevo apellido (" + empleadoEditar.getApellido() + "): ");
                        String nuevoApellido = scanner.nextLine();
                        if (!nuevoApellido.isEmpty()) empleadoEditar.setApellido(nuevoApellido);

                        System.out.print("Nuevo nombre de usuario (" + empleadoEditar.getNombreUsuario() + "): ");
                        String nuevoNombreUsuario = scanner.nextLine();
                        if (!nuevoNombreUsuario.isEmpty()) empleadoEditar.setNombreUsuario(nuevoNombreUsuario);

                        System.out.print("Nueva actividad (true/false): ");
                        boolean nuevaActividad = scanner.nextBoolean();
                        empleadoEditar.setActividad(nuevaActividad);

                        empleadoDAO.actualizarEmpleado(empleadoEditar);
                        System.out.println("Empleado actualizado.");
                    } else {
                        System.out.println("Empleado no encontrado.");
                    }
                    break;

                case 4:
                    // Eliminar un empleado
                    System.out.print("Ingrese el ID del empleado a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    empleadoDAO.eliminarEmpleado(idEliminar);
                    System.out.println("Empleado eliminado.");
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}
