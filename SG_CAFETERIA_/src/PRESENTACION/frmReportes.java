package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import DATOS.conexion;
import java.time.LocalDate;

public class frmReportes extends JFrame {

    private JComboBox<String> cbxTipoReporte;
    private JTextField tbxFechaInicio, tbxFechaFin;
    private JTextArea txaResultado;
    private JLabel lblTotalRegistros, lblMontoTotal;

    public frmReportes() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Reportes - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("SISTEMA DE REPORTES");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 800, 30);
        getContentPane().add(lblTitulo);

        JPanel panelFiltros = new JPanel();
        panelFiltros.setBackground(UIConstants.COLOR_PANEL);
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Filtros de Búsqueda",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelFiltros.setBounds(20, 60, 860, 120);
        panelFiltros.setLayout(null);
        getContentPane().add(panelFiltros);

        JLabel lblTipo = new JLabel("Tipo de Reporte:");
        lblTipo.setFont(UIConstants.FUENTE_NORMAL);
        lblTipo.setBounds(20, 35, 120, 25);
        panelFiltros.add(lblTipo);

        cbxTipoReporte = new JComboBox<>(new String[]{
            "Seleccionar...",
            "Ventas del Día",
            "Ventas por Período",
            "Productos Más Vendidos",
            "Clientes Frecuentes",
            "Reservas del Mes",
            "Inventario de Platos",
            "Ventas con Descuentos",
            "Ofertas Vigentes",
            "Clientes VIP (Fidelidad)"
        });
        cbxTipoReporte.setFont(UIConstants.FUENTE_NORMAL);
        cbxTipoReporte.setBounds(140, 35, 250, 28);
        panelFiltros.add(cbxTipoReporte);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setFont(UIConstants.FUENTE_NORMAL);
        lblFechaInicio.setBounds(420, 35, 100, 25);
        panelFiltros.add(lblFechaInicio);

        tbxFechaInicio = new JTextField();
        tbxFechaInicio.setFont(UIConstants.FUENTE_NORMAL);
        tbxFechaInicio.setBounds(520, 35, 120, 28);
        tbxFechaInicio.setText(LocalDate.now().minusDays(30).toString());
        tbxFechaInicio.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelFiltros.add(tbxFechaInicio);

        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setFont(UIConstants.FUENTE_NORMAL);
        lblFechaFin.setBounds(655, 35, 80, 25);
        panelFiltros.add(lblFechaFin);

        tbxFechaFin = new JTextField();
        tbxFechaFin.setFont(UIConstants.FUENTE_NORMAL);
        tbxFechaFin.setBounds(730, 35, 120, 28);
        tbxFechaFin.setText(LocalDate.now().toString());
        tbxFechaFin.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelFiltros.add(tbxFechaFin);

        JButton btnGenerar = new JButton("Generar Reporte");
        UIConstants.estilizarBoton(btnGenerar, UIConstants.COLOR_PRIMARY);
        btnGenerar.setBounds(300, 75, 200, 35);
        btnGenerar.addActionListener(e -> generarReporte());
        panelFiltros.add(btnGenerar);

        JPanel panelResultados = new JPanel();
        panelResultados.setBackground(UIConstants.COLOR_PANEL);
        panelResultados.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Resultados del Reporte",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelResultados.setBounds(20, 195, 860, 350);
        panelResultados.setLayout(new BorderLayout());
        getContentPane().add(panelResultados);

        txaResultado = new JTextArea();
        txaResultado.setEditable(false);
        txaResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txaResultado);
        panelResultados.add(scroll, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBackground(new Color(250, 250, 255));
        panelEstadisticas.setLayout(null);
        panelEstadisticas.setBounds(20, 555, 860, 50);
        panelEstadisticas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        getContentPane().add(panelEstadisticas);

        lblTotalRegistros = new JLabel("Total de Registros: 0");
        lblTotalRegistros.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalRegistros.setForeground(UIConstants.COLOR_PRIMARY);
        lblTotalRegistros.setBounds(30, 10, 300, 30);
        panelEstadisticas.add(lblTotalRegistros);

        lblMontoTotal = new JLabel("Monto Total: $0.00");
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMontoTotal.setForeground(UIConstants.COLOR_SUCCESS);
        lblMontoTotal.setBounds(500, 10, 300, 30);
        panelEstadisticas.add(lblMontoTotal);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(780, 615, 100, 30);
        btnCerrar.addActionListener(e -> dispose());
        getContentPane().add(btnCerrar);
    }

    private void generarReporte() {
        String tipoReporte = (String) cbxTipoReporte.getSelectedItem();
        String fechaInicio = tbxFechaInicio.getText().trim();
        String fechaFin = tbxFechaFin.getText().trim();

        if (tipoReporte == null || tipoReporte.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un tipo de reporte.",
                "Reporte no seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate.parse(fechaInicio);
            LocalDate.parse(fechaFin);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Formato de fecha inválido.\nUse el formato: YYYY-MM-DD",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        int totalRegistros = 0;
        double montoTotal = 0.0;

        Connection conexionDB = null;
        try {
            conexionDB = conexion.conectar();
            
            if (conexionDB == null) {
                JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "";
            
            switch (tipoReporte) {
                case "Ventas del Día":
                    sql = "SELECT p.id_pedido, CONCAT(per.nombre, ' ', per.apellidos) AS cliente, " +
                          "p.fecha, p.hora, p.total, p.estado " +
                          "FROM pedido p " +
                          "INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
                          "INNER JOIN persona per ON c.id_persona = per.id_persona " +
                          "WHERE p.fecha = CURDATE() AND p.estado IN ('Pagado', 'Servido') " +
                          "ORDER BY p.hora DESC";
                    break;

                case "Ventas por Período":
                    sql = "select * from vista_pedidos_por_fecha;";
                    break;

                case "Productos Más Vendidos":
                    sql = "SELECT * FROM v_productos_mas_vendidos vpmv;";
                    break;

                case "Clientes Frecuentes":
                    sql = "select * from vista_clientes_top;";
                    break;

                case "Reservas del Mes":
                    sql = "SELECT * FROM vista_reservas_mes_actual;";
                    break;

                case "Inventario de Platos":
                    sql = "select * from vista_platos_categorias;";
                    break;

                case "Ventas con Descuentos":
                    sql = "CALL sp_facturas_con_descuento('2025-11-01', '2025-11-24');";
                    break;

                case "Ofertas Vigentes":
                    sql = "SELECT nombre, descripcion, fecha_inicio, fecha_fin, tipo, " +
                          "porcentaje_descuento, monto_fijo " +
                          "FROM oferta " +
                          "WHERE activa = TRUE AND CURDATE() BETWEEN fecha_inicio AND fecha_fin " +
                          "ORDER BY fecha_inicio";
                    break;

                case "Clientes VIP (Fidelidad)":
                    sql = "select * from v_clientes_vip;";
                    break;

                default:
                   JOptionPane.showMessageDialog(this,
                        "Tipo de reporte no implementado: " + tipoReporte,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
            }

            if (sql == null || sql.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Error: La consulta SQL está vacía.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement ps = conexionDB.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnas = metaData.getColumnCount();

            sb.append("═══════════════════════════════════════════════════════════\n");
            sb.append(String.format("           %s\n", tipoReporte.toUpperCase()));
            sb.append(String.format("     Período: %s al %s\n", fechaInicio, fechaFin));
            sb.append("═══════════════════════════════════════════════════════════\n\n");

            while (rs.next()) {
                totalRegistros++;
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = metaData.getColumnLabel(i);
                    Object valor = rs.getObject(i);
                    sb.append(String.format("%s: %s\n", nombreColumna, 
                        valor != null ? valor.toString() : "N/A"));

                    if (nombreColumna.toLowerCase().contains("total") || 
                        nombreColumna.toLowerCase().contains("monto")) {
                        try {
                            montoTotal += rs.getDouble(i);
                        } catch (Exception ignored) {}
                    }
                }
                sb.append("───────────────────────────────────────\n");
            }

            if (totalRegistros == 0) {
                sb.append("\nNo se encontraron resultados para este reporte.\n");
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            sb.append("Error al generar reporte:\n");
            sb.append(e.getMessage());
            e.printStackTrace();
        } finally {
            conexion.desconectar(conexionDB);
        }

        txaResultado.setText(sb.toString());
        txaResultado.setCaretPosition(0);

        lblTotalRegistros.setText("Total de Registros: " + totalRegistros);
        lblMontoTotal.setText(String.format("Monto Total: $%.2f", montoTotal));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmReportes().setVisible(true));
    }
}
