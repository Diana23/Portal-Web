<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<script src="${pageContext.request.contextPath}/com/cablevision/controller/ordenadministrativa/ordenAdmin.js" type="text/javascript"></script>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>


<netui:scriptContainer>
  <br/>
  
  <div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
	
  	<div class="content-pago">
  		<h3 class="side">Solicitud de Servicio</h3>
		
		<table width="530" cellspacing="0" cellpadding="5">
			<tr>
				<td align="right" width="40%">
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
		</table>
		
		<p class="bord-toped padding-top15">
			<c:if test="${!empty pageInput.tipoPago}">
				<strong>Dar de alta mi tarjeta de cr&eacute;dito para pago recurrente</strong><br>
			</c:if>
			<c:if test="${empty pageInput.tipoPago}">
				<strong>Cambio de la tarjeta de cr&eacute;dito que uso para el cargo recurrente</strong><br>
			</c:if>
			<strong>Solicitud de Servicio </strong><br>
			<strong>Contrato: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/><br>
			<strong>Nombre de la Cuenta: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNameAccount}"/><br>
		</p>
		
		<netui:form action="guardarPagoRecurrente" tagId="ationForm_guardarPagoRecurrente" styleClass="formulario" enctype="multipart/form-data">
			<input type="hidden" name="tipoPago" id="<netui:rewriteName name="tipoPago" forTagId="true" />" value="${pageInput.tipoPago}">
			
			<ul class="bord-toped padding-top10">
				<li>
					<label>Tipo de Tarjeta (*)</label>
				</li>
				 <li>
					<netui:select dataSource="actionForm.tipoTarjeta" tagId="actionForm_tipoProblema" styleClass="selectAuto" style="width:220px;">
						<netui:selectOption value="">...</netui:selectOption>
						<c:forEach var="option" items="${pageInput.comboTarjetas}">
							<netui:selectOption value="${option.value}"/>
						</c:forEach>
					</netui:select>
				 </li>
				 <li>
				 	<label>Banco (*)</label>
				 </li>
				 <li>
					<netui:select dataSource="actionForm.banco" tagId="actionForm_tipoProblema"  styleClass="selectP">
						<netui:selectOption value="">...</netui:selectOption>
						<c:forEach var="optionBanco" items="${pageInput.comboBancos}">
							<netui:selectOption value="${optionBanco.value}"/>
						</c:forEach>
					</netui:select>
				 </li>
				 <li>
				 	<label>N&uacute;mero de Tarjeta (*)</label>
				 </li>
				 <li>
					<netui:textBox  tagId="actionForm_noTarjeta" dataSource="actionForm.noTarjeta" styleClass="txt" size="30" maxlength="16" onKeyPress="return soloNumeros(event);" />
				 </li>
				 <li>
				 </li>
				 	<label>Titular de la Tarjeta (*)</label>
				 <li>
					<netui:textBox dataSource="actionForm.titularTarjeta" styleClass="txt" size="30" maxlength="50" onKeyPress="return isAlpha(event);"/>
				 </li>
				 <li>
				 	<label>Fecha de vencimiento (*)</label>
				 </li>
				 <li>
					<netui:select dataSource="actionForm.mesVencimiento" tagId="actionForm_tipoProblema" styleClass="selectAuto">
						<netui:selectOption value="">Mes</netui:selectOption>
						<netui:selectOption value="01"/>
						<netui:selectOption value="02"/>
						<netui:selectOption value="03"/>
						<netui:selectOption value="04"/>
						<netui:selectOption value="05"/>
						<netui:selectOption value="06"/>
						<netui:selectOption value="07"/>
						<netui:selectOption value="08"/>
						<netui:selectOption value="09"/>
						<netui:selectOption value="10"/>
						<netui:selectOption value="11"/>
						<netui:selectOption value="12"/>
					</netui:select>
					<netui:select dataSource="actionForm.anioVencimiento" tagId="actionForm_tipoProblema" styleClass="selectAuto">
						<netui:selectOption value="">A&ntilde;o</netui:selectOption>
						<netui:selectOption value="07"/>
						<netui:selectOption value="08"/>
						<netui:selectOption value="09"/>
						<netui:selectOption value="10"/>
						<netui:selectOption value="11"/>
						<netui:selectOption value="12"/>
						<netui:selectOption value="13"/>
						<netui:selectOption value="14"/>
						<netui:selectOption value="15"/>
					</netui:select>
				 </li>
		         <li><span>(*) Campos obligatorios</span></li>
		         <li>
		         	<p>
		         		Para que tu solicitud de servicio quede completa y as&iacute; pueda ser procesada es necesario que nos proporciones los siguientes documentos: </br>
						<ul>
							<li>Copia de la Identificaci&oacute;n Oficial del titular de la tarjeta.</li>
							<li>Carta de autorizaci&oacute;n para cargos recurrentes. Obt&eacute;n formato <netui:anchor action="mostrarCartaAutorizacion" styleClass="linkC" popup="true">aqu&iacute;</netui:anchor></li>
						</ul>
						<label>Por favor especifica la forma en la cual nos proporcionar&aacute;s estos documentos: </label><br>
					</p>
		         </li>
				 <li>
					<netui:radioButtonGroup dataSource="actionForm.tipoArchivo" defaultValue="fax">
						<ul>
							<li>
								<netui:radioButtonOption value="archivo">
									Archivos Digitales (El tama&ntilde;o m&aacute;ximo permitido para cada archivo adjunto es 1Mb)
								</netui:radioButtonOption>
							</li>
							<li>
								<label>Copia de la identificación Oficial del titular de la tarjeta: </label><br>
								<netui:fileUpload dataSource="actionForm.identificacion" size="40" tagId="actionForm_archivoDigital" styleClass="txt"/><br>
								
								<label>Copia de la carta de autorización para cargos recurrentes firmada: </label><br>
								<netui:fileUpload dataSource="actionForm.cartaAutorizacion" size="40" tagId="actionForm_archivoDigital" styleClass="txt"/>
							</li>
							<li>
								<netui:radioButtonOption value="fax">
									Fax (Podr&aacute;s imprimir la car&aacute;tula del fax una vez que hayas enviado esta solicitud de servicio). 
								</netui:radioButtonOption>
							</li>
						</ul>
					</netui:radioButtonGroup>
				 </li>
			</ul>
			<netui:parameter name="tipoPago" value="${pageInput.tipoPago}"/>
			
			<div style="display:none;">
				<netui:rewriteName  name="ationForm_guardarPagoRecurrente" forTagId="true" resultId="ationForm_guardarPagoRecurrente"/>
			</div>
		  	
		  	<div class="margin-top15 marg-rig40">
		  		<netui:anchor formSubmit="true" action="guardarPagoRecurrente" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
		        	<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		        	<netui:parameter name="_nfto" value="false"/>
		        </netui:anchor>
		        <netui:anchor action="mostrarBienvenida" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
		        	<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		        </netui:anchor>
			</div>
			
		 </netui:form>
	</div>
  
</netui:scriptContainer>