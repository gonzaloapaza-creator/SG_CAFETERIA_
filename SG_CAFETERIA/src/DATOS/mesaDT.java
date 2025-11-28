package DATOS;
import LOGICA.Mesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mesaDT {

    // Obtener todas las mesas
    public static List<Mesa> obtenerMesas() {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT id_mesa, numero_mesa, capacidad FROM mesa ORDER BY numero_mesa";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mesa mesa = new Mesa(
                    rs.getInt("id_mesa"),
                    rs.getInt("numero_mesa"),
                    rs.getInt("capacidad")
                );
                lista.add(mesa);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar mesas: " + e.getMessage());
        }
        return lista;
    }

    // Agregar mesa
    public static boolean agregarMesa(Mesa m) {
        String sql = "INSERT INTO mesa (numero_mesa, capacidad) VALUES (?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getNumeroMesa());
            ps.setInt(2, m.getCapacidad());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar mesa: " + e.getMessage());
            return false;
        }
    }
}
