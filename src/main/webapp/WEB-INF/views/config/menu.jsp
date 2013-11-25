<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul class="nav nav-tabs nav-sub-menu">	
	<sec:authorize url="/configuracoes/bases"> 
		<li class="active">
			<a href="<c:url value="/configuracoes/bases" />"><i class="cicon-group"></i> <span class="label-menu hidden-phone">Bases VPSA</span></a>
		</li>
	</sec:authorize>
	<sec:authorize url="/configuracoes/informantes"> 
		<li class="active">
			<a href="<c:url value="/configuracoes/informantes" />"><i class="cicon-nameplate"></i> <span class="label-menu hidden-phone">Informantes</span></a>
		</li>
	</sec:authorize>
	<sec:authorize url="/configuracoes/configuracao"> 
		<li class="active">
			<a href="<c:url value="/configuracoes/configuracao" />"><i class="cicon-cogwheel"></i> <span class="label-menu hidden-phone">Configurações</span></a>
		</li>
	</sec:authorize>
</ul>