<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>

    <title>
    	<decorator:title />
    </title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">		
		
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/project.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/project.js"></script>	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.min.js"></script>
	
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src=".${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
    <![endif]-->
       
    <decorator:head/>
    
</head>

<body>

	<header>
		<div class="navbar navbar-fixed-top">
	      	<div class="navbar-inner">
	        	<div class="container">
		          	<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
		            	<span class="icon-bar"></span>
		            	<span class="icon-bar"></span>
		            	<span class="icon-bar"></span>
		          	</button>
		          	
		          	<a href="<c:url value="/home/" />" ><i class="logotipo-mini"></i></a>
		          	
		          	<div class="nav-collapse collapse">
		            	
		            	<ul class="nav nav-top-menu">      
		            		<%-- <sec:authorize url="/">    --%>	
				             	<li class="">
				                	<a href="<c:url value="/home" />">Home</a>
				              	</li>
			              	<%-- </sec:authorize> --%>
		            	</ul>
		            	
		            	<ul class="nav nav-top-menu pull-right">
		            	
		            		<sec:authorize access="isAuthenticated()">
								<li class="visible-desktop">							
									<a href="/usuario/perfil"><i class="icon-user"></i></a>								
								</li>	
								<li class="visible-phone">							
									<a href="/usuario/perfil">Meu Perfil</a>								
								</li>		
			              	</sec:authorize>
			              	
					    	<sec:authorize access="isAuthenticated()">				    	
					   			<li>					   			
					   				<a href="<c:url value="/j_spring_security_logout" />"><i class="icon-off visible-desktop"></i> <span class="hidden-desktop">Sair do Sistema</span></a>					   			
					   			</li>
					   		</sec:authorize>	
                    	</ul>
		          	</div>
		        </div>
	      	</div>
	    </div>
	</header>
	
	<div class="container">
		<decorator:body/>
	</div>
	
</body>
</html>