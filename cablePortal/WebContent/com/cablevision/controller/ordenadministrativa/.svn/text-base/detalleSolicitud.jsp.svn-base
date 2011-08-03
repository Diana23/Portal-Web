<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<script src="${pageContext.request.contextPath}/com/cablevision/controller/ordenadministrativa/ordenAdmin.js" type="text/javascript"></script>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>

<netui:scriptContainer>

  	<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	

	<div class="content-pago">
		<h3 class="side">Solicitud de Servicio</h3>
		
		<netui:form action="mostrarDetalle" tagId="fomaDetalle" styleClass="formulario">
			<c:if test="${!empty actionForm.backward}">
				<table width="530" cellspacing="0" cellpadding="5">
					<tr>
						<td>
							<c:if test="${!empty pageInput.errores}">
								<br/>
								<font class="error-msg">
				        			${pageInput.errores}
				        		</font>
				        	</c:if>
				        	<c:if test="${empty pageInput.errores}">
								<br/>
								<font class="error-msg">
				        			<netui:errors bundleName="ordenAdminBundle" />
				        		</font>
				        	</c:if>
				        	<c:if test="${!empty pageInput.successMsgSolicitud}">
								<br/>
				        		<font class="msg-success">
				        			${pageInput.successMsgSolicitud}
				        		</font>
				        	</c:if>
				        	<c:if test="${!empty pageInput.msg}">
								<br/>
								<font class="error-msg">
				        			${pageInput.msg}
				        		</font>
				        	</c:if>
						</td>
					</tr>
					<tr>
						<td class="revisa"><strong>Gracias por utilizar este tu Centro de Atenci&oacute;n a Clientes en L&iacute;nea.</strong> <br>
						  <br>
							El tiempo m&aacute;ximo para atender esta solicitud ser&aacute; de 72 horas. <br>
							En cualquier momento podr&aacute;s consultar las solicitudes de servicio que hayas enviado a trav&eacute;s de la opci&oacute;n "Mis Solicitudes de Servicio" del men&uacute; de este portal. En cualquier momento podr&aacute;s enviarnos comentarios a tus solicitudes abiertas y/o archivos adjuntos que se te pide enviar. 
						</td>
					</tr>
					<tr>
						<td class="revisa">No dejes de visitar este centro de servicios a lo largo del tiempo. Todo con el fin de servirte mejor</td>
					</tr>
				</table>
			</c:if>
		
			<table width="530" cellspacing="0" cellpadding="5">
				<tr>
					<c:if test="${!empty pageInput.errores}">
						<br/>
						<font class="error-msg">
		        			${pageInput.errores}
		        		</font>
		        	</c:if>
		        	<c:if test="${empty pageInput.errores}">
						<br/>
						<font class="error-msg">
		        			<netui:errors bundleName="ordenAdminBundle" />
		        		</font>
		        	</c:if>
		        	<c:if test="${!empty pageInput.msg}">
						<br/>
						<font class="error-msg">
		        			${pageInput.msg}
		        		</font>
		        	</c:if>		
				</tr>
				<!-- valida que venga el no.servicio sin errores -->
				<c:if test="${empty pageInput.errores}">
					<tr>
						<td height="205" class="revisa">
					  		<table cellspacing="0" cellpadding="0" align="center">
                            	<tr>
                                	<td align="right" width="50%"><strong>Contrato: </strong></td>
                                   	<td width="50%"><netui:label value="${actionForm.contrato}"/></td>
                                </tr>
                                <tr>
                                 	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Nombre de la Cuenta: </strong></td>
                                  	<td><netui:label value="${actionForm.nombreCuenta}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Tipo de Problema: </strong></td>
                                  	<td><netui:label value="${actionForm.tipoProblema}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Subtipo de Problema: </strong></td>
                                  	<td><netui:label value="${actionForm.subtipoProblema}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Motivo de Problema: </strong></td>
                                  	<td><netui:label value="${actionForm.motivoProblema}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td valign="top" align="right"><strong>Descripci&oacute;n: </strong></td>
                                  	<td>
                                  		<textarea class="ta-atencion-clientes" readonly="readonly"><c:out value="${actionForm.descripcion}"/></textarea>
          				</td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>N&uacute;mero de Solicitud: </strong></td>
                                  	<td><netui:label value="${actionForm.noSolicitud}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Creado el: </strong></td>
                                  	<td><netui:label value="${actionForm.creado}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
                                <tr>
                                  	<td align="right"><strong>Estado: </strong></td>
                                  	<td><netui:label value="${actionForm.estado}"/></td>
                                </tr>
                                <tr>
                                  	<td colspan="2"></td>
                                </tr>
				 				<c:if test="${empty pageInput.fax}">
				 					<tr>
										<td colspan="2" height="10" align="center" style="display:none"><br>
											Si requiere de enviar documentaci&oacute;n adicional para su Solicitud
											de Servicio, haga <netui:anchor action="mostrarPortadaFax" popup="true" styleClass="linkC">click aqu&iacute;</netui:anchor> para generar autom&aacute;ticamente 
											la car&aacute;tula de fax.
										</td>
									</tr>
								</c:if>
								<c:if test="${!empty pageInput.fax}">
									<tr>
										<td colspan="2" height="10" align="center"><br>
											Si requiere de enviar documentaci&oacute;n adicional para su Solicitud
											de Servicio, haga <netui:anchor action="mostrarPortadaFax" popup="true" styleClass="linkC">click aqu&iacute;<netui:parameter name="noSolicitud" value="${pageInput.solicitud}"/></netui:anchor> para generar autom&aacute;ticamente 
											la car&aacute;tula de fax.
										</td>
									</tr>
								</c:if>
                             </table>																	 
                         </td>
					</tr>
				</c:if>
				
				<!-- verifica que el no de solicitud exista -->
	            <c:if test="${empty pageInput.errores}">             
	            	<tr>
						<td height="350">
							<c:if test="${!empty pageInput.successMsgComentario}">
								<font class="msg-success">
									${pageInput.successMsg}
								</font>
							</c:if>
							<table width="622" height="150" cellspacing="0" cellpadding="0" border="0" class="margin-top40" id="tabla-importes">
								<tbody>
									<tr>
										<td width="311" valign="top" height="80" align="center" class="paddtop-in-table">
											<strong>Comentario</strong>
										</td>
										<td width="311" valign="top" height="80" align="center" class="paddtop-in-table">
											<strong>Fecha</strong>
										</td>
									</tr>
									<c:if test="${empty pageInput.comentariosList}">
										<tr>
											<td colspan="2" class="revisaCentrado">
											  Al momento no tienes Comentarios.
											</td>
										</tr>
									</c:if>
									
									<c:if test="${!empty pageInput.comentariosList}">
										<c:forEach var="comentario" items="${pageInput.comentariosList}">
											<tr>
												<td width="95" valign="top" align="center">
												  <c:out value="${comentario.coaComment}"/>
												</td>
												<td width="100" valign="top" align="center">
													<netui:label value="${comentario.coaDate}"><netui:formatDate pattern="dd/MM/yyyy"/></netui:label>
												</td>
											   </tr>
										</c:forEach>
									</c:if>
									
									<tr>
										<td width="622" valign="top" height="70" align="left" alt="" colspan="4" class="paddtop-in-table rowspaned">
											<c:if test="${empty pageInput.finalizada}">
												<netui:anchor action="mostrarAgregaComentario" value="Agregar Comentario" title="Agregar Comentario" styleClass="btn-small  margin-bot15 bs-2-lines fright">
													<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
												</netui:anchor>
											</c:if>
										</td>
									</tr>
								</tbody>
							</table>
				  			<br><br>
					  		<c:if test="${!empty pageInput.successMsgArchivo}">
								<font class="msg-success">
									${pageInput.successMsgArchivo}
								</font>
							</c:if>
							
							<table width="622" height="150" cellspacing="0" cellpadding="0" border="0" class="margin-top40" id="tabla-importes">
								<tbody>
									<tr>
										<td width="207" valign="top" height="80" align="center" alt="" class="paddtop-in-table">
											<strong>Archivo Adjunto</strong>
										</td>
										<td width="208" valign="top" height="80"  align="center" alt="" class="paddtop-in-table">
											<strong>Tama&ntilde;o</strong>
										</td>
										<td width="207" valign="top" height="80"  align="center" alt="" class="paddtop-in-table">
											<strong>Fecha</strong>
										</td>
									</tr>
									<c:if test="${empty pageInput.archivosList}">
										<tr>
											<td colspan="4" class="revisaCentrado">
											  Para agregar un archivo adjunto a tu solicitud, has clic en el bot&oacute;n.
											</td>
	                                    </tr>
									</c:if>
									<c:if test="${!empty pageInput.archivosList}">
	                                   <c:forEach var="archivo" items="${pageInput.archivosList}">
											<tr>
												<td width="100" valign="top" align="center">
													<c:out value="${archivo.nombre}"/>
												</td>
												<td width="100" valign="top" align="center">
												  	<c:out value="${archivo.size}"/> bytes
												</td>
												<td width="100" valign="top" align="center">
													<netui:label value="${archivo.fecha}"><netui:formatDate pattern="dd/MM/yyyy"/></netui:label>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<tr>
										<td width="622" valign="top" height="70" align="left" alt="" colspan="4" class="paddtop-in-table rowspaned">
											<c:if test="${empty pageInput.finalizada}">
												<netui:anchor action="mostrarAgregaArchivo" title="Agregar Archivo" value="Agregar Archivo" styleClass="btn-small  margin-bot15 bs-2-lines fright">
													<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
												</netui:anchor>
											</c:if>
										</td>
									</tr>
								</tbody>
							</table>
			  			</td>
					</tr>
				</c:if>
			</table>
			
			<div style="display:none;">
				<netui:rewriteName  name="fomaDetalle" forTagId="true" resultId="fomaDetalle"/>
			</div>
		
		</netui:form>
	</div>
  
</netui:scriptContainer>