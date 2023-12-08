package swingclass;

import java.awt.*;

import javax.swing.*;
import myclass.*;

public class GererLivre {
    public GererLivre(Utilisateur user){
        JFrame frame = new JFrame("Gerer Livre");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);
        
        JButton button1 = new JButton("Ajouter Livre");
        button1.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AjouterLivre(user));frame.dispose();});        
        frame.add(button1);

        JButton button2 = new JButton("Supprimer Livre");
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new SupprimerLivre(user));frame.dispose();});        
        frame.add(button2);

        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();});        
        frame.add(button_Accueil);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
