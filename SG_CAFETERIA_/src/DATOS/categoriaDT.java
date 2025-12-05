package DATOS;

import LOGICA.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class categoriaDT {

    public static List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria WHERE activo = TRUE ORDER BY orden_display, nombre";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                categorias.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener categorías: " + e.getMessage());
        }
        return categorias;
    }

    public static boolean agregarCategoria(Categoria cat) {
        String sql = "INSERT INTO categoria (nombre, descripcion, activo) VALUES (?, ?, TRUE)";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar categoría: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarCategoria(Categoria cat) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id_categoria = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            ps.setInt(3, cat.getIdCategoria());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarCategoria(int idCategoria) {
        String sql = "UPDATE categoria SET activo = FALSE WHERE id_categoria = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }

    public static int contarPlatosPorCategoria(int idCategoria) {
        String sql = "SELECT COUNT(*) FROM plato WHERE id_categoria = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al contar platos: " + e.getMessage());
        }
        return 0;
    }
}
