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
    	if(request.getParameter("_pageLabel")!= null && !request.getParameter("_pageLabel").equals("")){
    		SiteMap map = SiteMapManager.getBreadcrumbSites(request,response,params.isReturnHidden(), request.getParameter("_pageLabel"));
        	request.setAttribute("breadcrumbSiteMap", map);	
    	}
	} catch(NullPointerException npe) {
    	npe.printStackTrace();
	}
</jsp:scriptlet>

	<div class="breadcumbs dontIndex">
		<div class="span-12" id="breadcrumbDiv">
		
			<jsp:scriptlet>
				if(request.getAttribute("breadcrumbSiteMap") != null) {
					Site currentPageSite = ((SiteMap)request.getAttribute("breadcrumbSiteMap")).getSites().next();
					Site parent = currentPageSite.getParent();
					
					
					if(currentPageSite.getUrl().equals(parent.getDefaultPage())){
						pageContext.setAttribute("currentSite", parent);
						parent = parent.getParent();
					}else{
						pageContext.setAttribute("currentSite", currentPageSite);
					}
					
					while(parent != null) {
						if(parent.getLabel().equalsIgnoreCase("cablevision_portal_book_1")) {
							parent.setTitle("Inicio");
						}
						if(!parent.isHidden() || (parent.getProperty("servicios_enlinea") != null && parent.getProperty("servicios_enlinea").equals("true"))) {
							pageContext.setAttribute("parent", parent);
			</jsp:scriptlet>
							<script type="text/javascript">
								$('#breadcrumbDiv').prepend('<a href="${parent.defaultPage}" title="${parent.title}">${parent.title}</a> &rsaquo;&nbsp;');
							</script>
			<jsp:scriptlet>
						}
						parent = parent.getParent();
					}
				}
			</jsp:scriptlet>
			<strong>${currentSite.title }</strong>
		</div>
		<div class="bread-icos">
			<a href="http://www.youtube.com/user/cablevisionmx" target="_blank" class="ico-bred-you hidden-text" title="YouTube">YouTube</a>
			<a href="http://twitter.com/CablevisionMX/" target="_blank" class="ico-bred-twi hidden-text" title="Twitter">Twitter</a>
			<a href="http://www.facebook.com/cablevisionmx" target="_blank" class="ico-bred-fac hidden-text" title="facebook">facebook</a>
			<!--<a href="#" class="ico-bred-mai hidden-text" title="Mail">Mail</a>-->
			<!--<a href="#" class="ico-bred-imp hidden-text" title="Imprimir">Imprimir</a>-->
		</div>
	</div>