<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%

 if(request.getAttribute("contenidoId")!=null){
  com.cablevision.controller.contenido.ContenidoEstructuradoController.getContenido(request, response,(String)request.getAttribute("contenidoId"),(String)request.getAttribute("templateId"),(String)request.getAttribute("estructuraId"),"","");
 }else{
  com.cablevision.controller.contenido.ContenidoEstructuradoController.getContenido(request, response,request.getParameter("contenidoId"),request.getParameter("templateId"),request.getParameter("estructuraId"),"","");
 }
 

%>

<jsp:include page="pagina.jsp"/>

<%
request.removeAttribute("contenidoId");
request.removeAttribute("templateId");
request.removeAttribute("estructuraId");
%>