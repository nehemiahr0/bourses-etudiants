package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Payer {

	    private String idpaye;
	    private String matricule;
	    private String annee_univ;
	    private LocalDateTime date;
	    private int nbr_mois;
	    private String nom;
	    private Date datenais;
	    private String sexe;
	    private String institution;
	    private String niveau;
	
	    public String getNom() {
			return nom;
		}


		public void setNom(String nom) {
			this.nom = nom;
		}


		public Date getDatenais() {
			return datenais;
		}


		public void setDatenais(Date datenais) {
			this.datenais = datenais;
		}


		public String getSexe() {
			return sexe;
		}


		public void setSexe(String sexe) {
			this.sexe = sexe;
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
		
	
	public Payer(String idpaye, String matricule, String annee_univ, LocalDateTime date, int nbr_mois) {
		super();
		this.idpaye = idpaye;
		this.matricule = matricule;
		this.annee_univ = annee_univ;
		this.date = date;
		this.nbr_mois = nbr_mois;
	}
	
	
	public Payer(String idpaye, String matricule, String annee_univ, LocalDateTime date, int nbr_mois, String nom,
			Date datenais, String sexe, String institution, String niveau) {
		super();
		this.idpaye = idpaye;
		this.matricule = matricule;
		this.annee_univ = annee_univ;
		this.date = date;
		this.nbr_mois = nbr_mois;
		this.nom = nom;
		this.datenais = datenais;
		this.sexe = sexe;
		this.institution = institution;
		this.niveau = niveau;
	}


	public Payer(String matricule, String annee_univ, LocalDateTime date, int nbr_mois) {
		super();
		this.matricule = matricule;
		this.annee_univ = annee_univ;
		this.date = date;
		this.nbr_mois = nbr_mois;
	}


	public Payer() {
		
	}


	public String getIdpaye() {
		return idpaye;
	}
	public void setIdpaye(String idpaye) {
		this.idpaye = idpaye;
	}
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public String getAnnee_univ() {
		return annee_univ;
	}
	public void setAnnee_univ(String annee_univ) {
		this.annee_univ = annee_univ;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getNbr_mois() {
		return nbr_mois;
	}
	public void setNbr_mois(int nbr_mois) {
		this.nbr_mois = nbr_mois;
	}


	
	
	
}
