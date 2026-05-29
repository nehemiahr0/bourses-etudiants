package model;

public class Montant {

    private String idniv;
    private String niveau;
    private int montant; 
    private int equipement;

    public Montant(int equipement, int montant) {
		super();
		this.montant = montant;
		this.equipement = equipement;
	}
    

	public Montant(String idniv, String niveau, int montant, int equipement) {
		super();
		this.idniv = idniv;
		this.niveau = niveau;
		this.montant = montant;
		this.equipement = equipement;
	}


	public String getIdniv() {
        return idniv;
    }

    public void setIdniv(String idniv) {
        this.idniv = idniv;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getEquipement() {
        return equipement;
    }

    public void setEquipement(int equipement) {
        this.equipement = equipement;
    }
}
