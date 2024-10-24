package pe.edu.utp.aplicacion;
import pe.edu.utp.modelos.Cliente;
import pe.edu.utp.modelos.Mesa;
import pe.edu.utp.modelos.Pedido;
import pe.edu.utp.modelos.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainConsola {
    private static List<Mesa> mesas = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static int mesaCounter = 1;
    private static int clienteCounter = 1;
    private static int reservaCounter = 1;

    public static void main(String[] args) {
        inicializarMesas();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Hacer reserva");
            System.out.println("2. Atender sin reserva");
            System.out.println("3. Salir");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    hacerReserva(scanner);
                    break;
                case 2:
                    atenderSinReserva(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void inicializarMesas() {
        mesas.add(new Mesa(mesaCounter++, 2, true));
        mesas.add(new Mesa(mesaCounter++, 4, true));
        mesas.add(new Mesa(mesaCounter++, 6, true));
    }

    private static void hacerReserva(Scanner scanner) {
        System.out.println("Crear nuevo cliente:");
        Cliente cliente = crearCliente(scanner);

        System.out.println("Ingrese la fecha (formato: YYYY-MM-DD):");
        String fecha = scanner.next();
        System.out.println("Ingrese la hora (formato: HH:MM):");
        String hora = scanner.next();
        System.out.println("Ingrese la cantidad de personas:");
        int cantidad = scanner.nextInt();

        Mesa mesaSeleccionada = seleccionarMesa(cantidad);
        if (mesaSeleccionada != null) {
            Reserva reserva = new Reserva(reservaCounter++, cliente.getId_cliente(), 0, new Date(), mesaSeleccionada.getId_mesa());
            reservas.add(reserva);
            System.out.println("Reserva confirmada para " + cantidad + " personas en la mesa " + mesaSeleccionada.getId_mesa());
        } else {
            System.out.println("No hay mesas disponibles para esa cantidad de personas.");
        }
    }

    private static Cliente crearCliente(Scanner scanner) {
        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.next();
        System.out.println("Ingrese el apellido del cliente:");
        String apellido = scanner.next();
        System.out.println("Ingrese el teléfono del cliente:");
        String telefono = scanner.next();
        System.out.println("Ingrese el email del cliente:");
        String email = scanner.next();

        Cliente cliente = new Cliente(clienteCounter++, nombre, apellido, telefono, email);
        clientes.add(cliente);
        System.out.println("Cliente creado: " + cliente.getNombre() + " " + cliente.getApellido());
        return cliente;
    }

    private static void atenderSinReserva(Scanner scanner) {
        System.out.println("Crear nuevo cliente:");
        Cliente cliente = crearCliente(scanner);

        System.out.println("Ingrese la cantidad de personas:");
        int cantidad = scanner.nextInt();

        Mesa mesaSeleccionada = seleccionarMesa(cantidad);
        if (mesaSeleccionada != null) {
            System.out.println("Mesa " + mesaSeleccionada.getId_mesa() + " asignada.");
            System.out.println("¿Qué desea ordenar? (escriba 'salir' para finalizar)");
            List<String> orden = new ArrayList<>();
            while (true) {
                String plato = scanner.next();
                if (plato.equalsIgnoreCase("salir")) {
                    break;
                }
                orden.add(plato);
            }
            Pedido pedido = new Pedido(pedidos.size() + 1, 0, 0, mesaSeleccionada.getId_mesa(), true); // Estado en true para indicar que está activo
            pedidos.add(pedido);
            System.out.println("Pedido registrado: " + orden);
        } else {
            System.out.println("No hay mesas disponibles para esa cantidad de personas.");
        }
    }

    private static Mesa seleccionarMesa(int cantidad) {
        for (Mesa mesa : mesas) {
            if (mesa.getEstado() && mesa.getCapacidad() >= cantidad) {
                mesa.setEstado(false); // Cambia el estado de la mesa a ocupada
                return mesa;
            }
        }
        return null;
    }
}
