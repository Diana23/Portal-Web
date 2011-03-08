<%@ page import="com.bea.netuix.servlets.controls.application.BodyPresentationContext" %>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    BodyPresentationContext body = BodyPresentationContext.getBodyPresentationContext(request);
%>

<render:beginRender>
    <%-- Begin Body --%>
    <body
        <render:writeAttribute name="onload" value="<%= body.getOnloadScript() %>"/>
        <render:writeAttribute name="onunload" value="<%= body.getOnunloadScript() %>"/>
    >
        <%-- Begin Body Content --%>
</render:beginRender>
<render:endRender>
        <%-- End Body Content --%>
    </body>
    <%-- End Body--%>
</render:endRender>
