package swingclass;import javax.swing.*;
import javax.swing.table.DefaultTableModel;import java.awt.*;
import Exceptions.IOException;import myclass.*;

public class RechercherLivre {
    private JFrame frame;private JTable table;private JTextField searchField;
    public RechercherLivre(Utilisateur user) {
        frame = new JFrame("Rechercher Livre");
        frame.setLayout(new BorderLayout());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
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
                
                System.out.println(user);
                if (livre.getDisponibilite()) {
                    Emprunt emprunt = new Emprunt(true); 
                    
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
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");model.addColumn("Titre");
        model.addColumn("Auteur");model.addColumn("Genre");
        model.addColumn("Disponibilite");
        table = new JTable(model);JScrollPane scrollPane = new JScrollPane(table);
        JPanel searchPanel = new JPanel();searchPanel.add(new JLabel("Entrez le titre: "));
        searchPanel.add(searchField);searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);frame.add(scrollPane, BorderLayout.CENTER);
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Acceuil(user)); 
            frame.dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        
        buttonPanel.add(button_Accueil);buttonPanel.add(button_AjouterEmprunt);buttonPanel.add(button_Details);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void searchLivres() {
        String titre = searchField.getText();
        try {
            Livre[] livres = Livre.RechercherLivre(titre);
            // effacage du contenu existant
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Livre livre : livres) {
                model.addRow(new Object[]{livre.getId_Livre(), 
                    livre.getTitre(), livre.getAuteur(), livre.getGenre(), 
                    livre.getDisponibilite()? "Disponible" : "Non Disponible"});
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
