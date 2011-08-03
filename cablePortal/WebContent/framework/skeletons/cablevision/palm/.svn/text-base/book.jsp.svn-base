<%@ page import="com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.netuix.servlets.controls.page.MenuPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    BookPresentationContext book = BookPresentationContext.getBookPresentationContext(request);
    MenuPresentationContext menu = (MenuPresentationContext) book.getFirstChild("page:menu");
%>

<render:beginRender>
    <%-- Begin Book --%>
        <render:renderChild presentationContext="<%= menu %>"/>
        <%-- Begin Book Content --%>
</render:beginRender>
<render:endRender>
        <%-- End Book Content --%>
    <%-- End Book --%>
</render:endRender>
