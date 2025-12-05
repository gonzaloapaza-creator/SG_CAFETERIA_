package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import LOGICA.Sesion;
import LOGICA.Ingrediente;
import DATOS.inventarioDT;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.*;
import DATOS.conexion;

public class frmMenuAdministracion extends JFrame {

    public frmMenuAdministracion() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Módulo de Administración - Cafetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConstants.COLOR_FONDO);
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        // HEADER
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(52, 152, 219));
        panelHeader.setBounds(0, 0, 950, 80);
        panelHeader.setLayout(null);
        getContentPane().add(panelHeader);

        JLabel lblTitulo = new JLabel("PANEL DE ADMINISTRACIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 15, 500, 35);
        panelHeader.add(lblTitulo);

        String nombreUsuario = Sesion.getUsuarioActivo() != null ?
            Sesion.getUsuarioActivo().getUsername() : "Admin";
        JLabel lblUsuario = new JLabel("Usuario: " + nombreUsuario);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(30, 50, 300, 20);
        panelHeader.add(lblUsuario);

        JButton btnVolver = new JButton("← Volver al Menú Principal");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setBackground(new Color(41, 128, 185));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(730, 15, 190, 35);
        btnVolver.addActionListener(e -> volverMenuPrincipal());
        panelHeader.add(btnVolver);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnCerrarSesion.setBackground(new Color(192, 57, 43));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setBounds(730, 45, 190, 30);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelHeader.add(btnCerrarSesion);

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(UIConstants.COLOR_FONDO);
        panelContenido.setLayout(null);
        panelContenido.setPreferredSize(new Dimension(920, 1600));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBounds(0, 80, 950, 620);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(scrollPane);

        crearSeccion(panelContenido, "GESTIÓN DE PRODUCTOS", 20, 20, new String[][]{
            {"Gestionar Platos", "Agregar, editar y eliminar platos del menú"},
            {"Gestionar Categorías", "Crear y administrar categorías de productos"}
        }, new Runnable[]{
            () -> new frmPlato().setVisible(true),
            () -> new frmCategoria().setVisible(true)
        });

        crearSeccion(panelContenido, "PROMOCIONES Y OFERTAS", 20, 210, new String[][]{
            {"Gestionar Ofertas", "Crear ofertas, combos, descuentos y promociones"},
            {"Programa de Fidelidad", "Ver clientes VIP, puntos y descuentos por fidelidad"}
        }, new Runnable[]{
            () -> new frmOfertas().setVisible(true),
            () -> new frmFidelidad().setVisible(true)
        });

        crearSeccion(panelContenido, "GESTIÓN DE INVENTARIO", 20, 400, new String[][]{
            {"Alertas de Stock", "Ver ingredientes por agotarse"}
        }, new Runnable[]{
            () -> mostrarAlertasInventario()
        });

        crearSeccion(panelContenido, "REPORTES Y ANÁLISIS", 20, 590, new String[][]{
            {"Ver Reportes", "Reportes detallados de ventas, productos y clientes"},
            {"Dashboard Ejecutivo", "Vista general en tiempo real del negocio"}
        }, new Runnable[]{
            () -> new frmReportes().setVisible(true),
            () -> new frmDashboard().setVisible(true)
        });

        crearSeccion(panelContenido, "GESTIÓN DE USUARIOS", 20, 780, new String[][]{
            {"Administrar Usuarios", "Crear, editar, bloquear y asignar roles a usuarios"}
        }, new Runnable[]{
            () -> new frmGestionUsuarios().setVisible(true)
        });

        crearSeccion(panelContenido, "CONFIGURACIÓN", 20, 970, new String[][]{
            {"Respaldar Base de Datos", "Crear, descargar o restaurar copias de seguridad"}
        }, new Runnable[]{
            () -> respaldarBaseDeDatos()
        });
    }

    private void crearSeccion(JPanel panelPadre, String titulo, int x, int y, String[][] botones, Runnable[] acciones) {
        JLabel lblSeccion = new JLabel(titulo);
        lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSeccion.setForeground(new Color(52, 73, 94));
        lblSeccion.setBounds(x + 15, y, 500, 30);
        panelPadre.add(lblSeccion);

        JSeparator separator = new JSeparator();
        separator.setBounds(x + 15, y + 35, 890, 2);
        panelPadre.add(separator);

        for (int i = 0; i < botones.length; i++) {
            JButton btn = crearBotonOpcion(botones[i][0], botones[i][1], acciones[i]);
            int posX = x + 15 + (i % 2) * 460;
            int posY = y + 50 + (i / 2) * 90;
            btn.setBounds(posX, posY, 440, 75);
            panelPadre.add(btn);
        }
    }

    private JButton crearBotonOpcion(String titulo, String descripcion, Runnable accion) {
        JButton boton = new JButton();
        boton.setLayout(null);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(236, 240, 241));
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIConstants.COLOR_PRIMARY, 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.WHITE);
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setBounds(15, 10, 400, 25);
        boton.add(lblTitulo);

        JLabel lblDesc = new JLabel("<html>" + descripcion + "</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(127, 140, 141));
        lblDesc.setBounds(15, 37, 400, 28);
        boton.add(lblDesc);

        boton.addActionListener(e -> accion.run());
        return boton;
    }

    private void mostrarAlertasInventario() {
        List<Ingrediente> alertas = inventarioDT.obtenerIngredientesPorAgotarse();

        if (alertas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "✅ No hay ingredientes con stock bajo.\n\nTodo el inventario está en orden.",
                "Sin Alertas",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("ALERTA DE INVENTARIO\n\n");
            mensaje.append("Los siguientes ingredientes están por agotarse:\n\n");
            mensaje.append("═══════════════════════════════════════\n\n");

            for (Ingrediente ing : alertas) {
                mensaje.append(String.format("• %s\n", ing.getNombre()));
                mensaje.append(String.format("  Stock actual: %.2f %s\n",
                    ing.getStockActual(), ing.getUnidad()));
                mensaje.append(String.format("  Stock mínimo: %.2f %s\n",
                    ing.getStockMinimo(), ing.getUnidad()));
                mensaje.append(String.format("  Diferencia: %.2f %s\n\n",
                    ing.getStockMinimo() - ing.getStockActual(), ing.getUnidad()));
            }

            JOptionPane.showMessageDialog(this,
                mensaje.toString(),
                "Alertas de Stock Bajo",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void respaldarBaseDeDatos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Carpeta de Destino");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File carpeta = fileChooser.getSelectedFile();

            try (Connection conn = conexion.conectar()) {

                if (conn == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo conectar a la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String db = conn.getCatalog();
                if (db == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo obtener el nombre de la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String user = "root";
                String password = ""; 

                String backupPath = carpeta.getAbsolutePath() + File.separator +
                        db + "_backup_" + (System.currentTimeMillis() / 1000) + ".sql";

                String mysqldump = "C:\\xampp\\mysql\\bin\\mysqldump.exe";


                List<String> command = new ArrayList<>();
                command.add(mysqldump);
                command.add("-u" + user);
                if (!password.isEmpty()) {
                    command.add("-p" + password);
                }
                command.add("--add-drop-table");
                command.add("--routines");
                command.add("--events");
                command.add(db);
                command.add("-r");
                command.add(backupPath);

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.redirectErrorStream(true); 

                Process process = pb.start();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                int processComplete = process.waitFor();

                if (processComplete == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Respaldo generado correctamente:\n" + backupPath,
                            "Backup Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo generar el respaldo.\nVerifique permisos o ruta del mysqldump.",
                            "Backup Fallido", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al respaldar base de datos:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void volverMenuPrincipal() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea volver al menú principal?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new frmMenuPrincipal().setVisible(true);
            this.dispose();
        }
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            new frmLogin().setVisible(true);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            new frmMenuAdministracion().setVisible(true);
        });
    }
}
