package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import LOGICA.*;
import DATOS.*;
import java.util.List;

public class frmPlato extends JFrame {

    private JTextField tbxNombre, tbxPrecio, tbxDescripcion;
    private JComboBox<Categoria> cbxCategoria;
    private JTable tablePlatos;
    private DefaultTableModel modeloTabla;
    private Plato platoSeleccionado;

    public frmPlato() {
        configurarVentana();
        inicializarComponentes();
        cargarCategorias();
        cargarPlatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Platos - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE PLATOS");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 900, 30);
        add(lblTitulo);

        // Panel de datos
        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(UIConstants.COLOR_PANEL);
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos del Plato",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelDatos.setBounds(20, 60, 380, 420);
        panelDatos.setLayout(null);
        add(panelDatos);

        // Campos
        int y = 35;
        crearCampo(panelDatos, "Nombre:", tbxNombre = new JTextField(), 15, y);
        y += 60;

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(UIConstants.FUENTE_NORMAL);
        lblCategoria.setBounds(15, y, 90, 25);
        panelDatos.add(lblCategoria);

        cbxCategoria = new JComboBox<>();
        cbxCategoria.setFont(UIConstants.FUENTE_NORMAL);
        cbxCategoria.setBounds(110, y, 245, 28);
        panelDatos.add(cbxCategoria);
        y += 60;

        crearCampo(panelDatos, "Precio:", tbxPrecio = new JTextField(), 15, y);
        y += 60;

        crearCampo(panelDatos, "Descripción:", tbxDescripcion = new JTextField(), 15, y);

        // Botones
        JButton btnRegistrar = new JButton("Agregar");
        UIConstants.estilizarBoton(btnRegistrar, UIConstants.COLOR_SUCCESS);
        btnRegistrar.setBounds(25, 310, 150, 35);
        btnRegistrar.addActionListener(e -> registrarPlato());
        panelDatos.add(btnRegistrar);

        JButton btnActualizar = new JButton("Actualizar");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(190, 310, 160, 35);
        btnActualizar.addActionListener(e -> actualizarPlato());
        panelDatos.add(btnActualizar);

        JButton btnNuevo = new JButton("Nuevo");
        UIConstants.estilizarBoton(btnNuevo, UIConstants.COLOR_WARNING);
        btnNuevo.setBounds(25, 360, 150, 35);
        btnNuevo.addActionListener(e -> limpiarCampos());
        panelDatos.add(btnNuevo);

        // Panel de tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Lista de Platos",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelTabla.setBounds(420, 60, 560, 420);
        panelTabla.setLayout(new BorderLayout());
        add(panelTabla);

        // Crear tabla
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePlatos = new JTable(modeloTabla);
        tablePlatos.setFont(UIConstants.FUENTE_NORMAL);
        tablePlatos.setRowHeight(28);
        tablePlatos.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);
        tablePlatos.getTableHeader().setBackground(UIConstants.COLOR_PRIMARY);
        tablePlatos.getTableHeader().setForeground(Color.WHITE);
        tablePlatos.setSelectionBackground(new Color(184, 207, 229));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setForeground(Color.BLACK);
        for (int i = 0; i < tablePlatos.getColumnCount(); i++) {
            tablePlatos.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tablePlatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarPlatoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablePlatos);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotonesTabla = new JPanel();
        panelBotonesTabla.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesTabla.setBackground(UIConstants.COLOR_PANEL);
        panelTabla.add(panelBotonesTabla, BorderLayout.SOUTH);

        JButton btnEliminar = new JButton("Eliminar");
        UIConstants.estilizarBoton(btnEliminar, UIConstants.COLOR_DANGER);
        btnEliminar.addActionListener(e -> eliminarPlato());
        panelBotonesTabla.add(btnEliminar);

        JButton btnToggleEstado = new JButton("Cambiar Estado");
        UIConstants.estilizarBoton(btnToggleEstado, UIConstants.COLOR_WARNING);
        btnToggleEstado.addActionListener(e -> cambiarEstadoPlato());
        panelBotonesTabla.add(btnToggleEstado);

        JButton btnActualizarLista = new JButton("Actualizar");
        UIConstants.estilizarBoton(btnActualizarLista, UIConstants.COLOR_PRIMARY);
        btnActualizarLista.setBounds(650, 500, 180, 35);
        btnActualizarLista.addActionListener(e -> cargarPlatos());
        add(btnActualizarLista);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(850, 500, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearCampo(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setBounds(x, y, 90, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 95, y, 245, 28);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panel.add(campo);
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDT.obtenerCategorias();
        cbxCategoria.removeAllItems();
        for (Categoria c : categorias) {
            cbxCategoria.addItem(c);
        }
    }

    private void registrarPlato() {
        if (platoSeleccionado != null) {
            JOptionPane.showMessageDialog(this,
                "Use el botón 'Actualizar' para modificar el plato seleccionado\no presione 'Nuevo' para agregar uno nuevo.",
                "Plato Seleccionado",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String nombre = tbxNombre.getText().trim();
        String precioTxt = tbxPrecio.getText().trim();
        String descripcion = tbxDescripcion.getText().trim();
        Categoria cat = (Categoria) cbxCategoria.getSelectedItem();

        if (nombre.isEmpty() || precioTxt.isEmpty() || cat == null) {
            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.",
                "Campos Incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioTxt);
            if (precio <= 0) throw new NumberFormatException();

            Plato plato = new Plato(0, nombre, cat, descripcion, precio, true);

            if (platosDT.agregarPlato(plato)) {
                JOptionPane.showMessageDialog(this,
                    "Plato registrado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarPlatos();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El precio debe ser un número válido mayor a 0.",
                "Precio Inválido",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPlato() {
        if (platoSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un plato de la tabla para actualizar.",
                "Ningún Plato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = tbxNombre.getText().trim();
        String precioTxt = tbxPrecio.getText().trim();
        String descripcion = tbxDescripcion.getText().trim();
        Categoria cat = (Categoria) cbxCategoria.getSelectedItem();

        if (nombre.isEmpty() || precioTxt.isEmpty() || cat == null) {
            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.",
                "Campos Incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioTxt);
            if (precio <= 0) throw new NumberFormatException();

            platoSeleccionado.setNombre(nombre);
            platoSeleccionado.setCategoria(cat);
            platoSeleccionado.setDescripcion(descripcion);
            platoSeleccionado.setPrecio(precio);

            int confirm = JOptionPane.showConfirmDialog(this,
                String.format("¿Actualizar el plato:\n%s\nNuevo precio: $%.2f?", nombre, precio),
                "Confirmar Actualización",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (platosDT.actualizarPlato(platoSeleccionado)) {
                    JOptionPane.showMessageDialog(this,
                        "Plato actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarPlatos();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al actualizar el plato.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El precio debe ser un número válido mayor a 0.",
                "Precio Inválido",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatoSeleccionado() {
        int fila = tablePlatos.getSelectedRow();
        if (fila >= 0) {
            int idPlato = (int) modeloTabla.getValueAt(fila, 0);
            platoSeleccionado = platosDT.buscarPorId(idPlato);

            if (platoSeleccionado != null) {
                tbxNombre.setText(platoSeleccionado.getNombre());
                tbxPrecio.setText(String.valueOf(platoSeleccionado.getPrecio()));
                tbxDescripcion.setText(platoSeleccionado.getDescripcion());

                for (int i = 0; i < cbxCategoria.getItemCount(); i++) {
                    Categoria c = cbxCategoria.getItemAt(i);
                    if (c.getIdCategoria() == platoSeleccionado.getCategoria().getIdCategoria()) {
                        cbxCategoria.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    private void eliminarPlato() {
        int filaSeleccionada = tablePlatos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un plato de la tabla.",
                "Ningún Plato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPlato = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombrePlato = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar el plato:\n" + nombrePlato + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (platosDT.eliminarPlato(idPlato)) {
                JOptionPane.showMessageDialog(this,
                    "Plato eliminado exitosamente.",
                    "Eliminación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarPlatos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar el plato.\nPuede estar asociado a pedidos existentes.",
                    "Error al Eliminar",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cambiarEstadoPlato() {
        int filaSeleccionada = tablePlatos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un plato de la tabla.",
                "Ningún Plato Seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPlato = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        boolean disponibleActual = "✅ Disponible".equals(estadoActual);

        if (platosDT.cambiarEstado(idPlato, !disponibleActual)) {
            JOptionPane.showMessageDialog(this,
                "Estado cambiado exitosamente.",
                "Cambio Exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            cargarPlatos();
        }
    }

    private void cargarPlatos() {
        modeloTabla.setRowCount(0);
        List<Plato> lista = platosDT.listarPlatos();

        for (Plato p : lista) {
            Object[] fila = {
                p.getIdPlato(),
                p.getNombre(),
                p.getCategoria().getNombre(),
                String.format("$%.2f", p.getPrecio()),
                p.isDisponible() ? "✅ Disponible" : "❌ No disponible"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        platoSeleccionado = null;
        tbxNombre.setText("");
        tbxPrecio.setText("");
        tbxDescripcion.setText("");
        if (cbxCategoria.getItemCount() > 0) {
            cbxCategoria.setSelectedIndex(0);
        }
        tablePlatos.clearSelection();
        tbxNombre.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmPlato().setVisible(true));
    }
}
