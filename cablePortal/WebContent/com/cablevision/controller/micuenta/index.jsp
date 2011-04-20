<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
	String nombreCompleto = nombreContacto + " " + account.getApellidoPaterno();
	String saldo = account.getCvLastBalance() != null ? account.getCvLastBalance() : "";
	String fechaFactura = account.getFechaFactura() != null ? account.getFechaFactura() : "";
	String fechaLimite = account.getFechaLimite() != null ? account.getFechaLimite() : "";
	String email = account.getCorreoContacto() != null ? account.getCorreoContacto() : "";
	String direccion = account.getCvMailAddres() != null ? account.getCvMailAddres() : "";
	String noContrato = account.getCvNumberAccount() != null ? account.getCvNumberAccount() : "";
	String telefono = account.getTelefonoPrincipal() != null ? account.getTelefonoPrincipal() : "";
</jsp:scriptlet>

<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>
<p>
	<strong>Saldo de la &uacute;ltima factura:</strong>   $ <jsp:expression>saldo</jsp:expression><br/>
	<strong>Fecha de Facturaci&oacute;n:</strong> <jsp:expression>fechaFactura</jsp:expression><br/>
	<strong>Fecha L&iacute;mite de Pago:</strong> <jsp:expression>fechaLimite</jsp:expression><br/>
	<strong>Nombre:</strong> <jsp:expression>nombreCompleto</jsp:expression><br/>
	<strong>Correo electr&oacute;nico:</strong> <jsp:expression>email</jsp:expression><br/>
	<strong>Direcci&oacute;n de env&iacute;o de correspondencia:</strong> <jsp:expression>direccion</jsp:expression><br/>
	<strong>N&uacute;mero de contrato de CABLEVISI&Oacute;N&reg;:</strong> <jsp:expression>noContrato</jsp:expression><br/>
	<strong>Tel&eacute;fono de contacto:</strong> <jsp:expression>telefono</jsp:expression><br/>

</p>
