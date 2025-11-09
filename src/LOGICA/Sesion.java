package LOGICA;

/**
 * Clase para gestionar la sesión del usuario activo
 */
public class Sesion {
    
    // Variable estática para almacenar el usuario activo
    private static Usuario usuarioActivo = null;

    /**
     * Establecer el usuario activo en la sesión
     * @param usuario Usuario a establecer como activo
     */
    public static void establecerUsuarioActivo(Usuario usuario) {
        usuarioActivo = usuario;
    }

    /**
     * Obtener el usuario activo
     * @return Usuario activo, null si no hay sesión
     */
    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    /**
     * Verificar si hay usuario activo
     * @return true si hay usuario activo
     */
    public static boolean hayUsuarioActivo() {
        return usuarioActivo != null;
    }

    /**
     * Verificar si el usuario es administrador
     * @return true si es administrador
     */
    public static boolean esAdministrador() {
        return usuarioActivo != null && "Administrador".equals(usuarioActivo.getRol());
    }

    /**
     * Verificar si el usuario es cajero
     * @return true si es cajero
     */
    public static boolean esCajero() {
        return usuarioActivo != null && "Cajero".equals(usuarioActivo.getRol());
    }

    /**
     * Verificar si el usuario es mesero
     * @return true si es mesero
     */
    public static boolean esMesero() {
        return usuarioActivo != null && "Mesero".equals(usuarioActivo.getRol());
    }

    /**
     * Verificar si el usuario es chef
     * @return true si es chef
     */
    public static boolean esChef() {
        return usuarioActivo != null && "Chef".equals(usuarioActivo.getRol());
    }

    /**
     * Verificar si el usuario es gerente
     * @return true si es gerente
     */
    public static boolean esGerente() {
        return usuarioActivo != null && "Gerente".equals(usuarioActivo.getRol());
    }

    /**
     * Cerrar la sesión actual
     */
    public static void cerrarSesion() {
        usuarioActivo = null;
    }

    /**
     * Obtener información de la sesión
     * @return String con información del usuario activo
     */
    public static String obtenerInfoSesion() {
        if (usuarioActivo != null) {
            return "Usuario: " + usuarioActivo.getUsername() + 
                   " | Rol: " + usuarioActivo.getRol();
        }
        return "Sin sesión activa";
    }

    @Override
    public String toString() {
        return obtenerInfoSesion();
    }
}
