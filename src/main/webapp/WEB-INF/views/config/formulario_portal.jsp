<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>  
	<head>
		<title>Manutenção de Bases</title>
	</head>
	<body>
		<div class="row-fluid">
		  	<div class="span12">
				<div class="tabbable">

					<%@ include file="menu.jsp"%>
	
					<div class="tab-content">
	
						<form:form cssClass="form-horizontal"
							action="${pageContext.request.contextPath}/configuracoes/bases/cadastro/salvar"
							commandName="portal" method="post">
	
							<fieldset>
	
								<legend> Manutenção de Bases</legend>
	
								<div class="control-group">
	
									<form:hidden path="id" />
	
									<c:if test="${message != '' and message != null}">
										<div class="alert alert-error">${message}</div>
									</c:if>
	
								</div>
	
								<div class="control-group">
									<label class="control-label">CNPJ</label>
									<div class="controls">
										<form:input readonly="true" path="cnpj"											
											autocomplete="off" cssClass="span3"  />
									</div>
								</div>
	
								<div class="control-group">
									<label class="control-label">Nome Fantasia</label>
									<div class="controls">
										<form:input readonly="true" path="nomeFantasia"											
											autocomplete="off" cssClass="span3"  />
									</div>
								</div>
	
								<div class="control-group">
									<label class="control-label">Status Atual</label>
									<div class="controls">
										<input readonly="readonly" type="text" autocomplete="off" class="span3" value="${portal.statusPortal}" />
									</div>
								</div>
	
								<div class="control-group">
									<label class="control-label" for="statusPortal">Novo Status</label>
									<div class="controls">
										<label class="radio"> 
											<form:radiobuttons path="statusPortal" items="${statusPortais}" itemValue="code" itemLabel="description" />
										</label>
										<form:errors cssClass="native-error" path="statusPortal" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">Status Sincronização</label>
									<div class="controls">
										<c:if test="${statusSync == true}">
						      				<input readonly="readonly" type="text" autocomplete="off" class="span3" value="Em andamento" />
						      			</c:if>
						      			<c:if test="${statusSync == false}">
						      				<input readonly="readonly" type="text" autocomplete="off" class="span3" value="Parada" />
						      			</c:if>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">Status Sincronização</label>
									<div class="controls">
										<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="consultatable">
											<thead>
										    	<tr>
										      		<th>Tipo</th>
										      		<th>Última Sincronização</th>
										    	</tr>
										  	</thead>
										  	<tbody>
										    	<tr>
										      		<td>
										      			Dados de Empresas
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncEmpresa}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Entidades
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncEntidade}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Terceiros
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncTerceiros}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Produtos
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncProduto}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Notas de Mercadorias
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncNotasMercadorias}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Reduções Z
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncReducoesZ}" /></td>
										    	</tr>
										    	<tr>
										      		<td>
										      			Dados de Notas de Consumo 
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncNotasConsumo}" /></td>
										    	</tr>
										  	</tbody>
										</table>
									</div>
								</div>
								
								<div class="form-actions">
									<button type="submit" class="btn btn-primary">Confirmar</button>	
									<a class="btn" href="#" onClick="syncBase();">Sincronizar Base</a>									
								</div>
	
							</fieldset>
	
						</form:form>
	
					</div>
				</div>
			</div>
		</div>	
		
		<script type="text/javascript">
		
			function syncBase() {
				$.get('${pageContext.request.contextPath}/api/sync/manual/portal/${portal.id}', function(data) {  });
			}
		
			$(document).ready(function () {
				
				$('#consultatable').kappDataTable({ 
					"sortColumn" : 0,
			        "sortOrder" : "asc",
			        "bFilter": false,
			        "bPaginate": false,
			        "bInfo": false
				});		
			} );
		</script>
				
	</body>
</html>
