<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>  
	<head>
		<title><spring:message code="appDefaultTitle"/></title>
	</head>
	<body>		
		<div class="row-fluid">
		  	<div class="span12">
				
			</div>
		</div>
		
		<c:if test="${needSync}">
		
			<div id="modalSync" class="modal hide fade">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>Atualizando Registros!</h3>
				</div>
				<div class="modal-body">
					<p>Os registros da aplicação estão sendo atualizados!</p>
					<p>Atualizando base de Empresas.</p>
				</div>
			</div>
		
			<script type="text/javascript">
			
				$('#modalSync').modal({
				  keyboard: false
				});
			 
				$.get('${pageContext.request.contextPath}/api/sync/empresas', function(data) {
					$('#modalSync > .modal-body p').remove();
				  	$('#modalSync > .modal-body').html(data + "<p>Atualizando base de Entidades.</p>");		
				  	
				  	$.get('${pageContext.request.contextPath}/api/sync/entidades', function(data) {
						$('#modalSync > .modal-body p').remove();
					  	$('#modalSync > .modal-body').html(data + "<p>Atualizando base de Produtos.</p>");		
					  	
					  	$.get('${pageContext.request.contextPath}/api/sync/produtos', function(data) {
							$('#modalSync > .modal-body p').remove();
						  	$('#modalSync > .modal-body').html(data + "<p>Atualizando base de Terceiros.</p>");	
						  	
						  	$.get('${pageContext.request.contextPath}/api/sync/terceiros', function(data) {
								$('#modalSync > .modal-body p').remove();
								$('#modalSync > .modal-body').html(data + "<p>Finalizando atualização.</p>");
							  	
							  	$.get('${pageContext.request.contextPath}/api/sync/updateSyncDate', function(data) {
									$('#modalSync > .modal-body p').remove();
								  	$('#modalSync > .modal-body').html(data);
								  	
								  	setTimeout("$('#modalSync').modal('hide')", 2000);					  
								});	
							});
						  	
						});
					});
				});
			
			</script>
		
		</c:if>
	</body>
</html>
