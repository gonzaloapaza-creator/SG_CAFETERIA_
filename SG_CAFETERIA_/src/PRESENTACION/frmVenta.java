package PRESENTACION;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
private JTable tblDetalle;
private DefaultTableModel modeloTabla;
private JButton btnBuscarCliente;
private JLabel lblSubtotal, lblImpuesto, lblTotal, lblVuelto, lblMontoPersona, lblTiempoEstimado;

private Pedido pedidoActual;
private Cliente clienteActual;

private final int COL_ITEM = 0;
private final int COL_PLATO = 1;
private final int COL_CANTIDAD = 2;
private final int COL_PRECIO = 3;
private final int COL_SUBTOTAL = 4;

public frmVenta() {
pedidoActual = new Pedido();
configurarVentana();
inicializarComponentes();
cargarPlatos();
actualizarTotales();
}

private void configurarVentana() {
setTitle("Registrar Venta - Cafetería");
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
setSize(1000, 750);
setLocationRelativeTo(null);
setResizable(false);
getContentPane().setBackground(UIConstants.COLOR_FONDO);
getContentPane().setLayout(null);
}

private void inicializarComponentes() {
JLabel lblTitulo = new JLabel("REGISTRAR VENTA");
lblTitulo.setFont(UIConstants.FUENTE_TITULO);
lblTitulo.setForeground(UIConstants.COLOR_PRIMARY);
lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
lblTitulo.setBounds(50, 15, 900, 30);
getContentPane().add(lblTitulo);

JPanel panelCliente = new JPanel();
panelCliente.setBackground(UIConstants.COLOR_PANEL);
panelCliente.setBorder(BorderFactory.createTitledBorder(
BorderFactory.createLineBorder(new Color(200, 200, 200)),
"Datos del Cliente",
0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
));
panelCliente.setBounds(20, 60, 450, 150);
panelCliente.setLayout(null);
getContentPane().add(panelCliente);

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

btnBuscarCliente = new JButton("Buscar");
UIConstants.estilizarBoton(btnBuscarCliente, UIConstants.COLOR_PRIMARY);
btnBuscarCliente.setBounds(300, 30, 130, 28);
btnBuscarCliente.addActionListener(e -> buscarCliente());
panelCliente.add(btnBuscarCliente);

crearCampo(panelCliente, "Nombre:", tbxNombre = new JTextField(), 15, 70);

crearCampo(panelCliente, "Apellidos:", tbxApellidos = new JTextField(), 15, 105);

tbxCI.addActionListener(e -> buscarCliente());

JPanel panelItems = new JPanel();
panelItems.setBackground(UIConstants.COLOR_PANEL);
panelItems.setBorder(BorderFactory.createTitledBorder(
BorderFactory.createLineBorder(new Color(200, 200, 200)),
"Agregar Productos",
0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
));
panelItems.setBounds(20, 225, 450, 150);
panelItems.setLayout(null);
getContentPane().add(panelItems);

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

tbxCantidad = new JTextField("1");
tbxCantidad.setFont(UIConstants.FUENTE_NORMAL);
tbxCantidad.setBounds(110, 70, 100, 28);
tbxCantidad.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(new Color(200, 200, 200)),
BorderFactory.createEmptyBorder(2, 8, 2, 8)
));
panelItems.add(tbxCantidad);

JButton btnAgregar = new JButton("+ Agregar");
UIConstants.estilizarBoton(btnAgregar, UIConstants.COLOR_SUCCESS);
btnAgregar.setBounds(30, 110, 120, 30);
btnAgregar.addActionListener(e -> agregarItem());
panelItems.add(btnAgregar);

JButton btnModificar = new JButton("Modificar Cant.");
UIConstants.estilizarBoton(btnModificar, new Color(241, 196, 15));
btnModificar.setBounds(160, 110, 140, 30);
btnModificar.addActionListener(e -> modificarCantidadItem());
panelItems.add(btnModificar);

JButton btnEliminar = new JButton("🗑️ Eliminar");
UIConstants.estilizarBoton(btnEliminar, UIConstants.COLOR_DANGER);
btnEliminar.setBounds(310, 110, 120, 30);
btnEliminar.addActionListener(e -> eliminarFilaSeleccionada());
panelItems.add(btnEliminar);

JPanel panelDetalle = new JPanel();
panelDetalle.setBackground(UIConstants.COLOR_PANEL);
panelDetalle.setBorder(BorderFactory.createTitledBorder(
BorderFactory.createLineBorder(new Color(200, 200, 200)),
"Detalle de la Venta",
0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
));
panelDetalle.setBounds(490, 60, 490, 315);
panelDetalle.setLayout(new BorderLayout());
getContentPane().add(panelDetalle);

String[] columnas = {"#", "Plato", "Cant.", "Precio Unit.", "Subtotal"};
modeloTabla = new DefaultTableModel(columnas, 0) {
@Override
public boolean isCellEditable(int row, int column) {
return false;
}
};

tblDetalle = new JTable(modeloTabla);
tblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
tblDetalle.setRowHeight(25);
tblDetalle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
tblDetalle.getTableHeader().setBackground(new Color(52, 152, 219));
tblDetalle.getTableHeader().setForeground(Color.WHITE);
tblDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(30);
tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(200);
tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(60);
tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(80);
tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(80);

JScrollPane scrollTabla = new JScrollPane(tblDetalle);
panelDetalle.add(scrollTabla, BorderLayout.CENTER);

JPanel panelTotales = new JPanel();
panelTotales.setBackground(new Color(250, 250, 255));
panelTotales.setLayout(null);
panelTotales.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
panelTotales.setPreferredSize(new Dimension(490, 50));

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
lblTotal.setBounds(280, 10, 180, 30);
panelTotales.add(lblTotal);

panelDetalle.add(panelTotales, BorderLayout.SOUTH);

JPanel panelFinalizacion = new JPanel();
panelFinalizacion.setBackground(UIConstants.COLOR_PANEL);
panelFinalizacion.setBorder(BorderFactory.createTitledBorder(
BorderFactory.createLineBorder(new Color(200, 200, 200)),
"Finalizar Venta",
0, 0, UIConstants.FUENTE_SUBTITULO, UIConstants.COLOR_PRIMARY
));
panelFinalizacion.setBounds(20, 390, 960, 247);
panelFinalizacion.setLayout(null);
getContentPane().add(panelFinalizacion);

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

lblTiempoEstimado = new JLabel("⏱️ Tiempo estimado: 0 min");
lblTiempoEstimado.setFont(UIConstants.FUENTE_NORMAL);
lblTiempoEstimado.setForeground(new Color(41, 128, 185));
lblTiempoEstimado.setBounds(15, 150, 250, 25);
panelFinalizacion.add(lblTiempoEstimado);

JButton btnFinalizar = new JButton("GENERAR FACTURA");
UIConstants.estilizarBoton(btnFinalizar, UIConstants.COLOR_PRIMARY);
btnFinalizar.setBounds(300, 190, 350, 50);
btnFinalizar.setFont(new Font("Segoe UI", Font.BOLD, 16));
btnFinalizar.addActionListener(e -> generarFactura());
panelFinalizacion.add(btnFinalizar);

JButton btnNuevo = new JButton("Nueva Venta");
UIConstants.estilizarBoton(btnNuevo, UIConstants.COLOR_WARNING);
btnNuevo.setBounds(30, 648, 180, 35);
btnNuevo.addActionListener(e -> nuevaVenta());
getContentPane().add(btnNuevo);

JButton btnCerrar = new JButton("Cerrar");
UIConstants.estilizarBoton(btnCerrar, UIConstants.COLOR_DANGER);
btnCerrar.setBounds(830, 648, 120, 35);
btnCerrar.addActionListener(e -> dispose());
getContentPane().add(btnCerrar);
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
actualizarTablaDetalle();
tbxCantidad.setText("1");
tbxCantidad.requestFocus();
}

} catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
}
}

private void eliminarFilaSeleccionada() {
int fila = tblDetalle.getSelectedRow();
if (fila >= 0) {
int confirmar = JOptionPane.showConfirmDialog(this,
"¿Seguro que deseas eliminar el ítem seleccionado?",
"Confirmar eliminación",
JOptionPane.YES_NO_OPTION);
if (confirmar == JOptionPane.YES_OPTION) {
pedidoActual.getDetalles().remove(fila);
actualizarTablaDetalle();
}
} else {
JOptionPane.showMessageDialog(this,
"Debes seleccionar un ítem de la tabla para eliminar.",
"Sin selección",
JOptionPane.WARNING_MESSAGE);
}
}

private void modificarCantidadItem() {
int fila = tblDetalle.getSelectedRow();
if (fila >= 0) {
int cantidadActual = pedidoActual.getDetalles().get(fila).getCantidad();
String input = JOptionPane.showInputDialog(this,
"Ingrese la nueva cantidad:", cantidadActual);
if (input != null) {
try {
int nuevaCantidad = Integer.parseInt(input);
if (nuevaCantidad <= 0) {
JOptionPane.showMessageDialog(this,
"La cantidad debe ser mayor que 0.",
"Cantidad inválida",
JOptionPane.WARNING_MESSAGE);
return;
}
pedidoActual.getDetalles().get(fila).setCantidad(nuevaCantidad);
actualizarTablaDetalle();

} catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(this,
"Ingrese un número válido.",
"Error",
JOptionPane.ERROR_MESSAGE);
}
}
} else {
JOptionPane.showMessageDialog(this,
"Debes seleccionar un ítem de la tabla para modificar.",
"Sin selección",
JOptionPane.WARNING_MESSAGE);
}
}

private void actualizarTablaDetalle() {
modeloTabla.setRowCount(0);
int numero = 1;
for (DetallePedido d : pedidoActual.getDetalles()) {
modeloTabla.addRow(new Object[]{
numero++,
d.getPlato().getNombre(),
d.getCantidad(),
String.format("$%.2f", d.getPlato().getPrecio()),
String.format("$%.2f", d.getSubtotal())
});
}
actualizarTotales();
}

private int getTiempoTotalPreparacion() {
int tiempoTotal = 0;
for (DetallePedido d : pedidoActual.getDetalles()) {
int tiempoPlato = 15;
tiempoTotal = Math.max(tiempoTotal, tiempoPlato * d.getCantidad());
}
return tiempoTotal;
}

private void actualizarTotales() {
double subtotal = pedidoActual.calcularTotal();
double impuesto = subtotal * 0.13;
double total = subtotal + impuesto;

lblSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
lblImpuesto.setText(String.format("Impuesto (13%%): $%.2f", impuesto));
lblTotal.setText(String.format("TOTAL: $%.2f", total));

actualizarDivision();
calcularVuelto();

int tiempo = getTiempoTotalPreparacion();
lblTiempoEstimado.setText("Tiempo estimado: 0 min");
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
double impuesto = subtotal * 0.13;
double total = subtotal + impuesto;

int idPedido = pedidoDT.crearPedido(pedidoActual);

if (idPedido > 0) {
for (DetallePedido detalle : pedidoActual.getDetalles()) {
pedidoDT.agregarDetalle(idPedido, detalle);
}

String numeroFactura = String.format("F-%06d", idPedido);

Factura factura = new Factura(
0, idPedido, clienteActual.getIdCliente(),
numeroFactura, subtotal, impuesto, 0, total,
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

int tiempo = getTiempoTotalPreparacion();
mensaje.append(String.format("⏱️ Tiempo estimado: %d minutos\n", tiempo));

JOptionPane.showMessageDialog(this,
mensaje.toString(),
"Factura Generada",
JOptionPane.INFORMATION_MESSAGE);

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
tbxCantidad.setText("1");
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

modeloTabla.setRowCount(0);
actualizarTotales();
tbxCI.requestFocus();
}
}

public static void main(String[] args) {
EventQueue.invokeLater(() -> new frmVenta().setVisible(true));
}
}
