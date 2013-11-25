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
								
								<div class="form-actions">
									<button type="submit" class="btn btn-primary">Confirmar</button>									
								</div>
	
							</fieldset>
	
						</form:form>
	
					</div>
				</div>
			</div>
		</div>			
	</body>
</html>
