<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
	String nombreCompleto = nombreContacto + " " + account.getApellidoPaterno();
	String saldo = account.getCvLastBalance() != null ? account.getCvLastBalance() : "";
	String fechaFactura = account.getFechaFactura() != null ? account.getFechaFactura() : "";
	String fechaLimite = account.getFechaLimite() != null ? account.getFechaLimite() : "";
</jsp:scriptlet>

<div class="box-sel-pagos padd-fix-1"><strong>Saldo de la &uacute;ltima factura:</strong><span class="span-3 last color-orange precio-mispagos">$<jsp:expression>saldo</jsp:expression></span></div>
<div class="box-sel-pagos padd-fix-2">Fecha de Facturaci&oacute;n: <jsp:expression>fechaFactura</jsp:expression></div>
<div class="box-sel-pagos padd-fix-3">Fecha L&iacute;mite de Pago: <jsp:expression>fechaLimite</jsp:expression></div>
