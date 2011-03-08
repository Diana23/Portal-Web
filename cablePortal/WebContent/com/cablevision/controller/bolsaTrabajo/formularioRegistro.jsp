<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.cablevision.util.ConfigurationHelper"%>

<netui:scriptContainer>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>
<style type="text/css">
.recaptcha_content{width:300px;}
.recaptcha_content img{margin-left:0px;}
.recaptcha_content ul{font-family:Arial,Helvetica,sans-serif;font-size:11px;list-style-type:none;color: #221F72;}
.recaptcha_content a{font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #221F72;
	text-decoration: none;
	font-weight: bold;
}
.recaptcha_content p{color:#221F73;margin:0px;padding:0px;}
}
</style>
<div class="span-18 last">
	<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
				
	<h1 class="cable-no-img">¡Únete a nuestro equipo!</h1>
	<p>Te pedimos completar la siguiente forma de registro, este paso es necesario para que puedas postularte para las vacantes y dar de alta tu CV. (Te pedimos no utilizar acentos).</p>
	<!-- <p>En CABLEVISION<sup>&reg;</sup> nos preocupamos por contar con profesionales que se identifiquen con valores de nuestra empresa y a su vez proporcionen a nuestros clientes la atención y servicios de calidad que nos caracterizan. Para participar en nuestro proceso de selección, sólo necesitas registrarte e ingresar tu CV. ¡Únete a nuestro Equipo!</p>-->
      <netui:form action="registrar" tagId="formreg" styleClass="formulario">		
			<div class="span-16">
				<div class="error-msg" id="msgError">
					<c:if test="${!empty pageInput.errors}">
					<c:out value="${pageInput.errors}"/>
			       	</c:if>
					<netui:errors bundleName="bolsaTrabajoBundle" />
					<c:out value="${pageInput.msg}"/>
				</div>
		  <br><br><br>
		  
			<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
				<netui:hidden dataSource="actionForm.id" tagId="actionForm_id"/>
			  <tr>
				  <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_nombre" value="Nombre (*) " /> <input type="hidden" name="registry" id="<netui:rewriteName name="registry" forTagId="true"  />" value="ok"></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.nombre" tagId="actionForm_nombre" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
			  </tr>
			  <tr>			  
			  	  <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_paterno" value="Apellido Paterno (*) " /></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.paterno" tagId="actionForm_paterno" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
			  </tr>
			  <tr>
			      <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_materno" value="Apellido Materno (*) " /></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.materno" tagId="actionForm_materno" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
			  </tr>
			  <tr>
			      <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_email" value="Email (*) " /></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.email" tagId="actionForm_email" maxlength="250"></netui:textBox></td>
			  </tr>
			  <tr>
			      <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_password" value="Contraseña (*) " /></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.password" tagId="actionForm_password" password="true" maxlength="250"></netui:textBox></td>
			  </tr>
			  <tr>
			  <td align="right"><div style="top: -10px; position: relative;"><netui:label for="actionForm_confirmPassword" value="Confirma Contraseña (*) " /></div></td>
				  <td><netui:textBox styleClass="big" dataSource="actionForm.confirmPassword" tagId="actionForm_confirmPassword" password="true" maxlength="250"></netui:textBox></td>			
			  </tr>
			</table>
			</div>
			</td>
		  </tr>
		  <tr>
		  <div style="width: 300px ! important; padding-left: 200px;" align="left" class="wrap-captcha">
		  	<hr>
		    	<!-- inicio captcha -->
		    		<div class="recaptcha_content" style="width: 100px;">
						<script type="text/javascript">
							var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
							Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget'});
						</script>
						<br/><br/>									
						    	<div id="recaptcha_widget" >
									<table cellspacing="0" cellpadding="0" border="0" align="center" width="492">
										<tr>
											<td width="10"></td>
											<td width="472">
												<table cellspacing="0" cellpadding="0" border="0" align="center">
												 <tbody>
													<tr>
														<td valign="top" style="padding-top: 7px;">
															<div id="recaptcha_image" style="border: 1px solid rgb(235, 235, 235); width: 300px; height: 57px;">
															</div>
														</td>
														<tr>
														<td colspan="2">																																						
															<p id="recaptcha_input_field_text" class="color-orange">Escribe ambas palabras:</p>
															<div><input class="inputCaptcha" type="text" name="recaptcha_response_field" id="recaptcha_response_field" value="" size="40"  height="10px" style="width: 270px;"></div>																																				
														</td>
														</tr>
														<tr>
														<td>
															
																<a href="javascript:Recaptcha.reload();">
																	
																</a>
																<a title="Intentar Otro" class="inteo-icon hidden-text" id="reload_word_text_link" href="javascript:Recaptcha.reload();" ></a>
															
															<div class="recaptcha_only_if_image" >
																<a href="javascript:Recaptcha.switch_type('audio');">
																	
																</a>
																<a title="Discapavidad Visual" class="discv-icon hidden-text" href="javascript:Recaptcha.switch_type('audio');"></a>
															</div>
															<div class="recaptcha_only_if_audio" >
																<a href="javascript:Recaptcha.switch_type('image');">
																	
																</a>
																<a  href="javascript:Recaptcha.switch_type('image');">Regresar a versión de palabras</a>
															</div>
															
																<a href="javascript:Recaptcha.showhelp()">
																	
																</a>
																<a title="Ayuda" class="help-icon hidden-text" href="javascript:Recaptcha.showhelp()"></a>
															
														</td>
													</tr>
													</tr>
													
													
													<tr>
														<td colspan="2">
															<br/>
															<p class="captchaPar">
																La verificación de código nos permite confirmar que tu registro se realice de manera segura <br>
																y confiable así como para  mantener la privacidad de tu información	
															</p>
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<br/>
														</td>
													</tr>													
												</tbody>
												</table>
											</td>
											<td width="5"></td>
										</tr>
									</table>
								</div>	
						    </td>						   
						  </tr>
						  <tr>
						    <td colspan="3"></td>
						  </tr>
						  <tr>
						  <td>
						  	<div style="display:none;">
								<netui:rewriteName  name="formreg" forTagId="true" resultId="formreg"/>
							</div>
							<div align="center" class="span-14">
							<netui:anchor  onClick="submitForma('${formreg}',this);return false;" formSubmit="true" action="registrar" styleClass="btn-small margin-bot15" title="REGISTRAR" value="REGISTRAR"/>
							</div>						  
					</div>
		    	<!-- fin captcha -->
		    	</div>
		    </td>
		  </tr>	  		  
		</table>
		</div>
     </netui:form>
</netui:scriptContainer>