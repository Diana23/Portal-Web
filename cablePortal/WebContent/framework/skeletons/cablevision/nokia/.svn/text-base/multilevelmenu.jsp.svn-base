<%@ page import="com.bea.netuix.servlets.controls.window.WindowPresentationContext,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.netuix.servlets.controls.page.MenuPresentationContext,
                 java.util.List,
                 java.util.Iterator,
                 com.bea.netuix.servlets.controls.page.PagePresentationContext" %>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>
<%!
    static final String BOOK_CLASS =            "bea-portal-book";
    static final String MENU_CLASS =            BOOK_CLASS + "-menu";
    static final String MENU_CONTAINER_CLASS =  MENU_CLASS + "-container";
    static final String MENU_ITEM_CLASS =       MENU_CLASS + "-item";
    static final String MENU_ITEM_ACTIVE_CLASS= MENU_ITEM_CLASS + "-active";
    static final String MENU_ITEM_LINK_CLASS =  MENU_ITEM_CLASS + "-link";

    static final String DESKTOP_BOOK_CLASS =            "bea-portal-book-primary";
    static final String DESKTOP_MENU_CLASS =            DESKTOP_BOOK_CLASS + "-menu";
    static final String DESKTOP_MENU_CONTAINER_CLASS =  DESKTOP_MENU_CLASS + "-container";
    static final String DESKTOP_MENU_ITEM_CLASS =       DESKTOP_MENU_CLASS + "-item";
    static final String DESKTOP_MENU_ITEM_ACTIVE_CLASS= DESKTOP_MENU_ITEM_CLASS + "-active";
    static final String DESKTOP_MENU_ITEM_LINK_CLASS =  DESKTOP_MENU_ITEM_CLASS + "-link";

    static final String BOOK_PRESENTATION_CONTEXT_CLASS = BookPresentationContext.class.getName();
    static final String ROOT_FLAG_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".root-flag";
    static final String MENU_ITEM_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item";
    static final String MENU_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-class";
    static final String MENU_ITEM_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-class";
    static final String MENU_ITEM_ACTIVE_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-active-class";
    static final String MENU_ITEM_LINK_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-link-class";
%>

<%
    BookPresentationContext book = BookPresentationContext.getBookPresentationContext(request);
    MenuPresentationContext menu = MenuPresentationContext.getMenuPresentationContext(request);

    String menuContainerClass = MENU_CONTAINER_CLASS;
	String menuClass = MENU_CLASS;
    String menuItemClass = MENU_ITEM_CLASS;
    String menuItemActiveClass = MENU_ITEM_ACTIVE_CLASS;
    String menuItemLinkClass = MENU_ITEM_LINK_CLASS;

    if (book.isDesktopBook())
    {
		menuClass =             DESKTOP_MENU_CLASS;
        menuContainerClass =    DESKTOP_MENU_CONTAINER_CLASS;
        menuItemClass =         DESKTOP_MENU_ITEM_CLASS;
        menuItemActiveClass =   DESKTOP_MENU_ITEM_ACTIVE_CLASS;
        menuItemLinkClass =     DESKTOP_MENU_ITEM_LINK_CLASS;
    }

    List menuChildren = menu.getChildren();
%>

<render:beginRender> <%-- Begin Multi Level Menu --%>
<ul>
<%request.setAttribute(ROOT_FLAG_KEY, Boolean.TRUE); request.setAttribute(MENU_ITEM_KEY, book); request.setAttribute(MENU_CLASS_KEY, menuClass); request.setAttribute(MENU_ITEM_CLASS_KEY, menuItemClass); request.setAttribute(MENU_ITEM_ACTIVE_CLASS_KEY, menuItemActiveClass); request.setAttribute(MENU_ITEM_LINK_CLASS_KEY, menuItemLinkClass); %>
<jsp:include page="submenu.jsp"/><% request.removeAttribute(ROOT_FLAG_KEY); request.removeAttribute(MENU_ITEM_KEY); request.removeAttribute(MENU_CLASS_KEY); request.removeAttribute(MENU_ITEM_CLASS_KEY); request.removeAttribute(MENU_ITEM_ACTIVE_CLASS_KEY); request.removeAttribute(MENU_ITEM_LINK_CLASS_KEY); %>
</ul> </render:beginRender> <render:endRender> <HR> <%-- End Multi Level Menu --%> </render:endRender>
