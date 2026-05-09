package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.FraisEnvoiDAO;
import com.mobilemoney.model.FraisEnvoi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FraisEnvoiService {

	private FraisEnvoiDAO fraisEnvoiDAO = new FraisEnvoiDAO();
		
	public void createFraisEnvoi(HttpServletRequest request, HttpServletResponse response, FraisEnvoi fraisEnvoi) {
		fraisEnvoiDAO.insert(request, response, fraisEnvoi);
	}
		
	public List<FraisEnvoi> getAllFraisEnvoi() {
		return fraisEnvoiDAO.findAll();
	}
			
	public void updateFraisEnvoi(HttpServletRequest request, HttpServletResponse response, FraisEnvoi fraisRecep) {
		fraisEnvoiDAO.update(request, response, fraisRecep);
	}
		
	public void deleteFraisEnvoi(HttpServletRequest request, HttpServletResponse response, int idEnv) {
		fraisEnvoiDAO.delete(request, response, idEnv);
	}
	
}
