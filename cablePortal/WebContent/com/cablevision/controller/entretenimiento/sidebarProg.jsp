<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2 class="title-side-${pageInput.tipo} hidden-text">Lo m&aacute;s visto</h2>
<c:set var="tipo" value="${pageInput.tipo}" scope="request"/>
<c:set var="random" value="true" scope="request"/>
<jsp:include page="/com/cablevision/controller/entretenimiento/lmvAction.do?tipo=${pageInput.tipo}"/>
<div class="clear"></div>
<br><br>