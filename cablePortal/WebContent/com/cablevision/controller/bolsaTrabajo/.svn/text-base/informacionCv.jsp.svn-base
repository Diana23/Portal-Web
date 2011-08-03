<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar"%>

<script src="${pageContext.request.contextPath }/com/cablevision/controller/bolsaTrabajo/bolsaTrabajo.js" type="text/javascript"></script>

<netui:scriptContainer>

<div class="span-18 last">
	
	<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
	<div align="right">
		<c:if test="${!empty pageInput.usuario}">
	    	<br><label style="display:block"><netui:anchor styleClass="color-orange padding-top40" action="cerrarSesion">Cerrar Sesion</netui:anchor></label>	
	    </c:if>
	</div>
		<h1 class="cable-no-img">Ingresa tu CV</h1>
		<p>Esta es tu página personal en donde encontrarás las estadísticas que necesitas para dar seguimiento a tu búsqueda laboral. Te recordamos que es necesario actualizar tu información cada 6 meses de no ser así tu página será borrada de nuestra base de datos.*Los datos marcados son requeridos.</p>
		<!-- <div align="right" style="line-height: 30px;" class="span-9">
			<c:if test="${!empty pageInput.usuario}">
	        	<p>${pageInput.usuario.name} ${pageInput.usuario.lastname} ${pageInput.usuario.secondlastname} (${pageInput.usuario.email}) - <netui:anchor action="cerrarSesion">Cerrar Sesion</netui:anchor></p>
	       	</c:if>
	        <c:if test="${empty pageInput.usuario}">
	        <%
			     String redirectURL = "index.jsp";
			     response.sendRedirect(redirectURL);
			%>
	        </c:if>
		</div> -->
		
				<div class="span-17">
					<div class="acv-wrap-title acv-title">Datos personales</div>
					<div class="acv-wrap-cont" style="display: none;">
						<netui:form action="actualizaDatosPersonales" tagId="formDatosPersonales" styleClass="formulario">
							<table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
								<tbody><tr>
									<td><netui:label for="actionForm_nombre" value="* Nombre(s)" /></td>
									<td><netui:label for="actionForm_paterno" value="* Apellido Paterno" /></td>
								</tr>
								<tr>
									<td><netui:textBox dataSource="actionForm.nombre" tagId="actionForm_nombre" maxlength="250" styleClass="big" onKeyPress="return isAlpha(event);"></netui:textBox></td>
									<td><netui:textBox dataSource="actionForm.paterno" tagId="actionForm_paterno" maxlength="250" styleClass="big" onKeyPress="return isAlpha(event);"></netui:textBox></td>
								</tr>
								<tr>
									<td><netui:label for="actionForm_materno" value="* Apellido Materno" /></td>
									<td><netui:label for="actionForm_direccion" value="* Dirección" /></td>
								</tr>
								<tr>
									<td><netui:textBox dataSource="actionForm.materno" tagId="actionForm_materno" maxlength="250" styleClass="big" onKeyPress="return isAlpha(event);"></netui:textBox></td>
									<td><netui:textBox dataSource="actionForm.direccion" tagId="actionForm_direccion" size="50" maxlength="2000" styleClass="big" onKeyPress="return isAlphaNumeric(event);"></netui:textBox></td>
								</tr>
								<tr>
									<td><netui:label for="actionForm_telefono" value="* Teléfono" /></td>
									<td><netui:label for="actionForm_email" value="* Email" /></td>
								</tr>
								<tr>
									<td><netui:textBox dataSource="actionForm.telefono" tagId="actionForm_telefono" maxlength="20" styleClass="big" onKeyPress="return isNumeric(event);"></netui:textBox></td>
									<td><netui:textBox dataSource="actionForm.email" styleClass="big" tagId="actionForm_email" maxlength="250"></netui:textBox></td>
								</tr>								
								<tr>
									<td><netui:label for="actionForm_edoCivl" value="* Estado Civil" /></td>
									<td><netui:label for="actionForm_escolaridad" value="* Escolaridad" /></td>
								</tr>
								<tr>
									<td>
										<table width="300" border="0" cellspacing="0" cellpadding="0">
											<netui:radioButtonGroup dataSource="actionForm.edoCivl" >
											  <tr>
												<td width="24">&nbsp;</td>
												<td><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="SOLTERO" tagId="edoSoltero">
												  <label style="vertical-align: middle;">Soltero</label></td></netui:radioButtonOption>
											  </tr>
											  <tr>
												<td>&nbsp;</td>
												<td><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="CASADO" tagId="edoCasado">
												  <label style="vertical-align: middle;">Casado</label></td></netui:radioButtonOption>
											  </tr>
											  <tr>
												<td>&nbsp;</td>
												<td><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="DIVORCIADO" tagId="edoDiv">
												  <label style="vertical-align: middle;">Divorciado</label></td></netui:radioButtonOption>
											  </tr>
											  <tr>
												<td>&nbsp;</td>
												<td ><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="UNION LIBRE" tagId="edoUnion">
												  <label style="vertical-align: middle;">Unión Libre</label></td></netui:radioButtonOption>
											  </tr>
											  <tr>
												<td>&nbsp;</td>
												<td height="25"><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="VIUDO" tagId="edoViudo">
												  <label style="vertical-align: middle;">Viudo</label></td></netui:radioButtonOption>
											  </tr>
											</netui:radioButtonGroup>
										</table>										
									</td>
									<td>
									<table>
										<netui:textBox dataSource="actionForm.escolaridad" tagId="actionForm_escolaridad" maxlength="250" styleClass="big" onKeyPress="return isAlpha(event);"></netui:textBox></td>
										<br>
										<netui:radioButtonGroup dataSource="actionForm.statusEscolar">
										<tr>												
												<td height="25"><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="TITULADO" tagId="statusTit">
												  <label style="vertical-align: middle;">Titulado</label></netui:radioButtonOption>
										</tr>
										<tr>												
												<td height="25"><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="PASANTE" tagId="statusPas">
												  <label style="vertical-align: middle;">Pasante</label></netui:radioButtonOption>
										</tr>
										<tr>												
												<td height="25"><netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="TRUNCO" tagId="statusTrunc">
												<label style="vertical-align: middle;">Trunco</label></netui:radioButtonOption>
										</tr>									  							  
										</netui:radioButtonGroup>
										</table>										 
									</td>
								</tr>
																
								<tr>
									<td><netui:label for="actionForm_fechaNacimiento" value="* Fecha de nacimiento" /></td>									
								</tr>
								<tr>
									<td>
										<table width="400" border="0" cellspacing="0" cellpadding="0">
										  <tr>
											<td width="79" height="25">
											  <netui:select styleClass="big" dataSource="actionForm.diaNacimiento" tagId="actionForm_diaNacimiento">
											  	<netui:selectOption value="0">Día</netui:selectOption>
											  	<% 
											  		for(int i=1; i<=31 ;i++){
											  	%>
													<netui:selectOption value='<%=String.valueOf(i)%>'><%=i%></netui:selectOption>
												<%
													} 
												%>
											  </netui:select>                                    
											</td>
											<td width="100">
												<netui:select styleClass="big" dataSource="actionForm.mesNacimiento" tagId="actionForm_mesNacimiento">
												  <netui:selectOption value="0">Mes</netui:selectOption>
												  <netui:selectOption value="ENERO" >Enero</netui:selectOption>
												  <netui:selectOption value="FEBRERO" >Febrero</netui:selectOption>
									
												  <netui:selectOption value="MARZO" >Marzo</netui:selectOption>
												  <netui:selectOption value="ABRIL" >Abril</netui:selectOption>
												  <netui:selectOption value="MAYO" >Mayo</netui:selectOption>
												  <netui:selectOption value="JUNIO" >Junio</netui:selectOption>
												  <netui:selectOption value="JULIO" >Julio</netui:selectOption>
												  <netui:selectOption value="AGOSTO" >Agosto</netui:selectOption>
									
												  <netui:selectOption value="SEPTIEMBRE" >Septiembre</netui:selectOption>
												  <netui:selectOption value="OCTUBRE" >Octubre</netui:selectOption>
												  <netui:selectOption value="NOVIEMBRE" >Noviembre</netui:selectOption>
												  <netui:selectOption value="DICIEMBRE" >Diciembre</netui:selectOption>
												</netui:select>                                    
											</td>
											<td width="150">
											  <netui:select styleClass="big" dataSource="actionForm.anioNacimiento" tagId="actionForm_anioNacimiento">
											   <netui:selectOption value="0">Año</netui:selectOption>										   
											   <% 
											   	 Date fecha = new Date();
											   	 Calendar calendario = Calendar.getInstance();
											   	 calendario.setTime(fecha);
											   	 int anioInicio = calendario.get(Calendar.YEAR)-100;
											   	 
											   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
											   %>
											   		<netui:selectOption value="<%=String.valueOf(anio)%>"><%=anio%></netui:selectOption>
											   <% 
											   	 }
											   %>
											  </netui:select>                                   
											</td>
										  </tr>
										</table>  
									</td>																		
								</tr>								
								<tr>
									<td><div class="error_form_cv" id="<netui:rewriteName name="msg_error1" forTagId="true"  />"></div><br></td>
						  		</tr>
								<tr>
									<td>
										<a href="#" title="Actualizar" class="btn-small margin-bot15" onclick="enviarDatosPersonales(this);return false;">Actualizar</a>
									</td>
								</tr>
								<tr>
									<td>&nbsp;<div id="<netui:rewriteName name="correcto" forTagId="true"  />" ></td>
							  	</tr>
							</tbody></table>
						</netui:form>
						<br>
					</div>					
					<div class="acv-wrap-title acv-title">Experiencia laboral</div>
					<div class="acv-wrap-cont">
						<netui:form tagId="experienciaLaboralForm"  action="actualizaExperienciaLaboral" styleClass="formulario">						
							<table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
								<tbody><tr>
									<td><netui:label for="actionForm_empresa1" value="* Empresa (Último empleo)" /></td>
									<td><netui:label for="actionForm_puesto1" value="* Puesto" /></td>
								</tr>
								<tr>
									<td><netui:textBox dataSource="actionForm.empresa1" tagId="actionForm_empresa1" styleClass="big" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
									<td><netui:textBox dataSource="actionForm.puesto1" tagId="actionForm_puesto1" styleClass="big" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
								</tr>							
								<tr>
									<td><netui:label for="actionForm_fechaInicio1" value="* Fecha de inicio" /></td>		
									<td><netui:label for="actionForm_fechaFin1" value="* Fecha de terminación" /></td>							
								</tr>
								<tr>
									<td>
										<table width="400" border="0" cellspacing="0" cellpadding="0">
											<tr>
											  
											  <td width="79" height="25" >
											  	<netui:select dataSource="actionForm.diaInicio1" styleClass="big" tagId="actionForm_diaInicio1">
											  		<netui:selectOption value="0">Día</netui:selectOption>
											  		<% 
												  		for(int i=1; i<=31 ;i++){
												  	%>
														<netui:selectOption value='<%=String.valueOf(i)%>'><%=i%></netui:selectOption>
													<%
														} 
													%>
												</netui:select>                                          
											  </td>
											  <td width="147" >
											  	<netui:select dataSource="actionForm.mesInicio1" styleClass="big" tagId="actionForm_mesInicio1">
												  <netui:selectOption value="0">Mes</netui:selectOption>
												  <netui:selectOption value="ENERO" >Enero</netui:selectOption>
												  <netui:selectOption value="FEBRERO" >Febrero</netui:selectOption>
												  <netui:selectOption value="MARZO" >Marzo</netui:selectOption>
												  <netui:selectOption value="ABRIL" >Abril</netui:selectOption>
												  <netui:selectOption value="MAYO" >Mayo</netui:selectOption>
												  <netui:selectOption value="JUNIO" >Junio</netui:selectOption>
												  <netui:selectOption value="JULIO" >Julio</netui:selectOption>
												  <netui:selectOption value="AGOSTO" >Agosto</netui:selectOption>
												  <netui:selectOption value="SEPTIEMBRE" >Septiembre</netui:selectOption>
												  <netui:selectOption value="OCTUBRE" >Octubre</netui:selectOption>
												  <netui:selectOption value="NOVIEMBRE" >Noviembre</netui:selectOption>
												  <netui:selectOption value="DICIEMBRE" >Diciembre</netui:selectOption>
												</netui:select>                                            
											  </td>
											  <td width="150" >
											  	<netui:select dataSource="actionForm.anioInicio1" styleClass="big" tagId="actionForm_anioInicio1">
											  	  	<netui:selectOption value="0">Año</netui:selectOption>										   
												   <% 
												   	 Date fecha = new Date();
												   	 Calendar calendario = Calendar.getInstance();
												   	 calendario.setTime(fecha);
												   	 int anioInicio = calendario.get(Calendar.YEAR)-100;
												   	 
												   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
												   %>
												   		<netui:selectOption value="<%=String.valueOf(anio)%>"><%=anio%></netui:selectOption>
												   <% 
												   	 }
												   %>
												</netui:select>                    
											  </td>
											</tr>
										</table>  
									</td>	
									<td>
										<table width="400" border="0" cellspacing="0" cellpadding="0">
											<tr>
											  
											  <td width="79" height="25" >
											    <netui:select dataSource="actionForm.diaFin1" styleClass="big" tagId="actionForm_diaFin1">
											    	<netui:selectOption value="0">Día</netui:selectOption>
													<% 
												  		for(int i=1; i<=31 ;i++){
												  	%>
														<netui:selectOption value='<%=String.valueOf(i)%>'><%=i%></netui:selectOption>
													<%
														} 
													%>
												</netui:select>                                         
											  </td>
											  <td width="147" >
											  	<netui:select dataSource="actionForm.mesFin1" styleClass="big" tagId="actionForm_mesFin1">
												  <netui:selectOption value="0">Mes</netui:selectOption>
												  <netui:selectOption value="ENERO" >Enero</netui:selectOption>
												  <netui:selectOption value="FEBRERO" >Febrero</netui:selectOption>
												  <netui:selectOption value="MARZO" >Marzo</netui:selectOption>
												  <netui:selectOption value="ABRIL" >Abril</netui:selectOption>
												  <netui:selectOption value="MAYO" >Mayo</netui:selectOption>
												  <netui:selectOption value="JUNIO" >Junio</netui:selectOption>
												  <netui:selectOption value="JULIO" >Julio</netui:selectOption>
												  <netui:selectOption value="AGOSTO" >Agosto</netui:selectOption>
												  <netui:selectOption value="SEPTIEMBRE" >Septiembre</netui:selectOption>
												  <netui:selectOption value="OCTUBRE" >Octubre</netui:selectOption>
												  <netui:selectOption value="NOVIEMBRE" >Noviembre</netui:selectOption>
												  <netui:selectOption value="DICIEMBRE" >Diciembre</netui:selectOption>
												</netui:select>                                          
											  </td>
											  <td width="150" >
											  	<netui:select dataSource="actionForm.anioFin1" styleClass="big" tagId="actionForm_anioFin1">
											  		<netui:selectOption value="0">Año</netui:selectOption>										   
												   <% 
												   	 Date fecha = new Date();
												   	 Calendar calendario = Calendar.getInstance();
												   	 calendario.setTime(fecha);
												   	 int anioInicio = calendario.get(Calendar.YEAR)-100;
												   	 
												   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
												   %>
												   		<netui:selectOption value="<%=String.valueOf(anio)%>"><%=anio%></netui:selectOption>
												   <% 
												   	 }
												   %>
												</netui:select>                                             
											  </td>
											</tr>
										</table>
									</td>								
								</tr>								
								<tr>
									<td><netui:label for="actionForm_responsabilidades1" value="* Descripción de Responsabilidades" /></td>
								</tr>
								<tr>
									<td><netui:textArea dataSource="actionForm.responsabilidades1" tagId="actionForm_responsabilidades1" cols="50" rows="5" onKeyPress="return isAlphaNumeric(event);"/></td>
								</tr>
								<tr>
									<td><netui:label for="actionForm_empresa2" value="   Empresa (Empleo anterior)" /></td>
									<td><netui:label for="actionForm_puesto2" value="   Puesto" /></td>
								</tr>
								<tr>
									<td><netui:textBox dataSource="actionForm.empresa2" tagId="actionForm_empresa2" styleClass="big" maxlength="250" onKeyPress="return isAlpha(event);"></netui:textBox></td>
									<td><netui:textBox dataSource="actionForm.puesto2" tagId="actionForm_puesto2" styleClass="big" maxlength="250" onKeyPress="return isAlpha(event);"/></td>
								</tr>								
								<tr>
									<td><netui:label for="actionForm_fechaInicio2" value="   Fecha de inicio" /></td>
									<td><netui:label for="actionForm_fechaFin2" value="   Fecha de terminación" /></td>
								</tr>
								<tr>
									<td>
										<table width="400" border="0" cellspacing="0" cellpadding="0">
											<tr>
											  
											  <td width="79" height="25" >
											  	<netui:select dataSource="actionForm.diaInicio2" styleClass="big" tagId="actionForm_diaInicio2">
											  		<netui:selectOption value="0">Día</netui:selectOption>
													<% 
												  		for(int i=1; i<=31 ;i++){
												  	%>
														<netui:selectOption value='<%=String.valueOf(i)%>'><%=i%></netui:selectOption>
													<%
														} 
													%>
												</netui:select>
										      </td>
											  <td width="147" >
											  	<netui:select dataSource="actionForm.mesInicio2" styleClass="big" tagId="actionForm_mesInicio2">
												 <netui:selectOption value="0">Mes</netui:selectOption>
												  <netui:selectOption value="ENERO" >Enero</netui:selectOption>
												  <netui:selectOption value="FEBRERO" >Febrero</netui:selectOption>
												  <netui:selectOption value="MARZO" >Marzo</netui:selectOption>
												  <netui:selectOption value="ABRIL" >Abril</netui:selectOption>
												  <netui:selectOption value="MAYO" >Mayo</netui:selectOption>
												  <netui:selectOption value="JUNIO" >Junio</netui:selectOption>
												  <netui:selectOption value="JULIO" >Julio</netui:selectOption>
												  <netui:selectOption value="AGOSTO" >Agosto</netui:selectOption>
												  <netui:selectOption value="SEPTIEMBRE" >Septiembre</netui:selectOption>
												  <netui:selectOption value="OCTUBRE" >Octubre</netui:selectOption>
												  <netui:selectOption value="NOVIEMBRE" >Noviembre</netui:selectOption>
												  <netui:selectOption value="DICIEMBRE" >Diciembre</netui:selectOption>
												</netui:select>                                          
											  </td>
											  <td width="150" >
											    <netui:select dataSource="actionForm.anioInicio2" styleClass="big" tagId="actionForm_anioInicio2">
											  		<netui:selectOption value="0">Año</netui:selectOption>										   
												   <% 
												   	 Date fecha = new Date();
												   	 Calendar calendario = Calendar.getInstance();
												   	 calendario.setTime(fecha);
												   	 int anioInicio = calendario.get(Calendar.YEAR)-100;
												   	 
												   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
												   %>
												   		<netui:selectOption value="<%=String.valueOf(anio)%>"><%=anio%></netui:selectOption>
												   <% 
												   	 }
												   %>
												</netui:select>                                          
											  </td>
											</tr>
										</table> 
									</td>
									<td>
										<table width="400" border="0" cellspacing="0" cellpadding="0">
											<tr>
											  
											  <td width="79" height="25" >
											  	<netui:select dataSource="actionForm.diaFin2" styleClass="big" tagId="actionForm_diaFin2">
											  		<netui:selectOption value="0">Día</netui:selectOption>
													<% 
												  		for(int i=1; i<=31 ;i++){
												  	%>
														<netui:selectOption value='<%=String.valueOf(i)%>'><%=i%></netui:selectOption>
													<%
														} 
													%>
												</netui:select>                                          
											  </td>
											  <td width="147" >
											    <netui:select dataSource="actionForm.mesFin2" styleClass="big" tagId="actionForm_mesFin2">
												  <netui:selectOption value="0">Mes</netui:selectOption>
												  <netui:selectOption value="ENERO" >Enero</netui:selectOption>
												  <netui:selectOption value="FEBRERO" >Febrero</netui:selectOption>
												  <netui:selectOption value="MARZO" >Marzo</netui:selectOption>
												  <netui:selectOption value="ABRIL" >Abril</netui:selectOption>
												  <netui:selectOption value="MAYO" >Mayo</netui:selectOption>
												  <netui:selectOption value="JUNIO" >Junio</netui:selectOption>
												  <netui:selectOption value="JULIO" >Julio</netui:selectOption>
												  <netui:selectOption value="AGOSTO" >Agosto</netui:selectOption>
												  <netui:selectOption value="SEPTIEMBRE" >Septiembre</netui:selectOption>
												  <netui:selectOption value="OCTUBRE" >Octubre</netui:selectOption>
												  <netui:selectOption value="NOVIEMBRE" >Noviembre</netui:selectOption>
												  <netui:selectOption value="DICIEMBRE" >Diciembre</netui:selectOption>
												</netui:select>                                          
											  </td>
											  <td width="150" >
											    <netui:select dataSource="actionForm.anioFin2" styleClass="big" tagId="actionForm_anioFin2">
											  		<netui:selectOption value="0">Año</netui:selectOption>										   
												   <% 
												   	 Date fecha = new Date();
												   	 Calendar calendario = Calendar.getInstance();
												   	 calendario.setTime(fecha);
												   	 int anioInicio = calendario.get(Calendar.YEAR)-100;
												   	 
												   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
												   %>
												   		<netui:selectOption value="<%=String.valueOf(anio)%>"><%=anio%></netui:selectOption>
												   <% 
												   	 }
												   %>
												</netui:select>                                         
											  </td>
											</tr>
										</table>
									</td>
								</tr>																
								<tr>
									<td><netui:label for="actionForm_responsabilidades2" value="   Descripción de Responsabilidades" /></td>
								</tr>
								<tr>
									<td><netui:textArea dataSource="actionForm.responsabilidades2" tagId="actionForm_responsabilidades2" cols="50" rows="5" onKeyPress="return isAlphaNumeric(event);"/></td>
								</tr>
								<tr>
									<td><br/><div id="<netui:rewriteName name="msg_error2" forTagId="true"/>"></div><br></td>
						  		</tr>
								<tr>
									<td>										
										<a href="#" title="Actualizar" class="btn-small margin-bot15" onclick="enviarExpLaboral(this);return false;">Actualizar</a>
									</td>
								</tr>
							</tbody></table>
						</netui:form>
						<br>
					</div>
					<div class="acv-wrap-title acv-title">Información adicional</div>
					<div class="acv-wrap-cont">
					<netui:form  action="actualizaInfoAdicional" tagId="formInfoAdicional" styleClass="formulario">						
							<table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
								<tbody><tr>
									<td><netui:label for="actionForm_idiomas" value="* Idiomas" /></td>
								</tr>
								<tr>
									<td>
										<table width="475" border="0" cellspacing="0" cellpadding="0">
											  <tr>
												<td><netui:textBox dataSource="actionForm.idioma1" tagId="actionForm_idioma1" styleClass="big" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelIdioma1">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;"  value="BASICO" tagId="nivelBasicoI1"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntI1"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoI1"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
											  <tr>
												<td><netui:textBox dataSource="actionForm.idioma2" tagId="actionForm_idioma2" styleClass="big" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelIdioma2">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="BASICO" tagId="nivelBasicoI2"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntI2"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoI2"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
											  <tr>
												<td><netui:textBox dataSource="actionForm.idioma3" tagId="actionForm_idioma3" styleClass="big" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelIdioma3">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="BASICO" tagId="nivelBasicoI3"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntI3"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoI3"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><netui:label for="actionForm_paqueteria" value="* Paquetería" /></td>
								</tr>
								<tr>
									<td>									
										<table width="475" border="0" cellspacing="0" cellpadding="0">
											  <tr>
												<td><netui:textBox dataSource="actionForm.paqueteria1" styleClass="big" tagId="actionForm_paqueteria1" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelPaq1">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="BASICO" tagId="nivelBasicoP1"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntP1"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoP1"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
											  <tr>
											  	<td><netui:textBox dataSource="actionForm.paqueteria2" styleClass="big" tagId="actionForm_paqueteria2" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelPaq2">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="BASICO" tagId="nivelBasicoP2"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntP2"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoP2"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
											  <tr>
											  	<td><netui:textBox dataSource="actionForm.paqueteria3" styleClass="big" tagId="actionForm_paqueteria3" size="20" maxlength="30" onKeyPress="return isAlpha(event);"/></td>
												<netui:radioButtonGroup dataSource="actionForm.nivelPaq3">
													<td height="25" >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="BASICO" tagId="nivelBasicoP3"><label style="vertical-align: middle;">Básico</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="INTERMEDIO" tagId="nivelIntP3"><label style="vertical-align: middle;">Intermedio</label></netui:radioButtonOption> 
												  	</td>
													<td >
														<netui:radioButtonOption style="background: none repeat scroll 0% 0% transparent; border: 0pt none;" value="AVANZADO" tagId="nivelAvanzadoP3"><label style="vertical-align: middle;">Avanzado</label></netui:radioButtonOption>
												  	</td>
												</netui:radioButtonGroup>
											  </tr>
										</table>												
									</td>
								</tr>
								<tr>
									<td><netui:label for="actionForm_money" value="* Pretensión económica (Mensual)" /></td>
								</tr>
								<tr>
									<td>
									
									<netui:label style="vertical-align: middle;" for="actionForm_moneyMin" value="DE" />&nbsp;&nbsp;<netui:textBox dataSource="actionForm.moneyMin" styleClass="big" tagId="actionForm_moneyMin" size="13" maxlength="7" onKeyPress="return isNumeric(event);"/>&nbsp;&nbsp;<netui:label style="vertical-align: middle;" for="actionForm_moneyMax" value="A" />&nbsp;&nbsp;<netui:textBox dataSource="actionForm.moneyMax" styleClass="big" tagId="actionForm_moneyMax" size="13" maxlength="7" onKeyPress="return isNumeric(event);"/></td>
								</tr>
								<tr>
								    <td><br/><div id="<netui:rewriteName name="msg_error3" forTagId="true"/>"></div><br/></td>
							      </tr>
								<tr>
									<td>									
									<a href="#" title="Actualizar" class="btn-small margin-bot15" onclick="enviarInfoAdicional(this);return false;">Actualizar</a>									
								</tr>
							</tbody></table>
						</netui:form>
					</div>
				</div>
				<div align="center" class="span-14">
				<netui:anchor styleClass="btn-small margin-bot15" action="verVacantes">VER VACANTES</netui:anchor></div>
				
				<script type="text/javascript">
					$('.acv-wrap-title').mouseleave(function() {
						if ($(this).attr('class') == "acv-wrap-title acv-title-hov" )  { menuAccCV(this); }
						if ($(this).attr('class') == "acv-wrap-title acv-title-sel-hov" ) { menuAccSelCV(this); }
					});
			
					$('.acv-wrap-title').click(function() {
						if ( $(this).attr('class') == "acv-wrap-title acv-title-sel-hov" ){ 
								closeAllaccCV();
							} else { 
								menuAccSelHoverCV(this);
							    slideAccCV(this);
							}			
						return false;
					});
				</script>				
</netui:scriptContainer>