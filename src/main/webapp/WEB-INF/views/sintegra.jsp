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

	<style type="text/css">

            .scrollable {
                height: 100%;
                overflow: auto;
            }
            
    </style>

</head>
<body>
	<div class="row-fluid">
		<div class="span12">

			<fieldset>

				<legend> Geração do Sintegra - Arquivo Gerado </legend>

				<div class="control-group">
					<c:if test="${message != '' and message != null}">
						<div class="alert alert-error">${message}</div>
					</c:if>
				</div>
				
				<form class="form-inline">
					<a href="<c:url value="/home/download/${sintegra.id}" />" target="_blank" class="btn btn-primary">Salvar</a>	
				</form>

				<div class="control-group">
					<pre class="pre-scrollable" >${sintegra.sintegra}</pre>
				</div>
				
			</fieldset>

		</div>
	</div>
</body>
</html>
