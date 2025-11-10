package LOGICA;

import java.util.ArrayList;
import java.util.List;

public class Caja {
    private double saldoInicial;
    private List<Pedido> ventas;

    public Caja(double saldoInicial) {
        this.saldoInicial = saldoInicial;
        this.ventas = new ArrayList<>();
    }

    // Registrar una venta
    public void registrarVenta(Pedido pedido) {
        ventas.add(pedido);
    }

    // Calcular saldo
    public double obtenerSaldoActual() {
        double totalVentas = 0;
        for (Pedido p : ventas) {
            totalVentas += p.getTotal();
        }
        return saldoInicial + totalVentas;
    }

    // Getters
    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }

    public List<Pedido> getVentas() { return ventas; }

    @Override
    public String toString() {
        return "Caja [" +
               "Saldo Inicial: $" + String.format("%.2f", saldoInicial) +
               ", Ventas Realizadas: " + ventas.size() +
               ", Saldo Actual: $" + String.format("%.2f", obtenerSaldoActual()) +
               "]";
    }
}
