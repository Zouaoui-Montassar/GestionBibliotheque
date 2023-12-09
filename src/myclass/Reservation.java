package myclass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import Exceptions.IOException;


public class Reservation {
    private int Id_Reservation;
    private int Id_Utilisateur;
    private int Id_Livre;
    private Timestamp Date_Reservation;
    private String Statut;

    public Reservation(int Id_Reservation, int Id_Utilisateur, int Id_Livre, Timestamp Date_Reservation, String Statut) {
        this.Id_Reservation = Id_Reservation;
        this.Id_Utilisateur = Id_Utilisateur;
        this.Id_Livre = Id_Livre;
        this.Date_Reservation = Date_Reservation;
        this.Statut = Statut;
    }
    
    public Reservation(){}
    
    public int getId_Reservation() {
        return Id_Reservation;
    }

    public void setId_Reservation(int Id_Reservation) {
        this.Id_Reservation = Id_Reservation;
    }

    public int getId_Utilisateur() {
        return Id_Utilisateur;
    }
    
    public void setId_Utilisateur(int Id_Utilisateur) {
        this.Id_Utilisateur = Id_Utilisateur;
    }
    
    public int getId_Livre() {
        return Id_Livre;
    }
    
    public void setId_Livre(int Id_Livre) {
        this.Id_Livre = Id_Livre;
    }
    
    public Timestamp getDate_Reservation() {
        return Date_Reservation;
    }
    
    public void setDate_Reservation(Timestamp Date_Reservation) {
        this.Date_Reservation = Date_Reservation;
    }
    
    public String getStatut() {
        return Statut;
    }
    
    public void setStatut(String Statut) {
        this.Statut = Statut;
    }

    //ajouter une reservation
   public void AjouterReservation(int Id_Utilisateur, int Id_Livre) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try {
            conn = dbcnx.getConnection();
            String checkQuery = "SELECT Id_Reservation FROM Reservation WHERE Id_Utilisateur = ? AND Id_Livre = ? AND Statut = 'attente'";
            PreparedStatement checkps = conn.prepareStatement(checkQuery);
            checkps.setInt(1, Id_Utilisateur);
            checkps.setInt(2, Id_Livre);
            ResultSet resultSet = checkps.executeQuery();
            String checkQuery1 = "SELECT Id_Emprunt FROM emprunt WHERE Id_Utilisateur = ? AND Id_Livre = ? AND Statut = ?";
            PreparedStatement checkps1 = conn.prepareStatement(checkQuery1);
            checkps1.setInt(1, Id_Utilisateur);
            checkps1.setInt(2, Id_Livre);
            checkps1.setBoolean(3, true);
            ResultSet resultSet1 = checkps1.executeQuery();
            if (resultSet.next()) {
                throw new IOException("Vous avez deja reservé ce livre");
            }else if (resultSet1.next()){
                throw new IOException("Vous etes deja a la processus d'emprunter ce livre");
            } else {
                String insertQuery = "INSERT INTO Reservation (Id_Utilisateur, Id_Livre, Date_Reservation, Statut) VALUES (?, ?, ?, ?)";
                PreparedStatement insertPs = conn.prepareStatement(insertQuery);
                insertPs.setInt(1, Id_Utilisateur);
                insertPs.setInt(2, Id_Livre);
                insertPs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                insertPs.setString(4, "attente");
                insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IOException("Erreur d'ajout d'une réservation: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    } 
   
    //modifier la reservation selon la statut
    public void ModifierReservation(String ch) throws IOException{
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try  {
            conn = dbcnx.getConnection();
            String query = "UPDATE Reservation SET Statut = ? WHERE Id_Reservation = ?"; 
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ch);
            ps.setInt(2, Id_Reservation);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Erreur de confirmation de la reservation: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
   }
   
   //annuler la reservation
    public void AnnulerReservation() throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        try  {
            conn = dbcnx.getConnection();
            String query = "UPDATE Reservation SET Statut = ? Where Id_Reservation = ?"; 
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "annulee");
            ps.setInt(2, Id_Reservation);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Erreur d'annulation du reservation: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }

    //retourner liste des reservation en attent d'un utilisateur specifique
    public static List<Reservation> AfficherReservation(Utilisateur user) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        List<Reservation> ReservationEnCours = new ArrayList<>();
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM reservation WHERE Id_Utilisateur = ? AND Statut = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getIdUtilisateur());
            statement.setString(2, "attente");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idreservation = resultSet.getInt("Id_Reservation");
                int idutilisateur = resultSet.getInt("Id_Utilisateur");
                int idlivre = resultSet.getInt("Id_Livre");
                Timestamp Dateres = resultSet.getTimestamp("Date_Reservation");
                String statut = resultSet.getString("Statut");
                Reservation reservation = new Reservation(idreservation, idutilisateur, idlivre, Dateres, statut);
                ReservationEnCours.add(reservation);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("Error fetching reservations: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return ReservationEnCours;
    }
    
    //cherche un livre depuis une reservation
    public static Livre AfficherLivre(Reservation res) {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Livre livre = null ;
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT Id_Livre FROM reservation WHERE Id_Reservation = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, res.getId_Reservation());
            ResultSet resultSet0 = statement.executeQuery();
            if (resultSet0.next()) {
                int idLivre = resultSet0.getInt("Id_Livre");
                PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM livre WHERE Id_Livre = ?");
                statement2.setInt(1, idLivre);
                ResultSet resultSet = statement2.executeQuery();
                if (resultSet.next()) {
                    livre = new Livre(resultSet.getInt("Id_Livre"),resultSet.getString("Titre"),resultSet.getString("Auteur"),resultSet.getString("Genre"),resultSet.getBoolean("Disponibilite"));
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

    //cherche une reservatin depuis son id 
    public static Reservation ChercherReservation(int n) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        Reservation reservation = null;
        try {
            conn = dbcnx.getConnection();
            String query = "SELECT * FROM reservation WHERE Id_Reservation = ? ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, n);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idres = resultSet.getInt("Id_Reservation");
                int iduser = resultSet.getInt("Id_Utilisateur");
                int idlivre = resultSet.getInt("Id_Livre");
                Timestamp Dateres = resultSet.getTimestamp("Date_Reservation");
                String statut = resultSet.getString("Statut");
                reservation = new Reservation(idres, iduser, idlivre, Dateres, statut);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new IOException("Error fetching reservation: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
        return reservation;
    }
}
