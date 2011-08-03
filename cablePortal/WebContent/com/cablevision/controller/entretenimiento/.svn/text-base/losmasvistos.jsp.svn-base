<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>

<c:set var="catvisto" scope="page" value=""/>
<c:set var="contador" scope="page" value="1"/>
<ul>
	<c:forEach items="${pageInput.masvistos}" var="masvisto">
		<c:if test="${catvisto != masvisto.masVistos}">
			<c:set var="catvisto" scope="page" value="${masvisto.masVistos}"/>
			<li class="od-side-title">${masvisto.masVistos}</li>
			<c:set var="contador" scope="page" value="1"/>
		</c:if>
		
		<c:url value="/com/cablevision/controller/entretenimiento/mostrarDetalle.do" var="urlDetalle">
			<c:param name="id" value="${masvisto.id }"/>
			<c:param name="tipo" value="${masvisto.tipo }"/>
		</c:url>
		
		<c:choose>
          <c:when test="${contador % 2 == 0}">
            <c:set var="rowClass" scope="page" value="od-side-link bk-grey"/>
          </c:when>
          <c:otherwise>
            <c:set var="rowClass" scope="page" value="od-side-link"/>
          </c:otherwise>
        </c:choose>
		
		<li class="${rowClass}">
			<strong>${contador}</strong>
			<a title="${masvisto.titulo}" class="thickbox" href="${urlDetalle}&TB_iframe=true&modal=true&height=540&width=600">
				<strong>${masvisto.titulo}</strong>
				<br/>
			</a>
			<span>${masvisto.categoria}</span>
		</li>
		
		<c:set var="contador" scope="page" value="${contador+1}"/>
		
	</c:forEach>
</ul>