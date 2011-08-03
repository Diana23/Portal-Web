<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@page import="com.cablevision.util.Constantes"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/screenfix.css" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/print.css" type="text/css" media="print"/>	
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/layout.css" type="text/css" media="screen, projection"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/ui.css" type="text/css" media="screen, projection"/>

<script src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/framework/skins/cablevision/js/funciones.js" type="text/javascript"></script>

	<div class="imprimir-in-thbox">
		<br/><br/>
		<div>
			<table id="table-canales" border="0" cellpadding="0" cellspacing="0">
							<!-- CABECERA de la tabla - Nombres de paquetes -->
							  <tr>
								<td class="erasetd" width="28" height="45" align="center" valign="middle" class="bord-boted" bgcolor="#f28600">
									<a title="Nº" class="style5 mayusculas"><span class="color-white">N&ordm;</span></a>
								</td>
								<td width="75" height="45" align="center" valign="middle" class="bord-boted" >
									<a class="wtd" title="Canales" class="style5 mayusculas">Canales</a>
								</td>
								<c:set var="color" value="#fce5c9"/>
								<c:forEach var="pack" items="${pageInput.paquetes}">
									<td width="75" height="45" align="center" valign="middle" class="bord-boted" bgcolor="${ color }">
										<a class="wtd"  title="${pack.name}" class="style5 mayusculas">${pack.name}</a>
									</td>
									<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  	</c:choose>
									</c:forEach>
								<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  </c:choose>
								<td width="14" height="45" align="center" valign="middle" class="bord-boted" bgcolor="${ color }">&nbsp;</td>
								<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  </c:choose>
							  </tr>
							  
							  <!-- CANALES encontrados -->
							  <tr>
								<td colspan="${fn:length(pageInput.paquetes)+2 }" rowspan="20" align="left" valign="top">
									<div class="wrap-imprimir-canales-table">
									  <table width="100%" border="0" cellpadding="0" cellspacing="0">
									  	<c:set var="channelCount" value="1"/>
									  	<c:forEach var="canalKV" items="${pageInput.canales}">
									  		<tr>
											  <td style="width:28px;*width:26px" height="30" align="center" valign="middle" class="bord-boted" bgcolor="#f28600"><span class="color-white">${ canalKV.key.idchannel }</span></td>
											  <td width="83" height="30" align="center" valign="middle" class="bord-boted" >
											  	<a title="<strong>${ canalKV.key.name }</strong><br /> ${ canalKV.key.description }" class="bk-logos-canales" id="canal${channelCount}" rel="${channelCount}">
											  		<img src="${ canalKV.key.logo }" width="25" height="25" alt="${ canalKV.key.name }"/>
											  	</a>
											  </td>
											  <c:set var="channelCount" value="${channelCount + 1}"/>
											  <c:set var="size" value="${fn:length(pageInput.paquetes)}"/>
											  <c:set var="countPack" value="1"/>
											  <c:set var="width" value="83"/>
											  <c:forEach var="pack" items="${ pageInput.paquetes }">
											  	<c:if test="${countPack == size}">
											  		<c:set var="width" value="77"/>
											  	</c:if>
											  	<td width="${ width }" height="30" align="center" valign="middle" class="bord-boted">
											  	<c:set var="contains" value="false"/>
											  	<c:forEach var="packEl" items="${canalKV.value}">
											  		<c:if test="${packEl == pack.idpack}"><c:set var="contains" value="true"/></c:if>
											  	</c:forEach>
											  	<c:choose>
											  		<c:when test="${contains}">
											  			<img src="${pageContext.request.contextPath }/images/visto.png" alt="visto" />
											  		</c:when>
											  		<c:otherwise>
											  			<img src="${pageContext.request.contextPath }/images/cruz.png" alt="cruz" />
											  		</c:otherwise>
											  	</c:choose>
											  	</td>
											  	<c:set var="countPack" value="${countPack + 1}"/>
											  </c:forEach>
											  
											</tr>
									  	</c:forEach>
										
									  </table>
									</div>    
								</td>
							</tr>
						</table>

			
			<div class="span-imprimir-canales">
				<a id="imprimir" href="#" id="ingresar" title="Ingresar" class="btn-small fleft">Imprimir</a></td>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	
	<script>
		$(document).ready(function(){
			window.print();
			
			$('#imprimir').click(function() {
	             window.print();
	         });
		});
	</script>
	
