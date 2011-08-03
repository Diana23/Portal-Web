<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	int contador = 0;
	String css = "";
%>

<c:forEach items="${pageInput.noticias}" var="noticia">
	<%
		if(contador%2==0){
			css = "job-side-link-2l";
		}else{
			css="job-side-link-2l bk-grey";
		}
	contador++;
	%>
	<li class="<%=css%>">
		<a title="${noticia.noticia_titulo}" href="#">
			<input type="hidden" name="idNoticia" value="${noticia.id}" />
			${noticia.noticia_titulo}
		</a>
	</li>
</c:forEach>
			