package LOGICA;

public class Gerente extends Empleado {
    private int idGerente;
    private String areaResponsable;
    private double bonificacion;

    public Gerente(int idGerente, Empleado empleado, String areaResponsable, double bonificacion) {
        super(empleado.getIdEmpleado(), empleado.getPersona(), empleado.getCargo(), empleado.getCodigoTrabajador());
        this.idGerente = idGerente;
        this.areaResponsable = areaResponsable;
        this.bonificacion = bonificacion;
    }

    // Getters & Setters
    public int getIdGerente() { return idGerente; }
    public void setIdGerente(int idGerente) { this.idGerente = idGerente; }

    public String getAreaResponsable() { return areaResponsable; }
    public void setAreaResponsable(String areaResponsable) { this.areaResponsable = areaResponsable; }

    public double getBonificacion() { return bonificacion; }
    public void setBonificacion(double bonificacion) { this.bonificacion = bonificacion; }

    @Override
    public String toString() {
        return super.toString() + " | Área: " + areaResponsable + " | Bonificación: " + bonificacion;
    }
}
