package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.FraisEnvoiDAO;
import com.mobilemoney.model.FraisEnvoi;


public class FraisEnvoiService {

	private FraisEnvoiDAO fraisEnvoiDAO = new FraisEnvoiDAO();
		
	public void createFraisEnvoi(FraisEnvoi fraisEnvoi) {
		fraisEnvoiDAO.insert(fraisEnvoi);
	}
		
	public List<FraisEnvoi> getAllFraisEnvoi() {
		return fraisEnvoiDAO.findAll();
	}
			
	public void updateFraisEnvoi(FraisEnvoi fraisRecep) {
		fraisEnvoiDAO.update(fraisRecep);
	}
		
	public void deleteFraisEnvoi(int idEnv) {
		fraisEnvoiDAO.delete(idEnv);
	}
	
}
