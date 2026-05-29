package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Payer;

public class PayerDAO {

	    private String jdbcURL = "jdbc:mysql://localhost:3306/bourses?useSSL=false";
	    private String jdbcUsername = "root";
	    private String jdbcPassword = "";

	    private static final String INSERT_PAIEMENT_SQL = "INSERT INTO payer" + "  (idpaye, matricule, annee_univ, date, nbr_mois) VALUES " +
	            " (?, ?, ?, ?, ?);";

	    private static final String SELECT_PAIEMENT_BY_IDPAYE = "SELECT p.*, s.nom, s.datenais, s.sexe, s.institution, s.niveau FROM payer p JOIN etudiant s ON p.matricule = s.matricule WHERE p.idpaye = ?";
	    private static final String SELECT_TOUS_PAIEMENTS = "select * from payer";
	    private static final String DELETE_PAIEMENT_SQL = "delete from payer where idpaye = ?;";
	    private static final String UPDATE_PAIEMENTS_SQL = "update payer set idpaye = ?, matricule = ?, annee_univ = ?, date =?, nbr_mois = ? where idpaye = ?;";
	 
	    public PayerDAO() {}

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
	    
	    // Liste des paiements avec détails
	    public Payer selectPayerWithDetails(String idpaye) {
	        Payer paiement = null;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAIEMENT_BY_IDPAYE)) {
	            preparedStatement.setString(1, idpaye);
	            ResultSet rs = preparedStatement.executeQuery();
	            if (rs.next()) {
	                String matricule = rs.getString("matricule");
	                String nom = rs.getString("nom");
	                Date datenais = rs.getDate("datenais");
	                String sexe = rs.getString("sexe");
	                String institution = rs.getString("institution");
	                String niveau = rs.getString("niveau");
	                String annee_univ = rs.getString("annee_univ");

	                Timestamp timestamp = rs.getTimestamp("date");
	                LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;

	                int nbr_mois = rs.getInt("nbr_mois");

	                paiement = new Payer(idpaye, matricule, annee_univ, date, nbr_mois, nom, datenais, sexe, institution, niveau);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return paiement;
	    }
	    
	    // Ajout d'un paiement
	    public void insertPayer(Payer paiement) throws SQLException {
	        try (Connection connection = getConnection(); 
	             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAIEMENT_SQL)) {
	            
	            preparedStatement.setString(1, paiement.getIdpaye());
	            preparedStatement.setString(2, paiement.getMatricule());
	            preparedStatement.setString(3, paiement.getAnnee_univ());
	            
	            // Conversion de LocalDateTime à Timestamp
	            java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(paiement.getDate());
	            preparedStatement.setTimestamp(4, sqlTimestamp);
	            
	            preparedStatement.setInt(5, paiement.getNbr_mois());
	            
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    // Liste de paiement par son id
	    public Payer selectPayer(String idpaye) {
	        Payer paiement = null;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAIEMENT_BY_IDPAYE);) {
	            preparedStatement.setString(1, idpaye);
	            ResultSet rs = preparedStatement.executeQuery();
	            while (rs.next()) {
	                String matricule = rs.getString("matricule");
	                String annee_univ = rs.getString("annee_univ");
	                
	                // Récupération de la date en tant que Timestamp
	                Timestamp timestamp = rs.getTimestamp("date");
	                LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
	                
	                int nbr_mois = rs.getInt("nbr_mois");
	                paiement = new Payer(idpaye, matricule, annee_univ, date, nbr_mois);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return paiement;
	    }

	    // Liste de tous les paiements
	    public List<Payer> selectAllPaiements() {
	        List<Payer> paiements = new ArrayList<>();
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUS_PAIEMENTS);) {
	            
	            ResultSet rs = preparedStatement.executeQuery();
	            while (rs.next()) {
	                String idpaye = rs.getString("idpaye");
	                String matricule = rs.getString("matricule");
	                String annee_univ = rs.getString("annee_univ");
	                
	                // Récupération de la date en tant que Timestamp
	                Timestamp timestamp = rs.getTimestamp("date");
	                LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
	                
	                int nbr_mois = rs.getInt("nbr_mois");
	                paiements.add(new Payer(idpaye, matricule, annee_univ, date, nbr_mois));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return paiements;
	    }
	    
	    // Suppression d'un paiement
	    public boolean deletePayer(String idpaye) throws SQLException {
	        boolean rowDeleted;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PAIEMENT_SQL);) {
	            statement.setString(1, idpaye);
	            rowDeleted = statement.executeUpdate() > 0;
	        }
	        return rowDeleted;
	    }

	    // Mise à jour d'un paiement
	    public boolean updatePayer(Payer paiement, String oldIdpaye) throws SQLException {
	        boolean rowUpdated;
	        try (Connection connection = getConnection(); 
	             PreparedStatement statement = connection.prepareStatement(UPDATE_PAIEMENTS_SQL);) {
	            
	            statement.setString(1, paiement.getIdpaye());
	            statement.setString(2, paiement.getMatricule());
	            statement.setString(3, paiement.getAnnee_univ());
	            
	            // Conversion de LocalDateTime à Timestamp
	            LocalDateTime localDateTime = paiement.getDate();
	            Timestamp timestamp = localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
	            statement.setTimestamp(4, timestamp);
	            
	            statement.setInt(5, paiement.getNbr_mois());
	            statement.setString(6, oldIdpaye);
	            
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
