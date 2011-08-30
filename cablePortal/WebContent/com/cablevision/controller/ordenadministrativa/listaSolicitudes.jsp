<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>

<netui:scriptContainer><!-- 
		<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
		
		<div class="content-pago">
			<h3 class="side">Solicitud de Servicio</h3>
		
			<p class="bord-toped padding-top15">
				<strong>Error en factura: </strong><br>
				<strong>Solicitud de Servicio: </strong><br>
				<strong>Contrato: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/><br>
				<strong>Nombre de la Cuenta: </strong><netui:label value="${sessionScope._MI_CUENTA.cvNameAccount}"/><br>
			</p>
			
			<netui:form action="mostrarDetalle" tagId="fomaTest">
				<table width="635"  cellspacing="0" cellpadding="0">
					<tr class="fnd_tabla_inv_1">
						<td colspan="3"></td>
					</tr>
					<tr class="fnd_tabla_inv_3">
						<td width="120px">
							<strong>No. de Solicitud</strong>
						</td>
			             		<td width="120px">
			               			<strong>Creado el</strong>
			             		</td>
			             		<td width="395px">
			               			<strong>Descipci&oacute;n</strong>
			             		</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="fnd-tabla-pager-content" id="pager_content">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<c:if test="${!empty pageInput.listaSolicitudes}"> 
								<c:set var="paginas" value="${pageInput.solporpag}"/>
								<c:forEach var="solicitud" items="${pageInput.listaSolicitudes}" varStatus="status">
									<c:set var="contador" value="${status.count}"/>
									<c:if test="${status.index%paginas==0}">
										<div id="tsol_${status.index}" class="fnd-tabla-pager-content-sub" style="display: none;">
									</c:if>
									<c:choose>
										<c:when test="${solicitud != null}">
											<div class="fnd-tabla-pager-content-row">
												<div class="fnd-tabla-pager-content-col" style="width: 102px;">
													<netui:anchor action="mostrarDetalle" styleClass="linkB">
														<netui:parameter name="noSolicitud" value='${solicitud.toaNumberOa}'/>
														<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
														<c:out value="${solicitud.toaNumberOa}"/>
													</netui:anchor>	
												</div>
					   							<div class="fnd-tabla-pager-content-col" style="width:129px; padding: 5px; text-align: center;">
					   								<netui:label value="${solicitud.toaDate}">
					   									<netui:formatDate pattern="dd/MM/yyyy hh:mm:ss"/> 
					   								</netui:label>
												</div>
												<div class="fnd-tabla-pager-content-col" style="width: 308px; line-height: 0 !important; border-right: 1px solid #D6D6D6 !important;">
													<textarea class="ta-mis-solicitudes" readonly="readonly" ><c:out value="${solicitud.toaDescripcion}"/></textarea>
												</div>
												<div class="clear"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="fnd-tabla-pager-content-row">&nbsp;</div>
										</c:otherwise>
									</c:choose>
					  				<c:if test="${(status.index+1)%paginas==0}">
										</div>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${empty pageInput.listaSolicitudes}">
								<div class="fnd-tabla-pager-content-row" style="width: 635px;">
									<div class="revisa fnd-tabla-pager-content-col-no-end">Al momento no tienes ninguna solicitud</div>
								</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="pager-content">
								<div id="pager_results"></div>
								<div id="pager"></div>
								<div class="clear"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<table cellspacing="0" cellpadding="5">
								<tr>
									<td align="right">
										<c:if test="${!empty pageInput.errores}">
											<br/>
											<font class="error-msg">
							        			${pageInput.errores}
							        		</font>
							        	</c:if>
							        	<c:if test="${empty pageInput.errores}">
											<br/>
											<font class="error-msg">
							        			<netui:errors bundleName="ordenAdminBundle" />
							        		</font>
							        	</c:if>
							        	<c:if test="${!empty pageInput.successMsgSolicitud}">
											<br/>
							        		<font class="msg-success">
							        			${pageInput.successMsgSolicitud}
							        		</font>
							        	</c:if>
							        	<c:if test="${!empty pageInput.msg}">
											<br/>
											<font class="error-msg">
							        			${pageInput.msg}
							        		</font>
							        	</c:if>
									</td>
								</tr>
							</table>	
						 </td>
					</tr>
				</table>
				
			  </netui:form>
		</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery.pager.js"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
            	$("#pager").pager({ pagenumber: 1, pagecount: ${pageInput.numPaginas}, buttonClickCallback: PageClick });
            	$("#pager_content").html($('#tsol_0').html());
            	$('#pager_results').html("Resultado 1 - ${pageInput.solporpag} de ${pageInput.numSolicitudes}");
        });

        PageClick = function(pageclickednumber) {
        	var pag = pageclickednumber*${pageInput.solporpag}-${pageInput.solporpag};
        	var last = pag+${pageInput.solporpag}>${pageInput.numSolicitudes}?${pageInput.numSolicitudes}:pag+${pageInput.solporpag};
            	$("#pager").pager({ pagenumber: pageclickednumber, pagecount: ${pageInput.numPaginas}, buttonClickCallback: PageClick });
            	$("#pager_content").html($('#tsol_'+pag).html());
            	$('#pager_results').html("Resultado "+(pag+1)+" - "+last+" de ${pageInput.numSolicitudes}");
        }
</script> -->
</netui:scriptContainer>
