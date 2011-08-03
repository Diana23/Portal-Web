<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:directive.page import="java.util.StringTokenizer"/>
<netui:scriptContainer idScope="${param._windowLabel}">
<li class="padd-align">
	<c:if test="${!empty pageInput.product}">
		<img src="${pageInput.product.image}" alt="">
		<br><br>
		<div id="<netui:rewriteName name="descripcion" forTagId="true"/>">
			<c:set var="pseName" scope="request" value="${pageInput.product.name}"/>
			<%
				StringTokenizer st = new StringTokenizer(request.getAttribute("pseName").toString(),"|");
				while(st.hasMoreTokens()){
			%>
				<span class="color-orange"> &gt; </span> <%=st.nextToken()%><br/>	
			<% 
				}
			%>
		</div>
		<br><br>
		<div class="precio-bordered" align="center">Por sólo <span id="<netui:rewriteName name="sumatoriaExtras" forTagId="true"/>">$ <netui:span value="${pageInput.product.tcPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> </span> mensuales</div>
	</c:if>
		<% 
			int contador = 0;
			if(contador%2==0){
		%>
			<li class="padd-align" id="<netui:rewriteName name="liExtras" forTagId="true"/>" style="display:none;">
		<% 
			} else{
		%>
			<li class="padd-align back-ea" id="<netui:rewriteName name="liExtras" forTagId="true"/>" style="display:none;" >
		<%				
			}
		%>
			<div id="<netui:rewriteName name="descripcionExtras" forTagId="true"/>" ></div>
</li>
</netui:scriptContainer>