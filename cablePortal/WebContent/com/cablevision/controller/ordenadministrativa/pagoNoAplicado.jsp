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

	<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
	
  	<div class="content-pago">
		<h3 class="side">Solicitud de Servicio</h3>
		<table width="100%">
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
			<strong>Pagos no aplicados o aplicados err&oacute;neamente</strong><br>
			<strong>Solicitud de Servicio: </strong><br>
			<strong>Contrato: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/><br>
			<strong>Nombre de la Cuenta: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNameAccount}"/><br>
	    </p>
	    
	    <netui:form action="guardarPagoNoAplicado"  styleClass="formulario" enctype="multipart/form-data" tagId="formGuardarPago">
		 	<ul class="bord-toped padding-top10">
		        <li>
					<label>Indicar Tipo de Problema (*)</label><br>
					<netui:select dataSource="actionForm.tipoProblema" tagId="actionForm_tipoProblema"  styleClass="selectP">
				 		<netui:selectOption value="">...</netui:selectOption>
				 		<c:forEach var="option" items="${pageInput.combo}">
				 			<netui:selectOption value="${option.value}"/>
				 		</c:forEach>
				 	</netui:select>
		        </li>
		         <li>
		         	<label>Descripci&oacute;n del problema (*)</label><span class="caracteres"> (250 caracteres como m&aacute;ximo)</span><br>                                            
		             <netui:textArea dataSource="actionForm.descripcionProblema" cols="50" rows="4" styleClass="txt" tagId="actionForm_descripcionProblema" onKeyUp="return textLimit(this,250);" onKeyPress="return isAlphaNumeric(event);"/>
		         </li>
		         <li><span>(*) Campos obligatorios</span></li>
		         <li>
		         	<p>Para que tu solicitud quede completa y así pueda ser procesada es necesario que nos proporciones una copia del comprobante de tu pago (ficha de dep&oacute;sito, comprobante de la transferencia o estado de cuenta bancario donde se acredite el pago).</p>			  
					<p>Por favor especifica la forma en la cual nos proporcionarás este comprobante: </p>
		         </li>
		         <li>
		         	<netui:radioButtonGroup dataSource="actionForm.comprobante" defaultValue="fax">
			         	<ul>
			         		<li>
			         			<netui:radioButtonOption value="archivo">
			         				Archivo digital (El tama&ntilde;o m&aacute;ximo permitido para el archivo adjunto es 1Mb)
			         			</netui:radioButtonOption>
			         		</li>
			         		<li>
			         			<netui:fileUpload dataSource="actionForm.archivoDigital" size="40" tagId="actionForm_archivoDigital" styleClass="txt"/>
			         		</li>
			         		<li>
			         			<netui:radioButtonOption value="fax">
			         				Fax (Podr&aacute;s imprimir la car&aacute;tula del fax una vez que hayas enviado esta solicitud de servicio).
			         			</netui:radioButtonOption>
			         		</li>
			         		<li></li>
			         	</ul>
		         	</netui:radioButtonGroup>
		         </li>
		    </ul>
		    
		    <div style="display:none;">
				<netui:rewriteName  name="formGuardarPago" forTagId="true" resultId="formGuardarPago"/>
			</div>
		
		    <div class="margin-top15 marg-rig40">
		      	<netui:anchor formSubmit="true" action="guardarPagoNoAplicado" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
		        	<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		        	<netui:parameter name="_nfto" value="false"/>
		        </netui:anchor>
		        <netui:anchor action="mostrarBienvenida" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
		        	<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		        	<netui:parameter name="_nfto" value="false"/>
		        </netui:anchor>		      
		    </div>
		 </netui:form>
	</div>

 </netui:scriptContainer>
