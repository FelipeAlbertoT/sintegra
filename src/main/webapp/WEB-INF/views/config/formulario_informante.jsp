<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>  
	<head>
		<title>Manutenção de Informante</title>
	</head>
	<body>
		<div class="row-fluid">
		  	<div class="span12">
				<div class="tabbable">

					<%@ include file="menu.jsp"%>
	
					<div class="tab-content">
	
						<form:form cssClass="form-horizontal"
							action="${pageContext.request.contextPath}/configuracoes/informantes/cadastro/salvar"
							commandName="informante" method="post">
	
							<fieldset>
	
								<legend> Manutenção de Informante </legend>
	
								<div class="control-group">
	
									<form:hidden path="id" />
	
									<c:if test="${message != '' and message != null}">
										<div class="alert alert-error">${message}</div>
									</c:if>
	
								</div>
	
								<div class="control-group">
									<label class="control-label" for="empresa.nome">Empresa</label>
									<div class="controls">

										<form:hidden path="empresa.id" />

										<form:input path="empresa.nome"
											readonly="${empresa.id != null}" autofocus="autofocus"
											autocomplete="off" cssClass="span3" 
											placeholder="Preencha a Empresa associada." />
											
										<form:errors cssClass="native-error" path="empresa.nome" />

									</div>
								</div>
	
								<div class="control-group">
									<label class="control-label" for="nome">Nome</label>
									<div class="controls">
										<form:input path="nome" cssClass="span3"
											placeholder="Preencha o nome" />
										<form:errors cssClass="native-error" path="nome" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="telefone">Telefone</label>
									<div class="controls">
										<form:input path="telefone" cssClass="span3 informantephone"
											placeholder="Preencha o Telefone" />
										<form:errors cssClass="native-error" path="telefone" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="cep">Cep</label>
									<div class="controls">
										<form:input path="cep" cssClass="span3 cep"
											placeholder="Preencha o CEP" />
										<form:errors cssClass="native-error" path="cep" />
									</div>
								</div>
	
								<div class="control-group">
									<label class="control-label" for="logradouro">Endereço</label>
									<div class="controls">
										<form:input path="logradouro" cssClass="span3"
											placeholder="Preencha o logradouro" />
										<form:errors cssClass="native-error" path="logradouro" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="numero">Número</label>
									<div class="controls">
										<form:input path="numero" cssClass="span3"
											placeholder="Preencha o número" />
										<form:errors cssClass="native-error" path="numero" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="bairro">Bairro</label>
									<div class="controls">
										<form:input path="bairro" cssClass="span3"
											placeholder="Preencha o Bairro" />
										<form:errors cssClass="native-error" path="bairro" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="complemento">Complemento</label>
									<div class="controls">
										<form:input path="complemento" cssClass="span3"
											placeholder="Preencha o Complemento" />
										<form:errors cssClass="native-error" path="complemento" />
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
			$(function() {

				$('#empresa\\.nome').blur(function() {
				  	if($(this).val() == ""){
				  		$('#empresa\\.id').val("");
				  	}	
				  	if($('#empresa\\.id').val() == ""){
				  		$(this).val("");
				  	}	
				});
				
				$('#empresa\\.nome')
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
								});
			});
		</script>
				
	</body>
</html>
