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
<br/>
<p>
	<strong>N&uacute;mero de contrato:</strong> ${sessionScope._MI_CUENTA.cvNumberAccount }<br>
	<strong>Operaci&oacute;n:</strong> ${pageInput.operacion}<br>
	<strong>Fecha de Operaci&oacute;n:</strong> ${pageInput.fecha}<br>
	<strong>Correo Electrónico Registrado:</strong> ${pageInput.email}<br>
	<br>
	${pageInput.notificacionFooter} 
	<br>
	<netui:anchor href="${pageContext.request.contextPath}/com/cablevision/controller/micuenta/begin.do" title="Aceptar" styleClass="btn btn-small margin-bot40 fright">Aceptar</netui:anchor>
</p>