<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:directive.page import="com.bea.portlet.GenericURL"/>

<%
	GenericURL begin = GenericURL.createGenericURL(request, response);
	begin.setPath(request.getContextPath()+"/com/cablevision/controller/email/showEmail.do");
	begin.setTemplate("https");
%>
<iframe scrolling="no" src="<%=begin.toString()%>" width="240" height="197" frameborder="0"> </iframe>