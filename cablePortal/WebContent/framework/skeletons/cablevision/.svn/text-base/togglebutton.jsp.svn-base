<%@ page import="com.bea.netuix.servlets.controls.window.ToggleButtonPresentationContext"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><render:beginRender><%
    ToggleButtonPresentationContext button = ToggleButtonPresentationContext.getToggleButtonPresentationContext(request);
    String buttonClass = button.getPresentationClass();
%><a <render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
<render:writeAttribute name="class" value="<%= buttonClass %>" defaultValue="bea-portal-button"/>
<render:writeAttribute name="style" value="<%= button.getPresentationStyle() %>"/>href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></render:endRender>