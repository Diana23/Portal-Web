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
</jsp:scriptlet>

<!-- Sidebar: Accordion -->
<div class="span-6"> 

	<div class="head-ulsel">&nbsp;</div>
				
				<div class="cont-ulsel">
					<ul class="nav-serv-online">
						<c:forEach items="${siteMap.sites}" var="site" >
							<jsp:scriptlet> 
								Site site = (Site)pageContext.getAttribute("site");
								int count = 1;
							</jsp:scriptlet>
							<jsp:scriptlet>
								if(site.isBook() && site.getProperty("servicios_enlinea") != null && site.getProperty("servicios_enlinea").equals("true")) {
							</jsp:scriptlet>
								<c:forEach items="${site.children}" var="child">
									<jsp:scriptlet>
										Site c = (Site)pageContext.getAttribute("child");
										if(c.isHidden()){
											continue;	
										}
											String bgcolor = "";
											if(c.getProperty("selected")!= null && c.getProperties().getBoolean("selected")) {
												bgcolor = "item-sel-servonline";
											} else if(count % 2 == 0) {
												bgcolor = "dark-item";
											}
											count++;
									</jsp:scriptlet>
									<li>
										<a href='<jsp:expression>(c.isBook())?c.getDefaultPage():c.getUrl()</jsp:expression>' 
											class='<jsp:expression>bgcolor</jsp:expression>' 
											title="${child.title}">
												${child.title}
										</a>
									</li>
								</c:forEach>
							<jsp:scriptlet> } </jsp:scriptlet>
						</c:forEach>
						
						
					</ul>
					<div class="clear"></div>
				</div>
				
				<div class="foot-ulsel margin-bot40">&nbsp;</div>	

</div><!--End: Sidebar: Accordion -->