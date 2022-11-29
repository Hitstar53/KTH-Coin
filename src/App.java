import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;
public class App {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
    }
}
