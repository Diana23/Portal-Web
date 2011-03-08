<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ page import="com.cablevision.util.ConfigurationHelper"%>
<netui:scriptContainer>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/com/cablevision/controller/cotizador/popup.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/cotizador.css" type="text/css" rel="stylesheet">
	
	<jsp:directive.page import="com.bea.portlet.GenericURL" />
	<jsp:directive.page import="com.bea.portlet.PageURL" />

${pageScope.a }
	<jsp:scriptlet>
		PageURL url = PageURL.createPageURL(request, response);
		url.setTemplate("home");
		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		pageContext.setAttribute("url",url);
	</jsp:scriptlet>
	
	<div id="divResponse" style="display:none;">
		<br><br><br>
		<h1 class="color-orange" style="text-align: center;"><strong>Gracias!</strong></h1>
		<h3 style="text-align: center;"><div id="response-msg"></div></h3>
		<br><br><br><br><br><br><br>	
	</div>
	

	<div class="span-11" id="divFormCot">
			<div class="wrap-cont-simple wcs-padd" id="formulario">	
				<form action="" method="post" name="formcot" id="formcot"> <!-- id=solicitud -->
					<netui:hidden dataSource="pageInput.form.idService" />
					<netui:hidden dataSource="pageInput.form.idProduct" />
					<netui:hidden dataSource="pageInput.form.idExtra" />
					
					<h2 class="side ico-micta margin-bot15">Datos del Suscriptor:</h2>
					<br>
					<table cellspacing="0" cellpadding="0" border="1" align="center" width="95%">
						<tbody>
						<tr>
							<td colspan="3" width="33%">
								<label>Nombre (*)</label><br>
								<netui:textBox dataSource="pageInput.form.nombre" tagId="nombre" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="Nombre-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Apellido Paterno  (*)</label><br> 
								<netui:textBox dataSource="pageInput.form.apellidoPaterno" tagId="apellidoPaterno" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="PrimerApellido-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Apellido Materno  (*)</label><br>
								<netui:textBox dataSource="pageInput.form.apellidoMaterno" tagId="apellidoMaterno" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="SegundoApellido-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>RFC </label><br>
								<netui:textBox dataSource="pageInput.form.rfc" tagId="rfc" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="RFC-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Identificación  (*)</label><br>
								<netui:textBox dataSource="pageInput.form.identificacion" tagId="identificacion" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="Identificacion-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Correo Electrónico  (*)</label><br>
								<netui:textBox dataSource="pageInput.form.email" tagId="email" styleClass="small" />
								<div id="Email-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Teléfono de Casa (*)</label><br>
								<netui:textBox dataSource="pageInput.form.telCasa" tagId="telCasa" maxlength="8" onKeyPress="return isNumeric(event);" styleClass="small"/>
								<div id="TelCasa-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Teléfono de oficina </label><br>
								<netui:textBox dataSource="pageInput.form.telOficina" tagId="telOficina" maxlength="8" onKeyPress="return isNumeric(event);" styleClass="small"/>
								<div id="TelOfna-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<br>
							</td>
						</tr>
					</tbody>
				</table>

				<h2 class="side ico-micta margin-bot15">Domicilio de Instalación:</h2>
				<br>
				<table cellspacing="0" cellpadding="0" border="1" align="center" width="95%" class="instalacion">
					<tbody>
						<tr>
							<td colspan="3" width="33%">
								<label>Calle (*)</label><br>
								<netui:textBox dataSource="pageInput.form.calle" tagId="calle" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="Calle-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>No. Exterior (*)</label><br>
								<netui:textBox dataSource="pageInput.form.numeroExterior" tagId="numeroExterior" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="NoExt-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>No. Interior</label><br>
								<netui:textBox dataSource="pageInput.form.numeroInterior" tagId="numeroInterior" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="NoInt-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Entre calles (*)</label><br>
								<netui:textBox dataSource="pageInput.form.entreCalles" tagId="entreCalles" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="EntreCalles-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Colonia (*)</label><br>
								<netui:textBox dataSource="pageInput.form.colonia" tagId="colonia" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="Colonia-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Código postal (*)</label><br>
								<netui:textBox dataSource="pageInput.form.cp" tagId="cp" onKeyPress="return isNumeric(event);" styleClass="small"/>
								<div id="CP-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Delegación (*)</label><br>
								<netui:textBox dataSource="pageInput.form.delegacion" tagId="delegacion" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="Delegacion-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Estado (*)</label><br>
								<netui:textBox dataSource="pageInput.form.estado" tagId="estado" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="Estado-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<br>
							</td>
						</tr>
					</tbody>
				</table>
				
				<h2 class="side ico-micta margin-bot15">Domicilio de Facturación </h2>
				<br>
				<input type="checkbox" checked="checked" class="check-solicitud" id="<netui:rewriteName name="same" forTagId="true" resultId="same"/>" /> Mismo domicilio que el de instalación
				<div style="display:none;" id="<netui:rewriteName name="divFac" forTagId="true" resultId="divFac"/>">
				<table cellspacing="0" cellpadding="0" border="0" align="center" width="95%" class="factura"> 
					<tbody>
						<tr>
							<td colspan="0">
								
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Calle (*)</label><br>
								<netui:textBox dataSource="pageInput.form.calleFactura" tagId="calleFactura" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="CalleFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>No. Exterior (*)</label><br>
								<netui:textBox dataSource="pageInput.form.numeroExteriorFactura" tagId="numeroExteriorFactura" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="NoExtFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>No. Interior</label><br>
								<netui:textBox dataSource="pageInput.form.numeroInteriorFactura" tagId="numeroInteriorFactura" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="NoIntFact-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Entre calles (*)</label><br>
								<netui:textBox dataSource="pageInput.form.entreCallesFactura" tagId="entreCallesFactura" onKeyPress="return isAlphaNumeric(event);" styleClass="small"/>
								<div id="EntreCallesFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Colonia (*)</label><br>
								<netui:textBox dataSource="pageInput.form.coloniaFactura" tagId="coloniaFactura" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="ColoniaFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Código postal (*)</label><br>
								<netui:textBox dataSource="pageInput.form.cpFactura" tagId="cpFactura" onKeyPress="return isNumeric(event);" styleClass="small"/>
								<div id="CPFact-msg" class="divError"></div>
							</td>
						</tr>
						<tr>
							<td colspan="3" width="33%">
								<label>Delegación (*)</label><br>
								<netui:textBox dataSource="pageInput.form.delegacionFactura" tagId="delegacionFactura" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="DelegacionFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<label>Estado (*)</label><br>
								<netui:textBox dataSource="pageInput.form.estadoFactura" tagId="estadoFactura" onKeyPress="return isAlpha(event);" styleClass="small"/>
								<div id="EstadoFact-msg" class="divError"></div>
							</td>
							<td width="33%" colspan="3">
								<br>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
				<br>
				<!-- inicio captcha -->
				<div id="recaptcha_div" style=" width: 80%; margin-left:35px; " >
					<script type="text/javascript">
						var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
						Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget'});
					</script>
					<div class="wrap-captcha" id="recaptcha_widget">
				        <div id="recaptcha_image" style="border: 1px solid rgb(235, 235, 235); width: 300px; height: 57px;"></div>
				        <br>
				        
				        <label class="color-orange" for="captchaText">Escribe ambas palabras:</label>
				        <div>
				        	<input type="text" id="recaptcha_response_field" maxlength="256" size="52" name="recaptcha_response_field" autocomplete="off"/>
				        	<div id="recaptcha-msg" class="divError"></div>
				        </div>
				        <div>
				            <a class="inteo-icon-cot hidden-text" title="Intentar otro" href="javascript:Recaptcha.reload();">Intentar otro</a>
				        </div>
				        <div class="recaptcha_only_if_image">
				            <a class="discv-icon hidden-text" title="Discapacidad visual" href="javascript:Recaptcha.switch_type('audio');">Discapacidad visual</a>
				        </div>
				        <div class="recaptcha_only_if_audio">
				            <a class="text-icon hidden-text" title="Regresar a version de palabras" href="javascript:Recaptcha.switch_type('image');">Regresar a versi�n de palabras</a>
				        </div>
				        <div>
				            <a class="help-icon hidden-text" title="Ayuda" href="javascript:Recaptcha.showhelp();">Ayuda</a>
				        </div>
				        
				        <div class="clear"></div>
				        <p class="captchaPar">La verificación de código nos permite confirmar que tu registro se realize de manera segura y confiable asi como para mantener la privacidad de tu información.</p>
				    </div>
				</div>
		
				<p class="notAllowed"><strong>*NOTA</strong>: El formulario no acepta caracteres especiales tales como *.,;/\"\'@#~¬|!?¿¡+ç-&%</p>
		
				
				(*) Campos obligatorios
				<br>
				
				<a href="#" title="saveForma" class="btn-small marg-rig40 margin-bot15 fright" id="<netui:rewriteName name="saveForma" forTagId="true" />">
					Siguiente
				</a>
				<br><br>
			</form>
		</div> <!-- div del formulario -->
	</div>
</netui:scriptContainer>