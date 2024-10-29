package pe.edu.utp.dao;

import javafx.collections.ObservableList;
import pe.edu.utp.modelos.PedidoCocinaDTO;


public interface PedidoCocinaDAO {
    ObservableList<PedidoCocinaDTO> obtenerPedidos();
}
