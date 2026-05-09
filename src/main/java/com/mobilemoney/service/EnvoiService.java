package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.EnvoiDAO;
import com.mobilemoney.dao.FraisEnvoiDAO;
import com.mobilemoney.dao.FraisRecepDAO;
import com.mobilemoney.model.Client;
import com.mobilemoney.model.Envoi;
import com.mobilemoney.model.FraisEnvoi;
import com.mobilemoney.model.FraisRecep;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EnvoiService {
	private EnvoiDAO envoiDAO = new EnvoiDAO();
	private FraisEnvoiDAO fraisEnvoiDAO = new FraisEnvoiDAO();
	private FraisRecepDAO fraisRecepDAO = new FraisRecepDAO(); 
	private ClientDAO clientDAO = new ClientDAO();
	
	public void createEnvoi(HttpServletRequest request, HttpServletResponse response, Envoi envoi) {
		String numtelEnv = envoi.getNumEnvoyeur();
		String numtelRec = envoi.getNumRecepteur();
		boolean payerFR = envoi.isPayerFraisRetrait();
		int montant = envoi.getMontant();
		int fe = 0;
		int fr = 0;
		int montantTotal;
		Client Envoyeur;
		Client Recepteur;
		FraisEnvoi fraisEnvoi;
		FraisRecep fraisRecep;
		
		Envoyeur = clientDAO.findByNumtel(numtelEnv);
		int soldeEnv = Envoyeur.getSolde();
		String emailEnv = Envoyeur.getMail();
		Recepteur = clientDAO.findByNumtel(numtelRec);
		String emailRec = Recepteur.getMail();
		int soldeRec = Recepteur.getSolde();
		
		fraisEnvoi = fraisEnvoiDAO.findByMontant(montant);
		if(fraisEnvoi != null) {
			fe = fraisEnvoi.getFraisEnv();
		}else {
			fe = 5000;
		}
		
		
		if(payerFR) {
			fraisRecep = fraisRecepDAO.findByMontant(montant);
			if(fraisRecep != null) {
				fr = fraisRecep.getFraisRec();
			}else {
				fr = 5000;
			}
		}
		
		montantTotal = montant + fe + fr;
		int totalFrais = fe + fr;
		if(soldeEnv < montantTotal) return;
		
		int newSoldeEnv = soldeEnv - montantTotal;
		int newSoldeRec = soldeRec + montantTotal;
		Envoyeur.setSolde(newSoldeEnv);
		Recepteur.setSolde(newSoldeRec);
		clientDAO.update(request, response, Envoyeur);
		clientDAO.update(request, response, Recepteur);
		
		envoi.setMontant(montantTotal);
		envoiDAO.insertEnvoi(request, response, envoi);

	    EmailService emailService = new EmailService();

	    String messageEnvoyeur = "Bonjour " + Envoyeur.getNom() + ",\n\n"
	            + "Nous vous informons qu'un transfert Mobile Money a été effectué avec succès.\n\n"
	            
	            + "==============================\n"
	            + " DÉTAILS DE LA TRANSACTION\n"
	            + "==============================\n"
	            	            
	            + "Recepteur : " + Recepteur.getNom() + "\n"
	            + "Numéro recepteur : " + Recepteur.getNumtel() + "\n\n"
	            
	            + "Montant envoyé : " + envoi.getMontant() + " Ar\n"
	            + "Frais de transaction déjà inclus au montant : " + totalFrais + " Ar\n"
	            + "Raison : " + envoi.getRaison() + "\n"
	            + "Date de l'envoi : " + envoi.getDate() + "\n\n"
	            
				+ "Votre solde actuel : " + Envoyeur.getSolde() + " Ar\n\n"
	            
	            + "Votre opération a été enregistrée avec succès.\n"
	            + "Merci d'utiliser notre service Mobile Money.\n\n"
	            
	            + "Cordialement,\n"
	            + "L'équipe MoneyFlow";
	    
	    String messageRecepteur = "Bonjour " + Recepteur.getNom() + ",\n\n"
	            + "Vous avez reçu un transfert Mobile Money avec succès.\n\n"
	            
	            + "==============================\n"
	            + " DÉTAILS DE LA RÉCEPTION\n"
	            + "==============================\n"
	            
	            + "Envoyeur : " + Envoyeur.getNom() + "\n"
	            + "Numéro envoyeur : " + Envoyeur.getNumtel() + "\n\n"
	            
	            + "Montant reçu : " + envoi.getMontant() + " Ar\n"
	            + "Raison : " + envoi.getRaison() + "\n"
	            + "Date de réception : " + envoi.getDate() + "\n\n"
	            
	            + "Votre solde actuel : " + Recepteur.getSolde() + " Ar\n\n"
	            
	            + "Le montant a bien été ajouté à votre compte.\n"
	            + "Merci d'utiliser notre service Mobile Money.\n\n"
	            
	            + "Cordialement,\n"
	            + "L'équipe MoneyFlow";

	    emailService.sendEmail(emailEnv, "Confirmation de transfert d'argent", messageEnvoyeur);
	    emailService.sendEmail(emailRec, "Notification de réception d'argent", messageRecepteur);
	    
	}
	
	public List<Envoi> getAllEnvoi() {
		return envoiDAO.findAllEnvoi();
	}
	
	public List<Envoi> getClientMonthlyEnvoi(String numtel, String date) {
		return envoiDAO.findClientMonthlyEnvoi(numtel, date);
	}

	public List<Envoi> searchEnvoi(String date) {
		return envoiDAO.searchByDate(date);
	}
	
	public void deleteEnvoi(HttpServletRequest request, HttpServletResponse response, String numtel) {
		envoiDAO.deleteEnvoi(request, response, numtel);
	}

	
	
}
