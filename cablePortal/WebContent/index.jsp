<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<jsp:directive.page import="com.cablevision.util.PageNewUrl" />
<jsp:directive.page import="com.bea.portlet.PageURL" />
<jsp:directive.page import="com.bea.portlet.GenericURL" />

<%
try {
	
	String pageLabel = "cablevision_portal_page_home";
	PageURL urlAnterior = PageURL.createPageURL(request, response, pageLabel);
	urlAnterior.setTemplate("desktopContextPath");
	urlAnterior.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	urlAnterior.setForcedAmpForm(false);
	
	((HttpServletRequest)request).getRequestDispatcher(urlAnterior.toString()).forward(request, response);

} catch (Exception e) {
	e.printStackTrace();
}
%>