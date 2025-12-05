package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.Sesion;

public class frmMenuPrincipal extends JFrame {

    public frmMenuPrincipal() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Cafetería - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("SISTEMA DE CAFETERÍA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 30, 600, 40);
        getContentPane().add(lblTitulo);

        String nombreUsuario = Sesion.getUsuarioActivo() != null ?
            Sesion.getUsuarioActivo().getUsername() + " (" + Sesion.getUsuarioActivo().getRol() + ")" :
            "Invitado";

        JLabel lblUsuario = new JLabel("Usuario: " + nombreUsuario);
        lblUsuario.setFont(UIConstants.FUENTE_NORMAL);
        lblUsuario.setForeground(UIConstants.COLOR_TEXTO);
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setBounds(50, 80, 600, 25);
        getContentPane().add(lblUsuario);

        JPanel panelOpciones = new JPanel();
        panelOpciones.setBackground(UIConstants.COLOR_PANEL);
        panelOpciones.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panelOpciones.setBounds(100, 130, 500, 280);
        panelOpciones.setLayout(null);
        getContentPane().add(panelOpciones);

        JLabel lblSeleccione = new JLabel("Seleccione el módulo que desea acceder:");
        lblSeleccione.setFont(UIConstants.FUENTE_SUBTITULO);
        lblSeleccione.setForeground(UIConstants.COLOR_TEXTO);
        lblSeleccione.setBounds(80, 20, 350, 25);
        panelOpciones.add(lblSeleccione);

        int posY = 60;

        if (Sesion.getUsuarioActivo() != null && Sesion.esAdministrador()) {
            JButton btnAdministracion = crearBotonModulo(
                "ADMINISTRACIÓN",
                "Gestión de platos, ofertas y reportes",
                new Color(52, 152, 219),
                30, posY
            );
            btnAdministracion.addActionListener(e -> abrirMenuAdministracion());
            panelOpciones.add(btnAdministracion);
            posY += 70;
        }

        JButton btnPuntoVenta = crearBotonModulo(
            "PUNTO DE VENTA",
            "Ventas, facturación y atención al cliente",
            new Color(46, 204, 113),
            30, posY
        );
        btnPuntoVenta.addActionListener(e -> abrirMenuPuntoVenta());
        panelOpciones.add(btnPuntoVenta);
        posY += 70;

        JButton btnCliente = crearBotonModulo(
            "RESERVAS DE MESA",
            "Reservas y consultas",
            new Color(155, 89, 182),
            30, posY
        );
        btnCliente.addActionListener(e -> abrirMenuCliente());
        panelOpciones.add(btnCliente);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        UIConstants.estilizarBoton(btnCerrarSesion, UIConstants.COLOR_DANGER);
        btnCerrarSesion.setBounds(252, 415, 200, 35);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        getContentPane().add(btnCerrarSesion);
    }

    private JButton crearBotonModulo(String titulo, String descripcion, Color color, int x, int y) {
        JButton boton = new JButton();
        boton.setBounds(x, y, 440, 60);
        boton.setLayout(null);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(15, 10, 400, 25);
        boton.add(lblTitulo);

        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(240, 240, 240));
        lblDesc.setBounds(15, 32, 400, 20);
        boton.add(lblDesc);

        return boton;
    }

    private void abrirMenuAdministracion() {
        if (Sesion.getUsuarioActivo() != null && Sesion.esAdministrador()) {
            new frmMenuAdministracion().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Acceso denegado.\nSolo los administradores pueden acceder a este módulo.",
                "Permisos Insuficientes",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenuPuntoVenta() {
        if (Sesion.getUsuarioActivo() != null && 
            (Sesion.esAdministrador() || Sesion.esCajero())) {
            new frmMenuPuntoVenta().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Acceso denegado.\nSolo cajeros y administradores pueden acceder.",
                "Permisos Insuficientes",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenuCliente() {
        new frmMenuCliente().setVisible(true);
        this.dispose();
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            new frmLogin().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            new frmMenuPrincipal().setVisible(true);
        });
    }
}
