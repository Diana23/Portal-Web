<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="jstl-c"%>

<%@ page import="com.cablevision.util.ConfigurationHelper"%>

<netui:scriptContainer>
	<script type="text/javascript">
		var contextPath = "<%=request.getContextPath()%>";
		var newsLetterId = "<netui:rewriteName name="newsletters" forTagId="true" resultId="newsletters"/>";
		var noContratoId = "<netui:rewriteName name="noContrato_box" forTagId="true" resultId="noContrato_box"/>";
		var formulario = "<netui:rewriteName name="formActDatPer" forTagId="true" resultId="formActDatPer"/>";
		var divLateral = "<netui:rewriteName name="divLateral" forTagId="true" resultId="divLateral"/>";
		var divNoticias = "<netui:rewriteName name="divNoticias" forTagId="true" resultId="divNoticias"/>";
		var divMeses = "<netui:rewriteName name="divMeses" forTagId="true" resultId="divMeses"/>";
		
	</script>
	
	<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>
	
	<div class="newsletters" id="<netui:rewriteName name="newsletters" forTagId="true" resultId="newsletters"/>">
		<div class="wcs-marg">
			<div class="span-11 marg-left220 margin-top15">
				<netui:form action="verificarMail" tagId="formMostrar">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>

					<h2 class="side ico-micta margin-bot15">Ingresa</h2><div class="clear"></div>	
					<div style="height: 29px; margin-bottom: 15px; margin-top: -15px;" class="span-14">
						Si ya recibes los newsletters de CABLEVISION&reg; ingresa tu email
					</div><div class="clear"></div>	
					<div class="span-7">
						<label for="email-box">Email: </label>
						<div class="text-side marg-rig15 fieldNews">
							<netui:textBox dataSource="actionForm.emailRegistrado" tagId="actionForm_emailRegistado" size="25"/>
						</div>
						<div class="error-msg">
							<netui:errors  />
							${pageInput.errors}
						</div>
					</div>
					<div class="clear"></div>	
					<netui:button value="Enviar" styleClass="btn-minimizednl fright marg-rig15" style="margin-right: 50px; margin-top: -46px;position:relative;"/>
					<div class="clear"></div>
				</netui:form>					
			</div>					
			<div class="clear"></div>
			<div class="span-12 bord-toped padding-top40 margin-top10 marg-left220">
				<h2 class="side ico-micta margin-bot15">Suscríbete al newsletter</h2>
				<div style="height: 29px; margin-bottom: 15px; margin-top: -15px;" class="span-14">
					Para recibir información, noticias, promociones y más.
				</div><div class="clear"></div>
				<jstl-c:choose>
					<jstl-c:when test="${!empty pageInput.success}">
						<div class="successMsg">
							<br><br><br>
							<h1 class="color-orange" style="text-align: center;"><strong>Gracias!</strong></h1>
							<h3 style="text-align: center;">Su mensaje ha sido enviado con &eacute;xito.</h3>
							<br><br><br><br><br><br><br>
						</div>
					</jstl-c:when>
					<jstl-c:otherwise>
						<div>
							<netui:form  action="enviar" tagId="formActDatPer">
								<table width="500" cellspacing="0" cellpadding="0" border="0" class="noBorder">
									<tbody>
										<tr>
											<td>
												<netui:label for="actionForm_nombre" value="Nombre (*)" /><span>${pageInput.nombre}</span>
											</td>
											<td>
												<netui:label for="actionForm_apellidoPaterno" value="Apellido Paterno (*)" />
											</td>
										</tr>
										<tr>
											<td>
												<div class="text-side fieldNews">
													<netui:textBox dataSource="actionForm.nombre" tagId="actionForm_nombre" onKeyPress="return isAlpha(event);" size="40"></netui:textBox>
												</div>
												<div class="error-msg">
													<jstl-c:choose>
														<jstl-c:when test="${!empty pageInput.msgNombre}">
															${pageInput.msgNombre}
														</jstl-c:when>
														<jstl-c:otherwise>
															<br>
														</jstl-c:otherwise>
													</jstl-c:choose>
												</div>
											</td>
											<td>
												<div class="text-side fieldNews">
													<netui:textBox dataSource="actionForm.apellidoPaterno" tagId="actionForm_apellidoPaterno" onKeyPress="return isAlpha(event);" size="40"></netui:textBox>
												</div>
												<div class="error-msg">
													<jstl-c:choose>
														<jstl-c:when test="${!empty pageInput.msgAP}">
															${pageInput.msgAP}
														</jstl-c:when>
														<jstl-c:otherwise>
															<br>
														</jstl-c:otherwise>
													</jstl-c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<netui:label for="actionForm_apellidoMaterno" value="Apellido Materno (*)" />
											</td>
											<td>
												<netui:label for="actionForm_email" value="Email (*)" /> <span>${pageInput.email}</span>
											</td>
										</tr>
										<tr>
											<td>
												<div class="text-side fieldNews">
													<netui:textBox dataSource="actionForm.apellidoMaterno" tagId="actionForm_apellidoMaterno" onKeyPress="return isAlpha(event);" size="40"></netui:textBox>
												</div>
												<div class="error-msg">
													<jstl-c:choose>
														<jstl-c:when test="${!empty pageInput.msgAM}">
															${pageInput.msgAM}
														</jstl-c:when>
														<jstl-c:otherwise>
															<br>
														</jstl-c:otherwise>
													</jstl-c:choose>
												</div>
											</td>
											<td>
												<div class="text-side fieldNews">
													<netui:textBox dataSource="actionForm.email" tagId="actionForm_email" size="40"></netui:textBox>
												</div>
												<div class="error-msg">
													<jstl-c:choose>
														<jstl-c:when test="${!empty pageInput.msgEmail}">
															${pageInput.msgEmail}
														</jstl-c:when>
														<jstl-c:otherwise>
															<br>
														</jstl-c:otherwise>
													</jstl-c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<netui:label for="actionForm_esCliente" value="¿Soy cliente CABLEVISIÓN" /><sup>&reg;</sup>?
											</td>
											<td>
												<p class="esCliente">
													<netui:radioButtonGroup dataSource="actionForm.esCliente" style="background: none repeat scroll 0% 0% transparent; border: 0pt none;">
														<netui:radioButtonOption value="SI" />
														<netui:radioButtonOption value="NO" />
													</netui:radioButtonGroup>
												</p>
											</td>
										</tr>
										
										<jstl-c:choose>
											<jstl-c:when test="${actionForm.esCliente== 'Si' }">
												<tr id="<netui:rewriteName name="noContrato_box" forTagId="true" resultId="noContrato_box"/>">
													<td>
														<br>
													</td>
													<td>
														<netui:label for="actionForm_email" value="Número de contrato (en caso de ser cliente):" />
														<div class="text-side fieldNews">
															<netui:textBox dataSource="actionForm.noContrato" tagId="actionForm_noContrato" onKeyPress="return isNumeric(event);"></netui:textBox>
														</div>
														<div class="error-msg">${pageInput.msgEmail}</div>
													</td>
												</tr>
										
											</jstl-c:when>
											<jstl-c:otherwise>
												<tr id="<netui:rewriteName name="noContrato_box" forTagId="true" resultId="noContrato_box"/>" class="hidden">
													<td>
														<br>
													</td>
													<td>
														<netui:label for="actionForm_email" value="Número de contrato (en caso de ser cliente):" />
														<div class="text-side fieldNews">
															<netui:textBox dataSource="actionForm.noContrato" tagId="actionForm_noContrato" onKeyPress="return isNumeric(event);"></netui:textBox>
														</div>
														<div class="error-msg">${pageInput.msgContrato}</div>
													</td>
												</tr>
											</jstl-c:otherwise>
										</jstl-c:choose>
									</tbody>
								</table>
								
								<jstl-c:choose>
									<jstl-c:when test="${requestScope.saved!= 'true' }">
										<!-- inicio captcha -->
										<div id="recaptcha_div" style="width: 490px ! important; margin-left: 0pt;" >
											<script type="text/javascript">
												var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
												Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget'});
											</script>
											<div class="wrap-captcha" id="recaptcha_widget">
										        <div id="recaptcha_image" class="captcha" style="margin: 0pt; background: none repeat scroll 0% 0% rgb(255, 255, 255); width: 490px ! important;"m></div>
										        <br>
										        <div class="error-msg">${pageInput.msgCapcha}</div>
										        
										        <label class="color-orange" for="captchaText">Escribe ambas palabras:</label>
										        <div style="width: 180px;" class="text-side fix-top">
													<input type="text" id="recaptcha_response_field" style="width: 180px;" size="40" name="recaptcha_response_field"></div>
												<a style="margin-left: 205px; margin-top: -30px;" class="inteo-icon hidden-text" title="Intentar otro" href="javascript:Recaptcha.reload();">Intentar otro</a>
												<div class="recaptcha_only_if_image">
													<a style="margin-left: 230px; margin-top: -30px;" class="discv-icon hidden-text" title="Discapacidad visual" href="javascript:Recaptcha.switch_type('audio');">Discapacidad visual</a>
												</div>
												<div class="recaptcha_only_if_audio">
													<a style="margin-left: 230px; margin-top: -30px;" class="text-icon hidden-text" title="Regresar a version de palabras" href="javascript:Recaptcha.switch_type('image');">Regresar a versi?n de palabras</a>
												</div>
												<a style="margin-left: 255px; margin-top: -30px;" class="help-icon hidden-text" title="Ayuda" href="javascript:Recaptcha.showhelp();">Ayuda</a>
												
										        
										        <div class="clear"></div>
												<p style="width: 490px;" class="captchaPar">La verificación de código nos permite confirmar que tu registro se realize de manera segura y confiable asi como para mantener la privacidad de tu información.</p>
												<netui:button value="Enviar" styleClass="btn-minimizednl" style="margin-bottom: 50px; margin-left: 390px; margin-top: -75px;"/>
												<!--  <a href="#" title="Enviar" style="margin-bottom: 50px; margin-left: 390px; margin-top: -75px;" class="btn-minimized marg-rig15">Enviar</a> -->
										    </div>
										</div>
										<!-- fin recaptcha -->
									</jstl-c:when>
									<jstl-c:otherwise>
										
									</jstl-c:otherwise>
								</jstl-c:choose>
							</netui:form>
						</div>
					</jstl-c:otherwise>
				</jstl-c:choose>
				
			</div>					
			<div class="clear"></div>
		</div>
	</div>
</netui:scriptContainer>
