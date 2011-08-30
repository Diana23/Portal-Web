<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.cablevision.util.PageNewUrl"/>
<!-- 
<table width="622" height="150" cellspacing="0" cellpadding="0" border="0" class="margin-top40" id="tabla-importes">
	<tbody>
		<tr>
			<td width="155" valign="top" height="80" background="<%=request.getContextPath()%>/images/Cablevision_ServiciosEnLinea_misPagos_01.png" align="center" alt="" class="paddtop-in-table">
				<strong>No. de Pago</strong>
				<c:if test="${empty pageInput.pagosLista}">
					<br><br>
					Al momento no tienes ning&uacute;n pago realizado. 
				</c:if>
			</td>
			<td width="156" valign="top" height="80" background="<%=request.getContextPath()%>/images/Cablevision_ServiciosEnLinea_misPagos_02.png" align="center" alt="" class="paddtop-in-table">
				<strong>Fecha de Pago</strong>
			</td>
			<td width="155" valign="top" height="80" background="<%=request.getContextPath()%>/images/Cablevision_ServiciosEnLinea_misPagos_03.png" align="center" alt="" class="paddtop-in-table">
				<strong>Forma de Pago </strong>
			</td>
			<td width="156" valign="top" height="80" background="<%=request.getContextPath()%>/images/Cablevision_ServiciosEnLinea_misPagos_04.png" align="center" alt="" class="paddtop-in-table">
				<strong>Importe</strong>
			</td>
		</tr>
		<c:if test="${not empty pageInput.pagosLista}">
			<c:forEach var="pago" items="${pageInput.pagosLista}">
				<tr>
					<td width="155" valign="top" align="center"><c:out value="${pago.noPago}"/></td>
					<td width="156" valign="top" align="center"><netui:label value="${pago.fechaPago}"><netui:formatDate pattern="dd/MM/yyyy"/></netui:label></td>
					<td width="155" valign="top" align="center"><c:out value="${pago.formaPago}"/></td>
					<td width="156" valign="top" align="center"><netui:label value="${pago.importe}"></netui:label></td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td width="622" valign="top" height="70" background="<%=request.getContextPath()%>/images/Cablevision_ServiciosEnLinea_misPagos_05.png" align="left" alt="" colspan="4" class="paddtop-in-table rowspaned">
				<strong>&iquest;Deseas realizar un pago en l&iacute;nea?</strong>
				<br><br>
				Recuerda que es seguro.
			</td>
		</tr>
	</tbody>
</table>
<br/>
<jsp:scriptlet>
	GenericURL url = PageNewUrl.createPageURL(request, response, "servicios_enlinea_pagar");
	//url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	pageContext.setAttribute("url",url);
</jsp:scriptlet>
<a title="Pagar en Linea" class="btn-small margin-bot40 fright" href="${pageScope.url}">Pagar en Linea</a> -->