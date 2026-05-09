package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.mobilemoney.model.FraisEnvoi;
import com.mobilemoney.model.FraisRecep;
import com.mobilemoney.service.FraisEnvoiService;
import com.mobilemoney.service.FraisRecepService;

/**
 * Servlet implementation class FraisServlet
 */
@WebServlet("/frais/*")
public class FraisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FraisEnvoiService fraisEnvoiService;   
	private FraisRecepService fraisRecepService;   

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FraisServlet() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

		if (action == null || action.equals("/")) {
		    listFrais(request, response);
		    return;
		}
		
		switch (action) {			
			default:
				listFrais(request, response);
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
						insertFrais(request, response);
					break;
		
				case "/update":
						updateFrais(request, response);
					break;
					
				case "/delete":
						deleteFrais(request, response);
					break;
				default:
		            response.sendRedirect(request.getContextPath() + "/frais");
		            break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void listFrais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String frais = request.getParameter("f");
		List<FraisEnvoi> fraisEnvois;
		List<FraisRecep> fraisReceps;
		
		 if (frais == null || frais.trim().isEmpty()) {
			 frais = "envoi";
		 }
		 request.setAttribute("f", frais);

		if (frais != null && !frais.trim().isEmpty() && "envoi".equals(frais) ) {
			fraisEnvois = fraisEnvoiService.getAllFraisEnvoi();
	        request.setAttribute("frais", fraisEnvois);
	    } else {
	    	fraisReceps = fraisRecepService.getAllFraisRecep();
	    	request.setAttribute("frais", fraisReceps);
	    }
		
		request.getRequestDispatcher("views/frais/home-frais.jsp").forward(request, response);
	}
	
	private void insertFrais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String frais = request.getParameter("f");
		String m1 = request.getParameter("montant1");
		String m2 = request.getParameter("montant2");
		String f;
		
		if("envoi".equals(frais)) {
			f = request.getParameter("frais_env");
		} else {
			f = request.getParameter("frais_rec");
		}
				
		if (isInvalid(m1, m2, f, frais)) {
	        request.getSession().setAttribute("error", "Veuillez completer tous les champs !");
			response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
		    return;
		}

		int montant1;
		int montant2;
		int fr;
		try {
		    montant1 = Integer.parseInt(m1);
		    montant2 = Integer.parseInt(m2);
		    fr = Integer.parseInt(f);
		} catch (NumberFormatException e) {
		    response.sendRedirect(request.getPathInfo() + "/frais?f=" + frais);
		    return;
		}
		
		if("envoi".equals(frais)) {
			FraisEnvoi newFraisEnvoi= new FraisEnvoi(montant1, montant2, fr);
			fraisEnvoiService.createFraisEnvoi(request, response, newFraisEnvoi);
		} else {
			FraisRecep newFraisRecep= new FraisRecep(montant1, montant2, fr);
			fraisRecepService.createFraisRecep(request, response, newFraisRecep);
		}
		
		response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
	}
	
	private void updateFrais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String frais = request.getParameter("f");
		String m1 = request.getParameter("montant1");
		String m2 = request.getParameter("montant2");
		String i;
		String f;
		
		if("envoi".equals(frais)) {
			i = request.getParameter("idEnv");
			f = request.getParameter("frais_env");
		} else {
			i = request.getParameter("idRec");
			f = request.getParameter("frais_rec");
		}
				
		if (isInvalid(m1, m2, i, f, frais)) {
			request.getSession().setAttribute("error", "Veuillez completer tous les champs !");
			response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
		    return;
		}

		int montant1;
		int montant2;
		int id;
		int fr;
		try {
		    montant1 = Integer.parseInt(m1);
		    montant2 = Integer.parseInt(m2);
		    id = Integer.parseInt(i);
		    fr = Integer.parseInt(f);
		} catch (NumberFormatException e) {
		    response.sendRedirect(request.getPathInfo() + "/frais?f=" + frais);
		    return;
		}
		
		if("envoi".equals(frais)) {
			FraisEnvoi newFraisEnvoi= new FraisEnvoi(id, montant1, montant2, fr);
			fraisEnvoiService.updateFraisEnvoi(request, response, newFraisEnvoi);
		} else {
			FraisRecep newFraisRecep= new FraisRecep(id, montant1, montant2, fr);
			fraisRecepService.updateFraisRecep(request, response, newFraisRecep);
		}
		
		response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
	}
	
	private void deleteFrais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String frais = request.getParameter("f");
		String i;
		
		if("envoi".equals(frais)) {
			i = request.getParameter("idEnv");
		} else {
			i = request.getParameter("idRec");
		}
		
		if (isInvalid(i)) {
			request.getSession().setAttribute("error", "Frais non défini !");
			response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
		    return;
		}
		
		int id;
		try {
		    id = Integer.parseInt(i);
		} catch (NumberFormatException e) {
		    response.sendRedirect(request.getPathInfo() + "/frais?f=" + frais);
		    return;
		}
		
		if("envoi".equals(frais)) {
			fraisEnvoiService.deleteFraisEnvoi(request, response, id);
		} else {
			fraisRecepService.deleteFraisRecep(request, response, id);
		}
		response.sendRedirect(request.getContextPath() + "/frais?f=" + frais);
	}

}
