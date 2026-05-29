package crm.view;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
    public static final int MAX_INTENTOS_LOGIN = 3;
    private int intentos = 0;
    private JTextField txtUsuario;
    private JPasswordField txtPass;
    private JButton btnEntrar;

    public Login() {
        this.setTitle("CRM  - Acceso");
        this.setSize(320, 220);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout((LayoutManager)null);
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setBounds(40, 40, 80, 25);
        this.add(lblUser);
        this.txtUsuario = new JTextField();
        this.txtUsuario.setBounds(120, 40, 140, 25);
        this.add(this.txtUsuario);
        JLabel lblPass = new JLabel("Clave:");
        lblPass.setBounds(40, 80, 80, 25);
        this.add(lblPass);
        this.txtPass = new JPasswordField();
        this.txtPass.setBounds(120, 80, 140, 25);
        this.add(this.txtPass);
        this.btnEntrar = new JButton("Entrar");
        this.btnEntrar.setBounds(100, 130, 100, 30);
        this.add(this.btnEntrar);
        this.btnEntrar.addActionListener(new ActionListener() {
            {
                Objects.requireNonNull(Login.this);
            }

            public void actionPerformed(ActionEvent e) {
                Login.this.comprobarLogin();
            }
        });
    }

    private void comprobarLogin() {
        String user = this.txtUsuario.getText();
        String pass = new String(this.txtPass.getPassword());
        if (user.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Acceso correcto.");
            MenuVentana menu = new MenuVentana();
            menu.setVisible(true);
            this.dispose();
        } else {
            ++this.intentos;
            if (this.intentos >= 3) {
                JOptionPane.showMessageDialog(this, "Demasiados intentos. Se cierra el programa.");
                System.exit(0);
            } else {
                int quedan = 3 - this.intentos;
                JOptionPane.showMessageDialog(this, "Datos incorrectos. Te quedan " + quedan + " intentos.");
            }
        }

    }
}
