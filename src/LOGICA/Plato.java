package LOGICA;

public class Plato {
    private int idPlato;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public Plato() {
        this.disponible = true;
    }

    public Plato(int idPlato, String nombre, Categoria categoria, String descripcion, double precio, boolean disponible) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Getters & Setters
    public int getIdPlato() { return idPlato; }
    public void setIdPlato(int idPlato) { this.idPlato = idPlato; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return nombre + " | " + categoria.getNombre() + " | $" + precio +
               (disponible ? " ✅" : " ❌");
    }
}
