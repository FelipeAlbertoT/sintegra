<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>  
	<head>
		<title>Manutenção de Configurações</title>
	</head>
	<body>
		<div class="row-fluid">
		  	<div class="span12">
				<div class="tabbable">

					<%@ include file="menu.jsp"%>
	
					<div class="tab-content">
	
						<form:form cssClass="form-horizontal"
							action="${pageContext.request.contextPath}/configuracoes/configuracao/cadastro/salvar"
							commandName="configuracao" method="post">
	
							<fieldset>
	
								<legend> Manutenção de Configurações </legend>
	
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
											readonly="${empresa.id != null}"
											autocomplete="off" cssClass="span3" 
											placeholder="Preencha a Empresa associada." />
											
										<form:errors cssClass="native-error" path="empresa.nome" />

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
								}).focus();
			});
		</script>
				
	</body>
</html>
