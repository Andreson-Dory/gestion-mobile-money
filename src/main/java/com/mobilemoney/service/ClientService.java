package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.model.Client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientService {
	private ClientDAO clientDAO = new ClientDAO();
	
	public void createClient(HttpServletRequest request, HttpServletResponse response, Client client) {
		clientDAO.insert(request, response, client);
	}
	
	public List<Client> getAllClients() {
		return clientDAO.findAll();
	}
	
	public Client getClientByNumtel(String numtel) {
		return clientDAO.findByNumtel(numtel);
	}
	
	public List<Client> searchClient(String value) {
		return clientDAO.searchClients(value);
	}
	
	public void updateClient(HttpServletRequest request, HttpServletResponse response, Client client) {
		clientDAO.update(request, response, client);
	}
	
	public void deleteClient(HttpServletRequest request, HttpServletResponse response, String numtel) {
		clientDAO.delete(request, response, numtel);
	}
}
