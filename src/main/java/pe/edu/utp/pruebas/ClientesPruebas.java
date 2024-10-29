package pe.edu.utp.pruebas;

import pe.edu.utp.dao.impl.ClienteDAOImpl;
import pe.edu.utp.modelos.Cliente;
import pe.edu.utp.utilidades.Conexionsecu;

import java.util.List;
import java.util.Scanner;

public class    ClientesPruebas {
    private static ClienteDAOImpl clienteDAO;

    public static void main(String[] args) {
        // Crear conexión
        Conexionsecu conexion = new Conexionsecu();
        clienteDAO = new ClienteDAOImpl(conexion);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Selecciona una opción:");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Leer Cliente");
            System.out.println("3. Leer Todos los Clientes");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    crearCliente(scanner);
                    break;
                case 2:
                    leerCliente(scanner);
                    break;
                case 3:
                    leerTodosClientes();
                    break;
                case 4:
                    actualizarCliente(scanner);
                    break;
                case 5:
                    eliminarCliente(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void crearCliente(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, apellido, telefono, email);
        clienteDAO.crearCliente(nuevoCliente);
        System.out.println("Cliente creado exitosamente.");
    }

    private static void leerCliente(Scanner scanner) {
        System.out.print("ID del cliente a leer: ");
        int id = scanner.nextInt();
        Cliente cliente = clienteDAO.leerCliente(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void leerTodosClientes() {
        List<Cliente> clientes = clienteDAO.leerTodosCliente();
        System.out.println("Lista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void actualizarCliente(Scanner scanner) {
        System.out.print("ID del cliente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Cliente clienteExistente = clienteDAO.leerCliente(id);
        if (clienteExistente != null) {
            System.out.print("Nuevo Nombre (" + clienteExistente.getNombre() + "): ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo Apellido (" + clienteExistente.getApellido() + "): ");
            String apellido = scanner.nextLine();
            System.out.print("Nuevo Teléfono (" + clienteExistente.getTelefono() + "): ");
            String telefono = scanner.nextLine();
            System.out.print("Nuevo Email (" + clienteExistente.getEmail() + "): ");
            String email = scanner.nextLine();

            // Actualizar cliente
            clienteExistente.setNombre(nombre);
            clienteExistente.setApellido(apellido);
            clienteExistente.setTelefono(telefono);
            clienteExistente.setEmail(email);
            clienteDAO.actualizarCliente(clienteExistente);
            System.out.println("Cliente actualizado exitosamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void eliminarCliente(Scanner scanner) {
        System.out.print("ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        clienteDAO.eliminarCliente(id);
        System.out.println("Cliente eliminado.");
    }
}
