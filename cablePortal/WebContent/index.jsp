<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<jsp:directive.page import="com.cablevision.util.PageNewUrl" />
<jsp:directive.page import="com.bea.portlet.PageURL" />
<jsp:directive.page import="com.bea.portlet.GenericURL" />

<%
try {
	
	String pageLabel = "cablevision_portal_page_home";
	PageURL url = PageURL.createPageURL(request, response, pageLabel);
	url.setTemplate("desktopContextPath");
	url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	url.setForcedAmpForm(false);
	url.setEncodeSession(false);
	
	((HttpServletRequest)request).getRequestDispatcher(url.toString()).forward(request, response);

} catch (Exception e) {
	e.printStackTrace();
}
%>