package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.*;
import DATOS.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class frmFactura extends JFrame {

    private JTextField tbxBuscarFactura;
    private JComboBox<String> cbxMetodoPago;
    private JTextArea txaDetalle, txaListado;
    private JLabel lblSubtotal, lblImpuesto, lblTotal;

    public frmFactura() {
        configurarVentana();
        inicializarComponentes();
        cargarFacturas();
    }

    private void configurarVentana() {
        setTitle("Sistema de Facturación - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("SISTEMA DE FACTURACIÓN");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 800, 30);
        add(lblTitulo);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(UIConstants.COLOR_PANEL);
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Buscar Factura",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelBusqueda.setBounds(20, 60, 420, 100);
        panelBusqueda.setLayout(null);
        add(panelBusqueda);

        JLabel lblBuscar = new JLabel("N° Factura:");
        lblBuscar.setFont(UIConstants.FUENTE_NORMAL);
        lblBuscar.setBounds(15, 35, 100, 25);
        panelBusqueda.add(lblBuscar);

        tbxBuscarFactura = new JTextField();
        tbxBuscarFactura.setFont(UIConstants.FUENTE_NORMAL);
        tbxBuscarFactura.setBounds(110, 35, 180, 28);
        tbxBuscarFactura.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelBusqueda.add(tbxBuscarFactura);

        JButton btnBuscar = new JButton("Buscar");
        UIConstants.estilizarBoton(btnBuscar, UIConstants.COLOR_PRIMARY);
        btnBuscar.setBounds(300, 35, 100, 28);
        btnBuscar.addActionListener(e -> buscarFactura());
        panelBusqueda.add(btnBuscar);

        // Panel de detalle de factura
        JPanel panelDetalle = new JPanel();
        panelDetalle.setBackground(UIConstants.COLOR_PANEL);
        panelDetalle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Detalle de Factura",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelDetalle.setBounds(20, 175, 420, 340);
        panelDetalle.setLayout(new BorderLayout());
        add(panelDetalle);

        txaDetalle = new JTextArea();
        txaDetalle.setEditable(false);
        txaDetalle.setFont(new Font("Consolas", Font.PLAIN, 11));
        JScrollPane scrollDetalle = new JScrollPane(txaDetalle);
        panelDetalle.add(scrollDetalle, BorderLayout.CENTER);

        // Panel de totales
        JPanel panelTotales = new JPanel();
        panelTotales.setBackground(UIConstants.COLOR_PANEL);
        panelTotales.setLayout(null);
        panelTotales.setBounds(0, 280, 420, 60);
        panelDetalle.add(panelTotales, BorderLayout.SOUTH);

        lblSubtotal = new JLabel("Subtotal: $0.00");
        lblSubtotal.setFont(UIConstants.FUENTE_NORMAL);
        lblSubtotal.setBounds(15, 5, 180, 20);
        panelTotales.add(lblSubtotal);

        lblImpuesto = new JLabel("Impuesto (13%): $0.00");
        lblImpuesto.setFont(UIConstants.FUENTE_NORMAL);
        lblImpuesto.setBounds(15, 25, 180, 20);
        panelTotales.add(lblImpuesto);

        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(UIConstants.COLOR_SUCCESS);
        lblTotal.setBounds(220, 15, 180, 30);
        panelTotales.add(lblTotal);

        // Panel de listado de facturas
        JPanel panelListado = new JPanel();
        panelListado.setBackground(UIConstants.COLOR_PANEL);
        panelListado.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Facturas Registradas",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelListado.setBounds(460, 60, 420, 455);
        panelListado.setLayout(new BorderLayout());
        add(panelListado);

        txaListado = new JTextArea();
        txaListado.setEditable(false);
        txaListado.setFont(new Font("Consolas", Font.PLAIN, 11));
        JScrollPane scrollListado = new JScrollPane(txaListado);
        panelListado.add(scrollListado, BorderLayout.CENTER);

        // Botones inferiores
        JButton btnActualizar = new JButton("Actualizar Lista");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(560, 530, 150, 35);
        btnActualizar.addActionListener(e -> cargarFacturas());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(730, 530, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);

        // Enter para buscar
        tbxBuscarFactura.addActionListener(e -> buscarFactura());
    }

    private void buscarFactura() {
        String numeroFactura = tbxBuscarFactura.getText().trim();

        if (numeroFactura.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Ingrese un número de factura.",
                "Búsqueda",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Factura factura = facturaDT.buscarPorNumero(numeroFactura);

        if (factura != null) {
            mostrarDetalleFactura(factura);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se encontró la factura: " + numeroFactura,
                "Factura No Encontrada",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarDetalle();
        }
    }

    private void mostrarDetalleFactura(Factura f) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        
        sb.append("═══════════════════════════════════════\n");
        sb.append("           FACTURA ELECTRÓNICA\n");
        sb.append("═══════════════════════════════════════\n\n");
        sb.append(String.format("N° Factura: %s\n", f.getNumeroFactura()));
        sb.append(String.format("Fecha: %s\n", sdf.format(f.getFechaHora())));
        sb.append(String.format("Estado: %s\n", f.getEstado()));
        sb.append(String.format("Método de Pago: %s\n\n", f.getMetodoPago()));
        sb.append("───────────────────────────────────────\n");
        sb.append("           DETALLE DE PRODUCTOS\n");
        sb.append("───────────────────────────────────────\n\n");
        sb.append("(Consultar detalles del pedido en BD)\n\n");
        sb.append("═══════════════════════════════════════\n");

        txaDetalle.setText(sb.toString());

        lblSubtotal.setText(String.format("Subtotal: $%.2f", f.getSubtotal()));
        lblImpuesto.setText(String.format("Impuesto (13%%): $%.2f", f.getImpuesto()));
        lblTotal.setText(String.format("TOTAL: $%.2f", f.getTotal()));
    }

    private void limpiarDetalle() {
        txaDetalle.setText("");
        lblSubtotal.setText("Subtotal: $0.00");
        lblImpuesto.setText("Impuesto (13%): $0.00");
        lblTotal.setText("TOTAL: $0.00");
    }

    private void cargarFacturas() {
        List<Factura> facturas = facturaDT.listarFacturas();
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (facturas.isEmpty()) {
            sb.append("No hay facturas registradas.");
        } else {
            for (Factura f : facturas) {
                sb.append(String.format("══════════════════════════\n"));
                sb.append(String.format("Factura: %s\n", f.getNumeroFactura()));
                sb.append(String.format("Fecha: %s\n", sdf.format(f.getFechaHora())));
                sb.append(String.format("Total: $%.2f\n", f.getTotal()));
                sb.append(String.format("Estado: %s\n", f.getEstado()));
                sb.append(String.format("──────────────────────────\n"));
            }
        }

        txaListado.setText(sb.toString());
        txaListado.setCaretPosition(0);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmFactura().setVisible(true));
    }
}
