import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;
public class SignupForm extends JFrame {
    final private Font mainFont = new Font("comic sans", Font.PLAIN, 18);
    //create a sign up form
    JTextField tfEmail;
    JPasswordField pfPassword;
    JPasswordField pfConfirmPassword;
    public void initialize() {
        /*************** Form Panel ***************/
        JLabel lbSignupForm = new JLabel("KTH Coin Buy/Sell Portal", SwingConstants.CENTER);
        lbSignupForm.setFont(mainFont);
        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);
        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);
        JLabel lbname = new JLabel("Name");
        lbname.setFont(mainFont);
        JTextField tfname = new JTextField();
        tfname.setFont(mainFont);
        JLabel lbphone = new JLabel("Phone");
        lbphone.setFont(mainFont);
        JTextField tfphone = new JTextField();
        tfphone.setFont(mainFont);
        JLabel lbaddress = new JLabel("Address");
        lbaddress.setFont(mainFont);
        JTextField tfaddress = new JTextField();
        tfaddress.setFont(mainFont);
        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);
        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);
        JLabel lbConfirmPassword = new JLabel("Confirm Password");
        lbConfirmPassword.setFont(mainFont);
        pfConfirmPassword = new JPasswordField();
        pfConfirmPassword.setFont(mainFont);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbSignupForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbname);
        formPanel.add(tfname);
        formPanel.add(lbphone);
        formPanel.add(tfphone);
        formPanel.add(lbaddress);
        formPanel.add(tfaddress);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);
        formPanel.add(lbConfirmPassword);
        formPanel.add(pfConfirmPassword);
        /*************** Buttons Panel ***************/
        JButton btnSignup = new JButton("Signup");
        btnSignup.setFont(mainFont);
        btnSignup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String name = tfname.getText();
                String phone = tfphone.getText();
                String address = tfaddress.getText();
                String password = String.valueOf(pfPassword.getPassword());
                String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
                if (password.equals(confirmPassword)) {
                    User user = createUser(email,name,phone,address,password);
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Signup Successful");
                        LoginForm loginForm = new LoginForm();
                        loginForm.initialize();
                        loginForm.setVisible(true);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(SignupForm.this,
                                "Email already exists",
                                "Try again",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(SignupForm.this,
                            "Passwords do not match",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnSignup);
        buttonsPanel.add(btnCancel);
        /*************** Main Panel ***************/
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        /*************** Frame ***************/
        setContentPane(mainPanel);
        setTitle("Signup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private User createUser(String email,String name,String phone,String Address,String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/cryptodb?serverTimezone=UTC";
        final String USERNAME = "sqluser";
        final String PASSWORD = "password";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (name,email,phone,address,password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, Address);
            statement.setString(5, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                user = new User();
            }
            connection.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SignupForm signupForm = new SignupForm();
        signupForm.initialize();
    }
}
