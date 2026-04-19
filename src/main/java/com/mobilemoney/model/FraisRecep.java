package com.mobilemoney.model;

public class FraisRecep {

	private String idRec; 
	private int montant1;
	private int montant2; 
	private int fraisRec;
	
	public FraisRecep(String idRec, int montant1, int montant2, int frais_rec) {
		super();
		this.idRec = idRec;
		this.montant1 = montant1;
		this.montant2 = montant2;
		this.fraisRec = frais_rec;
	}

	public String getIdRec() {
		return idRec;
	}

	public void setIdRec(String idRec) {
		this.idRec = idRec;
	}

	public int getMontant1() {
		return montant1;
	}

	public void setMontant1(int montant1) {
		this.montant1 = montant1;
	}

	public int getMontant2() {
		return montant2;
	}

	public void setMontant2(int montant2) {
		this.montant2 = montant2;
	}

	public int getFrais_rec() {
		return fraisRec;
	}

	public void setFrais_rec(int frais_rec) {
		this.fraisRec = frais_rec;
	}
	
}
