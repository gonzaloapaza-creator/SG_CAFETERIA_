package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import LOGICA.Ingrediente;
import DATOS.inventarioDT;
import java.util.List;

public class frmInventario extends JFrame {

    private JTextField tbxNombre, tbxStockActual, tbxStockMinimo, tbxUnidad, tbxPrecio;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;

    public frmInventario() {
        configurarVentana();
        inicializarComponentes();
        cargarIngredientes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Inventario - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE INVENTARIO");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 950, 30);
        add(lblTitulo);

        // Panel de datos
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(UIConstants.COLOR_PANEL);
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos del Ingrediente",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelDatos.setBounds(20, 60, 400, 420);
        panelDatos.setLayout(null);
        add(panelDatos);

        // Campos
        int y = 35;
        crearCampo(panelDatos, "Nombre:", tbxNombre = new JTextField(), 15, y);
        y += 60;

        crearCampo(panelDatos, "Stock Actual:", tbxStockActual = new JTextField(), 15, y);
        y += 60;

        crearCampo(panelDatos, "Stock Mínimo:", tbxStockMinimo = new JTextField(), 15, y);
        y += 60;

        crearCampo(panelDatos, "Unidad:", tbxUnidad = new JTextField(), 15, y);
        tbxUnidad.setText("unidad");
        y += 60;

        crearCampo(panelDatos, "Precio Unit.:", tbxPrecio = new JTextField(), 15, y);

        // Botones
        JButton btnAgregar = new JButton("✓ Agregar");
        UIConstants.estilizarBoton(btnAgregar, UIConstants.COLOR_SUCCESS);
        btnAgregar.setBounds(25, 360, 150, 35);
        btnAgregar.addActionListener(e -> agregarIngrediente());
        panelDatos.add(btnAgregar);

        JButton btnLimpiar = new JButton("Limpiar");
        UIConstants.estilizarBoton(btnLimpiar, UIConstants.COLOR_WARNING);
        btnLimpiar.setBounds(200, 360, 160, 35);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelDatos.add(btnLimpiar);

        // Panel de tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Inventario de Ingredientes",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTabla.setBounds(440, 60, 590, 420);
        panelTabla.setLayout(new BorderLayout());
        add(panelTabla);

        String[] columnas = {"ID", "Nombre", "Stock", "Mínimo", "Unidad", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setFont(UIConstants.FUENTE_NORMAL);
        tablaIngredientes.setRowHeight(28);
        tablaIngredientes.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);
        tablaIngredientes.getTableHeader().setBackground(UIConstants.COLOR_PRIMARY);
        tablaIngredientes.getTableHeader().setForeground(Color.WHITE);
        tablaIngredientes.setSelectionBackground(new Color(184, 207, 229));

        // Texto negro en columnas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.BLACK);
        for (int i = 0; i < tablaIngredientes.getColumnCount(); i++) {
            tablaIngredientes.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaIngredientes);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Botones inferiores
        JButton btnAlertas = new JButton("🚨 Ver Alertas");
        UIConstants.estilizarBoton(btnAlertas, UIConstants.COLOR_DANGER);
        btnAlertas.setBounds(550, 500, 200, 35);
        btnAlertas.addActionListener(e -> mostrarAlertas());
        add(btnAlertas);

        JButton btnActualizar = new JButton("🔄 Actualizar");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(770, 500, 150, 35);
        btnActualizar.addActionListener(e -> cargarIngredientes());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(940, 500, 90, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearCampo(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setBounds(x, y, 110, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 115, y, 250, 28);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panel.add(campo);
    }

    private void agregarIngrediente() {
        try {
            String nombre = tbxNombre.getText().trim();
            double stockActual = Double.parseDouble(tbxStockActual.getText().trim());
            double stockMinimo = Double.parseDouble(tbxStockMinimo.getText().trim());
            String unidad = tbxUnidad.getText().trim();
            double precio = Double.parseDouble(tbxPrecio.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio.",
                    "Campo Requerido",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            Ingrediente ing = new Ingrediente(0, nombre, stockActual, stockMinimo, unidad, precio);

            if (inventarioDT.agregarIngrediente(ing)) {
                JOptionPane.showMessageDialog(this,
                    "Ingrediente agregado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarIngredientes();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Ingrese valores numéricos válidos.",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarIngredientes() {
        modeloTabla.setRowCount(0);
        List<Ingrediente> lista = inventarioDT.listarIngredientes();

        for (Ingrediente ing : lista) {
            String estado = ing.estaPorAgotarse() ? "🚨 BAJO" : "✅ OK";
            modeloTabla.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                String.format("%.2f", ing.getStockActual()),
                String.format("%.2f", ing.getStockMinimo()),
                ing.getUnidad(),
                estado
            });
        }
    }

    private void mostrarAlertas() {
        List<Ingrediente> alertas = inventarioDT.obtenerIngredientesPorAgotarse();

        if (alertas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "✅ No hay ingredientes con stock bajo.",
                "Sin Alertas",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("🚨 INGREDIENTES POR AGOTARSE\n\n");
            mensaje.append("═══════════════════════════════════════\n");

            for (Ingrediente ing : alertas) {
                mensaje.append(String.format("• %s\n", ing.getNombre()));
                mensaje.append(String.format("  Stock actual: %.2f %s\n", 
                    ing.getStockActual(), ing.getUnidad()));
                mensaje.append(String.format("  Stock mínimo: %.2f %s\n\n", 
                    ing.getStockMinimo(), ing.getUnidad()));
            }

            JOptionPane.showMessageDialog(this,
                mensaje.toString(),
                "Alertas de Inventario",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCampos() {
        tbxNombre.setText("");
        tbxStockActual.setText("");
        tbxStockMinimo.setText("");
        tbxUnidad.setText("unidad");
        tbxPrecio.setText("");
        tbxNombre.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmInventario().setVisible(true));
    }
}
