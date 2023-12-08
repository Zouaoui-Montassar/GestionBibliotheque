package swingclass;

import javax.swing.*;
import Exceptions.IOException;
import myclass.*;
import java.awt.*;

public class AfficherDetailsEmprunt {
    AfficherDetailsEmprunt(int idEmprunt,String titre,String auteur ,String date2 ,String date1,Utilisateur user) {
        JFrame detailsFrame = new JFrame("Détails de l'emprunt");
        String ch = String.format("Emprunt ID: %d | Livre: %s | Auteur : %s | Date Emprunt: %s | Date Retour: %s |",idEmprunt, titre, auteur, date1, date2);
        JTextArea detailsTextArea = new JTextArea(ch);
        detailsFrame.setLayout(new BorderLayout());
        detailsFrame.add(detailsTextArea, BorderLayout.CENTER);
        detailsFrame.setSize(400, 200);

        /*JButton annulerEmpruntButton = new JButton("Annuler Emprunt ");
        annulerEmpruntButton.addActionListener(e -> {
            try{
                Emprunt emprunt = Emprunt.rechercheEmprunt(idEmprunt);
                Livre livre = Emprunt.AfficherLivre(emprunt);
                emprunt.AnnulerEmprunt(emprunt,livre);
                SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));
                detailsFrame.dispose();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(detailsFrame, e1.getMessage());

                }
        });*/

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
                Emprunt emprunt = Emprunt.rechercheEmprunt(idEmprunt);
                Livre livre = Emprunt.AfficherLivre(emprunt);
                System.out.println("depuis l'interface avant valider le retour "+emprunt);
                System.out.println("depuis l'interface avant valider le retour "+livre);
                emprunt.ValiderRetour(user,livre);
                JOptionPane.showMessageDialog(detailsFrame,"Livre retouné avec success ! ");
                SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));
                detailsFrame.dispose();
                System.out.println("depuis l'interface apres valider le retour"+emprunt);
                System.out.println("depuis l'interface apres valider le retour"+livre);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(detailsFrame, e1.getMessage());
            } 
        });

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> {SwingUtilities.invokeLater(() -> new ConsulterEmprunt(user));detailsFrame.dispose();});        
        

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(retour);
        //buttonPanel.add(annulerEmpruntButton);
        buttonPanel.add(modifierDateRetourButton);
        buttonPanel.add(validerRetourLivreButton);
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
        detailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
