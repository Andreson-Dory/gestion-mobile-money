package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.mobilemoney.model.Client;
import com.mobilemoney.model.Retrait;
import com.mobilemoney.service.ClientService;
import com.mobilemoney.service.RetraitService;

/**
 * Servlet implementation class RetraitServlet
 */
@WebServlet("/retrait/*")
public class RetraitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RetraitService retraitService;
	private ClientService clientService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetraitServlet() {
    	this.retraitService = new RetraitService();
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null || action.equals("/")) {
		    listRetraits(request, response);
		    return;
		}
		
		switch (action) {
			default:
				listRetraits(request, response);
				break;
		}
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
							insertRetrait(request, response);
						break;
						
					case "/delete":
							deleteRetrait(request, response);
						break;
					default:
			            response.sendRedirect(request.getContextPath() + "/retrait");
			            break;
				}
			} catch (Exception e) {
				throw new ServletException(e);
			}
	}
	
	private void listRetraits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		List<Retrait> retraits;
		List<Client> clients;
		LocalDateTime daterecep;
				
		if (search != null && !search.trim().isEmpty()) {
			try {
				LocalDate date = LocalDate.parse(search);
				daterecep = date.atStartOfDay();
			} catch (DateTimeParseException e) {
			    response.sendRedirect(request.getContextPath() + "/retrait");
			    return;
			}
	        retraits = retraitService.searchRetrait(daterecep);
	    } else {
	        retraits = retraitService.getAllRetrait();
	    }
		clients = clientService.getAllClients();
		
		request.setAttribute("search", search);
		request.setAttribute("retraits", retraits);
		request.setAttribute("clients", clients);
		request.getRequestDispatcher("views/retrait/home-retrait.jsp").forward(request, response);
	}
	
	private void insertRetrait(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		String m = request.getParameter("montant");
		
		if (isInvalid(numtel, m)) {
			response.sendRedirect(request.getContextPath() + "/retrait");
		    return;
		}

		int montant;
		try {
		    montant = Integer.parseInt(m);
		} catch (NumberFormatException e) {
		    response.sendRedirect(request.getContextPath() + "/retrait");
		    return;
		}
		
		Retrait newRetrait= new Retrait(numtel, montant);
		retraitService.doRetrait(newRetrait);
		response.sendRedirect(request.getContextPath() + "/retrait");
	}
	
	private void deleteRetrait(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRecep = request.getParameter("idRecep");
		
		if (isInvalid(idRecep)) {
			response.sendRedirect(request.getContextPath() + "/retrait");
		    return;
		}
		
		retraitService.deleteRetrait(idRecep);
		response.sendRedirect(request.getContextPath() + "/retrait");
	}
}
