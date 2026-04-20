package com.mobilemoney.model;

public class FraisEnvoi {

	private String idEnv;
	private int montant1;
	private int montant2; 
	private int fraisEnv;
	
	public FraisEnvoi(String idEnv, int montant1, int montant2, int fraisEnv) {
		super();
		this.idEnv = idEnv;
		this.montant1 = montant1;
		this.montant2 = montant2;
		this.fraisEnv = fraisEnv;
	}

	public String getIdEnv() {
		return idEnv;
	}

	public void setIdEnv(String idEnv) {
		this.idEnv = idEnv;
	}

	public int getMontant1() {
		return montant1;
	}

	public void setMontant1(int montant1) {
		this.montant1 = montant1;
	}

	public int getFraisEnv() {
		return fraisEnv;
	}

	public void setFraisEnv(int fraisEnv) {
		this.fraisEnv = fraisEnv;
	}

	public int getMontant2() {
		return montant2;
	}

	public void setMontant2(int montant2) {
		this.montant2 = montant2;
	}
}
