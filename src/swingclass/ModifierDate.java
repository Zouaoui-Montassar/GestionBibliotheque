package swingclass;

import java.awt.*;
import javax.swing.*;
import Exceptions.IOException;
import myclass.*;

public class ModifierDate {
    public ModifierDate(Utilisateur user, Emprunt emprunt, Livre livre, String ch) {
        JFrame frame = new JFrame("Modifier Date");
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        JLabel label1 = new JLabel("Veuillez saisir combien voulez-vous ajouter de jours à votre emprunt : ");
        frame.add(label1);
        JTextField textField1 = new JTextField(50);
        frame.add(textField1);
        JButton button = new JButton("Valider modification ");
        button.addActionListener(e -> {
            String text = textField1.getText();
            try {
                int n = Integer.parseInt(text);
                if (n > 0 && n <= 7) {
                    emprunt.ModifierDateRetour(emprunt, n, livre);
                    JOptionPane.showMessageDialog(null, "Emprunt prolongé avec succès");
                    SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez saisir un nombre entre 1 et 7 jours.");
                }
            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nombre");
            } catch (IOException e3) {
                JOptionPane.showMessageDialog(frame, e3.getMessage());
            }
        });
        frame.add(button);
    
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user));
            frame.dispose();
        });
        frame.add(button_Accueil);
    
        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Make the frame visible
        frame.setVisible(true);
    }
    
}
