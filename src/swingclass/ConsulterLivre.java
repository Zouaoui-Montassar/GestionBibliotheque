package swingclass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;
import myclass.*;
import java.awt.*;
import java.util.List;

public class ConsulterLivre {
    public ConsulterLivre(Utilisateur user) {
        JFrame frame = new JFrame("Consulter Livre");
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
            // ... (your existing ActionListener code)
        });

        // Use FlowLayout for the button panel to place buttons next to each other
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(button_Accueil);
        buttonPanel.add(button_AjouterEmprunt);

        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set default close operation and make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
