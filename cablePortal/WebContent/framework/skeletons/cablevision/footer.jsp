<%@ page import="com.bea.netuix.servlets.controls.application.FooterPresentationContext"
%>
<%@ page session="false"%>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>
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
    	SiteMap map = SiteMapManager.getSiteMap(request,response,params.isReturnHidden());
    	request.setAttribute("siteMap",map);
	} catch(NullPointerException npe) {
    	npe.printStackTrace();
	}
	int count = 0;
</jsp:scriptlet>

<render:beginRender>
    <%-- Begin Body Footer --%>
    <jsp:directive.page session="false" />
    <jsp:directive.page isELIgnored="false" />
        	
        	<!-- Footer -->
			<div id="footer" class="dontIndex">
				<div class="container">
					<!-- sitemap -->
					<ul class="sitemap">
						<c:forEach items="${siteMap.sites}" var="site" >
							<c:if test="${not site.hidden}">
								<c:if test="${site.book}">
									<li>
									<a href="${ site.defaultPage }" title="${ site.title }" class="title-foot">${ site.title }</a>
									<c:forEach items="${site.children}" var="child">
										<c:if test="${not child.hidden}">
										<jsp:scriptlet>
											Site c = (Site)pageContext.getAttribute("child");
										</jsp:scriptlet>
											<a target="<jsp:expression>c.getTarget()</jsp:expression>" href="<jsp:expression>(c.isBook())?c.getDefaultPage():c.getUrl()</jsp:expression>" title="${ child.title }">${ child.title }</a>
										</c:if>
									</c:forEach>
									</li>  
								</c:if>
							</c:if>
						</c:forEach>
					</ul>
					
					<div class="clear"></div>
					
					<c:set var="contenidoId" value="CV004549" scope="request"/>
					<c:set var="estructuraId" value="CV001950" scope="request"/>
					<c:set var="templateId" value="CV001951" scope="request"/>
					<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
					
				</div>
			</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/thickbox-compressed.js"></script>
	<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>
	<script src="${pageContext.request.contextPath }/framework/skins/cablevision/js/funciones.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/framework/skins/cablevision/js/mousewheel.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var isCtrl=false;$(function(){$(document).keyup(function (e) {if(e.which == 17) isCtrl=false;}).keydown(function (e) {if(e.which == 17) isCtrl=true;if(e.which == 77 && isCtrl == true) {$(".contentEdit").toggle();return false;}});});$.ajaxSetup({cache: false});
		
			/* Slider Navs */			
			$(".slide-navs").jCarouselLite({
				btnNext: ".next-sli-nav",
				btnPrev: ".prev-sli-nav",
				visible: 3,
				circular: false,
				mouseWheel: true
			});
			
			var logoColor, negativeOrNot = $('#LogoCable').attr('rel');
			if(negativeOrNot != null){		
				if ( negativeOrNot != "BLANCO") { logoColor =  "logo" } else { logoColor =  "logo-blanco" }
				$('#logo').removeClass('logo-blanco').removeClass('logo').addClass(logoColor);
			}

	</script>
	
	<script type="text/javascript">

	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-15943617-1']);
	  _gaq.push(['_setDomainName', '.cablevision.net.mx']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	
	</script>
</render:beginRender>
<render:endRender>
    <%-- End Body Footer --%>
</render:endRender>
