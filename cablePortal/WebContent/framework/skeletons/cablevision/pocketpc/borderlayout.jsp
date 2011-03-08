<%@ page import="com.bea.netuix.servlets.controls.layout.PlaceholderPresentationContext,
                 com.bea.netuix.servlets.controls.layout.BorderLayoutPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    BorderLayoutPresentationContext layout = BorderLayoutPresentationContext.getBorderLayoutPresentationContext(request);
    PlaceholderPresentationContext north = layout.north();
    PlaceholderPresentationContext west = layout.west();
    PlaceholderPresentationContext center = layout.center();
    PlaceholderPresentationContext east = layout.east();
    PlaceholderPresentationContext south = layout.south();
    int columns = layout.columns();

    // mobile
	columns = 1;
%>

<render:beginRender>
    <%-- Begin Border Layout --%>
    <table
        <render:writeAttribute name="id" value="<%= layout.getPresentationId() %>"/>
        <render:writeAttribute name="class" value="<%= layout.getPresentationClass() %>" defaultValue="bea-portal-layout-border"/>
        <render:writeAttribute name="style" value="<%= layout.getPresentationStyle() %>"/>
        cellspacing="0"
    >
	<%-- mobile - override so that it's only one column --%>
    <%
        if (north != null)
        {
    %>
        <tr>
            <td class="bea-portal-layout-placeholder-container-north" colspan="<%= columns %>"
                <render:writeAttribute name="width" value="<%= north.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= north %>"/></td>
        </tr>
    <%
        }

        if (west != null || center != null || east != null)
        {
    %>
    <%
            if (west != null)
            {
    %>
        <tr>
            <td class="bea-portal-layout-placeholder-container-west"
                <render:writeAttribute name="width" value="<%= west.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= west %>"/></td> 
		</TR>
    <%
            }

            if (center != null)
            {
    %>
        <tr>
            <td class="bea-portal-layout-placeholder-container-center"
                <render:writeAttribute name="width" value="<%= center.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= center %>"/></td>
		</TR>
    <%
            }

            if (east != null)
            {
    %>
        <tr>
            <td class="bea-portal-layout-placeholder-container-east"
                <render:writeAttribute name="width" value="<%= east.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= east %>"/></td>
        </tr>
    <%
            }
        }

        if (south != null)
        {
    %>
        <tr>
            <td class="bea-portal-layout-placeholder-container-south" colspan="<%= columns %>"
                <render:writeAttribute name="width" value="<%= south.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= south %>"/></td>
        </tr>
    <%
        }
    %>
    </table>
    <%-- End Border Layout --%>
</render:beginRender>
<render:endRender>
</render:endRender>
