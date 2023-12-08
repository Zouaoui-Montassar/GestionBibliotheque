package swingclass;

import java.awt.*;
import javax.swing.*;
import myclass.Utilisateur;
import Exceptions.*;

public class LoginForm {
    public LoginForm(){
        JFrame frame = new JFrame("LoginForm");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);
        JLabel label1 = new JLabel("Login : ");
        frame.add(label1);
        JTextField textField1 = new JTextField(50);
        textField1.setText("zouaoui.montassar@etudiant-fst.utm.tn");
        frame.add(textField1);
        JLabel label2 = new JLabel("Password : ");
        frame.add(label2);
        JPasswordField passwordField1 = new JPasswordField(50);
        passwordField1.setText("montassar123");
        frame.add(passwordField1);
        JButton button = new JButton("Connexion");
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
                        JOptionPane.showMessageDialog(frame, Role);
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
        frame.add(button);
        JButton button2 = new JButton ("Creer un compte");
        button2.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new NewAccountForm());
            frame.dispose();
        });
        frame.add(button2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
