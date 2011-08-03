<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>

<div class="span-24" id="resultadosbusqueda">
	<netui:form action="begin">
		<div align="center" class=" margin-bot40" >
			<c:choose>
				<c:when test="${pageInput.total==0}">
					Su búsqueda no regreso ningún resultado. Intenta cambiando los criterios de tu búsqueda.
				</c:when>
				<c:otherwise>
					
					Resultado ${pageInput.registroInicial + 1} - ${pageInput.registroInicial+pageInput.listSize } de ${pageInput.total }<br /><br />
			
					<c:choose>
						<c:when test="${pageInput.paginaInicio!=1}">
							<netui:anchor action="begin" styleClass="color-orange" >
								Anterior
								<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
								<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
								<netui:parameter name="paginaInicio" value="${actionForm.paginaInicio-1}"/>
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							</netui:anchor>
						</c:when>
					</c:choose>
					
					
					<c:forEach begin="0" end="${pageInput.numeroPaginas - 1}" varStatus="rowCounter">
						<netui:anchor action="begin" styleClass="color-orange" >
							<c:choose>
								<c:when test="${ rowCounter.index + 1== pageInput.paginaInicio}">
									<strong>${ rowCounter.index + 1}</strong>
								</c:when>
								<c:otherwise>
									${ rowCounter.index + 1}
								</c:otherwise>
							</c:choose>
							<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
							<netui:parameter name="paginaInicio" value="${rowCounter.index + 1}"/>
							<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
							<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
						</netui:anchor>
					</c:forEach>
					
					<c:choose>
						<c:when test="${!pageInput.esPaginaFinal}">
							<netui:anchor action="begin" styleClass="color-orange" >
								Siguiente
								<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
								<netui:parameter name="paginaInicio" value="${actionForm.paginaInicio+1}"/>
								<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							</netui:anchor>
						</c:when>
					</c:choose>
					
					
					<br />
					
		
		
					<ul class="resbus-cont">
						<c:forEach items="${pageInput.resultadosBusqueda.results}" var="resultado">
							<li class="margin-bot40">
								<a class="color-orange linkBusqueda" href="${resultado.url }" target="_blank	">
										<c:choose >
											<c:when test="${pageInput.resultadosBusqueda.highlighting[resultado.id].title != null}">
												${pageInput.resultadosBusqueda.highlighting[resultado.id].title[0] }
											</c:when>
											<c:otherwise>
												${resultado.title }
											</c:otherwise>
										</c:choose>
								</a>
								<p>
									<c:choose >
										<c:when test="${pageInput.resultadosBusqueda.highlighting[resultado.id].content != null}">
											${pageInput.resultadosBusqueda.highlighting[resultado.id].content[0] }
										</c:when>
										<c:otherwise>
											${resultado.content }
										</c:otherwise>
									</c:choose> 
									<a title="Leer Más" target="_blank" class="color-orange" href="${resultado.url }">Leer más</a>
								</p>
							</li>
						</c:forEach>
					</ul>
					
					<br/><br/><br/>
					<div class="clear"></div>
					
					Resultado ${pageInput.registroInicial + 1} - ${pageInput.registroInicial+pageInput.listSize } de ${pageInput.total }<br /><br />
					
					<c:choose>
						<c:when test="${pageInput.paginaInicio!=1}">
							<netui:anchor action="begin" styleClass="color-orange" >
								Anterior
								<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
								<netui:parameter name="paginaInicio" value="${actionForm.paginaInicio-1}"/>
								<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							</netui:anchor>
						</c:when>
					</c:choose>
					
					<c:forEach begin="0" end="${pageInput.numeroPaginas - 1}" varStatus="rowCounter">
						<netui:anchor action="begin" styleClass="color-orange" >
							<c:choose>
								<c:when test="${ rowCounter.index + 1== pageInput.paginaInicio}">
									<strong>${ rowCounter.index + 1}</strong>
								</c:when>
								<c:otherwise>
									${ rowCounter.index + 1}
								</c:otherwise>
							</c:choose>
							<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
							<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
							<netui:parameter name="paginaInicio" value="${rowCounter.index + 1}"/>
						</netui:anchor>
					</c:forEach>
					
					<c:choose>
						<c:when test="${!pageInput.esPaginaFinal}">
							<netui:anchor action="begin" styleClass="color-orange" >
								Siguiente
								<netui:parameter name="busqueda" value="${actionForm.busqueda}"/>
								<netui:parameter name="paginaInicio" value="${actionForm.paginaInicio+1}"/>
								<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							</netui:anchor>
						</c:when>
					</c:choose>
					
					
				</c:otherwise>
			</c:choose>
			
			
			
		</div>
	</netui:form>
</div>