<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>
	
	<script type="text/javascript">
		var contextPath = "<%=request.getContextPath()%>";
		
		var divNoticias = "<netui:rewriteName name="divNoticias" forTagId="true" resultId="divNoticias"/>";
		var divUltimos = "<netui:rewriteName name="divUltimos" forTagId="true" resultId="divUltimos"/>";
		var divArchivo = "<netui:rewriteName name="divArchivo" forTagId="true" resultId="divArchivo"/>";
	</script>

	<div class="span-18 last">
			<!-- Noticias -->
			<div class="span-11 padding-rl20 ">
				<h2 class="title-noticias hidden-text">Noticias y Prensa</h2>
				<div id="<netui:rewriteName name="divNoticias" forTagId="true"/>">
					<jsp:include page="noticias.jsp"/>
				</div>
				<br><br>
			</div>
			<%-- 
			<div class="span-6 last">
				<ul>
					<li class="job-side-title margin-top79">Ordenar Por</li>
					<li class="job-side-link-wa bk-grey margin-bot15 ">
						<select  name="ordenNoticias" id="ordenNoticias" onchange="ordenarNoticias(this);">
							<option value="">...</option>
							<option value="noticia_fecha">FECHA</option>
							<option value="noticia_categoria">CATEGORÍA</option>
						</select>
					</li>
					
					
					<li class="job-side-title">Últimos Artículos</li>
					<div id="<netui:rewriteName name="divUltimos" forTagId="true"/>">
						<jsp:include page="ultimosArticulos.jsp"/>
					</div>
					
					<li class="job-side-title">Archivo</li>
					<div id="<netui:rewriteName name="divArchivo" forTagId="true"/>">
						<jsp:include page="archivo.jsp"/>
					</div>
				</ul>					
			</div>
			--%>
			<div class="clear"></div>
	</div>
	
</netui:scriptContainer>