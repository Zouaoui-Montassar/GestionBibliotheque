package myclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import Exceptions.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Livre {
    private int Id_Livre ;
    private String Titre ;
    private String Auteur ;
    private String Genre ;
    private boolean Disponibilite ;
    public Livre(String Titre ,String Auteur,String Genre , boolean Disponibilite){
        this.Titre=Titre;
        this.Auteur=Auteur;
        this.Genre=Genre;
        this.Disponibilite=Disponibilite;
    }
    
    public Livre(int Id_Livre,String Titre ,String Auteur,String Genre , boolean Disponibilite){
        this.Id_Livre=Id_Livre;
        this.Titre=Titre;
        this.Auteur=Auteur;
        this.Genre=Genre;
        this.Disponibilite=Disponibilite;
    } 

    @Override
    public String toString(){
        return "livre{" +
        "Id_livre=" + Id_Livre +
        ", titre='" + Titre + '\'' +
        ", auteur='" + Auteur + '\'' +
        ", genre='" + Genre + '\'' +
        ", dispo='" + Disponibilite + '\'' +
        '}';
    }
    
    public int getId_Livre() {
        return Id_Livre;
    }
    
    public void setTitre(String Titre) {
        this.Titre = Titre;
    }
    
    public String getTitre() {
        return Titre;
    }
    
    public void setAuteur(String Auteur) {
        this.Auteur = Auteur;
    }
    
    public String getAuteur() {
        return Auteur;
    }
    
    public void setGenre(String Genre) {
        this.Genre = Genre;
    }
    
    public String getGenre() {
        return Genre;
    }
    
    public void setDisponibilite(boolean Disponibilite) {
        this.Disponibilite = Disponibilite;
    }
    
    public boolean getDisponibilite() {
        return Disponibilite;
    }
    
    //retourner type livre par son ID
    public static Livre getLivreById(int id) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection connection = null;
        Livre livre = null;
        try {
            connection = dbcnx.getConnection();
            String query = "SELECT * FROM Livre WHERE Id_Livre = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    livre = new Livre(
                            resultSet.getInt("Id_Livre"),
                            resultSet.getString("Titre"),
                            resultSet.getString("Auteur"),
                            resultSet.getString("Genre"),
                            resultSet.getBoolean("Disponibilite"));
                }
            }
        } catch (SQLException e) {
            throw new IOException("Error fetching Livre details: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return livre;
    }

    //ajouter un livre (utilise seulement dans l'interface de la bibliothecaire)
    public void AjouterLivre() throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try  {
            conn = dbcnx.getConnection();
            String query = "INSERT INTO Livre (Titre, Auteur, Genre, Disponibilite) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, Titre);
            ps.setString(2, Auteur);
            ps.setString(3, Genre);
            ps.setBoolean(4, Disponibilite);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Erreur d'ajout: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }

    //supprimer un livre (utilise seulement dans l'interface de la bibliothecaire)
    public void SupprimerLivre(int Id_Livre) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try  {
            conn = dbcnx.getConnection();
            String query = "delete from Livre where Id_Livre = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Id_Livre);
            int n = ps.executeUpdate(); 
            if (n > 0) {
                System.out.println("Livre supprimé !");
            } else {
                throw new IOException("Ce livre n'existe pas");
            }
        } catch (SQLException e) {
            throw new IOException("Erreur de suppression: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }
    
    //modifier la disponibilite de livre
    public void ModifierDisponibility(int Id_Livre) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try {
            conn = dbcnx.getConnection();
            String query= "UPDATE livre set Disponibilite = ? where Id_Livre = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setBoolean(1,!Disponibilite);
            ps.setInt(2, Id_Livre);
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new IOException("Erreur de modification de la disponibilité " + e.getMessage());
        }finally {
             dbcnx.closeConnection();
        }
    }

    //retourner liste toute les livres qui s'affiche apres la recherche
    public static Livre[] RechercherLivre(String Titre) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Livre[] livres = null;
        try {
            conn = dbcnx.getConnection();
            String indic_rech = "%" + Titre + "%";
            String query ="SELECT * FROM Livre WHERE Titre LIKE ?";
            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, indic_rech);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                throw new IOException("Aucun livre existe avec ce titre");
            } else {
                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();
                livres = new Livre[rowCount];
                int i = 0;
                while (rs.next()){
                    Livre l = new Livre(rs.getInt("Id_Livre"), rs.getString("Titre"),
                                        rs.getString("Auteur"), rs.getString("Genre"),
                                        rs.getBoolean("Disponibilite"));
                    livres[i] = l;
                    i++;
                }
            }
        } catch ( SQLException e ){
            throw new IOException("Erreur de recherche du livre: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return livres;
    } 


    public void AfficherDetailsLivre() throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try {
            conn = dbcnx.getConnection();
            String query="SELECT * FROM Livre where Id_Livre= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, 7);

            ResultSet rs = ps.executeQuery();
            if( rs.next()){
                System.out.println("Details du livre :");
                System.out.println("Titre : "+rs.getString("Titre"));
                System.out.println("Auteur : "+rs.getString("Auteur"));
                System.out.println("Genre : "+rs.getString("Genre"));
                boolean disp = rs.getBoolean("Disponibilite");
                System.out.println("Disponibilite : "+ (disp? "Disponible" : "Indisponible")); 
            }
            else{
                throw new IOException("Aucun livre trouvé !");
            }


        }
        catch (SQLException e ){
            throw new IOException("Erreur d'affichage du livre" + e.getMessage());
        }
        finally{
            dbcnx.closeConnection();
        }
    }
    
    //retourner liste de toute les livres
    public static List<Livre> AfficherCatalogue() throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        List<Livre> livres = new ArrayList<>();
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT Id_Livre, Titre, Auteur, Genre, Disponibilite FROM Livre";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Id_Livre");
                String titre = rs.getString("Titre");
                String auteur = rs.getString("Auteur");
                String genre = rs.getString("Genre");
                boolean disp = rs.getBoolean("Disponibilite");
                Livre livre = new Livre(id, titre, auteur, genre, disp);
                livres.add(livre);
            }
        } catch (SQLException e) {
            throw new IOException("Erreur d'affichage du catalogue " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return livres;
    }

}