package com.mobilemoney.model;

import java.time.LocalDateTime;

public class Retrait {

	private String idrecep;
	private String numtel;
	private int montant;
	private LocalDateTime daterecep;
	private String nom;
		
	public Retrait(String idrecep, String numtel, int montant, LocalDateTime daterecep) {
		super();
		this.idrecep = idrecep;
		this.numtel = numtel;
		this.montant = montant;
		this.daterecep = daterecep;
	}

	public Retrait(String idrecep, String numtel, int montant, LocalDateTime daterecep, String nom) {
		super();
		this.idrecep = idrecep;
		this.numtel = numtel;
		this.montant = montant;
		this.daterecep = daterecep;
		this.nom = nom;
	}

	public Retrait(String numtel, int montant) {
		super();
		this.numtel = numtel;
		this.montant = montant;
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
	
	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public LocalDateTime getDaterecep() {
		return daterecep;
	}

	public void setDaterecep(LocalDateTime daterecep) {
		this.daterecep = daterecep;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
