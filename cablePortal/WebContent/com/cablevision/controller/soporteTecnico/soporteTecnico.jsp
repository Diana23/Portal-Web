<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="<%=request.getContextPath()%>/resources/css/explorer.css" type="text/css" rel="stylesheet">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/easing.1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/mousewheel.min.js"></script>

<jsp:directive.page import="com.bea.portlet.PageURL" />
<jsp:directive.page import="com.bea.portlet.GenericURL" />
<jsp:directive.page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils" />

<div class="span-18 last">
	<div class="wrap-cont-simple">
		<div class="span-11">
			<c:set var="contenidoId" value="CV002500" scope="request"/>
		    <c:set var="estructuraId" value="CV002503" scope="request"/>
		    <c:set var="templateId" value="CV002505" scope="request"/>
		    <jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
		    
		    <c:set var="contenidoId" value="CV002449" scope="request"/>
		    <c:set var="estructuraId" value="CV002447" scope="request"/>
		    <c:set var="templateId" value="CV002507" scope="request"/>
		    <jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
		</div>
		
		<div class="span-5 last marg-left20">						
			<ul>
				<c:set var="contenidoId" value="CV002501" scope="request"/>
			    <c:set var="estructuraId" value="CV002504" scope="request"/>
			    <c:set var="templateId" value="CV002506" scope="request"/>
			    <jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
				
				<li class="job-side-title">Descargables</li>
				<c:set var="contenidoId" value="CV002489" scope="request"/>
			    <c:set var="estructuraId" value="CV002488" scope="request"/>
			    <c:set var="templateId" value="CV002487" scope="request"/>
			    <jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
			</ul>	
			<div class="clear"></div><br><br><br>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>		
</div>

