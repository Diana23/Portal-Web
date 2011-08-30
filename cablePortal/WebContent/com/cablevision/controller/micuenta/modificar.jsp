<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>

<!-- 
<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
<netui:form action="guardarCuenta" tagId="formreg1" styleClass="formulario" enctype="multipart/form-data">

<div class="content-form margin-top10 marg-rig40">
	<span class="title-factura">Modificaci&oacute;n de mis datos</span>
	<table width="100%">
		<tr>
			<td colspan="2">
				<netui:errors bundleName="micuentaBundle" />
				<c:if test="${!empty pageInput.errores}">
					<br/>
					<font class="error-msg">
	        			${pageInput.errores}
	        		</font>
	        	</c:if>
	        	<c:if test="${!empty pageInput.exito}">
					<br/>
	        		<font class="msg-success">
	        			${pageInput.exito}
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
	<ul class="pago-form">
		<li class="right-li">
			<span class="category">
				<label>Nombre (*)</label>
				<netui:textBox  dataSource="actionForm.nombre" styleClass="big" size="50" maxlength="50" onKeyPress="return isAlpha(event);"/>
			</span>
		</li>
		<li>
			<span class="category">
				<label>Correo Electr&oacute;nico (*)</label>
				<netui:textBox dataSource="actionForm.email" styleClass="big" size="50" maxlength="50"/>
			</span>
		</li>
		<li class="right-li">
			<span class="category">
				<label>Apellido Paterno (*)</label>
				<netui:textBox dataSource="actionForm.apellidoPaterno" styleClass="big" size="50" maxlength="50" onKeyPress="return isAlpha(event);"/>
			</span>
		</li>
		<li>
			<span class="category">
				<label>Confirma Correo Electr&oacute;nico (*) </label><br>
				<netui:textBox dataSource="actionForm.emailConfirmacion" styleClass="big" size="50" maxlength="50"/>
			</span>
		</li>
		<li class="right-li">
			<span class="category">
				<label>Apellido Materno</label>
				<netui:textBox dataSource="actionForm.apellidoMaterno" styleClass="big" size="50" maxlength="50" onKeyPress="return isAlpha(event);"/>
			</span>
		</li>
		<li>
			<span class="category">
				<label>Sube tu foto</label><br>
				<netui:fileUpload dataSource="actionForm.foto"/>
			</span>
		</li>
	</ul>
	<div class="clear"></div>
</div>

<div class="margin-top15 marg-rig40">
 -->
 <!-- 
	<netui:anchor formSubmit="true" action="guardarCuenta" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
		<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
	</netui:anchor>
	 -->
<!-- 	
	<span>(*) Campos obligatorios</span>
	<div class="clear"></div>
</div>


</netui:form>
 -->
