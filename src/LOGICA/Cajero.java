package LOGICA;

public class Cajero extends Empleado {
    private int idCajero;
    private String turno;
    private String cajaAsignada;

    public Cajero(int idCajero, Empleado empleado, String turno, String cajaAsignada) {
        super(empleado.getIdEmpleado(), empleado.getPersona(), empleado.getCargo(), empleado.getCodigoTrabajador());
        this.idCajero = idCajero;
        this.turno = turno;
        this.cajaAsignada = cajaAsignada;
    }

    // Getters & Setters
    public int getIdCajero() { return idCajero; }
    public void setIdCajero(int idCajero) { this.idCajero = idCajero; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getCajaAsignada() { return cajaAsignada; }
    public void setCajaAsignada(String cajaAsignada) { this.cajaAsignada = cajaAsignada; }

    @Override
    public String toString() {
        return super.toString() + " - Turno: " + turno + " - Caja: " + cajaAsignada;
    }
}
