package swingclass;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import myclass.Emprunt;
import myclass.Utilisateur;

public class Rapport {
    public Rapport(Utilisateur user) {
        JFrame frame = new JFrame("Rapport statistiques");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        DefaultTableModel bookModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        DefaultTableModel userModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        JTable bookTable = new JTable(bookModel);JTable userTable = new JTable(userModel);
        bookTable.setShowGrid(false);userTable.setShowGrid(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        bookTable.setDefaultRenderer(Object.class, centerRenderer);
        userTable.setDefaultRenderer(Object.class, centerRenderer);
        JScrollPane bookScrollPane = new JScrollPane(bookTable);JScrollPane userScrollPane = new JScrollPane(userTable);
        frame.getContentPane().add(bookScrollPane, BorderLayout.NORTH);
        frame.getContentPane().add(userScrollPane, BorderLayout.CENTER);
        bookModel.addColumn("Titre");bookModel.addColumn("Auteur");
        bookModel.addColumn("Genre"); bookModel.addColumn("Nombre d'emprunts");
        userModel.addColumn("Nom");userModel.addColumn("Prénom");
        userModel.addColumn("Role");userModel.addColumn("Nombre de livre emprunté");
        List<String> rapports = Emprunt.GenererRapport();boolean isBookSection = false;
        for (String rapport : rapports) {
            if (rapport.startsWith("Statistiques des livres")) {
                isBookSection = true;continue; } 
            else if (rapport.startsWith("Statistiques des utilisateurs")) {
                isBookSection = false;continue;}
            String[] data = rapport.split(" \\| ");
            if (isBookSection) {
                bookModel.addRow(data);} 
            else {userModel.addRow(data); } }
        JButton buttonAccueil = new JButton("Accueil");
        buttonAccueil.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(buttonAccueil);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
