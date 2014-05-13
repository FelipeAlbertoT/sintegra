<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>  
	<head>
		<title></title>
	</head>
	<body>
		<div class="row">
		  	
		  	<div class="span12">
		  	
				<div class="tabbable">

					<div class="tab-content">
					
						<fieldset>
	
							<legend></legend>
	
							<div class="control-group">
							
								<form:form cssClass="form-horizontal" action="${pageContext.request.contextPath}/importador/sintegra/processar" commandName="sintegraTemplate" method="post">
							 
									<div class="control-group">
								    	<label>Dados da Planilha</label>
								    	<div>
								    		<form:textarea cssClass="span12" path="data" rows="10" cols="20"  />
								    	</div>
								  	</div>
								  			
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Processar</button>
									</div>
								</form:form>
							</div>
						
						</fieldset>					
					</div>
				</div>
			</div>
		</div>	
	</body>
</html>
