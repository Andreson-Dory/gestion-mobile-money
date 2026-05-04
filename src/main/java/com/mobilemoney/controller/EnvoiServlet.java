package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.mobilemoney.model.Client;
import com.mobilemoney.model.Envoi;
import com.mobilemoney.service.ClientService;
import com.mobilemoney.service.EnvoiService;

@WebServlet("/envoi/*")
public class EnvoiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnvoiService envoiService;
    private ClientService clientService;

    public EnvoiServlet() {
        this.envoiService = new EnvoiService();
        this.clientService = new ClientService();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();

        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            switch (action) {
                case "/insert":
                    insertEnvoi(request, response);
                    break;
                case "/delete":
                    deleteEnvoi(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/envoi");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void deleteEnvoi(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idEnv = request.getParameter("idEnv");

        if (isInvalid(idEnv)) {
            response.sendRedirect(request.getContextPath() + "/envoi");
            return;
        }

        envoiService.deleteEnvoi(idEnv);
        response.sendRedirect(request.getContextPath() + "/envoi");
    }

    private void insertEnvoi(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String numEnvoyeur = request.getParameter("numEnvoyeur");
        String numRecepteur = request.getParameter("numRecepteur");
        String m = request.getParameter("montant");
        String payFraisRetraitStr = request.getParameter("payer_frais_retrait");
        String raison = request.getParameter("raison");
        LocalDateTime date = LocalDateTime.now();

        if (isInvalid(numEnvoyeur, numRecepteur, m, payFraisRetraitStr, raison)) {
            response.sendRedirect(request.getContextPath() + "/envoi");
            return;
        }

        int montant;
        boolean payFraisRetrait;

        try {
            montant = Integer.parseInt(m);
            payFraisRetrait = Boolean.parseBoolean(payFraisRetraitStr);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/envoi");
            return;
        }

        Envoi newEnvoi = new Envoi(
                numEnvoyeur,
                numRecepteur,
                montant,
                date,
                payFraisRetrait,
                raison
        );

        envoiService.createEnvoi(newEnvoi);

        response.sendRedirect(request.getContextPath() + "/envoi");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();

        if (action == null || action.equals("/")) {
            listEnvoi(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/envoi");
    }

    private void listEnvoi(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String date = request.getParameter("search");
        List<Envoi> envoi;
        List<Client> clients;

        if (date != null && !date.trim().isEmpty()) {
            envoi = envoiService.searchEnvoi(date);
            envoi.forEach(e-> {
	        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        	e.setDateEnvoi(e.getDate().format(formatter));
	        });
        } else {
            envoi = envoiService.getAllEnvoi();
            envoi.forEach(e-> {
	        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        	e.setDateEnvoi(e.getDate().format(formatter));
	        });
        }
        clients = clientService.getAllClients();

        request.setAttribute("search", date);
        request.setAttribute("envoi", envoi);
        request.setAttribute("clients", clients);

        request.getRequestDispatcher("views/envoi/home-envoi.jsp")
                .forward(request, response);
    }

    private boolean isInvalid(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}