<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>
	<spring:message code="appDefaultTitle" /></title>
</head>
<body>
	<div class="row-fluid">
		<div class="span12">

			<form:form cssClass="form-horizontal" id="form"
				action="${pageContext.request.contextPath}/home/gerar"
				commandName="parametros" method="post">

				<fieldset>

					<legend> Gera��o do Sintegra </legend>

					<div class="control-group">
						<c:if test="${message != '' and message != null}">
							<div class="alert alert-error">${message}</div>
						</c:if>
						
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Informa��o:</strong> A aplica��o suporta a gera��o dos seguintes registros: 10, 11, 50, 51, 53, 54, 60M, 60A, 60R, 74, 75 e 90.
						</div>
						<div class="alert">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Sincroniza��o:</strong> A gera��o do arquivo pode levar v�rios minutos. A sincroniza��o de registros com a retaguarda pode ser necess�ria.
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
						<label class="control-label" for="dataInicial">Per�odo</label>
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
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro50" id="registro50" cssClass="blockOnTotal" /> Gerar Registro 50
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro50" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro51" id="registro51" cssClass="blockOnTotal" /> Gerar Registro 51
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro51" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro53" id="registro53" cssClass="blockOnTotal" /> Gerar Registro 53
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro53" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro54" id="registro54" cssClass="blockOnTotal" /> Gerar Registro 54
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro54" />
						</div>
					</div>

					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro60" id="registro60" cssClass="blockOnTotal" /> Gerar Registro 60
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro60" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro70" id="registro70" cssClass="blockOnTotal" /> Gerar Registro 70
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro70" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro71" id="registro71" cssClass="blockOnTotal" /> Gerar Registro 71
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro71" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro74" id="registro74" cssClass="blockOnTotal" /> Gerar Registro 74
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro74" />
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"> 
								<form:checkbox path="gerarRegistro75" id="registro75" cssClass="blockOnTotal" /> Gerar Registro 75
							</label>
							<form:errors cssClass="native-error" path="gerarRegistro75" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="dataInventario">�ltimo Invent�rio</label>
						<div class="controls">
							<form:input cssClass="span3 date" path="dataInventario"
								data-date-format="dd/mm/yyyy" placeholder="Data Invent�rio" />
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
			Seu sintegra est� sendo gerado. Aguarde redirecionamento!
		</div>
	</div>
	
	<script type="text/javascript">
	
		var needSyncSaldoMercadoria = true;
		
		function needSync() {
			
			if($("#registro74").is(':checked') && needSyncSaldoMercadoria){
				syncSaldoMercadorias(function(){
			  		needSync();
			  	});
			} 
			else {
				
				if($('#empresa\\.id').val() == ''){

					noty({
						text: 'Informe uma empresa para gera��o do sintegra!',
						type: 'error',
						layout: 'top',
						timeout: false,
					});	
					
				  	return;
				}

				$("#modalSubmit").modal({
					backdrop: 'static',
					keyboard: false
				});
				
				$("#form").submit();
			}				
		}
		
		function syncSaldoMercadorias(callback){
			
			if($('#empresa\\.id').val() == ''){

			  	noty({
					text: 'Informe uma empresa para gera��o do sintegra!',
					type: 'error',
					layout: 'top',
					timeout: false,
				});	
			  	
			  	return;
			}
			
			if($('#dataInventario').val() == ''){
				
			  	noty({
					text: 'Informe a data do invent�rio para consulta do saldo na retaguarda!',
					type: 'error',
					layout: 'top',
					timeout: false,
				});	
			  	
			  	return;
			}

			$('#modalSync > .modal-body').html("<p>Os registros da aplica��o est�o sendo atualizados!</p><p>Obtendo Saldo de Mercadorias.</p>");
			
			$("#modalSync").modal({
				backdrop: 'static',
				keyboard: false
			});
			
		  	var data = $('#dataInventario').val().split("/");
		  	data = data[2]+'-'+data[1]+'-'+data[0];
		  	
			$.get('${pageContext.request.contextPath}/api/sync/saldos/mercadorias/empresa/'+$('#empresa\\.id').val() + '/' + data + '/', function(data) {

				$("#modalSync").modal('hide');
				
			  	if(data.indexOf("Erro") != -1) {
			  		
			  		noty({
						text: data,
						type: 'error',
						layout: 'top',
						timeout: false,
					});
			  		
			  		return;
			  	}
			  	
			  	needSyncSaldoMercadoria = false;
			  	
			  	if(callback != null)
			  		callback();				  	
			});
		}
	
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
				
				$('#dataInventario').attr('disabled',true);
				if($('#registro74').is(":checked"))
					$('#dataInventario').attr('disabled',false);
				
				$('input[name="finalidadeArquivo"]').change(function() {
					  if($( this ).val() == 2) {
						  $('.blockOnTotal').each(function(idx, value){
							  $(this).attr('checked', false);
							  $(this).attr('disabled',true);
						  });	
						  
						  $('#dataInventario').attr('disabled',true);
					  } 
					  else
						  $('.blockOnTotal').each(function(idx, value){
							  $(this).attr('checked', false);
							  $(this).attr('disabled', false);
						  });
				});
				
				$('#registro74').change(function() {
					if($(this).is(":checked")){
						$('#dataInventario').val('');
						$('#dataInventario').attr('disabled',false);
					}
					else{
						$('#dataInventario').val('');
						$('#dataInventario').attr('disabled',true);
					}
				});
				
			});
		
	</script>

</body>
</html>
