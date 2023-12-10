package swingclass;
import java.awt.BorderLayout;import java.awt.FlowLayout;
import java.util.List;import javax.swing.Box;
import javax.swing.BoxLayout;import javax.swing.JButton;
import javax.swing.JFrame;import javax.swing.JOptionPane;
import javax.swing.JPanel;import javax.swing.JScrollPane;
import javax.swing.JTable;import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;import javax.swing.table.DefaultTableModel;
import Exceptions.IOException;import myclass.*;

public class GererReservation {
    private static int selectedRow = -1;
    public GererReservation(Utilisateur user){
        JFrame frame = new JFrame("Consulter Reservation En attente");
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
        model.addColumn("ID");model.addColumn("Titre");
        model.addColumn("Auteur");model.addColumn("Date reservation");
        model.addColumn("Statut Actuel");
        try {
            List<Reservation> reservations = Reservation.AfficherReservation(user);
            for (Reservation reservation : reservations) {
            	Livre livre=Reservation.AfficherLivre(reservation);
                model.addRow(new Object[]{reservation.getId_Reservation(),livre.getTitre(), livre.getAuteur(),reservation.getDate_Reservation(),reservation.getStatut()});
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
        JButton Button1 = new JButton("Annuler la reservation");
        Button1.addActionListener(e -> {
                if (selectedRow != -1) {
                	try {
                        int Id_Reservation = (int) table.getValueAt(selectedRow, 0);
                        Reservation reservation= Reservation.ChercherReservation(Id_Reservation);
                        int confirmResult = JOptionPane.showConfirmDialog(frame,
                        "Voulez-vous vraiment annuler cette reservation?",
                "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION){
                    reservation.AnnulerReservation();
                    JOptionPane.showMessageDialog(frame, "Reservation annuleé avec succès!");
                    SwingUtilities.invokeLater(() -> new GererReservation(user)); 
                    frame.dispose();}
                else{
                JOptionPane.showMessageDialog(frame, "Opération annulée ");
                    }
                } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Erreur.");
                }           
                }
                else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne avant de cliquer sur le bouton.");
                }
        });
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new Acceuil(user));frame.dispose();});        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button_Accueil);buttonPanel.add(Button1);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
