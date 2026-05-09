<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Gestion des clients</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/flatpickr/dist/plugins/monthSelect/style.css">
</head>
<body>
	<div class="d-flex vh-100">
	
	<%@ include file="/components/sidebar.jsp" %>
	
	<div class="flex-grow-1">
	<header class="my-4 mx-3 d-flex "> 
		<div class="flex-grow-1">
			<h2 class="fw-bold">Gestion des Clients</h2>
			<p>Gérer vos clients</p>
		</div>
		<div class="align-self-center">
			<button type="button"
		        class="btn btn-add d-inline-flex align-items-center gap-2 fw-semibold shadow-sm"
		        data-bs-toggle="modal"
		        data-bs-target="#addClientModal"
		        style="">
		        <i class="bi bi-plus" style="font-size: 20px"></i>
			    Ajouter un client
			</button>
		</div>
	</header>
	
	<form action="${pageContext.request.contextPath}/client"
      method="get"
      id="searchForm">
      	<div class="my-3 mx-3 p-1 pr-3 gap-2 d-flex bg-body-secondary align-items-center rounded-4">
	      	<i class="bi bi-search mx-2" style="font-size: 20px"></i>
		    <input type="text"
		       name="search"
		       id="searchInput"
		       class="form-control w-100 rounded-4 mr-4"
		       style="height: 45px"
		       value="${search}"
		       placeholder="Rechercher par nom, téléphone ou email..."
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
					<th style="width: 25%;">Nom</th>
			        <th style="width: 8%;">Sexe</th>
			        <th style="width: 10%;">Numéro téléphone</th>
			        <th style="width: 20%;">Email</th>
			        <th style="width: 2%;">Âge</th>
			        <th style="width: 8%;">Solde</th>
			        <th style="width: 1%;">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="client" items="${clients}">
	        <tr>
	            <td>${client.nom}</td>
	            <td>${client.sexe}</td>
	            <td>${client.numtel}</td>
	            <td>${client.mail}</td>
	            <td>${client.age}</td>
	            <td class="text-success">${client.solde}</td>
	            <td style="white-space: nowrap;">
					<a href="#"
						class="btn btn-primary"
						data-bs-toggle="modal"
						data-bs-target="#pdfModal"
						data-numtel="${client.numtel}"
					>
						Générer PDF
					</a>
										
				    <a href="#"
					   class="btn btn-sm btn-outline-secondary"
					   data-bs-toggle="modal"
					   data-bs-target="#editClientModal"
					   data-numtel="${client.numtel}"
					   data-nom="${client.nom}"
					   data-sexe="${client.sexe}"
					   data-mail="${client.mail}"
					   data-age="${client.age}"
					   data-solde="${client.solde}">
					    <i class="bi bi-pencil"></i>
					</a>
				
				    <form action="${pageContext.request.contextPath}/client/delete"
				      method="post"
				      style="display:inline;"
				      onsubmit="return confirm('Voulez-vous vraiment supprimer ce client ?');">
					    <input type="hidden"
					           name="numtel"
					           value="${client.numtel}">
					
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
	
		<!-- Modal Ajout Client -->
		<div class="modal fade"
	     id="addClientModal"
	     tabindex="-1"
	     aria-labelledby="addClientModalLabel"
	     aria-hidden="true">
	
	    <div class="modal-dialog modal-dialog-centered modal-lg"
	    	 style="max-width: 600px;">
	        <div class="modal-content mx-1">
	            <!-- Header -->
	            <div class="modal-header">
	                <h5 class="modal-title fw-bold" id="addClientModalLabel">
	                    Ajouter un client
	                </h5>
	                <button type="button"
	                        class="btn-close"
	                        data-bs-dismiss="modal"
	                        aria-label="Close">
	                </button>
	            </div>
	
	            <!-- Formulaire -->
	            <form action="${pageContext.request.contextPath}/client/insert"
	                  method="post">
	                <div class="modal-body">
	                        <div>
	                            <label class="form-label">Nom</label>
	                            <input type="text"
	                                   name="nom"
	                                   class="form-control"
	                                   required>
	                        </div>
	                        <div class="mt-4">
	                            <label class="form-label">Sexe</label>
	                            <select name="sexe"
	                                    class="form-select"
	                                    style="width: 110px"
	                                    required>
	                                <option value="">Choisir</option>
	                                <option value="Homme">Masculin</option>
	                                <option value="Femme">Feminin</option>
	                            </select>
	                        </div>	
	                        <div class="mt-4">
	                            <label class="form-label">Numéro téléphone</label>
	                            <input type="text"
	                                   name="numtel"
	                                   class="form-control"
	                                   required>
	                        </div>	
	                        <div class="mt-4">
	                            <label class="form-label">Email</label>
	                            <input type="email"
	                                   name="mail"
	                                   class="form-control"
	                                   required>
	                        </div>	
	                        <div class="mt-4">
	                            <label class="form-label">Âge</label>
	                            <input type="number"
	                                   name="age"
	                                   class="form-control"
	                                   style="width: 80px"
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
	
		<!-- Modal Edition Client -->
		<div class="modal fade"
	     id="editClientModal"
	     tabindex="-1">
	
	    <div class="modal-dialog modal-dialog-centered modal-lg"
	         style="max-width: 600px;">
	
	        <div class="modal-content mx-1">
	
	            <div class="modal-header">
	                <h5 class="modal-title fw-bold">Modifier un client</h5>
	
	                <button type="button"
	                        class="btn-close"
	                        data-bs-dismiss="modal">
	                </button>
	            </div>
	
	            <form action="${pageContext.request.contextPath}/client/update"
	                  method="post">
	
	                <div class="modal-body">
	
						<input type="hidden" id="edit_numtel" name="numtel">
						<div>
							<label class="form-label">Nom</label>
	                    	<input type="text" 
	                    		   id="edit_nom" 
	                    		   name="nom" 
	                    		   class="form-control">
	                    </div>
						<div class="mt-4">
							<label class="form-label">Sexe</label>
		                    <select id="edit_sexe" 
		                    	    name="sexe" 
		                    	    class="form-select" 
		                    	    style="width: 110px">
		                        <option>Masculin</option>
		                        <option>Feminin</option>
		                    </select>
		                </div>
		                <div class="mt-4">
							<label class="form-label">Email</label>
	                    	<input type="email" 
	                    		   id="edit_mail" 
	                    		   name="mail" 
	                    		   class="form-control">
						</div>
						<div class="mt-4">
							<label class="form-label">solde</label>
	                    	<input hidden="true"
	                    		   type="number" 
	                    		   id="edit_solde" 
	                    		   name="solde" 
	                    		   class="form-control">
						</div>
						<div class="mt-4">
							<label class="form-label">Âge</label>
	                    	<input type="number" 
	                    		   id="edit_age" 
	                    		   name="age" 
	                    		   class="form-control" 
	                    		   style="width: 80px">
						</div>
	                </div>
	
	                <div class="modal-footer">
	                    <button type="button"
	                            class="btn btn-secondary"
	                            data-bs-dismiss="modal">
	                        Annuler
	                    </button>
	
	                    <button type="submit"
	                            class="btn btn-primary">
	                        Modifier
	                    </button>
	                </div>
	           	 </form>
	        	</div>
	    	</div>
		</div>
		
		<!-- Modal Générer PDF -->
		<div class="modal fade"
	     id="pdfModal"
	     tabindex="-1"
	     aria-labelledby="pdfModalLabel"
	     aria-hidden="true">
	
	    <div class="modal-dialog modal-dialog-centered modal-lg"
	    	 style="max-width: 600px;">
	        <div class="modal-content mx-1">
	            <!-- Header -->
	            <div class="modal-header">
	                <h5 class="modal-title fw-bold" id="addClientModalLabel">
	                    Générer PDF
	                </h5>
	                <button type="button"
	                        class="btn-close"
	                        data-bs-dismiss="modal"
	                        aria-label="Close">
	                </button>
	            </div>
	            
	            <form action="${pageContext.request.contextPath}/client/pdf"
				      method="post"
				      style="display:inline;"
				      >
				      <div class="modal-body">
					    <input type="hidden"
					    	   id="pdf_numtel"
					           name="numtel">

						<input type="text"
						       id="monthPicker"
						       name="date"
						       class="form-control">
						
						<!-- JS -->
						<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
						
						<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/plugins/monthSelect/index.js"></script>
						
						<script>
							flatpickr("#monthPicker", {
							    plugins: [
							        new monthSelectPlugin({
							            shorthand: true,
							            dateFormat: "Y-m",
							            altFormat: "F Y"
							        })
							    ]
							});
						</script>
											
						<!-- Footer -->
	                	<div class="modal-footer">
							<button type="button"
		                            class="btn btn-secondary"
		                            data-bs-dismiss="modal">
		                        Annuler
		                    </button>
							
						    <button type="submit"
						            class="btn btn-primary"
						    >
						        Générer
						    </button>
					    </div>
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
		<script>
			const editModal = document.getElementById('editClientModal');
			const pdfModal = document.getElementById('pdfModal');
			
			editModal.addEventListener('show.bs.modal', function (event) {
			
			    const button = event.relatedTarget;
			
			    document.getElementById('edit_numtel').value = button.getAttribute('data-numtel');
			    document.getElementById('edit_nom').value = button.getAttribute('data-nom');
			    document.getElementById('edit_sexe').value = button.getAttribute('data-sexe');
			    document.getElementById('edit_mail').value = button.getAttribute('data-mail');
			    document.getElementById('edit_age').value = button.getAttribute('data-age');
			    document.getElementById('edit_solde').value = button.getAttribute('data-solde');
			
			});
			
			pdfModal.addEventListener('show.bs.modal', function (event) {
				const button = event.relatedTarget;
				
				document.getElementById('pdf_numtel').value = button.getAttribute('data-numtel');
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