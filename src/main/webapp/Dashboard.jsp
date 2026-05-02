<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="com.mobilemoney.dao.StatistiqueDAO" %>
	<%@ page import="java.util.List" %>
	<%@ page import="com.mobilemoney.dao.ActiviteDAO" %>
	<%@ page import="com.mobilemoney.model.Activite" %>

	<%
		StatistiqueDAO stat = new StatistiqueDAO();
		
		int total = stat.getRecetteTotale();
		int fraisEnvoi=stat.getRecetteFraisEnvoi();
		int nbEnvoi = stat.getNombreEnvois();
		int fraisRetrait=stat.getRecetteFraisRetrait();
		int nbRetrait =stat.getNombreRetraits();
	%>
	<div>
		<div>
			<h3>Recette Totale</h3>
			<p><%= total%> </p>
			<p>Frais collectés</p>
		</div>
		<div class="icons">
		
		</div>
	</div>
	<div>
		<div>
			<h3>Recette Frais de Retrait</h3>
			<p><%= fraisRetrait%> </p>
			<p><%= nbRetrait%> retraits</p>
		</div>
		<div class="icons">
		
		</div>
	</div>
	<div>
		<div>
			<h3>Recette Frais d'Envois</h3>
			<p><%= fraisEnvoi%> </p>
			<p><%= nbEnvoi%> envois</p>
		</div>
		<div class="icons">
		
		</div>
	</div>
	
	<%
    ActiviteDAO dao = new ActiviteDAO();
    List<Activite> activites = dao.getActivitesRecentes();
	%>
	
	<h2>Activité récente</h2>
	
	<% for(Activite a : activites){ %>
	    <div style="border-bottom:1px solid #ccc; padding:10px;">
	        <b>
	            <%= a.getType().equals("ENVOI") ? "Envoi vers " : "Retrait" %>
	        </b>
	        <%= a.getClient() %> <br>
	
	        <small><%= a.getDate() %></small>
	
	        <span style="float:right;">
	            <%= a.getMontant() %> F CFA
	        </span>
	    </div>
	<% } %>
</body>
</html>