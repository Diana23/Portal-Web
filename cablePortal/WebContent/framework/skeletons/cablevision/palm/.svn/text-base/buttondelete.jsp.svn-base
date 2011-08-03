<%@ page import="com.bea.netuix.servlets.manager.AppDescriptor,
                 com.bea.netuix.servlets.manager.AppContext,
                 com.bea.netuix.servlets.controls.window.*,
                 java.security.Principal"
%><%@ page session="false"%><%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %><%
    ButtonPresentationContext button = ButtonPresentationContext.getButtonPresentationContext(request);
    String buttonClass = "bea-portal-button";
    // if parent window is not Page it is a Portlet
    if ( button.isParentPortlet() )
    {
        buttonClass = "bea-portal-button-delete";
    }
    String defaultButtonClass = "bea-portal-button";

    Principal principal = request.getUserPrincipal();
    String userName = ( principal == null ? null : principal.getName() );

     boolean isCustomizationEnabled = AppDescriptor.getInstance().isCustomizationEnabled();
     if(userName != null && isCustomizationEnabled && buttonClass != null &&
        buttonClass.equals("bea-portal-button-delete") && ! AppContext.getAppContext(request).isDotPortal())
     {
%><render:beginRender><span><a <render:writeAttribute name="id" value="<%= button.getPresentationId() %>"/>
href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></span></render:endRender><%}
     else if(buttonClass != null && buttonClass.equals("bea-portal-button-delete")) {
%><render:beginRender><td VALIGN=top style="background-color: #c1c1d5"><a href="<render:toggleButtonUrl/>"><img <render:writeAttribute name="src" value="<%= button.getImageSrc() %>"/>
<render:writeAttribute name="alt" value="<%= button.getAltText() %>"/><render:writeAttribute name="title" value="<%= button.getAltText() %>"/><render:writeAttribute name="longdesc" value="<%= button.getRolloverImageSrc() %>"/>/></render:beginRender><render:endRender></a></td></render:endRender><%
}%>
