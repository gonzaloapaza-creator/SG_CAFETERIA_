package DATOS;

import LOGICA.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class pedidoDT {

    // Crear un nuevo pedido
    public static int crearPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_cliente, id_empleado, id_mesa, fecha, hora, total, estado, tipo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pedido.getCliente().getIdCliente());
            ps.setObject(2, pedido.getEmpleado() != null ? pedido.getEmpleado().getIdEmpleado() : null);
            ps.setObject(3, null); // Sin mesa para ventas directas
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setTime(5, Time.valueOf(LocalTime.now()));
            ps.setDouble(6, pedido.getTotal());
            ps.setString(7, "Pagado");
            ps.setString(8, "Local");

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al crear pedido: " + e.getMessage());
        }
        return -1;
    }

    // Agregar detalle del pedido
    public static boolean agregarDetalle(int idPedido, DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedido (id_pedido, id_plato, cantidad, precio_unitario) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, detalle.getPlato().getIdPlato());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPlato().getPrecio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar detalle: " + e.getMessage());
            return false;
        }
    }
}
