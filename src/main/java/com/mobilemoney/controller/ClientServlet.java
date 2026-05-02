package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.mobilemoney.model.Client;
import com.mobilemoney.service.ClientService;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientService clientService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
    	this.clientService = new ClientService();
    }
    
    private boolean isInvalid(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		
		if (action == null) {
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    return;
		}
		
		try {
			switch (action) {
				case "/insert":
						insertClient(request, response);
					break;
		
				case "/update":
						updateClient(request, response);
					break;
					
				case "/delete":
						deleteClient(request, response);
					break;
				default:
		            response.sendRedirect(request.getContextPath() + "/client");
		            break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null || action.equals("/")) {
		    listClients(request, response);
		    return;
		}
		
		switch (action) {
			default:
				listClients(request, response);
				break;
		}
	}
	
	private void insertClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String a = request.getParameter("age");
		String mail = request.getParameter("mail");
		
		if (isInvalid(numtel, nom, sexe, a, mail)) {
			response.sendRedirect(request.getContextPath() + "/client");
		    return;
		}

		int age;
		try {
		    age = Integer.parseInt(a);
		} catch (NumberFormatException e) {
		    response.sendRedirect(request.getContextPath() + "/client");
		    return;
		}
		
		Client newClient = new Client(numtel, nom, sexe, age, mail);
		clientService.createClient(newClient);
		response.sendRedirect(request.getContextPath() + "/client");
	}
	
	private void listClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		List<Client> clients;
		
		if (search != null && !search.trim().isEmpty()) {
	        clients = clientService.searchClient(search);
	    } else {
	        clients = clientService.getAllClients();
	    }

		request.setAttribute("search", search);
		request.setAttribute("clients", clients);
		request.getRequestDispatcher("views/client/home-client.jsp").forward(request, response);
	}
	
	private void updateClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String a = request.getParameter("age");
		String s = request.getParameter("solde");
		String mail = request.getParameter("mail");
		
		if (isInvalid(numtel, nom, sexe, a, mail)) {
			response.sendRedirect(request.getContextPath() + "/client/new");
		    return;
		}

		int age;
		int solde;
		try {
			age = Integer.parseInt(a);
			solde = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/client/new");
			return;
		}
		
		Client updatedClient = new Client(numtel, nom, sexe, age, solde, mail);
		clientService.updateClient(updatedClient);
		response.sendRedirect(request.getContextPath() + "/client");
	}
	
	private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		
		if (isInvalid(numtel)) {
			response.sendRedirect(request.getContextPath() + "/client");
		    return;
		}
		
		clientService.deleteClient(numtel);
		response.sendRedirect(request.getContextPath() + "/client");
	}

}
