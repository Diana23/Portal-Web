<%@ page import="com.bea.netuix.servlets.controls.layout.PlaceholderPresentationContext,
                 com.bea.netuix.servlets.controls.layout.GridLayoutPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<% GridLayoutPresentationContext layout = GridLayoutPresentationContext.getGridLayoutPresentationContext(request); %>

<render:beginRender>
    <%-- Begin Grid Layout--%>
    <table width=100%>
    <%
        PlaceholderPresentationContext[][] grid = layout.getPlaceholderGrid();

        // start mobile
        // create a new grid that's 1xn
        int length = 0;
        for (int i=0; i<grid.length; i++)  
        {
			length += grid[i].length;
        }

		PlaceholderPresentationContext[][] onegrid = new PlaceholderPresentationContext[length][1];

        int cur = 0;
        for (int i=0; i<grid.length; i++) 
        {
            for (int j=0; j<grid[i].length; j++)  
            {
				onegrid[cur][0] = grid[i][j];
				cur++;
            }
        }

        // use the one column-grid from here on out
		grid = onegrid;
        // end mobile

        for (int i = 0; i < grid.length; i++)
        {
    %>
        <tr>
    <%
            for (int j = 0; j < grid[i].length; j++)
            {
    %>
            <td width="100%"><render:renderChild presentationContext="<%= grid[i][j] %>"/></td>
    <%
            }
    %>
        </tr>
    <%
        }
    %>
    </table>
    <%-- End Grid Layout--%>
</render:beginRender>
<render:endRender>
</render:endRender>
