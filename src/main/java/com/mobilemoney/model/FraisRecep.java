package com.mobilemoney.model;

public class FraisRecep {

	private String idRec; 
	private int montant1;
	private int montant2; 
	private int fraisRec;
	
	public FraisRecep(String idRec, int montant1, int montant2, int fraisRec) {
		super();
		this.idRec = idRec;
		this.montant1 = montant1;
		this.montant2 = montant2;
		this.fraisRec = fraisRec;
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

	public int getFraisRec() {
		return fraisRec;
	}

	public void setFraisRec(int fraisRec) {
		this.fraisRec = fraisRec;
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
	
}
