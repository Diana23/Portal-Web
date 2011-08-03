<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.Site"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMap"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.SiteMapManager"/>
<jsp:directive.page import="com.cablevision.portal.sitemap.impl.SiteMapMetaParameters"/>

	<!-- Menu desplegable : Hovers-->
	<div id="wrap-despl" class="wrap-despl dontIndex" style="display: none;">
		<div class="desple-head">&nbsp;</div>
		
		<jsp:scriptlet>
			int siteCount = 0;
		</jsp:scriptlet>
		<div class="desple-cont">
			<c:forEach items="${siteMap.sites}" var="site" >
				<c:if test="${not site.hidden}">
					<c:if test="${site.book}">
						<jsp:scriptlet> 
							Site v = (Site)pageContext.getAttribute("site");
							int childrenSize = v.getChildrenSize();
							boolean special = false;
							int columns = 4;
							int count = 0;
							boolean wasHidden = true;
							siteCount++;
						</jsp:scriptlet>
						<ul id='<jsp:expression>v.getProperty("menuClass")</jsp:expression>' style="display: none;">
							<li id='column-li-<jsp:expression>siteCount+"-"</jsp:expression>1'></li>
							<li id='column-li-<jsp:expression>siteCount+"-"</jsp:expression>2'></li>
							<li id='column-li-<jsp:expression>siteCount+"-"</jsp:expression>3'></li>
							<li id='column-li-<jsp:expression>siteCount+"-"</jsp:expression>4'></li>
							<c:forEach items="${site.children}" var="child">
								<c:if test="${not child.hidden}">
									<jsp:scriptlet>
										Site c = (Site)pageContext.getAttribute("child");
										special = c.getProperty("submenuClass") != null;
										if(count == columns) { count = 0; }
										wasHidden = false;
									</jsp:scriptlet>
									<script type="text/javascript">
										$('#column-li-<jsp:expression>siteCount+"-"+(++count)</jsp:expression>').append('<a target="<jsp:expression>c.getTarget()</jsp:expression>" href="<jsp:expression>(c.isBook())?c.getDefaultPage():c.getUrl()</jsp:expression>"' +"class='<jsp:expression>(c.getProperties() != null && c.getProperty("submenuClass") != null)?c.getProperty("submenuClass"):"bullet"</jsp:expression>'" + 'title="${child.title}">' +'	${child.title}' +'</a>');
									</script>
								</c:if>
							</c:forEach>
							<jsp:scriptlet>
								if(!wasHidden) {
									for( ; count < columns; ) {
							</jsp:scriptlet>
										<script type="text/javascript">$('#column-li-<jsp:expression>siteCount+"-"+(++count)</jsp:expression>').append('<a href="#" title="&nbsp;">&nbsp;</a>');</script>
							<jsp:scriptlet>
									}
									if(childrenSize <= columns && count >= columns && !special) {
										count = 0;
										for( ; count < columns; ) {
							</jsp:scriptlet>
										<script type="text/javascript">$('#column-li-<jsp:expression>siteCount+"-"+(++count)</jsp:expression>').append('<a href="#">&nbsp;</a>');</script>
							<jsp:scriptlet>
										}
									}
									special = false; 
									wasHidden = true;
								}
							</jsp:scriptlet>
						</ul>
					</c:if>
				</c:if>
			</c:forEach>
			<div class="clear"></div>						
		</div>
		<div class="clear"></div>
		<div class="desple-foot">&nbsp;</div>
	</div><!-- End: Menu desplegable : Hovers-->