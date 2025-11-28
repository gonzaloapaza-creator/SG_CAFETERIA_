package LOGICA;

public class Proveedor {
    private int idProveedor;
    private Persona persona;
    private String empresa;

    public Proveedor() {}

    public Proveedor(int idProveedor, Persona persona, String empresa) {
        this.idProveedor = idProveedor;
        this.persona = persona;
        this.empresa = empresa;
    }

    // Getters & Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    @Override
    public String toString() {
        return empresa + " - " + persona;
    }
}
