<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@ page import="com.cablevision.util.ConfigurationHelper"%>

<netui:scriptContainer>
	<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>
	
	<div class="span-24">
		<div class="wcs-marg">
				<c:choose>
					<c:when test="${!empty pageInput.exito}">
						<div class="successMsg">
							<br><br><br>
							<h1 class="color-orange" style="text-align: center;"><strong>Gracias!</strong></h1>
							<h3 style="text-align: center;">Su mensaje ha sido enviado con &eacute;xito.</h3>
							<br><br><br><br><br><br><br>
						</div>
					</c:when>
					<c:otherwise>
						<netui:form action="enviarCorreo" tagId="formEnviarCorreo">
						<div class="span-16" style="margin-left: 250px;">	
							<h2 class="side ico-micta margin-bot15">Distribuidores de CABLEVISION&reg; </h2>
								Si estas interesado en formar parte del equipo de distribuidores de CABLEVISION&reg; , envianos tus datos y nos pondremos en contacto contigo. 
							<br><br>
							
					       	<c:choose>
								<c:when test="${!empty pageInput.errors}">
									<label class="error-msg">${pageInput.errors}</label>
								</c:when>
								<c:otherwise>
									<c:if test="${!empty pageInput.exito}">
										<label class="msg-success">${pageInput.exito}</label>
									</c:if>
								</c:otherwise>
							</c:choose>
							<label class="error-msg">
						       	<%
						       		org.apache.struts.action.ActionMessages errors = org.apache.struts.util.RequestUtils.getActionMessages(pageContext, org.apache.struts.Globals.ERROR_KEY);
						       		if(errors!=null&&!errors.isEmpty()){
						       	%>
						       	Proporcione los campos requeridos.
						       	<%
						       		}
						       	%>
							</label>
							<br>
							<table>
								<tr>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Nombre y Apellido </label><br>
											<netui:error key="nombreCompleto" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.nombreCompleto" tagId="actionForm_nombreCompleto" size="48" maxlength="50"/>
											</div>
										</div>
									</td>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Teléfono </label><br>
											<netui:error key="telefono" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.telefono" tagId="actionForm_telefono" size="48" maxlength="50" onKeyPress="return soloNumeros(event);"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Celular </label><br>
											<netui:error key="celular" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.celular" tagId="actionForm_celular" size="48" maxlength="50" onKeyPress="return soloNumeros(event);"/>
											</div>
										</div>
									</td>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Email </label><br>
											<netui:error key="email" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.email" tagId="actionForm_email" size="48" maxlength="50"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Horario para contactar </label><br>
											<netui:error key="horarioContactar" bundleName="distribuidores"/>
												<!-- 
												<select id="user-box" style="width: 303px;" class="selectwhitoutform" name="user-box">
													<option></option>
												</select>
												Distrito Federal
												 -->
												<div class="text-side marg-rig15 margin-bot0">
													<netui:textBox dataSource="actionForm.horarioContactar" tagId="actionForm_horarioContactar" size="48" maxlength="50"/>
												</div>
										</div>
									</td>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Estado </label>
											<netui:error key="estado" bundleName="distribuidores"/>
												<netui:select dataSource="actionForm.estado" tagId="actionForm_estado" styleClass="selectwhitoutform" style="width: 303px;">
											 		<netui:selectOption value="">...</netui:selectOption>
											 		<netui:selectOption value="Aguascalientes">Aguascalientes</netui:selectOption>
											 		<netui:selectOption value="Baja California Norte">Baja California Norte</netui:selectOption>
											 		<netui:selectOption value="Baja California Sur">Baja California Sur</netui:selectOption>
											 		<netui:selectOption value="Campeche">Campeche</netui:selectOption>
											 		<netui:selectOption value="Chiapas">Chiapas</netui:selectOption>
											 		<netui:selectOption value="Chihuahua">Chihuahua</netui:selectOption>
											 		<netui:selectOption value="Coahuila">Coahuila</netui:selectOption>
											 		<netui:selectOption value="Colima">Colima</netui:selectOption>
											 		<netui:selectOption value="Durango">Durango</netui:selectOption>
											 		<netui:selectOption value="Estado de Hidalgo">Estado de Hidalgo</netui:selectOption>
											 		<netui:selectOption value="Estado de México">Estado de México</netui:selectOption>
											 		<netui:selectOption value="Guanajuato">Guanajuato</netui:selectOption>
											 		<netui:selectOption value="Guerrero">Guerrero</netui:selectOption>
											 		<netui:selectOption value="Jalisco">Jalisco</netui:selectOption>
											 		<netui:selectOption value="Michoacán">México D.F.</netui:selectOption>					 		
											 		<netui:selectOption value="Michoacán">Michoacán</netui:selectOption>
											 		<netui:selectOption value="Morelos">Morelos</netui:selectOption>
											 		<netui:selectOption value="Nayarit">Nayarit</netui:selectOption>
											 		<netui:selectOption value="Nuevo León">Nuevo León</netui:selectOption>
											 		<netui:selectOption value="Oaxaca">Oaxaca</netui:selectOption>
											 		<netui:selectOption value="Puebla">Puebla</netui:selectOption>
											 		<netui:selectOption value="Querétaro">Querétaro</netui:selectOption>
											 		<netui:selectOption value="Quintana Roo">Quintana Roo</netui:selectOption>
											 		<netui:selectOption value="San Luis Potosí">San Luis Potosí</netui:selectOption>
											 		<netui:selectOption value="Sinaloa">Sinaloa</netui:selectOption>
											 		<netui:selectOption value="Sonora">Sonora</netui:selectOption>
											 		<netui:selectOption value="Tabasco">Tabasco</netui:selectOption>
											 		<netui:selectOption value="Tamaulipas">Tamaulipas</netui:selectOption>
											 		<netui:selectOption value="Tlaxcala">Tlaxcala</netui:selectOption>
											 		<netui:selectOption value="Veracruz">Veracruz</netui:selectOption>
											 		<netui:selectOption value="Yucatán">Yucatán</netui:selectOption>
											 		<netui:selectOption value="Zacatecas">Zacatecas</netui:selectOption>
											 	</netui:select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Ciudad </label><br>
											<netui:error key="ciudad" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.ciudad" tagId="actionForm_ciudad" size="48" maxlength="50"/>
											</div>
										</div>
									</td>
									<td>
										<div class="span-8 ">
											<label class="label-solos">Municipio</label><br>
											<netui:error key="municipio" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.municipio" tagId="actionForm_municipio" size="48" maxlength="50"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-16 last">
											<ul class="pago-form">
												<li style="width: 250px; line-height: 40px;">
													Cuentas con negocio propio?
												</li>
												<netui:radioButtonGroup dataSource="actionForm.negocioPropio">
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="SI" tagId="negocioPropio_SI">
													 	<label style="vertical-align: middle;" for="negocioPropio_SI">SI</label>
													 </netui:radioButtonOption>
												</li>
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="NO" tagId="negocioPropio_NO">
													 	<label style="vertical-align: middle;" for="negocioPropio_NO">NO</label>
													 </netui:radioButtonOption>
												</li>
												</netui:radioButtonGroup>
											</ul>
										</div>
										<div class="span-16 last"><netui:error key="negocioPropio" bundleName="distribuidores"/></div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-8 ">
											<label class="label-solos">Que tipo de negocio es? </label><br>
											<netui:error key="tipoNegocio" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.tipoNegocio" tagId="actionForm_tipoNegocio" size="48" maxlength="100"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-16 last">
											<ul class="pago-form">
												<li style="width: 250px; line-height: 40px;">
													Cuentas con local comercial?
												</li>
												<netui:radioButtonGroup dataSource="actionForm.localComercial">
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="SI" tagId="localComercial_SI">
													 	<label style="vertical-align: middle;" for="localComercial_SI">SI</label>
													 </netui:radioButtonOption>
												</li>
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="NO" tagId="localComercial_NO">
													 	<label style="vertical-align: middle;" for="localComercial_NO">NO</label>
													 </netui:radioButtonOption>
												</li>
												</netui:radioButtonGroup>
											</ul>
										</div>
										<div class="span-16 last"><netui:error key="localComercial" bundleName="distribuidores"/></div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-16 last">
											<ul class="pago-form">
												<li style="width: 250px; line-height: 40px;">
													tienes vendedores a tu cargo?
												</li>
												<netui:radioButtonGroup dataSource="actionForm.vendedores">
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="SI" tagId="vendedores_SI">
													 	<label style="vertical-align: middle;" for="vendedores_SI">SI</label>
													 </netui:radioButtonOption>
												</li>
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="NO" tagId="vendedores_NO">
													 	<label style="vertical-align: middle;" for="vendedores_NO">NO</label>
													 </netui:radioButtonOption>
												</li>
												</netui:radioButtonGroup>
											</ul>
										</div>
										<div class="span-16 last"><netui:error key="vendedores" bundleName="distribuidores"/></div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-8 ">
											<label class="label-solos">Que vendes? </label><br>
											<netui:error key="queVende" bundleName="distribuidores"/>
											<div class="text-side marg-rig15 margin-bot0">
												<netui:textBox dataSource="actionForm.queVende" tagId="actionForm_queVende" size="48" maxlength="100"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div class="span-16 last">
											<ul class="pago-form">
												<li style="width: 250px; line-height: 40px;">
													Como realizas la venta?
												</li>
												<netui:radioButtonGroup dataSource="actionForm.comoVende">
												<li style="width: 50px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="Local" tagId="comoVende_Local">
													 	<label style="vertical-align: middle;" for="comoVende_Local">Local</label>
													 </netui:radioButtonOption>
												</li>
												<li style="width: 117px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="Puerta en puerta" tagId="comoVende_Puerta">
														<label style="vertical-align: middle;" for="comoVende_Puerta">Puerta en puerta</label>
													</netui:radioButtonOption>
												</li>
												<li style="width: 80px; line-height: 40px;">
													<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="Telefono" tagId="comoVende_Telefono">
														<label style="vertical-align: middle;" for="comoVende_Telefono">Teléfono</label>
													</netui:radioButtonOption>
												</li>
												</netui:radioButtonGroup>								
											</ul>
										</div>
										<div class="span-16 last"><netui:error key="comoVende" bundleName="distribuidores"/></div>
									</td>
								</tr>
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
							        <div class="error-msg" id="errorCaptcha">${pageInput.msgCapcha}</div>
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
							<br><br>
							Aplica únicamente en área metropolitana
							
							<netui:anchor styleClass="btn-small marg-rig40 margin-bot15 fright" title="Enviar" formSubmit="true" onClick="">
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
								Enviar
							</netui:anchor>
							<div class="clear"></div>
						</div>
						</netui:form>
					</div>
				</c:otherwise>
			</c:choose>
		
		<br><br><br><br>
		<div class="clear"></div>
	</div>
</netui:scriptContainer>