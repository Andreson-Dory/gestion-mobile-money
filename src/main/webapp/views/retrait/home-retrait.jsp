<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Gestion des retraits</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link href="${pageContext.request.contextPath}/assets/css/select2.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
</head>
<body>

	<div class="d-flex vh-100">
		
		<%@ include file="/components/sidebar.jsp" %>
		
		<div class="flex-grow-1">
			<header class="my-4 mx-3 d-flex "> 
				<div class="flex-grow-1">
					<h2 class="fw-bold">Gestion des Retraits</h2>
					<p>Gérer les retraits d'argent</p>
				</div>
				<div class="align-self-center">
					<button type="button"
				        class="btn btn-add d-inline-flex align-items-center gap-2 fw-semibold shadow-sm"
				        data-bs-toggle="modal"
				        data-bs-target="#doRetraitModal"
				        style="">
				        <i class="bi bi-plus" style="font-size: 20px"></i>
					    Effectuer un retrait
					</button>
				</div>
			</header>
			
			<form action="${pageContext.request.contextPath}/retrait"
		      method="get"
		      id="searchForm">
		      	<div class="my-3 mx-3 p-1 pr-3 gap-2 d-flex bg-body-secondary align-items-center rounded-4">
			      	<i class="bi bi-search mx-2" style="font-size: 20px"></i>
				    <input type="date"
				       name="search"
				       id="searchInput"
				       class="form-control w-100 rounded-4 mr-4"
				       style="height: 45px"
				       value="${search}"
				       onchange="debounceSearch()">
			    </div>
			</form>
			
			<script>
				let timer;
				function debounceSearch() {
				    clearTimeout(timer);
				    timer = setTimeout(() => {
				        document.getElementById('searchForm').submit();
				    }, 400);
				}
				
				// Remettre le focus sur l'input après le rechargement de la page
				window.onload = function() {
				    const input = document.getElementById('searchInput');
				    if (input.value !== "") {
				        input.focus();
				        // Place le curseur à la fin du texte
				        const val = input.value;
				        input.value = '';
				        input.value = val;
				    }
				};
			</script>
		
			<div class="card border-0 shadow-sm rounded-4 p-4 mx-3">
				<table class="table align-middle mb-0">
					<thead>
						<tr class="border-bottom">
							<th style="width: 10%;">Date</th>
							<th style="width: 25%;">Nom du client</th>
					        <th style="width: 18%;">Numéro téléphone</th>
					        <th style="width: 20%;">Montant</th>
					        <th style="width: 1%;">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="retrait" items="${retraits}">
			        <tr>
			            <td>${retrait.daterecep}</td>
			            <td>${retrait.nom}</td>
			            <td>${retrait.numtel}</td>
			            <td>${retrait.montant}</td>
			            <td style="white-space: nowrap;">
						
						    <form action="${pageContext.request.contextPath}/retrait/delete"
						      method="post"
						      style="display:inline;"
						      onsubmit="return confirm('Voulez-vous vraiment supprimer ce retrait ?');">
							    <input type="hidden"
							           name="idRecep"
							           value="${retrait.idrecep}">
							
							    <button type="submit"
							            class="btn text-danger btn-sm"
							            title="Supprimer">
							        <i class="bi bi-trash"></i>
							    </button>
							</form>
						</td>
			        </tr>
			    		</c:forEach>
					</tbody>
				</table>
			</div>
			
			<!-- Modal Do Retrait -->
			<div class="modal fade"
		     id="doRetraitModal"
		     tabindex="-1"
		     aria-labelledby="doRetraitModalLabel"
		     aria-hidden="true">
		
		    <div class="modal-dialog modal-dialog-centered modal-lg"
		    	 style="max-width: 600px;">
		        <div class="modal-content mx-1">
		            <!-- Header -->
		            <div class="modal-header">
		                <h5 class="modal-title fw-bold" id="doRetraitModalLabel">
		                    Effectuer un retrait d'argent
		                </h5>
		                <button type="button"
		                        class="btn-close"
		                        data-bs-dismiss="modal"
		                        aria-label="Close">
		                </button>
		            </div>
		
		            <!-- Formulaire -->
		            <form action="${pageContext.request.contextPath}/retrait/insert"
		                  method="post">
		                <div class="modal-body">
		                        <div class="mt-4">
								    <label class="form-label">Client</label>
								
								    <select id="clientSelect"
								            name="numtel"
								            class="form-select"
								            required>
								        <option value="">Choisir un client</option>
								
								        <c:forEach var="client" items="${clients}">
								            <option value="${client.numtel}">
								                ${client.numtel} (${client.nom})
								            </option>
								        </c:forEach>
								    </select>
								</div>
		                        <div class="mt-4">
		                            <label class="form-label">Montant</label>
		                            <input type="text"
		                                   name="montant"
		                                   class="form-control"
		                                   required>
		                        </div>
		                </div>
		
		                <!-- Footer -->
		                <div class="modal-footer">
		                    <button type="button"
		                            class="btn btn-secondary"
		                            data-bs-dismiss="modal">
		                        Annuler
		                    </button>
		
		                    <button type="submit"
		                            class="btn btn-success">
		                        Enregistrer
		                    </button>
		                </div>
		            </form>
		        </div>
		    </div>
		</div>
		<script src="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/js/bootstrap.bundle.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery-4.0.0.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
		<script>
			$(document).ready(function () {
			    $('#clientSelect').select2({
			        placeholder: "Rechercher un numéro...",
			        allowClear: true,
			        width: '100%',
			        dropdownParent: $('#doRetraitModal')
			    });
			});
		</script>
		</div>
		
	</div>
</body>
</html>