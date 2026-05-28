package crm.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    public static final int MAX_INTENTOS_LOGIN = 3;
    private int intentos = 0;

    private JTextField txtUsuario;
    private JPasswordField txtPass;
    private JButton btnEntrar;

    public Login() {
        setTitle("CRM  - Acceso");
        setSize(320, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(40, 40, 80, 25);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 40, 140, 25);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Clave:");
        lblPass.setBounds(40, 80, 80, 25);
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(120, 80, 140, 25);
        add(txtPass);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(100, 130, 100, 30);
        add(btnEntrar);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobarLogin();
            }
        });
    }

    private void comprobarLogin() {
        String user = txtUsuario.getText();
        String pass = new String(txtPass.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Acceso correcto.");

            MenuVentana menu = new MenuVentana();
            menu.setVisible(true);

            this.dispose();

        } else {
            intentos++;
            if (intentos >= MAX_INTENTOS_LOGIN) {
                JOptionPane.showMessageDialog(this, "Demasiados intentos. Se cierra el programa.");
                System.exit(0);
            } else {
                int quedan = MAX_INTENTOS_LOGIN - intentos;
                JOptionPane.showMessageDialog(this, "Datos incorrectos. Te quedan " + quedan + " intentos.");
            }
        }
    }
}