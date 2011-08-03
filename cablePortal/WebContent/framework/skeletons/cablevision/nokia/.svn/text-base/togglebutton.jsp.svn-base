<%@ page import="com.bea.netuix.servlets.controls.window.ToggleButtonPresentationContext"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><%
    ToggleButtonPresentationContext button = ToggleButtonPresentationContext.getToggleButtonPresentationContext(request);
%><render:beginRender>
 <td><a href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
 <render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender>
<render:endRender>
</a></td>
</render:endRender>
