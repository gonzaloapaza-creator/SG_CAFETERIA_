package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.Sesion;

public class frmMenuPuntoVenta extends JFrame {

    public frmMenuPuntoVenta() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Punto de Venta - Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(46, 204, 113));
        panelHeader.setBounds(0, 0, 800, 80);
        panelHeader.setLayout(null);
        getContentPane().add(panelHeader);

        JLabel lblTitulo = new JLabel("PUNTO DE VENTA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 15, 400, 30);
        panelHeader.add(lblTitulo);

        String nombreUsuario = Sesion.getUsuarioActivo() != null ?
            Sesion.getUsuarioActivo().getUsername() : "Usuario";
        String rol = Sesion.getUsuarioActivo() != null ?
            Sesion.getUsuarioActivo().getRol() : "Cajero";

        JLabel lblUsuario = new JLabel(String.format("👤 %s - %s", nombreUsuario, rol));
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(30, 45, 300, 25);
        panelHeader.add(lblUsuario);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setBackground(new Color(39, 174, 96));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(650, 25, 120, 35);
        btnVolver.addActionListener(e -> volverMenuPrincipal());
        panelHeader.add(btnVolver);

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(UIConstants.COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panelCentral.setBounds(50, 110, 700, 380);
        panelCentral.setLayout(null);
        getContentPane().add(panelCentral);

        JLabel lblTituloCentral = new JLabel("Operaciones de Caja");
        lblTituloCentral.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloCentral.setForeground(UIConstants.COLOR_PRIMARY);
        lblTituloCentral.setBounds(250, 20, 250, 30);
        panelCentral.add(lblTituloCentral);

        // Botones principales
        JButton btnNuevaVenta = crearBotonGrande(
            "NUEVA VENTA",
            "Registrar venta y generar factura",
            new Color(52, 152, 219),
            80, 70
        );
        btnNuevaVenta.addActionListener(e -> new frmVenta().setVisible(true));
        panelCentral.add(btnNuevaVenta);

        JButton btnFacturas = crearBotonGrande(
            "FACTURAS",
            "Consultar y gestionar facturas",
            new Color(155, 89, 182),
            380, 70
        );
        btnFacturas.addActionListener(e -> new frmFactura().setVisible(true));
        panelCentral.add(btnFacturas);

        JButton btnClientes = crearBotonGrande(
            "CLIENTES",
            "Gestionar información de clientes",
            new Color(230, 126, 34),
            80, 200
        );
        btnClientes.addActionListener(e -> new frmCliente().setVisible(true));
        panelCentral.add(btnClientes);

        JButton btnReportes = crearBotonGrande(
            "REPORTES",
            "Ver reportes de ventas del día",
            new Color(26, 188, 156),
            380, 200
        );
        btnReportes.addActionListener(e -> new frmReportes().setVisible(true));
        panelCentral.add(btnReportes);

        // Información adicional
        JLabel lblInfo = new JLabel("Tip: Presione F1 para ayuda rápida");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(127, 140, 141));
        lblInfo.setBounds(220, 330, 300, 20);
        panelCentral.add(lblInfo);
    }

    private JButton crearBotonGrande(String titulo, String descripcion, Color color, int x, int y) {
        JButton boton = new JButton();
        boton.setBounds(x, y, 240, 100);
        boton.setLayout(null);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 25, 240, 30);
        boton.add(lblTitulo);

        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(new Color(240, 240, 240));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDesc.setBounds(0, 55, 240, 20);
        boton.add(lblDesc);

        return boton;
    }

    private void volverMenuPrincipal() {
        new frmMenuPrincipal().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new frmMenuPuntoVenta().setVisible(true);
        });
    }
}
