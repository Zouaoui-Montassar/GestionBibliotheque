package swingclass;

import java.awt.*;
import javax.swing.*;
import Exceptions.*;
import myclass.Utilisateur;

public class SupprimerCompte {
    public SupprimerCompte(Utilisateur user){
        JFrame frame = new JFrame("ModfierCompteForm");
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setVisible(true);

        JLabel label1 = new JLabel("Password : ");
        JPasswordField passwordField1 = new JPasswordField(50);

        frame.add(label1);
        frame.add(passwordField1);


        JButton button = new JButton("Valider la suppression ");
        button.addActionListener(e -> {
            String password = new String(passwordField1.getPassword()); 
            if (password.equals(user.getPwd()) && (!password.isEmpty())) {
                try {
                    user.SupprimerCompte();
                    SwingUtilities.invokeLater(() -> new LoginForm());
                    frame.dispose();
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Les mots de passe ne correspondent pas. Veuillez réessayer.");
                SwingUtilities.invokeLater(() -> new NewAccountForm());
                frame.dispose();
            }
        });
        JButton Retour = new JButton("Retour");
        Retour.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererCompte(user));frame.dispose();});
        frame.add(Retour);
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
