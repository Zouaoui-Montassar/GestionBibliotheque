package swingclass;
import javax.swing.*;import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;import java.awt.*;
import myclass.*;import java.util.List;

public class SupprimerLivre extends JFrame {
    private static int selectedRow = -1;
    public SupprimerLivre(Utilisateur user) {
        JFrame frame = new JFrame("Supprimer Livre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");model.addColumn("Titre");model.addColumn("Auteur");
        model.addColumn("Genre");model.addColumn("Disponibilité");
        try {
            List<Livre> livres = Livre.AfficherCatalogue();
            for (Livre livre : livres) {
                model.addRow(new Object[]{livre.getId_Livre(), 
                    livre.getTitre(), 
                    livre.getAuteur(), 
                    livre.getGenre(), 
                    livre.getDisponibilite()?"Disponible ":"Non Disponible"});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow();
            }
        });
        JButton supprimerButton = new JButton("Supprimer Livre");
        supprimerButton.addActionListener(e -> {
            if (selectedRow != -1) {
                String titre = (String) table.getValueAt(selectedRow, 1);
                int confirmDialogResult = JOptionPane.showConfirmDialog(frame, 
                "Voulez-vous vraiment supprimer le livre : " 
                + titre + "?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION);
                
                if (confirmDialogResult == JOptionPane.YES_OPTION) {
                    int id_livre = (int) table.getValueAt(selectedRow, 0);
                    try {
                        Livre livreToDelete = new Livre(id_livre, "", "", "", false);
                        livreToDelete.SupprimerLivre(id_livre);
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Livre supprimé : " + titre);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
            }
        });
        JButton retourButton = new JButton("Retour à Gerer Livre");
        retourButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GererLivre(user));frame.dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(supprimerButton);buttonPanel.add(retourButton);
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();
        });
        frame.add(button_Accueil);frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
