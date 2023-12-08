package swingclass;

import java.awt.*;
import javax.swing.*;
import myclass.Utilisateur;

import Exceptions.*;

public class NewAccountForm {
    public NewAccountForm(){
        JFrame frame = new JFrame("LoginForm");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JLabel label1 = new JLabel("Nom : ");
        JLabel label2 = new JLabel("Prenom : ");
        JLabel label3 = new JLabel("Login : ");
        JLabel label4 = new JLabel("Password : ");
        JLabel label5 = new JLabel("Confirm Password : ");
        JLabel label6 = new JLabel("Vous etes : ");
        JTextField textField1 = new JTextField(50);
        JTextField textField2 = new JTextField(50);
        JTextField textField3 = new JTextField(50);
        JRadioButton radioButton1 = new JRadioButton("Etudiant");
        JRadioButton radioButton2 = new JRadioButton("Enseignant");
        JPasswordField passwordField1 = new JPasswordField(50);
        JPasswordField passwordField2 = new JPasswordField(50);
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        frame.add(label1);
        frame.add(textField1);
        frame.add(label2);
        frame.add(textField2);
        frame.add(label3);
        frame.add(textField3);
        frame.add(label6);
        frame.add(radioButton1);
        frame.add(radioButton2);
        frame.add(label4);
        frame.add(passwordField1);
        frame.add(label5);
        frame.add(passwordField2);
        
        JButton button = new JButton("Creer Compte ");
        button.addActionListener(e -> {
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
            if (!password.isEmpty() && !password2.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !login.isEmpty() && !role.isEmpty()){
                if (password.equals(password2)) {
                    try {
                        Utilisateur user = new Utilisateur(3 ,nom ,prenom ,login ,password ,role);
                        user.CreerCompte();
                        SwingUtilities.invokeLater(() -> new LoginForm());
                        frame.dispose();
                    }catch(IOException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Les mots de passe ne correspondent pas. Veuillez réessayer.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "il y a un champs vide ");
            }
        });
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
