<%@ page import="com.bea.netuix.servlets.controls.ControlContext,
                 com.bea.netuix.servlets.controls.page.MenuPresentationContext,
                 com.bea.netuix.servlets.controls.window.TitlebarPresentationContext,
                 java.util.List,
                 java.util.LinkedList,
                 com.bea.netuix.servlets.controls.PresentationContext,
                 com.bea.netuix.servlets.controls.window.AbstractButtonPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    // Hack until a better context stack is available on the request...
    PresentationContext ctx = (PresentationContext) request.getAttribute(PresentationContext.class.getName() + ".current");

    if (ctx == null)
    {
        return;
    }

    List standards = new LinkedList();
    List extensions = new LinkedList();
    List buttons = ctx.getChildren();

    for (int i = 0, size = buttons.size(); i < size; i++)
    {
        AbstractButtonPresentationContext button = (AbstractButtonPresentationContext) buttons.get(i);
        String tagName = button.getTagName();

        if (tagName.equals("window:minimized") || tagName.equals("window:maximized") || tagName.equals("window:delete"))
        {
            standards.add(button);
        }
        else
        {
            extensions.add(button);
        }
    }

    int stdSize = standards.size();
    int extSize = extensions.size();

    // extra processing if we have buttons but not a titlebar
	if (extSize == 0 && stdSize > 0)
    { %>
		
    <TABLE><TR>
	<%
    }

    if (stdSize > 0 || extSize > 0)
    {
%>
				<%
    for (int i = 0; i < extSize; i++)
    {
%><render:renderChild presentationContext="<%= (AbstractButtonPresentationContext) extensions.get(i) %>"/><%
    }
%>
<%
    for (int i = 0; i < stdSize; i++)
    {
%><render:renderChild presentationContext="<%= (AbstractButtonPresentationContext) standards.get(i) %>"/><%
    }
	if (extSize == 0 && stdSize > 0)
    {
        // extra processing if we have buttons but not a titlebar
        %>
        </TR></TABLE>
		<%
    }
%>
<%
    }
%>
