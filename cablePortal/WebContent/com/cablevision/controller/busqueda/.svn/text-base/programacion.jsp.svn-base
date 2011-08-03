<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>

<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.cablevision.util.PageNewUrl"/>

<%
GenericURL guia = PageNewUrl.createPageURL(request, response, "entretenimiento_guia");
//guia.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
//guia.setEncodeSession(false)
pageContext.setAttribute("guiaUrl",guia.toString());

%>

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
								<netui:parameter name="paginaInicio" value="${actionForm.paginaInicio-1}"/>
								<netui:parameter name="tipoBusqueda" value="${actionForm.tipoBusqueda}"/>
								<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							</netui:anchor>
						</c:when>
					</c:choose>
					
					
					<c:forEach begin="0" end="${pageInput.numeroPaginas-1}" varStatus="rowCounter">
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
					
					
					<br/><br/><br/>
					<div class="clear"></div>
					
		
		
					<ul class="resbus-cont">
						<c:forEach items="${pageInput.resultadosBusqueda.results}" var="resultado">
							<li class="margin-bot40">
								<div class="color-orange linkBusqueda" style="float: left;" >
										<c:choose >
											<c:when test="${pageInput.resultadosBusqueda.highlighting[resultado.id].titulo != null}">
												${pageInput.resultadosBusqueda.highlighting[resultado.id].titulo[0] }
											</c:when>
											<c:otherwise>
												${resultado.titulo }
											</c:otherwise>
										</c:choose>
								</div>
								<div style="width: 100%;" align="right">
									<b>Hora Inicio:</b> 
									<netui:span value="${resultado.fechaini}">
										<netui:formatDate pattern="dd/MM/yyyy hh:mm"  />
									</netui:span>
									<b>Canal:</b> ${resultado.canal}
								</div>
								<p>
									<c:choose >
										<c:when test="${pageInput.resultadosBusqueda.highlighting[resultado.id].descripcion != null}">
											${pageInput.resultadosBusqueda.highlighting[resultado.id].descripcion[0] }
										</c:when>
										<c:otherwise>
											${resultado.descripcion }
										</c:otherwise>
									</c:choose> 
								</p>
								<a title="Leer Más" target="_blank" class="color-orange" href="${guiaUrl }">Leer más</a>
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
					
					<c:forEach begin="0" end="${pageInput.numeroPaginas-1}" varStatus="rowCounter">
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
					
					
				</c:otherwise>
			</c:choose>
			
			
			
		</div>
	</netui:form>
</div>