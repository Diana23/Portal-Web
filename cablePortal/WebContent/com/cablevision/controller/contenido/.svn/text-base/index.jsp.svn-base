<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/content" prefix="cm" %>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>
<cm:getNode id="pelicula" path="/WLP Repository/test/test3"/>


<link href="<%=request.getContextPath()%>/resources/css/explorer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/templates.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/explorer.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/prototype.js"></script>


<form action="<%=request.getContextPath()%>/com/cablevision/controller/contenido/getContenidoRenderizado.do" name="formContenido" id="formContenido">
	<table border="0" width="30%">
		<tr>
			<td>
				Estructura Id:
			</td>
			<td>
				<input type="text" name="estructuraId" id="estructuraId">
			</td>
		</tr>
		<tr>
			<td>
				Contenido Id:
			</td>
			<td>
				<input type="text" name="contenidoId" id="contenidoId">
			</td>
		</tr>
		<tr>
			<td>
				Template Id:
			</td>
			<td>
				<input type="text" name="templateId" id="templateId">
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<input type="submit" value="Enviar">
			</td>
		</tr>
	</table>
</form>