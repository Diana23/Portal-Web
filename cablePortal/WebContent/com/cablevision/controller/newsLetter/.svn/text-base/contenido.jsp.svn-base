<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>
	<div class="span-24 last">
		<!-- lista de noticias -->
		<div class="span-17 padding-rl20 " id="<netui:rewriteName name="divNoticias" forTagId="true"/>">   
			<jsp:include page="newsletters.jsp"/>
		</div>
		
		<!-- menu de busqueda lateral -->		
		<div class="span-6 last" id="<netui:rewriteName name="divLateral" forTagId="true"/>">
			<ul>
				<li class="job-side-title margin-top65">Buscar</li>
				<li class="job-side-link-wa bk-grey margin-bot15 ">
					<input type="text" name="buscar" value="">
					<a title="Buscar" href="#" class="busqueda">
						<img border="0" align="ABSMIDDLE" alt="Leer más" src="${pageContext.request.contextPath }/resources/images/logo_lupa.jpg">
					</a>
				</li>
				
				<li class="job-side-title">Ordenar por</li>
				<li class="job-side-link-wa bk-grey margin-bot15 ">
					<select id="ordenSelect" onchange="ordenar(this);" name="ordenSelect">
						<option value="">...</option>
						<option value="newsletter_fecha">FECHA</option>
						<option value="newsletter_titulo">NOMBRE</option>
					</select>
				</li>

				<li class="job-side-title">Últimos newsletters</li>
				
				<!-- div de ultimos newsletters -->
				<div id="<netui:rewriteName name="divMeses" forTagId="true"/>">
					<jsp:include page="ultimosNewsletters.jsp"/>
				</div>
				
				<li class="job-side-title">Preferencias</li>
				<li class="job-side-link bk-grey"><a title="Cambiar preferencias" href="#">Cambiar preferencias</a></li>
			</ul>						
		</div>
		<div class="clear"></div>
	</div>
</netui:scriptContainer>