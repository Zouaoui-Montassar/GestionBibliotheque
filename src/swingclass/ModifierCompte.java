package swingclass;

import java.awt.*;
import javax.swing.*;
import Exceptions.*;
import myclass.Utilisateur;

public class ModifierCompte {
    public ModifierCompte(Utilisateur user){
        JFrame frame = new JFrame("ModfierCompteForm");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JLabel label1 = new JLabel("Nom : ");
        JLabel label2 = new JLabel("Prenom : ");
        JLabel label3 = new JLabel("Login : ");
        JLabel label4 = new JLabel("New password : ");
        JLabel label5 = new JLabel("Confirm new password : ");
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
        JButton Retour = new JButton("Retour");
        Retour.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererCompte(user));frame.dispose();});
        frame.add(Retour);
        JButton button = new JButton("Valider modification ");
        button.addActionListener(e -> {
            String nom = (!textField1.getText().isEmpty()) ? textField1.getText() :  user.getNom();
            String prenom = (!textField2.getText().isEmpty()) ? textField2.getText() :  user.getPrenom();
            String login = (!textField3.getText().isEmpty()) ? textField3.getText() :  user.getLogin();
            String password = (passwordField1.getPassword().length!=0) ? new String(passwordField1.getPassword()) :  user.getPwd(); 
            String password2 = new String(passwordField1.getPassword()); 
            String role = "";
            if (radioButton1.isSelected()) {
                role = radioButton1.getText();
            } else if (radioButton2.isSelected()) {
                role = radioButton2.getText();
            }else {
                role = user.getRole();
            }
            System.out.println(nom + " " + prenom + " " + login + " " +password + " " +password2+" "+role);
            if (password.equals(password2) || (passwordField1.getPassword().length==0 && password2.isEmpty())) {

                try {
                    user.ModifierCompte(nom,prenom,login,password,role);
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Entrees invalide");
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);

    }
}
