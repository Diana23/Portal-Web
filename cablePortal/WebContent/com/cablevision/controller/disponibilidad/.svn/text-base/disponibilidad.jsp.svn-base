<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${!empty pageInput.listDisponibilidad && empty pageInput.error}">
		<span id="spanDisponibilidad" class="disp-ok">Ya está disponible la nueva red de CABLEVISION® en tu zona, llámanos al 51 699 696 para mayor información.</span>
	</c:when>
	<c:when test="${!empty pageInput.error}">
		<span id="spanDisponibilidad" class="disp-error-small">${pageInput.error}</span>
	</c:when>
	<c:otherwise>
		<span id="spanDisponibilidad" class="disp-error-small">Aún no está disponible la nueva red de CABLEVISION® en tu zona, seguimos trabajando para muy pronto ofrecerte todos nuestros beneficios. Llámanos al 51 699 696 si deseas más información.</span>
	</c:otherwise>
</c:choose>