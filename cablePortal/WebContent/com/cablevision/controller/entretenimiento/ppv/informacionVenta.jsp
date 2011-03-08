<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<style>
table {
	font-family: Tahoma, arial, verdana, serif;
	font-size: 11px;
	color: #787878;
	float: left;
}

input,form {
	margin: 0 0 0 0;
	padding: 0;
}

.wr-botns {
	margin: 0 0 0 10px;
	float: left;
	display: block;
	width: 508px;
	height: 50px;
}

.color-orange {
	color: #eb6500 !important;
	font-size: 23px !important;
}

.grid-content {
	margin: 7px 0 7px 43px;
}

.tbl-horario {
	padding-bottom: 4px; *
	margin-bottom: 5px;
	padding-left: 0;
	float: left;
	display: block;
	margin: 0 0 0 44px;
	width: 520px;
}

.tbl-horario li {
	border-style: solid;
	float: left;
	border-width: 1px;
	border-color: #e5e5e5;
	display: block;
	margin: 0 4px 1px 0; /*padding:5px;*/
}

.tbl-horario .ui1 {
	width: 155px;
	height: 20px;
	padding: 5px 4px 0 0; *
	height: 26px; *
	width: 168px; *
	padding: 4px 4px 0 0;
	text-align: right;
}

.tbl-horario .ui2 {
	width: 336px;
	height: 20px;
	padding: 5px 0 0 4px; *
	width: 336px; *
	height: 26px; *
	padding: 5px 0 0 4px;
}

.tbl-horario .ui3 {
	height: 110px; *
	height: 150px;
}

.titular {
	margin: 0 0 15px 44px;
	float: left;
	display: block;
	width: 508px;
	height: 30px;
	background: transparent
		url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png)
		no-repeat -379px -1912px;
	border-bottom-style: solid;
	border-bottom-width: 1px;
	border-bottom-color: #e5e5e5;
	text-indent: -9999px;
}

.btn-tiny:link,.btn-tiny:visited {
	display: block;
	width: 83px;
	height: 24px; *
	height: 25px;
	background: transparent
		url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png)
		no-repeat -505px -2040px;
	border: 0px;
	float: right;
	color: #fff;
	font-size: 10px;
	font-family: "Myriad pro", Times new roman;
	text-transform: uppercase;
	font-style: normal;
	text-align: center;
	letter-spacing: 0px;
	line-height: 22px;
	text-decoration: none;
	font-weight: none;
	padding-top: 1px;
	margin: 5px 0 0 5px;
	letter-spacing: 1px;
}

.btn-tiny:hover,.btn-tiny:active {
	color: #fff;
	text-decoration: none;
	background: transparent
		url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png)
		no-repeat -505px -2074px;
}
</style>
<%
	PageURL ppv = PageURL.createPageURL(request, response, "entretenimiento_payperview");
	ppv.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	ppv.setTemplate("defaultDesktop");
	pageContext.setAttribute("ppvUrl",ppv.toString());
%>
<div id="closeTabDiv">
	<a href="${ppvUrl}"  title="cerrar">
		<img src="${pageContext.request.contextPath }/resources/images/close2.png" border="0" alt="X" style="float: right" />
	</a>
</div>
<br />
<br />
<h3 class="titular">Informaci&oacute;n de Contrataci&oacute;n</h3>

<c:set var="usuario" value="${requestScope.usuario}"/>

<form id="formInfo">
	<input type="hidden" value="${requestScope.ppv.fechaIni}"  name="fechaIni" id="fechaIni"/>
	<input type="hidden" value="${requestScope.ppv.fechaFin}" name="fechaFin" id="fechaFin"/>
	<input type="hidden" value="${requestScope.ppv.titulo}" name="titulo" id="titulo"/>
	<input type="hidden" value="${requestScope.ppv.precio}" name="precio" id="precio"/>
	<input type="hidden" value="${requestScope.ppv.canal}" name="canal" id="canal"/>
	<input type="hidden" value="${usuario.nombreContacto} ${usuario.apellidoPaterno} ${usuario.apellidoMaterno}" name="nombre" id="nombre"/>
	<input type="hidden" value="${usuario.cvNumberAccount}" name="cuenta" id="cuenta"/>
	<input type="hidden" value="${requestScope.ppv.idEvento}" name="idEvento" id="idEvento"/>
	<input type="hidden" value="${requestScope.idSolr}" name="idSolr" id="idSolr"/>
	
	
	<ul class="tbl-horario">
		<li class="ui1"><strong>Nombre completo</strong></li>
		<li class="ui2">${usuario.nombreContacto}</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>N&uacute;mero de cuenta</strong></li>
		<li class="ui2">${usuario.cvNumberAccount}</li>
	</ul>
	<ul class="tbl-horario">
		<div id="equipos">
			<li class="ui1 ui3"><strong>Equipo donde se mostrar&aacute<br />
				la pel&iacute;cula:</strong
			</li>
			<li class="ui2 ui3">
				<div id="msg" style="display:none;" class="error-msg"></div>
				<table>
					<c:forEach var="equipo" items="${requestScope.equipos}">
						<tr>
							<td width="110"><input type="radio" name="equipoSerie" id="equipoSerie" value="${equipo.serialNum}" class="radio" />${equipo.serialNum}</td>
							<td>ubicaci&oacute;n <input type="text" name="u${equipo.serialNum}" id="u${equipo.serialNum}" value="${equipo.cvAssetAlias}" disabled="true" /></td>
							<input type="hidden" value="${equipo.cvassetPortalLogin}" name="idEquipo" id="idEquipo"/>
							<input type="hidden" value="" name="ubicacion" id="ubicacion"/>
						</tr>
					</c:forEach>
				</table>
			</li>
		</div>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>Pel&iacute;cula seleccionada</strong></li>
		<li class="ui2">${requestScope.ppv.titulo}</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>Canal</strong></li>
		<li class="ui2">${requestScope.ppv.canal}</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>Horario</strong></li>
		<li class="ui2">
			<netui:label value="${requestScope.fechaini}"><netui:formatDate language="es" pattern="dd MMM yyyy hh:mm" country="MX"/></netui:label> 
			/ 
			<netui:label value="${requestScope.fechafin}"><netui:formatDate language="es" pattern="dd MMM yyyy hh:mm" country="MX"/></netui:label>
		</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>Costo</strong></li>
		<li class="ui2">$ <netui:label value="${requestScope.ppv.precio}"><netui:formatNumber pattern="###.00"/></netui:label></li>
	</ul>
</form>

<div class="wr-botns">
	<a class="btn-tiny" title="SIGUIENTE" href="#" id="nextLink" name="nextLink">SIGUIENTE</a>
 	<a class="btn-tiny" title="ATRAS" href="#" id="backLink" name="backLink">ATR&Aacute;S</a>
</div>

<script>
	$('#TB_ajaxContent').css('height','535px');
	$('#TB_ajaxContent').css('overflow-y','auto');
	$('#TB_ajaxContent').css('width','600px');
	$('#TB_window').css('width','630px');
	
	$('#equipos input.radio').click(function(){
		$('#equipos input:text').each(function(){
			$(this).attr('disabled',true);
		});
		
		var radio = $(this).val();
		$('#u'+radio).attr('disabled', false); 
	});
	
	
	$('#nextLink').click(function(){
		if(!$('#equipos input.radio').is(':checked')){
			$('#msg').html('Favor de seleccionar un equipo');
			$('#msg').css('display','block');
			return false;
		}
		
		var value = $('[name="equipoSerie"]:checked').val();
		var ubicacion = $('#u'+value).val();
		$('#ubicacion').val(ubicacion);
		
		$.ajax({
			url: "${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/showConfirmacion.do?modal=true&amp;height=430&amp;width=520",
			data: $('#formInfo').serialize(), 
			beforeSend: function(objeto){
				$('#detalleDiv').html();
				$('#detalleDiv').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
			},
			dataType: "html",
			success: function(datos){
				$('#detalleDiv').html(datos);
			},
			type: "POST"
		});
	});
	
	$('#backLink').click(function(){
		var id = $('#idIni').val();
		var tipo = $('#tipoIni').val();
		$.ajax({
			url: "${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/mostrarDetalle.do?modal=true&amp;height=430&amp;width=520&id="+id+"&tipo="+tipo,
			beforeSend: function(objeto){
				$('#detalleDiv').html();
				$('#detalleDiv').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
			},
			dataType: "html",
			success: function(datos){
				$('#detalleDiv').html(datos);
			},
			type: "POST"
		});
	});
</script>