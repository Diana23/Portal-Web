<%@ page import="com.bea.netuix.servlets.controls.window.TitlebarPresentationContext,
                 com.bea.netuix.servlets.controls.window.WindowPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<render:beginRender>
<%
    WindowPresentationContext window = WindowPresentationContext.getWindowPresentationContext(request);
    TitlebarPresentationContext titlebar = TitlebarPresentationContext.getTitlebarPresentationContext(request);
    String title = window.getTitle();
    title = (title == null) ? "" : title;
    String imageIcon = titlebar.getIconUrl();
    boolean useIcon = (imageIcon != null && imageIcon.length() > 0);
%><div
        <render:writeAttribute name="id" value="<%= titlebar.getPresentationId() %>"/>
        <render:writeAttribute name="class" value="<%= titlebar.getPresentationClass() %>" defaultValue="bea-portal-window-titlebar"/>
        <render:writeAttribute name="style" value="<%= titlebar.getPresentationStyle() %>"/>
    >
        <div class="bea-portal-ie-table-buffer-div">
            <table class="bea-portal-window-titlebar-container" cellspacing="0">
                <tr>
<%
    if (useIcon)
    {
%>
                    <td class="bea-portal-window-icon"><img src="<%= imageIcon %>"/></td>
<%
    }
%>
                    <td class="bea-portal-window-titlebar-title" nowrap="nowrap"><%= title %></td>
                    <td class="bea-portal-window-titlebar-buttons" nowrap="nowrap"></render:beginRender><%--
                        This marks the break between begin and end render blocks; buttons will be output here.
                    --%><render:endRender></td>
                </tr>
            </table>
        </div>
  </div>
</render:endRender>
