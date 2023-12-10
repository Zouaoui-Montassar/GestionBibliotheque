package swingclass;
import java.awt.*;
import javax.swing.*;import javax.swing.table.DefaultTableModel;import Exceptions.IOException;
import myclass.EmailSender;import myclass.Emprunt;import myclass.Livre;
import myclass.Utilisateur;import java.util.List;

public class Notif {
    private static int selectedRow = -1;
    public Notif(Utilisateur user) {
        JFrame frame = new JFrame("Notification par email");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(650, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID Emprunt");
        model.addColumn("Id utilisateur");
        model.addColumn("Titre");
        model.addColumn("Auteur");
        model.addColumn("Date Emprunte");
        model.addColumn("Date Retour");
        model.addColumn("Statut Actuel");
        model.addColumn("Jours Restant");
        try {
            List<Emprunt> emprunts = Emprunt.RappelRetour(true);
            for (Emprunt emprunt : emprunts) {
                Utilisateur user1 = Emprunt.AfficherUser(emprunt);
                int n = Emprunt.CalculeJoursRestant(user1, emprunt);
                if (n<=100){
            	Livre livre=Emprunt.AfficherLivre(emprunt);
                model.addRow(new Object[]{emprunt.getId_Emprunt(), 
                    user1.getIdUtilisateur(),
                    livre.getTitre(), 
                    livre.getAuteur(),
                    emprunt.getDate_Emprunt(),
                    emprunt.getDate_Retour(),
                    emprunt.getStatut()?"En cours" :"Terminee",n});
                    
            }}
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
        JButton Button1 = new JButton("Envoyer un mail de rappel");
        Button1.addActionListener(e -> {
                if (selectedRow != -1) {
                    int id_emprunt = (int) table.getValueAt(selectedRow, 0);
                	String titre = (String) table.getValueAt(selectedRow, 2);
                    long n = ((Number) table.getValueAt(selectedRow, 7)).longValue();
                    Emprunt emprunt;
                    try {
                        emprunt = Emprunt.rechercheEmprunt(id_emprunt);
                        Utilisateur user1 = Emprunt.AfficherUser(emprunt);
                        String Subject = "Rappel Retour Livre "+titre ;
                        String Message = "Cher(e) " + user1.getRole() + "\n" 
                        +"Nous espérons que vous allez bien. Ceci est un rappel amical concernant le retour du livre \"" + 
                        titre + "\" emprunté à la Bibliothèque FST.\n"
                        + "Il vous reste " + n + " jours avant la date limite de retour, prévue le " + emprunt.getDate_Retour() + ".\n"
                        + "Merci de bien vouloir retourner le livre à la bibliothèque dans les délais.\n"
                        + "Nous apprécions votre coopération.\n\n"+ "Cordialement,\n"
                        + "L'équipe de la Bibliothèque FST";
                        EmailSender.sendEmail(user1.getLogin(), Subject, Message);
                        JOptionPane.showMessageDialog(frame, "Mail envoyé avec success !");
                    }
                    catch (IOException e1) {
                        System.out.println(e1.getMessage()); //debug
                        JOptionPane.showMessageDialog(frame, "Erreur lors de l'envoi de l'e-mail " );}
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Veuillez selectionner une ligne avant de cliquer sur le bouton.");
                }
        });
        JButton button_Accueil = new JButton("Accueil");
        button_Accueil.addActionListener(e -> {SwingUtilities.invokeLater(() -> new AcceuilBibliothecaire(user));frame.dispose();}); 
        JPanel buttonPanel = new JPanel();buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Button1);buttonPanel.add(button_Accueil);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}