<%@ page import="java.util.Iterator,
                 java.util.List,
                 com.bea.netuix.servlets.controls.page.BookPresentationContext,
                 com.bea.portlet.PageURL,
                 com.bea.netuix.servlets.controls.page.PagePresentationContext"%>
<%@ page session="false"%>
<%!
    static final String BOOK_PRESENTATION_CONTEXT_CLASS = BookPresentationContext.class.getName();
    static final String ROOT_FLAG_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".root-flag";
    static final String MENU_ITEM_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item";
    static final String MENU_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-class";
    static final String MENU_ITEM_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-class";
    static final String MENU_ITEM_ACTIVE_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-active-class";
    static final String MENU_ITEM_LINK_CLASS_KEY = BOOK_PRESENTATION_CONTEXT_CLASS + ".menu-item-link-class";
%>
<%
    Boolean isRoot = (Boolean) request.getAttribute(ROOT_FLAG_KEY);
    BookPresentationContext bookCtx = (BookPresentationContext) request.getAttribute(MENU_ITEM_KEY);
    String menuClass = (String) request.getAttribute(MENU_CLASS_KEY);
    String menuItemClass = (String) request.getAttribute(MENU_ITEM_CLASS_KEY);
    String menuItemActiveClass = (String) request.getAttribute(MENU_ITEM_ACTIVE_CLASS_KEY);
    String menuItemLinkClass = (String) request.getAttribute(MENU_ITEM_LINK_CLASS_KEY);
%>
<%
    if (!bookCtx.isHidden() && bookCtx.isVisible())
    {
        if (bookCtx instanceof BookPresentationContext)
        {
            List bookChildren = bookCtx.getPagePresentationContexts();
            Iterator it = bookChildren.iterator();

            while (it.hasNext())
            {
                PagePresentationContext childPageCtx = (PagePresentationContext) it.next();

                if (!childPageCtx.isHidden() && childPageCtx.isVisible())
                {
                    %><li class="<%= isRoot.booleanValue() && childPageCtx.isActive() ? menuItemActiveClass : menuItemClass %>"><%
                    %><a class="<%= menuItemLinkClass %>" href="<%= PageURL.createPageURL(request, response, childPageCtx.getDefinitionLabel()).toString() %>"><%= childPageCtx.getTitle() %></a><%

                    if (childPageCtx instanceof BookPresentationContext)
                    {
                        %><ul class="<%= menuClass %>"><%
                        request.setAttribute(ROOT_FLAG_KEY, Boolean.FALSE);
                        request.setAttribute(MENU_ITEM_KEY, childPageCtx);
                        %><jsp:include page="submenu.jsp"/><%
                        request.removeAttribute(ROOT_FLAG_KEY);
                        request.removeAttribute(MENU_ITEM_KEY);
                        %></ul><%
                    }

                    %></li><%
                }
            }
        }
    }
%>
