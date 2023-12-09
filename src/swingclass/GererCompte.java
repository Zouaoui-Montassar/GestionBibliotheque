package swingclass;

import javax.swing.*;
import myclass.Utilisateur;
import java.awt.*;

public class GererCompte {
    public GererCompte(Utilisateur user){
        JFrame frame = new JFrame("Gestion du compte");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension buttonSize = new Dimension(200, 30);

        GridBagConstraints gbcButton1 = new GridBagConstraints();
        gbcButton1.gridx = 0;
        gbcButton1.gridy = 0;
        gbcButton1.insets = new Insets(10, 10, 10, 10);

        JButton button = new JButton("Modifier Compte");
        button.setPreferredSize(buttonSize);
        button.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new ModifierCompte(user));
            frame.dispose();
        });
        frame.add(button, gbcButton1);

        GridBagConstraints gbcButton2 = new GridBagConstraints();
        gbcButton2.gridx = 0;
        gbcButton2.gridy = 1;
        gbcButton2.insets = new Insets(10, 10, 10, 10);

        JButton button2 = new JButton("Supprimer un compte");
        button2.setPreferredSize(buttonSize);
        button2.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new SupprimerCompte(user));
            frame.dispose();
        });
        frame.add(button2, gbcButton2);

        GridBagConstraints gbcButton3 = new GridBagConstraints();
        gbcButton3.gridx = 0;
        gbcButton3.gridy = 2;
        gbcButton3.insets = new Insets(10, 10, 10, 10);

        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.setPreferredSize(buttonSize);
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user));
            frame.dispose();
        });
        frame.add(button_Accueil, gbcButton3);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
