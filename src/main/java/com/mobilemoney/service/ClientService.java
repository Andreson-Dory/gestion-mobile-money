package com.mobilemoney.service;

import java.util.List;

import com.mobilemoney.dao.ClientDAO;
import com.mobilemoney.model.Client;

public class ClientService {
	private ClientDAO clientDAO = new ClientDAO();
	
	public void createClient(Client client) {
		clientDAO.insert(client);
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
	
	public void updateClient(Client client) {
		clientDAO.update(client);
	}
	
	public void deleteClient(String numtel) {
		clientDAO.delete(numtel);
	}
}
