package com.mobilemoney.model;

public class Activite {

	private String type;
	private String client;
	private int montant;
	private String date;
	
	public Activite (String type,String client,int montant,String date) {
			this.type=type;
			this.client=client;
			this.montant=montant;
			this.date=date;
	}
	
	public String getType() {
		return type;
	}

	public String getClient() {
		return client;
	}

	public int getMontant() {
		return montant;
	}

	public String getDate() {
		return date;
	}

	
	
}
