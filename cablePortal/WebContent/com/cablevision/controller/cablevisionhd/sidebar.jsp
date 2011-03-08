<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2 class="title-side-hd hidden-text">HD lo m&aacute;s visto</h2>
<c:set var="tipo" value="hd" scope="request"/>
<jsp:include page="/com/cablevision/controller/entretenimiento/lmvAction.do?tipo=hd"/>
<div class="clear"></div>
<br><br>
