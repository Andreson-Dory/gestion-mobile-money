package com.mobilemoney.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.mobilemoney.model.Envoi;
import com.mobilemoney.service.EnvoiService;

@WebServlet("/envoi/*")
public class EnvoiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EnvoiService envoiService;

    public EnvoiServlet() {
        this.envoiService = new EnvoiService();
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

        String idEnvoi = request.getParameter("idEnv");
        String numEnvoyeur = request.getParameter("numEnvoyeur");
        String numRecepteur = request.getParameter("numRecepteur");
        String m = request.getParameter("montant");
        String dateStr = request.getParameter("date");
        String payFraisRetraitStr = request.getParameter("payer_frais_retrait");
        String raison = request.getParameter("raison");
        String fraisStr = request.getParameter("fraisEnvoi");

        if (isInvalid(idEnvoi, numEnvoyeur, numRecepteur, m, dateStr, payFraisRetraitStr, raison, fraisStr)) {
            response.sendRedirect(request.getContextPath() + "/envoi");
            return;
        }

        int montant;
        double fraisEnvoi;
        boolean payFraisRetrait;
        LocalDateTime date;

        try {
            montant = Integer.parseInt(m);
            fraisEnvoi = Double.parseDouble(fraisStr);
            payFraisRetrait = Boolean.parseBoolean(payFraisRetraitStr);
            date = LocalDateTime.parse(dateStr); // format ISO obligatoire
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/envoi");
            return;
        }

        Envoi newEnvoi = new Envoi(
                idEnvoi,
                numEnvoyeur,
                numRecepteur,
                montant,
                date,
                payFraisRetrait,
                raison,
                fraisEnvoi
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

        if (date != null && !date.trim().isEmpty()) {
            envoi = envoiService.searchEnvoi(date);
        } else {
            envoi = envoiService.getAllEnvoi();
        }

        request.setAttribute("search", date);
        request.setAttribute("envoi", envoi);

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