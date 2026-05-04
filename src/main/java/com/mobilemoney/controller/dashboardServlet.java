package com.mobilemoney.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mobilemoney.model.Activite;
import com.mobilemoney.model.Statistique;
import com.mobilemoney.service.dashboardService;

@WebServlet("/")
public class dashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private dashboardService service = new dashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Statistique stat = service.getStatistiques();
        List<Activite> activites = service.getActivitesRecentes();

        request.setAttribute("stat", stat);
        request.setAttribute("activites", activites);

        request.getRequestDispatcher("views/Dashboard/dashboard.jsp").forward(request, response);
    }
}