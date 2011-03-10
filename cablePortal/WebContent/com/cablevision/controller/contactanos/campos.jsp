<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.cablevision.util.ConfigurationHelper"%>

<%@page import="com.cablevision.vo.CvLeadField"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="utils.system"%>
<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>

<jsp:scriptlet>
		com.bea.portlet.PageURL centros = com.bea.portlet.PageURL.createPageURL(request, response, "atencion_centros");
		com.bea.portlet.PageURL cotizador = com.bea.portlet.PageURL.createPageURL(request, response, "contrata_cotiza");
		centros.setTemplate("defaultDesktop");
		cotizador.setTemplate("defaultDesktop");
		centros.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		centros.setEncodeSession(false);
		cotizador.setForcedAmpForm(false);
		pageContext.setAttribute("centros",centros.toString());
		pageContext.setAttribute("cotizador",cotizador.toString());
	</jsp:scriptlet>

Entra en contacto con nosotros en cualquier momento, en CABLEVISION&reg; contamos con diferentes medios de contacto que aquí te presentamos, asegúrate de revisar los horarios de atención.
<br><br>


-	<strong>Por tel&eacute;fono:</strong> 51 699 699 de lunes a domingo de 7:00 AM a 11:00 PM <br>
-	<strong>V&iacute;a Web: Facebook y Twitter,</strong> de lunes a domingo de 7:00 AM a 4:00 PM <br>
-	<strong>En nuestros Centros de Atención:</strong> <a title="Haz clic aquí" class="color-orange" href="${centros }">Haz clic aquí</a><br><br>
-	<strong>O completa el siguiente formulario:</strong>

<br><br>

<div>
	<font color="Red">
		<netui-data:repeater dataSource="requestScope.errors">${container.item}<br/></netui-data:repeater>
	</font>
</div>

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

<form id="form_campos" action="${pageContext.request.contextPath}/com/cablevision/controller/contactanos/saveContact.do" class="formulario">
	<input type="hidden" value="${pageInput.leadType}" name="campo_leadType" id="campo_leadType"/> 
	<table width="95%" cellspacing="0" cellpadding="0" border="0" align="center" class="sinbordes">
		<tbody>
			<jsp:scriptlet>
				int count = 1;
				boolean latterHasTextarea = false;
			</jsp:scriptlet>
			<script type="text/javascript">
				var campos = new Array();
				var count = 0;
			</script>
			<c:forEach items="${pageInput.leadFields}" var="leadField">
				<jsp:scriptlet>
					CvLeadField leadField = (CvLeadField)pageContext.getAttribute("leadField");
					String isRequired = "";
					if(leadField.getId().getField().getCfsIsrequiredfield().toLowerCase().equals("si"))
						isRequired = "(*)";
					String fieldType = leadField.getId().getField().getCfsTypefield().toLowerCase();
					if(count == 1) {
						out.print("<tr>");
					}
					if(latterHasTextarea)
						count++;
					int rowspan = 1;
					if(fieldType.equals("textarea")) {
						rowspan = 2;
						latterHasTextarea = true;
					} else {
						latterHasTextarea = false;
					}
					
					CvLeadField lf = (CvLeadField)pageContext.getAttribute("leadField");
					String nombreCampo = lf.getId().getField().getCfsNamefield();
					String valor = (String)request.getSession().getAttribute("contactanos_"+nombreCampo);
					if(valor == null) valor = "";
					request.getSession().removeAttribute("contactanos_"+nombreCampo);
				</jsp:scriptlet>
					<td colspan="2" valign="top" rowspan="<jsp:expression>rowspan</jsp:expression>">
					<div id="div_${leadField.id.field.cfsNamefield}">
						<jsp:scriptlet>if(fieldType.equals("radio")) out.print("<div>");</jsp:scriptlet>
						<label>${leadField.id.field.cfsNamealias} <jsp:expression>isRequired</jsp:expression></label><br>
						<c:choose>
						<c:when test="${leadField.id.field.cfsTypefield eq 'TextArea'}">
							<textarea id="campo_${leadField.id.field.cfsNamefield}" name="campo_${leadField.id.field.cfsNamefield}" rows="" cols="" onkeypress="${leadField.id.field.cfsScripts}"><jsp:expression>valor</jsp:expression></textarea>
						</c:when>
						<c:otherwise>
							<jsp:scriptlet>
								if(fieldType.equals("radio") && leadField.getId().getField().getCfsAttrvalues()!=null) {
									String attrValues = leadField.getId().getField().getCfsAttrvalues();
									String[] avArray = StringUtils.split(attrValues, '|');
									String[] labelValue1 = StringUtils.split(avArray[0], ':');
									String[] labelValue2 = StringUtils.split(avArray[1], ':');
									String label1 = labelValue1[0];
									String value1 = labelValue1[1];
									String label2 = labelValue2[0];
									String value2 = labelValue2[1];
									
									String checked1 = "";
									String checked2 = "";
										
									if(valor.toLowerCase().equals(value2.toLowerCase()))
										checked2 = "checked=\"checked\"";
									else
										checked1 = "checked=\"checked\"";
							</jsp:scriptlet>
									<input <jsp:expression>checked1</jsp:expression> type="radio" name="campo_${leadField.id.field.cfsNamefield}" id="campo_${leadField.id.field.cfsNamefield}1" class="marg-top10" value="<jsp:expression>value1</jsp:expression>"/><jsp:expression>label1</jsp:expression>
									<input <jsp:expression>checked2</jsp:expression> type="radio" name="campo_${leadField.id.field.cfsNamefield}" id="campo_${leadField.id.field.cfsNamefield}2" class="marg-top10" value="<jsp:expression>value2</jsp:expression>"/><jsp:expression>label2</jsp:expression>
							<jsp:scriptlet>
								} else {
							</jsp:scriptlet>
									<input type="<jsp:expression>fieldType</jsp:expression>" id="campo_${leadField.id.field.cfsNamefield}" name="campo_${leadField.id.field.cfsNamefield}" class="big" onkeypress="${leadField.id.field.cfsScripts}" value="<jsp:expression>valor</jsp:expression>"/>
							<jsp:scriptlet>
								}
							</jsp:scriptlet>
						</c:otherwise>
						</c:choose>
						<jsp:scriptlet>if(fieldType.equals("radio")) out.print("</div>");</jsp:scriptlet>
					</div>
					</td>
				<jsp:scriptlet>
					if(count == 1) {
						count++;
					} else {
						out.print("</tr>");
						count = 1;
					}
				</jsp:scriptlet>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- inicio captcha -->
 	<script type="text/javascript">
        var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
        Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom', lang : 'es', custom_theme_widget: 'recaptcha_widget'});
    </script>
    <div align="center">
    <div class="wrap-captcha" id="recaptcha_widget">
        <hr />
        <div class="captcha" id="recaptcha_image"></div>
        <br />
        
        <label for="captchaText" class="color-orange">Escribe ambas palabras:</label>
        <div class="fix-top">
            <input type="text" id="recaptcha_response_field" name="recaptcha_response_field" value="" size="40" style="width:180px" />
        </div>
        <div>
            <a href="javascript:Recaptcha.reload();" title="Intentar otro" class="inteo-icon hidden-text">Intentar otro</a>
        </div>
        <div class="recaptcha_only_if_image">
            <a href="javascript:Recaptcha.switch_type('audio');" title="Discapacidad visual" class="discv-icon hidden-text" >Discapacidad visual</a>
        </div>
        <div class="recaptcha_only_if_audio">
            <a href="javascript:Recaptcha.switch_type('image');" title="Regresar a version de palabras" class="text-icon hidden-text" >Regresar a versión de palabras</a>
        </div>
        <div>
            <a href="javascript:Recaptcha.showhelp();" title="Ayuda" class="help-icon hidden-text">Ayuda</a>
        </div>
        
        
        <div class="clear"></div>
        <p class="captchaPar">La verificaci&oacute;n de c&oacute;digo nos permite confirmar que tu registro se realize de manera segura y confiable asi como para mantener la privacidad de tu informaci&oacute;n.</p>
    </div>
    </div>
    <!-- fin captcha -->
    
    <div class="clear"></div> 
 	
 	(*) Campos obligatorios
	<a class="btn-small marg-rig40 margin-bot15 fright" title="Enviar" href="#" id="submit">Enviar</a>
	<br><br><br>
</form>

<script type="text/javascript">
$(function(){
	var select = '#'+getJqId('<netui:rewriteName name="actionForm_asunto" forTagId="true" resultId="actionForm_asunto"/>');
	
	$('input[name="campo_Cliente"]').click(function(){
		var field1 = $('#div_No_contrato');
		var field2 = $('#div_Tel_cable');
		if(this.value=='Si'){
			field1.show();
			field2.show();
		}else{
			field1.hide();
			field2.hide();
		}
	})
	
	$('#submit').click(function(){
		if($(select).val() == 4){
			alert("Seleccione otro Asunto");
			return false;
		}
	
		var data = $('#form_campos').serialize();
		data = data.replace(/%7B/g,'');
		data = data.replace(/%7D/g,'');
		data = data.replace(/(\w)*campo\_/g,'');
	
		$.ajax({ 
			url: '${pageContext.request.contextPath}/com/cablevision/controller/contactanos/saveContact.do',
			beforeSend: function(objeto){
				$('#campos').html();
				$('#campos').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
			},
			dataType: "html", 
			context: document.body, 
			data: data,
			type: 'POST',
			success: function(results){
        		$('#campos').html(results);
      		}
      	});
		return false;
	})
	
	$(select).change(function(){
		var value = $(this).val();
		if(value=='4' || value=='6'){
			window.location = "${cotizador}";
			return false;
		}
		$('#campo_leadType').val(value);
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
	})

	$(document).ready(function() {
		var val1 = $('#campo_Cliente1').attr('checked');
		var field1 = $('#div_No_contrato');
		var field2 = $('#div_Tel_cable');
		if(val1){
			field1.show();
			field2.show();
		}else{
			field1.hide();
			field2.hide();
		}
	});
	
	function getJqId(myid){ 
		return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
	}
});


function redirect(val) {
	if(val == '4')
		document.location = "${cotizador}";
} 
</script>