package crm.view;

import crm.dao.ComercialDAO;
import crm.dao.ClienteFormalDAO;
import crm.dao.ClientePotencialDAO;
import crm.dao.PedidoDAO;
import crm.dao.FacturaDAO;
import crm.database.ConexionBD;
import crm.model.Comercial;
import crm.model.ClienteFormal;
import crm.model.ClientePotencial;
import crm.model.Pedido;
import crm.model.Factura;
import crm.database.ConexionBD;
import crm.exception.EmailInvalidoException;
import crm.exception.NifInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
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
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        txtConsola = new JTextArea();
        txtConsola.setEditable(false);

        JScrollPane scroll = new JScrollPane(txtConsola);
        scroll.setBounds(20, 20, 640, 400);
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
<<<<<<< HEAD
        txtConsola.append("6. Comprobar conexión a BD\n");
=======
        txtConsola.append("6. Comprobar conexión con BD\n");
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        txtConsola.append("7. Salir del programa\n");
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
                    comprobarConexion();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(this, "Saliendo del sistema CRM.");
                    System.exit(0);
                    break;
                default:
                    txtConsola.append("\n> Opción no válida. Introduce un número del 1 al 7.\n");
            }
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Debes introducir un número entero.\n");
        }
    }

    private void comprobarConexion() {
        txtConsola.append("\n> Comprobando conexión con la base de datos MySQL...\n");
        try (Connection con = ConexionBD.getConexion()) {
            if (con != null) {
                txtConsola.append("> ¡ÉXITO! La conexión a la base de datos funciona correctamente.\n");
            } else {
                txtConsola.append("> ERROR: La conexión ha devuelto un valor nulo. Revisa XAMPP/MySQL.\n");
            }
        } catch (Exception e) {
            txtConsola.append("> ERROR de conexión: " + e.getMessage() + "\n");
            txtConsola.append("> Verifica que la base de datos 'crmxtart' existe y el puerto es correcto.\n");
        }
    }

    // --- COMERCIALES ---

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
            String nombre = JOptionPane.showInputDialog("Nombre del comercial:\n(Texto normal)");
            String apellidos = JOptionPane.showInputDialog("Apellidos:\n(Texto normal)");
            String email = JOptionPane.showInputDialog("Email:\n(OBLIGATORIO: Debe contener '@' y ser ÚNICO en toda la BD)");

            if (!email.contains("@") || !email.contains(".")) {
                throw new EmailInvalidoException("El formato del correo es incorrecto.");
            }

            String telefono = JOptionPane.showInputDialog("Teléfono:\n(Solo números, ej: 600123456)");
            String codigo = JOptionPane.showInputDialog("Código de comercial:\n(OBLIGATORIO: Debe ser ÚNICO, ej: COM-01)");
            String zona = JOptionPane.showInputDialog("Zona geográfica:\n(Texto normal)");

            Comercial nuevo = new Comercial();
            nuevo.setNombre(nombre);
            nuevo.setApellidos(apellidos);
            nuevo.setEmail(email);
            nuevo.setTelefono(telefono);
            nuevo.setCodigoComercial(codigo);
            nuevo.setZonaGeografica(zona);
            nuevo.setFechaAlta(LocalDate.now());

            if (comercialDAO.insertar(nuevo)) txtConsola.append("\n> Comercial guardado en BD.\n");
            else txtConsola.append("\n> Error al guardar en BD. Comprueba que el Email o Código no estén repetidos.\n");

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
                txtConsola.append("ID: " + c.getIdPersona() + " | Cód: " + c.getCodigoComercial() + " | Nombre: " + c.getNombre() + " " + c.getApellidos() + " | Email: " + c.getEmail() + "\n");
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
                pw.println(c.getCodigoComercial() + " - " + c.getNombre() + " - " + c.getEmail());
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

            String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
            String email = JOptionPane.showInputDialog("Email:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getEmail());
            String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
            String codigo = JOptionPane.showInputDialog("Código Comercial:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getCodigoComercial());
            String zona = JOptionPane.showInputDialog("Zona:", c.getZonaGeografica());

            if (nombre != null) c.setNombre(nombre);
            if (email != null) c.setEmail(email);
            if (telefono != null) c.setTelefono(telefono);
            if (codigo != null) c.setCodigoComercial(codigo);
            if (zona != null) c.setZonaGeografica(zona);

            if (comercialDAO.actualizar(c)) {
                txtConsola.append("\n> Comercial actualizado correctamente en la BD.\n");
            } else {
                txtConsola.append("\n> Error al actualizar. Comprueba que el nuevo Email o Código no choquen con otro.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: El ID debe ser un número entero.\n");
        }
    }

    // --- CLIENTES FORMALES ---

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
            String email = JOptionPane.showInputDialog("Email:\n(OBLIGATORIO: Debe ser ÚNICO en toda la BD)");
            String telefono = JOptionPane.showInputDialog("Teléfono:\n(Solo números)");
            String nif = JOptionPane.showInputDialog("NIF/CIF:\n(OBLIGATORIO: Debe ser ÚNICO y tener EXACTAMENTE 9 caracteres)");

            if (nif.length() != 9) {
                throw new NifInvalidoException("El NIF/CIF debe tener exactamente 9 caracteres.");
            }

            String razon = JOptionPane.showInputDialog("Razón Social:\n(Nombre legal de la empresa)");
            String direccion = JOptionPane.showInputDialog("Dirección fiscal:");
            String codigo = JOptionPane.showInputDialog("Código de cliente:\n(OBLIGATORIO: Debe ser ÚNICO, ej: CLI-001)");

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
            else txtConsola.append("\n> Error al guardar en BD. Verifica que el NIF, Email o Código no estén repetidos.\n");

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
                txtConsola.append("ID: " + c.getIdPersona() + " | Cód: " + c.getCodigoCliente() + " | NIF: " + c.getNifCif() + " | Razón: " + c.getRazonSocial() + " | Email: " + c.getEmail() + "\n");
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

    // ---CLIENTES POTENCIALES ---

    private void gestionarPotenciales() {
        String input = JOptionPane.showInputDialog(this,
<<<<<<< HEAD
                "Módulo Potenciales:\n1. Dar de alta\n2. Listar\n3. Borrar\n4. Modificar (UPDATE)\n5. Guardar copia (Serializar)\n6. Cargar copia (Deserializar)\nElige opción:");
=======
                "Módulo Potenciales:\n1. Dar de alta\n2. Listar\n3. Borrar\n4. Modificar\n5. Guardar copia (Serializar)\n6. Cargar copia (Deserializar)\nElige opción:");
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaPotencial();
            else if (opcion == 2) listarPotenciales();
            else if (opcion == 3) borrarPotencial();
            else if (opcion == 4) modificarPotencial();
            else if (opcion == 5) serializarPotenciales();
            else if (opcion == 6) deserializarPotenciales();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaPotencial() {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del contacto:");
            String email = JOptionPane.showInputDialog("Email:\n(OBLIGATORIO: Debe ser ÚNICO en toda la BD)");
            String telefono = JOptionPane.showInputDialog("Teléfono:");
            String empresa = JOptionPane.showInputDialog("Nombre de la empresa:");
            String fuente = JOptionPane.showInputDialog("Fuente de captación:");
            String idComercialStr = JOptionPane.showInputDialog("ID del Comercial asignado:\n(CRÍTICO: DEBE EXISTIR previamente un Comercial con este ID en la base de datos)");

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
            else txtConsola.append("\n> Error al guardar. Verifica que el Email sea único y que el ID del Comercial exista realmente.\n");

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
                txtConsola.append("ID: " + c.getIdPersona() + " | Empresa: " + c.getEmpresa() + " | Email: " + c.getEmail() + " | Estado: " + c.getEstado() + "\n");
            }
        }
        txtConsola.append("----------------------------\n");
    }

    private void borrarPotencial() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (clientePotencialDAO.eliminar(id)) txtConsola.append("\n> Borrado correctamente.\n");
                else txtConsola.append("\n> ID no encontrado.\n");
            } catch (NumberFormatException e) {
                txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }
    }

    private void modificarPotencial() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona del cliente potencial a modificar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            ClientePotencial c = clientePotencialDAO.buscarPotencial(id);

            if (c == null) {
                txtConsola.append("\n> No se encontró ningún cliente potencial con ese ID.\n");
                return;
            }

            String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
            String email = JOptionPane.showInputDialog("Email:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getEmail());
            String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
            String empresa = JOptionPane.showInputDialog("Empresa:", c.getEmpresa());
            String fuente = JOptionPane.showInputDialog("Fuente de captación:", c.getFuenteCaptacion());
            String estado = JOptionPane.showInputDialog("Estado (nuevo, contactado, descartado, etc.):", c.getEstado());
            String idComercialStr = JOptionPane.showInputDialog("ID Comercial Asignado:", String.valueOf(c.getIdComercialAsignado()));

            if (nombre != null) c.setNombre(nombre);
            if (email != null) c.setEmail(email);
            if (telefono != null) c.setTelefono(telefono);
            if (empresa != null) c.setEmpresa(empresa);
            if (fuente != null) c.setFuenteCaptacion(fuente);
            if (estado != null) c.setEstado(estado);
            if (idComercialStr != null) c.setIdComercialAsignado(Integer.parseInt(idComercialStr));

            if (clientePotencialDAO.actualizar(c)) {
                txtConsola.append("\n> Cliente Potencial actualizado correctamente en la BD.\n");
            } else {
                txtConsola.append("\n> Error al actualizar. Comprueba que el nuevo Email no choque con otro o que el ID del comercial exista.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: El ID o ID Comercial debe ser un número entero.\n");
        }
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

<<<<<<< HEAD
    // ---PEDIDOS ---
=======
    private void borrarPotencial() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (clientePotencialDAO.eliminar(id)) txtConsola.append("\n> Cliente potencial borrado correctamente.\n");
                else txtConsola.append("\n> ID no encontrado.\n");
            } catch (NumberFormatException e) {
                txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }
    }

    private void modificarPotencial() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona del potencial a modificar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            ClientePotencial c = clientePotencialDAO.buscarPotencial(id);

            if (c == null) {
                txtConsola.append("\n> No se encontró ningún cliente potencial con ese ID.\n");
                return;
            }

            String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
            String email = JOptionPane.showInputDialog("Email:", c.getEmail());
            String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
            String empresa = JOptionPane.showInputDialog("Empresa:", c.getEmpresa());
            String estado = JOptionPane.showInputDialog("Estado (nuevo/contactado/descartado):", c.getEstado());

            if (nombre != null) c.setNombre(nombre);
            if (email != null) c.setEmail(email);
            if (telefono != null) c.setTelefono(telefono);
            if (empresa != null) c.setEmpresa(empresa);
            if (estado != null) c.setEstado(estado);

            if (clientePotencialDAO.actualizar(c)) {
                txtConsola.append("\n> Cliente potencial actualizado correctamente.\n");
            } else {
                txtConsola.append("\n> Error al actualizar el cliente potencial en la BD.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: El ID debe ser un número entero.\n");
        }
    }

    // --- MÓDULO PEDIDOS ---
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2

    private void gestionarPedidos() {
        // MODIFICADO: Añadidas opciones de Borrar (3) y Modificar (4)
        String input = JOptionPane.showInputDialog(this,
                "Módulo Pedidos:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Modificar (UPDATE)\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaPedido();
            else if (opcion == 2) listarPedidos();
            else if (opcion == 3) borrarPedido();
            else if (opcion == 4) modificarPedido();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaPedido() {
        try {
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:\n(CRÍTICO: DEBE EXISTIR previamente un Cliente Formal con este ID)");
            String idComercial = JOptionPane.showInputDialog("ID del Comercial:\n(CRÍTICO: DEBE EXISTIR previamente un Comercial con este ID)");
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
            else txtConsola.append("\n> Error al guardar. Verifica que los IDs de Cliente y Comercial existan realmente.\n");

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
                txtConsola.append("ID Pedido: " + p.getIdPedido() + " | Cliente ID: " + p.getIdClienteFormal() + " | Estado: " + p.getEstado() + "\n");
            }
        }
        txtConsola.append("---------------\n");
    }

    // BORRAR PEDIDO
    private void borrarPedido() {
        String input = JOptionPane.showInputDialog("ID de Pedido a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (pedidoDAO.eliminar(id)) txtConsola.append("\n> Borrado correctamente.\n");
                else txtConsola.append("\n> ID de pedido no encontrado.\n");
            } catch (NumberFormatException e) {
                txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }
    }

    // MODIFICAR PEDIDO
    private void modificarPedido() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID del pedido a modificar:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            Pedido p = pedidoDAO.buscarPedido(id);

            if (p == null) {
                txtConsola.append("\n> No se encontró ningún pedido con ese ID.\n");
                return;
            }

            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:", String.valueOf(p.getIdClienteFormal()));
            String idComercial = JOptionPane.showInputDialog("ID del Comercial:", String.valueOf(p.getIdComercial()));
            String estado = JOptionPane.showInputDialog("Estado (pendiente, en curso, servido, anulado):", p.getEstado());

            if (estado != null && !pedidoDAO.validarEstadoPedido(estado)) {
                txtConsola.append("\n> Error: El estado introducido no es válido. Cancelando modificación.\n");
                return;
            }

            if (idCliente != null) p.setIdClienteFormal(Integer.parseInt(idCliente));
            if (idComercial != null) p.setIdComercial(Integer.parseInt(idComercial));
            if (estado != null) p.setEstado(estado.toLowerCase());

            if (pedidoDAO.actualizar(p)) {
                txtConsola.append("\n> Pedido actualizado correctamente en la BD.\n");
            } else {
                txtConsola.append("\n> Error al actualizar. Comprueba que los IDs de cliente y comercial existan.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Los IDs deben ser números enteros.\n");
        }
    }

    // --- FACTURAS ---

    private void gestionarFacturas() {
        // MODIFICADO: Añadidas opciones de Borrar (3) y Modificar (4)
        String input = JOptionPane.showInputDialog(this,
                "Módulo Facturas:\n1. Dar de alta\n2. Listar todas\n3. Borrar\n4. Modificar (UPDATE)\nElige una opción:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int opcion = Integer.parseInt(input);
            if (opcion == 1) altaFactura();
            else if (opcion == 2) listarFacturas();
            else if (opcion == 3) borrarFactura();
            else if (opcion == 4) modificarFactura();
            else txtConsola.append("\n> Opción no válida.\n");
        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Introduce un número válido.\n");
        }
    }

    private void altaFactura() {
        try {
            String numero = JOptionPane.showInputDialog("Número de factura:\n(OBLIGATORIO: Debe ser ÚNICO, ej: FAC-001)");
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:\n(CRÍTICO: DEBE EXISTIR previamente un Cliente Formal con este ID)");
            String idPedido = JOptionPane.showInputDialog("ID del Pedido asociado:\n(CRÍTICO: DEBE EXISTIR previamente un Pedido con este ID)");
            String baseImponible = JOptionPane.showInputDialog("Base Imponible:\n(Importe sin IVA, ej: 100.50)");

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
            else txtConsola.append("\n> Error al guardar. Verifica que el Número sea único y que los IDs de Cliente/Pedido existan.\n");

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

<<<<<<< HEAD
    // BORRAR FACTURA
    private void borrarFactura() {
        String input = JOptionPane.showInputDialog("Número de Factura a borrar:");
        if (input != null && !input.trim().isEmpty()) {
            if (facturaDAO.eliminar(input)) txtConsola.append("\n> Factura borrada correctamente.\n");
            else txtConsola.append("\n> Número de factura no encontrado.\n");
        }
    }

    // MODIFICAR FACTURA
    private void modificarFactura() {
        String numeroFactura = JOptionPane.showInputDialog("Introduce el Número de Factura a modificar:");
        if (numeroFactura == null || numeroFactura.trim().isEmpty()) return;

        try {
            Factura f = facturaDAO.buscarFactura(numeroFactura);

            if (f == null) {
                txtConsola.append("\n> No se encontró ninguna factura con ese número.\n");
                return;
            }

            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:", String.valueOf(f.getIdClienteFormal()));
            String idPedido = JOptionPane.showInputDialog("ID del Pedido asociado:", String.valueOf(f.getIdPedido()));
            String baseImponible = JOptionPane.showInputDialog("Base Imponible:", String.valueOf(f.getBaseImponible()));
            String estado = JOptionPane.showInputDialog("Estado (pendiente, cobrada, anulada, etc.):", f.getEstado());

            if (idCliente != null) f.setIdClienteFormal(Integer.parseInt(idCliente));
            if (idPedido != null) f.setIdPedido(Integer.parseInt(idPedido));

            if (baseImponible != null) {
                double base = Double.parseDouble(baseImponible);
                f.setBaseImponible(base);
                f.setTotal(base + (base * (f.getTipoIva() / 100))); // Recalcular el total al cambiar la base
            }

            if (estado != null) f.setEstado(estado.toLowerCase());

            if (facturaDAO.actualizar(f)) {
                txtConsola.append("\n> Factura actualizada correctamente en la BD.\n");
            } else {
                txtConsola.append("\n> Error al actualizar. Comprueba que los IDs de cliente y pedido existan.\n");
            }

        } catch (NumberFormatException e) {
            txtConsola.append("\n> Error: Los IDs deben ser enteros y la base imponible debe ser numérica (ej: 100.50).\n");
=======
    // --- COMPROBACIÓN DE CONEXIÓN ---

    /**
     * Comprueba si la conexión con la base de datos está activa y la muestra por consola.
     *
     * @author CRM-XTART Team
     * @version 1.0
     */
    private void comprobarConexion() {
        try (Connection conn = ConexionBD.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                txtConsola.append("\n> Conexión con la BD establecida correctamente.\n");
            } else {
                txtConsola.append("\n> No se pudo establecer conexión con la BD.\n");
            }
        } catch (Exception e) {
            txtConsola.append("\n> Error al comprobar la conexión: " + e.getMessage() + "\n");
>>>>>>> 1ccd50291a0558ebad9df4bab284815145f5bbf2
        }
    }
}