package com.mobilemoney.model;

import java.time.LocalDateTime;

public class Envoi {

    private int idEnv;
    private String numEnvoyeur;
    private String numRecepteur;
    private int montant;
    private LocalDateTime date;
    private boolean payerFraisRetrait;
    private String raison;
    private String dateEnvoi;

	public Envoi(String numEnvoyeur, String numRecepteur,
                 int montant, LocalDateTime date,
                 boolean payerFraisRetrait, String raison
    		) {

        this.numEnvoyeur = numEnvoyeur;
        this.numRecepteur = numRecepteur;
        this.montant = montant;
        this.date = date;
        this.payerFraisRetrait = payerFraisRetrait;
        this.raison = raison;
    }


    public Envoi(int idEnv, String numEnvoyeur, String numRecepteur,
                 String montant, String date,
                 String payerFraisRetrait, String raison) {

        this.idEnv = idEnv;
        this.numEnvoyeur = numEnvoyeur;
        this.numRecepteur = numRecepteur;
        this.montant = Integer.parseInt(montant);
        this.date = LocalDateTime.parse(date);
        this.payerFraisRetrait = Boolean.parseBoolean(payerFraisRetrait);
        this.raison = raison;
    }

    public Envoi(int idEnv, String numEnvoyeur, String numRecepteur,
            int montant, LocalDateTime date,
            boolean payerFraisRetrait, String raison) {

		   this.idEnv = idEnv;
		   this.numEnvoyeur = numEnvoyeur;
		   this.numRecepteur = numRecepteur;
		   this.montant = montant;
		   this.date = date;
		   this.payerFraisRetrait = payerFraisRetrait;
		   this.raison = raison;
		}

    public int getIdEnv() {
		return idEnv;
	}

	public void setIdEnv(int idEnv) {
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

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }
    
    public String getDateEnvoi() {
  		return dateEnvoi;
  	}

  	public void setDateEnvoi(String dateEnvoi) {
  		this.dateEnvoi = dateEnvoi;
  	}

}