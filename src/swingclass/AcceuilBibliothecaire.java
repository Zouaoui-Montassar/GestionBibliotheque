package swingclass;

import java.awt.*;
import javax.swing.*;
import myclass.*;

public class AcceuilBibliothecaire {
    public AcceuilBibliothecaire(Utilisateur user){
        JFrame frame = new JFrame("AcceuilBibliotheque");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);
        JButton button5 = new JButton("Liste des emprunts");
        button5.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ToutEmprunt(user));frame.dispose();});        
        frame.add(button5);
        JButton button = new JButton("Rapport a propos notre bibliotheque");
        button.addActionListener(e -> {SwingUtilities.invokeLater(() -> new Rapport(user));frame.dispose();});        
        frame.add(button);
        JButton button2 = new JButton("Notification par email");
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new Notif(user));frame.dispose();});        
        frame.add(button2);
        JButton button3 = new JButton("Gerer livre");
        button3.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererLivre(user));frame.dispose();});        
        frame.add(button3);
        JButton button4 = new JButton ("Deconnexion");
        button4.addActionListener(e -> {SwingUtilities.invokeLater(() -> new LoginForm());frame.dispose();});
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
