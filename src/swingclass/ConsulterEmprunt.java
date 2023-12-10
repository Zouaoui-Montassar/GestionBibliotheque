package swingclass;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;
import java.awt.*;
import java.text.SimpleDateFormat;
import myclass.*;
import java.util.List;

public class ConsulterEmprunt {
    private static int selectedRow = -1;
    public ConsulterEmprunt(Utilisateur user) {
        JFrame frame = new JFrame("Consulter Emprunt En Cours");
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
        model.addColumn("date Retour");
        model.addColumn("Statut Actuel");
        try {
            List<Emprunt> emprunts = Emprunt.AfficherEmpruntEnCours(user);
            for (Emprunt emprunt : emprunts) {
            	Livre livre=Emprunt.AfficherLivre(emprunt);
                model.addRow(new Object[]{emprunt.getId_Emprunt(),livre.getTitre(), livre.getAuteur(),emprunt.getDate_Emprunt(),emprunt.getDate_Retour(),emprunt.getStatut() ? "En cours" : "Terminee"});
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
        JButton Button1 = new JButton("Afficher details de cette emprunt");
        Button1.addActionListener(e -> {
                if (selectedRow != -1) {
                    int id_emprunt = (int) table.getValueAt(selectedRow, 0);
                	String titre = (String) table.getValueAt(selectedRow, 1);
                    String auteur = (String) table.getValueAt(selectedRow, 2);
                    java.sql.Date sqlDate = (java.sql.Date) table.getValueAt(selectedRow, 4);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date_retour = dateFormat.format(sqlDate);
                    java.sql.Date sqlDate2 = (java.sql.Date) table.getValueAt(selectedRow, 3);
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    String date_emprunt = dateFormat2.format(sqlDate2);

                    SwingUtilities.invokeLater(() -> new AfficherDetailsEmprunt(id_emprunt,titre,auteur,date_retour,date_emprunt,user)); 
                    frame.dispose();              
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
                }
        });
        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> {SwingUtilities.invokeLater(() -> new GererEmprunt(user));frame.dispose();});        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        
        
        buttonPanel.add(retour);
        buttonPanel.add(Button1);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        


    }
}
