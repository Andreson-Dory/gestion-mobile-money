
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: Arial, sans-serif;
    }

    .container {
        display: flex;
        min-height: 100vh;
    }
    
    .menus {
        display: block;
        min-height: 80vh;
        padding-left: 20px;
        padding-right: 20px;
        margin-top: 25px;
    }

    .sidebar {
	    background-color: var(--sidebar);
	    color: var(--sidebar-foreground);
	    border-right: 1px solid var(--border);
	}

    .sidebar h2 {
    	font-size: 20px;
		font-weight: bold;
		font-family: inherit;
        margin-bottom: 30px;
    }

    .sidebar a {
        display: block;
        color: white;
        text-decoration: none;
        margin-bottom: 5px;
        padding: 5px;
        padding-left: 10px;
        border-radius: 10px;
        font-size: 14px;
    }

    .sidebar a:hover {
        background: #34495e;
    }
    
	.sidebar a.active {
	    background-color: var(--sidebar-accent);
	    color: var(--sidebar-accent-foreground);
	    border-radius: var(--radius);
	}

    .content {
        flex: 1;
        padding: 30px;
        background: #f5f6fa;
    }
    
    .icon-large {
    	font-size: 22px;
	}

	.btn-icon-title {
		width: 45px; 
		height: 42px;
	}
	
	.btn-add {
		font-size: 16px;
		background-color: var(--primary);
		padding: 5px;
		color: white;
		padding-left: 10px;
		padding-right: 15px;
		border-radius: 15px;
	}
	
	.card {
	    background: white;
	    border: 1px solid #e2e8f0;
	}
	
	.table thead th {
	    font-size: 15px;
	    padding: 10px 12px;
	    color: dark;
	    border-bottom: 1px solid #dee2e6;
	}
	
	.table tbody td {
	    padding: 10px 12px;
	    vertical-align: middle;
	    border-bottom: 1px solid #f1f5f9;
	    color: dark;
	}
	
	.table tbody tr:last-child td {
	    border-bottom: none;
	}
	
	.table tbody tr:hover {
	    background-color: #f8fafc;
	    transition: 0.2s;
	}
	
	.toast {
	    visibility: hidden;
	    min-width: 250px;
	    text-align: center;
	    border-radius: 8px;
	    padding: 12px;
	    position: fixed;
	    z-index: 9999;
	    bottom: 30px;
	    right: 30px;
	    opacity: 0;
	    color: white;
	    transition: all 0.4s ease;
	}

	.toast.show {
	    visibility: visible;
	    opacity: 1;
	    bottom: 50px;
	}
	
	.toast.success {
	    background-color: #2ecc71;
	}
	
	.toast.error {
	    background-color: #e74c3c;
	}

</style>
<div class="sidebar">
	<h2 class="d-flex mx-3 my-4 gap-2 align-items-center">
		<span class="btn-icon-title d-flex align-items-center justify-content-center rounded-2 bg-primary text-white">
			<i class="bi bi-currency-dollar" style="font-size: 30px;"></i>
		</span >
		MoneyFlow
	</h2>
	
	<%
	
		String currentPath = request.getRequestURI();
		String contextPath = request.getContextPath();	
	
		String homePath = contextPath + "";
		String clientPath = contextPath + "/client";
		String envoiPath = contextPath + "/envoi";
		String retraitPath = contextPath + "/retrait";
		String fraisPath = contextPath + "/frais";
		
	%>
	
	<div class="menus">
		<a href=<%= homePath %> 
		   class="d-flex gap-2 fw-semibold align-items-center <%= currentPath.equals(contextPath + "/") ? "active" : "" %>">
			<i class="bi bi-house icon-large"></i>
			Tableau de bord
		</a>
		<a href=<%= clientPath %> 
		   class="d-flex gap-2 fw-semibold align-items-center <%= currentPath.contains("/client") ? "active" : "" %>">
			<i class="bi bi-people icon-large"></i>
			Clients
		</a>
		<a href=<%= envoiPath %> 
		   class="d-flex gap-2 fw-semibold align-items-center <%= currentPath.contains("/envoi") ? "active" : "" %>">
			<i class="bi bi-send icon-large"></i>
			Envoi
		</a>
		<a href=<%= retraitPath %> 
		   class="d-flex gap-2 fw-semibold align-items-center <%= currentPath.contains("/retrait") ? "active" : "" %>">
			<i class="bi bi-cash-stack icon-large" class="d-flex gap-2 fw-semibold align-items-center"></i>
			Retrait
		</a>
		<a href=<%= fraisPath %> 
		   class="d-flex gap-2 fw-semibold align-items-center <%= currentPath.contains("/frais") ? "active" : "" %>">
			<i class="bi bi-gear icon-large" ></i>
			Paramètres des frais
		</a>
	</div>
</div>
