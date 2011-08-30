<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.apache.struts.util.MessageResources"%>

<netui:scriptContainer>
<script type="text/javascript" src="<%=request.getContextPath()%>/com/cablevision/controller/misFacturas/misfacturas.js"></script>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
<div align="center">
	<div class="margin-bot15 title-factura "><strong>Resumen de tu estado de cuenta</strong></div>
		
		<div class="error-msg">
			<c:if test="${!empty pageInput.errores}">
				${pageInput.errores}
			</c:if>
		</div>
		<netui:form action="begin" tagId="formFactura"></netui:form>
		<netui-data:declareBundle name="conf" bundlePath="configuracion"/>
		<input type="hidden" id="<netui:rewriteName name="anioServer" forTagId="true"/>" 
			value="${bundle.conf['facturacion.fecha.anio']}" />
		<input type="hidden" id="<netui:rewriteName name="mesServer" forTagId="true"/>" 
			value="${bundle.conf['facturacion.fecha.mes']}" />		        	
		<select id="mes" onchange="mostrarFactura(this);return false;"> 
			  <option value="0">Mes</option>
			  <option value="01">Enero</option>
			  <option value="02">Febrero</option>
			  <option value="03">Marzo</option>
			  <option value="04">Abril</option>
			  <option value="05">Mayo</option>
			  <option value="06">Junio</option>
			  <option value="07">Julio</option>
			  <option value="08">Agosto</option>
			  <option value="09">Septiembre</option>
			  <option value="10">Octubre</option>
			  <option value="11">Noviembre</option>
			  <option value="12">Diciembre</option>
		</select> 
		<select id="anio" onchange="mostrarFactura(this);return false;">
		   <option value="0">A&ntilde;o</option>										   
		   <% 
		   	 Date fecha = new Date();
		   	 Calendar calendario = Calendar.getInstance();
		   	 calendario.setTime(fecha);
		   	 int anioInicio = calendario.get(Calendar.YEAR)-1;
		   	 
		   	 for(int anio = calendario.get(Calendar.YEAR); anio>=anioInicio; anio--){
		   %>
		   		<option value="<%=String.valueOf(anio)%>"><%=anio%></option>
		   <% 
		   	 }
		   %>
		</select>
													
	<div class="clear"></div>
	<div align="left" class="span-16 padding-top25" id="secFactura" style="display:none;">						
		<div class="span-10">
			<div class="title-factura padding-top25"><strong>&Uacute;ltima Factura</strong></div>
		</div>

		<div class="span-6 last">
			<!-- 
				<a title="Imprimir Factura" class="icons3-3 hidden-text" href="#">Imprimir Factura</a>
			 -->
			<a title="Descargar Factura" id="descargarFactura" class="icons3-2 hidden-text" href="#" style="display:none;">Descargar Factura</a>
			<a title="Ver Factura" id="verFactura" class="icons3-1 hidden-text" href="#" style="display:none;">Ver Factura</a>							
		</div>
		<br/><br/>
		<div>
			<iframe frameborder="0" id="<netui:rewriteName name="facturaFrame" forTagId="true"/>" src="" width="100%" height="500" style="display:none"></iframe>
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
	<div id="<netui:rewriteName name="facturasPago" forTagId="true"/>" ></div>
</div> 
</netui:scriptContainer>

<script type="text/javascript">
	var contextPath = '<%=request.getContextPath()%>';
	var frameError;
	var mesGlobal;
	var anioGlobal;
	
	$('#verFactura').click(function(){
		facturaPopup();
	});
	
	$('#descargarFactura').click(function(){
		descargarFactura();
	});
	
	function mostrarFactura(scope){
		var mes = $('#mes').val();
		var anio = $('#anio').val();
		var anioServer = document.getElementById(lookupIdByTagId('anioServer',scope)).value;
		var mesServer = document.getElementById(lookupIdByTagId('mesServer',scope)).value;
		mesGlobal = mes;
		anioGlobal = anio;
		
		var d = new Date();
		var thisMonth = new Date(d.getFullYear(),d.getMonth(),1);
		var selectedMonth = new Date(anio,mes-1,1);
		
		if(mes!= "0" && anio != "0"){
			$.ajax({
				url: contextPath + '/com/cablevision/controller/misFacturas/muestraFacturasPago.do',
				data: 'anio='+anio+'&mes=' + mes,
			  	success: function(data) {
			    	$('#'+getJqId(lookupIdByTagId('facturasPago',scope))).html(data);
			  	}
			});
		
			document.getElementById(lookupIdByTagId('facturaFrame',scope)).src = contextPath+'/com/cablevision/controller/misFacturas/muestraFactura.do?anio='+anio+"&mes="+mes;
			frameError = document.getElementById(lookupIdByTagId('facturaFrame',scope));
			frameError.setAttribute("height", "500")
			document.getElementById(lookupIdByTagId('facturaFrame',scope)).style.display = '';
			
			
			if(anioServer == anio) {
				if(selectedMonth>=thisMonth){
					document.getElementById('secFactura').style.display = '';
					document.getElementById('verFactura').style.display = '';
					document.getElementById('descargarFactura').style.display = '';
					return;
				}
			}else if(anioServer<anio){
				document.getElementById('secFactura').style.display = '';
				document.getElementById('verFactura').style.display = '';
				document.getElementById('descargarFactura').style.display = '';
				return;
			}
		}
		document.getElementById('secFactura').style.display = 'none';
		document.getElementById('verFactura').style.display = 'none';
		document.getElementById('descargarFactura').style.display = 'none';
	}
	
	function facturaPopup() {
		var mes = $('#mes').val();
		var anio = $('#anio').val();
		var url = contextPath + '/com/cablevision/controller/misFacturas/imprimeFactura.do?anio='+ anio + '&mes=' + mes;
		window.open(url,'Factura','width=700,height=411,menubar=no,scrollbars=yes,toolbar=no,location=yes,directories=yes,resizable=yes,top=0,left=0');
	}
	
	function descargarFactura() {
		var mes = $('#mes').val();
		var anio = $('#anio').val();
		var url = contextPath + '/com/cablevision/controller/misFacturas/descargaFactura.do?anio='+ anio + '&mes=' + mes;
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
</script>
