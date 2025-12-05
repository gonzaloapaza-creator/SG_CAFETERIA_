package LOGICA;

import java.sql.Timestamp;

public class Factura {
    private int idFactura;
    private int idPedido;
    private int idCliente;
    private String numeroFactura;
    private double subtotal;
    private double impuesto;
    private double descuento;
    private double total;
    private String metodoPago;
    private String estado;
    private Timestamp fechaHora;

    public Factura() {}

    public Factura(int idFactura, int idPedido, int idCliente, String numeroFactura, 
                   double subtotal, double impuesto, double descuento, double total, 
                   String metodoPago, String estado, Timestamp fechaHora) {
        this.idFactura = idFactura;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.numeroFactura = numeroFactura;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = total;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getImpuesto() { return impuesto; }
    public void setImpuesto(double impuesto) { this.impuesto = impuesto; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }

    @Override
    public String toString() {
        return String.format("Factura %s | Total: $%.2f | Estado: %s", 
                           numeroFactura, total, estado);
    }
}
