<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>

<title>Abertura de Chamados</title>

</head>

<body>

	<div class="row">
		<div class="span12">

			<form:form cssClass="form-horizontal" action="${pageContext.request.contextPath}/util/issue/cadastro/salvar" commandName="gitHubIssue" method="post">

				<fieldset>

					<legend>Abertura de Chamados </legend>
					
						<div class="alert alert-info">
						  	<h5>Encontrou um erro no aplicativo?</h5>	 	 	  	
						  	<p><small>Envie-nos os detalhes do erro, indicando os passos para reproduzí-lo. Em breve seu problema será corrigido.</small></p>
						</div> 
						
					
					<div class="control-group">
						<c:if test="${message != '' and message != null}">
							<div class="alert alert-error">${message}</div>
						</c:if>
					</div>
					
					<div class="control-group">
						<c:if test="${messageSuccess != '' and messageSuccess != null}">
							<div class="alert alert-success">${messageSuccess}</div>
						</c:if>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="issueTitle">Título</label>
						<div class="controls">
							<form:input path="issueTitle" cssClass="span3" placeholder="Título do Chamado" />
							<form:errors cssClass="native-error" path="issueTitle" />
						</div>
					</div>
					
					<div class="control-group"> 
						<label class="control-label" for="message">Mensagem</label>
						<div class="controls">
							<form:textarea path="message" cssClass="span6" rows="5" placeholder="Mensagem do Chamado" />
							<form:errors cssClass="native-error" path="message" />
						</div>
					</div>

					<div class="form-actions">
						<button type="submit" class="btn btn-primary">Salvar</button>
					</div>

				</fieldset>

			</form:form>

		</div>
	</div>
	
</body>
</html>
