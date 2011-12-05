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


<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>

<div class="content-pago">
 <h3 class="color-orange">Tu n&uacute;mero de tel&eacute;fono no cambia, tu estado de cuenta s&iacute;.</h3>
 <br /><br />
 <strong>Conoce en qu&eacute; etapa est&aacute; la portabilidad de tu n&uacute;mero tel&eacute;fonico.</strong>
 <br /><br />

	<netui:label value="Ingresa tu n&uacute;mero telef&eacute;nico de 10 digitos:"/>
	<input type="text" id="telefono" maxlength="10" onkeypress="return soloNumeros(event);" class="selectwhitoutform big"/>
	<div id="verPortabilidad"></div>
	<a title="Consultar" id="consultar" class="btn-small fix-portabi" href="#" onclick="verPorta(this);return false;">Consultar</a>
	<br /><br />
</div>

<c:set var="contenidoId" value="CV004541" scope="request"/>
<c:set var="estructuraId" value="CV001950" scope="request"/>
<c:set var="templateId" value="CV001951" scope="request"/>
<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>

<script type="text/javascript">

	function verPorta(scope){
	    var telefono = $('#'+getJqId('telefono')).val();
	   
	    if(telefono != ''){
	   		$.ajax({
				url: "${pageContext.request.contextPath}/com/cablevision/controller/micuenta/obtenerEstatusPortabilidad.do",
				data: ({telefono:telefono}),
				success: function(datos){
					$('#verPortabilidad').html(datos);
				}
			});
	    }
	    else{
	    	$('#verPortabilidad').html("<label class='disp-error-small'>Proporcione un n\u00famero de tel\u00e9fono.</label>");
	    }
	}
	
	function getJqId(myid){ 
	 return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
	}
</script>