<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Paramètre des frais</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap-5.3.8/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">

<style type="text/css">
	.btn-transparent {
	    background: transparent;
	    border: none;
	    color: #212529;
	}
	
	.btn-transparent:hover {
	    background: rgba(255,255,255,0.5);
	}
	
	.sub-header {
		border-radius: 10px;
	}
</style>
</head>
<body>

	<div class="d-flex vh-100">
	
		<%@ include file="/components/sidebar.jsp" %>
	
		<div class="flex-grow-1">
			<header class="my-4 mx-3 d-flex "> 
				<div class="flex-grow-1">
					<h2 class="fw-bold">Gestion des Frais</h2>
					<p>Configurez les tarifs d'envoi et de réception</p>
				</div>
				<div class="align-self-center">
					<button type="button"
				        class="btn btn-add d-inline-flex align-items-center gap-2 fw-semibold shadow-sm"
				        data-bs-toggle="modal" 
				        data-bs-target="#addFraisModal">
				        <i class="bi bi-plus" style="font-size: 20px"></i>
				        <c:choose>
						    <c:when test="${f eq 'envoi'}">
						        Ajouter frais d'envoi
						    </c:when>
						    <c:otherwise>
						        Ajouter frais réception
						    </c:otherwise>
						</c:choose>
					</button>
				</div>
			</header>
			
			<div class="d-flex sub-header bg-body-secondary p-1 gap-2 mx-3 mb-4 w-80">
			    <!-- Bouton Frais d'Envoi -->
			    <a href="${pageContext.request.contextPath}/frais?f=envoi"
			       class="flex-fill text-decoration-none">
			        <button
			            class="btn sub-header w-100 d-flex align-items-center justify-content-center gap-2
			            ${f eq 'envoi' ? 'btn-light shadow-sm fw-semibold' : 'btn-transparent'}">
			
			            <i class="bi bi-send"></i>
			            Frais d'Envoi
			        </button>
			    </a>
			
			    <!-- Bouton Frais de Réception -->
			    <a href="${pageContext.request.contextPath}/frais?f=recep"
			       class="flex-fill text-decoration-none">
			        <button
			            class="btn sub-header w-100 d-flex align-items-center justify-content-center gap-2
			            ${f eq 'recep' ? 'btn-light shadow-sm fw-semibold' : 'btn-transparent'}">
			
			            <i class="bi bi-receipt"></i>
			            Frais de Réception
			        </button>
			    </a>
			</div>	
			
			<div class="card border-0 shadow-sm rounded-4 p-4 mx-3">
				<table class="table align-middle mb-0">
					<thead>
						<tr class="border-bottom">
							<th style="width: 30%;">Montant Min</th>
							<th style="width: 30%;">Montant Max</th>
					        <th style="width: 30%;">Frais</th>
					        <th style="width: 1%;">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="fr" items="${frais}">
			        <tr>
			            <td>${fr.montant1}</td>
			            <td>${fr.montant2}</td>
			            <td>
			            	<c:choose>
			            		<c:when test="${ f eq 'envoi' }">
			            			<span style="color: #fd7e14"> ${fr.fraisEnv} </span>
			            		</c:when>
			            		<c:otherwise>
			            			<span style="color: #0d6efd"> ${fr.fraisRec} </span>
			            		</c:otherwise>
			            	</c:choose>
			            	
			            </td>
			            <td style="white-space: nowrap;">
						    <a href="#"
						       class="btn btn-sm btn-outline-secondary"
						       data-bs-toggle="modal"
						       data-bs-target="#editFraisModal"
						
						       data-id="${f eq 'envoi' ? fr.idEnv : fr.idRec}"
						       data-montant1="${fr.montant1}"
						       data-montant2="${fr.montant2}"
						       data-frais="${f eq 'envoi' ? fr.fraisEnv : fr.fraisRec}"
						       data-type="${f}">
						
						        <i class="bi bi-pencil"></i>
						    </a>
						
						    <form action="${pageContext.request.contextPath}/frais/delete"
						          method="post"
						          style="display:inline;"
						          onsubmit="return confirm('Voulez-vous vraiment supprimer ce frais ?');">
						
						        <input type="hidden" name="f" value="${f}">
						
						        <input type="hidden"
						               name="${f eq 'envoi' ? 'idEnv' : 'idRec'}"
						               value="${f eq 'envoi' ? fr.idEnv : fr.idRec}">
						
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
			
			<!-- Modal Add Frais -->
			<div class="modal fade"
		     id="addFraisModal"
		     tabindex="-1"
		     aria-labelledby="addFraisModalLabel"
		     aria-hidden="true">
		
		    <div class="modal-dialog modal-dialog-centered modal-lg"
		    	 style="max-width: 600px;">
		        <div class="modal-content mx-1">
		            <!-- Header -->
		            <div class="modal-header">
		                <h5 class="modal-title fw-bold" id="addFraisModalLabel">
		            		<c:choose>
			            		<c:when test="${ f eq 'envoi' }">
			            			Ajouter un frais d'envoi
			            		</c:when>
			            		<c:otherwise>
									Ajouter un frais de réception
			            		</c:otherwise>
			            	</c:choose>
		                </h5>
		                <button type="button"
		                        class="btn-close"
		                        data-bs-dismiss="modal"
		                        aria-label="Close">
		                </button>
		            </div>
		
		            <!-- Formulaire -->
		            <form action="${pageContext.request.contextPath}/frais/insert"
		                  method="post">
		                <div class="modal-body">
		                    	<input type="hidden" name="f" value="${f}">
		                    			                    	
		                        <div>
		                            <label class="form-label">Montant Min</label>
		                            <input type="text"
		                                   name="montant1"
		                                   pattern="[0-9]+"
		                                   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									   oninput="this.setCustomValidity('')"
		                                   class="form-control"
		                                   required>
		                        </div>
		                        <div class="mt-4">
		                            <label class="form-label">Montant Max</label>
		                            <input type="text"
		                                   name="montant2"
		                                   pattern="[0-9]+"
		                                   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									   oninput="this.setCustomValidity('')"
		                                   class="form-control"
		                                   required>
		                        </div>	
		                        <div class="mt-4">
		                            <label class="form-label">Frais</label>
		                            <c:choose>
					            		<c:when test="${ f eq 'envoi' }">
					            			<input type="text"
			                                   name="frais_env"
			                                   pattern="[0-9]+"
			                                   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
	       									   oninput="this.setCustomValidity('')"
			                                   class="form-control"
			                                   required>
					            		</c:when>
					            		<c:otherwise>
					            			<input type="text"
			                                   name="frais_rec"
		                                   	   pattern="[0-9]+"
		                                       oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									       oninput="this.setCustomValidity('')"
			                                   class="form-control"
			                                   required>
					            		</c:otherwise>
					            	</c:choose>
		                            
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
		
		<!-- Modal Edit Frais -->
			<div class="modal fade"
		     id="editFraisModal"
		     tabindex="-1"
		     aria-labelledby="editFraisModalLabel"
		     aria-hidden="true">
		
		    <div class="modal-dialog modal-dialog-centered modal-lg"
		    	 style="max-width: 600px;">
		        <div class="modal-content mx-1">
		            <!-- Header -->
		            <div class="modal-header">
		                <h5 class="modal-title fw-bold" id="editClientModalLabel">
		            		<c:choose>
			            		<c:when test="${ f eq 'envoi' }">
			            			Mettre à jour un frais d'envoi
			            		</c:when>
			            		<c:otherwise>
									Mettre à jour un frais de réception
			            		</c:otherwise>
			            	</c:choose>
		                </h5>
		                <button type="button"
		                        class="btn-close"
		                        data-bs-dismiss="modal"
		                        aria-label="Close">
		                </button>
		            </div>
		
		            <!-- Formulaire -->
		            <form action="${pageContext.request.contextPath}/frais/update"
		                  method="post">
		                <div class="modal-body">
		                    	<input type="hidden" name="f" value="${f}">
		                    	<c:choose>
					            		<c:when test="${ f eq 'envoi' }">
					            			<input type="hidden" name="idEnv" id="edit_id">
					            		</c:when>
					            		<c:otherwise>
					            			<input type="hidden" name="idRec" id="edit_id">
					            		</c:otherwise>
								</c:choose>
		                        <div>
		                            <label class="form-label">Montant Min</label>
		                            <input type="text"
		                                   name="montant1"
		                            	   id="edit_montant1"
		                                   pattern="[0-9]+"
		                                   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									   oninput="this.setCustomValidity('')"
		                                   class="form-control"
		                                   required>
		                        </div>
		                        <div class="mt-4">
		                            <label class="form-label">Montant Max</label>
		                            <input type="text"
		                                   name="montant2"
		                            	   id="edit_montant2"
		                                   pattern="[0-9]+"
		                                   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									   oninput="this.setCustomValidity('')"
		                                   class="form-control"
		                                   required>
		                        </div>	
		                        <div class="mt-4">
		                            <label class="form-label">Frais</label>
		                            <c:choose>
					            		<c:when test="${ f eq 'envoi' }">
					            			<input type="text"
			                                   name="frais_env"
			                                   id="edit_frais"
		                                   	   pattern="[0-9]+"
		                                   	   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									   	   oninput="this.setCustomValidity('')"
			                                   class="form-control"
			                                   required>
					            		</c:when>
					            		<c:otherwise>
					            			<input type="text"
			                                   name="frais_rec"
			                                   id="edit_frais"
		                                   	   pattern="[0-9]+"
		                                   	   oninvalid="this.setCustomValidity('Veuillez entrer uniquement des chiffres')"
       									  	   oninput="this.setCustomValidity('')"
			                                   class="form-control"
			                                   required>
					            		</c:otherwise>
					            	</c:choose>
		                            
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
		<script>
			const editModal = document.getElementById('editFraisModal');
			
			editModal.addEventListener('show.bs.modal', function (event) {
			
			    const button = event.relatedTarget;
				
				document.getElementById('edit_id').value = button.getAttribute('data-id');
				document.getElementById('edit_montant1').value = button.getAttribute('data-montant1');
				document.getElementById('edit_montant2').value = button.getAttribute('data-montant2');
				document.getElementById('edit_frais').value = button.getAttribute('data-frais');
			
			});
		</script>
		
		</div>
	</div>
</body>
</html>