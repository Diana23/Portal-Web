<%@ page import="com.bea.netuix.servlets.controls.window.ToggleButtonPresentationContext"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><%
    ToggleButtonPresentationContext button = ToggleButtonPresentationContext.getToggleButtonPresentationContext(request);
    String buttonClass = button.getPresentationClass();
    String defaultButtonClass = "bea-portal-button";
%><render:beginRender>
 <td VALIGN=top style="background-color: #c1c1d5">
 <a <render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
 href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
 <render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></a></render:beginRender>
 <render:endRender></a></TD></render:endRender>
