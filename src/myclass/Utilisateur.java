package myclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Exceptions.IOException;


public class Utilisateur {
    private int Id_Utilisateur;
    private String Nom;
    private String Prenom;
    private String Login;
    private String Pwd;
    private String Role;

    public Utilisateur(int idUtilisateur, String nom, String prenom, String login, String pwd, String role) {
            this.Id_Utilisateur = idUtilisateur;
            this.Nom = nom;
            this.Prenom = prenom;
            this.Login = login;
            this.Pwd = pwd;
            this.Role = role;
    
    }

    public int getIdUtilisateur() {
        return Id_Utilisateur;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getLogin() {
        return Login;
    }

    public String getPwd() {
        return Pwd;
    }

    public String getRole() {
        return Role;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.Id_Utilisateur = idUtilisateur;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public void setPrenom(String prenom) {
        this.Prenom = prenom;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public void setPwd(String pwd) {
        this.Pwd = pwd;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "Id_Utilisateur=" + Id_Utilisateur +
                ", Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Login='" + Login + '\'' +
                ", Pwd='" + Pwd + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }

    public static Utilisateur authentifier(String login, String password) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection connection = null;
        boolean isAuthenticated = false;
        Utilisateur user = null;

            try {
                connection= dbcnx.getConnection();
                String sql = "SELECT * FROM Utilisateur WHERE Login=? AND Pwd=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                isAuthenticated = resultSet.next();
                if (isAuthenticated) {
                    user = new Utilisateur(resultSet.getInt("Id_Utilisateur"), resultSet.getString("Nom"), resultSet.getString("Prenom"), resultSet.getString("Login"), resultSet.getString("Pwd"), resultSet.getString("Role"));
                    System.out.println(user);
                }
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new IOException("Erreur lors de l'authentification : " + e.getMessage());
            } finally {
                dbcnx.closeConnection();
            }

        return user;
    }
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.lastIndexOf(".") > email.lastIndexOf("@");
    }
    public static boolean isValidPassword(String password) {
        if ((password.length() < 8)||(!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))||(!password.matches(".*\\d.*"))||
        (!password.matches(".*[A-Z].*"))||(!password.matches(".*[a-z].*"))) {
            return false;
        }
        return true;
    }

    public void CreerCompte() throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dbcnx.getConnection();
            String role = this.getRole(); 
            if (isValidEmail(Login)) {
                if (isValidPassword(Pwd)) {
                stmt = conn.prepareStatement("INSERT INTO utilisateur (Nom, Prenom, Login, Pwd, Role) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, this.getNom());
                stmt.setString(2, this.getPrenom());
                stmt.setString(3, this.getLogin());
                stmt.setString(4, this.getPwd());
                stmt.setString(5, role);
                stmt.executeUpdate();
                } else {
                    throw new IOException("mot de passe doit contient au moins un caractere majuscule ,"+
                     "au moins un caractere minuscule, au moins caractere spécial , au moins un chiffres et la longeur minimale est 8 caracteres");
                }
            } else {
                throw new IOException("Login invalide il faut etre sous la forme example@example.com");
            }
        } catch (SQLException e) {
            throw new IOException("Erreur lors de la création du compte: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }
    
    public void SupprimerCompte() throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn =  dbcnx.getConnection();
            String role = this.getRole();
            if (role.equals("Etudiant") || role.equals("Enseignant")) {
                stmt = conn.prepareStatement("Delete  from utilisateur WHERE Id_Utilisateur = ? ");
                stmt.setInt(1, this.getIdUtilisateur());
                stmt.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "Cet Utilisateur ne peut pas supprimer un compte");
            }
        } catch (SQLException e) {
            throw new IOException("Erreur lors de la Suppression du compte: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }
    
    public void ModifierCompte(String nom, String prenom, String login, String pwd, String role) throws IOException {
        DataBaseConnection dbcnx = new DataBaseConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try {
            conn =  dbcnx.getConnection();
            String role1 = this.getRole();
            if (role1.equals("Etudiant") || role1.equals("Enseignant")) {
                stmt = conn.prepareStatement("UPDATE utilisateur SET nom = ?, prenom = ?, login = ?, pwd = ?, role = ? WHERE Id_Utilisateur = ?");
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, login);
                stmt.setString(4, pwd);
                stmt.setString(5, role);
                stmt.setInt(6, this.getIdUtilisateur());
                stmt.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "This user cannot modify an account.");
            }
        } catch (SQLException e) {
            throw new IOException("Erreur lors de la modification du compte: " + e.getMessage());
        } finally {
            dbcnx.closeConnection();
        }
    }
    
}



