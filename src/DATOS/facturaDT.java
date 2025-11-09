package DATOS;

import LOGICA.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class facturaDT {

    // Generar factura desde un pedido
    public static boolean generarFactura(Factura factura) {
        String sql = "INSERT INTO factura (id_pedido, id_cliente, numero_factura, subtotal, impuesto, descuento, total, metodo_pago, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, factura.getIdPedido());
            ps.setInt(2, factura.getIdCliente());
            ps.setString(3, factura.getNumeroFactura());
            ps.setDouble(4, factura.getSubtotal());
            ps.setDouble(5, factura.getImpuesto());
            ps.setDouble(6, factura.getDescuento());
            ps.setDouble(7, factura.getTotal());
            ps.setString(8, factura.getMetodoPago());
            ps.setString(9, factura.getEstado());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    factura.setIdFactura(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al generar factura: " + e.getMessage());
        }
        return false;
    }

    // Listar facturas
    public static List<Factura> listarFacturas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT f.*, p.fecha, p.hora " +
                     "FROM factura f " +
                     "INNER JOIN pedido p ON f.id_pedido = p.id_pedido " +
                     "ORDER BY f.fecha_hora DESC";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Factura f = new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getString("numero_factura"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("impuesto"),
                    rs.getDouble("descuento"),
                    rs.getDouble("total"),
                    rs.getString("metodo_pago"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_hora")
                );
                lista.add(f);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    // Buscar factura por número
    public static Factura buscarPorNumero(String numeroFactura) {
        String sql = "SELECT * FROM factura WHERE numero_factura = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroFactura);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_pedido"),
                    rs.getInt("id_cliente"),
                    rs.getString("numero_factura"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("impuesto"),
                    rs.getDouble("descuento"),
                    rs.getDouble("total"),
                    rs.getString("metodo_pago"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_hora")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar factura: " + e.getMessage());
        }
        return null;
    }

    // Anular factura
    public static boolean anularFactura(int idFactura) {
        String sql = "UPDATE factura SET estado = 'Anulada' WHERE id_factura = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al anular factura: " + e.getMessage());
            return false;
        }
    }
}
