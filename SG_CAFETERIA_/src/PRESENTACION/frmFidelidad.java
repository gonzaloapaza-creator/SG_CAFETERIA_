package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import LOGICA.Cliente;
import DATOS.clienteDT;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class frmFidelidad extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalClientes, lblClientesVIP;

    public frmFidelidad() {
        configurarVentana();
        inicializarComponentes();
        cargarClientesFrecuentes();
    }

    private void configurarVentana() {
        setTitle("Programa de Fidelidad - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("PROGRAMA DE FIDELIDAD");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 850, 35);
        getContentPane().add(lblTitulo);

        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(245, 247, 250));
        panelInfo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelInfo.setBounds(50, 65, 850, 100);
        panelInfo.setLayout(null);
        getContentPane().add(panelInfo);

        JLabel lblInfoTitulo = new JLabel("Niveles de Fidelidad");
        lblInfoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfoTitulo.setForeground(new Color(52, 73, 94));
        lblInfoTitulo.setBounds(20, 10, 300, 25);
        panelInfo.add(lblInfoTitulo);

        JLabel lblNivel1 = new JLabel("🥉 Silver (50-99 pts): 5% de descuento");
        lblNivel1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNivel1.setBounds(30, 40, 250, 20);
        panelInfo.add(lblNivel1);

        JLabel lblNivel2 = new JLabel("🥈 Gold (100-199 pts): 10% de descuento");
        lblNivel2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNivel2.setBounds(300, 40, 260, 20);
        panelInfo.add(lblNivel2);

        JLabel lblNivel3 = new JLabel("🥇 VIP (200+ pts): 15% de descuento");
        lblNivel3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNivel3.setBounds(580, 40, 240, 20);
        panelInfo.add(lblNivel3);

        JLabel lblAcumulacion = new JLabel("💰 Se gana 1 punto por cada $10 gastados");
        lblAcumulacion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAcumulacion.setForeground(new Color(127, 140, 141));
        lblAcumulacion.setBounds(30, 65, 400, 20);
        panelInfo.add(lblAcumulacion);

        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Top 20 Clientes Frecuentes",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTabla.setBounds(50, 185, 850, 370);
        panelTabla.setLayout(new BorderLayout());
        getContentPane().add(panelTabla);

        String[] columnas = {"#", "Cliente", "CI", "Puntos", "Nivel", "Descuento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFont(UIConstants.FUENTE_NORMAL);
        tablaClientes.setRowHeight(28);
        tablaClientes.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);
        tablaClientes.getTableHeader().setBackground(UIConstants.COLOR_PRIMARY);
        tablaClientes.getTableHeader().setForeground(Color.WHITE);
        tablaClientes.setSelectionBackground(new Color(184, 207, 229));
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.BLACK);
        for (int i = 0; i < tablaClientes.getColumnCount(); i++) {
            tablaClientes.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBackground(new Color(250, 250, 255));
        panelEstadisticas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelEstadisticas.setBounds(50, 570, 850, 40);
        panelEstadisticas.setLayout(null);
        getContentPane().add(panelEstadisticas);

        lblTotalClientes = new JLabel("Total de clientes: 0");
        lblTotalClientes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalClientes.setForeground(UIConstants.COLOR_PRIMARY);
        lblTotalClientes.setBounds(30, 10, 300, 20);
        panelEstadisticas.add(lblTotalClientes);

        lblClientesVIP = new JLabel("Clientes VIP: 0");
        lblClientesVIP.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblClientesVIP.setForeground(UIConstants.COLOR_SUCCESS);
        lblClientesVIP.setBounds(500, 10, 300, 20);
        panelEstadisticas.add(lblClientesVIP);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(780, 615, 120, 30);
        btnCerrar.addActionListener(e -> dispose());
        getContentPane().add(btnCerrar);

        JButton btnActualizar = new JButton("🔄 Actualizar");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(630, 615, 130, 30);
        btnActualizar.addActionListener(e -> cargarClientesFrecuentes());
        getContentPane().add(btnActualizar);
    }

    private void cargarClientesFrecuentes() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = clienteDT.obtenerClientesFrecuentes(20);

        int totalClientes = clientes.size();
        int clientesVIP = 0;

        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            int puntos = clienteDT.obtenerPuntos(c.getIdCliente());

            String nivel;
            String descuento;

            if (puntos >= 200) {
                nivel = "VIP";
                descuento = "15%";
                clientesVIP++;
            } else if (puntos >= 100) {
                nivel = "Gold";
                descuento = "10%";
            } else if (puntos >= 50) {
                nivel = "Silver";
                descuento = "5%";
            } else {
                nivel = "Básico";
                descuento = "0%";
            }

            modeloTabla.addRow(new Object[]{
                (i + 1),
                c.getPersona().getNombre() + " " + c.getPersona().getApellidos(),
                c.getPersona().getCi(),
                puntos,
                nivel,
                descuento
            });
        }

        lblTotalClientes.setText("Total de clientes: " + totalClientes);
        lblClientesVIP.setText("Clientes VIP: " + clientesVIP);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmFidelidad().setVisible(true));
    }
}
