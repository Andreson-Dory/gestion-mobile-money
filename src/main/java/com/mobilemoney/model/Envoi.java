package com.mobilemoney.model;

import java.time.LocalDateTime;

public class Envoi {

	private String idEnv;
	private String numEnvoyeur;
	private String numRecepteur;
	private int montant;
	private LocalDateTime date;
	private boolean payerFraisRetrait;
	private String raison;

	public Envoi(String idEnv, String numEnvoyeur, String numRecepteur, int montant, LocalDateTime date,
			boolean payerFraisRetrait, String raison) {
		super();
		this.idEnv = idEnv;
		this.numEnvoyeur = numEnvoyeur;
		this.numRecepteur = numRecepteur;
		this.montant = montant;
		this.date = date;
		this.payerFraisRetrait = payerFraisRetrait;
		this.raison = raison;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public boolean isPayerFraisRetrait() {
		return payerFraisRetrait;
	}

	public void setPayerFraisRetrait(boolean payerFraisRetrait) {
		this.payerFraisRetrait = payerFraisRetrait;
	}

	public String getIdEnv() {
		return idEnv;
	}

	public void setIdEnv(String idEnv) {
		this.idEnv = idEnv;
	}

	public String getNumEnvoyeur() {
		return numEnvoyeur;
	}

	public void setNumEnvoyeur(String numEnvoyeur) {
		this.numEnvoyeur = numEnvoyeur;
	}

	public String getNumRecepteur() {
		return numRecepteur;
	}

	public void setNumRecepteur(String numRecepteur) {
		this.numRecepteur = numRecepteur;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}
	
}
