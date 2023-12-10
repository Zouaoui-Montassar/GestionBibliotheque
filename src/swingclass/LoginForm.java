package swingclass;
import java.awt.*;
import javax.swing.*;
import myclass.Utilisateur;
import Exceptions.*;

public class LoginForm {
    public LoginForm(){
        JFrame frame = new JFrame("Gestion Bibliotheque");
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 10);
        JLabel titleLabel = new JLabel("Gestion Bibliotheque en ligne");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 40f));
        JLabel label1 = new JLabel("Login");
        JTextField textField1 = new JTextField(40);
        JLabel label2 = new JLabel("Password");
        JPasswordField passwordField1 = new JPasswordField(40);
        JButton resetButton = new JButton("Réinitialiser");
        JButton button = new JButton("Connexion");
        JButton button2 = new JButton("Créer un compte");
        JLabel subscribeLabel = new JLabel("Besoin d'un compte ?");
        button.addActionListener(e -> {
            String login = textField1.getText(); 
            String password = new String(passwordField1.getPassword()); 
            if(login.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Entrees invalide");
            }
            else{
            try {
                Utilisateur authenticated = Utilisateur.authentifier(login, password);
    
                if (authenticated != null) {
                    String Role = authenticated.getRole();
                    if (Role.equals("Bibliothecaire")){
                        JOptionPane.showMessageDialog(frame, Role);
                        SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(authenticated));
                        frame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Bienvenue "+authenticated.getNom()+"! ");
                        SwingUtilities.invokeLater(() -> new Acceuil(authenticated));
                        frame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Authentification échouée. Veuillez réessayer.");
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }}
        });
        button2.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new NewAccountForm());
            frame.dispose();
        });
        resetButton.addActionListener(e -> {
                textField1.setText("");
                passwordField1.setText("");
        });
        gbc.gridx = 0;gbc.gridy = 0;gbc.gridwidth = 2; frame.add(titleLabel, gbc);
        gbc.gridy = 1;gbc.gridwidth = 1;frame.add(label1, gbc);
        gbc.gridx = 1;frame.add(textField1, gbc);
        gbc.gridx = 0;gbc.gridy = 2;frame.add(label2, gbc);
        gbc.gridx = 1;frame.add(passwordField1, gbc);
        gbc.gridy = 3; gbc.gridx = 0;frame.add(resetButton, gbc);
        gbc.gridx = 1;frame.add(button, gbc);
        gbc.gridy = 4; gbc.gridx = 0;
        frame.add(subscribeLabel, gbc);
        gbc.gridx = 1;
        frame.add(button2, gbc);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
