<%@ page import="com.bea.netuix.servlets.controls.window.ButtonPresentationContext"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><render:beginRender><%
    ButtonPresentationContext button = ButtonPresentationContext.getButtonPresentationContext(request);
    String buttonClass = button.getPresentationClass();
    String defaultButtonClass = "bea-portal-button-float";
%><a target="_blank"
<render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
<render:writeAttribute name="class" value="<%= buttonClass %>" defaultValue="<%= defaultButtonClass %>"/>
<render:writeAttribute name="style" value="<%= button.getPresentationStyle() %>"/>href="<render:standalonePortletUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></render:endRender>