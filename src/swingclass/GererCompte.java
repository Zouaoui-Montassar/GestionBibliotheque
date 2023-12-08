package swingclass;

import javax.swing.*;
import myclass.Utilisateur;
import java.awt.*;


public class GererCompte {
    public GererCompte(Utilisateur user){
        JFrame frame = new JFrame("GererCompteForm");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JButton button = new JButton("Modifier Compte");
        button.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ModifierCompte(user));frame.dispose();});        
        frame.add(button);

        JButton button2 = new JButton ("Supprimer un compte");
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new SupprimerCompte(user));frame.dispose();});
        frame.add(button2);
        
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new Acceuil(user));frame.dispose();});        
        frame.add(button_Accueil);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
