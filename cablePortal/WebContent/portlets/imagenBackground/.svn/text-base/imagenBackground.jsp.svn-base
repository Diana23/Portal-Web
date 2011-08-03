<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<% 
com.bea.netuix.servlets.controls.portlet.PortletPresentationContext porContext = com.bea.netuix.servlets.controls.portlet.PortletPresentationContext.getPortletPresentationContext(request);
com.bea.netuix.servlets.controls.page.PagePresentationContext context = com.bea.netuix.servlets.controls.page.PagePresentationContext.getPagePresentationContext(request);
String backgroundId = porContext.getPortletPreferences(request).getValue("backgroundId",null);



if(org.apache.commons.lang.StringUtils.isEmpty(backgroundId)){
	backgroundId = context.getProperty("backgroundId");
}

pageContext.setAttribute("backgroundId",backgroundId);
%>

<jsp:include page="/com/cablevision/controller/contenido/renderizarContenido.jsp?contenidoId=${backgroundId}&estructuraId=CV001993&templateId=CV001994"/>
