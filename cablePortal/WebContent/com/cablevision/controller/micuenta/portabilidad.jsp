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

<c:set var="contenidoId" value="CV004541" scope="request"/>
<c:set var="estructuraId" value="CV001950" scope="request"/>
<c:set var="templateId" value="CV001951" scope="request"/>
<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>


<netui:label value="Ingresa tu número telefónico de 10 dígitos para consultar el estatus de tu portacion:"/><br/>
<input type="text" id="telefono" size="10" maxlength="10" onkeypress="return soloNumeros(event);"/>
<div id="verPortabilidad"></div>
<a title="Consultar" id="consultar" class="btn-small margin-bot15 fright" href="#" onclick="verPorta(this);return false;">Consultar</a>

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