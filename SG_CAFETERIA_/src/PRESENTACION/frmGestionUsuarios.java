package PRESENTACION;

import DATOS.usuarioDT;
import LOGICA.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class frmGestionUsuarios extends JFrame {

    private JTable tblUsuarios;
    private DefaultTableModel modelo;
    private JButton btnEliminar, btnEditar, btnCerrar, btnBuscar;
    private JTextField txtBuscar;

    public frmGestionUsuarios() {
        setTitle("Gestión de usuarios");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.COLOR_FONDO);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(UIConstants.COLOR_PRIMARY);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblTitulo = new JLabel("Gestión de usuarios");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_TEXTO_CLARO);

        JLabel lblSub = new JLabel("Visualizar, eliminar usuarios o cambiar contraseña");
        lblSub.setFont(UIConstants.FUENTE_SUBTITULO);
        lblSub.setForeground(UIConstants.COLOR_TEXTO_CLARO);

        JPanel pnlTextos = new JPanel(new GridLayout(2, 1));
        pnlTextos.setOpaque(false);
        pnlTextos.add(lblTitulo);
        pnlTextos.add(lblSub);

        pnlHeader.add(pnlTextos, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCentral = new JPanel(new BorderLayout(10, 10));
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        pnlCentral.setBackground(UIConstants.COLOR_FONDO);
        add(pnlCentral, BorderLayout.CENTER);

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Usuario", "Rol", "Empleado", "Activo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios = new JTable(modelo);
        tblUsuarios.setRowHeight(22);
        tblUsuarios.setFont(UIConstants.FUENTE_NORMAL);
        tblUsuarios.getTableHeader().setFont(UIConstants.FUENTE_SUBTITULO);

        JScrollPane scroll = new JScrollPane(tblUsuarios);
        scroll.setBorder(BorderFactory.createTitledBorder("Listado de usuarios"));
        pnlCentral.add(scroll, BorderLayout.CENTER);

        JPanel pnlBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlBuscar.setBackground(UIConstants.COLOR_FONDO);

        JLabel lblBuscar = new JLabel("Buscar usuario:");
        lblBuscar.setFont(UIConstants.FUENTE_NORMAL);

        txtBuscar = new JTextField(20);
        txtBuscar.setFont(UIConstants.FUENTE_NORMAL);

        btnBuscar = new JButton("Filtrar");
        UIConstants.estilizarBoton(btnBuscar, UIConstants.COLOR_PRIMARY);

        pnlBuscar.add(lblBuscar);
        pnlBuscar.add(txtBuscar);
        pnlBuscar.add(btnBuscar);

        pnlCentral.add(pnlBuscar, BorderLayout.NORTH);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.setBackground(UIConstants.COLOR_FONDO);

        btnEliminar = new JButton("Eliminar usuario");
        btnEditar = new JButton("Cambiar contraseña");
        btnCerrar = new JButton("Cerrar");

        UIConstants.estilizarBoton(btnEliminar, UIConstants.COLOR_DANGER);
        UIConstants.estilizarBoton(btnEditar, UIConstants.COLOR_PRIMARY);
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_SUCCESS);

        pnlBotones.add(btnEditar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnCerrar);

        pnlCentral.add(pnlBotones, BorderLayout.SOUTH);

        configurarEventos();
        cargarUsuariosTabla();
    }

    private void configurarEventos() {

        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        btnEditar.addActionListener(e -> cambiarPasswordUsuario());
        btnCerrar.addActionListener(e -> dispose());
        btnBuscar.addActionListener(e -> filtrarPorUsuario());
    }

    private void cargarUsuariosTabla() {
        modelo.setRowCount(0);
        List<Usuario> lista = usuarioDT.listarUsuarios();

        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getUsername(),
                    u.getRol(),
                    u.getIdEmpleado(),
                    u.isActivo()
            });
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int fila = tblUsuarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.");
            return;
        }

        int idUsuario = (int) modelo.getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar usuario con ID " + idUsuario + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            if (usuarioDT.eliminarUsuario(idUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                cargarUsuariosTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.");
            }
        }
    }

    private void cambiarPasswordUsuario() {
        int fila = tblUsuarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return;
        }

        int idUsuario = (int) modelo.getValueAt(fila, 0);

        String nuevaClave = JOptionPane.showInputDialog(
                this,
                "Ingrese la nueva contraseña:",
                "Cambiar contraseña",
                JOptionPane.PLAIN_MESSAGE
        );

        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contraseña inválida.");
            return;
        }

        if (usuarioDT.cambiarPassword(idUsuario, nuevaClave)) {
            JOptionPane.showMessageDialog(this, "Contraseña actualizada.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar.");
        }
    }

    private void filtrarPorUsuario() {
        String filtro = txtBuscar.getText().trim().toLowerCase();

        modelo.setRowCount(0);
        List<Usuario> lista = usuarioDT.listarUsuarios();

        for (Usuario u : lista) {
            if (u.getUsername().toLowerCase().contains(filtro)) {
                modelo.addRow(new Object[]{
                        u.getIdUsuario(),
                        u.getUsername(),
                        u.getRol(),
                        u.getIdEmpleado(),
                        u.isActivo()
                });
            }
        }
    }
}
