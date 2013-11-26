$(document).ready(function () {

	configProc.currentEnt = parseInt($('#nent').val() - 1);	
	configProc.currentNome = $('#nNome').val();
});

configProc = {
	
	currentNome: null,
	
	currentEnt : null,		
	
	addEntidade : function () {
		
		if($('#tempEnt\\.id').val() == '')
			return;
		
		this.currentEnt++;			
		
		var copy = '<div class="controls">'+						
						'<input id="'+this.currentNome+''+this.currentEnt+'.id" name="'+this.currentNome+'['+this.currentEnt+'].id" type="hidden" value="'+$('#tempEnt\\.id').val()+'">'+
						'<input id="'+this.currentNome+''+this.currentEnt+'.nome" name="'+this.currentNome+'['+this.currentEnt+'].nome" class="span3 entidade" readonly="readonly" type="text" value="'+$('#tempEnt\\.nome').val()+'"> '+
						'<button type="button" id="del'+this.currentEnt+'" class="btn" onclick="configProc.removeEntidade('+this.currentEnt+');"><i class="icon-minus"></i></button>'+
					'</div>';

		$("#"+this.currentNome).append(copy);		
		
		$('#tempEnt\\.id').val("");
		$('#tempEnt\\.nome').val("");
		
		project.applyMasks();
	},	
	
	removeEntidade : function (idx) {
				
		var cNome = this.currentNome;
		
		$("#"+cNome).find('.controls').get(idx + 1).remove();		
		
		this.currentEnt--;
		
		var re1 = new RegExp(cNome+"\\[[0-9]+\\]", 'gi');
		var re2 = new RegExp(cNome+"[0-9]+", 'gi');
		
		$("#"+cNome).find('.controls').each(function(index, value){

			if(index <= idx)
				return;
			
			copy = $(value).html();
			copy = copy.replace(re1, cNome+'[' + (index - 1) + ']');
			copy = copy.replace(re2, cNome+ (index - 1));
			copy = copy.replace(/del[0-9]+/gi, 'del' + (index - 1));
			copy = copy.replace(/removeEntidade\([0-9]+\)/gi, 'removeEntidade(' + (index - 1) + ')');
			
			$(value).html(copy);
		});		
	},
	
	habilitarEntidades : function () {				
		if($('#entidade\\.id').val() != "") {
			$('#tempEnt\\.nome').attr('disabled',false);
		}		
	},
	
	validarForm : function () {
		
		if($('#tempEnt\\.id').val() != ""){
			
			$("#modalEntAlert").modal();
			
			return false;
		}
		
		return true;
	},
	
	ignoreEntAndContinue : function () {
		$('#tempEnt\\.id').val("");
		$('#configuracao').submit();
	}
};