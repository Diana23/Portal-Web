<jsp:root version="2.0" 
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:skeleton="http://www.bea.com/servers/portal/tags/netuix/skeleton"
>
    <jsp:directive.page isELIgnored="false" />
    
	<ul>
		<c:forEach items="${requestScope.sites}" var="site">
			<li id="${site.label}" class="arial11">
				<c:choose>
	            	<c:when test="${site.book}">
		                <a href="#" ><ins>&nbsp;</ins>
							<jsp:text>${site.title}</jsp:text>
						</a>
		                <c:set var="sites" value="${site.children}" scope="request"/>
		                <jsp:include page="sites2.jsp"/>
	            	</c:when>
	            	<c:otherwise>
	            		<a href="#" onclick="setLink('${site.label}');"><ins>&nbsp;</ins>
							<jsp:text>${site.title}</jsp:text>
						</a>
	            	</c:otherwise>
				</c:choose>
			</li>
			
		</c:forEach>
	</ul>
</jsp:root>