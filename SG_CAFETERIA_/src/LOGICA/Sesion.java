package LOGICA;

public class Sesion {
    
    private static Usuario usuarioActivo = null;

    public static void establecerUsuarioActivo(Usuario usuario) {
        usuarioActivo = usuario;
    }
    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public static boolean hayUsuarioActivo() {
        return usuarioActivo != null;
    }

    public static boolean esAdministrador() {
        return usuarioActivo != null && "Administrador".equals(usuarioActivo.getRol());
    }

    public static boolean esCajero() {
        return usuarioActivo != null && "Cajero".equals(usuarioActivo.getRol());
    }

    public static boolean esMesero() {
        return usuarioActivo != null && "Mesero".equals(usuarioActivo.getRol());
    }

    public static boolean esChef() {
        return usuarioActivo != null && "Chef".equals(usuarioActivo.getRol());
    }

    public static boolean esGerente() {
        return usuarioActivo != null && "Gerente".equals(usuarioActivo.getRol());
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }

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