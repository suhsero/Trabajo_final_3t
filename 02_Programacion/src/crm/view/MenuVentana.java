package crm.view;

import crm.dao.ComercialDAO;
import crm.dao.ClienteFormalDAO;
import crm.dao.ClientePotencialDAO;
import crm.dao.PedidoDAO;
import crm.dao.FacturaDAO;
import crm.model.Comercial;
import crm.model.ClienteFormal;
import crm.model.ClientePotencial;
import crm.model.Pedido;
import crm.model.Factura;
import crm.exception.EmailInvalidoException;
import crm.exception.NifInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuVentana extends JFrame {

    private JTextArea txtConsola;
    private JTextField txtOpcion;
    private JButton btnEnviar;

    private ComercialDAO comercialDAO = new ComercialDAO();
    private ClienteFormalDAO clienteFormalDAO = new ClienteFormalDAO();
    private ClientePotencialDAO clientePotencialDAO = new ClientePotencialDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();

    public MenuVentana() {
        setTitle("CRM XTART - Menú Principal");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        txtConsola = new JTextArea();
        txtConsola.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtConsola);
        scroll.setBounds(20, 20, 540, 400);
        add(scroll);

        JLabel lblInfo = new JLabel("Elige una opción:");
        lblInfo.setBounds(20, 440, 120, 25);
        add(lblInfo);

        txtOpcion = new JTextField();
        txtOpcion.setBounds(130, 440, 50, 25);
        add(txtOpcion);

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBounds(190, 440, 90, 25);
        add(btnEnviar);

        imprimirMenu();

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarOpcion();
            }
        });
    }

    private void imprimirMenu() {
        txtConsola.setText("--- MENU PRINCIPAL CRM XTART ---\n");
        txtConsola.append("1. Gestión de Comerciales\n");
        txtConsola.append("2. Gestión de Clientes Potenciales\n");
        txtConsola.append("3. Gestión de Clientes Formales\n");
        txtConsola.append("4. Gestión de Pedidos\n");
        txtConsola.append("5. Gestión de Facturas\n");
        txtConsola.append("6. Salir del programa\n");
        txtConsola.append("--------------------------------\n");
        txtConsola.append("Introduce el número de la opción y pulsa Enviar.\n");
    }

    private void procesarOpcion() {
        String texto = txtOpcion.getText();
        txtOpcion.setText("");

        try {
            int opcion = Integer.parseInt(texto);

            switch (opcion) {
                case 1:
                    gestionarComerciales();
                    break;
                case 2:
                    gestionarPotenciales();
                    break;
                case 3:
                    gestionarClientesFormales();
                    break;
                case 4:
                    gestionarPedidos();
                    break;
                case 5:
                    gestionarFacturas();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(this, "Saliendo del sistema CRM.");
                    System.exit(0);
                    break;
                default:
                    txtConsola.append("\n> Opción no válida. Introduce un número del 1 al 6.\n");
            }
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Debes introducir un número entero.\n");
        }
    }

    // --- MÓDULO COMERCIALES ---

    private void gestionarComerciales() {
        String input = JOptionPane.showInputDialog(this,
                "Módulo Comerciales:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Exportar a TXT\n5. Modificar (UPDATE)\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaComercial();
            else if (opcion == 2) listarComerciales();
            else if (opcion == 3) borrarComercial();
            else if (opcion == 4) exportarComercialesTXT();
            else if (opcion == 5) modificarComercial();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaComercial() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del comercial:");
            String apellidos = JOptionPane.showInputDialog("Apellidos:");
            String email = JOptionPane.showInputDialog("Email:");

            if (!email.contains("@") || !email.contains(".")) {
                throw new EmailInvalidoException("El formato del correo es incorrecto.");
            }

            String telefono = JOptionPane.showInputDialog("Teléfono:");
            String codigo = JOptionPane.showInputDialog("Código de comercial:");
            String zona = JOptionPane.showInputDialog("Zona geográfica:");

            Comercial nuevo = new Comercial();
            nuevo.setNombre(nombre);
            nuevo.setApellidos(apellidos);
            nuevo.setEmail(email);
            nuevo.setTelefono(telefono);
            nuevo.setCodigoComercial(codigo);
            nuevo.setZonaGeografica(zona);
            nuevo.setFechaAlta(LocalDate.now());

            if (comercialDAO.insertar(nuevo)) txtConsola.append("\n> Comercial guardado en BD.\n");
            else txtConsola.append("\n> Error al guardar en BD.\n");

        } catch (EmailInvalidoException ex) {
            txtConsola.append("\n> Error de validación: " + ex.getMessage() + "\n");
        } catch (Exception e) {
            txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }
    }

    private void listarComerciales() {
        ArrayList<Comercial> lista = comercialDAO.listarTodos();
        txtConsola.append("\n--- COMERCIALES ---\n");
        if (lista.isEmpty()) {
            txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for (Comercial c : lista) {
                txtConsola.append("ID: " + c.getIdPersona() + " | Nombre: " + c.getNombre() + " " + c.getApellidos() + "\n");
            }
        }
        txtConsola.append("-------------------\n");
    }

    private void borrarComercial() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (comercialDAO.eliminar(id)) txtConsola.append("\n> Borrado correctamente.\n");
                else txtConsola.append("\n> ID no encontrado.\n");
            } catch (NumberFormatException e) {
                txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }
    }

    private void exportarComercialesTXT() {
        ArrayList<Comercial> lista = comercialDAO.listarTodos();
        if (lista.isEmpty()) {
            txtConsola.append("\n> No hay datos para exportar.\n");
            return;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("comerciales.txt"))) {
            pw.println("LISTADO DE COMERCIALES");
            for (Comercial c : lista) {
                pw.println(c.getNombre() + " - " + c.getEmail());
            }
            txtConsola.append("\n> Exportado a 'comerciales.txt'.\n");
        } catch (Exception e) {
            txtConsola.append("\n> Error al generar archivo de exportación.\n");
        }
    }

    private void modificarComercial() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona del comercial a modificar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            Comercial c = comercialDAO.buscarComercial(id);

            if (c == null) {
                txtConsola.append("\n> No se encontró ningún comercial con ese ID.\n");
                return;
            }

            // Pedimos los datos nuevos mostrando los antiguos por defecto
            String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
            String email = JOptionPane.showInputDialog("Email:", c.getEmail());
            String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
            String codigo = JOptionPane.showInputDialog("Código Comercial:", c.getCodigoComercial());
            String zona = JOptionPane.showInputDialog("Zona:", c.getZonaGeografica());

            // Actualizamos el objeto
            if (nombre != null) c.setNombre(nombre);
            if (email != null) c.setEmail(email);
            if (telefono != null) c.setTelefono(telefono);
            if (codigo != null) c.setCodigoComercial(codigo);
            if (zona != null) c.setZonaGeografica(zona);

            if (comercialDAO.actualizar(c)) {
                txtConsola.append("\n> Comercial actualizado correctamente en la BD.\n");
            } else {
                txtConsola.append("\n> Error al actualizar el comercial en la BD.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: El ID debe ser un número entero.\n");
        }
    }

    // --- MÓDULO CLIENTES FORMALES ---

    private void gestionarClientesFormales() {
        String input = JOptionPane.showInputDialog(this,
                "Módulo Clientes Formales:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Buscar Cliente\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaClienteFormal();
            else if (opcion == 2) listarClientesFormales();
            else if (opcion == 3) borrarClienteFormal();
            else if (opcion == 4) buscarClienteFormal();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaClienteFormal() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del contacto principal:");
            String email = JOptionPane.showInputDialog("Email:");
            String telefono = JOptionPane.showInputDialog("Teléfono:");
            String nif = JOptionPane.showInputDialog("NIF/CIF (9 caracteres):");

            if (nif.length() != 9) {
                throw new NifInvalidoException("El NIF/CIF debe tener exactamente 9 caracteres.");
            }

            String razon = JOptionPane.showInputDialog("Razón Social:");
            String direccion = JOptionPane.showInputDialog("Dirección fiscal:");
            String codigo = JOptionPane.showInputDialog("Código de cliente (ej: CLI-001):");

            ClienteFormal nuevo = new ClienteFormal();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);
            nuevo.setTelefono(telefono);
            nuevo.setNifCif(nif);
            nuevo.setRazonSocial(razon);
            nuevo.setDireccionFiscal(direccion);
            nuevo.setCodigoCliente(codigo);
            nuevo.setCondicionesPago("contado");
            nuevo.setDescuentoHabitual(0.0);
            nuevo.setEstado("activo");

            if (clienteFormalDAO.insertar(nuevo)) txtConsola.append("\n> Cliente Formal guardado en BD.\n");
            else txtConsola.append("\n> Error al guardar en BD.\n");

        } catch (NifInvalidoException ex) {
            txtConsola.append("\n> Error de NIF: " + ex.getMessage() + "\n");
        } catch (Exception e) {
            txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }
    }

    private void listarClientesFormales() {
        ArrayList<ClienteFormal> lista = clienteFormalDAO.listarTodos();
        txtConsola.append("\n--- CLIENTES FORMALES ---\n");
        if (lista.isEmpty()) {
            txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for (ClienteFormal c : lista) {
                txtConsola.append("ID: " + c.getIdPersona() + " | NIF: " + c.getNifCif() + " | Razón Social: " + c.getRazonSocial() + "\n");
            }
        }
        txtConsola.append("-------------------------\n");
    }

    private void borrarClienteFormal() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (clienteFormalDAO.eliminar(id)) txtConsola.append("\n> Borrado correctamente.\n");
                else txtConsola.append("\n> ID no encontrado.\n");
            } catch (NumberFormatException e) {
                txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }
    }

    private void buscarClienteFormal() {
        String modoStr = JOptionPane.showInputDialog("Buscar por:\n1. ID de Persona\n2. NIF/CIF");
        if (modoStr == null || modoStr.trim().isEmpty()) return;

        try {
            int modo = Integer.parseInt(modoStr);
            ClienteFormal resultado = null;

            if (modo == 1) {
                String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona:");
                if (idStr != null) {
                    resultado = clienteFormalDAO.buscarCliente(Integer.parseInt(idStr));
                }
            } else if (modo == 2) {
                String nifStr = JOptionPane.showInputDialog("Introduce el NIF/CIF:");
                if (nifStr != null) {
                    resultado = clienteFormalDAO.buscarCliente(nifStr);
                }
            } else {
                txtConsola.append("\n> Opción de búsqueda no válida.\n");
                return;
            }

            if (resultado != null) {
                txtConsola.append("\n> Cliente Encontrado: " + resultado.getRazonSocial() + " (NIF: " + resultado.getNifCif() + ")\n");
            } else {
                txtConsola.append("\n> No se ha encontrado ningún cliente con esos datos.\n");
            }
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error en los datos introducidos.\n");
        }
    }

    // --- MÓDULO CLIENTES POTENCIALES ---

    private void gestionarPotenciales() {
        String input = JOptionPane.showInputDialog(this,
                "Módulo Potenciales:\n1. Dar de alta\n2. Listar\n3. Guardar copia (Serializar)\n4. Cargar copia (Deserializar)\nElige opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaPotencial();
            else if (opcion == 2) listarPotenciales();
            else if (opcion == 3) serializarPotenciales();
            else if (opcion == 4) deserializarPotenciales();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaPotencial() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del contacto:");
            String email = JOptionPane.showInputDialog("Email:");
            String telefono = JOptionPane.showInputDialog("Teléfono:");
            String empresa = JOptionPane.showInputDialog("Nombre de la empresa:");
            String fuente = JOptionPane.showInputDialog("Fuente de captación:");
            String idComercialStr = JOptionPane.showInputDialog("ID del Comercial asignado:");

            ClientePotencial nuevo = new ClientePotencial();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);
            nuevo.setTelefono(telefono);
            nuevo.setEmpresa(empresa);
            nuevo.setFuenteCaptacion(fuente);
            nuevo.setEstado("nuevo");
            nuevo.setFechaPrimerContacto(LocalDate.now());
            nuevo.setIdComercialAsignado(Integer.parseInt(idComercialStr));

            if (clientePotencialDAO.insertar(nuevo)) txtConsola.append("\n> Cliente Potencial guardado en BD.\n");
            else txtConsola.append("\n> Error al guardar en BD.\n");

        } catch (Exception e) {
            txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }
    }

    private void listarPotenciales() {
        ArrayList<ClientePotencial> lista = clientePotencialDAO.listarTodos();
        txtConsola.append("\n--- CLIENTES POTENCIALES ---\n");
        if (lista.isEmpty()) {
            txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for (ClientePotencial c : lista) {
                txtConsola.append("ID: " + c.getIdPersona() + " | Empresa: " + c.getEmpresa() + " | Estado: " + c.getEstado() + "\n");
            }
        }
        txtConsola.append("----------------------------\n");
    }

    private void serializarPotenciales() {
        ArrayList<ClientePotencial> lista = clientePotencialDAO.listarTodos();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("potenciales_backup.dat"))) {
            oos.writeObject(lista);
            txtConsola.append("\n> Copia serializada correctamente en 'potenciales_backup.dat'.\n");
        } catch (Exception e) {
            txtConsola.append("\n> Error al serializar los datos.\n");
        }
    }

    private void deserializarPotenciales() {
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream("potenciales_backup.dat"))) {
            ArrayList<ClientePotencial> recuperados = (ArrayList<ClientePotencial>) ois.readObject();
            txtConsola.append("\n--- DATOS RECUPERADOS DEL BACKUP ---\n");
            for (ClientePotencial c : recuperados) {
                txtConsola.append("Recuperado: " + c.getNombre() + " de la empresa " + c.getEmpresa() + "\n");
            }
        } catch (Exception e) {
            txtConsola.append("\n> Error al leer el archivo.\n");
        }
    }

    // --- MÓDULO PEDIDOS ---

    private void gestionarPedidos() {
        String input = JOptionPane.showInputDialog(this,
                "Módulo Pedidos:\n1. Dar de alta\n2. Listar todos\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaPedido();
            else if (opcion == 2) listarPedidos();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaPedido() {
        try {
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:");
            String idComercial = JOptionPane.showInputDialog("ID del Comercial:");
            String estado = JOptionPane.showInputDialog("Estado (pendiente, en curso, servido, anulado):");

            if (!pedidoDAO.validarEstadoPedido(estado)) {
                txtConsola.append("\n> Error: El estado '" + estado + "' no es válido.\n");
                return;
            }

            Pedido nuevo = new Pedido();
            nuevo.setFechaPedido(LocalDateTime.now());
            nuevo.setIdClienteFormal(Integer.parseInt(idCliente));
            nuevo.setIdComercial(Integer.parseInt(idComercial));
            nuevo.setEstado(estado.toLowerCase());

            if (pedidoDAO.insertar(nuevo)) txtConsola.append("\n> Pedido guardado en BD.\n");
            else txtConsola.append("\n> Error al guardar el pedido en BD.\n");

        } catch (Exception e) {
            txtConsola.append("\n> Alta de pedido cancelada o datos incorrectos.\n");
        }
    }

    private void listarPedidos() {
        ArrayList<Pedido> lista = pedidoDAO.listarTodos();
        txtConsola.append("\n--- PEDIDOS ---\n");
        if (lista.isEmpty()) {
            txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for (Pedido p : lista) {
                txtConsola.append("ID Pedido: " + p.getIdPedido() + " | Cliente: " + p.getIdClienteFormal() + " | Estado: " + p.getEstado() + "\n");
            }
        }
        txtConsola.append("---------------\n");
    }

    // --- MÓDULO FACTURAS ---

    private void gestionarFacturas() {
        String input = JOptionPane.showInputDialog(this,
                "Módulo Facturas:\n1. Dar de alta\n2. Listar todas\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaFactura();
            else if (opcion == 2) listarFacturas();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaFactura() {
        try {
            String numero = JOptionPane.showInputDialog("Número de factura (ej: FAC-001):");
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:");
            String idPedido = JOptionPane.showInputDialog("ID del Pedido asociado:");
            String baseImponible = JOptionPane.showInputDialog("Base Imponible:");

            double base = Double.parseDouble(baseImponible);
            double iva = 21.00;
            double total = base + (base * (iva / 100));

            Factura nueva = new Factura();
            nueva.setNumeroFactura(numero);
            nueva.setFechaEmision(LocalDate.now());
            nueva.setFechaVencimiento(LocalDate.now().plusDays(30));
            nueva.setIdClienteFormal(Integer.parseInt(idCliente));
            nueva.setIdPedido(Integer.parseInt(idPedido));
            nueva.setBaseImponible(base);
            nueva.setTipoIva(iva);
            nueva.setTotal(total);
            nueva.setEstado("pendiente");

            if (facturaDAO.insertar(nueva)) txtConsola.append("\n> Factura guardada en BD.\n");
            else txtConsola.append("\n> Error al guardar la factura en BD.\n");

        } catch (Exception e) {
            txtConsola.append("\n> Alta de factura cancelada o datos incorrectos.\n");
        }
    }

    private void listarFacturas() {
        ArrayList<Factura> lista = facturaDAO.listarTodos();
        txtConsola.append("\n--- FACTURAS ---\n");
        if (lista.isEmpty()) {
            txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for (Factura f : lista) {
                txtConsola.append("Nº: " + f.getNumeroFactura() + " | Total: " + f.getTotal() + "€ | Estado: " + f.getEstado() + "\n");
            }
        }
        txtConsola.append("----------------\n");
    }
}