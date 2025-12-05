package LOGICA;

public class DetallePedido {
    private int idDetalle;
    private Pedido pedido;
    private Plato plato;
    private int cantidad;
    private double subtotal;

    public DetallePedido() {}

    public DetallePedido(int idDetalle, Pedido pedido, Plato plato, int cantidad) {
        this.idDetalle = idDetalle;
        this.pedido = pedido;
        this.plato = plato;
        this.cantidad = cantidad;
        this.subtotal = cantidad * plato.getPrecio();
    }

    // Getters & Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Plato getPlato() { return plato; }
    public void setPlato(Plato plato) { this.plato = plato; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return plato.getNombre() + " x" + cantidad + " = $" + String.format("%.2f", subtotal);
    }
}
