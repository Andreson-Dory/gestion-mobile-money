package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.FraisRecepDAO;
import com.mobilemoney.model.FraisRecep;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FraisRecepService {
	private FraisRecepDAO fraisRecepDAO = new FraisRecepDAO();
	
	public void createFraisRecep(HttpServletRequest request, HttpServletResponse response, FraisRecep fraisRecep) {
		fraisRecepDAO.insert(request, response, fraisRecep);
	}
		
	public List<FraisRecep> getAllFraisRecep() {
		return fraisRecepDAO.findAll();
	}
			
	public void updateFraisRecep(HttpServletRequest request, HttpServletResponse response, FraisRecep fraisRecep) {
		fraisRecepDAO.update(request, response, fraisRecep);
	}
		
	public void deleteFraisRecep(HttpServletRequest request, HttpServletResponse response, int idRec) {
		fraisRecepDAO.delete(request, response, idRec);
	}
}
