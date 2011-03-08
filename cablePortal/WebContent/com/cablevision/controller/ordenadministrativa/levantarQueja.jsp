<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<jsp:useBean id="JSONRPCBridge" scope="session" class="com.metaparadigm.jsonrpc.JSONRPCBridge" />
<%JSONRPCBridge.registerObject("uploadCombos", new com.cablevision.util.UploadCombos());%>


<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<script src="${pageContext.request.contextPath}/com/cablevision/controller/ordenadministrativa/ordenAdmin.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/framework/skins/cablevision/js/jsonrpc.js" type="text/javascript"></script>

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
			<strong>Levantar una queja</strong><br>
			<strong>Solicitud de Servicio: </strong><br>
			<strong>Contrato: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/><br>
			<strong>Nombre de la Cuenta: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNameAccount}"/><br>
		</p>
  

	  <netui:form action="guardarLevantarQueja" tagId="ationForm_guardarQueja" styleClass="formulario" enctype="multipart/form-data">
	  	<input type="hidden" value="${pageContext.request.contextPath}" id="<netui:rewriteName name="contexto" forTagId="true"/>">
	  	
	    <ul class="bord-toped padding-top10">
	    	<li>
	    		<label>Selecciona el &Aacute;rea con la cu&aacute;l est&aacute; <br>Relacionada tu inconformidad (*)</label><br>
	    	</li>
		    <li>
		    	<netui:select dataSource="actionForm.area" tagId="actionForm_area"  styleClass="selectAuto" defaultValue=" " onChange="cargarSubCombo(this);">
			 		<netui:selectOption value=" ">...</netui:selectOption>
			 		<c:forEach var="option" items="${pageInput.combo}">
			 			<netui:selectOption value="${option.value}"/>
			 		</c:forEach>
			 	</netui:select>
			</li>
			<li>
				<label>Selecciona el Motivo (*)</label><br>
			</li>
			<li>
				<div id="<netui:rewriteName name="subComboDiv" forTagId="true"/>"> </div>
				<netui:select dataSource="actionForm.motivo" tagId="actionForm_motivo" defaultValue=" " styleClass="selectAuto">
			 		<netui:selectOption value=" ">...</netui:selectOption>
			 	</netui:select>
			</li>
			<li>
				<label>Fecha y Hora Aproximadas de tu Mala Experiencia<br> con CABLEVISI&Oacute;N<sup>&reg;</sup>:</label><br>
				<netui:textBox dataSource="actionForm.fechaHora" tagId="actionForm_fechaHora" styleClass="txt" size="40" maxlength="21" onKeyPress="return isAlphaNumeric(event);"/>
			</li>
			<li>
				<label>Nombre de la Persona que te Atendi&oacute;:</label><br>
				<netui:textBox dataSource="actionForm.quienAtendio" tagId="actionForm_quienAtendio" styleClass="txt" size="40" maxlength="40" onKeyPress="return isAlpha(event);"/>
			</li>
			<li>
				<label>Descripci&oacute;n del problema (*)</label><br><span class="caracteres"> (200 caracteres como m&aacute;ximo)</span><br>
	            <netui:textArea dataSource="actionForm.descripcion" cols="40" rows="5" styleClass="txt" tagId="actionForm_descripcion" onKeyUp="return textLimit(this,200);" onKeyPress="return isAlphaNumeric(event);"/>
	        </li>
	        <li>
	        	<label>Comentarios Adicionales:</label><br><span class="caracteres"> (100 caracteres como m&aacute;ximo)</span><br>
	        	<netui:textArea dataSource="actionForm.comentario" tagId="actionForm_comentario" styleClass="txt" cols="40" rows="5" onkeyup="return textLimit(this, 100);" onKeyPress="return isAlphaNumeric(event);"/>
	        </li>
	        <li><span>(*) Campos obligatorios</span></li>
		</ul>
	
	  	<div style="display:none;">
			<netui:rewriteName  name="ationForm_guardarQueja" forTagId="true" resultId="ationForm_guardarQueja"/>
		</div>
	  	
	  	<div class="margin-top15 marg-rig40">
			<netui:anchor  onClick="submitForma('${ationForm_guardarQueja}',this);return false;" formSubmit="true" action="guardarLevantarQueja" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
				<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
			</netui:anchor>
			<netui:anchor action="mostrarBienvenida" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
				<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
			</netui:anchor>
	  	</div>
	  	
	 </netui:form>
</div>

</netui:scriptContainer>