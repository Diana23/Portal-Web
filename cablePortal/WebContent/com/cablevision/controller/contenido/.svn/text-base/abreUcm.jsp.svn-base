<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<body>
		<netui-data:declareBundle name="conf" bundlePath="configuracion"/>
		<div style="display:none;">
		<form name="loginForm" action="/contentserver/idcplg?cookieLogin=1&IdcService=LOGIN" method="POST" id="loginForm">
			<input type="text" size="30" maxlength="50" name="username" value="<%=((String)session.getAttribute(com.cablevision.util.Constantes.SESSION_ACCOUNT_ID)) %>" /> 
			<input type="password" size="30" maxlength="50" name="password" value="<%=com.cablevision.util.Blowfish.desEncriptar((byte[])session.getAttribute(com.cablevision.util.Constantes.SESSION_MI_PASSWD),com.cablevision.util.Constantes.ENCRIPT_PASSWD)%>" />
			<input type="checkbox" name="persistent" value="1" /> 
			
			<c:choose>
				<c:when test="${empty pageInput.urlArchivo}">
					<c:set var="type" value="ucm.${param.type}.popup.url"/>
					<input type="hidden" name="redirecturl" value="${bundle.conf[pageScope.type]}"/>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="redirecturl" value="${pageInput.urlArchivo}"/>
				</c:otherwise>
			</c:choose>
			
			<input type="submit" name="edit" value="Login" />
		</form>
		</div>
		<script type="text/javascript">
			<%
				if(request.getParameter("CKEditorFuncNum")!= null){
					%>
						window.opener.setVariableEditor(<%=request.getParameter("CKEditorFuncNum")%>);
					<%
				}
				
			%>
			document.getElementById('loginForm').submit();
		</script>
	</body>
</html>