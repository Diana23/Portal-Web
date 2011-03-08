<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="com.cablevision.util.PaymentsLine"/>

<netui:scriptContainer>

<div class="clear"></div>
<div align="left" class="span-16 padding-top25">
	<div class="span-10">
		<div class="title-factura padding-top25"><strong>¿Deseas realizar un pago en línea?</strong></div>
	</div>	
	<div class="clear"></div>
		<jsp:scriptlet>
			PageURL url = PageURL.createPageURL(request, response, "servicios_enlinea_pagar");
			url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setTemplate("defaultDesktop");
			pageContext.setAttribute("url",url);	
		</jsp:scriptlet>
		<br/>
		<b>Recuerda que es seguro.</b><br/>
					
		<c:if test="${!empty pageInput.listaRef}">
			Paga directamente en el banco de tu preferencia</p><br/>
			<b>Referencias bancarias de pago</b></p>
			
			<table width="100%" border="0" align="center">
				<c:set var="referencias" scope="request" value="${pageInput.listaRef}"/>
				<%
					ArrayList listaReferencias = new ArrayList();
					listaReferencias = (ArrayList)request.getAttribute("referencias");
					Iterator it = listaReferencias.iterator();
					
					while(it.hasNext()){
						PaymentsLine pl = (PaymentsLine)it.next();
						%>
							<tr>
								<td width="33%"> 
									<b><%=pl.getCV_FLD_BANK_NAME()%></b> 
									<br/><%=pl.getCV_FLD_BANK_LINE()%>
								</td>
								<td width="33%">
									<%
										if(it.hasNext()){
											PaymentsLine pl2 = (PaymentsLine)it.next();
									%>
										<b><%=pl2.getCV_FLD_BANK_NAME()%></b> 
										<br/><%=pl2.getCV_FLD_BANK_LINE()%>
									<%
										} 
									%>
								</td>
								<td width="33%">
									<%
										if(it.hasNext()){
											PaymentsLine pl3 = (PaymentsLine)it.next();
									%>
										<b><%=pl3.getCV_FLD_BANK_NAME()%></b> 
										<br/><div class="revisa"><%=pl3.getCV_FLD_BANK_LINE()%></div>
									<%
										} 
									%>
								</td>
							</tr>
						<%
					}
				%>	
			</table>
			<!-- 
			 -->
			<a title="Pago en l&iacute;nea" value="Pago en l&iacute;nea" href="${url}" class="btn-small bs-2-lines fright">Pago en l&iacute;nea</a>
		</c:if>
			
</div>
</netui:scriptContainer>