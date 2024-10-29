package pe.edu.utp.modelos;

public class Cliente {
    private int id_cliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private int puntos;  // Nuevo campo de puntos de fidelización

    // Constructor vacío
    public Cliente(int i, String nombre, String apellido, String telefono, String email) {
    }

    // Constructor sin puntos (para compatibilidad con otros sistemas)
    public Cliente(String nombre, String apellido, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    // Constructor completo, incluyendo el campo de puntos
    public Cliente(int id_cliente, String nombre, String apellido, String telefono, String email, int puntos) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.puntos = puntos;
    }

    // Getters
    public int getId_cliente() {
        return id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getPuntos() {  // Getter para puntos
        return puntos;
    }

    // Setters
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPuntos(int puntos) {  // Setter para puntos
        this.puntos = puntos;
    }

    // Método toString para imprimir el cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
