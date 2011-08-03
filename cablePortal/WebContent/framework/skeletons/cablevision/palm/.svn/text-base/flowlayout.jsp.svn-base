<%@ page import="com.bea.netuix.servlets.controls.layout.PlaceholderPresentationContext,
                 com.bea.netuix.servlets.controls.layout.FlowLayoutPresentationContext,
                 java.util.List,
                 com.bea.netuix.servlets.controls.PresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    FlowLayoutPresentationContext layout = FlowLayoutPresentationContext.getFlowLayoutPresentationContext(request);
%>

<render:beginRender>
    <%-- Begin Flow Layout--%>
    <table cellspacing="0" >
        <tr>
    <%
        List children = layout.getChildren("layout:placeholder");
        int size = children.size();

        for (int i = 0; i < size; i++)
        {
            PlaceholderPresentationContext child = (PlaceholderPresentationContext) children.get(i);
    %>
            <td
                <render:writeAttribute name="width" value="<%= child.getWidth() %>"/>
            ><render:renderChild presentationContext="<%= child %>"/></td>
    <%
            // start mobile
            //if (layout.isVertical())
			if (true) 
            // end mobile
            {
                if (i != size - 1)
                {
    %>           </tr>
                 <tr>
    <%
                }
            }
        }
    %>
        </tr>
    </table>
    <%-- End Flow Layout--%>
</render:beginRender>
<render:endRender>
</render:endRender>
