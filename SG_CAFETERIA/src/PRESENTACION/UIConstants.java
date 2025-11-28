package PRESENTACION;

import java.awt.*;

public class UIConstants {
    // Paleta de colores suaves
    public static final Color COLOR_FONDO = new Color(245, 247, 250);          // Gris azulado muy claro
    public static final Color COLOR_PANEL = new Color(255, 255, 255);          // Blanco
    public static final Color COLOR_PRIMARY = new Color(79, 129, 189);         // Azul suave
    public static final Color COLOR_SUCCESS = new Color(92, 184, 92);          // Verde suave
    public static final Color COLOR_WARNING = new Color(240, 173, 78);         // Naranja suave
    public static final Color COLOR_DANGER = new Color(217, 83, 79);           // Rojo suave
    public static final Color COLOR_TEXTO = new Color(51, 51, 51);             // Gris oscuro
    public static final Color COLOR_TEXTO_CLARO = new Color(255, 255, 255);    // Blanco
    
    // Tipografía
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 12);
    
    // Método auxiliar para crear botones estilizados
    public static void estilizarBoton(javax.swing.JButton boton, Color colorFondo) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(colorFondo);
        boton.setForeground(COLOR_TEXTO_CLARO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
