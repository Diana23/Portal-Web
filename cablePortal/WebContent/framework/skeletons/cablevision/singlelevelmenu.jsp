<%@ page import="com.bea.netuix.servlets.controls.window.WindowPresentationContext,
                 com.bea.netuix.servlets.controls.window.TitlebarPresentationContext,
                 java.util.Iterator,
                 com.bea.netuix.servlets.controls.page.PagePresentationContext,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.netuix.servlets.controls.page.MenuPresentationContext,
                 com.bea.netuix.servlets.controls.PresentationContext,
                 java.util.List,
                 com.bea.netuix.servlets.controls.window.WindowCapabilities" %>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>
<%!
    static final String BOOK_CLASS =            "bea-portal-book";
    static final String MENU_CLASS =            BOOK_CLASS + "-menu-single";
    static final String MENU_CONTAINER_CLASS =  MENU_CLASS + "-container";
    static final String MENU_ITEM_CLASS =       MENU_CLASS + "-item";
    static final String MENU_ITEM_ACTIVE_CLASS= MENU_ITEM_CLASS + "-active";
    static final String MENU_BUTTONS_CLASS =    MENU_ITEM_CLASS + "-buttons";

    static final String DESKTOP_BOOK_CLASS =            "bea-portal-book-primary";
    static final String DESKTOP_MENU_CLASS =            DESKTOP_BOOK_CLASS + "-menu-single";
    static final String DESKTOP_MENU_CONTAINER_CLASS =  DESKTOP_MENU_CLASS + "-container";
    static final String DESKTOP_MENU_ITEM_CLASS =       DESKTOP_MENU_CLASS + "-item";
    static final String DESKTOP_MENU_ITEM_ACTIVE_CLASS= DESKTOP_MENU_ITEM_CLASS + "-active";
    static final String DESKTOP_MENU_BUTTONS_CLASS =    DESKTOP_MENU_ITEM_CLASS + "-buttons";
%>


<%
    BookPresentationContext book = BookPresentationContext.getBookPresentationContext(request);
    MenuPresentationContext menu = MenuPresentationContext.getMenuPresentationContext(request);

    String menuContainerClass = MENU_CONTAINER_CLASS;
	String menuClass = MENU_CLASS;
    String menuItemClass = MENU_ITEM_CLASS;
    String menuItemActiveClass = MENU_ITEM_ACTIVE_CLASS;
    String menuButtonsClass = MENU_BUTTONS_CLASS;

    if (book.isDesktopBook())
    {
		menuClass =             DESKTOP_MENU_CLASS;
        menuContainerClass =    DESKTOP_MENU_CONTAINER_CLASS;
        menuItemClass =         DESKTOP_MENU_ITEM_CLASS;
        menuItemActiveClass =   DESKTOP_MENU_ITEM_ACTIVE_CLASS;
        menuButtonsClass =      DESKTOP_MENU_BUTTONS_CLASS;
    }

    List children = menu.getChildren();
%>

<render:beginRender>
    <%-- Begin Single Level Menu --%>
    <div class="bea-portal-ie-table-buffer-div">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="<%= menuContainerClass %>" align="left" nowrap="nowrap">
    <ul
        <render:writeAttribute name="id" value="<%= menu.getPresentationId() %>"/>
        <render:writeAttribute name="class" value="<%= menu.getPresentationClass() %>" defaultValue="<%= menuClass %>"/>
        <render:writeAttribute name="style" value="<%= menu.getPresentationStyle() %>"/>
    ><%

    // Only render tabs when in view mode
    if (book.getWindowMode().equals(WindowCapabilities.VIEW))
    {

        Iterator pages = book.getPagePresentationContexts().iterator();

        while (pages.hasNext())
        {
            PagePresentationContext pageCtx = (PagePresentationContext) pages.next();
            String activeImage = pageCtx.getActiveImage();
            String inactiveImage = pageCtx.getInactiveImage();
            String rolloverImage = pageCtx.getRolloverImage();

            if (!pageCtx.isHidden() && pageCtx.isVisible())
            {
                if (pageCtx.isActive())
                {
                    if (activeImage == null)
                    {
                        %><li class="<%= menuItemActiveClass %>"><span><%= pageCtx.getTitle() %></span></li><%
                    }
                    else if (rolloverImage == null)
                    {
                        %><li class="<%= menuItemActiveClass %>"><span><img src="<%= activeImage %>"></span></li><%
                    }
                    else
                    {
                        %><li class="<%= menuItemActiveClass %>"><span><img src="<%= activeImage %>" longDesc="<%= rolloverImage %>"></span></li><%
                    }
                }
                else
                {
                    if (inactiveImage == null)
                    {
                        %><li class="<%= menuItemClass %>"><a href="<render:pageUrl pageLabel="<%= pageCtx.getDefinitionLabel() %>"/>"><%= pageCtx.getTitle() %></a></li><%
                    }
                    else if (rolloverImage == null)
                    {
                        %><li class="<%= menuItemClass %>"><a href="<render:pageUrl pageLabel="<%= pageCtx.getDefinitionLabel() %>"/>"><img src="<%= inactiveImage %>"></a></li><%
                    }
                    else
                    {
                        %><li class="<%= menuItemClass %>"><a href="<render:pageUrl pageLabel="<%= pageCtx.getDefinitionLabel() %>"/>"><img src="<%= inactiveImage %>" longDesc="<%= rolloverImage %>"></a></li><%
                    }
                }
            }
        }
    }
%></ul>
                </td>
<%
    if (children != null && children.size() > 0)
    {
%>
                <td class="<%= menuButtonsClass %>" align="right" nowrap="nowrap">
<%
    }
%>
</render:beginRender>
<render:endRender>
<%
    if (children != null && children.size() > 0)
    {
%>
                </td>
<%
    }
%>
            </tr>
        </table>
    </div>
    <%-- End Single Level Menu --%>
</render:endRender>
