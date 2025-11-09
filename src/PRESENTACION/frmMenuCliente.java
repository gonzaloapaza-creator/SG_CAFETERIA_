package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import DATOS.platosDT;
import DATOS.reservaDT;
import DATOS.clienteDT;
import LOGICA.Plato;
import LOGICA.Reserva;
import LOGICA.Sesion;
import java.util.List;
import java.time.LocalDate;

public class frmMenuCliente extends JFrame {

    private JLabel lblCliente;
    private JLabel lblPuntos;
    private JTextArea txaMenuDisponible;
    private JTextArea txaReservasActivas;

    public frmMenuCliente() {
        configurarVentana();
        inicializarComponentes();
        cargarDatosCliente();
    }

    private void configurarVentana() {
        setTitle("Portal del Cliente - Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // ============================================
        // HEADER
        // ============================================
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(155, 89, 182));
        panelHeader.setBounds(0, 0, 1000, 100);
        panelHeader.setLayout(null);
        getContentPane().add(panelHeader);

        JLabel lblTitulo = new JLabel("PORTAL DEL CLIENTE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 15, 400, 35);
        panelHeader.add(lblTitulo);

        // Información del cliente
        lblCliente = new JLabel("Cliente: Cargando...");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCliente.setForeground(new Color(230, 230, 230));
        lblCliente.setBounds(30, 55, 400, 20);
        panelHeader.add(lblCliente);

        lblPuntos = new JLabel("Puntos de Fidelidad: 0");
        lblPuntos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPuntos.setForeground(new Color(255, 235, 100));
        lblPuntos.setBounds(30, 75, 400, 20);
        panelHeader.add(lblPuntos);

        // Botón volver
        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setBackground(new Color(142, 68, 173));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(850, 30, 120, 35);
        btnVolver.addActionListener(e -> volverMenuPrincipal());
        panelHeader.add(btnVolver);

        // ============================================
        // PANEL PRINCIPAL (dividido en 2 columnas)
        // ============================================

        // COLUMNA IZQUIERDA: Menú y Acciones
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setBackground(UIConstants.COLOR_FONDO);
        panelIzquierda.setBounds(20, 120, 480, 550);
        panelIzquierda.setLayout(null);
        getContentPane().add(panelIzquierda);

        // Sección: Menú Disponible
        JLabel lblMenuTitulo = new JLabel("Menú Disponible");
        lblMenuTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMenuTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblMenuTitulo.setBounds(0, 10, 480, 25);
        panelIzquierda.add(lblMenuTitulo);

        txaMenuDisponible = new JTextArea();
        txaMenuDisponible.setEditable(false);
        txaMenuDisponible.setFont(new Font("Consolas", Font.PLAIN, 11));
        txaMenuDisponible.setBackground(Color.WHITE);
        txaMenuDisponible.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane scrollMenu = new JScrollPane(txaMenuDisponible);
        scrollMenu.setBounds(0, 40, 480, 220);
        panelIzquierda.add(scrollMenu);

        // Botones de acciones
        JButton btnHacerReserva = new JButton("HACER RESERVA");
        UIConstants.estilizarBoton(btnHacerReserva, UIConstants.COLOR_SUCCESS);
        btnHacerReserva.setBounds(0, 270, 230, 45);
        btnHacerReserva.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHacerReserva.addActionListener(e -> hacerReserva());
        panelIzquierda.add(btnHacerReserva);

        JButton btnActualizarMenu = new JButton("ACTUALIZAR");
        UIConstants.estilizarBoton(btnActualizarMenu, UIConstants.COLOR_PRIMARY);
        btnActualizarMenu.setBounds(250, 270, 230, 45);
        btnActualizarMenu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizarMenu.addActionListener(e -> cargarMenuDisponible());
        panelIzquierda.add(btnActualizarMenu);

        // Sección: Ofertas Vigentes
        JLabel lblOfertasTitulo = new JLabel("Ofertas Vigentes");
        lblOfertasTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblOfertasTitulo.setForeground(UIConstants.COLOR_SUCCESS);
        lblOfertasTitulo.setBounds(0, 330, 480, 25);
        panelIzquierda.add(lblOfertasTitulo);

        JTextArea txaOfertas = new JTextArea();
        txaOfertas.setEditable(false);
        txaOfertas.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txaOfertas.setBackground(new Color(240, 255, 240));
        txaOfertas.setBorder(BorderFactory.createLineBorder(new Color(92, 184, 92)));
        cargarOfertasVigentes(txaOfertas);
        JScrollPane scrollOfertas = new JScrollPane(txaOfertas);
        scrollOfertas.setBounds(0, 360, 480, 180);
        panelIzquierda.add(scrollOfertas);

        // ============================================
        // COLUMNA DERECHA: Reservas Activas e Información
        // ============================================

        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(UIConstants.COLOR_FONDO);
        panelDerecha.setBounds(520, 120, 450, 550);
        panelDerecha.setLayout(null);
        getContentPane().add(panelDerecha);

        // Sección: Mis Reservas
        JLabel lblReservasTitulo = new JLabel("Mis Reservas Activas");
        lblReservasTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblReservasTitulo.setForeground(UIConstants.COLOR_PRIMARY);
        lblReservasTitulo.setBounds(0, 10, 450, 25);
        panelDerecha.add(lblReservasTitulo);

        txaReservasActivas = new JTextArea();
        txaReservasActivas.setEditable(false);
        txaReservasActivas.setFont(new Font("Consolas", Font.PLAIN, 11));
        txaReservasActivas.setBackground(Color.WHITE);
        txaReservasActivas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        cargarReservasActivas();
        JScrollPane scrollReservas = new JScrollPane(txaReservasActivas);
        scrollReservas.setBounds(0, 40, 450, 250);
        panelDerecha.add(scrollReservas);

        // Información de contacto
        JLabel lblContactoTitulo = new JLabel("📞 Información de Contacto");
        lblContactoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblContactoTitulo.setForeground(new Color(52, 152, 219));
        lblContactoTitulo.setBounds(0, 310, 450, 25);
        panelDerecha.add(lblContactoTitulo);

        JTextArea txaContacto = new JTextArea(
            "UBICACIÓN:\r\n   Av. Principal #123, Centro\r\n   Zona Sur\r\n\r\n📞 TELÉFONO:\r\n   (591) 2-123-4567\r\n\r\n📧 EMAIL:\r\n   info@cafeteria.com\r\n\r\n🕐 HORARIO:\r\n   Lunes a Viernes: 8:00 AM - 10:00 PM\r\n   Sábado y Domingo: 9:00 AM - 11:00 PM\r\n\r\n💎 BENEFICIOS CLIENTE:\r\n   • Descuentos según fidelidad\r\n   • Reservas prioritarias\r\n   • Ofertas exclusivas"
        );
        txaContacto.setEditable(false);
        txaContacto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txaContacto.setBackground(new Color(240, 248, 255));
        txaContacto.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219)));
        txaContacto.setLineWrap(true);
        txaContacto.setWrapStyleWord(true);
        JScrollPane scrollContacto = new JScrollPane(txaContacto);
        scrollContacto.setBounds(0, 340, 450, 200);
        panelDerecha.add(scrollContacto);
    }

    // ============================================
    // MÉTODOS PARA CARGAR DATOS DE LA BD
    // ============================================

    /**
     * Cargar información del cliente actual
     */
    private void cargarDatosCliente() {
        if (Sesion.hayUsuarioActivo() && Sesion.getUsuarioActivo().getIdEmpleado() > 0) {
            // Es un empleado, no un cliente
            lblCliente.setText("👤 Usuario: " + Sesion.getUsuarioActivo().getUsername());
            lblPuntos.setText("Rol: " + Sesion.getUsuarioActivo().getRol());
        } else {
            lblCliente.setText("👤 Cliente: Ingrese su CI para ver su información");
            lblPuntos.setText("💎 Puntos de Fidelidad: 0");
        }

        cargarMenuDisponible();
        cargarReservasActivas();
    }

    /**
     * Cargar menú disponible desde BD
     */
    private void cargarMenuDisponible() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("     MENÚ DISPONIBLE - HOY\n");
        sb.append("═══════════════════════════════════════\n\n");

        try {
            List<Plato> platos = platosDT.listarPlatos();

            if (platos.isEmpty()) {
                sb.append("No hay platos disponibles en este momento.");
            } else {
                // Agrupar por categoría
                java.util.Map<String, java.util.List<Plato>> platosPorCategoria = 
                    new java.util.LinkedHashMap<>();

                for (Plato p : platos) {
                    if (p.isDisponible()) {
                        // ✅ CORREGIDO: Obtener el nombre de la categoría
                        String categoria = p.getCategoria().getNombre();  // ← CAMBIO AQUÍ
                        platosPorCategoria.computeIfAbsent(categoria, k -> new java.util.ArrayList<>())
                            .add(p);
                    }
                }

                // Mostrar por categoría
                for (java.util.Map.Entry<String, java.util.List<Plato>> entry : 
                     platosPorCategoria.entrySet()) {
                    sb.append("🍽️ ").append(entry.getKey()).append("\n");
                    sb.append("─────────────────────────────────────\n");

                    for (Plato p : entry.getValue()) {
                        sb.append(String.format("  • %s\n", p.getNombre()));
                        sb.append(String.format("    Precio: Bs. %.2f\n", p.getPrecio()));
                        if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
                            sb.append(String.format("    %s\n", p.getDescripcion()));
                        }
                        sb.append("\n");
                    }
                }
            }

        } catch (Exception e) {
            sb.append("❌ Error al cargar el menú: ").append(e.getMessage());
            e.printStackTrace();
        }

        txaMenuDisponible.setText(sb.toString());
        txaMenuDisponible.setCaretPosition(0);
    }
    /**
     * Cargar ofertas vigentes desde BD
     */
    private void cargarOfertasVigentes(JTextArea txaOfertas) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");

        try {
            // Aquí necesitarías un método en ofertaDT para obtener ofertas vigentes
            // Por ahora, mostrar ofertas de ejemplo
            sb.append("🎁 HAPPY HOUR CAFÉ\n");
            sb.append("   30% descuento en bebidas calientes\n");
            sb.append("   Lunes a Viernes: 3:00 PM - 5:00 PM\n\n");

            sb.append("🎁 COMBO ALMUERZO\n");
            sb.append("   Plato + Bebida + Postre con 25% OFF\n");
            sb.append("   Lunes a Viernes: 12:00 PM - 3:00 PM\n\n");

            sb.append("🎁 FIN DE SEMANA FAMILIAR\n");
            sb.append("   10% en todo los fines de semana\n");

        } catch (Exception e) {
            sb.append("❌ Error al cargar ofertas");
        }

        txaOfertas.setText(sb.toString());
        txaOfertas.setCaretPosition(0);
    }

    /**
     * Cargar reservas activas del cliente
     */
    private void cargarReservasActivas() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");

        try {
            List<Reserva> reservas = reservaDT.listarReservas();

            if (reservas.isEmpty()) {
                sb.append("\n❌ No tiene reservas activas.\n");
                sb.append("\nHaga clic en 'HACER RESERVA' para\n");
                sb.append("crear una nueva reserva.\n");
            } else {
                int count = 0;
                for (Reserva r : reservas) {
                    // ✅ CORREGIDO: Usar getEstado() en lugar de r.getEstado()
                    if ("Confirmada".equals(r.getEstado()) || 
                        "Pendiente".equals(r.getEstado())) {
                        count++;
                        sb.append(String.format("✅ Reserva #%d\n", r.getIdReserva()));
                        sb.append(String.format("   Mesa: %d | Personas: %d\n", 
                            r.getMesa().getIdMesa(), r.getPersonas()));
                        sb.append(String.format("   Fecha: %s\n", r.getFecha()));
                        sb.append(String.format("   Hora: %s\n", r.getHora()));
                        sb.append(String.format("   Estado: %s\n", r.getEstado()));  // ✅ CORREGIDO
                        sb.append("─────────────────────────────────────\n");
                    }
                }

                if (count == 0) {
                    sb.append("\n❌ No tiene reservas confirmadas.\n");
                }
            }

        } catch (Exception e) {
            sb.append("❌ Error al cargar reservas: ").append(e.getMessage());
            e.printStackTrace();
        }

        txaReservasActivas.setText(sb.toString());
        txaReservasActivas.setCaretPosition(0);
    }

    // ============================================
    // ACCIONES
    // ============================================

    /**
     * Abrir formulario para hacer reserva
     */
    private void hacerReserva() {
        new frmReserva().setVisible(true);
    }

    /**
     * Volver al menú principal
     */
    private void volverMenuPrincipal() {
        Sesion.cerrarSesion();
        new frmMenuPrincipal().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new frmMenuCliente().setVisible(true);
        });
    }
}
