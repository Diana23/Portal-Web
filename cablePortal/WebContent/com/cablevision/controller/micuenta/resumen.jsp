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
</jsp:scriptlet>
<!-- 
<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
<netui:form action="refrescarResumen" tagId="resumenForm">


<div class="span-10" id="contenedorResumen">
	
	<div class="box-sel-pagos padd-fix-1"><strong>Saldo de la &uacute;ltima factura:</strong><span class="span-3 last color-orange precio-mispagos">$<jsp:expression>saldo</jsp:expression></span></div>
	<div class="box-sel-pagos padd-fix-2">Fecha de Facturaci&oacute;n: <jsp:expression>fechaFactura</jsp:expression></div>
	<div class="box-sel-pagos padd-fix-3">Fecha L&iacute;mite de Pago: <jsp:expression>fechaLimite</jsp:expression></div>
</div>
</netui:form>

<div class="span-6 last"> -->
	<!-- 
		<a title="Imprimir Factura" class="icons3-3 hidden-text" href="#">Imprimir Factura</a>
	 --><!-- 
	<a title="Descargar Factura" id="descargarFacturaActual" class="icons3-2 hidden-text" href="#">Descargar Factura</a>
	<a title="Ver Factura" id="verFacturaActual" class="icons3-1 hidden-text" href="#">Ver Factura</a>							
	<div style="display:none;">
		<netui:rewriteName  name="resumenForm" forTagId="true" resultId="resumenForm"/>
	</div>
	<netui:anchor action="refrescarResumen" styleClass="btn-small margin-top100 bs-2-lines fright">
	  Actualizar Saldo
 	</netui:anchor>
	
</div>

<div class="clear" ></div>
<br/>-->
<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	var frameError;
	var mesGlobal;
	var anioGlobal;
	
	$('#verFacturaActual').click(function(){
		facturaActualPopup();
	});
	
	$('#descargarFacturaActual').click(function(){
		descargarFacturaActual();
	});
	
	function facturaActualPopup() {
		var url = contextPath + '/com/cablevision/controller/misFacturas/imprimeFactura.do?anio=${pageInput.anio}&mes=${pageInput.mes}';
		window.open(url,'Factura','width=700,height=411,menubar=no,scrollbars=yes,toolbar=no,location=yes,directories=yes,resizable=yes,top=0,left=0');
	}
	
	function descargarFacturaActual() {
		var url = contextPath + '/com/cablevision/controller/misFacturas/descargaFactura.do?anio=${pageInput.anio}&mes=${pageInput.mes}';
		window.open(url, '_blank');
		
		/**
		$.ajax({
			url: "${pageContext.request.contextPath }/com/cablevision/controller/misFacturas/descargaFactura.do",
			data: ({
				anio:anio,
				mes:mes
			}),
			dataType: "html",
			type: "POST",
			success: function(datos){
			}
		});
		*/
	}

	$('#actualizarSaldo').click(function() {
		$.ajax({
			url: "${pageContext.request.contextPath }/com/cablevision/controller/micuenta/refrescarResumen.do",
			beforeSend: function(objeto){
				$('#contenedorResumen').html();
				$('#contenedorResumen').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
			},
			dataType: "html",
			success: function(datos){
				$('#contenedorResumen').html();
				$('#contenedorResumen').html(datos);
			}
		});
	});
</script>
