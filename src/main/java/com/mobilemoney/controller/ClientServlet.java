package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobilemoney.model.Client;
import com.mobilemoney.service.ClientService;
import com.mobilemoney.service.FraisEnvoiService;
import com.mobilemoney.service.FraisRecepService;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientService clientService;
    private FraisRecepService fraisRecepService;
    private FraisEnvoiService fraisEnvoiService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
    	this.clientService = new ClientService();
    	this.fraisEnvoiService = new FraisEnvoiService();
    	this.fraisRecepService = new FraisRecepService();
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
	
	private void generatePDF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=releve-client.pdf"
        );

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    response.getOutputStream()
            );

            document.open();

            // ===== TITRE =====

            Paragraph titre = new Paragraph(
                    "Date : Avril 2024",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)
            );

            titre.setAlignment(Element.ALIGN_CENTER);

            document.add(titre);

            document.add(new Paragraph(" "));

            // ===== INFOS CLIENT =====

            document.add(new Paragraph("Contact : 0324432167"));
            document.add(new Paragraph("RAKOTO Bernard"));
            document.add(new Paragraph("40 ans"));
            document.add(new Paragraph("Masculin"));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Solde actuel : 340.000 Ariary"
            ));

            document.add(new Paragraph(" "));

            // ===== TABLEAU =====

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);

            table.addCell("Date");
            table.addCell("Raison");
            table.addCell("Débit");
            table.addCell("Crédit");

            // ===== DONNEES =====

            table.addCell("01/04/2024");
            table.addCell("Jirama");
            table.addCell("50.000");
            table.addCell("");

            table.addCell("15/04/2024");
            table.addCell("Trosa Hery");
            table.addCell("");
            table.addCell("125.000");

            table.addCell("26/04/2024");
            table.addCell("Merci");
            table.addCell("15.000");
            table.addCell("");

            document.add(table);

            document.add(new Paragraph(" "));

            // ===== TOTAUX =====

            document.add(new Paragraph(
                    "Total Débit : 65.000 Ar"
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Total Crédit : 125.000 Ar"
            ));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void handleGeneratePDF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		
		
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
