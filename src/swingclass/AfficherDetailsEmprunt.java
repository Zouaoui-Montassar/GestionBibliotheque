package swingclass;

import javax.swing.*;
import Exceptions.IOException;
import myclass.*;
import java.awt.*;

public class AfficherDetailsEmprunt {
    AfficherDetailsEmprunt(int idEmprunt,String titre,String auteur ,String date2 ,String date1,Utilisateur user) {
        JFrame detailsFrame = new JFrame("Détails de l'emprunt");
        
        detailsFrame.setVisible(true);
        detailsFrame.setSize(800, 400);
        String ch = String.format("Emprunt ID: %d | Livre: %s | Auteur : %s | Date Emprunt: %s | Date Retour: %s |",idEmprunt, titre, auteur, date1, date2);
        JTextArea detailsTextArea = new JTextArea(ch);
        detailsFrame.setLayout(new BorderLayout());
        
        detailsFrame.add(detailsTextArea, BorderLayout.CENTER);

        JButton modifierDateRetourButton = new JButton("Modifier date retour de l'Emprunt ");
        modifierDateRetourButton.addActionListener(e -> {
            try{
                Emprunt emprunt = Emprunt.rechercheEmprunt(idEmprunt);
                Livre livre = Emprunt.AfficherLivre(emprunt);
                SwingUtilities.invokeLater(() -> new ModifierDate(user,emprunt,livre,ch));
                detailsFrame.dispose();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(detailsFrame, e1.getMessage());

            } 
        });

        JButton validerRetourLivreButton = new JButton("Valider retour de Livre");
        validerRetourLivreButton.addActionListener(e -> {
            try{
                int confirmResult = JOptionPane.showConfirmDialog(
                detailsFrame,
                "Voulez-vous vraiment retourner ce livre ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                Emprunt emprunt = Emprunt.rechercheEmprunt(idEmprunt);
                Livre livre = Emprunt.AfficherLivre(emprunt);
                emprunt.ValiderRetour(user,livre);
                JOptionPane.showMessageDialog(detailsFrame,"Livre retouné avec success ! ");
                SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));
                detailsFrame.dispose();}
                else{
                    JOptionPane.showMessageDialog(detailsFrame, "Opération annulée ");
                }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(detailsFrame, e1.getMessage());
            } 
        });

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));detailsFrame.dispose();});        
        

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        
        buttonPanel.add(modifierDateRetourButton);
        buttonPanel.add(validerRetourLivreButton);
        buttonPanel.add(retour);
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailsFrame.setVisible(true);
    }
}
