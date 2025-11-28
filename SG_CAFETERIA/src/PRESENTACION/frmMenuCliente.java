package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import DATOS.reservaDT;
import LOGICA.Reserva;
import LOGICA.Sesion;
import java.util.List;

public class frmMenuCliente extends JFrame {

    private JLabel lblCliente;
    private JTextArea txaReservasActivas;

    public frmMenuCliente() {
        configurarVentana();
        inicializarComponentes();
        cargarDatosCliente();
    }

    private void configurarVentana() {
        setTitle("Portal del Cliente - Sólo Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 250));
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {

        // ==========================
        // Encabezado
        // ==========================
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(52, 73, 94));
        panelHeader.setBounds(0, 0, 700, 80);
        panelHeader.setLayout(null);
        getContentPane().add(panelHeader);

        JLabel lblTitulo = new JLabel("GESTIÓN DE RESERVAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 10, 400, 32);
        panelHeader.add(lblTitulo);

        lblCliente = new JLabel("Cargando información del cliente...");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCliente.setForeground(new Color(220, 220, 220));
        lblCliente.setBounds(30, 48, 500, 22);
        panelHeader.add(lblCliente);

        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalir.setBounds(580, 20, 100, 32);
        btnSalir.setBackground(new Color(192, 57, 43));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> cerrarSesion());
        panelHeader.add(btnSalir);

        // ==============================
        // Panel Reservas
        // ==============================
        JPanel panelReservas = new JPanel();
        panelReservas.setLayout(null);
        panelReservas.setBackground(new Color(245, 245, 250));
        panelReservas.setBounds(30, 100, 640, 240);
        getContentPane().add(panelReservas);

        JLabel lblReservas = new JLabel("Mis Reservas Activas:");
        lblReservas.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblReservas.setBounds(0, 0, 300, 25);
        panelReservas.add(lblReservas);

        txaReservasActivas = new JTextArea();
        txaReservasActivas.setEditable(false);
        txaReservasActivas.setFont(new Font("Consolas", Font.PLAIN, 13));
        txaReservasActivas.setBackground(Color.WHITE);
        txaReservasActivas.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        JScrollPane scrollReservas = new JScrollPane(txaReservasActivas);
        scrollReservas.setBounds(0, 35, 640, 190);
        panelReservas.add(scrollReservas);

        // ==============================
        // Botón Nueva Reserva
        // ==============================
        JButton btnNuevaReserva = new JButton("Nueva Reserva");
        btnNuevaReserva.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnNuevaReserva.setBounds(30, 360, 300, 45);
        btnNuevaReserva.setBackground(new Color(39, 174, 96));
        btnNuevaReserva.setForeground(Color.WHITE);
        btnNuevaReserva.setFocusPainted(false);
        btnNuevaReserva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaReserva.addActionListener(e -> hacerReserva());
        getContentPane().add(btnNuevaReserva);

        // ==============================
        // Botón Volver al Menú Principal
        // ==============================
        JButton btnVolverMenu = new JButton("Volver al Menú Principal");
        btnVolverMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnVolverMenu.setBounds(350, 360, 300, 45);
        btnVolverMenu.setBackground(new Color(41, 128, 185));
        btnVolverMenu.setForeground(Color.WHITE);
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverMenu.addActionListener(e -> volverMenuPrincipal());
        getContentPane().add(btnVolverMenu);
    }

    private void cargarDatosCliente() {
        if (Sesion.hayUsuarioActivo()) {
            lblCliente.setText("👤 Cliente: " + Sesion.getUsuarioActivo().getUsername());
        } else {
            lblCliente.setText("👤 No identificado");
        }
        cargarReservasActivas();
    }

    private void cargarReservasActivas() {
        StringBuilder sb = new StringBuilder();
        sb.append("════════════════════════════════════════\n");
        try {
            List<Reserva> reservas = reservaDT.listarReservas();
            if (reservas.isEmpty()) {
                sb.append("No tienes reservas activas.\n\nHaz clic en 'Nueva Reserva' para solicitar una.");
            } else {
                for (Reserva r : reservas) {
                    if ("Confirmada".equals(r.getEstado()) || "Pendiente".equals(r.getEstado())) {
                        sb.append(String.format("Reserva #%d | Mesa: %d\n", 
                            r.getIdReserva(), r.getMesa().getIdMesa()));
                        sb.append(String.format("Personas: %d  Fecha: %s  Hora: %s  Estado: %s\n", 
                            r.getPersonas(), r.getFecha(), r.getHora(), r.getEstado()));
                        sb.append("------------------------------------------\n");
                    }
                }
            }
        } catch (Exception e) {
            sb.append("❌ Error al consultar reservas.");
            e.printStackTrace();
        }
        txaReservasActivas.setText(sb.toString());
        txaReservasActivas.setCaretPosition(0);
    }

    private void hacerReserva() {
        new frmReserva().setVisible(true);
    }

    private void volverMenuPrincipal() {
        new frmMenuPrincipal().setVisible(true);
        this.dispose();
    }

    private void cerrarSesion() {
        Sesion.cerrarSesion();
        new frmMenuPrincipal().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new frmMenuCliente().setVisible(true);
        });
    }
}
