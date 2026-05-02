package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.FraisRecepDAO;
import com.mobilemoney.model.FraisRecep;


public class FraisRecepService {
	private FraisRecepDAO fraisRecepDAO = new FraisRecepDAO();
	
	public void createFraisRecep(FraisRecep fraisRecep) {
		fraisRecepDAO.insert(fraisRecep);
	}
		
	public List<FraisRecep> getAllFraisRecep() {
		return fraisRecepDAO.findAll();
	}
			
	public void updateFraisRecep(FraisRecep fraisRecep) {
		fraisRecepDAO.update(fraisRecep);
	}
		
	public void deleteFraisRecep(int idRec) {
		fraisRecepDAO.delete(idRec);
	}
}
