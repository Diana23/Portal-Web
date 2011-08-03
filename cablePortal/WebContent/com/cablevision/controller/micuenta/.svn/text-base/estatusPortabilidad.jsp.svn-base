<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${!empty pageInput.msg && empty pageInput.error}">
		${pageInput.msg}
	</c:when>
	<c:when test="${!empty pageInput.error}">
		<label class='disp-error-small'>${pageInput.error}</label>
	</c:when>
</c:choose>