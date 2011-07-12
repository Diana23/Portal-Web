<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="java.util.HashMap"/>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.Site"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMap"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMapManager"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.impl.SiteMapMetaParameters"/>
<jsp:directive.page import="com.cablevision.util.PageNewUrl"/>

<jsp:scriptlet>
   	SiteMapMetaParameters params = new SiteMapMetaParameters(true,false,false,"com.cablevision.portal.sitemap.impl.ContextSiteMapFactory");
   	request.setAttribute("site.map.factory",Class.forName(
			"com.cablevision.portal.sitemap.impl.ContextSiteMapFactory"
		).newInstance()
  	);
    try {
    	SiteMap map = SiteMapManager.getSiteMap(request,response,params.isReturnHidden());
    	request.setAttribute("siteMap",map);
	} catch(NullPointerException npe) {
    	npe.printStackTrace();
	}
	int count = 0;
	
	PageURL busqueda = PageURL.createPageURL(request, response, "busqueda");
	busqueda.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	busqueda.setEncodeSession(false);
	pageContext.setAttribute("busquedaUrl",busqueda.toString());
</jsp:scriptlet>

	
<form id="formBusqueda" action="${busquedaUrl }" method="get" >
	<input type="hidden" name="_nfpb" value="true">
	<input type="hidden" name="_portlet.async" value="false">
	<input type="hidden" name="_pageLabel" value="busqueda">
	<input type="hidden" name="_nfto" value="false">
	<input type="hidden" name="changeSearch" id="changeSearch" value="false">
	
	<ul class="wraps-bk-items dontIndex">
		<c:forEach items="${siteMap.sites}" var="site" >
			<c:if test="${not site.hidden}">
				<c:if test="${site.book}">
					<jsp:scriptlet> 
						Site v = (Site)pageContext.getAttribute("site");
					</jsp:scriptlet>
					<li class="bk-item-<jsp:expression>++count</jsp:expression>">
						<a href="${site.defaultPage}" 
							class='hidden-text <jsp:expression>v.getProperty("menuClass")</jsp:expression>' 
							title='${site.title}'>${site.title}
						</a>
					</li>
				</c:if>
			</c:if>
		</c:forEach>
		<li class="bk-item-7 bk-item-7-hover">
			<input name="busqueda" class="search-box" value="Buscar en" size="11" type="text"/>
			<input type="hidden" name="tipoBusqueda" id="tipoBusqueda" value=""/>
		</li>
		<li class="bk-item-8 bk-item-8-hover bk-item-r-hover"><a href="#" class="search-select" title="Todo el Sitio">Todo el Sitio</a></li>
		<li class="bk-item-9 bk-item-9-hover bk-item-r-hover"><a href="#" onclick="document.getElementById('formBusqueda').submit();return false;" class="search-btn hidden-text" title="Buscar">Buscar</a></li>					
	</ul>
</form>
