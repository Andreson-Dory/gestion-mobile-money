package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.EnvoiDAO;
import com.mobilemoney.model.Envoi;

public class EnvoiService {
	private EnvoiDAO envoiDAO = new EnvoiDAO();
	
	public void createEnvoi(Envoi envoi) {
		envoiDAO.insertEnvoi(envoi);
		String emailClient = envoiDAO.getEmailByNumero(envoi.getNumEnvoyeur());

	    EmailService emailService = new EmailService();

	    String message = "Bonjour,\n\n" +
	            "Votre envoi a été effectué avec succès.\n" +
	            "Montant : " + envoi.getMontant() + " Ar\n" +
	            "Frais : " + envoi.getFraisEnvoi() + " Ar\n\n" +
	            "Merci d'utiliser notre service.";

	    emailService.sendEmail(emailClient, "Confirmation Envoi", message);
	}
	
	public List<Envoi> getAllEnvoi() {
		return envoiDAO.findAllEnvoi();
	}
	

	public List<Envoi> searchEnvoi(String date) {
		return envoiDAO.searchByDate(date);
	}
	
	public void deleteEnvoi(String numtel) {
		envoiDAO.deleteEnvoi(numtel);
	}

	
	
}
