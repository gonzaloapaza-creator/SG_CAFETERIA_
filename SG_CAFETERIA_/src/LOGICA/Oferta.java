package LOGICA;

import java.time.LocalDate;

public class Oferta {

    private int idOferta;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo;
    private double porcentajeDescuento;
    private double montoFijo;
    private boolean activa;

    public Oferta() {}

    public Oferta(int idOferta, String nombre, String descripcion, LocalDate fechaInicio,
                  LocalDate fechaFin, String tipo, double porcentajeDescuento,
                  double montoFijo, boolean activa) {

        this.idOferta = idOferta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.montoFijo = montoFijo;
        this.activa = activa;
    }

    public int getIdOferta() { return idOferta; }
    public void setIdOferta(int idOferta) { this.idOferta = idOferta; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getMontoFijo() { return montoFijo; }
    public void setMontoFijo(double montoFijo) { this.montoFijo = montoFijo; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    @Override
    public String toString() {
        return String.format(
            "%s (%s) - %s al %s | Descuento: %.0f%%",
            nombre, tipo, fechaInicio, fechaFin, porcentajeDescuento
        );
    }
}
