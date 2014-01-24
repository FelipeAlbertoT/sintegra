<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>

    <title>
    	<decorator:title />
    </title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">		
		
	<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-responsive.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/project.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/DT_bootstrap.css" />
	<link rel="stylesheet" type="text/css"	href="/resources/css/datepicker.css" />
	
	<script type="text/javascript" src="/resources/js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="/resources/js/project.js"></script>	
	
	<script type="text/javascript" src="/resources/js/jquery.maskedinput.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.maskMoney.min.js"></script>
		
	<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/resources/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="/resources/js/kapp.dataTables.js"></script>

	<script type="text/javascript" src="/resources/js/noty/jquery.noty.js"></script>
	<script type="text/javascript" src="/resources/js/noty/top.js"></script>	
	<script type="text/javascript" src="/resources/js/noty/topCenter.js"></script>	
	<script type="text/javascript" src="/resources/js/noty/default.js"></script>
	<script type="text/javascript"	src="/resources/js/bootstrap-datepicker.js"></script>
 
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/resources/js/html5shiv.js"></script>
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
		            		<sec:authorize url="/home">    	
				             	<li class="">
				                	<a href="<c:url value="/home" />">Home</a>
				              	</li>
			              	</sec:authorize>      
		            		<sec:authorize url="/configuracoes/">    	
				             	<li class="">
				                	<a href="<c:url value="/configuracoes/" />">Configurações</a>
				              	</li>
			              	</sec:authorize>
		            	</ul>
		            	
		            	<ul class="nav nav-top-menu pull-right">
		            	
		            		<sec:authorize access="isAuthenticated()">
		            			<li class="divider-vertical"></li>	
								<li class="visible-desktop">							
									<a href="#"><i class="icon-user"></i>  &nbsp; <span class="text-success">${currentLoggedUser.nome}</span></a>								
								</li>	
			              	</sec:authorize>
			              	
					    	<sec:authorize access="isAuthenticated()">
					    	
								<li class="divider-vertical"></li>
								
								<li id="sync" data-placement="bottom" rel="popover" data-trigger="manual" data-content="" data-original-title="Status de Sincronização">
									<a href="javascript: syncControlObj.syncManual();">
										<i class="icon-refresh visible-desktop"></i><span class="hidden-desktop">Sincronizar com VPSA</span>
									</a>								
								</li>
								
								<li class="divider-vertical"></li>		
									    	
					   			<li>					   			
					   				<a href="<c:url value="/j_spring_security_logout" />" title="Sair do Sistema"><i class="icon-off visible-desktop"></i> <span class="hidden-desktop">Sair do Sistema</span></a>					   			
					   			</li>
					   			
					   			<li class="divider-vertical"></li>
					   			   
					   		</sec:authorize>	
                    	</ul>
		          	</div>
		        </div>
	      	</div>
	    </div>
	</header>

	
	
	<div class="container master">
	
		<decorator:body/>

	</div>
	
	<script type="text/javascript">

		syncControlObj = {
				
			popoverShow : false,
			
			syncImg : null,  
			
			initialize : function () {
				
				syncControlObj.syncImg = $("#sync > a > i ");				
				syncControlObj.updateSyncImg(true);
				
			},
			
			updateSyncLabels : function (data) {
				
				$('#sync').popover('destroy');

				if(data) {
					
					$("#sync").attr("data-content", "Sincronização em andamento!");
					
					if($(syncControlObj.syncImg).hasClass("icon-refresh"))
						$("#sync > a > i ").removeClass("icon-refresh");
					
					if(!$(syncControlObj.syncImg).hasClass("cicon-refresh-in-progress"))
						$("#sync > a > i ").addClass("cicon-refresh-in-progress");
			  	}
			  	else{
			  		
			  		$("#sync").attr("data-content", "Aplicativo sincronizado a menos de 15 minutos!");
					
					if($(syncControlObj.syncImg).hasClass("cicon-refresh-in-progress"))
						$("#sync > a > i ").removeClass("cicon-refresh-in-progress");
			  		
			  		if(!$(syncControlObj.syncImg).hasClass("icon-refresh"))
						$("#sync > a > i ").addClass("icon-refresh");		  		
			  	}	
				
				if(syncControlObj.popoverShow) {
					
					$('#sync').popover('show');
					
					setTimeout(function () {
						$('#sync').popover('destroy');
				    }, 3000);
					
					syncControlObj.popoverShow = false;
				}
			},
			
			updateSyncImg : function (runLoop){
				
				$.get('${pageContext.request.contextPath}/api/sync/status', function(data) {
					syncControlObj.updateSyncLabels(data);
				});
		
				if(runLoop)
					setTimeout('syncControlObj.updateSyncImg(true)', 10000);
			},
			
			syncManual : function (){
				
				$.get('${pageContext.request.contextPath}/api/sync/manual', function(data) {
					syncControlObj.popoverShow = true;
					syncControlObj.updateSyncImg(false);		  	
				});
			}
		};
	
		$(document).ready(function(){
			syncControlObj.initialize();
		});
		

	</script>
	
</body>
</html>
