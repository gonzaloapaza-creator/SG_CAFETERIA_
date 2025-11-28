package LOGICA;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int idReserva;
    private Cliente cliente;
    private Mesa mesa;
    private LocalDate fecha;
    private LocalTime hora;
    private int personas;
    private int idEmpleado;          // ✅ AGREGAR ESTE ATRIBUTO
    private String estado;
    private String motivo_especial;
    private String notas;

    // Constructores
    public Reserva() {}

    public Reserva(int idReserva, Cliente cliente, Mesa mesa, LocalDate fecha, 
                   LocalTime hora, int personas, int idEmpleado) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.personas = personas;
        this.idEmpleado = idEmpleado;     // ✅ INICIALIZAR
    }

    public Reserva(int idReserva, Cliente cliente, Mesa mesa, LocalDate fecha, 
                   LocalTime hora, int personas, String estado, String motivo_especial, String notas) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.personas = personas;
        this.estado = estado;
        this.motivo_especial = motivo_especial;
        this.notas = notas;
    }

    // ============================================
    // GETTERS
    // ============================================
    
    public int getIdReserva() { return idReserva; }
    public Cliente getCliente() { return cliente; }
    public Mesa getMesa() { return mesa; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public int getPersonas() { return personas; }
    public int getIdEmpleado() { return idEmpleado; }  // ✅ NUEVO GETTER
    public String getEstado() { return estado; }
    public String getMotivo_especial() { return motivo_especial; }
    public String getNotas() { return notas; }

    // ============================================
    // SETTERS
    // ============================================
    
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setPersonas(int personas) { this.personas = personas; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }  // ✅ NUEVO SETTER
    public void setEstado(String estado) { this.estado = estado; }
    public void setMotivo_especial(String motivo_especial) { this.motivo_especial = motivo_especial; }
    public void setNotas(String notas) { this.notas = notas; }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", personas=" + personas +
                ", estado='" + estado + '\'' +
                '}';
    }
}
