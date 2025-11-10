package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import LOGICA.Oferta;
import DATOS.ofertaDT;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class frmOfertas extends JFrame {

    private JTextField tbxNombre, tbxDescripcion, tbxFechaInicio, tbxFechaFin, tbxPorcentaje;
    private JComboBox<String> cbxTipo;
    private JTable tablaOfertas;
    private DefaultTableModel modeloTabla;

    public frmOfertas() {
        configurarVentana();
        inicializarComponentes();
        cargarOfertas();
    }

    private void configurarVentana() {
        setTitle("Gestión de Ofertas y Promociones - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE OFERTAS Y PROMOCIONES");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 900, 30);
        add(lblTitulo);

        // Panel de registro
        JPanel panelRegistro = new JPanel();
        panelRegistro.setBackground(UIConstants.COLOR_PANEL);
        panelRegistro.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Crear Nueva Oferta",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelRegistro.setBounds(20, 60, 450, 450);
        panelRegistro.setLayout(null);
        add(panelRegistro);

        // Campos
        int y = 35;
        crearCampo(panelRegistro, "Nombre:", tbxNombre = new JTextField(), 15, y);
        y += 60;

        crearCampo(panelRegistro, "Descripción:", tbxDescripcion = new JTextField(), 15, y);
        y += 60;

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(UIConstants.FUENTE_NORMAL);
        lblTipo.setBounds(15, y, 110, 25);
        panelRegistro.add(lblTipo);

        cbxTipo = new JComboBox<>(new String[]{"Descuento", "Combo", "Fidelidad", "Festivo"});
        cbxTipo.setFont(UIConstants.FUENTE_NORMAL);
        cbxTipo.setBounds(130, y, 290, 28);
        panelRegistro.add(cbxTipo);
        y += 60;

        crearCampo(panelRegistro, "Fecha Inicio:", tbxFechaInicio = new JTextField(), 15, y);
        tbxFechaInicio.setText(LocalDate.now().toString());
        y += 60;

        crearCampo(panelRegistro, "Fecha Fin:", tbxFechaFin = new JTextField(), 15, y);
        tbxFechaFin.setText(LocalDate.now().plusDays(7).toString());
        y += 60;

        crearCampo(panelRegistro, "Descuento (%):", tbxPorcentaje = new JTextField(), 15, y);

        // Botones
        JButton btnRegistrar = new JButton("✓ Crear Oferta");
        UIConstants.estilizarBoton(btnRegistrar, UIConstants.COLOR_SUCCESS);
        btnRegistrar.setBounds(30, 390, 180, 35);
        btnRegistrar.addActionListener(e -> registrarOferta());
        panelRegistro.add(btnRegistrar);

        JButton btnLimpiar = new JButton("Limpiar");
        UIConstants.estilizarBoton(btnLimpiar, UIConstants.COLOR_WARNING);
        btnLimpiar.setBounds(230, 390, 180, 35);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelRegistro.add(btnLimpiar);

        // Panel de tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Ofertas Registradas",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTabla.setBounds(490, 60, 490, 450);
        panelTabla.setLayout(new BorderLayout());
        add(panelTabla);

        String[] columnas = {"ID", "Nombre", "Tipo", "Inicio", "Fin", "Descuento %"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaOfertas = new JTable(modeloTabla);
        tablaOfertas.setFont(UIConstants.FUENTE_NORMAL);
        tablaOfertas.setRowHeight(25);
        tablaOfertas.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);
        tablaOfertas.getTableHeader().setBackground(UIConstants.COLOR_PRIMARY);
        tablaOfertas.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.BLACK);
        for (int i = 0; i < tablaOfertas.getColumnCount(); i++) {
            tablaOfertas.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        
        
        
        
        JScrollPane scrollPane = new JScrollPane(tablaOfertas);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Botones de tabla
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.add(panelBotones, BorderLayout.SOUTH);

        JButton btnEliminar = new JButton("🗑️ Eliminar");
        UIConstants.estilizarBoton(btnEliminar, UIConstants.COLOR_DANGER);
        btnEliminar.addActionListener(e -> eliminarOferta());
        panelBotones.add(btnEliminar);

        // Botones inferiores
        JButton btnActualizar = new JButton("Actualizar Lista");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(600, 530, 200, 35);
        btnActualizar.addActionListener(e -> cargarOfertas());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(820, 530, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearCampo(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setBounds(x, y, 110, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 115, y, 290, 28);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panel.add(campo);
    }

    private void registrarOferta() {
        try {
            String nombre = tbxNombre.getText().trim();
            String descripcion = tbxDescripcion.getText().trim();
            String tipo = (String) cbxTipo.getSelectedItem();
            LocalDate fechaInicio = LocalDate.parse(tbxFechaInicio.getText().trim());
            LocalDate fechaFin = LocalDate.parse(tbxFechaFin.getText().trim());
            double porcentaje = Double.parseDouble(tbxPorcentaje.getText().trim());

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Complete todos los campos.",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (fechaFin.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(this,
                    "La fecha de fin debe ser posterior a la fecha de inicio.",
                    "Fechas Inválidas",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Oferta oferta = new Oferta(0, nombre, descripcion, fechaInicio, fechaFin, 
                                       tipo, porcentaje, 0, true);

            if (ofertaDT.agregarOferta(oferta)) {
                JOptionPane.showMessageDialog(this,
                    "Oferta creada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarOfertas();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al crear oferta: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarOferta() {
        int fila = tablaOfertas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una oferta de la tabla.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idOferta = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la oferta: " + nombre + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION && ofertaDT.eliminarOferta(idOferta)) {
            JOptionPane.showMessageDialog(this, "Oferta eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarOfertas();
        }
    }

    private void cargarOfertas() {
        modeloTabla.setRowCount(0);
        List<Oferta> ofertas = ofertaDT.listarOfertas();

        for (Oferta o : ofertas) {
            modeloTabla.addRow(new Object[]{
                o.getIdOferta(),
                o.getNombre(),
                o.getTipo(),
                o.getFechaInicio(),
                o.getFechaFin(),
                o.getPorcentajeDescuento() + "%"
            });
        }
    }

    private void limpiarCampos() {
        tbxNombre.setText("");
        tbxDescripcion.setText("");
        tbxPorcentaje.setText("");
        tbxFechaInicio.setText(LocalDate.now().toString());
        tbxFechaFin.setText(LocalDate.now().plusDays(7).toString());
        cbxTipo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmOfertas().setVisible(true));
    }
}
