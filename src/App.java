import java.io.File;
import Exceptions.IOException;
import javax.swing.SwingUtilities;
import swingclass.*;
public class App {
    private static final String LOCK_FILE_PATH = "app.lock"; 
    public static void main(String[] args) throws java.io.IOException, IOException {
        File lockFile = new File(LOCK_FILE_PATH);
        if (lockFile.exists()) {
            System.out.println("Another instance is already running.");
            System.exit(0);
        }
        lockFile.createNewFile(); 
        SwingUtilities.invokeLater(() -> new LoginForm());
        lockFile.deleteOnExit(); 
    }
    }
