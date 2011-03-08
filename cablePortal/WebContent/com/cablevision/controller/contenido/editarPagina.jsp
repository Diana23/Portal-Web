<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="<%=request.getContextPath()%>/resources/css/explorer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/templates.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/explorer.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/prototype.js"></script>
<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath()%>/com/cablevision/controller/contenido/contenido.js"></script>

<c:set var="contentId">${pageInput.contenidoId}</c:set>

<c:choose>
	<c:when test="${empty pageInput.popup or !pageInput.popup eq true}">
		<%
			com.bea.portlet.PageURL preview = com.bea.portlet.PageURL.createPageURL(request, response, "cablevision_portal_preview");
			preview.setTemplate("defaultDesktop");
			preview.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			preview.setEncodeSession(false);
			pageContext.setAttribute("preview",preview.toString());
			
		%>
	</c:when>
	<c:otherwise>
		<% pageContext.setAttribute("preview",""); %>
	</c:otherwise>
</c:choose>


<div id="toolBarXml" style='display:none' class='toolbarElementos'></div>
    <table cellpadding="0" cellspacing="0" width="100%" height="100%">
       		<tr> 
            <td align='center' valign="top">
            	<form action="${preview}" style="height:100%; margin-bottom: 0" id="formaSave" name="formaSave" target="_blank" method="post">
                    <input type="hidden" name="__Elemento__" id="__Elemento__">
                    <input type="hidden" name="__Comando__" id="__Comando__">
                    <input type="hidden" id="__idText__" name="__idText__">
                    <input type="hidden" id="contenidoId" name="contenidoId" value="${pageInput.contenidoId}">
                    <input type="hidden" id="estructuraId" name="estructuraId" value="${pageInput.estructuraId}">
                    <input type="hidden" id="templateId" name="templateId" value="${pageInput.templateId}">
                    <input type="hidden" id="folderId" name="folderId" value="${pageInput.folderId}">
                    <input type="hidden" id="_pageLabel" name="_pageLabel" value="<%=org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel")%>"/>
                    
                    <table bgcolor="#f8f8f8" width="100%" height="100%">
                        <tr>
                            <td>
                                <div style="height:100%; width:100%;" id="htmlOfXml">
                                    ${pageInput.strHtml}
                                    <textarea rows="0" name="__NodeContent__" style="display:none">${pageInput.sw}</textarea>
                                    <input type="hidden" id="editores" value="${pageInput.strLista}">
                                </div>
                            </td>
                        </tr>
                    </table>
                <form>
            </td>
        </tr>
        <tr>
        	<td>
        		<c:choose>
        			<c:when test="${empty pageInput.popup or !pageInput.popup eq true}">
        				<a href="#" onclick="saveXmlData(false);return false;">Guardar</a>
        				<c:if test="${!empty pageInput.templateId}">
        					<a href="#" onclick="previewXmlData();return false;">Preview</a>
        				</c:if>
        			</c:when>
        			<c:otherwise>
        				<a href="#" onclick="saveXmlData(true);">Guardar</a>
        			</c:otherwise>
        		</c:choose>
        	</td>
        </tr>
    </table>
    <iframe id='_hideSelect' class="hideSelect"></iframe>
    <div id='_overlay' class="overlay"></div>
    <div id='_load' class="load"><img src="<%=request.getContextPath()%>/resources/images/saving.gif" ></img></div>