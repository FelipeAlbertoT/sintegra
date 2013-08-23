<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>

    <title>
    	<decorator:title default="Canal VPSA"/>
    </title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">		
		
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/project.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/project.js"></script>	
	
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src=".${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
    <![endif]-->
        
    <decorator:head/>
    
</head>

<body>
 
    <header class="jumbotron subhead" id="overview">
	 	<div class="container">	 	
	 		<div class="row-fluid">
              	<div class="span12" data-original-title="" title="">
              		<div class="navbar ">
				         <div class="navbar-inner">   
					         <ul class="nav">
							  	<li class="active">
							    	<div class="logotipo" style="padding-bottom: 10px;"></div>  
							  	</li>
							</ul>
				         </div>
				    </div>
              	</div>
            </div>
	  	</div>	  	
	</header>
 
    <div class="container">
		<div class="row">
			<div class="span12">
	 			<decorator:body/>
	 		</div>
	 	</div>
	</div>
 
</body>
</html>