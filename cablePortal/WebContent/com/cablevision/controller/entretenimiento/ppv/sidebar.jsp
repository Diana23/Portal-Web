<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2 class="title-side-ppv hidden-text">PPV lo m&aacute;s visto</h2>
<c:set var="tipo" value="ppv" scope="request"/>
<jsp:include page="/com/cablevision/controller/entretenimiento/lmvAction.do?tipo=ppv"/>
<div class="clear"></div>
<br><br>

<c:set var="contenidoId" value="CV002598" scope="request"/>
<c:set var="estructuraId" value="CV001950" scope="request"/>
<c:set var="templateId" value="CV001951" scope="request"/>
<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
