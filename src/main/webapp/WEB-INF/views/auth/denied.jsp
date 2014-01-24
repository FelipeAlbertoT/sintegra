<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
 
<head>
	<title>Aviso do Sistema</title>
</head>

<body>

	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>Acesso Negado!</h4>
		<p></p>
		<p>Você não tem permissão para acessar este recurso.</p>
		<p>Solicite a liberação de acesso ao administrador do sistema.</p>
	</div>

</body>

</html>