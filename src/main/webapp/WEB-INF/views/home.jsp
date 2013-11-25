<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title><spring:message code="appDefaultTitle" /></title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/datepicker.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>

</head>
<body>
	<div class="row-fluid">
		<div class="span12">

			<form:form cssClass="form-horizontal" id="form"
				action="${pageContext.request.contextPath}/home/gerar"
				commandName="parametros" method="post">

				<fieldset>

					<legend> Geração do Sintegra </legend>

					<div class="control-group">
					
						<c:if test="${message != '' and message != null}">
							<div class="alert alert-error">${message}</div>
						</c:if>
						
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Informação:</strong> Serão gerados os registros 10, 11, 50, 51, 53, 54, 75 e 90.
						</div>
						<div class="alert">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Sincronização:</strong> A geração do arquivo pode levar vários minutos. A sincronização de registros com a retaguarda pode ser necessária.
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="empresa.terceiro.nomeFantasia">Empresa</label>
						<div class="controls">
							<form:hidden path="empresa.id" />
							<form:input path="empresa.terceiro.nomeFantasia" readonly="${empresa.id != null}"
								autocomplete="off" cssClass="span3"
								placeholder="Preencha a Loja associada." />
							<form:errors cssClass="native-error" path="empresa.terceiro.nomeFantasia" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="finalidadeArquivo">Finalidade:</label>
						<div class="controls">
							<label class="radio"> <form:radiobuttons
									path="finalidadeArquivo" items="${finalidades}"
									itemValue="codigo" itemLabel="descricao" />
							</label>
							<form:errors cssClass="native-error" path="finalidadeArquivo" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="dataInicial">Período</label>
						<div class="controls">
							<form:input cssClass="span3 date" path="dataInicial"
								data-date-format="dd/mm/yyyy" placeholder="Data Inicial" />
							<form:errors cssClass="native-error" path="dataInicial" />
						</div>
						<div class="controls">
							<form:input cssClass="span3 date" path="dataFinal"
								data-date-format="dd/mm/yyyy" placeholder="Data Final" />
							<form:errors cssClass="native-error" path="dataFinal" />
						</div>
					</div>
					

					<div class="control-group">
						<form:label path="gerarRegistro74" for="registro74" cssClass="control-label">Gerar Registro 74:</form:label>
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro74" id="registro74" />
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro74" />
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="control-label" for="dataInventario">Último Inventário</label>
						<div class="controls">
							<form:input cssClass="span3 date" path="dataInventario"
								data-date-format="dd/mm/yyyy" placeholder="Data Inventário" />
							<form:errors cssClass="native-error" path="dataInventario" />
						</div>
					</div>

					<div class="form-actions">
						<button type="button" onclick="javascript: needSync();" class="btn btn-primary">Prosseguir</button>
					</div>

				</fieldset>

			</form:form>

		</div>
	</div>

	<div id="modalSync" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3>Atualizando Registros!</h3>
		</div>
		<div class="modal-body">
			
		</div>
	</div>
	
	<div id="modalSubmit" class="modal hide fade">
		<div class="modal-header">
			<h3>Gerando Sintegra</h3>
		</div>
		<div class="modal-body">
			Seu sintegra está sendo gerado. Aguarde redirecionamento!
		</div>
	</div>
	
	<script type="text/javascript">
	
		var needSyncEmpresa = ${needSyncEmpresa};
		var needSyncEntidade = ${needSyncEntidade};
		var needSyncProduto = ${needSyncProduto};
		var needSyncNotasMercadorias = ${needSyncNotasMercadorias};
		var needSyncNotasConsumo = ${needSyncNotasConsumo};
		var needSyncTerceiros = ${needSyncTerceiros};
		
		var isModalOpened = false;
		
		function bootstrapSync(){
			
			if(needSyncEmpresa){	
				openModal();
			  	syncEmpresa(function(){
			  		bootstrapSync();
			  	});
			}
			else if(needSyncEntidade){
				openModal();
				syncEntidade(function(){
					bootstrapSync();
			  	});
			}
			else {
				setTimeout("$('#modalSync').modal('hide'); isModalOpened = false;", 2000);		
			}	
		}
		
		function needSync() {
			
			if(needSyncTerceiros){
				openModal();
				syncTerceiro(function(){
			  		needSync();
			  	});
			}
			else if(needSyncProduto){
				openModal();
				syncProduto(function(){
			  		needSync();
			  	});
			}
			else if(needSyncNotasMercadorias){
				openModal();
				syncNotasMercadoria(function(){
			  		needSync();
			  	});
			}
			else if(needSyncNotasConsumo){
				openModal();
				syncNotasConsumo(function(){
			  		needSync();
			  	});
			}
			else {
				setTimeout("$('#modalSync').modal('hide'); isModalOpened = false;", 2000);
				$("#modalSubmit").modal();
				$("#form").submit();
			}				
		}
		
		function openModal(){
			
			if(isModalOpened)
				return;
			
			$('#modalSync').modal({
			  keyboard: false
			});				
		}
		
		function syncEmpresa(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Empresas.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/empresas', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncEmpresa = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
		
		function syncEntidade(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Entidades.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/entidades', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncEntidade = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
		
		function syncTerceiro(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Terceiros.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/terceiros', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncTerceiros = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
		
		function syncProduto(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Produtos.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/produtos', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncProduto = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
		
		function syncNotasMercadoria(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Notas de Mercadoria.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/notas/mercadoria', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncNotasMercadorias = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
		
		function syncNotasConsumo(callback){
			
			$('#modalSync > .modal-body p').remove();
		  	$('#modalSync > .modal-body').html("<p>Os registros da aplicação estão sendo atualizados!</p><p>Atualizando base de Notas de Consumo.</p>");
			
			$.get('${pageContext.request.contextPath}/api/sync/notas/consumo', function(data) {
				$('#modalSync > .modal-body p').remove();
			  	$('#modalSync > .modal-body').html(data);
			  	
			  	needSyncNotasConsumo = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
	
		bootstrapSync();
		
		$(function() {

			$('#empresa\\.terceiro\\.nomeFantasia').blur(function() {
				if ($(this).val() == "") {
					$('#empresa\\.id').val("");
				}
				if ($('#empresa\\.id').val() == "") {
					$(this).val("");
				}
			});

			$('#empresa\\.terceiro\\.nomeFantasia')
				.typeahead(
					{

						source : function(query, process) {

							return $
									.get(
											'${pageContext.request.contextPath}/api/empresa/get',
											{
												query : query
											},
											function(data) {
												$('#empresa\\.id').val(
														"");
												var resultList = data
														.map(function(
																item) {
															var aItem = {
																id : item.id,
																nome : item.terceiro.nomeFantasia
															};
															return JSON
																	.stringify(aItem);
														});
												return process(resultList);
											});
						},

						matcher : function(obj) {
							var item = JSON.parse(obj);
							return ~item.nome.toLowerCase().indexOf(
									this.query.toLowerCase());
						},

						sorter : function(items) {
							var beginswith = [], caseSensitive = [], caseInsensitive = [];
							while (aItem = items.shift()) {
								var item = JSON.parse(aItem);
								if (!item.nome.toLowerCase().indexOf(
										this.query.toLowerCase()))
									beginswith.push(JSON
											.stringify(item));
								else if (~item.nome.indexOf(this.query))
									caseSensitive.push(JSON
											.stringify(item));
								else
									caseInsensitive.push(JSON
											.stringify(item));
							}
							return beginswith.concat(caseSensitive,
									caseInsensitive);
						},

						highlighter : function(obj) {
							var item = JSON.parse(obj);
							var query = this.query.replace(
									/[\-\[\]{}()*+?.,\\\^$|#\s]/g,
									'\\$&');
							return item.nome.replace(new RegExp('('
									+ query + ')', 'ig'),
									function($1, match) {
										return '<strong>' + match
												+ '</strong>';
									});
						},

						updater : function(obj) {
							var item = JSON.parse(obj);
							$('#empresa\\.id').val(item.id);
							return item.nome;
						}
					}).focus();
		});
		
		$(document).ready(
			function() {

				var nowTemp = new Date();
				var now = new Date(nowTemp.getFullYear(), nowTemp
						.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

				var checkin = $('#dataInicial').datepicker({
					format : 'dd/mm/yyyy',
					todayBtn : 'linked'
				}).on('changeDate', function(ev) {

					var newDate = new Date(ev.date);
					newDate.setDate(newDate.getDate() + 1);
					checkout.setStartDate(newDate);

					if (ev.date.valueOf() > checkout.date.valueOf())
						checkout.update(newDate);

					checkin.hide();
					$('#dataFinal')[0].focus();

				}).data('datepicker');

				var checkout = $('#dataFinal').datepicker({
					format : 'dd/mm/yyyy',
					todayBtn : 'linked'
				}).on('changeDate', function(ev) {
					checkout.hide();
				}).data('datepicker');

				$('#dataInventario').datepicker({
					format : 'dd/mm/yyyy',
					todayBtn : 'linked'
				}).on('changeDate', function(ev) {
					checkout.hide();
				}).data('datepicker');
				
			});
		
	</script>

</body>
</html>
