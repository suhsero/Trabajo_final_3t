package crm.main;

import crm.view.Login;

/**
 * Punto de entrada principal de la aplicación CRM XTART.
 * Lanza la ventana de inicio de sesión al arrancar el programa.
 *
 * @author Javier Stampa García, Joel Guadalix y Francisco José Álvarez
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de la aplicación.
     * Crea y muestra la ventana de {@link crm.view.Login} para autenticar al usuario.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Login ventana = new Login();
        ventana.setVisible(true);
    }
}
