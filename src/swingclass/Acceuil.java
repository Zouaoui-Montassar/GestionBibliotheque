package swingclass;

import java.awt.*;
import javax.swing.*;
import myclass.*;

public class Acceuil {
    public Acceuil(Utilisateur user) {
        JFrame frame = new JFrame("Accueil");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JButton button = new JButton("Consulter Catalogue livre");
        button.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterLivre(user));frame.dispose();});        
        frame.add(button);

        JButton button5 = new JButton ("Rechercher un livre");
        button5.addActionListener(e -> {SwingUtilities.invokeLater(() -> new RechercherLivre(user));frame.dispose();});
        frame.add(button5);


        JButton button2 = new JButton("Gerer Compte");
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererCompte(user));frame.dispose();});        
        frame.add(button2);

        JButton button3 = new JButton("Gerer Emprunt");
        button3.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererEmprunt(user));frame.dispose();});        
        frame.add(button3);

        JButton button6= new JButton("Gerer Reservation");
        button6.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererReservation(user));frame.dispose();});        
        frame.add(button6);
        
        JButton button4 = new JButton ("Deconnexion");
        button4.addActionListener(e -> {SwingUtilities.invokeLater(() -> new LoginForm());frame.dispose();});
        frame.add(button4);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

