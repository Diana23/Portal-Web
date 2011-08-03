<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="wcs-marg">
	<div class="wrap-car-entre">
		<div class="span-6">
			<h2 class="title-carr-enod1 hidden-text">TV destacado de la semana</h2>
		</div>
		<c:set var="tipo" value="ondemand" scope="request"/>
		<c:set var="tipoRuleta" value="ruletaPrincipal" scope="request"/>
		<c:set var="todos" value="true" scope="request"/>
		<jsp:include page="/com/cablevision/controller/entretenimiento/begin.do"/>
	</div>
	
	<div class="wrap-car-entre">
		<div class="span-6">
			<h2 class="title-carr-enod2 hidden-text">OnDemand </h2>
		</div>
	
		<c:set var="tipo" value="ondemand" scope="request"/>
		<c:set var="tipoRuleta" value="ruletaSecundaria" scope="request"/>
		<jsp:include page="/com/cablevision/controller/entretenimiento/begin.do?tipo=ondemand&tipoRuleta=ruletaSecundaria"/>
	</div>
</div>

	<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/easing.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/mousewheel.min.js"></script>
	

