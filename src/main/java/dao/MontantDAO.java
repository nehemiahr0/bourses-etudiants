package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Montant;

public class MontantDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/bourses?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_MONTANT_SQL = "INSERT INTO montant" + "  (idniv, niveau, montant, equipement) VALUES " +
            " (?, ?, ?, ?);";

    private static final String SELECT_MONTANT_BY_IDNIV = "select * from montant where idniv =?";
    private static final String SELECT_TOUS_MONTANTS = "select * from montant";
    private static final String DELETE_MONTANT_SQL = "delete from montant where idniv = ?;";
    private static final String UPDATE_MONTANTS_SQL = "UPDATE montant SET idniv = ?, niveau = ?, montant = ?, equipement =? WHERE idniv = ?;";
    private static final String SELECT_EQUIPEMENTS_BY_NIVEAU = "SELECT equipement, montant FROM montant WHERE niveau = ?";

    public MontantDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

 // Méthode pour récupérer les montants d'équipement par niveau
    public List<Montant> getMontantsByNiveau(String niveau) {
        List<Montant> bourses = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EQUIPEMENTS_BY_NIVEAU)) {
            
            preparedStatement.setString(1, niveau);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int equipement = rs.getInt("equipement");
                int bourse = rs.getInt("montant");
                bourses.add(new Montant(equipement, bourse));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return bourses;
    }
    
    // Ajout des montants
    public void insertMontant(Montant bourse) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MONTANT_SQL)) {
            preparedStatement.setString(1, bourse.getIdniv());
            preparedStatement.setString(2, bourse.getNiveau());
            preparedStatement.setInt(3, bourse.getMontant());
            preparedStatement.setInt(4, bourse.getEquipement());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    // Liste des montants par niveau
    public Montant selectMontant(String idniv) {
        Montant bourse = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MONTANT_BY_IDNIV);) {
            preparedStatement.setString(1, idniv);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String niveau = rs.getString("niveau");
                int montant = rs.getInt("montant");
                int equipement = rs.getInt("equipement");
                bourse = new Montant(idniv, niveau, montant, equipement);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return bourse;
    }
    
    // Liste de tous les montants
    public List<Montant> selectAllMontants() {
        List<Montant> boursess = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUS_MONTANTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String idniv = rs.getString("idniv");
                String niveau = rs.getString("niveau");
                int montant = rs.getInt("montant");
                int equipement = rs.getInt("equipement");
                boursess.add(new Montant(idniv, niveau, montant, equipement));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return boursess;
    }
    
    // Suppression d'un montant
    public boolean deleteMontant(String idniv) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_MONTANT_SQL);) {
            statement.setString(1, idniv);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    

    // Modification ou mise à jour d'un montant
    public boolean updateMontant(Montant bourse, String oldIdniv) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_MONTANTS_SQL);) {
            statement.setString(1, bourse.getIdniv());
            statement.setString(2, bourse.getNiveau());
            statement.setInt(3, bourse.getMontant());
            statement.setInt(4, bourse.getEquipement());
            statement.setString(5, oldIdniv); // Utilise l'ancien idniv pour la clause WHERE
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
