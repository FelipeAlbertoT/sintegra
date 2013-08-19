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
							<div class="tool-top">
								<div class="hbtns btn-toolbar">								
								
					             	<div class="btn-group">
					             	
					             		<a class="btn btn-success dropdown-toggle" data-toggle="dropdown" href="#">
										    <i class="icon-user icon-white"></i>
										    <span class="hidden-phone"> <sec:authentication property="principal.username" /> &nbsp;</span>
										    <span class="caret"></span>
										</a>
					
						                <ul class="dropdown-menu pull-right">
						                	<sec:authorize access="hasRole('ROLE_ADMIN')">
									    		<li><a href="<c:url value="#" />"><i class="icon-wrench"></i> &nbsp; <spring:message code="settingsLabel"/></a></li>
									    		<li class="divider"></li>
									    	</sec:authorize>						    	
									   		<li>
									   			<a href="<c:url value="/j_spring_security_logout" />"><i class="icon-power"></i> &nbsp; <spring:message code="logoutLinkLabel"/></a>
									   		</li>	
									  	</ul>
						             </div>
						    	 </div>
					         </div>
						</div>
				    </div>
              	</div>
            </div>
	  	</div>	  	
	</header>
	
	<div class="container">	
		<div class="row">
			<div class="span12">	
				<div class="navbar top-menu">
               		<div class="container">
               		
                 		<a class="btn btn-navbar btn-top-menu" data-toggle="collapse" data-target=".nav-collapse">
                   			<spring:message code="navBarTitle"/> <i class="icon icon-chevron-down"></i>
	                  	</a>
	                  	
	                  	<div class="nav-collapse collapse">
	                  		
	                    	<ul class="nav nav-tabs nav-stacked">
	                    	
	                      		<li class="active">
	                      			<a href="<c:url value="/" />"><spring:message code="homePageTitle"/></a>
	                      		</li>
	                      		
	                      		<li>
	                      			<a href="#">Option 1</a>
	                      		</li>
	                      		
	                      		<li class="dropdown">
	                      		
								 	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								    	Option 2
								    </a>
								    
								    <ul class="dropdown-menu">
								    	<sec:authorize access="hasRole('ROLE_USER')">
								      		<li><a href="<c:url value="#" />">Opt User</a></li>
								      	</sec:authorize>
								      	
								      	<sec:authorize access="hasRole('ROLE_ADMIN')">
								      		<li><a href="<c:url value="#" />">Opt Admin</a></li>
								      	</sec:authorize>
								    </ul>
								    
								</li>
								
								<li>
	                      			<a href="#">Option 3</a>
	                      		</li>
	                      		
	                    	</ul>
	                    </div>
	                </div>
	            </div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="span12">
	 			<decorator:body/>
	 		</div>
	 	</div>
	</div>
	    
</body>
</html>