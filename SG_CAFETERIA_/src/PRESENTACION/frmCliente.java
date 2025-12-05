package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import LOGICA.*;
import DATOS.clienteDT;
import java.util.List;

public class frmCliente extends JFrame {

    private JTextField tbxNombre, tbxApellidos, tbxCI, tbxEdad;
    private JTextArea txaSalida;

    public frmCliente() {
        configurarVentana();
        inicializarComponentes();
        cargarClientes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Clientes - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("GESTIÓN DE CLIENTES");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 650, 30);
        add(lblTitulo);

        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(UIConstants.COLOR_PANEL);
        panelDatos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos del Cliente",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelDatos.setBounds(20, 60, 330, 320);
        panelDatos.setLayout(null);
        add(panelDatos);

        int y = 35;
        int espaciado = 55;

        crearCampoTexto(panelDatos, "Nombre:", tbxNombre = new JTextField(), 15, y);
        y += espaciado;

        crearCampoTexto(panelDatos, "Apellidos:", tbxApellidos = new JTextField(), 15, y);
        y += espaciado;

        crearCampoTexto(panelDatos, "CI:", tbxCI = new JTextField(), 15, y);
        y += espaciado;

        crearCampoTexto(panelDatos, "Edad:", tbxEdad = new JTextField(), 15, y);

        JButton btnRegistrar = new JButton("Registrar");
        UIConstants.estilizarBoton(btnRegistrar, UIConstants.COLOR_SUCCESS);
        btnRegistrar.setBounds(20, 260, 130, 35);
        btnRegistrar.addActionListener(e -> registrarCliente());
        panelDatos.add(btnRegistrar);

        JButton btnLimpiar = new JButton("Limpiar");
        UIConstants.estilizarBoton(btnLimpiar, UIConstants.COLOR_WARNING);
        btnLimpiar.setBounds(170, 260, 130, 35);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panelDatos.add(btnLimpiar);

        JPanel panelLista = new JPanel();
        panelLista.setBackground(UIConstants.COLOR_PANEL);
        panelLista.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Lista de Clientes",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelLista.setBounds(370, 60, 360, 320);
        panelLista.setLayout(new BorderLayout());
        add(panelLista);

        txaSalida = new JTextArea();
        txaSalida.setEditable(false);
        txaSalida.setFont(new Font("Consolas", Font.PLAIN, 12));
        txaSalida.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(txaSalida);
        panelLista.add(scrollPane, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar Lista");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(250, 395, 200, 35);
        btnActualizar.addActionListener(e -> cargarClientes());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(470, 395, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearCampoTexto(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setForeground(UIConstants.COLOR_TEXTO);
        lbl.setBounds(x, y, 90, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 95, y, 200, 28);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panel.add(campo);
    }

    private void registrarCliente() {
        String nombre = tbxNombre.getText().trim();
        String apellidos = tbxApellidos.getText().trim();
        String ci = tbxCI.getText().trim();
        String edadTxt = tbxEdad.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || ci.isEmpty() || edadTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos.",
                "Campos Incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int edad = Integer.parseInt(edadTxt);
            if (edad < 0 || edad > 120) {
                throw new NumberFormatException();
            }

            Persona persona = new Persona(0, nombre, apellidos, ci, edad);
            Cliente cliente = new Cliente(0, persona);

            if (clienteDT.agregarCliente(cliente)) {
                JOptionPane.showMessageDialog(this,
                    "Cliente registrado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el cliente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "La edad debe ser un número válido entre 0 y 120.",
                "Edad Inválida",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarClientes() {
        List<Cliente> lista = clienteDT.listarClientes();
        StringBuilder sb = new StringBuilder();

        if (lista.isEmpty()) {
            sb.append("No hay clientes registrados.");
        } else {
            for (Cliente c : lista) {
                Persona p = c.getPersona();
                sb.append(String.format("ID: %d | %s %s\n", c.getIdCliente(), p.getNombre(), p.getApellidos()));
                sb.append(String.format("CI: %s | Edad: %d años\n", p.getCi(), p.getEdad()));
                sb.append("─────────────────────────────\n");
            }
        }

        txaSalida.setText(sb.toString());
        txaSalida.setCaretPosition(0);
    }

    private void limpiarCampos() {
        tbxNombre.setText("");
        tbxApellidos.setText("");
        tbxCI.setText("");
        tbxEdad.setText("");
        tbxNombre.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmCliente().setVisible(true));
    }
}