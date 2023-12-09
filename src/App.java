import java.io.File;
import Exceptions.IOException;
import javax.swing.SwingUtilities;

import swingclass.*;

public class App {
    // nfasarlek el principe : lock file ykhalik ma texecuti lapplication ken mara khw 
    private static final String LOCK_FILE_PATH = "app.lock"; //hedhi kima lvariable lih
    public static void main(String[] args) throws java.io.IOException, IOException {
        
        File lockFile = new File(LOCK_FILE_PATH);

        if (lockFile.exists()) {
            System.out.println("Another instance is already running.");
            System.exit(0);
        }

        lockFile.createNewFile(); //creation mtaa el lock

        SwingUtilities.invokeLater(() -> new LoginForm());

        lockFile.deleteOnExit(); // suppression mtaa el lock file bech najmou naawdou nexecutiwha kif tetsakr mara okhra
    }





        
    }
