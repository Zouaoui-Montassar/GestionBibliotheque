package swingclass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;
import myclass.*;
import java.awt.*;
import java.util.List;

public class ConsulterLivre {
    public ConsulterLivre(Utilisateur user) {
        JFrame frame = new JFrame("Consulter catalogue livre");
        frame.setLayout(new BorderLayout());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Titre");
        model.addColumn("Auteur");
        model.addColumn("Genre");
        model.addColumn("Disponibilite");

        try {
            List<Livre> livres = Livre.AfficherCatalogue();
            for (Livre livre : livres) {
                model.addRow(new Object[]{livre.getId_Livre(), livre.getTitre(), livre.getAuteur(), livre.getGenre(), livre.getDisponibilite() ? "Disponible" : "Non Disponible"});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);

        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user));
            frame.dispose();
        });

        JButton button_AjouterEmprunt = new JButton("Emprunter/Réserver");
        button_AjouterEmprunt.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int idLivre = (int) table.getValueAt(selectedRow, 0);
                String titre = (String) table.getValueAt(selectedRow,1);
                String auteur = (String) table.getValueAt(selectedRow,2);
                String genre = (String) table.getValueAt(selectedRow,3);
                String dispo =(String) table.getValueAt(selectedRow, 4);
                boolean disponibilite;
                if (dispo=="Disponible"){
                    disponibilite=true;
                }
                else{
                    disponibilite=false;
                }
                Livre livre = new Livre(idLivre, titre, auteur, genre, disponibilite); 
                System.out.println(livre);
                if (livre.getDisponibilite()) {
                    Emprunt emprunt = new Emprunt(true); 
                    System.out.println(emprunt);
                    try {
                        emprunt.AjouterEmprunt(user, livre);
                        JOptionPane.showMessageDialog(frame, "Emprunt ajouté avec succès!");
                        SwingUtilities.invokeLater(() -> new ConsulterLivre(user));
                        frame.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout de l'emprunt: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    Reservation reservation = new Reservation(); 
                    System.out.println(reservation);
                    try {
                        reservation.AjouterReservation(user.getIdUtilisateur(), idLivre);
                        JOptionPane.showMessageDialog(frame, "Réservation ajoutée avec succès!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout de la réservation: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un livre avant d'ajouter un emprunt.");
            }
        });

    JButton button_Details = new JButton("Détails du Livre");
button_Details.addActionListener(e -> {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {

        String titre = (String) table.getValueAt(selectedRow, 1);
        String auteur = (String) table.getValueAt(selectedRow, 2);
        String genre = (String) table.getValueAt(selectedRow, 3);
        String dispo = (String) table.getValueAt(selectedRow, 4);
        String bookDetails = "Titre: " + titre + "\nAuteur: " + auteur + "\nGenre: " + genre + "\nDisponibilité: " + dispo;
        JOptionPane.showMessageDialog(frame, bookDetails, "Détails du Livre", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un livre pour afficher les détails.");
    }
});


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(button_Accueil);
        buttonPanel.add(button_AjouterEmprunt);
        buttonPanel.add(button_Details);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
