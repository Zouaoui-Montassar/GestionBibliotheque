package swingclass;

import java.awt.*;
import javax.swing.*;
import myclass.*;

public class Acceuil {
    public Acceuil(Utilisateur user) {
        JFrame frame = new JFrame("Accueil");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        
        Dimension buttonSize = new Dimension(200, 30);

        
        JButton button = new JButton("Consulter Catalogue livre");
        button.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(button, gbc);

        
        JButton button5 = new JButton("Rechercher un livre");
        button5.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(button5, gbc);

        
        JButton button2 = new JButton("Gerer Compte");
        button2.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(button2, gbc);

        
        JButton button3 = new JButton("Gerer Emprunt");
        button3.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(button3, gbc);

        
        JButton button6 = new JButton("Gerer Reservation");
        button6.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(button6, gbc);

        
        JButton button4 = new JButton("Deconnexion");
        button4.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(button4, gbc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        // events 
        button.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterLivre(user));frame.dispose();});  
        button5.addActionListener(e -> {SwingUtilities.invokeLater(() -> new RechercherLivre(user));frame.dispose();});
        button2.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererCompte(user));frame.dispose();}); 
        button3.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererEmprunt(user));frame.dispose();}); 
        button6.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererReservation(user));frame.dispose();});   
        button4.addActionListener(e -> {SwingUtilities.invokeLater(() -> new LoginForm());frame.dispose();});
    }
}

