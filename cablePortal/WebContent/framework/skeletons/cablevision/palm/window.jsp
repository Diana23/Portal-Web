<%@ page import="com.bea.netuix.servlets.controls.window.WindowPresentationContext,
            com.bea.netuix.servlets.controls.window.TitlebarPresentationContext,
                 java.util.List,
                 java.util.Iterator,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    WindowPresentationContext window = WindowPresentationContext.getWindowPresentationContext(request);
    TitlebarPresentationContext titlebar = (TitlebarPresentationContext) window.getTitlebarPresentationContext();

%>

<render:beginRender>
        <render:renderChild presentationContext="<%= titlebar %>"/>
</render:beginRender>
<render:endRender>
    <HR>
</render:endRender>
