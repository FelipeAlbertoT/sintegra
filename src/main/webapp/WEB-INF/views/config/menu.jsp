<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul class="nav nav-tabs nav-sub-menu">	
	<sec:authorize url="/configuracoes/informante"> 
		<li class="active">
			<a href="<c:url value="/configuracoes/informante" />"><i class="cicon-nameplate"></i> <span class="label-menu hidden-phone">Informante</span></a>
		</li>
	</sec:authorize>
</ul>