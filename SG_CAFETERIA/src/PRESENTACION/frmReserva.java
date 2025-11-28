package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.*;
import DATOS.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class frmReserva extends JFrame {

    private JTextField tbxCI, tbxNombre, tbxApellidos, tbxEdad, tbxPersonas, tbxFecha, tbxHora;
    private JComboBox<String> cbxMesa;
    private JTextArea txaSalida;
    private JButton btnBuscar;
    private Cliente clienteActual;

    public frmReserva() {
        configurarVentana();
        inicializarComponentes();
        cargarMesas();
        cargarReservas();
    }

    private void configurarVentana() {
        setTitle("Gestión de Reservas - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE RESERVAS");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 750, 30);
        add(lblTitulo);

        JPanel panelCliente = new JPanel();
        panelCliente.setBackground(UIConstants.COLOR_PANEL);
        panelCliente.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos del Cliente",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelCliente.setBounds(20, 60, 400, 200);
        panelCliente.setLayout(null);
        add(panelCliente);

        JLabel lblCI = new JLabel("CI:");
        lblCI.setFont(UIConstants.FUENTE_NORMAL);
        lblCI.setBounds(15, 30, 90, 25);
        panelCliente.add(lblCI);

        tbxCI = new JTextField();
        tbxCI.setFont(UIConstants.FUENTE_NORMAL);
        tbxCI.setBounds(110, 30, 150, 28);
        tbxCI.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelCliente.add(tbxCI);

        btnBuscar = new JButton("🔍 Buscar");
        UIConstants.estilizarBoton(btnBuscar, UIConstants.COLOR_PRIMARY);
        btnBuscar.setBounds(270, 30, 100, 28);
        btnBuscar.addActionListener(e -> buscarCliente());
        panelCliente.add(btnBuscar);

        crearCampo(panelCliente, "Nombre:", tbxNombre = new JTextField(), 15, 70);

        crearCampo(panelCliente, "Apellidos:", tbxApellidos = new JTextField(), 15, 110);

        crearCampo(panelCliente, "Edad:", tbxEdad = new JTextField(), 15, 150);

        // Panel de datos de reserva
        JPanel panelReserva = new JPanel();
        panelReserva.setBackground(UIConstants.COLOR_PANEL);
        panelReserva.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos de la Reserva",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelReserva.setBounds(20, 275, 400, 190);
        panelReserva.setLayout(null);
        add(panelReserva);

        JLabel lblMesa = new JLabel("Mesa:");
        lblMesa.setFont(UIConstants.FUENTE_NORMAL);
        lblMesa.setBounds(15, 30, 90, 25);
        panelReserva.add(lblMesa);

        cbxMesa = new JComboBox<>();
        cbxMesa.setFont(UIConstants.FUENTE_NORMAL);
        cbxMesa.setBounds(110, 30, 260, 28);
        panelReserva.add(cbxMesa);

        crearCampo(panelReserva, "Personas:", tbxPersonas = new JTextField(), 15, 70);

        crearCampo(panelReserva, "Fecha:", tbxFecha = new JTextField(), 15, 110);
        tbxFecha.setText(LocalDate.now().toString());

        crearCampo(panelReserva, "Hora:", tbxHora = new JTextField(), 15, 150);
        tbxHora.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        JPanel panelLista = new JPanel();
        panelLista.setBackground(UIConstants.COLOR_PANEL);
        panelLista.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Reservas Registradas",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelLista.setBounds(440, 60, 390, 405);
        panelLista.setLayout(new BorderLayout());
        add(panelLista);

        txaSalida = new JTextArea();
        txaSalida.setEditable(false);
        txaSalida.setFont(new Font("Consolas", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(txaSalida);
        panelLista.add(scroll, BorderLayout.CENTER);

        // Botones inferiores
        JButton btnRegistrar = new JButton("✓ Confirmar Reserva");
        UIConstants.estilizarBoton(btnRegistrar, UIConstants.COLOR_SUCCESS);
        btnRegistrar.setBounds(20, 480, 180, 35);
        btnRegistrar.addActionListener(e -> registrarReserva());
        add(btnRegistrar);

        JButton btnLimpiar = new JButton("Limpiar");
        UIConstants.estilizarBoton(btnLimpiar, UIConstants.COLOR_WARNING);
        btnLimpiar.setBounds(220, 480, 120, 35);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        add(btnLimpiar);

        JButton btnActualizar = new JButton("Actualizar");
        UIConstants.estilizarBoton(btnActualizar, UIConstants.COLOR_PRIMARY);
        btnActualizar.setBounds(560, 480, 130, 35);
        btnActualizar.addActionListener(e -> cargarReservas());
        add(btnActualizar);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(710, 480, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);

        tbxCI.addActionListener(e -> buscarCliente());
    }

    private void crearCampo(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setBounds(x, y, 90, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 95, y, 260, 28);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panel.add(campo);
    }

    
    private void buscarCliente() {
        String ci = tbxCI.getText().trim();
        
        if (ci.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese un CI.",
                "CI Requerido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        clienteActual = clienteDT.buscarPorCI(ci);

        if (clienteActual != null) {
            // Cliente encontrado - autocompletar
            Persona p = clienteActual.getPersona();
            tbxNombre.setText(p.getNombre());
            tbxApellidos.setText(p.getApellidos());
            tbxEdad.setText(String.valueOf(p.getEdad()));
            
            // Deshabilitar campos
            tbxNombre.setEditable(false);
            tbxApellidos.setEditable(false);
            tbxEdad.setEditable(false);
            
            tbxNombre.setBackground(new Color(240, 240, 240));
            tbxApellidos.setBackground(new Color(240, 240, 240));
            tbxEdad.setBackground(new Color(240, 240, 240));
            
            // NUEVO: Sugerir mesa habitual
            int idMesaHabitual = reservaDT.obtenerMesaHabitual(clienteActual.getIdCliente());
            int numReservas = reservaDT.contarReservasCliente(clienteActual.getIdCliente());
            
            String mensaje = "Cliente frecuente: " + p.getNombre() + " " + p.getApellidos() + 
                            "\nTotal de reservas: " + numReservas;
            
            if (idMesaHabitual > 0) {
                // Buscar y seleccionar la mesa habitual en el combo
                for (int i = 0; i < cbxMesa.getItemCount(); i++) {
                    String item = cbxMesa.getItemAt(i);
                    if (item.startsWith(idMesaHabitual + " -")) {
                        cbxMesa.setSelectedIndex(i);
                        mensaje += "\n\n⭐ Se recomienda su mesa habitual (ID: " + idMesaHabitual + ")";
                        break;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this,
                mensaje,
                "Cliente Registrado",
                JOptionPane.INFORMATION_MESSAGE);
                
        } else {
            // Cliente nuevo - habilitar campos
            tbxNombre.setText("");
            tbxApellidos.setText("");
            tbxEdad.setText("");
            
            tbxNombre.setEditable(true);
            tbxApellidos.setEditable(true);
            tbxEdad.setEditable(true);
            
            tbxNombre.setBackground(Color.WHITE);
            tbxApellidos.setBackground(Color.WHITE);
            tbxEdad.setBackground(Color.WHITE);
            
            JOptionPane.showMessageDialog(this,
                "Cliente nuevo. Por favor complete los datos.",
                "Nuevo Cliente",
                JOptionPane.INFORMATION_MESSAGE);
                
            tbxNombre.requestFocus();
        }
    }

    
    
    private void registrarReserva() {
        String ci = tbxCI.getText().trim();
        String nombre = tbxNombre.getText().trim();
        String apellidos = tbxApellidos.getText().trim();
        String edadTxt = tbxEdad.getText().trim();
        String personasTxt = tbxPersonas.getText().trim();

        if (ci.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || 
            edadTxt.isEmpty() || personasTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Complete todos los campos obligatorios.",
                "Datos Incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (clienteActual == null) {
                int edad = Integer.parseInt(edadTxt);
                Persona persona = new Persona(0, nombre, apellidos, ci, edad);
                clienteActual = new Cliente(0, persona);
                
                if (!clienteDT.agregarCliente(clienteActual)) {
                    JOptionPane.showMessageDialog(this,
                        "Error al registrar el cliente.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(this,
                    "Cliente registrado exitosamente.",
                    "Nuevo Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
            }

            if (cbxMesa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una mesa.", "Mesa Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idMesa = Integer.parseInt(cbxMesa.getSelectedItem().toString().split(" - ")[0]);
            int personas = Integer.parseInt(personasTxt);
            LocalDate fecha = LocalDate.parse(tbxFecha.getText().trim());
            LocalTime hora = LocalTime.parse(tbxHora.getText().trim());

            Mesa mesa = new Mesa(idMesa);
            Reserva reserva = new Reserva(0, clienteActual, mesa, fecha, hora, personas, 0);

            if (reservaDT.agregarReserva(reserva)) {
                JOptionPane.showMessageDialog(this,
                    "Reserva registrada exitosamente para:\n" + 
                    nombre + " " + apellidos + "\nMesa " + idMesa + " - " + fecha + " " + hora,
                    "Reserva Confirmada",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarReservas();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMesas() {
        List<Mesa> mesas = mesaDT.obtenerMesas();
        cbxMesa.removeAllItems();
        for (Mesa m : mesas) {
            cbxMesa.addItem(m.getIdMesa() + " - Mesa " + m.getNumeroMesa() + " (Cap: " + m.getCapacidad() + ")");
        }
    }

    private void cargarReservas() {
        List<Reserva> lista = reservaDT.listarReservas();
        StringBuilder sb = new StringBuilder();

        if (lista.isEmpty()) {
            sb.append("No hay reservas registradas.");
        } else {
            for (Reserva r : lista) {
                sb.append(String.format("═══ Reserva #%d ═══\n", r.getIdReserva()));
                sb.append(String.format("Mesa: %d | Personas: %d\n", 
                    r.getMesa().getIdMesa(), r.getPersonas()));
                sb.append(String.format("Fecha: %s a las %s\n", 
                    r.getFecha(), r.getHora()));
                sb.append("─────────────────────────\n");
            }
        }

        txaSalida.setText(sb.toString());
        txaSalida.setCaretPosition(0);
    }

    private void limpiarCampos() {
        clienteActual = null;
        tbxCI.setText("");
        tbxNombre.setText("");
        tbxApellidos.setText("");
        tbxEdad.setText("");
        tbxPersonas.setText("");
        tbxFecha.setText(LocalDate.now().toString());
        tbxHora.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        
        tbxNombre.setEditable(true);
        tbxApellidos.setEditable(true);
        tbxEdad.setEditable(true);
        
        tbxNombre.setBackground(Color.WHITE);
        tbxApellidos.setBackground(Color.WHITE);
        tbxEdad.setBackground(Color.WHITE);
        
        if (cbxMesa.getItemCount() > 0) {
            cbxMesa.setSelectedIndex(0);
        }
        
        tbxCI.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmReserva().setVisible(true));
    }
}
