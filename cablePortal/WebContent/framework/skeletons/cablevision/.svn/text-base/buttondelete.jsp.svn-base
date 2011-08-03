<%@ page import="com.bea.netuix.servlets.manager.AppDescriptor,
                 com.bea.netuix.servlets.manager.AppContext,
                 com.bea.netuix.servlets.controls.window.*,
                 java.security.Principal"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><%
    ButtonPresentationContext button = ButtonPresentationContext.getButtonPresentationContext(request);
    String buttonClass = "bea-portal-button";
    // if parent window is not Page it is a Portlet
	boolean isParentPortlet = false;
    if ( button.isParentPortlet() )
    {
        buttonClass = "bea-portal-button-delete";
		isParentPortlet = true;
    }
    String defaultButtonClass = "bea-portal-button";

    Principal principal = request.getUserPrincipal();
    String userName = ( principal == null ? null : principal.getName() );

     boolean isCustomizationEnabled = AppDescriptor.getInstance().isCustomizationEnabled();
     if(userName != null && isCustomizationEnabled && buttonClass != null &&
        isParentPortlet && ! AppContext.getAppContext(request).isDotPortal())
     {
%><render:beginRender><span><a <render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
<render:writeAttribute name="class" value="<%= buttonClass %>" defaultValue="<%= defaultButtonClass %>"/>
<render:writeAttribute name="style" value="<%= button.getPresentationStyle() %>"/>href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></span></render:endRender><%}
     else if(buttonClass != null && isParentPortlet) {
%><render:beginRender><a <render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
<render:writeAttribute name="class" value="<%= buttonClass %>" defaultValue="<%= defaultButtonClass %>"/>
<render:writeAttribute name="style" value="<%= button.getPresentationStyle() %>"/>href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></render:endRender><%
}%>
