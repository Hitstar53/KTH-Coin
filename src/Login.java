import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("Username:"));
        JTextField username = new JTextField(10);
        frame.add(username);
        frame.add(new JLabel("Password:"));
        JPasswordField password = new JPasswordField(10);
        frame.add(password);
        JButton login = new JButton("Login");
        frame.add(login);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pass = new String(password.getPassword());
                if (user.equals("admin") && pass.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed!");
                }
            }
        });
        frame.setVisible(true);
    }
}
