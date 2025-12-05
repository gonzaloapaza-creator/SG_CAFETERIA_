package DATOS;

import LOGICA.Plato;
import LOGICA.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class platosDT {

	public static List<Plato> listarPlatos() {
	    List<Plato> lista = new ArrayList<>();
	    String sql = "SELECT p.id_plato, p.nombre, c.id_categoria, c.nombre AS categoria, p.descripcion, p.precio, p.disponible " +
	                 "FROM plato p INNER JOIN categoria c ON p.id_categoria = c.id_categoria ORDER BY p.id_plato ASC"; // AGREGADO ORDER BY
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("categoria"));
                Plato p = new Plato(
                    rs.getInt("id_plato"),
                    rs.getString("nombre"),
                    cat,
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar los platos: " + e.getMessage());
        }
        return lista;
    }

    public static boolean agregarPlato(Plato p) {
        String sql = "INSERT INTO plato (nombre, id_categoria, descripcion, precio, disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCategoria().getIdCategoria());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setBoolean(5, p.isDisponible());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar plato: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarPlato(Plato p) {
        String sql = "UPDATE plato SET nombre=?, id_categoria=?, descripcion=?, precio=?, disponible=? WHERE id_plato=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getCategoria().getIdCategoria());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setBoolean(5, p.isDisponible());
            ps.setInt(6, p.getIdPlato());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar plato: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarPlato(int idPlato) {
        String sql = "DELETE FROM plato WHERE id_plato = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPlato);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar plato: " + e.getMessage());
            return false;
        }
    }

    public static boolean cambiarEstado(int id, boolean disponible) {
        String sql = "UPDATE plato SET disponible=? WHERE id_plato=?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, disponible);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    public static Plato buscarPorId(int idPlato) {
        String sql = "SELECT p.id_plato, p.nombre, c.id_categoria, c.nombre AS categoria, p.descripcion, p.precio, p.disponible " +
                     "FROM plato p INNER JOIN categoria c ON p.id_categoria = c.id_categoria WHERE p.id_plato = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPlato);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("categoria"));
                return new Plato(
                    rs.getInt("id_plato"),
                    rs.getString("nombre"),
                    cat,
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar plato: " + e.getMessage());
        }
        return null;
    }
}
