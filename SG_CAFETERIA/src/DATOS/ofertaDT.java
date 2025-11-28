package DATOS;

import LOGICA.Oferta;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ofertaDT {

    // Obtener todas las ofertas activas
    public static List<Oferta> listarOfertas() {
        List<Oferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM oferta WHERE activa = TRUE ORDER BY fecha_inicio DESC";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Oferta oferta = new Oferta(
                    rs.getInt("id_oferta"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin").toLocalDate(),
                    rs.getString("tipo"),
                    rs.getDouble("porcentaje_descuento"),
                    rs.getDouble("monto_fijo"),
                    rs.getBoolean("activa")
                );
                lista.add(oferta);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar ofertas: " + e.getMessage());
        }
        return lista;
    }

    // Obtener oferta vigente por fecha (para días festivos)
    public static Oferta obtenerOfertaVigente(LocalDate fecha) {
        String sql = "SELECT * FROM oferta " +
                     "WHERE activa = TRUE AND ? BETWEEN fecha_inicio AND fecha_fin " +
                     "AND tipo IN ('Festivo', 'Descuento') " +
                     "ORDER BY porcentaje_descuento DESC LIMIT 1";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Oferta(
                    rs.getInt("id_oferta"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha_inicio").toLocalDate(),
                    rs.getDate("fecha_fin").toLocalDate(),
                    rs.getString("tipo"),
                    rs.getDouble("porcentaje_descuento"),
                    rs.getDouble("monto_fijo"),
                    rs.getBoolean("activa")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener oferta vigente: " + e.getMessage());
        }
        return null;
    }

    // Agregar nueva oferta
    public static boolean agregarOferta(Oferta oferta) {
        String sql = "INSERT INTO oferta (nombre, descripcion, fecha_inicio, fecha_fin, tipo, porcentaje_descuento, monto_fijo, activa) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, oferta.getNombre());
            ps.setString(2, oferta.getDescripcion());
            ps.setDate(3, Date.valueOf(oferta.getFechaInicio()));
            ps.setDate(4, Date.valueOf(oferta.getFechaFin()));
            ps.setString(5, oferta.getTipo());
            ps.setDouble(6, oferta.getPorcentajeDescuento());
            ps.setDouble(7, oferta.getMontoFijo());
            ps.setBoolean(8, oferta.isActiva());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar oferta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar oferta
    public static boolean eliminarOferta(int idOferta) {
        String sql = "DELETE FROM oferta WHERE id_oferta = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOferta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar oferta: " + e.getMessage());
            return false;
        }
    }
}
