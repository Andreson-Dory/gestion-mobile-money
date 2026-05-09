<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Gestion des envois</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link href="${pageContext.request.contextPath}/assets/css/select2.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">

</head>

<body>

<div class="d-flex vh-100">

    <%@ include file="/components/sidebar.jsp" %>

    <div class="flex-grow-1">

        <!-- HEADER -->
        <header class="my-4 mx-3 d-flex">
            <div class="flex-grow-1">
                <h2 class="fw-bold">Gestion des envois</h2>
                <p>Gérez les transferts d'argent</p>
            </div>

            <div class="align-self-center">
                <button type="button"
                        class="btn btn-add d-inline-flex align-items-center gap-2 fw-semibold shadow-sm"
                        data-bs-toggle="modal"
                        data-bs-target="#addEnvoiModal">

                    <i class="bi bi-plus" style="font-size: 20px"></i>
                    Ajouter un envoi
                </button>
            </div>
        </header>

        <!-- SEARCH -->
        <form action="${pageContext.request.contextPath}/envoi"
              method="get"
              id="searchForm">
            <div class="my-3 mx-3 p-1 gap-2 d-flex bg-body-secondary align-items-center rounded-4">
                <i class="bi bi-search mx-2" style="font-size: 20px"></i>

                <input type="date"
                       name="search"
                       id="searchInput"
                       class="form-control w-100 rounded-4"
                       value="${search}"
                       onkeyup="debounceSearch()">
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
        </script>

        <!-- TABLE -->
        <div class="card border-0 shadow-sm rounded-4 p-4 mx-3">
            <table class="table align-middle mb-0">
                <thead>
                <tr class="border-bottom">
                    <th style="width: 20%">Date</th>
                    <th style="width: 15%">Client</th>
                    <th style="width: 15%">Bénéficiaire</th>
                    <th style="width: 15%">Montant</th>
                    <th style="width: 10%">Retrait</th>
                    <th style="width: 10%">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="envoi" items="${envoi}">
                    <tr>
                        <td>${envoi.dateEnvoi}</td>
                        <td>${envoi.numEnvoyeur}</td>
                        <td>${envoi.numRecepteur}</td>
                        <td>${envoi.montant} Ar</td>
                        <td>
                            <c:choose>
                                <c:when test="${envoi.payerFraisRetrait}">
                                    Oui
                                </c:when>
                                <c:otherwise>
                                    Non
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="white-space: nowrap;">
                            <form action="${pageContext.request.contextPath}/envoi/delete"
                                  method="post"
                                  style="display:inline;"
                                  onsubmit="return confirm('Supprimer cet envoi ?');">

                                <input type="hidden" name="idEnv" value="${envoi.idEnv}">

                                <button type="submit"
                                        class="btn text-danger btn-sm">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- MODAL -->
        <div class="modal fade" 
        	id="addEnvoiModal" 
        	tabindex="-1" 
        	aria-labelledby="AddEnvoiModalLabel"
		    aria-hidden="true">

            <div class="modal-dialog modal-dialog-centered modal-lg" style="max-width: 600px;">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title fw-bold" id="AddEnvoiModalLabel">
                        	Effectuer un Transfert d'argent
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>

                    <form action="${pageContext.request.contextPath}/envoi/insert"
                          method="post">
                        <div class="modal-body">
                            <div>
                                <label class="form-label">Client</label>
                                
                                <select 
                                	id="EnvoyeurSelect" 
                                	name="numEnvoyeur" 
                                	class="form-select" 
                                	required
                                >
                                    <option value="">Choisir</option>
                                    <c:forEach var="client" items="${clients}">
                                        <option value="${client.numtel}">
                                            ${client.nom} (${client.numtel})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mt-4">
                                <label class="form-label">Bénéficiaire</label>
                                
                                <select 
                                	id="RecepteurSelect" 
                                	name="numRecepteur" 
                                	class="form-select" 
                                	required>
                                    <option value="">Choisir</option>
                                    <c:forEach var="client" items="${clients}">
                                        <option value="${client.numtel}">
                                            ${client.nom} (${client.numtel})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mt-4">
                                <label class="form-label">Montant</label>
                                <input type="number" name="montant" class="form-control" required>
                            </div>

                            <div class="mt-4">
                                <label class="form-label">Frais retrait</label>
                                <select name="payer_frais_retrait" class="form-select">
                                    <option value="true">Oui</option>
                                    <option value="false">Non</option>
                                </select>
                            </div>

                            <div class="mt-4">
                                <label class="form-label">Motif</label>
                                <input type="text" name="raison" class="form-control" required>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                Annuler
                            </button>

                            <button type="submit" class="btn btn-success">
                                Enregistrer
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="toast-container position-fixed bottom-0 end-0 p-3">
		    <div id="liveToast"
		         class="toast align-items-center text-bg-success border-0"
		         role="alert"
		         aria-live="assertive"
		         aria-atomic="true">
		
		        <div class="d-flex">
		            <div class="toast-body" id="toastMessage">
		                Message ici
		            </div>
		
		            <button type="button"
		                    class="btn-close btn-close-white me-2 m-auto"
		                    data-bs-dismiss="toast">
		            </button>
		        </div>
		    </div>
		</div>
       
        <script src="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/js/bootstrap.bundle.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery-4.0.0.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
		<script>
			$(document).ready(function () {
				$('#EnvoyeurSelect').select2({
			     	 placeholder: "Rechercher un client...",
			    	 allowClear: true,
			     	 width: '100%',
		        	 dropdownParent: $('#addEnvoiModal')
		    	});
			});
			$(document).ready(function () {
				$('#RecepteurSelect').select2({
			     	 placeholder: "Rechercher un client...",
			    	 allowClear: true,
			     	 width: '100%',
		        	 dropdownParent: $('#addEnvoiModal')
		    	});
			});
		</script>
		<script>
		    function showToast(message, type) {
		
		        const toastElement = document.getElementById('liveToast');
		        const toastMessage = document.getElementById('toastMessage');
		
		        toastMessage.textContent = message;
		
		        toastElement.classList.remove('text-bg-success');
		        toastElement.classList.remove('text-bg-danger');
		
		        if (type === 'success') {
		            toastElement.classList.add('text-bg-success');
		        } else if (type === 'error') {
		            toastElement.classList.add('text-bg-danger');
		        }
		
		        const toast = new bootstrap.Toast(toastElement);
		        toast.show();
		    }
		</script>
		<c:if test="${not empty sessionScope.success}">
		    <script>
		        showToast("${sessionScope.success}", "success");
		    </script>
		    <c:remove var="success" scope="session"/>
		</c:if>
		
		<c:if test="${not empty sessionScope.error}">
		    <script>
		        showToast("${sessionScope.error}", "error");
		    </script>
		    <c:remove var="error" scope="session"/>
		</c:if>
    </div>
</div>
</body>
</html>