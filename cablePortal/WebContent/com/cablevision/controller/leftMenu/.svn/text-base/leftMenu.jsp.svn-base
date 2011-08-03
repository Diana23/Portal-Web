<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.Site"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMap"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMapManager"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.impl.SiteMapMetaParameters"/>

<jsp:scriptlet>
   	SiteMapMetaParameters params = new SiteMapMetaParameters(true,false,false,"com.cablevision.portal.sitemap.impl.ContextSiteMapFactory");
   	request.setAttribute("site.map.factory",Class.forName(
			"com.cablevision.portal.sitemap.impl.ContextSiteMapFactory"
		).newInstance()
  	);
    try {
    	SiteMap map = SiteMapManager.getSiteMapFromCurrentPage(request,response,params.isReturnHidden(), request.getParameter("_pageLabel"));
    	request.setAttribute("siteMap",map);
	} catch(NullPointerException npe) {
    	npe.printStackTrace();
	}
	int count = 0;
</jsp:scriptlet>

<!-- Sidebar: Accordion -->
<div class="span-6 dontIndex"> 
	<ul class="accor-side">
	
		<c:forEach items="${siteMap.sites}" var="site" >
			<jsp:scriptlet> 
				Site site = (Site)pageContext.getAttribute("site");
				boolean haveOnePage = false;
				haveOnePage = (site.isBook() && site.getChildrenSize() == 1);
				String url = "#";
				String addToId = "-lastPage";
				if(site.isBook()) {
					url = site.getDefaultPage();
					if(!haveOnePage) {
						addToId = "";
					} 	
				} else 
					url = site.getUrl();
				
			</jsp:scriptlet>
			<c:if test="${not site.hidden}">
				<li>
					<a href='<jsp:expression>url</jsp:expression>' 
						class='<jsp:expression>(site.getProperty("selected")!= null && site.getProperties().getBoolean("selected"))?"menu-acc-sel":"menu-acc"</jsp:expression>' 
						id='${site.label}<jsp:expression>addToId</jsp:expression>' 
						target='<jsp:expression>site.getTarget()</jsp:expression>' 
						title="${site.title}">
							<jsp:scriptlet> 
								if(site.getProperty("selected")!= null && site.getProperties().getBoolean("selected")) {
							</jsp:scriptlet>
								<strong>${site.title}</strong>
							<jsp:scriptlet>
								} else {
							</jsp:scriptlet>
								${site.title}
							<jsp:scriptlet>
								}
							</jsp:scriptlet>
					</a>
					<c:if test="${site.book}">
						<div style='display:<jsp:expression>(site.getProperty("selected")!= null && site.getProperties().getBoolean("selected"))?"block;":"none;"</jsp:expression>' class="acc-cont">
							<c:forEach items="${site.children}" var="child">
								<c:if test="${not child.hidden}">
									<jsp:scriptlet>
										Site c = (Site)pageContext.getAttribute("child");
									</jsp:scriptlet>
									<a href='<jsp:expression>(c.isBook())?c.getDefaultPage():c.getUrl()</jsp:expression>'
										title="${child.title}" 
										class='<jsp:expression>(c.getProperty("selected")!= null && c.getProperties().getBoolean("selected"))?"a-select":"a-item"</jsp:expression>'>
											${child.title}
									</a>
								</c:if>
							</c:forEach>
						</div>
					</c:if>
				</li>
			</c:if>
		</c:forEach>
		
	</ul>
</div><!--End: Sidebar: Accordion -->