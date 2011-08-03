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
			<strong>Error en factura</strong><br>
			<strong>Solicitud de Servicio: </strong><br>
			<strong>Contrato: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/><br>
			<strong>Nombre de la Cuenta: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNameAccount}"/><br>
		</p>
	
	
		<netui:form action="guardarErrorFactura" tagId="formGuardarError" styleClass="formulario">
		
		    <ul class="bord-toped padding-top10">
		    	<li>
		    		<netui:label for="actionForm_tipoProblema" value="Indicar Tipo de Problema (*)"/>
		    	</li>
			    <li>
			    	<netui:select dataSource="actionForm.tipoProblema" defaultValue=" " tagId="actionForm_tipoProblema"  styleClass="selectP">
				 		<netui:selectOption value=" ">...</netui:selectOption>
				 		<c:forEach var="option" items="${pageInput.combo}">
				 			<netui:selectOption value="${option.value}"/>
				 		</c:forEach>
				 	</netui:select>
				</li>
				<li>
					<label>Descripci&oacute;n del problema (*)</label><span class="caracteres"> (250 caracteres como m&aacute;ximo)</span><br>
				</li>
				<li>
		             <netui:textArea dataSource="actionForm.descripcion" cols="50" rows="4" styleClass="txt" tagId="actionForm_descripcion" onKeyUp="return textLimit(this,250);" onKeyPress="return isAlphaNumeric(event);"/>
		         </li>
		         <li><span>(*) Campos obligatorios</span></li>
			</ul>
	  	
		  	<div style="display:none;">
				<netui:rewriteName  name="formGuardarError" forTagId="true" resultId="formGuardarError"/>
			</div>
		  	
		  	<div class="margin-top15 marg-rig40">
				<netui:anchor  onClick="submitForma('${formGuardarError}',this);return false;" formSubmit="true" action="guardarErrorFactura" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
				</netui:anchor>
		  		<netui:anchor action="mostrarBienvenida" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
		  			<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		  		</netui:anchor>
			</div>
		
	 	</netui:form>
</div>
 
</netui:scriptContainer>
