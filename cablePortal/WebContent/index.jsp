<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%
com.bea.portlet.PageURL url = com.bea.portlet.PageURL.createPageURL(request, response);
url.setTemplate("home");
url.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");

response.sendRedirect(url.toString());
%>