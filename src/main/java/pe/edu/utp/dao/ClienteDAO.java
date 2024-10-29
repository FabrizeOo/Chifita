package pe.edu.utp.dao;

import pe.edu.utp.modelos.Cliente;
import java.util.List;

public interface ClienteDAO {
        // Crear un nuevo cliente
        void crearCliente(Cliente cliente);

        // Leer un empleado por ID
        Cliente leerCliente(int id);

        // Leer todos los empleados
        List<Cliente> leerTodosCliente();

        // Actualizar un cliente
        void actualizarCliente(Cliente cliente);

        // Eliminar un cliente
        void eliminarCliente(int id);

        // Actualizar los puntos de un cliente
        boolean actualizarPuntosCliente(int idCliente, int puntos); // Método nuevo

        // Leer cliente por nombre o email
        Cliente leerClientePorNombre(String nombre);

        boolean eliminarClientePorId(int idCliente);

}

