package swingclass;
import java.awt.*;import java.util.List;
import javax.swing.*;import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;import myclass.*;

public class ToutEmprunt {
    public ToutEmprunt(Utilisateur user){
        JFrame frame = new JFrame("La liste de touts les emprunts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);frame.setVisible(true);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID Emprunt"); model.addColumn("Id utilisateur");
        model.addColumn("Titre");model.addColumn("Auteur");
        model.addColumn("Date Emprunt");model.addColumn("Date Retour");
        model.addColumn("Statut Actuel");model.addColumn("Jours Restant");
        try {
            List<Emprunt> emprunts = Emprunt.RappelRetour(true);
            for (Emprunt emprunt : emprunts) {
                Utilisateur user1 = Emprunt.AfficherUser(emprunt);
                long n = Emprunt.CalculeJoursRestant(user1, emprunt);
                String statut=emprunt.getStatut()? "En cours" : "Termineé";
                if (statut.equals("Termineé")){
                    n=0;
                }
            	Livre livre=Emprunt.AfficherLivre(emprunt);
                model.addRow(new Object[]{emprunt.getId_Emprunt(), 
                    user1.getIdUtilisateur(),livre.getTitre(), 
                    livre.getAuteur(),emprunt.getDate_Emprunt(),
                    emprunt.getDate_Retour(),statut,n});
            }
            List<Emprunt> emprunts2 = Emprunt.RappelRetour(false);
            for (Emprunt emprunt : emprunts2) {
                Utilisateur user1 = Emprunt.AfficherUser(emprunt);
                String statut=emprunt.getStatut()? "En cours" : "Termineé";
            	Livre livre=Emprunt.AfficherLivre(emprunt);
                model.addRow(new Object[]{emprunt.getId_Emprunt(), user1.getIdUtilisateur(),
                    livre.getTitre(), livre.getAuteur(),
                    emprunt.getDate_Emprunt(),emprunt.getDate_Retour(),
                    statut,"N/a"});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());}
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();});        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button_Accueil);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
