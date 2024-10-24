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
    }
