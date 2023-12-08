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
        frame.setLayout(new GridLayout(6, 2));
        frame.setSize(400, 200);

        JLabel titreLabel = new JLabel("Titre:");
        titreField = new JTextField();

        JLabel auteurLabel = new JLabel("Auteur:");
        auteurField = new JTextField();

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField();

        JLabel disponibiliteLabel = new JLabel("Disponibilité:");

        disponibleRadioButton = new JRadioButton("Disponible");
        nonDisponibleRadioButton = new JRadioButton("Non Disponible");

        disponibiliteGroup = new ButtonGroup();
        disponibiliteGroup.add(disponibleRadioButton);
        disponibiliteGroup.add(nonDisponibleRadioButton);


        JButton addButton = new JButton("Ajouter Livre");
        addButton.addActionListener(e -> ajouterLivre());
        frame.add(titreLabel);
        frame.add(titreField);
        frame.add(auteurLabel);
        frame.add(auteurField);
        frame.add(genreLabel);
        frame.add(genreField);
        frame.add(disponibiliteLabel);
        frame.add(disponibleRadioButton);
        frame.add(new JLabel()); 
        frame.add(nonDisponibleRadioButton);
        frame.add(addButton);


        JButton backButton = new JButton("Retour a gerer livre");
        backButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GererLivre(user));
            frame.dispose();
        });
        frame.add(backButton);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private void ajouterLivre() {

        String titre = titreField.getText();
        String auteur = auteurField.getText();
        String genre = genreField.getText();


        if (titre.isEmpty() || auteur.isEmpty() || genre.isEmpty() || (!disponibleRadioButton.isSelected() && !nonDisponibleRadioButton.isSelected())) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs avant d'ajouter un livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // check
        }

        boolean disponibilite = disponibleRadioButton.isSelected();


        Livre livre = new Livre(titre, auteur, genre, disponibilite); //constructeur maghir id , khatrou auto inc
        try {
            livre.AjouterLivre();
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
