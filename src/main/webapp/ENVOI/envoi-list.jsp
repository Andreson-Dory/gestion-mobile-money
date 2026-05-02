<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List-envoi</title>
</head>
<body>
	<table>
	   <thead>
	   		<tr>
	   			<th>Date</th>
	   			<th>Client</th>
	   			<th>Bénéficiaire</th>
	   			<th>Montant</th>
	   			<th>Frais d'Envoi</th>
	   			<th>Payer Frais Retrait</th>
	   			<th>Action</th>
	   		</tr>
	   </thead>
	   <tbody>
	  		<!--  -->
	   		<c:forEach var="Envoi" items="${listEnvoi }">
		   		<tr>
		   		  <td><c:out value="${Envoi.date}"/></td>
		   		  <td><c:out value="${Envoi.numEnvoyeur}"/></td>
		   		  <td><c:out value="${Envoi.numRecepteur}"/></td>
		   		  <td><c:out value="${Envoi.montant}"/></td>
		   		  <td><c:out value="${Envoi.date}"/></td>
		   		  <td><c:out value="${Envoi.payer_frais_retrait}"/></td>
		   		  <td><a href="delete?idEnv=<c:out value='${Envoi.idEnv }'/>">Delete</a></td>
		   		</tr>
	   		</c:forEach>
	   </tbody>
	</table>
</body>
</html>