package swingclass;
import javax.swing.*;
import java.awt.*;
import myclass.*;

public class GererEmprunt {
    public GererEmprunt (Utilisateur user){
        JFrame frame = new JFrame("Gerer Emprunt");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JButton button = new JButton("Consulter Emprunt en cours pour modifier ou valider retours des empruntes ");
        button.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));frame.dispose();});        
        frame.add(button);

        JButton button2 = new JButton ("Consulter l'historique de tous les empruntes ");
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterHistorique(user));frame.dispose();});
        frame.add(button2);

        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new Acceuil(user));frame.dispose();});        
        frame.add(button_Accueil);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
