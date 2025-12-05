package DATOS;

import LOGICA.Oferta;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ofertaDT {

    public static List<Oferta> listarOfertas() {
        List<Oferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM oferta ORDER BY fecha_inicio DESC";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Oferta o = new Oferta(
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
                lista.add(o);
            }

        } catch (SQLException e) {
            System.err.println("Error listarOfertas: " + e.getMessage());
        }
        return lista;
    }

    public static Oferta obtenerOfertaVigente(LocalDate fecha) {
        String sql = "SELECT * FROM oferta WHERE activa = TRUE AND ? BETWEEN fecha_inicio AND fecha_fin " +
                     "AND tipo IN ('Festivo','Descuento') ORDER BY porcentaje_descuento DESC LIMIT 1";

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
            System.err.println("Error obtenerOfertaVigente: " + e.getMessage());
        }
        return null;
    }

    public static boolean agregarOferta(Oferta o) {
        String sql = "INSERT INTO oferta(nombre, descripcion, fecha_inicio, fecha_fin, tipo, porcentaje_descuento, monto_fijo, activa) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getNombre());
            ps.setString(2, o.getDescripcion());
            ps.setDate(3, Date.valueOf(o.getFechaInicio()));
            ps.setDate(4, Date.valueOf(o.getFechaFin()));
            ps.setString(5, o.getTipo());
            ps.setDouble(6, o.getPorcentajeDescuento());
            ps.setDouble(7, o.getMontoFijo());
            ps.setBoolean(8, o.isActiva());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error agregarOferta: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarOferta(Oferta o) {
        String sql = "UPDATE oferta SET nombre=?, descripcion=?, fecha_inicio=?, fecha_fin=?, tipo=?, porcentaje_descuento=?, monto_fijo=?, activa=? " +
                     "WHERE id_oferta=?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, o.getNombre());
            ps.setString(2, o.getDescripcion());
            ps.setDate(3, Date.valueOf(o.getFechaInicio()));
            ps.setDate(4, Date.valueOf(o.getFechaFin()));
            ps.setString(5, o.getTipo());
            ps.setDouble(6, o.getPorcentajeDescuento());
            ps.setDouble(7, o.getMontoFijo());
            ps.setBoolean(8, o.isActiva());
            ps.setInt(9, o.getIdOferta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizarOferta: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarOferta(int id) {
        String sql = "DELETE FROM oferta WHERE id_oferta=?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminarOferta: " + e.getMessage());
            return false;
        }
    }
}
