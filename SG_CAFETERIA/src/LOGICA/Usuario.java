package LOGICA;

public class Usuario {
    private int idUsuario;
    private String username;
    private String rol;
    private int idEmpleado;
    private boolean activo;

    public Usuario() {}

    public Usuario(int idUsuario, String username, String rol, int idEmpleado, boolean activo) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.rol = rol;
        this.idEmpleado = idEmpleado;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                '}';
    }
}
