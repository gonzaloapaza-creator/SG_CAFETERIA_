package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.*;
import DATOS.*;
import java.util.List;

public class frmVenta extends JFrame {

    private JTextField tbxCI, tbxNombre, tbxApellidos;
    private JComboBox<String> cbxPlato;
    private JTextField tbxCantidad;
    private JComboBox<String> cbxMetodoPago;
    private JTextField tbxPagado;
    private JSpinner spinnerDividir;
    private JTextArea txaFactura;
    private JButton btnBuscarCliente;
    private JLabel lblSubtotal, lblImpuesto, lblTotal, lblVuelto, lblMontoPersona, lblTiempoEstimado;
    
    private Pedido pedidoActual;
    private Cliente clienteActual;

    public frmVenta() {
        pedidoActual = new Pedido();
        configurarVentana();
        inicializarComponentes();
        cargarPlatos();
        actualizarVistaFactura();
    }

    private void configurarVentana() {
        setTitle("Registrar Venta - Cafetería");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("REGISTRAR VENTA");
        lblTitulo.setFont(UIConstants.FUENTE_TITULO);
        lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 15, 850, 30);
        add(lblTitulo);

        // Panel de datos del cliente
        JPanel panelCliente = new JPanel();
        panelCliente.setBackground(UIConstants.COLOR_PANEL);
        panelCliente.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Datos del Cliente",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelCliente.setBounds(20, 60, 450, 150);
        panelCliente.setLayout(null);
        add(panelCliente);

        JLabel lblCI = new JLabel("CI:");
        lblCI.setFont(UIConstants.FUENTE_NORMAL);
        lblCI.setBounds(15, 30, 90, 25);
        panelCliente.add(lblCI);

        tbxCI = new JTextField();
        tbxCI.setFont(UIConstants.FUENTE_NORMAL);
        tbxCI.setBounds(110, 30, 180, 28);
        tbxCI.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelCliente.add(tbxCI);

        btnBuscarCliente = new JButton("🔍 Buscar");
        UIConstants.estilizarBoton(btnBuscarCliente, UIConstants.COLOR_PRIMARY);
        btnBuscarCliente.setBounds(300, 30, 130, 28);
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        panelCliente.add(btnBuscarCliente);

        // Nombre
        crearCampo(panelCliente, "Nombre:", tbxNombre = new JTextField(), 15, 70);
        
        // Apellidos
        crearCampo(panelCliente, "Apellidos:", tbxApellidos = new JTextField(), 15, 105);

        // Enter para buscar
        tbxCI.addActionListener(e -> buscarCliente());

        // Panel de agregar items
        JPanel panelItems = new JPanel();
        panelItems.setBackground(UIConstants.COLOR_PANEL);
        panelItems.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Agregar Productos",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelItems.setBounds(20, 225, 450, 150);
        panelItems.setLayout(null);
        add(panelItems);

        JLabel lblPlato = new JLabel("Plato:");
        lblPlato.setFont(UIConstants.FUENTE_NORMAL);
        lblPlato.setBounds(15, 30, 90, 25);
        panelItems.add(lblPlato);

        cbxPlato = new JComboBox<>();
        cbxPlato.setFont(UIConstants.FUENTE_NORMAL);
        cbxPlato.setBounds(110, 30, 320, 28);
        panelItems.add(cbxPlato);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(UIConstants.FUENTE_NORMAL);
        lblCantidad.setBounds(15, 70, 90, 25);
        panelItems.add(lblCantidad);

        tbxCantidad = new JTextField();
        tbxCantidad.setFont(UIConstants.FUENTE_NORMAL);
        tbxCantidad.setBounds(110, 70, 100, 28);
        tbxCantidad.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelItems.add(tbxCantidad);

        JButton btnAgregar = new JButton("➕ Agregar");
        UIConstants.estilizarBoton(btnAgregar, UIConstants.COLOR_SUCCESS);
        btnAgregar.setBounds(30, 110, 180, 30);
        btnAgregar.addActionListener(e -> agregarItem());
        panelItems.add(btnAgregar);

        JButton btnQuitar = new JButton("➖ Quitar Último");
        UIConstants.estilizarBoton(btnQuitar, UIConstants.COLOR_WARNING);
        btnQuitar.setBounds(230, 110, 180, 30);
        btnQuitar.addActionListener(e -> quitarUltimo());
        panelItems.add(btnQuitar);

        JPanel panelFactura = new JPanel();
        panelFactura.setBackground(UIConstants.COLOR_PANEL);
        panelFactura.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Detalle de la Venta",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelFactura.setBounds(490, 60, 440, 435);
        panelFactura.setLayout(new BorderLayout());
        add(panelFactura);

        txaFactura = new JTextArea();
        txaFactura.setEditable(false);
        txaFactura.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txaFactura);
        panelFactura.add(scroll, BorderLayout.CENTER);

        JPanel panelTotales = new JPanel();
        panelTotales.setBackground(new Color(250, 250, 255));
        panelTotales.setLayout(null);
        panelTotales.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
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
        lblTotal.setBounds(240, 10, 180, 30);
        panelTotales.add(lblTotal);

        panelFactura.add(panelTotales, BorderLayout.SOUTH);

        JPanel panelFinalizacion = new JPanel();
        panelFinalizacion.setBackground(UIConstants.COLOR_PANEL);
        panelFinalizacion.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Finalizar Venta",
            0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
        ));
        panelFinalizacion.setBounds(20, 390, 450, 270);
        panelFinalizacion.setLayout(null);
        add(panelFinalizacion);

        JLabel lblMetodoPago = new JLabel("Método de Pago:");
        lblMetodoPago.setFont(UIConstants.FUENTE_NORMAL);
        lblMetodoPago.setBounds(15, 30, 130, 25);
        panelFinalizacion.add(lblMetodoPago);

        cbxMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia", "QR"});
        cbxMetodoPago.setFont(UIConstants.FUENTE_NORMAL);
        cbxMetodoPago.setBounds(150, 30, 150, 28);
        panelFinalizacion.add(cbxMetodoPago);

        JLabel lblPagado = new JLabel("Efectivo recibido:");
        lblPagado.setFont(UIConstants.FUENTE_NORMAL);
        lblPagado.setBounds(15, 70, 130, 25);
        panelFinalizacion.add(lblPagado);

        tbxPagado = new JTextField("0");
        tbxPagado.setFont(UIConstants.FUENTE_NORMAL);
        tbxPagado.setBounds(150, 70, 100, 28);
        tbxPagado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        panelFinalizacion.add(tbxPagado);

        lblVuelto = new JLabel("Vuelto: $0.00");
        lblVuelto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblVuelto.setForeground(UIConstants.COLOR_SUCCESS);
        lblVuelto.setBounds(270, 70, 150, 25);
        panelFinalizacion.add(lblVuelto);

        tbxPagado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calcularVuelto();
            }
        });

        JLabel lblDividir = new JLabel("Dividir entre:");
        lblDividir.setFont(UIConstants.FUENTE_NORMAL);
        lblDividir.setBounds(15, 110, 110, 25);
        panelFinalizacion.add(lblDividir);

        spinnerDividir = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spinnerDividir.setFont(UIConstants.FUENTE_NORMAL);
        spinnerDividir.setBounds(130, 110, 60, 28);
        panelFinalizacion.add(spinnerDividir);

        lblMontoPersona = new JLabel("Por persona: $0.00");
        lblMontoPersona.setFont(UIConstants.FUENTE_NORMAL);
        lblMontoPersona.setBounds(200, 110, 200, 25);
        panelFinalizacion.add(lblMontoPersona);

        spinnerDividir.addChangeListener(e -> actualizarDivision());

        // NUEVO: Tiempo estimado
        lblTiempoEstimado = new JLabel("⏱️ Tiempo estimado: 0 min");
        lblTiempoEstimado.setFont(UIConstants.FUENTE_NORMAL);
        lblTiempoEstimado.setForeground(new Color(41, 128, 185));
        lblTiempoEstimado.setBounds(15, 150, 250, 25);
        panelFinalizacion.add(lblTiempoEstimado);

        JButton btnFinalizar = new JButton("💳 GENERAR FACTURA");
        UIConstants.estilizarBoton(btnFinalizar, UIConstants.COLOR_PRIMARY);
        btnFinalizar.setBounds(70, 200, 300, 45);
        btnFinalizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnFinalizar.addActionListener(e -> generarFactura());
        panelFinalizacion.add(btnFinalizar);

        JButton btnNuevo = new JButton("Nueva Venta");
        UIConstants.estilizarBoton(btnNuevo, UIConstants.COLOR_WARNING);
        btnNuevo.setBounds(550, 510, 180, 35);
        btnNuevo.addActionListener(e -> nuevaVenta());
        add(btnNuevo);

        JButton btnCerrar = new JButton("Cerrar");
        UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
        btnCerrar.setBounds(750, 510, 120, 35);
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar);
    }

    private void crearCampo(JPanel panel, String label, JTextField campo, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(UIConstants.FUENTE_NORMAL);
        lbl.setBounds(x, y, 90, 25);
        panel.add(lbl);

        campo.setFont(UIConstants.FUENTE_NORMAL);
        campo.setBounds(x + 95, y, 320, 28);
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
            Persona p = clienteActual.getPersona();
            tbxNombre.setText(p.getNombre());
            tbxApellidos.setText(p.getApellidos());
            
            tbxNombre.setEditable(false);
            tbxApellidos.setEditable(false);
            tbxNombre.setBackground(new Color(240, 240, 240));
            tbxApellidos.setBackground(new Color(240, 240, 240));
            
            JOptionPane.showMessageDialog(this,
                "Cliente frecuente: " + p.getNombre() + " " + p.getApellidos(),
                "Cliente Encontrado",
                JOptionPane.INFORMATION_MESSAGE);
                
        } else {
            tbxNombre.setText("");
            tbxApellidos.setText("");
            tbxNombre.setEditable(true);
            tbxApellidos.setEditable(true);
            tbxNombre.setBackground(Color.WHITE);
            tbxApellidos.setBackground(Color.WHITE);
            
            JOptionPane.showMessageDialog(this,
                "Cliente nuevo. Complete los datos para registrarlo.",
                "Nuevo Cliente",
                JOptionPane.INFORMATION_MESSAGE);
                
            tbxNombre.requestFocus();
        }
    }

    private void cargarPlatos() {
        List<Plato> platos = platosDT.listarPlatos();
        cbxPlato.removeAllItems();
        for (Plato p : platos) {
            if (p.isDisponible()) {
                cbxPlato.addItem(p.getIdPlato() + " - " + p.getNombre() + " ($" + p.getPrecio() + ")");
            }
        }
    }

    private void agregarItem() {
        try {
            if (cbxPlato.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un plato.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] partes = cbxPlato.getSelectedItem().toString().split(" - ");
            int idPlato = Integer.parseInt(partes[0]);
            int cantidad = Integer.parseInt(tbxCantidad.getText().trim());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Plato platoSeleccionado = platosDT.buscarPorId(idPlato);

            if (platoSeleccionado != null) {
                DetallePedido detalle = new DetallePedido(0, pedidoActual, platoSeleccionado, cantidad);
                pedidoActual.agregarDetalle(detalle);
                actualizarVistaFactura();
                tbxCantidad.setText("");
                tbxCantidad.requestFocus();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quitarUltimo() {
        if (!pedidoActual.getDetalles().isEmpty()) {
            List<DetallePedido> items = pedidoActual.getDetalles();
            items.remove(items.size() - 1);
            actualizarVistaFactura();
        } else {
            JOptionPane.showMessageDialog(this, "No hay items para quitar.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private int getTiempoTotalPreparacion() {
        int tiempoTotal = 0;
        for (DetallePedido d : pedidoActual.getDetalles()) {
            int tiempoPlato = 15;
            tiempoTotal = Math.max(tiempoTotal, tiempoPlato * d.getCantidad());
        }
        return tiempoTotal;
    }

    private void actualizarVistaFactura() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("            DETALLE DE VENTA\n");
        sb.append("═══════════════════════════════════════\n\n");

        if (pedidoActual.getDetalles().isEmpty()) {
            sb.append("No hay productos agregados.\n");
        } else {
            int numero = 1;
            for (DetallePedido d : pedidoActual.getDetalles()) {
                sb.append(String.format("%d. %s\n", numero++, d.getPlato().getNombre()));
                sb.append(String.format("   %d x $%.2f = $%.2f\n\n",
                    d.getCantidad(), d.getPlato().getPrecio(), d.getSubtotal()));
            }
        }

        txaFactura.setText(sb.toString());
        txaFactura.setCaretPosition(0);

        double subtotal = pedidoActual.calcularTotal();
        double impuesto = subtotal * 0.13;
        double total = subtotal + impuesto;

        lblSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
        lblImpuesto.setText(String.format("Impuesto (13%%): $%.2f", impuesto));
        lblTotal.setText(String.format("TOTAL: $%.2f", total));

        actualizarDivision();

        calcularVuelto();

        int tiempo = getTiempoTotalPreparacion();
        lblTiempoEstimado.setText(String.format("⏱️ Tiempo estimado: %d min", tiempo));
    }

    private void calcularVuelto() {
        try {
            double pagado = Double.parseDouble(tbxPagado.getText().trim());
            double total = pedidoActual.calcularTotal() * 1.13;
            double vuelto = pagado - total;
            lblVuelto.setText(String.format("Vuelto: $%.2f", vuelto >= 0 ? vuelto : 0));
            lblVuelto.setForeground(vuelto >= 0 ? UIConstants.COLOR_SUCCESS : UIConstants.COLOR_DANGER);
        } catch (Exception ignored) {
            lblVuelto.setText("Vuelto: $0.00");
        }
    }

    private void actualizarDivision() {
        int n = (Integer) spinnerDividir.getValue();
        double total = pedidoActual.calcularTotal() * 1.13;
        lblMontoPersona.setText(String.format("Por persona: $%.2f", total / n));
    }

    private void generarFactura() {
        String ci = tbxCI.getText().trim();
        String nombre = tbxNombre.getText().trim();
        String apellidos = tbxApellidos.getText().trim();

        if (ci.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Complete los datos del cliente antes de generar la factura.",
                "Datos Incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pedidoActual.getDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Agregue al menos un producto a la venta.",
                "Venta Vacía",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Efectivo".equals(cbxMetodoPago.getSelectedItem())) {
            try {
                double pagado = Double.parseDouble(tbxPagado.getText().trim());
                double total = pedidoActual.calcularTotal() * 1.13;
                if (pagado < total) {
                    JOptionPane.showMessageDialog(this,
                        "El monto recibido no puede ser menor al total.\n" +
                        String.format("Total: $%.2f\nRecibido: $%.2f", total, pagado),
                        "Pago Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Ingrese un monto válido de pago en efectivo.",
                    "Pago Inválido",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            if (clienteActual == null) {
                Persona persona = new Persona(0, nombre, apellidos, ci, 0);
                clienteActual = new Cliente(0, persona);
                
                if (!clienteDT.agregarCliente(clienteActual)) {
                    JOptionPane.showMessageDialog(this,
                        "Error al registrar el cliente.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            pedidoActual.setCliente(clienteActual);
            pedidoActual.setFecha(java.time.LocalDate.now().toString());
            
            double subtotal = pedidoActual.calcularTotal();
            
            double porcentajeFidelidad = 0.0;
            String mensajeFidelidad = "";
            int puntos = clienteDT.obtenerPuntos(clienteActual.getIdCliente());
            
            if (puntos >= 200) {
                porcentajeFidelidad = 0.15;
                mensajeFidelidad = "Cliente VIP - 15% de descuento por fidelidad";
            } else if (puntos >= 100) {
                porcentajeFidelidad = 0.10;
                mensajeFidelidad = "Cliente Gold - 10% de descuento por fidelidad";
            } else if (puntos >= 50) {
                porcentajeFidelidad = 0.05;
                mensajeFidelidad = "Cliente Silver - 5% de descuento por fidelidad";
            }
            
            Oferta ofertaVigente = ofertaDT.obtenerOfertaVigente(java.time.LocalDate.now());
            double porcentajeOferta = 0.0;
            String mensajeOferta = "";
            
            if (ofertaVigente != null) {
                porcentajeOferta = ofertaVigente.getPorcentajeDescuento() / 100.0;
                mensajeOferta = ofertaVigente.getNombre() + " - " + 
                               ofertaVigente.getPorcentajeDescuento() + "% de descuento";
            }
            
            double porcentajeDescuentoTotal = Math.max(porcentajeFidelidad, porcentajeOferta);
            String mensajeDescuento = porcentajeFidelidad > porcentajeOferta ? mensajeFidelidad : mensajeOferta;
            
            double descuentoTotal = subtotal * porcentajeDescuentoTotal;
            double impuesto = (subtotal - descuentoTotal) * 0.13;
            double total = subtotal - descuentoTotal + impuesto;

            int idPedido = pedidoDT.crearPedido(pedidoActual);
            
            if (idPedido > 0) {
                for (DetallePedido detalle : pedidoActual.getDetalles()) {
                    pedidoDT.agregarDetalle(idPedido, detalle);
                    inventarioDT.descontarIngredientes(detalle.getPlato().getIdPlato(), detalle.getCantidad());
                }

                String numeroFactura = String.format("F-%06d", idPedido);

                Factura factura = new Factura(
                    0, idPedido, clienteActual.getIdCliente(),
                    numeroFactura, subtotal, impuesto, descuentoTotal, total,
                    (String) cbxMetodoPago.getSelectedItem(), "Pagada",
                    new java.sql.Timestamp(System.currentTimeMillis())
                );

                if (facturaDT.generarFactura(factura)) {
                    int puntosGanados = (int) (total / 10);
                    clienteDT.actualizarPuntos(clienteActual.getIdCliente(), puntosGanados);
                    
                    double vuelto = 0;
                    if ("Efectivo".equals(cbxMetodoPago.getSelectedItem())) {
                        vuelto = Double.parseDouble(tbxPagado.getText().trim()) - total;
                    }
                    
                    int nPersonas = (Integer) spinnerDividir.getValue();
                    
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("✅ FACTURA GENERADA EXITOSAMENTE\n\n");
                    mensaje.append("═══════════════════════════════════════\n");
                    mensaje.append(String.format("Número de Factura: %s\n", numeroFactura));
                    mensaje.append(String.format("Cliente: %s %s\n", nombre, apellidos));
                    mensaje.append(String.format("Subtotal: $%.2f\n", subtotal));
                    
                    if (porcentajeDescuentoTotal > 0) {
                        mensaje.append(String.format("Descuento (%.0f%%): -$%.2f\n", 
                                                    porcentajeDescuentoTotal * 100, descuentoTotal));
                        mensaje.append(String.format("  ⭐ %s\n", mensajeDescuento));
                    }
                    
                    mensaje.append(String.format("Impuesto (13%%): $%.2f\n", impuesto));
                    mensaje.append("═══════════════════════════════════════\n");
                    mensaje.append(String.format("TOTAL: $%.2f\n", total));
                    
                    if (nPersonas > 1) {
                        mensaje.append(String.format("💰 Dividido entre %d personas: $%.2f c/u\n", 
                                                    nPersonas, total / nPersonas));
                    }
                    
                    if ("Efectivo".equals(cbxMetodoPago.getSelectedItem())) {
                        mensaje.append(String.format("💵 Efectivo recibido: $%.2f\n", 
                                                    Double.parseDouble(tbxPagado.getText())));
                        mensaje.append(String.format("💸 Vuelto: $%.2f\n", vuelto));
                    }
                    
                    mensaje.append(String.format("\nMétodo de Pago: %s\n", cbxMetodoPago.getSelectedItem()));
                    mensaje.append(String.format("🎁 Puntos ganados: +%d\n", puntosGanados));
                    mensaje.append(String.format("💎 Puntos totales: %d\n", puntos + puntosGanados));
                    
                    int tiempo = getTiempoTotalPreparacion();
                    mensaje.append(String.format("⏱️ Tiempo estimado: %d minutos\n", tiempo));

                    JOptionPane.showMessageDialog(this,
                        mensaje.toString(),
                        "Factura Generada",
                        JOptionPane.INFORMATION_MESSAGE);

                    List<Ingrediente> alertas = inventarioDT.obtenerIngredientesPorAgotarse();
                    if (!alertas.isEmpty()) {
                        StringBuilder alertaMensaje = new StringBuilder();
                        alertaMensaje.append("🚨 ALERTA DE INVENTARIO\n\n");
                        alertaMensaje.append("Los siguientes ingredientes están por agotarse:\n\n");
                        for (Ingrediente ing : alertas) {
                            alertaMensaje.append(String.format("• %s: %.2f %s (mín: %.2f)\n",
                                ing.getNombre(), ing.getStockActual(), 
                                ing.getUnidad(), ing.getStockMinimo()));
                        }
                        JOptionPane.showMessageDialog(this,
                            alertaMensaje.toString(),
                            "Alerta de Stock",
                            JOptionPane.WARNING_MESSAGE);
                    }

                    nuevaVenta();
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void nuevaVenta() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Iniciar una nueva venta?",
            "Nueva Venta",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            pedidoActual = new Pedido();
            clienteActual = null;
            
            tbxCI.setText("");
            tbxNombre.setText("");
            tbxApellidos.setText("");
            tbxCantidad.setText("");
            tbxPagado.setText("0");
            
            tbxNombre.setEditable(true);
            tbxApellidos.setEditable(true);
            tbxNombre.setBackground(Color.WHITE);
            tbxApellidos.setBackground(Color.WHITE);
            
            spinnerDividir.setValue(1);
            
            if (cbxPlato.getItemCount() > 0) {
                cbxPlato.setSelectedIndex(0);
            }
            if (cbxMetodoPago.getItemCount() > 0) {
                cbxMetodoPago.setSelectedIndex(0);
            }
            
            actualizarVistaFactura();
            tbxCI.requestFocus();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new frmVenta().setVisible(true));
    }
}
