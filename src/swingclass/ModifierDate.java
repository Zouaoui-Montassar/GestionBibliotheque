package swingclass;
import java.awt.*;
import javax.swing.*;
import Exceptions.IOException;
import myclass.*;
public class ModifierDate {
    public ModifierDate(Utilisateur user, Emprunt emprunt, Livre livre, String ch) {
        JFrame frame = new JFrame("Modifier Date");
        frame.setLayout(new GridBagLayout());
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel label1 = new JLabel("Veuillez saisir combien voulez-vous ajouter de jours à votre emprunt : ");
        gbc.gridx = 0;gbc.gridy = 0;frame.add(label1, gbc);
        JTextField textField1 = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(textField1, gbc);
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
        gbc.gridx = 0; gbc.gridy = 2;frame.add(button, gbc);
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user));frame.dispose();});
        gbc.gridx = 0;gbc.gridy = 3;frame.add(button_Accueil, gbc);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
