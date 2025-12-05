package LOGICA;

public class Persona {
    private int idPersona;
    private String nombre;
    private String apellidos;
    private String ci;
    private int edad;

    public Persona() {}

    public Persona(int idPersona, String nombre, String apellidos, String ci, int edad) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.ci = ci;
        this.edad = edad;
    }

    // Getters & Setters
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + ci + ")";
    }
}
