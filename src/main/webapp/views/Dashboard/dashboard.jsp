<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.mobilemoney.dao.StatistiqueDAO" %>
<%@ page import="com.mobilemoney.dao.ActiviteDAO" %>
<%@ page import="com.mobilemoney.model.Activite" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">

<style>
/* Cartes */
.card-box {
    border-radius: 12px;
    padding: 20px;
    background: white;
    box-shadow: 0px 2px 6px rgba(0,0,0,0.1);
}

.icon-box {
    width: 40px;
    height: 40px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.green { background-color: #d4edda; }
.blue { background-color: #d1ecf1; }
.orange { background-color: #ffe5d0; }

.activity-item {
    border-bottom: 1px solid #eee;
    padding: 10px 0;
}
</style>
</head>
<body>
<div class="d-flex vh-100">

	<%@ include file="/components/sidebar.jsp" %>
	<%
	    StatistiqueDAO stat = new StatistiqueDAO();
	
	    int total = stat.getRecetteTotale();
	    int fraisEnvoi = stat.getRecetteFraisEnvoi();
	    int nbEnvoi = stat.getNombreEnvois();
	    int fraisRetrait = stat.getRecetteFraisRetrait();
	    int nbRetrait = stat.getNombreRetraits();
	
	    ActiviteDAO dao = new ActiviteDAO();
	    List<Activite> activites = dao.getActivitesRecentes();
	%>
	
	<div class="flex-grow-1">
		<h2 class="my-4 mx-3 fw-bold">Tableau de Bord</h2>
		
		<div class="row g-3 my-4 mx-3">
		
		    <!-- Recette Totale -->
		    <div class="col-md-4">
		        <div class="card-box d-flex justify-content-between">
		            <div>
		                <h6>Recette Totale</h6>
		                <h4><%= total %> Ar</h4>
		                <small>Frais collectés</small>
		            </div>
		            <div class="icon-box green">$</div>
		        </div>
		    </div>
		
		    <!-- Retrait -->
		    <div class="col-md-4">
		        <div class="card-box d-flex justify-content-between">
		            <div>
		                <h6>Recette Frais de Retrait</h6>
		                <h4><%= fraisRetrait %> Ar</h4>
		                <small><%= nbRetrait %> retraits</small>
		            </div>
		            <div class="icon-box blue">$</div>
		        </div>
		    </div>
		
		    <!-- Envoi -->
		    <div class="col-md-4">
		        <div class="card-box d-flex justify-content-between">
		            <div>
		                <h6>Recette Frais d'Envois</h6>
		                <h4><%= fraisEnvoi %> Ar</h4>
		                <small><%= nbEnvoi %> envois</small>
		            </div>
		            <div class="icon-box orange">$</div>
		        </div>
		    </div>
		
		</div>
		
		<!-- ACTIVITÉ -->
		<div class="card-box my-4 mx-3">
		    <h5>Activité récente</h5>
		
		    <% for(Activite a : activites){ %>
		        <div class="activity-item d-flex justify-content-between">
		
		            <div>
		                <b>
		                    <%= a.getType().equals("ENVOI") ? "Envoi vers " + a.getClient() : "Retrait par " + a.getClient() %>
		                </b>
		                <br>
		                <small><%= a.getDate() %></small>
		            </div>
		
		            <div>
		                <b><%= a.getMontant() %> Ar</b>
		            </div>
		
		        </div>
		    <% } %>
		
		</div>
	</div>
</div>
</body>
</html>