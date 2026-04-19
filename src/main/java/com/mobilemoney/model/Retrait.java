package com.mobilemoney.model;

import java.time.LocalDateTime;

public class Retrait {

	private String idrecep;
	private String numtel;
	private String montant;
	private LocalDateTime daterecep;
	
	public Retrait(String idrecep, String numtel, String montant, LocalDateTime daterecep) {
		super();
		this.idrecep = idrecep;
		this.numtel = numtel;
		this.montant = montant;
		this.daterecep = daterecep;
	}

	public String getIdrecep() {
		return idrecep;
	}

	public void setIdrecep(String idrecep) {
		this.idrecep = idrecep;
	}

	public String getNumtel() {
		return numtel;
	}

	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}

	public LocalDateTime getDaterecep() {
		return daterecep;
	}

	public void setDaterecep(LocalDateTime daterecep) {
		this.daterecep = daterecep;
	}
	
}
