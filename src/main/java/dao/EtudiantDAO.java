package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Etudiant;

public class EtudiantDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/bourses?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_ETUDIANTS_SQL = "INSERT INTO etudiant" + "  (matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ) VALUES " +
        " (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ETUDIANT_BY_MATRICULE = "select matricule,nom,sexe,datenais,institution,niveau,mail,annee_univ from etudiant where matricule =?";
    private static final String SELECT_TOUS_ETUDIANTS = "select * from etudiant";
    private static final String DELETE_ETUDIANTS_SQL = "delete from etudiant where matricule = ?;";
    private static final String UPDATE_ETUDIANTS_SQL = "update etudiant set matricule = ?, nom = ?, sexe = ?, datenais =? , institution = ?, niveau = ?,  mail= ?, annee_univ =? where matricule = ?;";
    private static final String SELECT_TOUS_NIVEAUX = "SELECT DISTINCT niveau FROM etudiant";
    private static final String SELECT_TOUS_INSTITUTIONS = "SELECT DISTINCT institution FROM etudiant";
    private static final String FILTER_ETUDIANTS = "SELECT * FROM etudiant WHERE (? IS NULL OR niveau = ?) AND (? IS NULL OR institution = ?)";


    
public EtudiantDAO() {}

protected Connection getConnection() {
    Connection connection = null;
    try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return connection;
}
// Méthode pour mapper le nom du mois à son équivalent numérique
private static final Map<String, String> moisMap = new HashMap<>();
static {
    moisMap.put("janvier", "01");
    moisMap.put("février", "02");
    moisMap.put("mars", "03");
    moisMap.put("avril", "04");
    moisMap.put("mai", "05");
    moisMap.put("juin", "06");
    moisMap.put("juillet", "07");
    moisMap.put("août", "08");
    moisMap.put("septembre", "09");
    moisMap.put("octobre", "10");
    moisMap.put("novembre", "11");
    moisMap.put("décembre", "12");
}

public List<Etudiant> getRetardataires(String mois) {
    List<Etudiant> retardataires = new ArrayList<>();
    
    String query = "SELECT e.matricule, e.nom, e.mail " +
                   "FROM etudiant e " +
                   "WHERE NOT EXISTS (" +
                   "    SELECT 1 " +
                   "    FROM payer p " +
                   "    WHERE p.matricule = e.matricule " +
                   "    AND MONTH(p.date) = ?" + // Utilisation de MONTHNAME pour obtenir le nom du mois
                   ")";
    
    try (Connection connection = getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
        
        // Convertir le nom du mois en numéro de mois
        String moisNumerique = moisMap.get(mois.toLowerCase());
        
        ps.setString(1, moisNumerique);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setMatricule(rs.getString("matricule"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setMail(rs.getString("mail"));
                retardataires.add(etudiant);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérer l'exception ou la propager si nécessaire
    }
    
    return retardataires;
}


public Etudiant getEtudiantByEmail(String email) throws SQLException {
    Etudiant etudiant = null;
    String query = "SELECT * FROM etudiant WHERE mail = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, email);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                etudiant = new Etudiant();
                etudiant.setNom(rs.getString("nom"));
                etudiant.setMatricule(rs.getString("matricule"));
                etudiant.setMail(rs.getString("mail"));
                // Ajoutez d'autres attributs de l'étudiant si nécessaire
            }
        }
    }
    
    return etudiant;
}



public void insertEtudiant(Etudiant etudiant) throws SQLException {
    System.out.println(INSERT_ETUDIANTS_SQL);
    // try-with-resource statement will auto close the connection.
    try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ETUDIANTS_SQL)) {
        preparedStatement.setString(1, etudiant.getMatricule());
        preparedStatement.setString(2, etudiant.getNom());
        preparedStatement.setString(3, etudiant.getSexe());
        preparedStatement.setDate(4, etudiant.getDatenais());
        preparedStatement.setString(5, etudiant.getInstitution());
        preparedStatement.setString(6, etudiant.getNiveau());
        preparedStatement.setString(7, etudiant.getMail());
        preparedStatement.setString(8, etudiant.getAnnee_univ());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        printSQLException(e);
    }
}
// select etudiant par matricule
public Etudiant selectEtudiant(String matricule) {
    Etudiant etudiant = null;
    // 1. Etablir la Connection
    try (Connection connection = getConnection();
        // 2. Créer a statement avec l'objet connection
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ETUDIANT_BY_MATRICULE);) {
        preparedStatement.setString(1, matricule);
        System.out.println(preparedStatement);
        // 3. Execute or update query
        ResultSet rs = preparedStatement.executeQuery();

        // 4: Process l'objet ResultSet 
        while (rs.next()) {
        	//String matricule = rs.getString("matricule");
            String nom = rs.getString("nom");
            String sexe = rs.getString("sexe");
            Date datenais = rs.getDate("datenais");
            String institution = rs.getString("institution");
            String niveau = rs.getString("niveau");
            String mail = rs.getString("mail");
            String annee_univ = rs.getString("annee_univ");
            etudiant = new Etudiant(matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ);
        }
    } catch (SQLException e) {
        printSQLException(e);
    }
    return etudiant;
}

// Affichage de la table etudiant
public List < Etudiant > selectAllEtudiants() {

    // using try-with-resources to avoid closing resources (boiler plate code)
    List < Etudiant > etudiants = new ArrayList < > ();
    // 1. Etablir la Connection
    try (Connection connection = getConnection();

        // 2. Create a statement via l'objet connection
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUS_ETUDIANTS);) {
        System.out.println(preparedStatement);
        // 3. Execute ou update query
        ResultSet rs = preparedStatement.executeQuery();

        // 4. ResultSet 
        while (rs.next()) {
            String matricule = rs.getString("matricule");
            String nom = rs.getString("nom");
            String sexe = rs.getString("sexe");
            Date datenais = rs.getDate("datenais");
            String institution = rs.getString("institution");
            String niveau = rs.getString("niveau");
            String mail = rs.getString("mail");
            String annee_univ = rs.getString("annee_univ");
            etudiants.add(new Etudiant(matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ));
        }
    } catch (SQLException e) {
        printSQLException(e);
    }
    return etudiants;
}

// Lister les niveaux
public List<String> getAllNiveaux() {
    List<String> niveaux = new ArrayList<>();
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUS_NIVEAUX)) {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            niveaux.add(rs.getString("niveau"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return niveaux;
}

// Les institutions
public List<String> getAllInstitutions() {
    List<String> institutions = new ArrayList<>();
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUS_INSTITUTIONS)) {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            institutions.add(rs.getString("institution"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return institutions;
}

// Filtre les étudiants par leur institution
public List<Etudiant> filterEtudiants(String niveau, String institution) {
    List<Etudiant> etudiants = new ArrayList<>();
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(FILTER_ETUDIANTS)) {
        preparedStatement.setString(1, niveau);
        preparedStatement.setString(2, niveau);
        preparedStatement.setString(3, institution);
        preparedStatement.setString(4, institution);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String matricule = rs.getString("matricule");
            String nom = rs.getString("nom");
            String sexe = rs.getString("sexe");
            Date datenais = rs.getDate("datenais");
            String institutionEtudiant = rs.getString("institution");
            String niveauEtudiant = rs.getString("niveau");
            String mail = rs.getString("mail");
            String annee_univ = rs.getString("annee_univ");
            etudiants.add(new Etudiant(matricule, nom, sexe, datenais, institutionEtudiant, niveauEtudiant, mail, annee_univ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return etudiants;
}

// Recherche dynamique des étudiants
public List<Etudiant> searchEtudiants(String searchTerm) throws SQLException {
    List<Etudiant> etudiants = new ArrayList<>();
    String sql = "SELECT * FROM etudiant WHERE LOWER(nom) LIKE LOWER(?) OR LOWER(matricule) LIKE LOWER(?) OR LOWER(sexe) LIKE LOWER(?) OR LOWER(datenais) LIKE LOWER(?) OR LOWER(mail) LIKE LOWER(?) OR LOWER(annee_univ) LIKE LOWER(?)";
    
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        
        String searchPattern = "%" + searchTerm + "%";
        for (int i = 1; i <= 6; i++) {
            preparedStatement.setString(i, searchPattern);
        }
        ResultSet rs = preparedStatement.executeQuery();
        
        while (rs.next()) {
            String matricule = rs.getString("matricule");
            String nom = rs.getString("nom");
            String sexe = rs.getString("sexe");
            Date datenais = rs.getDate("datenais");
            String institution = rs.getString("institution");
            String niveau = rs.getString("niveau");
            String mail = rs.getString("mail");
            String annee_univ = rs.getString("annee_univ");
            etudiants.add(new Etudiant(matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ));
        }
    } catch (SQLException e) {
        printSQLException(e);
    }
    return etudiants;
}

// Liste des étudiants moins de 18 ans
public List<Etudiant> getEtudiantsMineurs() {
    List<Etudiant> etudiantsMineurs = new ArrayList<>();
    
    // Calculer la date limite pour être considéré comme mineur (moins de 18 ans)
    LocalDate dateLimite = LocalDate.now().minusYears(18);
    
    // Convertir la date limite en java.sql.Date pour l'utiliser dans la requête SQL
    Date dateLimiteSQL = Date.valueOf(dateLimite);

    // Requête SQL pour sélectionner les étudiants mineurs
    String sql = "SELECT * FROM etudiant WHERE datenais > ?";
    
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        // Passer la date limite comme paramètre à la requête SQL
        preparedStatement.setDate(1, dateLimiteSQL);

        // Exécuter la requête et obtenir les résultats
        try (ResultSet rs = preparedStatement.executeQuery()) {
            // Parcourir les résultats et créer des objets Etudiant à partir des données récupérées
            while (rs.next()) {
                String matricule = rs.getString("matricule");
                String nom = rs.getString("nom");
                String sexe = rs.getString("sexe");
                Date datenais = rs.getDate("datenais");
                String institution = rs.getString("institution");
                String niveau = rs.getString("niveau");
                String mail = rs.getString("mail");
                String annee_univ = rs.getString("annee_univ");

                // Créer un objet Etudiant et l'ajouter à la liste des étudiants mineurs
                Etudiant etudiant = new Etudiant(matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ);
                etudiantsMineurs.add(etudiant);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer l'exception de manière appropriée dans votre application
    }
    
    return etudiantsMineurs;
}



// Effacer un etudiant
public boolean deleteEtudiant(String matricule ) throws SQLException {
    boolean rowDeleted;
    try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_ETUDIANTS_SQL);) {
        statement.setString(1, matricule);
        rowDeleted = statement.executeUpdate() > 0;
    }
    return rowDeleted;
}

// Modifier l'étudiant existant
public boolean updateEtudiant(Etudiant etudiant, String oldMatricule) throws SQLException {
    boolean rowUpdated;
    try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_ETUDIANTS_SQL);) {
        statement.setString(1, etudiant.getMatricule());
        statement.setString(2, etudiant.getNom());
        statement.setString(3, etudiant.getSexe());
        statement.setDate(4, etudiant.getDatenais());
        statement.setString(5, etudiant.getInstitution());
        statement.setString(6, etudiant.getNiveau());
        statement.setString(7, etudiant.getMail());
        statement.setString(8, etudiant.getAnnee_univ());
        statement.setString(9, oldMatricule); // Utiliser le  ancien matricule de l'objet Etudiant pour identifier l'étudiant à modifier

        rowUpdated = statement.executeUpdate() > 0;
    }
    return rowUpdated;
}


private void printSQLException(SQLException ex) {
	// TODO Auto-generated method stub
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

