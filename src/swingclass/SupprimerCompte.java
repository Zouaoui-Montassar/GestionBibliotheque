package swingclass;
import java.awt.*;
import javax.swing.*;
import Exceptions.*;
import myclass.Utilisateur;

public class SupprimerCompte {
    public SupprimerCompte(Utilisateur user) {
        JFrame frame = new JFrame("SupprimerCompteForm");
        frame.setLayout(new GridBagLayout());frame.setSize(1024, 768);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JPasswordField passwordField1 = new JPasswordField(20);
        gbc.gridx = 1;frame.add(passwordField1, gbc);
        JLabel passwordPlaceholderLabel = new JLabel("Enter your password to confirm deletion:");
        gbc.gridx = 0;gbc.gridy = 0;frame.add(passwordPlaceholderLabel, gbc);
        JButton button = new JButton("Valider la suppression ");
        gbc.gridx = 0;gbc.gridy = 1;gbc.gridwidth = 3;
        button.addActionListener(e -> {
            String password = new String(passwordField1.getPassword());
            if (password.equals(user.getPwd()) && (!password.isEmpty())) {
                try {
                    user.SupprimerCompte();
                    JOptionPane.showMessageDialog(frame, "Compte supprimé avec succès!");
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Entrez votre mot de passe pour la suppresion.");
            }
        });
        frame.add(button, gbc);
        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GererCompte(user));
            frame.dispose();
        });
        gbc.gridx = 0;gbc.gridy = 2;gbc.gridwidth = 3;
        frame.add(retourButton, gbc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);frame.setVisible(true);
    }
}
