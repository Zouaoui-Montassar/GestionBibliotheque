package swingclass;

import javax.swing.*;
import myclass.Utilisateur;
import Exceptions.IOException;
import java.awt.*;

public class NewAccountForm {
    public NewAccountForm() {
        JFrame frame = new JFrame("NewAccountForm");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel titleLabel = new JLabel("Creation d'un nouveau compte");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;gbc.gridy = 0;gbc.gridwidth = 2; 
        frame.add(titleLabel, gbc);
        JLabel label1 = new JLabel("Nom : ");
        JLabel label2 = new JLabel("Prenom : ");
        JLabel label3 = new JLabel("Login : ");
        JLabel label4 = new JLabel("Password : ");
        JLabel label5 = new JLabel("Confirm Password : ");
        JLabel label6 = new JLabel("Vous etes : ");
        JTextField textField1 = new JTextField(20);
        JTextField textField2 = new JTextField(20);
        JTextField textField3 = new JTextField(20);
        JRadioButton radioButton1 = new JRadioButton("Etudiant");
        JRadioButton radioButton2 = new JRadioButton("Enseignant");
        JPasswordField passwordField1 = new JPasswordField(20);
        JPasswordField passwordField2 = new JPasswordField(20);
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        gbc.gridx = 0;gbc.gridy = 1;gbc.gridwidth = 1;frame.add(label1, gbc);
        gbc.gridx = 1;gbc.gridy = 1;frame.add(textField1, gbc);
        gbc.gridx = 0;gbc.gridy = 2;frame.add(label2, gbc);
        gbc.gridx = 1;gbc.gridy = 2;frame.add(textField2, gbc);
        gbc.gridx = 0;gbc.gridy = 3;frame.add(label3, gbc);
        gbc.gridx = 1;gbc.gridy = 3;frame.add(textField3, gbc);
        gbc.gridx = 0; gbc.gridy = 4;frame.add(label6, gbc);
        gbc.gridx = 1;gbc.gridy = 4;frame.add(radioButton1, gbc);
        gbc.gridx = 1;gbc.gridy = 5;frame.add(radioButton2, gbc);
        gbc.gridx = 0;gbc.gridy = 6;frame.add(label4, gbc);
        gbc.gridx = 1;gbc.gridy = 6;frame.add(passwordField1, gbc);
        gbc.gridx = 0;gbc.gridy = 7; frame.add(label5, gbc);
        gbc.gridx = 1;gbc.gridy = 7;frame.add(passwordField2, gbc);
        JButton button = new JButton("Creer Compte");
        button.addActionListener(e -> createAccount(textField1, textField2, textField3, passwordField1, passwordField2, radioButton1, radioButton2, frame));
        gbc.gridx = 1;gbc.gridy = 8;frame.add(button, gbc);
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LoginForm());
            frame.dispose();
        });
        gbc.gridx = 0;gbc.gridy = 8;frame.add(backButton, gbc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createAccount(JTextField textField1, 
    JTextField textField2, 
    JTextField textField3, 
    JPasswordField passwordField1, 
    JPasswordField passwordField2, 
    JRadioButton radioButton1, 
    JRadioButton radioButton2, 
    JFrame frame ) 
    {
        String nom = textField1.getText();
        String prenom = textField2.getText();
        String login = textField3.getText();
        String password = new String(passwordField1.getPassword());
        String password2 = new String(passwordField2.getPassword());
        String role = "";
        if (radioButton1.isSelected()) {
            role = radioButton1.getText();
        } else if (radioButton2.isSelected()) {
            role = radioButton2.getText();
        }
        if (!password.isEmpty() && !password2.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !login.isEmpty() && !role.isEmpty()) {
            if (password.equals(password2)) {
                try {
                    Utilisateur user = new Utilisateur(3, nom, prenom, login, password, role);
                    user.CreerCompte();
                    JOptionPane.showMessageDialog(frame, "Compte creé avec success ! Connecter vous !.");
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Les mots de passe ne correspondent pas. Veuillez réessayer.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Il y a un champ vide.");
        }
    }
}
