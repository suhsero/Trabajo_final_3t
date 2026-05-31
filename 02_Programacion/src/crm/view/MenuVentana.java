package crm.view;

import crm.dao.ClienteFormalDAO;
import crm.dao.ClientePotencialDAO;
import crm.dao.ComercialDAO;
import crm.dao.FacturaDAO;
import crm.dao.PedidoDAO;
import crm.database.ConexionBD;
import crm.exception.EmailInvalidoException;
import crm.exception.NifInvalidoException;
import crm.model.ClienteFormal;
import crm.model.ClientePotencial;
import crm.model.Comercial;
import crm.model.Factura;
import crm.model.Pedido;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Ventana principal de navegación del sistema CRM XTART.
 * Presenta un menú numerado con acceso a los módulos de Comerciales,
 * Clientes Potenciales, Clientes Formales, Pedidos, Facturas y conexión BD.
 * Incluye funcionalidades de exportación a fichero TXT (E/S ficheros)
 * y serialización/deserialización de colecciones (ObjectOutputStream/ObjectInputStream).
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class MenuVentana extends JFrame {
    private JTextArea txtConsola;
    private JTextField txtOpcion;
    private JButton btnEnviar;
    private ComercialDAO comercialDAO = new ComercialDAO();
    private ClienteFormalDAO clienteFormalDAO = new ClienteFormalDAO();
    private ClientePotencialDAO clientePotencialDAO = new ClientePotencialDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();

    /**
     * Construye y configura la ventana del menú principal.
     * Inicializa el área de texto de consola, el campo de opción y el botón
     * de envío, y muestra el menú inicial.
     */
    public MenuVentana() {
        this.setTitle("CRM - Menú Principal");
        this.setSize(700, 550);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout((LayoutManager)null);
        this.txtConsola = new JTextArea();
        this.txtConsola.setEditable(false);
        JScrollPane scroll = new JScrollPane(this.txtConsola);
        scroll.setBounds(20, 20, 640, 400);
        this.add(scroll);
        JLabel lblInfo = new JLabel("Elige una opción:");
        lblInfo.setBounds(20, 440, 120, 25);
        this.add(lblInfo);
        this.txtOpcion = new JTextField();
        this.txtOpcion.setBounds(130, 440, 50, 25);
        this.add(this.txtOpcion);
        this.btnEnviar = new JButton("Enviar");
        this.btnEnviar.setBounds(190, 440, 90, 25);
        this.add(this.btnEnviar);
        this.imprimirMenu();
        this.btnEnviar.addActionListener(new ActionListener() {
            {
                Objects.requireNonNull(MenuVentana.this);
            }

            public void actionPerformed(ActionEvent e) {
                MenuVentana.this.procesarOpcion();
            }
        });
    }

    /** Muestra el menú principal con las opciones disponibles en el área de consola. */
    private void imprimirMenu() {
        this.txtConsola.setText("--- MENU PRINCIPAL CRM XTART ---\n");
        this.txtConsola.append("1. Gestión de Comerciales\n");
        this.txtConsola.append("2. Gestión de Clientes Potenciales\n");
        this.txtConsola.append("3. Gestión de Clientes Formales\n");
        this.txtConsola.append("4. Gestión de Pedidos\n");
        this.txtConsola.append("5. Gestión de Facturas\n");
        this.txtConsola.append("6. Comprobar conexión a BD\n");
        this.txtConsola.append("7. Salir del programa\n");
        this.txtConsola.append("--------------------------------\n");
        this.txtConsola.append("Introduce el número de la opción y pulsa Enviar.\n");
    }

    /**
     * Lee la opción introducida por el usuario y delega en el método
     * correspondiente a cada módulo. Muestra un error si el valor no es numérico
     * o está fuera del rango 1-7.
     */
    private void procesarOpcion() {
        String texto = this.txtOpcion.getText();
        this.txtOpcion.setText("");

        try {
            int opcion = Integer.parseInt(texto);
            switch (opcion) {
                case 1:
                    this.gestionarComerciales();
                    break;
                case 2:
                    this.gestionarPotenciales();
                    break;
                case 3:
                    this.gestionarClientesFormales();
                    break;
                case 4:
                    this.gestionarPedidos();
                    break;
                case 5:
                    this.gestionarFacturas();
                    break;
                case 6:
                    this.comprobarConexion();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(this, "Saliendo del sistema CRM.");
                    System.exit(0);
                    break;
                default:
                    this.txtConsola.append("\n> Opción no válida. Introduce un número del 1 al 7.\n");
            }
        } catch (NumberFormatException var3) {
            this.txtConsola.append("\n> Error: Debes introducir un número entero.\n");
        }

    }

    /** Comprueba la conexión JDBC con la base de datos e informa del resultado en la consola. */
    private void comprobarConexion() {
        this.txtConsola.append("\n> Comprobando conexión con la base de datos MySQL...\n");

        try (Connection con = ConexionBD.getConexion()) {
            if (con != null) {
                this.txtConsola.append("> ¡ÉXITO! La conexión a la base de datos funciona correctamente.\n");
            } else {
                this.txtConsola.append("> ERROR: La conexión ha devuelto un valor nulo. Revisa XAMPP/MySQL.\n");
            }
        } catch (Exception e) {
            this.txtConsola.append("> ERROR de conexión: " + e.getMessage() + "\n");
            this.txtConsola.append("> Verifica que la base de datos 'crmxtart' existe y el puerto es correcto.\n");
        }

    }

    /** Muestra el submenú de Comerciales y delega en la opción seleccionada. */
    private void gestionarComerciales() {
        String input = JOptionPane.showInputDialog(this, "Módulo Comerciales:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Exportar a TXT\n5. Modificar (UPDATE)\nElige una opción:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int opcion = Integer.parseInt(input);
                if (opcion == 1) {
                    this.altaComercial();
                } else if (opcion == 2) {
                    this.listarComerciales();
                } else if (opcion == 3) {
                    this.borrarComercial();
                } else if (opcion == 4) {
                    this.exportarComercialesTXT();
                } else if (opcion == 5) {
                    this.modificarComercial();
                } else {
                    this.txtConsola.append("\n> Opción no válida.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> Error: Introduce un número válido.\n");
            }

        }
    }

    /**
     * Solicita los datos de un nuevo comercial mediante diálogos de entrada,
     * valida el formato del email y lo persiste en la base de datos a través de
     * {@link crm.dao.ComercialDAO#insertar(crm.model.Comercial)}.
     */
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
            if (this.comercialDAO.insertar(nuevo)) {
                this.txtConsola.append("\n> Comercial guardado en BD.\n");
            } else {
                this.txtConsola.append("\n> Error al guardar en BD. Comprueba que el Email o Código no estén repetidos.\n");
            }
        } catch (EmailInvalidoException ex) {
            this.txtConsola.append("\n> Error de validación: " + ex.getMessage() + "\n");
        } catch (Exception var9) {
            this.txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }

    }

    /** Recupera todos los comerciales de la BD y los muestra en la consola. */
    private void listarComerciales() {
        ArrayList<Comercial> lista = this.comercialDAO.listarTodos();
        this.txtConsola.append("\n--- COMERCIALES ---\n");
        if (lista.isEmpty()) {
            this.txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for(Comercial c : lista) {
                JTextArea var10000 = this.txtConsola;
                int var10001 = c.getIdPersona();
                var10000.append("ID: " + var10001 + " | Cód: " + c.getCodigoComercial() + " | Nombre: " + c.getNombre() + " " + c.getApellidos() + " | Email: " + c.getEmail() + "\n");
            }
        }

        this.txtConsola.append("-------------------\n");
    }

    /** Solicita un ID de Persona y elimina el comercial correspondiente de la BD. */
    private void borrarComercial() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (this.comercialDAO.eliminar(id)) {
                    this.txtConsola.append("\n> Borrado correctamente.\n");
                } else {
                    this.txtConsola.append("\n> ID no encontrado.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }

    }

    /**
     * Exporta el listado completo de comerciales a un fichero de texto plano
     * llamado {@code comerciales.txt} usando {@link java.io.FileWriter} y
     * {@link java.io.PrintWriter} (E/S de ficheros).
     */
    private void exportarComercialesTXT() {
        ArrayList<Comercial> lista = this.comercialDAO.listarTodos();
        if (lista.isEmpty()) {
            this.txtConsola.append("\n> No hay datos para exportar.\n");
        } else {
            try (PrintWriter pw = new PrintWriter(new FileWriter("comerciales.txt"))) {
                pw.println("LISTADO DE COMERCIALES");

                for(Comercial c : lista) {
                    String var10001 = c.getCodigoComercial();
                    pw.println(var10001 + " - " + c.getNombre() + " - " + c.getEmail());
                }

                this.txtConsola.append("\n> Exportado a 'comerciales.txt'.\n");
            } catch (Exception var7) {
                this.txtConsola.append("\n> Error al generar archivo de exportación.\n");
            }

        }
    }

    /**
     * Busca un comercial por ID, solicita los nuevos datos mediante diálogos
     * y los actualiza en la BD mediante
     * {@link crm.dao.ComercialDAO#actualizar(crm.model.Comercial)}.
     */
    private void modificarComercial() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona del comercial a modificar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Comercial c = this.comercialDAO.buscarComercial(id);
                if (c == null) {
                    this.txtConsola.append("\n> No se encontró ningún comercial con ese ID.\n");
                    return;
                }

                String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
                String email = JOptionPane.showInputDialog("Email:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getEmail());
                String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
                String codigo = JOptionPane.showInputDialog("Código Comercial:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getCodigoComercial());
                String zona = JOptionPane.showInputDialog("Zona:", c.getZonaGeografica());
                if (nombre != null) {
                    c.setNombre(nombre);
                }

                if (email != null) {
                    c.setEmail(email);
                }

                if (telefono != null) {
                    c.setTelefono(telefono);
                }

                if (codigo != null) {
                    c.setCodigoComercial(codigo);
                }

                if (zona != null) {
                    c.setZonaGeografica(zona);
                }

                if (this.comercialDAO.actualizar(c)) {
                    this.txtConsola.append("\n> Comercial actualizado correctamente en la BD.\n");
                } else {
                    this.txtConsola.append("\n> Error al actualizar. Comprueba que el nuevo Email o Código no choquen con otro.\n");
                }
            } catch (NumberFormatException var9) {
                this.txtConsola.append("\n> Error: El ID debe ser un número entero.\n");
            }

        }
    }

    /** Muestra el submenú de Clientes Formales y delega en la opción seleccionada. */
    private void gestionarClientesFormales() {
        String input = JOptionPane.showInputDialog(this, "Módulo Clientes Formales:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Buscar Cliente\nElige una opción:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int opcion = Integer.parseInt(input);
                if (opcion == 1) {
                    this.altaClienteFormal();
                } else if (opcion == 2) {
                    this.listarClientesFormales();
                } else if (opcion == 3) {
                    this.borrarClienteFormal();
                } else if (opcion == 4) {
                    this.buscarClienteFormal();
                } else {
                    this.txtConsola.append("\n> Opción no válida.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> Error: Introduce un número válido.\n");
            }

        }
    }

    /**
     * Solicita los datos de un nuevo cliente formal, valida el NIF/CIF (9 chars)
     * y lo persiste en la BD mediante
     * {@link crm.dao.ClienteFormalDAO#insertar(crm.model.ClienteFormal)}.
     */
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
            nuevo.setDescuentoHabitual((double)0.0F);
            nuevo.setEstado("activo");
            if (this.clienteFormalDAO.insertar(nuevo)) {
                this.txtConsola.append("\n> Cliente Formal guardado en BD.\n");
            } else {
                this.txtConsola.append("\n> Error al guardar en BD. Verifica que el NIF, Email o Código no estén repetidos.\n");
            }
        } catch (NifInvalidoException ex) {
            this.txtConsola.append("\n> Error de NIF: " + ex.getMessage() + "\n");
        } catch (Exception var10) {
            this.txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }

    }

    /** Recupera todos los clientes formales de la BD y los muestra en la consola. */
    private void listarClientesFormales() {
        ArrayList<ClienteFormal> lista = this.clienteFormalDAO.listarTodos();
        this.txtConsola.append("\n--- CLIENTES FORMALES ---\n");
        if (lista.isEmpty()) {
            this.txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for(ClienteFormal c : lista) {
                JTextArea var10000 = this.txtConsola;
                int var10001 = c.getIdPersona();
                var10000.append("ID: " + var10001 + " | Cód: " + c.getCodigoCliente() + " | NIF: " + c.getNifCif() + " | Razón: " + c.getRazonSocial() + " | Email: " + c.getEmail() + "\n");
            }
        }

        this.txtConsola.append("-------------------------\n");
    }

    /** Solicita un ID de Persona y elimina el cliente formal correspondiente de la BD. */
    private void borrarClienteFormal() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (this.clienteFormalDAO.eliminar(id)) {
                    this.txtConsola.append("\n> Borrado correctamente.\n");
                } else {
                    this.txtConsola.append("\n> ID no encontrado.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }

    }

    /**
     * Permite buscar un cliente formal por ID de Persona o por NIF/CIF
     * y muestra el resultado en la consola.
     */
    private void buscarClienteFormal() {
        String modoStr = JOptionPane.showInputDialog("Buscar por:\n1. ID de Persona\n2. NIF/CIF");
        if (modoStr != null && !modoStr.trim().isEmpty()) {
            try {
                int modo = Integer.parseInt(modoStr);
                ClienteFormal resultado = null;
                if (modo == 1) {
                    String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona:");
                    if (idStr != null) {
                        resultado = this.clienteFormalDAO.buscarCliente(Integer.parseInt(idStr));
                    }
                } else {
                    if (modo != 2) {
                        this.txtConsola.append("\n> Opción de búsqueda no válida.\n");
                        return;
                    }

                    String nifStr = JOptionPane.showInputDialog("Introduce el NIF/CIF:");
                    if (nifStr != null) {
                        resultado = this.clienteFormalDAO.buscarCliente(nifStr);
                    }
                }

                if (resultado != null) {
                    JTextArea var10000 = this.txtConsola;
                    String var10001 = resultado.getRazonSocial();
                    var10000.append("\n> Cliente Encontrado: " + var10001 + " (NIF: " + resultado.getNifCif() + ")\n");
                } else {
                    this.txtConsola.append("\n> No se ha encontrado ningún cliente con esos datos.\n");
                }
            } catch (NumberFormatException var5) {
                this.txtConsola.append("\n> Error en los datos introducidos.\n");
            }

        }
    }

    /** Muestra el submenú de Clientes Potenciales y delega en la opción seleccionada. */
    private void gestionarPotenciales() {
        String input = JOptionPane.showInputDialog(this, "Módulo Potenciales:\n1. Dar de alta\n2. Listar\n3. Borrar\n4. Modificar (UPDATE)\n5. Guardar copia (Serializar)\n6. Cargar copia (Deserializar)\nElige opción:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int opcion = Integer.parseInt(input);
                if (opcion == 1) {
                    this.altaPotencial();
                } else if (opcion == 2) {
                    this.listarPotenciales();
                } else if (opcion == 3) {
                    this.borrarPotencial();
                } else if (opcion == 4) {
                    this.modificarPotencial();
                } else if (opcion == 5) {
                    this.serializarPotenciales();
                } else if (opcion == 6) {
                    this.deserializarPotenciales();
                } else {
                    this.txtConsola.append("\n> Opción no válida.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> Error: Introduce un número válido.\n");
            }

        }
    }

    /**
     * Solicita los datos de un nuevo cliente potencial y lo persiste en la BD
     * mediante {@link crm.dao.ClientePotencialDAO#insertar(crm.model.ClientePotencial)}.
     */
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
            if (this.clientePotencialDAO.insertar(nuevo)) {
                this.txtConsola.append("\n> Cliente Potencial guardado en BD.\n");
            } else {
                this.txtConsola.append("\n> Error al guardar. Verifica que el Email sea único y que el ID del Comercial exista realmente.\n");
            }
        } catch (Exception var8) {
            this.txtConsola.append("\n> Alta cancelada o datos incorrectos.\n");
        }

    }

    /** Recupera todos los clientes potenciales de la BD y los muestra en la consola. */
    private void listarPotenciales() {
        ArrayList<ClientePotencial> lista = this.clientePotencialDAO.listarTodos();
        this.txtConsola.append("\n--- CLIENTES POTENCIALES ---\n");
        if (lista.isEmpty()) {
            this.txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for(ClientePotencial c : lista) {
                JTextArea var10000 = this.txtConsola;
                int var10001 = c.getIdPersona();
                var10000.append("ID: " + var10001 + " | Empresa: " + c.getEmpresa() + " | Email: " + c.getEmail() + " | Estado: " + c.getEstado() + "\n");
            }
        }

        this.txtConsola.append("----------------------------\n");
    }

    /** Solicita un ID de Persona y elimina el cliente potencial correspondiente de la BD. */
    private void borrarPotencial() {
        String input = JOptionPane.showInputDialog("ID de Persona a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (this.clientePotencialDAO.eliminar(id)) {
                    this.txtConsola.append("\n> Borrado correctamente.\n");
                } else {
                    this.txtConsola.append("\n> ID no encontrado.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }

    }

    /**
     * Busca un cliente potencial por ID, solicita los nuevos datos mediante diálogos
     * y los actualiza en la BD.
     */
    private void modificarPotencial() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID de Persona del cliente potencial a modificar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                ClientePotencial c = this.clientePotencialDAO.buscarPotencial(id);
                if (c == null) {
                    this.txtConsola.append("\n> No se encontró ningún cliente potencial con ese ID.\n");
                    return;
                }

                String nombre = JOptionPane.showInputDialog("Nombre:", c.getNombre());
                String email = JOptionPane.showInputDialog("Email:\n(CUIDADO: Si lo cambias, debe seguir siendo ÚNICO)", c.getEmail());
                String telefono = JOptionPane.showInputDialog("Teléfono:", c.getTelefono());
                String empresa = JOptionPane.showInputDialog("Empresa:", c.getEmpresa());
                String fuente = JOptionPane.showInputDialog("Fuente de captación:", c.getFuenteCaptacion());
                String estado = JOptionPane.showInputDialog("Estado (nuevo, contactado, descartado, etc.):", c.getEstado());
                String idComercialStr = JOptionPane.showInputDialog("ID Comercial Asignado:", String.valueOf(c.getIdComercialAsignado()));
                if (nombre != null) {
                    c.setNombre(nombre);
                }

                if (email != null) {
                    c.setEmail(email);
                }

                if (telefono != null) {
                    c.setTelefono(telefono);
                }

                if (empresa != null) {
                    c.setEmpresa(empresa);
                }

                if (fuente != null) {
                    c.setFuenteCaptacion(fuente);
                }

                if (estado != null) {
                    c.setEstado(estado);
                }

                if (idComercialStr != null) {
                    c.setIdComercialAsignado(Integer.parseInt(idComercialStr));
                }

                if (this.clientePotencialDAO.actualizar(c)) {
                    this.txtConsola.append("\n> Cliente Potencial actualizado correctamente en la BD.\n");
                } else {
                    this.txtConsola.append("\n> Error al actualizar. Comprueba que el nuevo Email no choque con otro o que el ID del comercial exista.\n");
                }
            } catch (NumberFormatException var11) {
                this.txtConsola.append("\n> Error: El ID o ID Comercial debe ser un número entero.\n");
            }

        }
    }

    /**
     * Serializa la lista completa de clientes potenciales en el fichero
     * {@code potenciales_backup.dat} usando {@link java.io.ObjectOutputStream}
     * (serialización de objetos Java).
     */
    private void serializarPotenciales() {
        ArrayList<ClientePotencial> lista = this.clientePotencialDAO.listarTodos();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("potenciales_backup.dat"))) {
            oos.writeObject(lista);
            this.txtConsola.append("\n> Copia serializada correctamente en 'potenciales_backup.dat'.\n");
        } catch (Exception var7) {
            this.txtConsola.append("\n> Error al serializar los datos.\n");
        }

    }

    /**
     * Deserializa el fichero {@code potenciales_backup.dat} y muestra
     * los objetos {@link crm.model.ClientePotencial} recuperados en la consola,
     * usando {@link java.io.ObjectInputStream}.
     */
    private void deserializarPotenciales() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("potenciales_backup.dat"))) {
            ArrayList<ClientePotencial> recuperados = (ArrayList)ois.readObject();
            this.txtConsola.append("\n--- DATOS RECUPERADOS DEL BACKUP ---\n");

            for(ClientePotencial c : recuperados) {
                JTextArea var10000 = this.txtConsola;
                String var10001 = c.getNombre();
                var10000.append("Recuperado: " + var10001 + " de la empresa " + c.getEmpresa() + "\n");
            }
        } catch (Exception var7) {
            this.txtConsola.append("\n> Error al leer el archivo.\n");
        }

    }

    /** Muestra el submenú de Pedidos y delega en la opción seleccionada. */
    private void gestionarPedidos() {
        String input = JOptionPane.showInputDialog(this, "Módulo Pedidos:\n1. Dar de alta\n2. Listar todos\n3. Borrar\n4. Modificar (UPDATE)\nElige una opción:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int opcion = Integer.parseInt(input);
                if (opcion == 1) {
                    this.altaPedido();
                } else if (opcion == 2) {
                    this.listarPedidos();
                } else if (opcion == 3) {
                    this.borrarPedido();
                } else if (opcion == 4) {
                    this.modificarPedido();
                } else {
                    this.txtConsola.append("\n> Opción no válida.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> Error: Introduce un número válido.\n");
            }

        }
    }

    /**
     * Solicita los datos de un nuevo pedido, valida el estado y lo persiste
     * en la BD mediante {@link crm.dao.PedidoDAO#insertar(crm.model.Pedido)}.
     */
    private void altaPedido() {
        try {
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:\n(CRÍTICO: DEBE EXISTIR previamente un Cliente Formal con este ID)");
            String idComercial = JOptionPane.showInputDialog("ID del Comercial:\n(CRÍTICO: DEBE EXISTIR previamente un Comercial con este ID)");
            String estado = JOptionPane.showInputDialog("Estado (pendiente, en curso, servido, anulado):");
            if (!this.pedidoDAO.validarEstadoPedido(estado)) {
                this.txtConsola.append("\n> Error: El estado '" + estado + "' no es válido.\n");
                return;
            }

            Pedido nuevo = new Pedido();
            nuevo.setFechaPedido(LocalDateTime.now());
            nuevo.setIdClienteFormal(Integer.parseInt(idCliente));
            nuevo.setIdComercial(Integer.parseInt(idComercial));
            nuevo.setEstado(estado.toLowerCase());
            if (this.pedidoDAO.insertar(nuevo)) {
                this.txtConsola.append("\n> Pedido guardado en BD.\n");
            } else {
                this.txtConsola.append("\n> Error al guardar. Verifica que los IDs de Cliente y Comercial existan realmente.\n");
            }
        } catch (Exception var5) {
            this.txtConsola.append("\n> Alta de pedido cancelada o datos incorrectos.\n");
        }

    }

    /** Recupera todos los pedidos de la BD y los muestra en la consola. */
    private void listarPedidos() {
        ArrayList<Pedido> lista = this.pedidoDAO.listarTodos();
        this.txtConsola.append("\n--- PEDIDOS ---\n");
        if (lista.isEmpty()) {
            this.txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for(Pedido p : lista) {
                JTextArea var10000 = this.txtConsola;
                int var10001 = p.getIdPedido();
                var10000.append("ID Pedido: " + var10001 + " | Cliente ID: " + p.getIdClienteFormal() + " | Estado: " + p.getEstado() + "\n");
            }
        }

        this.txtConsola.append("---------------\n");
    }

    /** Solicita un ID de pedido y lo elimina de la BD. */
    private void borrarPedido() {
        String input = JOptionPane.showInputDialog("ID de Pedido a borrar:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                if (this.pedidoDAO.eliminar(id)) {
                    this.txtConsola.append("\n> Borrado correctamente.\n");
                } else {
                    this.txtConsola.append("\n> ID de pedido no encontrado.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> El ID debe ser numérico.\n");
            }
        }

    }

    /**
     * Busca un pedido por ID, solicita los nuevos datos (cliente, comercial, estado)
     * y los actualiza en la BD.
     */
    private void modificarPedido() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID del pedido a modificar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Pedido p = this.pedidoDAO.buscarPedido(id);
                if (p == null) {
                    this.txtConsola.append("\n> No se encontró ningún pedido con ese ID.\n");
                    return;
                }

                String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:", String.valueOf(p.getIdClienteFormal()));
                String idComercial = JOptionPane.showInputDialog("ID del Comercial:", String.valueOf(p.getIdComercial()));
                String estado = JOptionPane.showInputDialog("Estado (pendiente, en curso, servido, anulado):", p.getEstado());
                if (estado != null && !this.pedidoDAO.validarEstadoPedido(estado)) {
                    this.txtConsola.append("\n> Error: El estado introducido no es válido. Cancelando modificación.\n");
                    return;
                }

                if (idCliente != null) {
                    p.setIdClienteFormal(Integer.parseInt(idCliente));
                }

                if (idComercial != null) {
                    p.setIdComercial(Integer.parseInt(idComercial));
                }

                if (estado != null) {
                    p.setEstado(estado.toLowerCase());
                }

                if (this.pedidoDAO.actualizar(p)) {
                    this.txtConsola.append("\n> Pedido actualizado correctamente en la BD.\n");
                } else {
                    this.txtConsola.append("\n> Error al actualizar. Comprueba que los IDs de cliente y comercial existan.\n");
                }
            } catch (NumberFormatException var7) {
                this.txtConsola.append("\n> Error: Los IDs deben ser números enteros.\n");
            }

        }
    }

    /** Muestra el submenú de Facturas y delega en la opción seleccionada. */
    private void gestionarFacturas() {
        String input = JOptionPane.showInputDialog(this, "Módulo Facturas:\n1. Dar de alta\n2. Listar todas\n3. Borrar\n4. Modificar (UPDATE)\nElige una opción:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int opcion = Integer.parseInt(input);
                if (opcion == 1) {
                    this.altaFactura();
                } else if (opcion == 2) {
                    this.listarFacturas();
                } else if (opcion == 3) {
                    this.borrarFactura();
                } else if (opcion == 4) {
                    this.modificarFactura();
                } else {
                    this.txtConsola.append("\n> Opción no válida.\n");
                }
            } catch (NumberFormatException var3) {
                this.txtConsola.append("\n> Error: Introduce un número válido.\n");
            }

        }
    }

    /**
     * Solicita los datos de una nueva factura, calcula el total con IVA del 21%
     * y la persiste en la BD mediante
     * {@link crm.dao.FacturaDAO#insertar(crm.model.Factura)}.
     */
    private void altaFactura() {
        try {
            String numero = JOptionPane.showInputDialog("Número de factura:\n(OBLIGATORIO: Debe ser ÚNICO, ej: FAC-001)");
            String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:\n(CRÍTICO: DEBE EXISTIR previamente un Cliente Formal con este ID)");
            String idPedido = JOptionPane.showInputDialog("ID del Pedido asociado:\n(CRÍTICO: DEBE EXISTIR previamente un Pedido con este ID)");
            String baseImponible = JOptionPane.showInputDialog("Base Imponible:\n(Importe sin IVA, ej: 100.50)");
            double base = Double.parseDouble(baseImponible);
            double iva = (double)21.0F;
            double total = base + base * (iva / (double)100.0F);
            Factura nueva = new Factura();
            nueva.setNumeroFactura(numero);
            nueva.setFechaEmision(LocalDate.now());
            nueva.setFechaVencimiento(LocalDate.now().plusDays(30L));
            nueva.setIdClienteFormal(Integer.parseInt(idCliente));
            nueva.setIdPedido(Integer.parseInt(idPedido));
            nueva.setBaseImponible(base);
            nueva.setTipoIva(iva);
            nueva.setTotal(total);
            nueva.setEstado("pendiente");
            if (this.facturaDAO.insertar(nueva)) {
                this.txtConsola.append("\n> Factura guardada en BD.\n");
            } else {
                this.txtConsola.append("\n> Error al guardar. Verifica que el Número sea único y que los IDs de Cliente/Pedido existan.\n");
            }
        } catch (Exception var12) {
            this.txtConsola.append("\n> Alta de factura cancelada o datos incorrectos.\n");
        }

    }

    /** Recupera todas las facturas de la BD y las muestra en la consola. */
    private void listarFacturas() {
        ArrayList<Factura> lista = this.facturaDAO.listarTodos();
        this.txtConsola.append("\n--- FACTURAS ---\n");
        if (lista.isEmpty()) {
            this.txtConsola.append("No hay registros en la base de datos.\n");
        } else {
            for(Factura f : lista) {
                JTextArea var10000 = this.txtConsola;
                String var10001 = f.getNumeroFactura();
                var10000.append("Nº: " + var10001 + " | Total: " + f.getTotal() + "€ | Estado: " + f.getEstado() + "\n");
            }
        }

        this.txtConsola.append("----------------\n");
    }

    /** Solicita el número de factura y la elimina de la BD. */
    private void borrarFactura() {
        String input = JOptionPane.showInputDialog("Número de Factura a borrar:");
        if (input != null && !input.trim().isEmpty()) {
            if (this.facturaDAO.eliminar(input)) {
                this.txtConsola.append("\n> Factura borrada correctamente.\n");
            } else {
                this.txtConsola.append("\n> Número de factura no encontrado.\n");
            }
        }

    }

    /**
     * Busca una factura por su número, solicita los nuevos datos y los actualiza
     * en la BD, recalculando el total si cambia la base imponible.
     */
    private void modificarFactura() {
        String numeroFactura = JOptionPane.showInputDialog("Introduce el Número de Factura a modificar:");
        if (numeroFactura != null && !numeroFactura.trim().isEmpty()) {
            try {
                Factura f = this.facturaDAO.buscarFactura(numeroFactura);
                if (f == null) {
                    this.txtConsola.append("\n> No se encontró ninguna factura con ese número.\n");
                    return;
                }

                String idCliente = JOptionPane.showInputDialog("ID del Cliente Formal:", String.valueOf(f.getIdClienteFormal()));
                String idPedido = JOptionPane.showInputDialog("ID del Pedido asociado:", String.valueOf(f.getIdPedido()));
                String baseImponible = JOptionPane.showInputDialog("Base Imponible:", String.valueOf(f.getBaseImponible()));
                String estado = JOptionPane.showInputDialog("Estado (pendiente, cobrada, anulada, etc.):", f.getEstado());
                if (idCliente != null) {
                    f.setIdClienteFormal(Integer.parseInt(idCliente));
                }

                if (idPedido != null) {
                    f.setIdPedido(Integer.parseInt(idPedido));
                }

                if (baseImponible != null) {
                    double base = Double.parseDouble(baseImponible);
                    f.setBaseImponible(base);
                    f.setTotal(base + base * (f.getTipoIva() / (double)100.0F));
                }

                if (estado != null) {
                    f.setEstado(estado.toLowerCase());
                }

                if (this.facturaDAO.actualizar(f)) {
                    this.txtConsola.append("\n> Factura actualizada correctamente en la BD.\n");
                } else {
                    this.txtConsola.append("\n> Error al actualizar. Comprueba que los IDs de cliente y pedido existan.\n");
                }
            } catch (NumberFormatException var9) {
                this.txtConsola.append("\n> Error: Los IDs deben ser enteros y la base imponible debe ser numérica (ej: 100.50).\n");
            }

        }
    }
}
