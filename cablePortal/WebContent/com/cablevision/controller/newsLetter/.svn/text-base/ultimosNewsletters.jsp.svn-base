<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	int contador = 0;
	String css = "";
%>

<c:forEach items="${pageInput.fechaNoticias}" var="fecha">
	<%
		if(contador%2==0){
			css = "job-side-link";
		}else{
			css="job-side-link bk-grey";
		}
	contador++;
	%>
	<li class="<%=css%>">
		<a href="#">
			<input type="hidden" name="fechaCorta" value="${fecha}" />
			<netui:label value="${fecha}" style="text-transform:capitalize"><netui:formatDate pattern="MMMM yyyy" country="MX" language="es" /></netui:label>
		</a>
	</li>
</c:forEach>