package DATOS;

import LOGICA.Ingrediente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class inventarioDT {

    public static List<Ingrediente> listarIngredientes() {
        List<Ingrediente> lista = new ArrayList<>();
        String sql = "SELECT * FROM ingrediente WHERE activo = TRUE ORDER BY nombre";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ingrediente ing = new Ingrediente(
                    rs.getInt("id_ingrediente"),
                    rs.getString("nombre"),
                    rs.getDouble("stock_actual"),
                    rs.getDouble("stock_minimo"),
                    rs.getString("unidad"),
                    rs.getDouble("precio_unitario")
                );
                lista.add(ing);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar ingredientes: " + e.getMessage());
        }
        return lista;
    }

    public static List<Ingrediente> obtenerIngredientesPorAgotarse() {
        List<Ingrediente> alertas = new ArrayList<>();
        String sql = "SELECT * FROM ingrediente WHERE activo = TRUE AND stock_actual <= stock_minimo ORDER BY stock_actual";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ingrediente ing = new Ingrediente(
                    rs.getInt("id_ingrediente"),
                    rs.getString("nombre"),
                    rs.getDouble("stock_actual"),
                    rs.getDouble("stock_minimo"),
                    rs.getString("unidad"),
                    rs.getDouble("precio_unitario")
                );
                alertas.add(ing);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alertas: " + e.getMessage());
        }
        return alertas;
    }

    public static void descontarIngredientes(int idPlato, int cantidadVendida) {
        String sql = "UPDATE ingrediente i " +
                     "INNER JOIN ingrediente_plato ip ON i.id_ingrediente = ip.id_ingrediente " +
                     "SET i.stock_actual = i.stock_actual - (? * ip.cantidad) " +
                     "WHERE ip.id_plato = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idPlato);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al descontar stock: " + e.getMessage());
        }
    }

    public static boolean agregarIngrediente(Ingrediente ing) {
        String sql = "INSERT INTO ingrediente (nombre, stock_actual, stock_minimo, unidad, precio_unitario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ing.getNombre());
            ps.setDouble(2, ing.getStockActual());
            ps.setDouble(3, ing.getStockMinimo());
            ps.setString(4, ing.getUnidad());
            ps.setDouble(5, ing.getPrecioUnitario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar ingrediente: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarStock(int idIngrediente, double nuevoStock) {
        String sql = "UPDATE ingrediente SET stock_actual = ? WHERE id_ingrediente = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoStock);
            ps.setInt(2, idIngrediente);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}
