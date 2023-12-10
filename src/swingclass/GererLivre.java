package swingclass;
import java.awt.*;
import javax.swing.*;
import myclass.*;

public class GererLivre {
    public GererLivre(Utilisateur user){
        JFrame frame = new JFrame("Gerer Livre");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension buttonSize = new Dimension(300, 30);
        

        GridBagConstraints gbcButton1 = new GridBagConstraints();
        gbcButton1.gridx = 0;
        gbcButton1.gridy = 0;
        gbcButton1.insets = new Insets(10, 10, 10, 10);
        JButton button1 = new JButton("Ajouter Livre");
        button1.setPreferredSize(buttonSize);
        frame.add(button1,gbcButton1);

        GridBagConstraints gbcButton2 = new GridBagConstraints();
        gbcButton2.gridx = 0;
        gbcButton2.gridy = 1;
        gbcButton2.insets = new Insets(10, 10, 10, 10);
        JButton button2 = new JButton("Supprimer Livre");
        button2.setPreferredSize(buttonSize);
        frame.add(button2 , gbcButton2);

        GridBagConstraints gbcButton3 = new GridBagConstraints();
        gbcButton3.gridx = 0;
        gbcButton3.gridy = 2;
        gbcButton3.insets = new Insets(10, 10, 10, 10);
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.setPreferredSize(buttonSize);
        frame.add(button_Accueil , gbcButton3);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        button1.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AjouterLivre(user));frame.dispose();});
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new SupprimerLivre(user));frame.dispose();}); 
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();}); 

    }
}
