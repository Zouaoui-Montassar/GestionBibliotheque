import javax.swing.SwingUtilities;
import swingclass.*;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}
