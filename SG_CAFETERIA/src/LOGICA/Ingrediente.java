package LOGICA;

public class Ingrediente {
    private int idIngrediente;
    private String nombre;
    private double stockActual;
    private double stockMinimo;
    private String unidad;
    private double precioUnitario;

    public Ingrediente() {}

    public Ingrediente(int idIngrediente, String nombre, double stockActual, 
                       double stockMinimo, String unidad, double precioUnitario) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidad = unidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public int getIdIngrediente() { return idIngrediente; }
    public void setIdIngrediente(int idIngrediente) { this.idIngrediente = idIngrediente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getStockActual() { return stockActual; }
    public void setStockActual(double stockActual) { this.stockActual = stockActual; }

    public double getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(double stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public boolean estaPorAgotarse() {
        return stockActual <= stockMinimo;
    }

    @Override
    public String toString() {
        return nombre + " (" + stockActual + " " + unidad + ")";
    }
}
