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
    boolean isMinimized =  window.getWindowState().equals(WindowCapabilities.MINIMIZED);
%>

<render:beginRender>
<%
    TitlebarPresentationContext titlebar = (TitlebarPresentationContext) window.getTitlebarPresentationContext();
    final String expandWidth = "100%";
%>
    <%-- Begin Window --%>
    <div
        <render:writeAttribute name="id" value="<%= window.getPresentationId() %>"/>
        <render:writeAttribute name="class" value="<%= window.getPresentationClass() %>" defaultValue="bea-portal-borderless-window"/>
        <render:writeAttribute name="style" value="<%= window.getPresentationStyle() %>"/>
        <render:writeAttribute name="width" value="<%= window.isPacked() ? null : expandWidth %>"/>
    >
        <render:renderChild presentationContext="<%= titlebar %>"/>
        <%-- Begin Window Content --%>
<%  if (! isMinimized )
{
        %><div class="bea-portal-window-content"
               <render:writeAttribute name="style" value="<%= window.getContentPresentationStyle() %>" />
        ><%
}
%>
</render:beginRender>
<render:endRender>
<%  if (! isMinimized )
{
        %></div><%
}
%>
        <%-- End Window Content --%>
    </div>
    <%-- End Window --%>
</render:endRender>
