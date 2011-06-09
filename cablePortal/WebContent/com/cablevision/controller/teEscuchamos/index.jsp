<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.cablevision.util.ConfigurationHelper"%>

<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>

<div id="thisistop" class="span-18 last">
	<div class="wrap-cont-simple wcs-padd">
		<div class="wcs-marg">
				<h2 class="side ico-micta margin-bot15">Te escuchamos</h2>
				Para CABLEVISION&reg; es muy importante conocer tus sugerencias,  quejas y/o comentarios los cuales nos ayudan a brindarte un mejor servicio as&iacute; como mejorar la calidad de los mismos, por esta raz&oacute;n tus comentarios son de mucho valor y ser&aacute;n atendidos por nuestro Centro de Atenci&oacute;n.
				<br><br><br>
				
				<netui:form action="saveLead" tagId="ationForm_guardarTeEscuchamos" enctype="multipart/form-data">
					<strong>Deja tu comentario</strong>
					<div class="clear"></div>
					<div>
						<font color="Red">
							<netui-data:repeater dataSource="requestScope.errors">${container.item}<br/></netui-data:repeater>
						</font>
					</div>
					<div class="span-8 ">
						<netui:label styleClass="label-solos" value="Asunto (*)" for="form_asunto"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:select dataSource="actionForm.asunto" tagId="form_asunto">
								<netui:selectOption value="1">Queja</netui:selectOption>
								<netui:selectOption value="5">Sugerencia</netui:selectOption>
								<netui:selectOption value="18">Comentario</netui:selectOption>
								<netui:selectOption value="23">Duda</netui:selectOption>
							</netui:select>
						</div>
					</div>	
					<div class="clear"></div>
					<netui:hidden dataSource="actionForm.challenge" tagId="actionForm_challenge"/>
					<netui:hidden dataSource="actionForm.uresponse" tagId="actionForm_uresponse"/>
					<div class="span-8 ">
						<netui:label styleClass="label-solos" value="Nombre (*)" for="form_nombre"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.nombre" size="48" tagId="form_nombre"/>
						</div>
						<label class="label-solos" for="form_esCliente">¿Eres cliente de CABLEVISION®? (*)</label><br>
						<div class="text-side-sinborder marg-rig15 margin-bot0">
							<netui:radioButtonGroup dataSource="actionForm.esCliente" defaultValue="Si">
								<netui:radioButtonOption value="Si" styleClass="marg-top10"/>
								<netui:radioButtonOption value="No" styleClass="marg-top10"/>
							</netui:radioButtonGroup>
						</div>
						<label class="label-solos" for="form_email">Correo electr&oacute;nico (*)</label><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.email" size="48" tagId="form_email"/>
						</div>
						<label class="label-solos" for="form_domicilio">Domicilio </label><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.domicilio" size="48" tagId="form_domicilio"/>
						</div>
						<label class="label-solos" for="form_cp">Código Postal (*)</label><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.cp" size="48" tagId="form_cp"/>
						</div>
						<label class="label-solos" for="form_estado">Estado </label><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.estado" size="48" tagId="form_estado"/>
						</div>
					</div>							
					<div class="span-8 ">
						<netui:label styleClass="label-solos" value="Apellidos (*)" for="form_apellidos"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.apellidos" size="48" tagId="form_apellidos"/>
						</div>
						<label class="label-solos" for="form_numContrato">No. Contrato</label><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.numContrato" size="48" tagId="form_numContrato"/>
						</div>
						<netui:label styleClass="label-solos" value="Teléfono " for="form_telefono"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.telefono" size="48" tagId="form_telefono"/>
						</div>
						<netui:label styleClass="label-solos" value="Colonia " for="form_colonia"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.colonia" size="48" tagId="form_colonia"/>
						</div>
						<netui:label styleClass="label-solos" value="Ciudad " for="form_ciudad"/><br>
						<div class="text-side marg-rig15 margin-bot0">
							<netui:textBox dataSource="actionForm.ciudad" size="48" tagId="form_ciudad"/>
						</div>
						<netui:label styleClass="label-solos" value="Comentario (*)" for="form_comentario" /><br>
						<div class="text-side marg-rig15">
							<netui:textArea dataSource="actionForm.comentario" rows="4" cols="48" tagId="form_comentario"/>
						</div>
					</div>	
					<div class="span-8 ">
						<netui:label styleClass="label-solos" value="Sube tu foto" for="form_foto" />
						<div>
							<netui:fileUpload dataSource="actionForm.foto" size="33" tagId="form_foto"/>
						</div>
					</div>
					<div class="span-8 last">
						<netui:label styleClass="label-solos" value="Sube tu video" for="form_video" /><br>
						<div>
							<netui:fileUpload dataSource="actionForm.video" size="33" tagId="form_video"/>
						</div>
					</div>
					
					<div class="clear"></div>
					<br><br>
					<div align="center">
						<!-- inicio captcha -->
					 	<script type="text/javascript">
					        var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
					        Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget'});
					    </script>
					    <div class="wrap-captcha wrap-captcha-wid" id="recaptcha_widget">
					        <div class="captcha" id="recaptcha_image"></div>
					        <br />
					        
					        <label for="captchaText" class="color-orange">Escribe ambas palabras:</label>
					        <div class="text-side fix-top margin-left-15">
					            <input type="text" id="recaptcha_response_field" name="recaptcha_response_field" value="" style="width: 180px;" size="40" />
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
					    <!-- fin captcha -->
					</div>
					
					<br><br>
					(*) Campos obligatorios
					<div style="display:none;">
						<netui:rewriteName  name="ationForm_guardarTeEscuchamos" forTagId="true" resultId="ationForm_guardarTeEscuchamos"/>
					</div>
					<netui:anchor onClick="submitForma('${ationForm_guardarTeEscuchamos}',this);return false;" formSubmit="true" action="saveLead" styleClass="btn-small margin-bot15 fright" title="Enviar" value="Enviar">
				        <netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
				    </netui:anchor>
				    <!--<a href="javascript: ;" onclick="submitForm()" class="btn-small margin-bot15 fright" title="Enviar">Enviar</a>
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					-->
				</netui:form>
			<div class="clear"></div>
		</div>	
		
		<c:set var="contenidoId" value="CV004372" scope="request"/>
		<c:set var="estructuraId" value="CV001950" scope="request"/>
		<c:set var="templateId" value="CV001951" scope="request"/>
		<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
		
	</div>
	<div class="clear"></div>				
</div>
<script type="text/javascript">
	$(document).ready(function() {
		var challengeVal = $('#recaptcha_challenge_field').val();
		$('#actionForm_challenge').val(challengeVal);
	});
	
	$('#recaptcha_response_field').keypress(function() {
		var responseVal = $('#recaptcha_response_field').val();
        $('#actionForm_uresponse').val(responseVal);
	});
	
	$('#recaptcha_response_field').blur(function() {
		var responseVal = $('#recaptcha_response_field').val();
        $('#actionForm_uresponse').val(responseVal);
	});		
	
	/*function submitForm(){
		var challengeVal = $('#recaptcha_challenge_field').val();
		$('#actionForm_challenge').val(challengeVal);
		$('#ationForm_guardarTeEscuchamos').submit();
	}*/
	
	/*
	$('#subir').click(function(event) {
		event.preventDefault();
		var destination = 'http://localhost/cablePortal/cablevision.portal?_nfpb=true&_nfxr=false&_pageLabel=atencion_teEscuchamos&_nfto=false#thisistop';
		$("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination}, 1100 );
	});
	*/
</script>