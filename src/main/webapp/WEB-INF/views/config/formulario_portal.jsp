<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>  
	<head>
		<title>Manutenção de Bases</title>
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/DT_bootstrap.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/DT_bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/kapp.dataTables.js"></script>
	
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/noty/jquery.noty.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/noty/top.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/noty/topCenter.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/noty/default.js"></script>
		
		
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
									<label class="control-label" for="statusPortal">Status Sincronização</label>
									<div class="controls">
										<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="consultatable">
											<thead>
										    	<tr>
										      		<th>Status</th>
										      		<th>Tipo</th>
										      		<th>Última Sincronização</th>
										    	</tr>
										  	</thead>
										  	<tbody>
										    	<tr>
										      		<td oName="address" oValue="empresas/portal/${portal.getId()}">
										      			<c:if test="${needSyncEmpresa == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncEmpresa == false}">
										      				<i class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Empresas
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncEmpresa}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="entidades/portal/${portal.getId()}">
										      			<c:if test="${needSyncEntidade == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncEntidade == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Entidades
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncEntidade}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="terceiros/portal/${portal.getId()}">
										      			<c:if test="${needSyncTerceiros == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncTerceiros == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Terceiros
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncTerceiros}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="produtos/portal/${portal.getId()}">
										      			<c:if test="${needSyncProduto == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncProduto == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Produtos
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncProduto}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="notas/mercadoria/portal/${portal.getId()}">
										      			<c:if test="${needSyncNotasMercadorias == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncNotasMercadorias == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Notas de Mercadorias
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncNotasMercadorias}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="reducoes/portal/${portal.getId()}">
										      			<c:if test="${needSyncReducoesZ == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncReducoesZ == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
										      		<td>
										      			Dados de Reduções Z
										      		</td>
										      		<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${lastSyncReducoesZ}" /></td>
										    	</tr>
										    	<tr>
										      		<td oName="address" oValue="notas/consumo/portal/${portal.getId()}">
										      			<c:if test="${needSyncNotasConsumo == true}">
										      				<i title="Base Desatualizada" class="icon-warning-sign icon-red"></i>
										      			</c:if>
										      			<c:if test="${needSyncNotasConsumo == false}">
										      				<i title="Base Atualizada" class="icon-ok-sign icon-green"></i>
										      			</c:if>
										      		</td>
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
								</div>
	
							</fieldset>
	
						</form:form>
	
					</div>
				</div>
			</div>
		</div>	
		
		<script type="text/javascript">
	
			entities = {
					
				oTable : null,
				
				notyOpened : false,
				
				changeMenu : function () {
					
					var selection = entities.oTable.Selected();			
					
					if (selection.length > 0){
						
						if(!entities.notyOpened){
							noty({
								text: '<strong>Escolha a opção desejada!</strong>',
								type: 'alert',
								layout: 'topCenter',
								buttons: [
						           	{
						           		addClass: 'btn btn-primary', text: 'Sincronizar Seleção', onClick: function($noty) {
						           			$noty.close();
						           			var selection = entities.oTable.Selected();
						           			for(var i = 0; i < selection.length; i++){
												$.get('${pageContext.request.contextPath}/api/sync/' + selection[i].address, function(data) { });
											}
							            }
						           	},
						           	{
						           		addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
							               	$noty.close();
							            }
						           	}
						         ],
					         	callback: {
					        	    onShow: function() { entities.notyOpened = true; },
					        	    afterShow: function() {},
					        	    onClose: function() { },
					        	    afterClose: function() { entities.notyOpened = false; }
					        	  },
							});	
						}
						
					}
					else
						$.noty.closeAll();
				}
			};
		
			$(document).ready(function () {
				
				entities.oTable = $('#consultatable').kappDataTable({ 
					"MultiRowSelection": true,
					"trClickCallback" : entities.changeMenu,
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
