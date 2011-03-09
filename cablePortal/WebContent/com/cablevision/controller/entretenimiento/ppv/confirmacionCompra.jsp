<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<style>
body {
	font-family: Tahoma, arial, verdana, serif;
	font-size: 11px;
	color: #787878;
	background: #fff;
	margin-top: 12px; *
	margin-top: ;
}

h3 {
	font-size: 20px;
	font-weight: none;
	margin: 0 0 0 0;
}

p {
	margin: 0 0 0 0;
}

p.date {
	margin: 0 0 0 0;
	font-family: Tahoma, arial, verdana, serif;
	font-size: 10px;
	color: #787878;
}

table {
	font-family: Tahoma, arial, verdana, serif;
	font-size: 11px;
	color: #787878;
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


#wrap-img {
	background: url(img/back-logo-lgbox.png);
	float: Left;
	width: 128px;
	height: 112px;
	margin: 0 5px 0 0;
}

.grid-content {
	margin: 7px 0 7px 43px;
}

.boxd {
	display: block;
	width: 600px;
	height: 32px;
	margin: 5px 0 25px 0;
}

.blacker {
	color: #2a2a2a;
}

.titular {
	margin: 0 0 15px 44px;
	float: left;
	display: block;
	width: 508px;
	height: 30px;
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -358px -1947px;
	border-bottom-style: solid;
	border-bottom-width: 1px;
	border-bottom-color: #e5e5e5;
	text-indent: -9999px;
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
	height: 30px; *
	height: 35px;
}

.btn-tiny:link,.btn-tiny:visited {
	display: block;
	width: 83px;
	height: 24px; *
	height: 25px;
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -505px -2040px;
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
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -505px -2074px;
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
<h3 class="titular">CONFIRMACI&Oacute;N DE COMPRA</h3>

<form id="contrataForm">

	<input type="hidden" value="${requestScope.ppv.fechaIni}"  name="fechaIni" id="fechaIni"/>
	<input type="hidden" value="${requestScope.ppv.fechaFin}" name="fechaFin" id="fechaFin"/>
	<input type="hidden" value="${requestScope.ppv.titulo}" name="titulo" id="titulo"/>
	<input type="hidden" value="${requestScope.ppv.precio}" name="precio" id="precio"/>
	<input type="hidden" value="${requestScope.ppv.idEquipo}" name="idEquipo" id="idEquipo"/>
	<input type="hidden" value="${requestScope.ppv.idEvento}" name="idEvento" id="idEvento"/>
	<input type="hidden" value="${requestScope.ppv.ubicacion}" name="ubicacion" id="ubicacion"/>
	<input type="hidden" value="${requestScope.ppv.equipoSerie}" name="equipoSerie" id="equipoSerie"/>
	<input type="hidden" value="${requestScope.ppv.canal}" name="canal" id="canal"/>
	<input type="hidden" value="${requestScope.idSolr}" name="idSolr" id="idSolr"/>
	<input type="hidden" value="${requestScope.nombre}" name="nombre" id="nombre"/>
	<input type="hidden" value="${requestScope.cuenta}" name="cuenta" id="cuenta"/>

	<ul class="tbl-horario">
		<li class="ui1"><strong>Nombre completo</strong></li>
		<li class="ui2">${requestScope.nombre} ${requestScope.apellidoPaterno} ${requestScope.apellidoMaterno}</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1"><strong>N&uacute;mero de cuenta</strong></li>
		<li class="ui2">${requestScope.cuenta}</li>
	</ul>
	<ul class="tbl-horario">
		<li class="ui1 ui3"><strong>Equipo donde se mostrar&aacute<br />
		la pel&iacute;cula:</strong></li>
		<li class="ui2 ui3">${requestScope.ppv.equipoSerie} / ${requestScope.ppv.ubicacion}</li>
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
	<a class="btn-tiny" title="CONTRATA" href="#" id="nextLink" name="nextLink">CONTRATA</a>
	<a class="btn-tiny" title="ATRAS" href="#" id="backLink" name="backLink">ATRAS</a>
</div>

<script>
	$('#nextLink').click(function(){
		$.ajax({
			url: "${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/contratar.do?modal=true&amp;height=430&amp;width=520",
			data: $('#contrataForm').serialize(), 
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
			url: "${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/verificarContratar.do?modal=true&amp;height=430&amp;width=520&id="+id+"&tipo="+tipo,
			data: $('#contrataForm').serialize(), 
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