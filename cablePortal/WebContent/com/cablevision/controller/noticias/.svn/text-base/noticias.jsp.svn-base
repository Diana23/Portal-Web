<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>
<br/><br/><br/><br/>
	<h2 style="text-align: center; font-size: 35px;" class="color-orange">
	Próximamente</h2>
	<%-- 
	<c:if test="${pageInput.canAdd}">
		<a target="_blank" class="fright" 
			href="<%=request.getContextPath()%>/com/cablevision/controller/contenido/getContenidoEditable.do?estructuraId=CV002560&folderId=301" style="text-decoration: none;">
			<img border="0" src="${pageContext.request.contextPath}/resources/images/new.png" alt="Editar"></img>
		</a>
	</c:if>
	<ul id="cotiz-content" class="cotiz-content">
		<c:forEach items="${pageInput.noticias}" var="noticia" varStatus="rowCounter">
			<li class="wrap-paq-cot_NP">
				<c:set var="contenidoId" value="${noticia.id}" scope="request"/>
					<c:set var="estructuraId" value="CV002560" scope="request"/>
					<c:set var="templateId" value="CV002559" scope="request"/>
					<c:set var="borrar" value="true" scope="request"/>
					<jsp:include page="/util/contenido/renderizarContenido.jsp?contenidoId=${noticia.id}&estructuraId=CV002560&templateId=CV002559&borrar=true" flush="true"/>	
			</li>
		</c:forEach>
	</ul>
	<c:set var="minRes" value="${pageInput.currentPage*pageInput.minRows-pageInput.minRows+1}" scope="request"/>
	
	<c:choose>
		<c:when test="${!empty pageInput.noticias}">
			<!-- div de paginacion -->
			<div class="faqpager" style="text-align: center;">
				<div class="faqpager-results">
					Resultado ${minRes} - ${pageInput.maxRes} de ${pageInput.numPreguntas}
				</div>
				<div class="faqpager-pages">
					<div class="faqpager-pages">
						<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-prev-page" title="Anterior">Anterior</a>
						<c:forEach items="${requestScope.paginas}" var="pagina">
							<c:choose> 
										<c:when test="${pagina==pageInput.currentPage}">
									<strong class="color-orange">${pageInput.mapaPaginas[pagina]}</strong>
										</c:when>
										<c:otherwise>
		 								<a href="javascript: ;" onclick="goToFAQPage(this)" title="Ir a p&aacute;gina ${pageInput.mapaPaginas[pagina]}" class="color-orange">${pageInput.mapaPaginas[pagina]}</a> 
										</c:otherwise>
							</c:choose>
						</c:forEach>
						<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-next-page" title="Siguiente">Siguiente</a>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<br/><strong>No hay Noticias a mostrar.</strong>
		</c:otherwise>
	</c:choose>
	<input type="hidden" id="hdnMinRows" value="${pageInput.minRows}"/>
	<input type="hidden" id="hdnCurrentPage" value="${pageInput.currentPage}"/>
	<input type="hidden" id="hdnNumPages" value="${pageInput.numPages}"/>
	<input type="hidden" id="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
	--%>
</netui:scriptContainer>