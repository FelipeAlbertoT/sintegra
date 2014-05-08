<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>  
	<head>
		<title>Cadastro de Redução Z</title>
		
		<script type="text/javascript"	src="${pageContext.request.contextPath}/resources/js/configuracao.js"></script>
	</head>
	<body>
		<div class="row-fluid">
		  	<div class="span12">

				<form:form cssClass="form-horizontal" onsubmit="return validaForm();" action="${pageContext.request.contextPath}/reducao/nova/salvar" commandName="reducaoZ" method="post">

					<fieldset>

						<legend>Cadastro de Redução Z</legend>

						<div class="control-group">

							<form:hidden path="id" />
							<form:hidden path="portal.id" />

							<c:if test="${message != '' and message != null}">
								<div class="alert">${message}</div>
							</c:if>

						</div>

						<div class="control-group">
							<label class="control-label" path="entidade" for="entidade">Entidade</label>
							<div class="controls">
								<form:select path="entidade.id" items="${entidades}" itemLabel="nome" itemValue="id"></form:select>
								<form:errors cssClass="native-error" path="entidade" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="numeroSerieECF">Número Série ECF</label>
							<div class="controls">
								<form:input cssClass="span3" path="numeroSerieECF" placeholder="Número de Série" />
								<form:errors cssClass="native-error" path="numeroSerieECF" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="crz">Número Sequêncial ECF</label>
							<div class="controls">
								<form:input cssClass="span3" path="numeroSequencialECF" placeholder="Número Sequencial" />
								<form:errors cssClass="native-error" path="numeroSequencialECF" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="dataMovimento">Data Movimento</label>
							<div class="controls">
								<form:input cssClass="span3 date" path="dataMovimento" data-date-format="dd/mm/yyyy" placeholder="Data do Movimento" />
								<form:errors cssClass="native-error" path="dataMovimento" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="coo">COO</label>
							<div class="controls">
								<form:input cssClass="span3" path="coo" placeholder="Digite o COO" />
								<form:errors cssClass="native-error" path="coo" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="cooInicial">COO Inicial</label>
							<div class="controls">
								<form:input cssClass="span3" path="cooInicial" placeholder="Digite o COO Inicial" />
								<form:errors cssClass="native-error" path="cooInicial" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="cooFinal">COO Final</label>
							<div class="controls">
								<form:input cssClass="span3" path="cooFinal" placeholder="Digite o COO Final" />
								<form:errors cssClass="native-error" path="cooFinal" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="coo">CRO</label>
							<div class="controls">
								<form:input cssClass="span3" path="cro" placeholder="Digite o CRO" />
								<form:errors cssClass="native-error" path="cro" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="crz">CRZ</label>
							<div class="controls">
								<form:input cssClass="span3" path="crz" placeholder="Digite o crz" />
								<form:errors cssClass="native-error" path="crz" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="totalizadorGeral">Totalizador Geral</label>
							<div class="controls">
								<form:input cssClass="span3 real" path="totalizadorGeral" placeholder="Totalizador Geral" />
								<form:errors cssClass="native-error" path="totalizadorGeral" />
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="vendaBrutaDiaria">Venda Bruta Diária</label>
							<div class="controls">
								<form:input cssClass="span3 real" path="vendaBrutaDiaria" placeholder="Venda Brutá Diária" />
								<form:errors cssClass="native-error" path="vendaBrutaDiaria" />
							</div>
						</div>
						
						<div id="entidades" class="control-group">
						
							<label class="control-label">Totalizadores de Imposto</label>

							<div class="controls">
								
								<table id="totalizadores">
								
									<tr>
										<td>Código</td>
										<td>Valor</td>
									</tr>
									
									<c:forEach items="${reducaoZ.totalizadores}" var="totalizador" varStatus="status">
										<tr>
											<td><form:input path="totalizadores[${status.index}].codigo" /></td>
											<td><form:input cssClass="real" path="totalizadores[${status.index}].valorAcumulado" /></td>
										</tr>
									</c:forEach>	
																									
								</table>
								
								<input type="button" onClick="javascript: addTotalizador()" value="Novo Totalizador" />
							</div>
						</div>
						
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Confirmar</button>
						</div>

					</fieldset>

				</form:form>
				
			</div>
		</div>	

		<script type="text/javascript">
		
			function validaForm(){
			
				$(".real").each(function () {
					$(this).val($(this).val().replace('.', '').replace(',', '.'));
				});
				
				return true;
			}
		
			function addTotalizador(){
				
				var rowCount = $('#totalizadores tr').length - 1;
				
				$('#totalizadores tr:last').after('<tr>'+
						'<td><input type="text" value="" id="totalizadores'+rowCount+'.codigo" name="totalizadores['+rowCount+'].codigo" /></td>'+
						'<td><input type="text" class="real" id="totalizadores'+rowCount+'.valorAcumulado" name="totalizadores['+rowCount+'].valorAcumulado" /></td>'+
					'</tr>');
				
				project.applyMasks();
			}
		
			$(function() {
				
				$('#dataMovimento').datepicker({
					format : 'dd/mm/yyyy',
					todayBtn : 'linked'
				}).on('changeDate', function(ev) {
					checkout.hide();
				}).data('datepicker');
				
			});
		</script>
				
	</body>
</html>
