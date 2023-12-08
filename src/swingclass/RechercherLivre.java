package swingclass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import Exceptions.IOException;
import myclass.*;

public class RechercherLivre {
    private JFrame frame;
    private JTable table;
    private JTextField searchField;

    public RechercherLivre(Utilisateur user) {
        System.out.println(user+"men interface el recherche livre , w mazelna ma gadina chay ");
        frame = new JFrame("Rechercher Livre");
        frame.setLayout(new BorderLayout());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        // Components for searching a book
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 25));
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> searchLivres());
        JButton button_AjouterEmprunt = new JButton("Ajouter Emprunt");
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
                //System.out.println(livre);
                System.out.println(user);
                if (livre.getDisponibilite()) {
                    Emprunt emprunt = new Emprunt(true); 
                    //System.out.println(emprunt);
                    System.out.println(user);
                    try {
                        emprunt.AjouterEmprunt(user, livre);
                        JOptionPane.showMessageDialog(frame, "Emprunt ajouté avec succès!");
                        SwingUtilities.invokeLater(() -> new RechercherLivre(user));
                        frame.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout de l'emprunt: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    Reservation reservation = new Reservation(); 
                    //System.out.println(reservation);
                    System.out.println(user);
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

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titre");
        model.addColumn("Auteur");
        model.addColumn("Genre");
        model.addColumn("Disponibilite");

        // Create a JTable with the model
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the components and the table to the frame
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Entrez le titre: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a button for returning to the main page
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user)); 
            frame.dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        
        // Add buttons to the button panel
        buttonPanel.add(button_Accueil);
        buttonPanel.add(button_AjouterEmprunt);
        
        // Add the button panel to the frame's SOUTH position
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set default close operation and make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void searchLivres() {
        String titre = searchField.getText();
        try {
            Livre[] livres = Livre.RechercherLivre(titre);

            // Clear existing data in the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            // Populate the table model with the search results
            for (Livre livre : livres) {
                model.addRow(new Object[]{livre.getId_Livre(), livre.getTitre(), livre.getAuteur(), livre.getGenre(), livre.getDisponibilite()? "Disponible" : "Non Disponible"});
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
