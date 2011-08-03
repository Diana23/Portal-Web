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
    <table width="100%" >
	<%-- mobile - override so that it's only one column --%>
    <%
        if (north != null)
        {
    %>
        <tr>
            <td colspan="<%= columns %>" width="100%" ><render:renderChild presentationContext="<%= north %>"/></td>
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
            <td width="100%" ><render:renderChild presentationContext="<%= west %>"/></td> 
		</TR>
    <%
            }

            if (center != null)
            {
    %>
        <tr>
            <td width="100%" ><render:renderChild presentationContext="<%= center %>"/></td>
		</TR>
    <%
            }

            if (east != null)
            {
    %>
        <tr>
            <td width="100%" ><render:renderChild presentationContext="<%= east %>"/></td>
        </tr>
		 
    <%
            }
        }

        if (south != null)
        {
    %>
        <tr>
            <td colspan="<%= columns %>" width="100%"><render:renderChild presentationContext="<%= south %>"/></td>
        </tr>
    <%
        }
    %>
    </table>
    <%-- End Border Layout --%>
</render:beginRender>
<render:endRender>
</render:endRender>
