<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<style>
h3 {
	font-size: 20px;
	font-weight: none;
	margin: 0 0 0 0;
}

p {
	margin: 0 0 0 0;
}

.titular {
	display: block;
	width: 508px;
	height: 30px;
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -313px -1836px;
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
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -505px -2040px;
	border: 0px;
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
	margin: 0 auto;
	letter-spacing: 1px;
}

.btn-tiny:hover,.btn-tiny:active {
	color: #fff;
	text-decoration: none;
	background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -505px -2074px;
}

.pr {
	font-family: Tahoma, arial, verdana, serif;
	font-size: 11px;
	color: #f00;
	font-weight: bold;
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
<div align="center">
<h3 class="titular">CONFIRMACI&Oacute;N</h3>
<br />
<br />
<br />
	<p class="pr">${pageInput.msg}</p>
<br />
</div>
	