package model;
import java.sql.Date;

public class Etudiant {
	private String matricule;
	private String nom;
	private String sexe;
	private Date datenais;
	private String institution;
	private String niveau;
	private String mail;
	private String annee_univ;

	public Etudiant(String matricule, String nom, String sexe, Date datenais, String institution, String niveau,
			String mail, String annee_univ) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.sexe = sexe;
		this.datenais = datenais;
		this.institution = institution;
		this.niveau = niveau;
		this.mail = mail;
		this.annee_univ = annee_univ;
	}
	
	public Etudiant(String nom, String sexe, Date datenais, String institution, String niveau, String mail,
			String annee_univ) {
		super();
		this.nom = nom;
		this.sexe = sexe;
		this.datenais = datenais;
		this.institution = institution;
		this.niveau = niveau;
		this.mail = mail;
		this.annee_univ = annee_univ;
	}

	public Etudiant() {
		
	}

	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public Date getDatenais() {
		return datenais;
	}
	public void setDatenais(Date datenais) {
		this.datenais = datenais;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAnnee_univ() {
		return annee_univ;
	}
	public void setAnnee_univ(String annee_univ) {
		this.annee_univ = annee_univ;
	}
			
}
