package myclass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Exceptions.IOException;

public class Emprunt {
    private int Id_Emprunt ;
    private Date Date_Emprunt ;
    private Date Date_Retour ;
    private boolean Statut ;

    public Emprunt(boolean Statut){
        this.Date_Emprunt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date_Emprunt);
        calendar.add(Calendar.DAY_OF_YEAR, 15);
        Date nouvelleDate = calendar.getTime();
        this.Date_Retour = nouvelleDate;
        this.Statut = Statut ;
    }
    
    public Emprunt(int Id_Emprunt, Date Date_Emprunt,Date Date_Retour, boolean Statut) {
        this.Id_Emprunt = Id_Emprunt;
        this.Date_Emprunt = Date_Emprunt;
        this.Date_Retour = Date_Retour;
        this.Statut = Statut ;
    }
    
    public int getId_Emprunt() {
        return Id_Emprunt;
    }
    
    public Date getDate_Emprunt() {
        return Date_Emprunt;
    }
    
    public Date getDate_Retour() {
        return Date_Retour;
    }
    
    public boolean getStatut() {
        return Statut;
    }
    
    public void setId_Emprunt(int Id_Emprunt) {
        this.Id_Emprunt = Id_Emprunt;
    }
    
    public void setDate_Emprunt(Date Date_Emprunt) {
        this.Date_Emprunt = Date_Emprunt;
    }
    
    public void setDate_Retour(Date Date_Retour) {
        this.Date_Retour = Date_Retour;
    }
    
    public void setStatut(boolean Statut) {
        this.Statut = Statut;
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "Id_Emprunt=" + Id_Emprunt +
                ", Date_Emprunt=" + Date_Emprunt +
                ", Date_Retour=" + Date_Retour +
                ", Statut='" + Statut + '\'' +
                '}';
    }
    
    //Ajouter une emprunt
    public void AjouterEmprunt(Utilisateur user, Livre livre) throws IOException  {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbcnx.getConnection();
            Date Date_Emprunt= new Date();
            if (checkLivreExists(livre.getId_Livre(), conn) && livre.getDisponibilite()) {
                // ajouter l'emprunt a la base 
                stmt = conn.prepareStatement("INSERT INTO emprunt (Id_Utilisateur, Id_Livre, Date_Emprunt, Date_Retour, Statut) VALUES (?, ?, ?, ?, ?)");
                stmt.setInt(1, user.getIdUtilisateur());
                stmt.setInt(2, livre.getId_Livre());
                stmt.setDate(3, new java.sql.Date(Date_Emprunt.getTime()));
                stmt.setDate(4, new java.sql.Date(Date_Retour.getTime()));
                stmt.setBoolean(5, Statut);
                stmt.executeUpdate();
                stmt.close();
                livre.ModifierDisponibility(livre.getId_Livre());
                conn.close();
            } else {
                System.out.println("Conditions invalides pour ajouter l'emprunt.");}
        } catch (SQLException e) {
            throw new IOException("Erreur lors de la création du compte: " + e.getMessage());}

        finally {
            dbcnx.closeConnection();
        }
    }
    
    //cherche l'existance de livre
    private boolean checkLivreExists(int idLivre, Connection connection) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM livre WHERE Id_Livre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idLivre);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    //modifier la date de retour d'une emprunt a des conditions specifique
    public void ModifierDateRetour(Emprunt emprunt, int n, Livre livre) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(emprunt.getDate_Retour());
        calendar.add(Calendar.DAY_OF_YEAR, n);
        Date nouvelleDate = calendar.getTime();
        try {
            conn = dbcnx.getConnection();
            if (noReservationAfter(livre.getId_Livre(), nouvelleDate, conn)){
                stmt = conn.prepareStatement("UPDATE emprunt SET Date_Retour=? WHERE Id_Emprunt=?");
                stmt.setDate(1, new java.sql.Date(nouvelleDate.getTime()));
                stmt.setInt(2, emprunt.getId_Emprunt());
                stmt.executeUpdate();
                stmt.close();
                conn.close();
            }
            else {
                throw new IOException("Conditions invalides pour modifier la date de retour.(il ya une reservation pour ce livre en cours au meme temps).");
            }
        }catch (SQLException e) {
            throw new IOException("probleme a la connection de la base de donnes essaie plus tard");
        } finally {
            dbcnx.closeConnection();
        }
    }

    //cherche s'il y a une reservation a en cours a la date de modification pour donnees l'access au modification 
    private boolean noReservationAfter(int idLivre, Date modifiedReturnDate, Connection conn) {
        boolean noReservations = false;
        try {
            String query = "SELECT * FROM reservation WHERE Id_Livre = ? AND Date_Reservation > ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idLivre);
            statement.setDate(2, new java.sql.Date(modifiedReturnDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                noReservations = true;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noReservations;
    }

    //valider le retour d'un livre
    public void ValiderRetour(Utilisateur user ,Livre livre) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {  
            conn = dbcnx.getConnection();
            stmt = conn.prepareStatement("UPDATE emprunt SET Statut=0 WHERE Id_Emprunt=?");
            stmt.setInt(1, Id_Emprunt);
            stmt.executeUpdate();
            stmt.close();
            livre.ModifierDisponibility(livre.getId_Livre());
            livre = Livre.getLivreById(livre.getId_Livre());
            Utilisateur premierUtilisateur = FirstUserReservation(user,livre,conn);
            if (premierUtilisateur != null) {
                AjouterEmprunt(premierUtilisateur, livre);
                ConfirmerReservation(premierUtilisateur, livre,conn);
            }
        } catch (SQLException e) {
            throw new IOException("Erreur lors de la création du compte: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }
    
    //confirmer la resrvation apres l'emprunt d'un livre
    private void ConfirmerReservation(Utilisateur user, Livre livre ,Connection conn) throws IOException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Reservation reservation = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM reservation WHERE Id_Livre=? AND Statut=? ORDER BY Date_Reservation ASC LIMIT 1");
            stmt.setInt(1, livre.getId_Livre());
            stmt.setString(2, "attente");
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int IdReservation = resultSet.getInt("Id_Reservation");
                int Id_Utilisateur = resultSet.getInt("Id_Utilisateur");
                int Id_Livre = resultSet.getInt("Id_Livre");
                Timestamp Date_Reservation = resultSet.getTimestamp("Date_Reservation");
                String Statut = resultSet.getString("Statut");
                reservation = new Reservation(IdReservation,Id_Utilisateur,Id_Livre,Date_Reservation,Statut);
                reservation.ModifierReservation("confirmee");
                stmt.close();
            }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    //chercher et retourner le premier utilisateur qui a une reservation a un livre donnee 
    private Utilisateur FirstUserReservation(Utilisateur user, Livre livre,Connection conn) throws IOException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Utilisateur user0 = null;
    
        try {
            stmt = conn.prepareStatement("SELECT * FROM reservation WHERE Id_Livre=? AND Statut=? ORDER BY Date_Reservation ASC LIMIT 1");
            stmt.setInt(1, livre.getId_Livre());
            stmt.setString(2, "attente");
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int IdUtilisateur = resultSet.getInt("Id_Utilisateur");
                stmt.close();
                stmt = conn.prepareStatement("SELECT * FROM Utilisateur WHERE Id_Utilisateur=?");
                stmt.setInt(1, IdUtilisateur);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    user0 = new Utilisateur(
                        rs.getInt("Id_Utilisateur"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Login"),
                        rs.getString("Pwd"),
                        rs.getString("Role")
                    );
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return user0;
    }
    
    //chercher tous les emprunts effectuer par un utilisateur donnee et retourne une liste de ces emprunts
    public static List<Emprunt> AfficherHistoriqueEmprunt(Utilisateur user) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        List<Emprunt> historiqueEmprunts = new ArrayList<>();
    
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM emprunt WHERE Id_Utilisateur = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getIdUtilisateur());
            ResultSet resultSet = statement.executeQuery();
    
    
            while (resultSet.next()) {
                int idEmprunt = resultSet.getInt("Id_Emprunt");
                Date dateEmprunt = resultSet.getDate("Date_Emprunt");
                Date dateRetour = resultSet.getDate("Date_Retour");
                boolean statut = resultSet.getBoolean("Statut");
                Emprunt emprunt = new Emprunt(idEmprunt, dateEmprunt, dateRetour, statut);
                historiqueEmprunts.add(emprunt);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("erreur: connection a la base de donnees essaie plus tard !");
        } 
        finally {
            dbcnx.closeConnection();
        }
        return historiqueEmprunts;
    }
    
    //retourne le type livre depuis une emprunt (besoin dans l'interface)
    public static Livre AfficherLivre(Emprunt emprunt) {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Livre livre = null ;
    
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT Id_Livre FROM Emprunt WHERE Id_Emprunt = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, emprunt.getId_Emprunt());
            ResultSet resultSet0 = statement.executeQuery();
    
            if (resultSet0.next()) {
                int idLivre = resultSet0.getInt("Id_Livre");
                PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM livre WHERE Id_Livre = ?");
                statement2.setInt(1, idLivre);
                ResultSet resultSet = statement2.executeQuery();
                if (resultSet.next()) {
                    livre = new Livre(resultSet.getInt("Id_Livre"),resultSet.getString("Titre"),resultSet.getString("Auteur"),
                    resultSet.getString("Genre"),resultSet.getBoolean("Disponibilite"));
                }
                resultSet.close();
                statement2.close();
            }
    
            resultSet0.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbcnx.closeConnection();
        }
        return livre;
    }
    
    //retourne le type utilisateur depuis une emprunt (besoin dans l'interface)
    public static Utilisateur AfficherUser(Emprunt emprunt){
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Utilisateur user = null ;
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT Id_Utilisateur FROM Emprunt WHERE Id_Emprunt = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, emprunt.getId_Emprunt());
            ResultSet resultSet0 = statement.executeQuery();
            if (resultSet0.next()) {
                int iduser = resultSet0.getInt("Id_Utilisateur");
                PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM Utilisateur WHERE Id_Utilisateur = ?");
                statement2.setInt(1, iduser);
                ResultSet resultSet = statement2.executeQuery();
                if (resultSet.next()) {
                    user = new Utilisateur(resultSet.getInt("Id_Utilisateur"), resultSet.getString("Nom"), resultSet.getString("Prenom"),
                    resultSet.getString("Login"), resultSet.getString("Pwd"), resultSet.getString("Role"));
                }
                resultSet.close();
                statement2.close();
            }
            resultSet0.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbcnx.closeConnection();
        }
        return user;
    }

    //retourne liste des rapports de livre les plus empruntees et les utilisateurs qui ont fait plus d'emprunts
    public static List<String> GenererRapport() {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        List<String> Rapport = new ArrayList<>();
        try {
            conn = dbcnx.getConnection();
            stmt = conn.prepareStatement("SELECT titre, auteur, genre, COUNT(emprunt.id_emprunt) AS nb_emprunts " +
                "FROM livre " +
                "LEFT JOIN emprunt ON livre.id_livre = emprunt.id_livre " +
                "GROUP BY titre, auteur, genre " +
                "ORDER BY nb_emprunts DESC");

            ResultSet resultSet = stmt.executeQuery();
            Rapport.add("Statistiques des livres empruntés : ");
        
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                String genre = resultSet.getString("genre");
                int nbEmprunts = resultSet.getInt("nb_emprunts");
                Rapport.add(titre + " | " + auteur + " | " + genre + " | " + nbEmprunts);
            }
            stmt.close();
            resultSet.close();
            stmt = conn.prepareStatement("SELECT utilisateur.id_utilisateur, nom, prenom, role, COUNT(emprunt.id_emprunt) AS nb_emprunts " +
                "FROM utilisateur " +
                "LEFT JOIN emprunt ON utilisateur.id_utilisateur = emprunt.id_utilisateur " +
                "GROUP BY utilisateur.id_utilisateur, nom, prenom, role " +
                "ORDER BY nb_emprunts DESC");
            ResultSet resultSet1 = stmt.executeQuery();
            Rapport.add("");
            Rapport.add("Statistiques des utilisateurs emprunteurs : ");
            while (resultSet1.next()) {
                if ((resultSet1.getInt("utilisateur.id_utilisateur")!=1) && (!resultSet1.getString("role").equals("Bibliothecaire"))){
                    String nom = resultSet1.getString("nom");
                    String prenom = resultSet1.getString("prenom");
                    String role = resultSet1.getString("role");
                    int nbEmprunts = resultSet1.getInt("nb_emprunts");
                    Rapport.add(nom + " | " + prenom + " | " + role + " | " + nbEmprunts);
            }}
            stmt.close();
            resultSet1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbcnx.closeConnection();
        }
        return Rapport;
    }

    // retourne un nombre de type Long -> le nbre de jour en retard 
    public static int CalculeJoursRestant(Utilisateur user, Emprunt emprunt) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        long n = 0;
        try {
            conn = dbcnx.getConnection();
            stmt = conn.prepareStatement("SELECT MAX(Date_Retour) AS MaxDate FROM emprunt WHERE Id_Utilisateur = ? AND Id_Emprunt = ?");
            stmt.setInt(1, user.getIdUtilisateur());
            stmt.setInt(2, emprunt.getId_Emprunt());
            resultSet = stmt.executeQuery();
    
            if (resultSet.next()) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(resultSet.getDate("MaxDate"));
                long differenceInMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
                n = differenceInMillis / (1000 * 60 * 60 * 24);
            }
    
        }catch (SQLException e) {
            throw new IOException(e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return (int) n+1;
    }

    //retourne une liste de tout les emprunts en cours
    public static List<Emprunt> AfficherEmpruntEnCours(Utilisateur user) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        List<Emprunt> empruntsEnCours = new ArrayList<>();
    
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM emprunt WHERE Id_Utilisateur = ? AND Statut = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getIdUtilisateur());
            statement.setBoolean(2, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idEmprunt = resultSet.getInt("Id_Emprunt");
                Date dateEmprunt = resultSet.getDate("Date_Emprunt");
                Date dateRetour = resultSet.getDate("Date_Retour");
                boolean statut = resultSet.getBoolean("Statut");
                Emprunt emprunt = new Emprunt(idEmprunt, dateEmprunt, dateRetour, statut);
                empruntsEnCours.add(emprunt);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("erreur: ");
        } finally {
            dbcnx.closeConnection();
        }   
        return empruntsEnCours;
    }
    
    //recherche et retourne un emprunt selon son ID
    public static Emprunt rechercheEmprunt(int idEmprunt) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Emprunt emprunt = null;
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM emprunt WHERE Id_Emprunt = ? ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idEmprunt);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_Emprunt = resultSet.getInt("Id_Emprunt");
                Date dateEmprunt = resultSet.getDate("Date_Emprunt");
                Date dateRetour = resultSet.getDate("Date_Retour");
                boolean statut = resultSet.getBoolean("Statut");
                emprunt = new Emprunt(id_Emprunt, dateEmprunt, dateRetour, statut);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("erreur: ");
        } finally {
            dbcnx.closeConnection();
        }
        return emprunt;
    }

    //retourne liste des emprunts selon leur statut
    public static List<Emprunt> RappelRetour(boolean x) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        List<Emprunt> Rappel = new ArrayList<>();
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM emprunt WHERE Statut = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setBoolean(1, x);
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                int idEmprunt = resultSet.getInt("Id_Emprunt");
                Date dateEmprunt = resultSet.getDate("Date_Emprunt");
                Date dateRetour = resultSet.getDate("Date_Retour");
                boolean statut = resultSet.getBoolean("Statut");
                Emprunt emprunt = new Emprunt(idEmprunt, dateEmprunt, dateRetour, statut);
                Rappel.add(emprunt);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("erreur: ");
        } finally {
            dbcnx.closeConnection();
        }
        return Rappel;
    }
    

}