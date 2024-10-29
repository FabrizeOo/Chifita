package pe.edu.utp.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexionsecu {

    private String url = "jdbc:mysql://localhost:3306/chifa2";
    private String user = "root";
    private String pass = "";

    public Conexionsecu() {

    }

    public Connection conectar() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
            return null;
        }
    }

    public void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al desconectar: " + e.getMessage());
            }
        }
    }
}
