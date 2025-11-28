package DATOS;

import LOGICA.Usuario;
import java.sql.*;
import java.util.logging.Logger;

public class usuarioDT {

    private static final Logger LOGGER = Logger.getLogger(usuarioDT.class.getName());

    public static Usuario validarUsuario(String username, String password) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, username, id_empleado, rol, activo " +
                     "FROM usuario " +
                     "WHERE username = ? AND activo = TRUE";

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();  // ✅ CORRECTO
            
            if (conexionDB == null) {
                LOGGER.severe("❌ No se pudo establecer conexión a BD");
                return null;
            }

            PreparedStatement ps = conexionDB.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String rol = rs.getString("rol");
                int idEmpleado = rs.getInt("id_empleado");
                boolean activo = rs.getBoolean("activo");

                String sqlPassword = "SELECT password_hash FROM usuario WHERE id_usuario = ?";
                PreparedStatement psPassword = conexionDB.prepareStatement(sqlPassword);
                psPassword.setInt(1, idUsuario);
                ResultSet rsPassword = psPassword.executeQuery();
                
                if (rsPassword.next()) {
                    String passwordBD = rsPassword.getString("password_hash");
                    
                    // Validar contraseña
                    if (validarPassword(password, passwordBD)) {
                        usuario = new Usuario(
                            idUsuario,
                            username,
                            rol,
                            idEmpleado,
                            activo
                        );
                        
                        registrarLogin(idUsuario);
                        LOGGER.info("✅ Login exitoso para: " + username);
                    } else {
                        registrarLoginFallido(idUsuario);
                        LOGGER.warning("❌ Contraseña incorrecta para: " + username);
                    }
                }
                
                rsPassword.close();
                psPassword.close();
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            LOGGER.severe("Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexion.desconectar(conexionDB); 
        }

        return usuario;
    }
    private static boolean validarPassword(String passwordIngresada, String passwordBD) {
        return passwordIngresada.equals(passwordBD);
    }

    private static void registrarLogin(int idUsuario) {
        String sql = "UPDATE usuario SET ultimo_acceso = NOW(), intentos_fallidos = 0, bloqueado = FALSE " +
                     "WHERE id_usuario = ?";

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();  // ✅ CORRECTO
            
            if (conexionDB != null) {
                PreparedStatement ps = conexionDB.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
                ps.close();
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al registrar login: " + e.getMessage());
        } finally {
            conexion.desconectar(conexionDB);  // ✅ CORRECTO
        }
    }

    // Registrar intento fallido de login
     
    private static void registrarLoginFallido(int idUsuario) {
        String sql = "UPDATE usuario SET intentos_fallidos = intentos_fallidos + 1 WHERE id_usuario = ?";

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();  // ✅ CORRECTO
            
            if (conexionDB != null) {
                PreparedStatement ps = conexionDB.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
                ps.close();
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al registrar intento fallido: " + e.getMessage());
        } finally {
            conexion.desconectar(conexionDB);
        }
    }

    public static Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, username, id_empleado, rol, activo FROM usuario WHERE id_usuario = ?";

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();
            
            if (conexionDB != null) {
                PreparedStatement ps = conexionDB.prepareStatement(sql);
                ps.setInt(1, idUsuario);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("rol"),
                        rs.getInt("id_empleado"),
                        rs.getBoolean("activo")
                    );
                }
                
                rs.close();
                ps.close();
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al obtener usuario: " + e.getMessage());
        } finally {
            conexion.desconectar(conexionDB); 
        }

        return usuario;
    }

    // Cambiar contraseña de usuario
    public static boolean cambiarPassword(int idUsuario, String passwordNueva) {
        String sql = "UPDATE usuario SET password_hash = ?, requiere_cambio_password = FALSE WHERE id_usuario = ?";

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();
            
            if (conexionDB != null) {
                PreparedStatement ps = conexionDB.prepareStatement(sql);
                ps.setString(1, passwordNueva);
                ps.setInt(2, idUsuario);

                int filas = ps.executeUpdate();
                ps.close();
                return filas > 0;
            }

        } catch (SQLException e) {
            LOGGER.severe("Error al cambiar password: " + e.getMessage());
        } finally {
            conexion.desconectar(conexionDB);
        }

        return false;
    }
}
