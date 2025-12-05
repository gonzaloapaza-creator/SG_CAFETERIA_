package PRESENTACION;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import DATOS.reservaDT;
import LOGICA.Reserva;
import LOGICA.Sesion;
import LOGICA.Persona;
import java.util.List;

public class frmMenuCliente extends JFrame {

    private JLabel lblCliente;
    private JTable tblReservas;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadistica;
    private JPanel panelBotones;

    public frmMenuCliente() {
        configurarVentana();
        inicializarComponentes();
        cargarDatosCliente();
        cargarReservasActivas();
        configurarEventsTabla();
    }

    private void configurarVentana() {
        setTitle("Gestor de Reservas - Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 242, 245));
        getContentPane().setLayout(null);
        setIconImage(new ImageIcon("recursos/icono.png").getImage());
    }

    private void inicializarComponentes() {
        // Panel Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(44, 62, 80));
        panelHeader.setBounds(0, 0, 900, 85);
        panelHeader.setLayout(null);
        getContentPane().add(panelHeader);

        JLabel lblTitulo = new JLabel("GESTOR DE RESERVAS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(25, 10, 500, 35);
        panelHeader.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Sistema de Administración de Cafetería");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(189, 195, 199));
        lblSubtitulo.setBounds(25, 48, 300, 20);
        panelHeader.add(lblSubtitulo);

        lblCliente = new JLabel("Usuario: Cargando...");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCliente.setForeground(new Color(220, 220, 220));
        lblCliente.setBounds(400, 50, 350, 20);
        panelHeader.add(lblCliente);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setBounds(662, 18, 90, 32);
        btnActualizar.setBackground(new Color(52, 152, 219));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> recargarDatos());
        panelHeader.add(btnActualizar);

        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSalir.setBounds(762, 18, 106, 32);
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> cerrarSesion());
        panelHeader.add(btnSalir);

        // Panel Estadísticas
        JPanel panelStats = new JPanel();
        panelStats.setBackground(Color.WHITE);
        panelStats.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        panelStats.setBounds(25, 100, 850, 50);
        panelStats.setLayout(null);
        getContentPane().add(panelStats);

        lblEstadistica = new JLabel("Reservas Activas: 0 | Pendientes: 0 | Confirmadas: 0");
        lblEstadistica.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEstadistica.setForeground(new Color(52, 73, 94));
        lblEstadistica.setBounds(15, 15, 500, 20);
        panelStats.add(lblEstadistica);

        String[] columnas = {"ID", "Mesa", "Personas", "Fecha", "Hora", "Acciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; 
            }
        };

        tblReservas = new JTable(modeloTabla);
        tblReservas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblReservas.setRowHeight(30);
        tblReservas.setSelectionBackground(new Color(149, 165, 166));
        tblReservas.setGridColor(new Color(220, 220, 220));

        JTableHeader encabezado = tblReservas.getTableHeader();
        encabezado.setBackground(new Color(44, 62, 80));
        encabezado.setForeground(Color.WHITE);
        encabezado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        encabezado.setPreferredSize(new Dimension(0, 28));

        tblReservas.getColumnModel().getColumn(0).setPreferredWidth(40); 
        tblReservas.getColumnModel().getColumn(1).setPreferredWidth(50); 
        tblReservas.getColumnModel().getColumn(2).setPreferredWidth(70); 
        tblReservas.getColumnModel().getColumn(3).setPreferredWidth(90); 
        tblReservas.getColumnModel().getColumn(4).setPreferredWidth(70); 
        tblReservas.getColumnModel().getColumn(5).setPreferredWidth(90); 

        JScrollPane scrollTabla = new JScrollPane(tblReservas);
        scrollTabla.setBounds(25, 165, 850, 340);
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        getContentPane().add(scrollTabla);

        panelBotones = new JPanel();
        panelBotones.setBackground(new Color(240, 242, 245));
        panelBotones.setBounds(25, 520, 850, 90);
        panelBotones.setLayout(null);
        getContentPane().add(panelBotones);

        JButton btnNuevaReserva = crearBoton("Nueva Reserva", 30, 20, 200, 40, new Color(39, 174, 96));
        btnNuevaReserva.addActionListener(e -> hacerReserva());
        panelBotones.add(btnNuevaReserva);

        JButton btnEditar = crearBoton("Editar", 250, 20, 150, 40, new Color(52, 152, 219));
        btnEditar.addActionListener(e -> editarReserva());
        panelBotones.add(btnEditar);

        JButton btnCancelar = crearBoton("Cancelar", 420, 20, 150, 40, new Color(231, 76, 60));
        btnCancelar.addActionListener(e -> cancelarReserva());
        panelBotones.add(btnCancelar);

        JButton btnVolver = crearBoton("Volver", 590, 20, 150, 40, new Color(149, 165, 166));
        btnVolver.addActionListener(e -> volverMenuPrincipal());
        panelBotones.add(btnVolver);

        JLabel lblInfo = new JLabel("Selecciona una reserva de la tabla para editarla o cancelarla. " +
                "Haz clic en 'Nueva Reserva' para crear una nueva.");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(127, 140, 141));
        lblInfo.setBounds(30, 65, 800, 16);
        panelBotones.add(lblInfo);
    }

    private void configurarEventsTabla() {
        tblReservas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblReservas.rowAtPoint(e.getPoint());
                int columna = tblReservas.columnAtPoint(e.getPoint());

                if (fila >= 0 && columna == 5) { // Acciones
                    int idReserva = (int) modeloTabla.getValueAt(fila, 0);
                    mostrarDetallesReserva(idReserva);
                }
            }
        });
    }

    private void mostrarDetallesReserva(int idReserva) {
        try {
            List<Reserva> reservas = reservaDT.listarReservas();
            Reserva reservaEncontrada = null;

            for (Reserva r : reservas) {
                if (r.getIdReserva() == idReserva) {
                    reservaEncontrada = r;
                    break;
                }
            }

            if (reservaEncontrada != null) {
                Reserva r = reservaEncontrada;
                String nombreCliente = "Sin cliente";

                if (r.getCliente() != null && r.getCliente().getPersona() != null) {
                    Persona persona = r.getCliente().getPersona();
                    nombreCliente = persona.getNombre() + " " + persona.getApellidos();
                }

                String detalles = "═══════════════════════════════════\n" +
                        "      DETALLES DE LA RESERVA\n" +
                        "═══════════════════════════════════\n\n" +
                        "ID Reserva: " + r.getIdReserva() + "\n" +
                        "Cliente: " + nombreCliente + "\n" +
                        "Número de Mesa: " + (r.getMesa() != null ? r.getMesa().getIdMesa() : "N/A") + "\n" +
                        "Cantidad de Personas: " + r.getPersonas() + "\n" +
                        "Fecha: " + r.getFecha() + "\n" +
                        "Hora: " + r.getHora() + "\n" +
                        "Estado: " + r.getEstado() + "\n" +
                        "═══════════════════════════════════";

                JOptionPane.showMessageDialog(this, detalles, "📋 Detalles de Reserva",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró la reserva con ID: " + idReserva,
                        "⚠️ Reserva no encontrada",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener detalles: " + ex.getMessage(),
                    "❌ Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBounds(x, y, ancho, alto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(oscurecerColor(color));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    private Color oscurecerColor(Color color) {
        return new Color(
                Math.max(0, color.getRed() - 20),
                Math.max(0, color.getGreen() - 20),
                Math.max(0, color.getBlue() - 20)
        );
    }

    private void cargarDatosCliente() {
        if (Sesion.hayUsuarioActivo()) {
            lblCliente.setText("Usuario: " + Sesion.getUsuarioActivo().getUsername() + 
                    " | Rol: " + Sesion.getUsuarioActivo().getRol());
        } else {
            lblCliente.setText("Usuario: No identificado");
        }
    }

    private void cargarReservasActivas() {
        modeloTabla.setRowCount(0);
        int totalReservas = 0;
        int pendientes = 0;
        int confirmadas = 0;

        try {
            List<Reserva> reservas = reservaDT.listarReservas();
            if (reservas == null) {
                lblEstadistica.setText("❌ Error: No se pudo conectar a la base de datos");
                JOptionPane.showMessageDialog(this, 
                        "Error: listarReservas() devolvió NULL\nRevisa la conexión a BD en reservaDT",
                        "Error Crítico", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Reserva r : reservas) {
                if (r == null) continue;

                String estado = r.getEstado() != null ? r.getEstado() : "Sin estado";

                totalReservas++;
                if ("Pendiente".equals(estado)) pendientes++;
                else if ("Confirmada".equals(estado)) confirmadas++;

                Object[] fila = {
                        r.getIdReserva(),
                        (r.getMesa() != null ? r.getMesa().getIdMesa() : "N/A"),
                        r.getPersonas(),
                        r.getFecha(),
                        r.getHora(),
                        "Ver detalles"
                };
                modeloTabla.addRow(fila);
            }

            lblEstadistica.setText(String.format(
                    "Reservas Activas: %d | Pendientes: %d | Confirmadas: %d",
                    totalReservas, pendientes, confirmadas
            ));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las reservas:\n" + e.getMessage(),
                    "❌ Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarDatos() {
        cargarReservasActivas();
        JOptionPane.showMessageDialog(this,
                "Datos recargados correctamente",
                "✅ Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void hacerReserva() {
        new frmReserva().setVisible(true);
    }

    private void editarReserva() {
        int filaSeleccionada = tblReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una reserva para editar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        JOptionPane.showMessageDialog(this,
                "Editando reserva #" + idReserva,
                "Editar Reserva",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarReserva() {
        int filaSeleccionada = tblReservas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una reserva para cancelar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas cancelar la reserva #" + idReserva + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                modeloTabla.setValueAt("Cancelada", filaSeleccionada, 5); // Se puede omitir si no se muestra
                cargarReservasActivas();
                JOptionPane.showMessageDialog(this,
                        "Reserva cancelada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al cancelar la reserva: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void volverMenuPrincipal() {
        new frmMenuPrincipal().setVisible(true);
        this.dispose();
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            new frmLogin().setVisible(true);
            this.dispose();
        }
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
