package crm.main;

import crm.view.Login;

/**
 * Punto de entrada de la aplicación CRM XTART.
 * Inicializa y muestra la ventana de autenticación.
 *
 * @author Javier
 * @version 1.0
 */
public class Main {

    /**
     * Método principal. Crea y muestra la ventana de Login.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Login ventana = new Login();
        ventana.setVisible(true);
    }
}
