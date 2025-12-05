package LOGICA;

public class Mesa {
    private int idMesa;
    private int numeroMesa;
    private int capacidad;

    public Mesa() {}

    public Mesa(int idMesa, int numeroMesa, int capacidad) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
    }

    public Mesa(int idMesa) {
        this.idMesa = idMesa;
    }

    // Getters & Setters
    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Override
    public String toString() {
        return "Mesa " + numeroMesa + " (Capacidad: " + capacidad + ")";
    }
}
