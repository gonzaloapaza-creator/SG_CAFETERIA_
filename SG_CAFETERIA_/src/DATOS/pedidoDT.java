package DATOS;

import LOGICA.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class pedidoDT {

    public static int crearPedido(Pedido pedido) {
        String sql = "INSERT INTO pedido " +
                "(numero_pedido, id_cliente, id_empleado, fecha, hora, " +
                "subtotal, descuento, impuesto, propina, total, " +
                "estado, tipo, tiempo_estimado_min, notas, id_oferta_aplicada, puntos_ganados) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String numeroPedido = "PED-" + System.currentTimeMillis();
            ps.setString(1, numeroPedido);

            ps.setInt(2, pedido.getCliente().getIdCliente());

            if (pedido.getEmpleado() != null) {
                ps.setInt(3, pedido.getEmpleado().getIdEmpleado());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setTime(5, Time.valueOf(LocalTime.now()));

            double subtotal = pedido.calcularTotal(); 
            double impuesto = subtotal * 0.13;
            double total = subtotal + impuesto;

            ps.setDouble(6, subtotal);  
            ps.setDouble(7, 0.0);   
            ps.setDouble(8, impuesto); 
            ps.setDouble(9, 0.0);  
            ps.setDouble(10, total); 

            ps.setString(11, "Pagado");
            ps.setString(12, "Local");
            ps.setInt(13, 0); 
            ps.setString(14, null); 
            ps.setNull(15, Types.INTEGER);
            ps.setInt(16, 0); 

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

    public static boolean agregarDetalle(int idPedido, DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedido " +
                     "(id_pedido, id_plato, cantidad, precio_unitario) " +
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
