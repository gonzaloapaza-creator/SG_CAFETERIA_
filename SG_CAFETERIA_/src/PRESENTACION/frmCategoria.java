package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import LOGICA.Categoria;
import DATOS.categoriaDT;
import java.util.List;

public class frmCategoria extends JFrame {

    private JTextField tbxNombre, tbxDescripcion;
    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;
    private Categoria categoriaSeleccionada;

    public frmCategoria() {
        configurarVentana();
        inicializarComponentes();
        cargarCategorias();
    }

    private void configurarVentana() {
        setTitle("Gestión de Categorías - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("GESTIÓN DE CATEGORÍAS");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 750, 30);
        add(lblTitulo);

        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(UIConstants.COLOR_PANEL);
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos de la Categoría",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelDatos.setBounds(20, 60, 380, 380);
        panelDatos.setLayout(null);
        add(panelDatos);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(UIConstants.FUENTE_NORMAL);
        lblNombre.setBounds(20, 40, 100, 25);
        panelDatos.add(lblNombre);

        tbxNombre = new JTextField();
        tbxNombre.setFont(UIConstants.FUENTE_NORMAL);
        tbxNombre.setBounds(20, 70, 330, 30);
        tbxNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelDatos.add(tbxNombre);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(UIConstants.FUENTE_NORMAL);
        lblDescripcion.setBounds(20, 120, 100, 25);
        panelDatos.add(lblDescripcion);

        tbxDescripcion = new JTextField();
        tbxDescripcion.setFont(UIConstants.FUENTE_NORMAL);
        tbxDescripcion.setBounds(20, 150, 330, 30);
        tbxDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelDatos.add(tbxDescripcion);

        JButton btnAgregar = new JButton("Agregar");
        UIConstants.estilizarBoton(btnAgregar, UIConstants.COLOR_SUCCESS);
        btnAgregar.setBounds(20, 220, 150, 35);
        btnAgregar.addActionListener(e -> agregarCategoria());
        panelDatos.add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(190, 220, 160, 35);
        btnActualizar.addActionListener(e -> actualizarCategoria());
        panelDatos.add(btnActualizar);

        JButton btnNuevo = new JButton("Nuevo");
        UIConstants.estilizarBoton(btnNuevo, UIConstants.COLOR_WARNING);
        btnNuevo.setBounds(20, 270, 150, 35);
        btnNuevo.addActionListener(e -> limpiarCampos());
        panelDatos.add(btnNuevo);

        JButton btnEliminar = new JButton("Eliminar");
        UIConstants.estilizarBoton(btnEliminar, UIConstants.COLOR_DANGER);
        btnEliminar.setBounds(190, 270, 160, 35);
        btnEliminar.addActionListener(e -> eliminarCategoria());
        panelDatos.add(btnEliminar);

        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Categorías Registradas",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTabla.setBounds(420, 60, 410, 380);
        panelTabla.setLayout(new BorderLayout());
        add(panelTabla);

        String[] columnas = {"ID", "Nombre", "N° Platos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setFont(UIConstants.FUENTE_NORMAL);
        tablaCategorias.setRowHeight(25);
        tablaCategorias.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);
        tablaCategorias.getTableHeader().setBackground(UIConstants.COLOR_PRIMARY);
        tablaCategorias.getTableHeader().setForeground(Color.WHITE);
        tablaCategorias.setSelectionBackground(new Color(184, 207, 229));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.BLACK);
        for (int i = 0; i < tablaCategorias.getColumnCount(); i++) {
            tablaCategorias.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tablaCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarCategoriaSeleccionada();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaCategorias);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        JButton btnActualizarLista = new JButton("Actualizar Lista");
        UIConstants.estilizarBoton(btnActualizarLista, UIConstants.COLOR_PRIMARY);
        btnActualizarLista.setBounds(450, 460, 200, 35);
        btnActualizarLista.addActionListener(e -> cargarCategorias());
        add(btnActualizarLista);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(670, 460, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void agregarCategoria() {
        String nombre = tbxNombre.getText().trim();
        String descripcion = tbxDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre es obligatorio.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Categoria categoria = new Categoria(0, nombre, descripcion);

        if (categoriaDT.agregarCategoria(categoria)) {
            JOptionPane.showMessageDialog(this,
                "Categoría agregada exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarCategorias();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al agregar la categoría.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCategoria() {
        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una categoría de la tabla.",
                "Selección Requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = tbxNombre.getText().trim();
        String descripcion = tbxDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre es obligatorio.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        categoriaSeleccionada.setNombre(nombre);
        categoriaSeleccionada.setDescripcion(descripcion);

        if (categoriaDT.actualizarCategoria(categoriaSeleccionada)) {
            JOptionPane.showMessageDialog(this,
                "Categoría actualizada exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarCategorias();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar la categoría.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una categoría de la tabla.",
                "Selección Requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCategoria = (int) modeloTabla.getValueAt(fila, 0);
        String nombreCategoria = (String) modeloTabla.getValueAt(fila, 1);
        int numPlatos = (int) modeloTabla.getValueAt(fila, 2);

        if (numPlatos > 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Esta categoría tiene " + numPlatos + " platos asociados.\n" +
                "¿Está seguro que desea eliminarla?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la categoría: " + nombreCategoria + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (categoriaDT.eliminarCategoria(idCategoria)) {
                JOptionPane.showMessageDialog(this,
                    "Categoría eliminada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar la categoría.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarCategoriaSeleccionada() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila >= 0) {
            int idCategoria = (int) modeloTabla.getValueAt(fila, 0);
            List<Categoria> categorias = categoriaDT.obtenerCategorias();

            for (Categoria c : categorias) {
                if (c.getIdCategoria() == idCategoria) {
                    categoriaSeleccionada = c;
                    tbxNombre.setText(c.getNombre());
                    tbxDescripcion.setText(c.getDescripcion());
                    break;
                }
            }
        }
    }

    private void cargarCategorias() {
        modeloTabla.setRowCount(0);
        List<Categoria> categorias = categoriaDT.obtenerCategorias();

        for (Categoria c : categorias) {
            int numPlatos = categoriaDT.contarPlatosPorCategoria(c.getIdCategoria());
            modeloTabla.addRow(new Object[]{
                c.getIdCategoria(),
                c.getNombre(),
                numPlatos
            });
        }
    }

    private void limpiarCampos() {
        categoriaSeleccionada = null;
        tbxNombre.setText("");
        tbxDescripcion.setText("");
        tablaCategorias.clearSelection();
        tbxNombre.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmCategoria().setVisible(true));
    }
}
