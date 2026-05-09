package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobilemoney.model.Client;
import com.mobilemoney.model.Envoi;
import com.mobilemoney.service.ClientService;
import com.mobilemoney.service.EnvoiService;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientService clientService;
    private EnvoiService envoiService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
    	this.clientService = new ClientService();
    	this.envoiService = new EnvoiService();
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
					
				case "/pdf":
						handleGeneratePDF(request, response);
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
			request.getSession().setAttribute("error", "Veuillez completer tous les champs !");
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
		clientService.createClient(request, response, newClient);
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
		request.getRequestDispatcher("/views/client/home-client.jsp").forward(request, response);
	}
	
	private void updateClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String a = request.getParameter("age");
		String s = request.getParameter("solde");
		String mail = request.getParameter("mail");
		
		if (isInvalid(numtel, nom, sexe, a, mail)) {
			request.getSession().setAttribute("error", "Veuillez completer tous les champs !");
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
		clientService.updateClient(request, response, updatedClient);
		response.sendRedirect(request.getContextPath() + "/client");
	}
	
	private void generatePDF(HttpServletRequest request, HttpServletResponse response, Client client, List<Envoi> envois) throws ServletException, IOException {
		String date = request.getParameter("date");
        String month = date.split("-")[1];
        String year = date.split("-")[0];
        AtomicInteger credit = new AtomicInteger(0);
        AtomicInteger debit = new AtomicInteger(0);
        
        String[] Mois = {
        	"",
        	"Janvier", 
        	"Fevrier",
        	"Mars",
        	"Avril",
        	"Mai",
        	"Juin",
        	"Juillet",
        	"Août",
        	"Septembre",
        	"Octobre",
        	"Novembre",
        	"Decembre"
        };
        
        envois.forEach(envoi -> {
        	if(envoi.getNumEnvoyeur().equals(client.getNumtel())) {
        		debit.addAndGet(envoi.getMontant());
        	} else {
        		credit.addAndGet(envoi.getMontant());
        	}
        });
		
		response.setContentType("application/pdf");
		
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=releve-d'opération-" + Mois[Integer.valueOf(month)] + "-" + year + "-" + client.getNom()  + ".pdf"
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
                    "Date : " + Mois[Integer.valueOf(month)] + " " + year,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)
            );

            titre.setAlignment(Element.ALIGN_CENTER);

            document.add(titre);

            document.add(new Paragraph(" "));

            // ===== INFOS CLIENT =====

            document.add(new Paragraph("Contact : " + client.getNumtel()));
            document.add(new Paragraph(client.getNom()));
            document.add(new Paragraph(client.getAge() + " ans"));
            document.add(new Paragraph(client.getSexe()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Solde actuel : " + client.getSolde() +" Ariary"
            ));

            document.add(new Paragraph(" "));

            // ===== TABLEAU =====

            PdfPTable table = new PdfPTable(4);
            
            table.setWidthPercentage(100);

            table.addCell(cell("Date"));
            table.addCell(cell("Raison"));
            table.addCell(cell("Débit"));
            table.addCell(cell("Crédit"));

            // ===== DONNEES =====

            envois.forEach(envoi -> {
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	if(envoi.getNumEnvoyeur().equals(client.getNumtel())) {
            		table.addCell(cell(envoi.getDate().format(formatter)));
                    table.addCell(cell(envoi.getRaison()));
                    table.addCell(cell(String.valueOf(envoi.getMontant())));
                    table.addCell("");
                    
            	}else {
            		table.addCell(cell(envoi.getDate().format(formatter)));
                    table.addCell(cell(envoi.getRaison()));
                    table.addCell("");
                    table.addCell(cell(String.valueOf(envoi.getMontant())));
                    
            	}
            });

            document.add(table);

            document.add(new Paragraph(" "));

            // ===== TOTAUX =====

            document.add(new Paragraph(
                    "Total Débit : " + debit.get() + " Ar"
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Total Crédit : " + credit.get() + " Ar"
            ));

            document.close();
            
            request.getSession().setAttribute("success", "PDF généré !");
        } catch (Exception e) {
        	request.getSession().setAttribute("error", "Erreur lors de la génération du PDF !");
            e.printStackTrace();
        }
	}
	
	private void handleGeneratePDF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		String date = request.getParameter("date");
		Client client = clientService.getClientByNumtel(numtel);
		List<Envoi> envois = envoiService.getClientMonthlyEnvoi(numtel, date);
		
		generatePDF(request, response, client, envois);
		response.sendRedirect(request.getContextPath() + "/client");
	}
	
	private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numtel = request.getParameter("numtel");
		
		if (isInvalid(numtel)) {
			request.getSession().setAttribute("error", "Client non défini !");
			response.sendRedirect(request.getContextPath() + "/client");
		    return;
		}
		
		clientService.deleteClient(request, response, numtel);
		response.sendRedirect(request.getContextPath() + "/client");
	}
	
	private PdfPCell cell(String text) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPaddingLeft(8f);
	    return cell;
	}

}
