<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:directive.page import="java.util.StringTokenizer"/>
<netui:scriptContainer idScope="${param._windowLabel}">
<p id="<netui:rewriteName name="emptyMsg" forTagId="true"/>" style="display:none" class="color-orange">Selecciona por favor una opción</p>
	<ul class="cotiz-content" id="cotiz-content">
		
		<li>
			<div class="color-orange add-pqt"><strong>Agrega a tu paquete:</strong></div>
		</li>
		
		<netui-data:repeater dataSource="pageInput.products">
			<li class="wrap-paq-cot">
				<input type="hidden" name="price" value="${container.item.tcPrice}" />
				<c:set var="pseName" scope="request" value="${container.item.name}"/>
				<input type="hidden" name="serviceOrder" value="${container.item.service.orden}">
				
				<div class="color-orange add-pqt-descrip">
					<span>${container.item.description}</span><br />
				</div>
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
					<div class="clear"></div>
				</div>
		
				<div class="clear"></div>
				<div class="precio-bordered"><label for="check1">Por sólo $ <netui:span value="${container.item.tcPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> mensuales</label> 
					<c:choose>
						<c:when test="${!empty pageInput.productSelect && pageInput.productSelect == container.item.idProductService }">
							<input id="<netui:rewriteName name="product_" forTagId="true"/>${container.item.idProductService}" type="radio" name="productId" value="${container.item.idProductService}" class="fright radio" checked="checked" />
						</c:when>
						<c:otherwise>
							<input id="<netui:rewriteName name="product_" forTagId="true"/>${container.item.idProductService}" type="radio" name="productId" value="${container.item.idProductService}" class="fright radio" />
						</c:otherwise>
					</c:choose>
				</div>
			</li>
		</netui-data:repeater>
	</ul>
</netui:scriptContainer>