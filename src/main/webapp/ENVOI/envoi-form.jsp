<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Envoi</title>
</head>
<body>
	<header>
		<h2>Ajouter Envoi</h2>
	</header>
	<div class="container">
	  
		<form action="insert" method="post">
		
			<fieldset class="form-group">
			<label>Client</label>
			<select name="Séléctionner un client">
				<option value="<c:out value='${Envoi.numEnvoyeur }'/>"></option>
			</select>
			</fieldset>
			<fieldset class="form-group">
				<label>Montant</label>
				<input
				  type="number"
				  name=""
				  value="<c:out value='${Envoi.montant }' />"
				  class="form-control"
				  placeholder="Montant"
				  required="required"
				/>
			</fieldset>
			<fieldset class="form-group">
				<label>Bénéficiaire</label>
				<input
				  type="text"
				  name=""
				  value="<c:out value='${Envoi.numRecepteur}' />"
				  class="form-control"
				  placeholder="Nom du bénéficiaire"
				  required="required"
				/>
			</fieldset>
			<fieldset class="form-group">
				<label>Motif</label>
				<input
				  type="text"
				  name=""
				  value="<c:out value='${Envoi.raison }' />"
				  class="form-control"
				  placeholder="Motif de l'envoi"
				  required="required"
				/>
			</fieldset>
		
		<p>Payer frais de retrait</p>
		
		<button type="submit" class="btn btn-success">Créer</button>
		</form>
	</div>
	
</body>
</html>