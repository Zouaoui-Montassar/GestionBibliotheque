package swingclass;
import java.awt.*;
import javax.swing.*;
import Exceptions.*;
import myclass.Utilisateur;

public class ModifierCompte {
    public ModifierCompte(Utilisateur user) {
        JFrame frame = new JFrame("ModfierCompteForm");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel label1 = new JLabel("Nom : ");
        gbc.gridx = 0;gbc.gridy = 0;frame.add(label1, gbc);
        JTextField textField1 = new JTextField(50);
        gbc.gridx = 1;frame.add(textField1, gbc);
        JLabel label2 = new JLabel("Prenom : ");
        gbc.gridx = 0;gbc.gridy = 1;frame.add(label2, gbc);
        JTextField textField2 = new JTextField(50);
        gbc.gridx = 1;frame.add(textField2, gbc);
        JLabel label3 = new JLabel("Login : ");
        gbc.gridx = 0;gbc.gridy = 2;frame.add(label3, gbc);
        JTextField textField3 = new JTextField(50);
        gbc.gridx = 1;frame.add(textField3, gbc);
        JLabel label6 = new JLabel("Vous etes : ");
        gbc.gridx = 0;gbc.gridy = 3;frame.add(label6, gbc);
        JRadioButton radioButton1 = new JRadioButton("Etudiant");
        gbc.gridx = 1;frame.add(radioButton1, gbc);
        JRadioButton radioButton2 = new JRadioButton("Enseignant");
        gbc.gridx = 2;frame.add(radioButton2, gbc);
        JLabel label4 = new JLabel("New password : ");
        gbc.gridx = 0;gbc.gridy = 4;frame.add(label4, gbc);
        JPasswordField passwordField1 = new JPasswordField(50);
        gbc.gridx = 1;frame.add(passwordField1, gbc);
        JLabel label5 = new JLabel("Confirm new password : ");
        gbc.gridx = 0;gbc.gridy = 5;frame.add(label5, gbc);
        JPasswordField passwordField2 = new JPasswordField(50);
        gbc.gridx = 1;frame.add(passwordField2, gbc);

        JButton Retour = new JButton("Retour");
        gbc.gridx = 0;gbc.gridy = 6;gbc.gridwidth = 1;
        Retour.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GererCompte(user));
            frame.dispose();
        });
        frame.add(Retour, gbc);

        JButton button = new JButton("Valider modification ");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
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
            if (password.equals(password2) || (passwordField1.getPassword().length==0 && password2.isEmpty())) {
                try {
                    int result = JOptionPane.showConfirmDialog(
                frame,
                "Voulez vous faire ces modifications?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    user.ModifierCompte(nom,prenom,login,password,role);
                    JOptionPane.showMessageDialog(frame, "Modification confirmeé avec success! Reconnecter vous !");
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();}
                else{
                    JOptionPane.showMessageDialog(frame, "Operation annulé.");
                }
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Entrees invalide");
            }
        });
        frame.add(button, gbc);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
