package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import DATOS.conexion;
import java.text.NumberFormat;
import java.util.Locale;

public class frmDashboard extends JFrame {

    private JLabel lblVentasHoy, lblVentasMes, lblClientesRegistrados, lblPlatosDisponibles;
    private JLabel lblPedidosHoy, lblReservasHoy, lblFacturasPendientes;
    private JTextArea txaTopPlatos, txaTopClientes;

    public frmDashboard() {
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Dashboard Ejecutivo - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("DASHBOARD EJECUTIVO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 1000, 35);
        add(lblTitulo);

        JLabel lblFecha = new JLabel("Fecha: " + java.time.LocalDate.now());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFecha.setForeground(new Color(127, 140, 141));
        lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
        lblFecha.setBounds(50, 50, 1000, 20);
        add(lblFecha);

        // Indicadores principales (KPIs)
        crearIndicador("Ventas Hoy", lblVentasHoy = new JLabel("$0.00"), 
                      new Color(52, 152, 219), 50, 90);
        
        crearIndicador("Ventas del Mes", lblVentasMes = new JLabel("$0.00"), 
                      new Color(46, 204, 113), 280, 90);
        
        crearIndicador("Pedidos Hoy", lblPedidosHoy = new JLabel("0"), 
                      new Color(155, 89, 182), 510, 90);
        
        crearIndicador("Reservas Hoy", lblReservasHoy = new JLabel("0"), 
                      new Color(230, 126, 34), 740, 90);

        // Indicadores secundarios
        crearIndicadorPequeno("Clientes", lblClientesRegistrados = new JLabel("0"), 50, 250);
        crearIndicadorPequeno("Platos", lblPlatosDisponibles = new JLabel("0"), 280, 250);
        crearIndicadorPequeno("Facturas Pend.", lblFacturasPendientes = new JLabel("0"), 510, 250);

        // Top 5 Platos
        JPanel panelTopPlatos = new JPanel();
        panelTopPlatos.setBackground(UIConstants.COLOR_PANEL);
        panelTopPlatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Top 5 Platos Más Vendidos",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTopPlatos.setBounds(50, 360, 500, 250);
        panelTopPlatos.setLayout(new BorderLayout());
        add(panelTopPlatos);

        txaTopPlatos = new JTextArea();
        txaTopPlatos.setEditable(false);
        txaTopPlatos.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPlatos = new JScrollPane(txaTopPlatos);
        panelTopPlatos.add(scrollPlatos, BorderLayout.CENTER);

        // Top 5 Clientes
        JPanel panelTopClientes = new JPanel();
        panelTopClientes.setBackground(UIConstants.COLOR_PANEL);
        panelTopClientes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Top 5 Clientes VIP",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTopClientes.setBounds(570, 360, 500, 250);
        panelTopClientes.setLayout(new BorderLayout());
        add(panelTopClientes);

        txaTopClientes = new JTextArea();
        txaTopClientes.setEditable(false);
        txaTopClientes.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollClientes = new JScrollPane(txaTopClientes);
        panelTopClientes.add(scrollClientes, BorderLayout.CENTER);

        // Botones
        JButton btnActualizar = new JButton("Actualizar Dashboard");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(720, 625, 220, 35);
        btnActualizar.addActionListener(e -> cargarDatos());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(960, 625, 110, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearIndicador(String titulo, JLabel lblValor, Color color, int x, int y) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x, y, 210, 120);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder());
        add(panel);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(15, 15, 180, 25);
        panel.add(lblTitulo);

        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(Color.WHITE);
        lblValor.setBounds(15, 50, 180, 50);
        panel.add(lblValor);
    }

    private void crearIndicadorPequeno(String titulo, JLabel lblValor, int x, int y) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        panel.setBounds(x, y, 210, 80);
        panel.setLayout(null);
        add(panel);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(15, 10, 180, 20);
        panel.add(lblTitulo);

        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblValor.setForeground(UIConstants.COLOR_PRIMARY);
        lblValor.setBounds(15, 35, 180, 35);
        panel.add(lblValor);
    }

    private void cargarDatos() {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "BO"));

        try (Connection conn = conexion.conectar()) {

            // Ventas de hoy
            String sqlVentasHoy = "SELECT COALESCE(SUM(total), 0) AS total FROM pedido WHERE fecha = CURDATE() AND estado = 'Pagado'";
            try (PreparedStatement ps = conn.prepareStatement(sqlVentasHoy);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblVentasHoy.setText(formatoMoneda.format(rs.getDouble("total")));
                }
            }

            // Ventas del mes
            String sqlVentasMes = "SELECT COALESCE(SUM(total), 0) AS total FROM pedido " +
                                 "WHERE MONTH(fecha) = MONTH(CURDATE()) AND YEAR(fecha) = YEAR(CURDATE()) AND estado = 'Pagado'";
            try (PreparedStatement ps = conn.prepareStatement(sqlVentasMes);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblVentasMes.setText(formatoMoneda.format(rs.getDouble("total")));
                }
            }

            // Pedidos de hoy
            String sqlPedidosHoy = "SELECT COUNT(*) AS total FROM pedido WHERE fecha = CURDATE()";
            try (PreparedStatement ps = conn.prepareStatement(sqlPedidosHoy);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblPedidosHoy.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // Reservas de hoy
            String sqlReservasHoy = "SELECT COUNT(*) AS total FROM reserva WHERE fecha = CURDATE()";
            try (PreparedStatement ps = conn.prepareStatement(sqlReservasHoy);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblReservasHoy.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // Total de clientes
            String sqlClientes = "SELECT COUNT(*) AS total FROM cliente";
            try (PreparedStatement ps = conn.prepareStatement(sqlClientes);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblClientesRegistrados.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // Platos disponibles
            String sqlPlatos = "SELECT COUNT(*) AS total FROM plato WHERE disponible = TRUE";
            try (PreparedStatement ps = conn.prepareStatement(sqlPlatos);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblPlatosDisponibles.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // Facturas pendientes
            String sqlFacturasPendientes = "SELECT COUNT(*) AS total FROM factura WHERE estado = 'Pendiente'";
            try (PreparedStatement ps = conn.prepareStatement(sqlFacturasPendientes);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblFacturasPendientes.setText(String.valueOf(rs.getInt("total")));
                }
            }

            // Top 5 platos
            StringBuilder sbPlatos = new StringBuilder();
            String sqlTopPlatos = "SELECT p.nombre, COUNT(dp.id_detalle) AS veces, SUM(dp.subtotal) AS ingresos " +
                                 "FROM detalle_pedido dp " +
                                 "INNER JOIN plato p ON dp.id_plato = p.id_plato " +
                                 "GROUP BY p.id_plato " +
                                 "ORDER BY veces DESC LIMIT 5";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlTopPlatos);
                 ResultSet rs = ps.executeQuery()) {
                int pos = 1;
                while (rs.next()) {
                    sbPlatos.append(String.format("%d. %s\n", pos++, rs.getString("nombre")));
                    sbPlatos.append(String.format("   Vendido: %d veces | Ingresos: %s\n\n",
                                                 rs.getInt("veces"),
                                                 formatoMoneda.format(rs.getDouble("ingresos"))));
                }
            }
            txaTopPlatos.setText(sbPlatos.toString());

            // Top 5 clientes VIP
            StringBuilder sbClientes = new StringBuilder();
            String sqlTopClientes = "SELECT CONCAT(per.nombre, ' ', per.apellidos) AS cliente, " +
                                   "c.puntos_fidelidad, COUNT(p.id_pedido) AS pedidos " +
                                   "FROM cliente c " +
                                   "INNER JOIN persona per ON c.id_persona = per.id_persona " +
                                   "LEFT JOIN pedido p ON c.id_cliente = p.id_cliente " +
                                   "WHERE c.puntos_fidelidad >= 50 " +
                                   "GROUP BY c.id_cliente " +
                                   "ORDER BY c.puntos_fidelidad DESC LIMIT 5";
            
            try (PreparedStatement ps = conn.prepareStatement(sqlTopClientes);
                 ResultSet rs = ps.executeQuery()) {
                int pos = 1;
                while (rs.next()) {
                    int puntos = rs.getInt("puntos_fidelidad");
                    String nivel = puntos >= 200 ? "VIP" : puntos >= 100 ? "Gold" : "Silver";
                    
                    sbClientes.append(String.format("%d. %s\n", pos++, rs.getString("cliente")));
                    sbClientes.append(String.format("   %s | Puntos: %d | Pedidos: %d\n\n",
                                                   nivel, puntos, rs.getInt("pedidos")));
                }
            }
            txaTopClientes.setText(sbClientes.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar datos del dashboard:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmDashboard().setVisible(true));
    }
}
