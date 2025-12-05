package DATOS;

import LOGICA.Cliente;
import LOGICA.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDT {

    // Listar clientes con datos personales
    public static List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT c.id_cliente, p.id_persona, p.nombre, p.apellidos, p.ci, p.edad " +
                     "FROM cliente c INNER JOIN persona p ON c.id_persona = p.id_persona " +
                     "WHERE p.activo = TRUE";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona per = new Persona(
                    rs.getInt("id_persona"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("ci"),
                    rs.getInt("edad")
                );
                Cliente cli = new Cliente(rs.getInt("id_cliente"), per);
                lista.add(cli);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // Buscar cliente por CI
    public static Cliente buscarPorCI(String ci) {
        String sql = "SELECT c.id_cliente, p.id_persona, p.nombre, p.apellidos, p.ci, p.edad " +
                     "FROM cliente c INNER JOIN persona p ON c.id_persona = p.id_persona " +
                     "WHERE p.ci = ? AND p.activo = TRUE";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ci.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Persona per = new Persona(
                    rs.getInt("id_persona"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("ci"),
                    rs.getInt("edad")
                );
                return new Cliente(rs.getInt("id_cliente"), per);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por CI: " + e.getMessage());
        }
        return null;
    }

    // Agregar nuevo cliente
    public static boolean agregarCliente(Cliente cliente) {
        boolean exito = false;
        String sqlPersona = "INSERT INTO persona (nombre, apellidos, ci, edad) VALUES (?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (id_persona, puntos_fidelidad) VALUES (?, 0)";

        try (Connection conn = conexion.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                psPersona.setString(1, cliente.getPersona().getNombre());
                psPersona.setString(2, cliente.getPersona().getApellidos());
                psPersona.setString(3, cliente.getPersona().getCi());
                psPersona.setInt(4, cliente.getPersona().getEdad());
                psPersona.executeUpdate();

                ResultSet keys = psPersona.getGeneratedKeys();
                if (keys.next()) {
                    int idPersona = keys.getInt(1);
                    cliente.getPersona().setIdPersona(idPersona);

                    try (PreparedStatement psCliente = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS)) {
                        psCliente.setInt(1, idPersona);
                        psCliente.executeUpdate();

                        ResultSet keysCliente = psCliente.getGeneratedKeys();
                        if (keysCliente.next()) {
                            cliente.setIdCliente(keysCliente.getInt(1));
                            exito = true;
                        }
                    }
                }

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
        }

        return exito;
    }

    // Verificar si CI ya existe
    public static boolean ciExiste(String ci) {
        String sql = "SELECT COUNT(*) FROM persona WHERE ci = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ci.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar CI: " + e.getMessage());
        }
        return false;
    }

    public static int obtenerPuntos(int idCliente) {
        String sql = "SELECT puntos_fidelidad FROM cliente WHERE id_cliente = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("puntos_fidelidad");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener puntos: " + e.getMessage());
        }
        return 0;
    }

    public static boolean actualizarPuntos(int idCliente, int puntos) {
        String sql = "UPDATE cliente SET puntos_fidelidad = puntos_fidelidad + ? WHERE id_cliente = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, puntos);
            ps.setInt(2, idCliente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar puntos: " + e.getMessage());
            return false;
        }
    }

    public static List<Cliente> obtenerClientesFrecuentes(int limite) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT c.id_cliente, p.id_persona, p.nombre, p.apellidos, p.ci, p.edad, c.puntos_fidelidad, " +
                     "COUNT(ped.id_pedido) AS total_pedidos " +
                     "FROM cliente c " +
                     "INNER JOIN persona p ON c.id_persona = p.id_persona " +
                     "LEFT JOIN pedido ped ON c.id_cliente = ped.id_cliente " +
                     "WHERE p.activo = TRUE " +
                     "GROUP BY c.id_cliente, p.id_persona, p.nombre, p.apellidos, p.ci, p.edad, c.puntos_fidelidad " +
                     "HAVING COUNT(ped.id_pedido) > 0 " +
                     "ORDER BY total_pedidos DESC, c.puntos_fidelidad DESC " +
                     "LIMIT ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Persona per = new Persona(
                    rs.getInt("id_persona"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("ci"),
                    rs.getInt("edad")
                );
                Cliente cli = new Cliente(rs.getInt("id_cliente"), per);
                lista.add(cli);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener clientes frecuentes: " + e.getMessage());
        }
        return lista;
    }
}
