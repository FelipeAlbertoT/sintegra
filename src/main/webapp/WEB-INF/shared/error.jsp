<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

	<head>
		<title>Mensagem do Sistema</title>
	</head>
	
	<body>	 
		<c:if test="${message != '' and message != null}">
			<div class="alert alert-error">${message}</div>
		</c:if>
		
		<c:if test="${exception != null}">
		
			<div class="page-header">
			
				<div class="hero-unit">
				  	<p>Erro no processamento!</p>			  	
				  	<p><small>
				   		<div class="alert alert-error">${exception}</div>
				  	</small></p>
				</div>
				
			</div>
			
		</c:if>
		
	</body>
	
</html>