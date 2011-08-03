<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>
<script src="<%=request.getContextPath()%>/com/cablevision/controller/contenido/contenido.js"></script>

<netui:scriptContainer>
	<netui:form action="nextContenidoPopup" tagId="formContenidoNuevo" onSubmit="return validaDatosNuevoContenido(this);">
	<table align="center" style="width:50%;" cellpadding="0" cellspacing="8">
		<input type="hidden" id="__idText__" name="__idText__" />
		<input type="hidden" id="_pageLabel" name="_pageLabel" value="<%=org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel")%>"/>
	
		<tr>
			<td align="right">Selecciona una Estructura: </td>
			<td>
				<netui:select dataSource="actionForm.estructuraId" style="width:210;" tagId="estructuraId" >
					<c:forEach items="${pageInput.estructuras}" var="estructura">
						<netui:selectOption value="${estructura.id}"><c:out value="${estructura.name}"/></netui:selectOption>
					</c:forEach>
				</netui:select>	
			</td>
		</tr>
		<tr>
			<td align="right">Selecciona la carpeta en donde se guardara el contenido:</td>
			<td>
	           <table cellpadding="0" cellspacing="0" border="0" style="width:50%;margin-bottom: 0px;">
	               <tr>
	                   <td width='50%'>
	                   		<netui:hidden dataSource="actionForm.folderId" tagId="folderId" dataInput='<%=request.getParameter("folderId")%>'/>
	                   		<input type="text" size="30" id="idFolder" name="idFolder" value="<%=request.getParameter("folderPath")%>" disabled="disabled">
	                   </td>
	                   <td>
	                   		<div style='width: 23px; float: left;'>
		                    	<a href='javascript:void(0)' class='button' title='title' onclick="guardarScope(this);openExplorer('folderId','folder','')">
		                        	<span unselectable='on'>
		                            	<img class='button' border="0" src="<%=request.getContextPath()%>/resources/images/folder.gif"></img>
		                        	</span>
		                    	</a>
		                	</div>
	                   </td>
	               </tr>
	           </table>
		</tr>
		<tr>
			<td align="right">Nombre del contenido: </td>
			<td>
				<netui:textBox dataSource="actionForm.nombre" size="30" tagId="nombre" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="right">
				<netui:button value="Aceptar" />
			</td>
		</tr>
	</table>
	</netui:form>
</netui:scriptContainer>