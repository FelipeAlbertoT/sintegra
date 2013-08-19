<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
	<link
		href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css"
		rel="stylesheet">
	
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
    	<script src=".${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
	<![endif]-->
	
	<style type="text/css">
	body {
		padding-top: 40px;
		padding-bottom: 40px;
		background-color: #f5f5f5;
	}
	</style>

</head>

<body>

	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4>Aguarde!</h4>
		Estamos realizando a autenticação do usuário.
	</div>

	<form name="login_form" id="login_form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
		<input type="hidden" id="j_username" name="j_username" value="${j_username}"> 
		<input type="hidden" id="j_password" name="j_password" value="${j_password}">
	</form>

	<script type="text/javascript">
		$('#login_form').submit();
	</script>

</body>

</html>