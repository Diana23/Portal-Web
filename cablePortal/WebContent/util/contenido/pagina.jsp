<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils"/>

<%
	if(ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel")!=null){
		PageURL urlReturn = PageURL.createPageURL(request, response, ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel"));
		urlReturn.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		pageContext.setAttribute("urlReturn",urlReturn);
		
		PageURL urlUltima = PageURL.createPageURL(request, response, ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel"));
		urlUltima.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		urlUltima.addParameter("ultima", "true");
		pageContext.setAttribute("urlUltima",urlUltima);
		
		PageURL urlDelete = PageURL.createPageURL(request, response, ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel"));
		urlDelete.setTemplate("defaultDesktop");
		urlDelete.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		pageContext.setAttribute("urlDelete",urlDelete);
	}
%>

<div class="contentEdit" style="display: none;">
	<c:if test="${requestScope.canEdit}">
		<a target="_blank" 
			href="<%=request.getContextPath()%>/com/cablevision/controller/contenido/getContenidoEditable.do?contenidoId=${requestScope.contenidoId}&templateId=${requestScope.templateId}&estructuraId=${requestScope.estructuraId}" style="text-decoration: none;">
			<img border="0" src="${pageContext.request.contextPath}/resources/images/edit.png" alt="Editar"></img>
		</a>
	</c:if>	
	<c:if test="${!empty requestScope.ultima}">
		<c:choose>
			<c:when test="${requestScope.ultima}">
				<a href="${urlReturn}" style="text-decoration: none;">
					<img border="0" src="${pageContext.request.contextPath}/resources/images/return.png" alt="return"></img>
				</a>
			</c:when>
			<c:otherwise>
				<a href="${urlUltima}" style="text-decoration: none;">
					<img border="0" src="${pageContext.request.contextPath}/resources/images/preview1.png" alt="preview"></img>
				</a>
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${requestScope.inWorkFlow}">
		<a target="_blank" 
			href="<%=request.getContextPath()%>/com/cablevision/controller/contenido/showPaginaWorkFlow.do?contenidoId=${requestScope.contenidoId}&templateId=${requestScope.templateId}&estructuraId=${requestScope.estructuraId}&ultima=true" style="text-decoration: none;">
			<img border="0" src="${pageContext.request.contextPath}/resources/images/workflow.gif" alt="workflow"></img>
		</a>
	</c:if>
	<c:if test="${requestScope.canDelete}">
		<script src="<%=request.getContextPath()%>/com/cablevision/controller/contenido/contenido.js"></script>
		<a href="javascript:deleteContent('${requestScope.contenidoId}','${urlDelete}');" style="text-decoration: none;">
			<img border="0" src="${pageContext.request.contextPath}/resources/images/deletecontent.png" alt="Borrar"></img>
		</a>
	</c:if>
</div>
	${requestScope.strHtml}
	