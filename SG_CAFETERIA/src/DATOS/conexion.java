package DATOS;
import java.sql.*;

public class conexion {

    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/dbcafeteria?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para conectar
    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión establecida con éxito a MySQL.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver de MySQL no encontrado.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    // Método para desconectar
    public static void desconectar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
