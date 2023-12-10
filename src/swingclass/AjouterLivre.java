package swingclass;
import javax.swing.*;
import Exceptions.IOException;
import java.awt.*;
import myclass.*;
public class AjouterLivre extends JFrame {

    private JTextField titreField;
    private JTextField auteurField;
    private JTextField genreField;
    private JRadioButton disponibleRadioButton;
    private JRadioButton nonDisponibleRadioButton;
    private ButtonGroup disponibiliteGroup;

    public AjouterLivre(Utilisateur user) {

        JFrame frame = new JFrame("Ajouter Livre");
        frame.setLayout(new GridBagLayout());
        frame.setSize(1024, 768); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titreLabel = new JLabel("Titre:");
        titreField = new JTextField(40);
        

        JLabel auteurLabel = new JLabel("Auteur:");
        auteurField = new JTextField(40);

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(40);

        JLabel disponibiliteLabel = new JLabel("Disponibilité:");

        disponibleRadioButton = new JRadioButton("Disponible");
        nonDisponibleRadioButton = new JRadioButton("Non Disponible");
        disponibleRadioButton.setSelected(true);
        disponibiliteGroup = new ButtonGroup();
        disponibiliteGroup.add(disponibleRadioButton);
        disponibiliteGroup.add(nonDisponibleRadioButton);

        JButton addButton = new JButton("Ajouter Livre");
        addButton.addActionListener(e -> ajouterLivre());

        JButton backButton = new JButton("Retour à Gerer Livre");
        backButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GererLivre(user));
            frame.dispose();
        });

        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(titreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        frame.add(titreField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(auteurLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        frame.add(auteurField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(genreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        frame.add(genreField, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(disponibiliteLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        frame.add(disponibleRadioButton, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        frame.add(nonDisponibleRadioButton, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        frame.add(addButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        frame.add(backButton, gbc);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void ajouterLivre() {

        String titre = titreField.getText();
        String auteur = auteurField.getText();
        String genre = genreField.getText();


        if (titre.isEmpty() || auteur.isEmpty() || genre.isEmpty() || (!disponibleRadioButton.isSelected() && !nonDisponibleRadioButton.isSelected())) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs avant d'ajouter un livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean disponibilite = disponibleRadioButton.isSelected();


        Livre livre = new Livre(titre, auteur, genre, disponibilite);
        try {
            livre.AjouterLivre();
            JOptionPane.showMessageDialog(this, "Livre ajouté avec success !");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
