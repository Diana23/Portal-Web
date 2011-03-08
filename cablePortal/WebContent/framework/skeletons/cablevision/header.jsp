<%@ page import="java.security.Principal,
                 com.bea.netuix.servlets.controls.application.DesktopPresentationContext,
                 com.bea.netuix.servlets.controls.application.HeaderPresentationContext,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.netuix.servlets.controls.page.PagePresentationContext,
                 com.bea.netuix.servlets.manager.AppContext,
                 com.bea.portlet.GenericURL,
                 com.bea.portlet.PostbackURL" %>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<%
    HeaderPresentationContext header = HeaderPresentationContext.getHeaderPresentationContext(request);
%>

<render:beginRender>
    <script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
	</script>
    <script src="${pageContext.request.contextPath}/framework/skins/cablevision/js/jquery-1.4.2.min.js" type="text/javascript"/>
</render:beginRender>

<render:endRender>
</render:endRender>
