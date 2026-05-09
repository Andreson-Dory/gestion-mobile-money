package com.mobilemoney.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.dao.FraisRecepDAO;
import com.mobilemoney.dao.RetraitDAO;
import com.mobilemoney.model.Client;
import com.mobilemoney.model.FraisRecep;
import com.mobilemoney.model.Retrait;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RetraitService {
	private ClientDAO clientDAO = new ClientDAO();
	private RetraitDAO retraitDAO = new RetraitDAO();
	private FraisRecepDAO fraisRecepDAO = new FraisRecepDAO(); 
	
	public void doRetrait(HttpServletRequest request, HttpServletResponse response, Retrait retrait) {
		String numtel = retrait.getNumtel();
		int montant = retrait.getMontant();
		Client client;
		FraisRecep fraisRecep;
		
		client = clientDAO.findByNumtel(numtel);
		int solde = client.getSolde();
			
		fraisRecep = fraisRecepDAO.findByMontant(montant);
		int frais = fraisRecep.getFraisRec();
			
		int totalMontantRetire = frais + montant;
		if(solde < totalMontantRetire) {
			return;
		}
		int newSolde = solde - totalMontantRetire;
		retrait.setMontant(totalMontantRetire);
		client.setSolde(newSolde);
		clientDAO.update(request, response, client);
		retraitDAO.insert(request, response, retrait);
	}
	
	public List<Retrait> getAllRetrait(){
		return retraitDAO.findAll();
	}
	
	public List<Retrait> searchRetrait(LocalDateTime value) {
		return retraitDAO.searchRetrait(value);
	}
	
	public void deleteRetrait(HttpServletRequest request, HttpServletResponse response, String idRecep) {
		retraitDAO.delete(request, response, idRecep);
	}
}
