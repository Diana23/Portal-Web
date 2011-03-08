<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer idScope="${param._windowLabel}">
	<h2 class="title-cotizador hidden-text">Cotizador</h2>
				
		<netui-data:repeater dataSource="pageInput.services">
			<c:choose>
				<c:when test="${container.item.orden eq pageInput.ordenSelect}">
					<a href="#" title="Combo" id="<netui:rewriteName name="menu-cot" forTagId="true"/>${container.item.orden}" class="cs-nav${container.item.orden}-active hidden-text">
						${container.item.name}
						<input type="hidden" name="idService" value="${container.item.idService}" />
						<input type="hidden" name="orden" value="${container.item.orden}" />
					</a>
				</c:when>
				<c:otherwise>
					<a href="#" title="Combo" id="<netui:rewriteName name="menu-cot" forTagId="true"/>${container.item.orden}" class="cs-nav-${container.item.orden} hidden-text">
						${container.item.name}
						<input type="hidden" name="idService" value="${container.item.idService}" />
						<input type="hidden" name="orden" value="${container.item.orden}" />
					</a>
				</c:otherwise>
			</c:choose>	
		</netui-data:repeater>
</netui:scriptContainer>