<%@ page language="java" contentType="text/html;charset=UTF-8"%><%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%><%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%><%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%><%@ page import="com.cablevision.util.ConfigurationHelper"%>
<netui:scriptContainer>
	<jsp:scriptlet>
		com.bea.portlet.PageURL centros = com.bea.portlet.PageURL.createPageURL(request, response, "atencion_centros");
		com.bea.portlet.PageURL cotizador = com.bea.portlet.PageURL.createPageURL(request, response, "contrata_cotiza");
		centros.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		centros.setEncodeSession(false);
		cotizador.setForcedAmpForm(false);
		pageContext.setAttribute("centros",centros.toString());
		pageContext.setAttribute("cotizador",cotizador.toString());
	</jsp:scriptlet>
	<div id="thisistop" class="span-18 last">
			<div class="wrap-cont-simple wcs-padd">
				<h2 class="side ico-micta margin-bot15">Contáctanos</h2>
					
				<div id="campos">
					Entra en contacto con nosotros en cualquier momento, en CABLEVISION&reg; contamos con diferentes medios de contacto que aquí te presentamos, asegúrate de revisar los horarios de atención.
					<br><br>
					
					<!-- -	<strong>Por mail:</strong> 	<a class="color-orange" href="mailto:info@cable.com" title="info@cable.com">info@cable.com</a><br /> -->
					-	<strong>Por tel&eacute;fono:</strong> 51 699 699 De lunes a domingo de 7:00 AM a 11:00 PM<br>
					-	<strong>V&iacute;a Web: Facebook y Twitter,</strong> de lunes a domingo de 7:00 AM a 4:00 PM<br>
					-	<strong>En nuestros Centros de Atención:</strong> <a title="Haz clic aquí" class="color-orange" href="${centros }">Haz clic aquí</a><br><br>
					-	<strong>O completa el siguiente formularios:</strong>
		
					<br><br>
				
					<netui:form action="saveContact" tagId="contact_form" styleClass="formulario"> 
						<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center" class="sinbordes">
							<tbody>
								<tr>
									<td>      
										<netui:label for="actionForm_asunto" value="Asunto (*)" /><br>
										<netui:select dataSource="actionForm.asunto" optionsDataSource="${pageInput.leadTypesMap}" tagId="actionForm_asunto" />
									</td>
								</tr>
							</tbody>
						</table>
					</netui:form>
				</div>
				
		</div>	
	</div>

<script type="text/javascript">
$(function(){

	var formId = '#'+getJqId('<netui:rewriteName name="contact_form" forTagId="true" resultId="contact_form"/>');
	var selectId = '#'+getJqId('<netui:rewriteName name="actionForm_asunto" forTagId="true" resultId="actionForm_asunto"/>');

	$(selectId).change(function(){
		getForm();
	})

	function getJqId(myid){ 
		return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
	}
	
	function getForm() {
		var value = $(selectId).val();
		if(value=='4' || value=='6'){
			window.location = "${cotizador}";
			return false;
		}
		$.ajax({ 
			url: '${pageContext.request.contextPath}/com/cablevision/controller/contactanos/getCampos.do',
			beforeSend: function(objeto){
				$('#campos').html();
				$('#campos').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
			},
			dataType: "html", 
			context: document.body, 
			data: 'asunto=' + value,
			type: 'POST',
			success: function(results){
        		$('#campos').html(results);
        		$('.recaptcha_content').show();
      		}
      	});
	}

	$(document).ready(function() {
		getForm();
	});
});

function redirect(val) {
	if(val == '4')
		document.location = "${cotizador}";
} 
</script>


</netui:scriptContainer>
