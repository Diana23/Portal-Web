<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.page import="java.util.StringTokenizer"/>
<netui:scriptContainer idScope="${fn:escapeXml(param._windowLabel)}">
<p id="<netui:rewriteName name="emptyMsg" forTagId="true"/>" style="display:none" class="color-orange">Selecciona por favor una opción</p>
	<ul class="cotiz-content" id="cotiz-content">
		<li>
			<div class="color-orange add-pqt"><strong>Agrega a tu paquete:</strong></div>
		</li>
		
		<netui-data:repeater dataSource="requestScope.mejoras">
			<netui-data:repeater dataSource="container.item">
				
				<li class="wrap-paq-cot">
					<input type="hidden" name="price" value="${container.item.tcPrice}" />
					<input type="hidden" name="pricenormal" value="${container.item.normalPrice}" />
					<c:set var="pseName" scope="request" value="${container.item.name}"/>
					<input type="hidden" name="group" value="${container.item.grupo}" />
					
					<div class="span-5 height-fix">
						<%
							StringTokenizer st = new StringTokenizer(request.getAttribute("pseName").toString(),"|");
							while(st.hasMoreTokens()){
						%>
							<span class="color-orange"> > </span><c:out value='<%=st.nextToken()%>'/><br />
						<% 
							}
						%>
					</div>
					<div class="span-5 bk-paq-cot height-fix last" align="center">
						<img src="${container.item.image}" alt="${container.item.name}"  />
						<!--img src="http://www.latinlabs.com.ar/cablevision/img/thmubcombo2.png" alt="${container.item.name}"  /-->
						<div class="clear"></div>
					</div>
			
					<div class="clear"></div>
					<div class="precio-bordered"><label for="check1">Por sólo $ <netui:span value="${container.item.tcPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> mensuales</label> 
						<input id="<netui:rewriteName name="product_" forTagId="true"/>${container.item.grupo}" type="radio" name="<netui:rewriteName name="product_" forTagId="true"/>${container.item.grupo}" value="${container.item.idProductService}" class="fright radiom" />
						</br><label for="check1" style="font-size: 9px;">*Precio en efectivo $ <netui:span value="${container.item.normalPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> mensuales</label>
					</div>
				</li>
			</li>
			</netui-data:repeater>
		</netui-data:repeater>
	</ul>
</netui:scriptContainer>