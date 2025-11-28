package PRESENTACION;

import javax.swing.*;
import LOGICA.Usuario;
import DATOS.usuarioDT;
import LOGICA.Sesion;
import java.awt.*;
import java.awt.event.*;

public class frmLogin extends JFrame {

    private JTextField tbxUsuario;
    private JPasswordField tbxPassword;
    private JButton btnEntrar;
    private JButton btnSalir;
    private JLabel lblMensaje;

    public frmLogin() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Cafetería - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        getContentPane().setLayout(null);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("SISTEMA DE CAFETERÍA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(52, 152, 219));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 30, 350, 35);
        getContentPane().add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Iniciar Sesión");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(127, 140, 141));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(50, 65, 350, 20);
        getContentPane().add(lblSubtitulo);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsuario.setBounds(50, 110, 100, 25);
        getContentPane().add(lblUsuario);

        tbxUsuario = new JTextField();
        tbxUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbxUsuario.setBounds(50, 140, 350, 35);
        tbxUsuario.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        getContentPane().add(tbxUsuario);

        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setBounds(50, 180, 100, 25);
        getContentPane().add(lblPassword);

        tbxPassword = new JPasswordField();
        tbxPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tbxPassword.setBounds(50, 210, 350, 35);
        tbxPassword.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        getContentPane().add(tbxPassword);

        // Mensaje
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMensaje.setForeground(new Color(192, 57, 43));
        lblMensaje.setBounds(50, 250, 350, 20);
        getContentPane().add(lblMensaje);

        btnEntrar = new JButton("ENTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getModel().isRollover() ? new Color(41, 128, 185) : new Color(52, 152, 219));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setOpaque(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setBounds(50, 259, 165, 40);
        btnEntrar.addActionListener(e -> iniciarSesion());
        getContentPane().add(btnEntrar);

        btnSalir = new JButton("SALIR") {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getModel().isRollover() ? new Color(192, 57, 43) : new Color(231, 76, 60));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setOpaque(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBounds(235, 259, 165, 40);
        btnSalir.addActionListener(e -> System.exit(0));
        getContentPane().add(btnSalir);

        // Enter en campos
        tbxUsuario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    tbxPassword.requestFocus();
            }
        });

        tbxPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    iniciarSesion();
            }
        });
    }

    private void iniciarSesion() {
        String usuario = tbxUsuario.getText().trim();
        String password = new String(tbxPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("❌ Por favor complete todos los campos.");
            return;
        }

        Usuario usuarioValidado = usuarioDT.validarUsuario(usuario, password);

        if (usuarioValidado != null) {
            Sesion.establecerUsuarioActivo(usuarioValidado);
            JOptionPane.showMessageDialog(this,
                "✅ Bienvenido " + usuarioValidado.getUsername() + "\nRol: " + usuarioValidado.getRol(),
                "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
            new frmMenuPrincipal().setVisible(true);
            dispose();
        } else {
            lblMensaje.setText("❌ Usuario o contraseña incorrectos.");
            tbxPassword.setText("");
            tbxPassword.requestFocus();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> new frmLogin().setVisible(true));
    }
}
