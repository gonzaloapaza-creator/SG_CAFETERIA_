package LOGICA;

public class Cliente {
    private int idCliente;
    private Persona persona;

    public Cliente() {}

    public Cliente(int idCliente, Persona persona) {
        this.idCliente = idCliente;
        this.persona = persona;
    }

    public Cliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Getters & Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    @Override
    public String toString() {
        return persona != null ? persona.toString() : "Cliente #" + idCliente;
    }
}
