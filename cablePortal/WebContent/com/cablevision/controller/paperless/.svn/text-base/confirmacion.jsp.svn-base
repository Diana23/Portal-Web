<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
	String nombreCompleto = nombreContacto + " " + account.getApellidoPaterno();
</jsp:scriptlet>
<br>
<h3>Modificación de correo en el servicio Paperless</h3>

<span class="estimateuser">Estimado <jsp:expression>nombreCompleto</jsp:expression></span>
<p>${pageInput.parrafo1}</p>
<c:if test="${!empty pageInput.errores}">
	<div class="clear">
		<font color="Red">
			<netui-data:repeater dataSource="pageInput.errores">${container.item}<br/></netui-data:repeater>
		</font>
	</div>
</c:if>
<c:if test="${empty pageInput.errores}">
	<netui:errors bundleName="paperlessBundle" />
</c:if>	
<p>
	<span class="title-paperless"><strong>El correo electrónico que tenemos registrado es:</strong></span>
	<span>${pageInput.emailActual}</span></p>
<p>
	<netui:anchor action="begin" onClick="submitForma('${form}',this);return false;" title="Cancelar Paperles" styleClass="btn-small fright">Cancelar</netui:anchor>
	<netui:anchor action="mostrarNotificacion" onClick="submitForma('${form}',this);return false;" title="${pageInput.textoBoton}" styleClass="btn-small">${pageInput.textoBoton}</netui:anchor>
</p>
<p class="bord-toped padding-top10">${pageInput.parrafo2}</p>
                                
