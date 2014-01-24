<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>  
	<head>
		<title>Manutenção de Configurações</title>
		<script type="text/javascript"	src="${pageContext.request.contextPath}/resources/js/configuracao.js"></script>
	</head>
	<body>
		<div class="row-fluid">
		  	<div class="span12">
				<div class="tabbable">

					<%@ include file="menu.jsp"%>
	
					<div class="tab-content">
	
						<form:form cssClass="form-horizontal"
							action="${pageContext.request.contextPath}/configuracoes/configuracao/cadastro/salvar"
							commandName="configuracao" onsubmit="return configProc.validarForm()" method="post">
	
							<fieldset>
	
								<legend> Manutenção de Configurações </legend>
	
								<div class="control-group">
	
									<form:hidden path="id" />
	
									<c:if test="${message != '' and message != null}">
										<div class="alert alert-error">${message}</div>
									</c:if>
	
								</div>
	
								<div class="control-group">
									<label class="control-label" for="empresa.terceiro.nomeFantasia">Empresa</label>
									<div class="controls">

										<form:hidden path="empresa.id" />

										<form:input path="empresa.terceiro.nomeFantasia"
											readonly="${empresa.id != null}"
											autocomplete="off" cssClass="span3" 
											onchange="configProc.habilitarEntidades()"
											placeholder="Preencha a Empresa associada." />
											
										<form:errors cssClass="native-error" path="empresa.terceiro.nomeFantasia" />

									</div>
								</div>
	
								<div class="control-group">
									<form:label path="contribuinteIpi" for="contribuinteIpi" cssClass="control-label">Contribuinte IPI</form:label>
									<div class="controls">
										<label class="checkbox"> 
											<form:checkbox path="contribuinteIpi" id="contribuinteIpi" />
										</label>
										<form:errors cssClass="native-error" path="contribuinteIpi" />
									</div>
								</div>

								<div id="entidades" class="control-group">
									<label class="control-label">Entidades Registro 74</label>
	
									<div class="controls">
										
										<input type="hidden" id="nNome" value="entidades" />
										<input type="hidden" id="nNomeItem" value="entidade" />					
										<input type="hidden" id="nent"
											value="${fn:length(configuracao.entidades)}" /> 
										<input type="hidden" id="tempEnt.id" />
										<input type="text" class="span3" id="tempEnt.nome" 
											<c:if test="${configuracao.empresa.terceiro.nomeFantasia == '' or configuracao.empresa.terceiro.nomeFantasia == null}">
												disabled="disabled"
											</c:if>
											autocomplete="off"
											placeholder="Digite o nome da Entidade" /> 
											
										<button type="button" class="btn" disabled="disabled" id="addEntBtn"
											onclick="javascript: configProc.addEntidade()"><i class="icon-plus"></i></button>
									</div>
									<c:forEach items="${configuracao.entidades}" var="entidade" varStatus="status">
										<div class="controls">
											<form:hidden path="entidades[${status.index}].id" />
											<form:input cssClass="span3 entidade" readonly="true" path="entidades[${status.index}].nome" />
											<a id="del${status.index}" class="btn" onclick="configProc.removeEntidade(${status.index});"><i class="icon-minus"></i></a>
										</div>
									</c:forEach>
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
		
		<div id="modalEntAlert" class="modal hide fade">
		  	<div class="modal-header">
		    	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    	<h3>Atenção</h3>
		  	</div>
		  	<div class="modal-body">
		   		<p>Você buscou uma entidade mais ainda não a adicionou à lista de entidades associadas à configuração. Clique no botão + ao lado do campo de pesquisa de entidade para adicioná-la ou clique em submeter para ignorar e confirmar o cadastro.</p>
		  	</div>
		  	<div class="modal-footer">
		    	<a href="#" data-dismiss="modal" class="btn btn-primary">Voltar ao Cadastro</a>
		    	<a href="javascript: configProc.ignoreEntAndContinue();" class="btn">Submeter</a>
		  	</div>
		</div>
		
		<script type="text/javascript">
			$(function() {

				$('#empresa\\.terceiro\\.nomeFantasia').blur(function() {
				  	if($(this).val() == ""){
				  		$('#empresa\\.id').val("");
				  	}	
				  	if($('#empresa\\.id').val() == ""){
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
															$(
																	'#empresa\\.id')
																	.val("");
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
				
				$('#tempEnt\\.nome').blur(function() {
				  	if($(this).val() == ""){
				  		$('#tempEnt\\.id').val("");
				  	}	
				  	if($('#tempEnt\\.id').val() == ""){
				  		$(this).val("");
				  	}	
				});
				
				$('#tempEnt\\.nome')
				.typeahead(
					{

						source : function(query, process) {
							
							$('#addEntBtn').attr('disabled',true);
	
							return $
									.get(
											'${pageContext.request.contextPath}/api/entidade/empresa/'+$('#empresa\\.id').val()+'/' + query + '/',
											function(data) {
												
												var newData = new Array();
												
												for(i = 0; i < data.length; i++){
													
													existe = false;
													
													$("#entidades").find('.controls > .entidade').each(function(index){
														
														if(data[i].nome == $(this).val())
															existe = true;
													});
													
													if(!existe){
														newData[newData.length] = data[i];
													}
												}
												
												
												$('#tempEnt\\.id').val("");
												var resultList = newData
														.map(function(item) {
															var aItem = {
																id : item.id,
																nome : item.nome																
															};																																	
															
															return JSON
																	.stringify(aItem);
														});
												return process(resultList);
											});
						},

						matcher : function(obj) {
							var item = JSON.parse(obj);
							
							this.query = this.query.substring(0, this.query.indexOf("_"));
							
							return ~item.nome.toLowerCase()
									.indexOf(this.query.toLowerCase());
						},

						sorter : function(items) {
							var beginswith = [], caseSensitive = [], caseInsensitive = [];
							
							this.query = this.query.substring(0, this.query.indexOf("_"));
							
							while (aItem = items.shift()) {
								var item = JSON.parse(aItem);
								if (!item.nome.toLowerCase()
										.indexOf(
												this.query
														.toLowerCase()))
									beginswith.push(JSON
											.stringify(item));
								else if (~item.nome
										.indexOf(this.query))
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
							
							this.query = this.query.substring(0, this.query.indexOf("_"));
							
							var item = JSON.parse(obj);
							var query = this.query.replace(
									/[\-\[\]{}()*+?.,\\\^$|#\s]/g,
									'\\$&');
							return item.nome.replace(new RegExp(
									'(' + query + ')', 'ig'),
									function($1, match) {
										return '<strong>' + match
												+ '</strong>';
									});
						},

						updater : function(obj) {
						
							var item = JSON.parse(obj);
							
							$('#tempEnt\\.id').val(item.id);
							$('#tempEnt\\.nome').val(item.nome);
							
							if(item.id != null && item.id != undefined)
								$('#addEntBtn').attr('disabled',false);
							
							return item.nome;
						}
					});	
				
			});
		</script>
				
	</body>
</html>
