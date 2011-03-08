<%@ page import="com.bea.netuix.servlets.controls.window.WindowPresentationContext,
                 com.bea.netuix.servlets.controls.window.TitlebarPresentationContext,
                 java.util.List,
                 java.util.Iterator,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.netuix.servlets.controls.window.WindowCapabilities"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    WindowPresentationContext window = WindowPresentationContext.getWindowPresentationContext(request);
%>
<render:beginRender>
<%
    TitlebarPresentationContext titlebar = (TitlebarPresentationContext) window.getTitlebarPresentationContext();
%>
    <%-- Begin Window --%>
	<render:renderChild presentationContext="<%= titlebar %>"/>
    <%-- Begin Window Content --%>
</render:beginRender>
<render:endRender>
    <%-- End Window --%>
</render:endRender>
