package LOGICA;

public class Empleado {
    private int idEmpleado;
    private Persona persona;
    private String cargo;
    private String codigoTrabajador;

    public Empleado() {}

    public Empleado(int idEmpleado, Persona persona, String cargo, String codigoTrabajador) {
        this.idEmpleado = idEmpleado;
        this.persona = persona;
        this.cargo = cargo;
        this.codigoTrabajador = codigoTrabajador;
    }

    // Getters & Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getCodigoTrabajador() { return codigoTrabajador; }
    public void setCodigoTrabajador(String codigoTrabajador) { this.codigoTrabajador = codigoTrabajador; }

    @Override
    public String toString() {
        return persona.getNombre() + " - " + cargo + " (" + codigoTrabajador + ")";
    }
}
