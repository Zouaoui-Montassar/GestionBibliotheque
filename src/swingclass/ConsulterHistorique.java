package swingclass;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Exceptions.IOException;
import java.awt.*;
import myclass.*;
import java.util.List;

public class ConsulterHistorique  {
    public ConsulterHistorique(Utilisateur user) {
        JFrame frame = new JFrame("Consulter Historique Emprunt");
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
        model.addColumn("ID");
        model.addColumn("Titre");
        model.addColumn("Auteur");
        model.addColumn("Date Emprunte");
        model.addColumn("Date Retour");
        model.addColumn("Statut Actuel");
        try {
            List<Emprunt> emprunts = Emprunt.AfficherHistoriqueEmprunt(user);
            for (Emprunt emprunt : emprunts) {
            	Livre livre=Emprunt.AfficherLivre(emprunt);
                model.addRow(new Object[]{  emprunt.getId_Emprunt(),
                                            livre.getTitre(), 
                                            livre.getAuteur(),
                                            emprunt.getDate_Emprunt(),
                                            emprunt.getDate_Retour(), 
                                            emprunt.getStatut() == true ? "En cours" : "Termineé"});}
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
        JButton button_Accueil = new JButton("Retour");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererEmprunt(user));frame.dispose();});    
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(model.getColumnCount() - 1, (o1, o2) -> {
            String status1 = (String) o1;
            String status2 = (String) o2;
            return status1.compareTo(status2);
        });

        JTable table = new JTable(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button_Accueil);
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
    }
}
