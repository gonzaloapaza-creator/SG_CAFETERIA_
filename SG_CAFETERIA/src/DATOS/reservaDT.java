package DATOS;

import LOGICA.Reserva;
import LOGICA.Cliente;
import LOGICA.Mesa;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class reservaDT {

    // Agregar reserva
    public static boolean agregarReserva(Reserva r) {
        String sql = "INSERT INTO reserva (id_cliente, id_empleado, id_mesa, fecha, hora, personas) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getCliente().getIdCliente());
            if (r.getIdEmpleado() > 0) {
                ps.setInt(2, r.getIdEmpleado());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, r.getMesa().getIdMesa());
            ps.setDate(4, Date.valueOf(r.getFecha()));
            ps.setTime(5, Time.valueOf(r.getHora()));
            ps.setInt(6, r.getPersonas());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar reserva: " + e.getMessage());
            return false;
        }
    }

    // Listar reservas
    public static List<Reserva> listarReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT id_reserva, id_cliente, id_empleado, id_mesa, fecha, hora, personas FROM reserva";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva r = new Reserva(
                    rs.getInt("id_reserva"),
                    new Cliente(rs.getInt("id_cliente")),
                    new Mesa(rs.getInt("id_mesa")),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getInt("personas"),
                    rs.getInt("id_empleado")
                );
                lista.add(r);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar reservas: " + e.getMessage());
        }
        return lista;
    }

    // NUEVO: Obtener mesa más frecuente de un cliente
    public static int obtenerMesaHabitual(int idCliente) {
        String sql = "SELECT id_mesa, COUNT(*) AS veces " +
                     "FROM reserva " +
                     "WHERE id_cliente = ? " +
                     "GROUP BY id_mesa " +
                     "ORDER BY veces DESC " +
                     "LIMIT 1";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_mesa");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener mesa habitual: " + e.getMessage());
        }
        return 0;
    }

 // NUEVO: Verificar si ya existe una reserva en una mesa, fecha y hora específicas
    public static boolean existeReserva(int idMesa, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM reserva " +
                     "WHERE id_mesa = ? AND fecha = ? AND hora = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMesa);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(hora));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0; // True si existe 1 o más reservas
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar reserva existente: " + e.getMessage());
        }

        return false;
    }

    
    // NUEVO: Obtener número de reservas de un cliente
    public static int contarReservasCliente(int idCliente) {
        String sql = "SELECT COUNT(*) AS total FROM reserva WHERE id_cliente = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error al contar reservas: " + e.getMessage());
        }
        return 0;
    }
}
