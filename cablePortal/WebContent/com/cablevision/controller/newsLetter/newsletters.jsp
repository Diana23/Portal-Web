<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer> 
	<a name="anchorTarget" id="anchorTarget"></a>	
	<c:if test="${pageInput.canAdd}">
		<a target="_blank" class="fright" 
			href="<%=request.getContextPath()%>/com/cablevision/controller/contenido/getContenidoEditable.do?estructuraId=CV002321&folderId=287" style="text-decoration: none;">
			<img border="0" src="${pageContext.request.contextPath}/resources/images/new.png" alt="Editar"></img>
		</a>
	</c:if>
	<ul id="cotiz-content" class="cotiz-content margin-top45">
		<c:forEach items="${pageInput.noticias}" var="item" varStatus="rowCounter">
			<li class="wrap-newsitem">	
				<c:set var="contenidoId" value="${item.id}" scope="request"/>
				<c:set var="estructuraId" value="CV002321" scope="request"/>
				<c:set var="templateId" value="CV002322" scope="request"/>
				<c:set var="borrar" value="true" scope="request"/>
				<c:set value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>' var="_pageLabel" scope="request" />
				
				<jsp:include page="/util/contenido/renderizarContenido.jsp?contenidoId=${item.id}&estructuraId=CV002321&templateId=CV002322&borrar=true" flush="true"/>
			</li>
		</c:forEach>
	</ul>
	
	<c:choose>
		<c:when test="${empty pageInput.noticias}">
			<br/><strong>Su b&uacute;squeda no produjo resultado intente de nuevo.</strong>
		</c:when>
		<c:otherwise>
			<a class="color-orange fright" title="subir" href="#anchorTarget">^ Subir</a>
		</c:otherwise>
	</c:choose>
	<br><br>
</netui:scriptContainer>