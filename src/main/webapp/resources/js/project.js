project = {
		
	initialize : function () {
		
		/* Escondendo os erros nativos do spring validation e criando ícones com title associado ao erro. */
		$('.native-error').each(function (index){
			var fieldName = '#' + this.id.replace(".errors", "").replace(/\./g, '\\.');
			$(fieldName).addClass("cerror");
		});
		
		$('.native-error-message').each(function (index){
			var fieldName = '#' + this.id.replace(".errors", "").replace(/\./g, '\\.');
			$(fieldName).addClass("cerror");
		});
		
		$('body').tooltip({
			selector: "a[data-toggle=tooltip]"
		});	
		
		this.selectedMenu();
		
		this.applyMasks();
	},
	
	selectedMenu : function () {
		
		/* Remove a propriedade active de todos os itens de navegação */
		$('.nav-sub-menu > li > a, .nav-top-menu > li > a').each(function (index){
			$(this).parent().removeClass("active");	
		});
		
		/* Atribui a propriedade active para todos os links que contiverem o path da URL do browser ou a parte inicial dele */
		$('.nav-sub-menu > li > a, .nav-top-menu > li > a').each(function (){
			if(window.location.pathname.indexOf($(this).attr('href')) > -1)
				$(this).parent().addClass("active");
		}); 
	},
	
	applyMasks : function () {
		$(".date").mask("99/99/9999");
		$(".areaphone").mask("999");
		$(".ecf").mask("999");
		$(".cnpj").mask("99.999.999/9999-99");
		$(".logicocielo").mask("99999999-9");
		$(".logicoredecard, .logicobanrisul, .logicogetnet").mask("999999999999999");
		$(".terminalgetnet").mask("T9999999");
		$(".empresasitef").mask("99999999");	
		$(".informantephone").mask("99999999999?9");
	}
};

$(document).ready(function () {
	project.initialize();
});