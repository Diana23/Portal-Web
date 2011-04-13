<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>
<%@page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils"%>



<div id="user-info">
	<c:choose>
	<c:when test="${not empty _MI_CUENTA}">
		<jsp:scriptlet>
		RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String nombreCompleto = account.getNombreContacto();
		if(nombreCompleto != null){
			if(nombreCompleto.length()>=13)
				nombreCompleto = nombreCompleto.substring(0,13);
				nombreCompleto += "...";
		}
		
		String pageLabel = request.getParameter("pageLabel");
		if(pageLabel == null)
			pageLabel = request.getParameter("_pageLabel");
		
		pageContext.setAttribute("pageLabelParam",pageLabel);
		
		com.bea.portlet.PageURL miPerfil = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_inicio");
		miPerfil.setTemplate("https");
		miPerfil.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		miPerfil.setEncodeSession(false);
		pageContext.setAttribute("miPerfil",miPerfil.toString());
		
		com.bea.portlet.PageURL cablevision = com.bea.portlet.PageURL.createPageURL(request, response, "cablevision_portal_page_home");
		cablevision.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		cablevision.setEncodeSession(false);
		pageContext.setAttribute("cablevision",cablevision.toString());
		</jsp:scriptlet>
		<div class="span-7 dontIndex">	
			<a title="CABLEVISION&reg;" class="hidden-text logo" id="logo" href="${ cablevision }">Cablevision</a>
		</div>
		
		<div class="span-17 last">
		<div class="fright dontIndex" id="wrap-userlog">
			<ul class="bk-opacity">
				<li class="user-photo">
					<jsp:scriptlet>
						Object fotoObj = request.getSession().getAttribute("fotoUsuario");
						String fotoFilePath = "/contentserver/groups/mercadotecnia/documents/imagen_cv/cv004531.png";
						if(request.getSession().getAttribute("fotoUsuario") != null) {
							fotoFilePath = request.getContextPath() + "/archivosPerfil/fotos/" + (String)request.getSession().getAttribute("fotoUsuario");
						}
					</jsp:scriptlet>
					<img class="user" width="48" height="49" alt="<jsp:expression>nombreCompleto</jsp:expression>" src="<jsp:expression>fotoFilePath</jsp:expression>"/>
					</li>
					<li class="user-details">
						<span><jsp:expression>nombreCompleto</jsp:expression></span>
						<br/>
						&gt; <a title="Mi Perfil" href="${miPerfil}"> Mi Perfil </a>&gt;
						<netui:anchor title="Cerrar sesi&oacute;n" href='${pageContext.request.contextPath}/com/cablevision/controller/login/desconectar.do'>
							Cerrar sesi&oacute;n
							<netui:parameter name="pageLabel" value="${pageLabelParam}"/>
						</netui:anchor>
						<!-- <a title="Cerrar sesi&oacute;n" href='${pageContext.request.contextPath }/com/cablevision/controller/login/desconectar.do?pageLabel=<jsp:expression>pageLabel</jsp:expression>' > Cerrar sesi&oacute;n </a>-->
					</li>
					<li class="user-contact">
						<a title="" class="hidden-text" href="#">&nbsp;</a> 
						<a title="+" id="items-users" class="mas hidden-text" href="#">+</a>
					</li>
				</ul>
				<ul style="display: none;" class="wrap-user-items">
				<jsp:scriptlet>
					com.bea.portlet.PageURL misFacturas = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_factura");
					misFacturas.setTemplate("https");
					misFacturas.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					misFacturas.setEncodeSession(false);
					pageContext.setAttribute("misFacturas",misFacturas.toString());
					
					com.bea.portlet.PageURL misPagos = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_mis_pagos");
					misPagos.setTemplate("https");
					misPagos.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					misPagos.setEncodeSession(false);
					pageContext.setAttribute("misPagos",misPagos.toString());
					
					com.bea.portlet.PageURL realizarPago = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_pagar");
					realizarPago.setTemplate("https");
					realizarPago.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					realizarPago.setEncodeSession(false);
					pageContext.setAttribute("realizarPago",realizarPago.toString());
					
					com.bea.portlet.PageURL paperless = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_paperless_p1");
					paperless.setTemplate("https");
					paperless.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					paperless.setEncodeSession(false);
					pageContext.setAttribute("paperless",paperless.toString());
					
					com.bea.portlet.PageURL atnClientes = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_atencion_clientes");
					atnClientes.setTemplate("https");
					atnClientes.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					atnClientes.setEncodeSession(false);
					pageContext.setAttribute("atnClientes",atnClientes.toString());
					
					com.bea.portlet.PageURL solicitudes = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_missolicitudes");
					solicitudes.setTemplate("https");
					solicitudes.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
					solicitudes.setEncodeSession(false);
					pageContext.setAttribute("solicitudes",solicitudes.toString());
				</jsp:scriptlet>
					<li><a title="" href="${ misFacturas }">Mis Facturas</a></li>
					<li><a title="" href="${ misPagos }">Mis pagos</a></li>
					<li><a title="" href="${ realizarPago }">Realizar un pago</a></li>
					<li><a title="" href="${ paperless }">Paperless</a></li>
					<li><a title="" href="${ atnClientes }">Atenci&oacute;n a clientes</a></li>
					<li><a title="" href="${ solicitudes }">Solicitudes</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
	</c:when>
	<c:when test="${not empty _ACCOUNT_ID}">
		<jsp:scriptlet>
			String pageLabel = request.getParameter("pageLabel");
			if(pageLabel == null)
				pageLabel = request.getParameter("_pageLabel");
			if(pageLabel == null)
				pageLabel = ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel");
			pageContext.setAttribute("pageLabelParam",pageLabel);
			
			com.bea.portlet.PageURL cablevision = com.bea.portlet.PageURL.createPageURL(request, response, "cablevision_portal_page_home");
			cablevision.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			cablevision.setEncodeSession(false);
			pageContext.setAttribute("cablevision",cablevision.toString());
		</jsp:scriptlet>
		<div class="span-17">		
			<a title="CABLEVISION&reg;" class="hidden-text logo" id="logo" href="${ cablevision }">Cablevision</a>
		</div>
		<div class="span-7 last">
			<ul class="bk-opacity no-log">
				<li class="user-details">
					<li class="user-logins">
					<span class="fright">Bienvenido</span><br>
					<netui:anchor title="Cerrar sesi&oacute;n" styleClass="fright" href='${pageContext.request.contextPath }/com/cablevision/controller/login/desconectar.do'>
						<strong>Cerrar sesi&oacute;n </strong>
						<netui:parameter name="pageLabel" value="${pageLabelParam}"/>
					</netui:anchor>
					<!--<a title="Cerrar sesi&oacute;n" class="fright" href='${pageContext.request.contextPath }/com/cablevision/controller/login/desconectar.do?pageLabel=<jsp:expression>pageLabel</jsp:expression>'><strong>Cerrar sesi&oacute;n </strong></a>-->
				</li>
			</ul>
		</div>
	</c:when>
	<c:otherwise>
	<jsp:scriptlet>						
			com.bea.portlet.PageURL cablevision = com.bea.portlet.PageURL.createPageURL(request, response, "cablevision_portal_page_home");
			cablevision.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			cablevision.setEncodeSession(false);
			pageContext.setAttribute("cablevision",cablevision.toString());
		</jsp:scriptlet>
		<div class="span-17 dontIndex" >		
			<a title="CABLEVISION&reg;" class="hidden-text logo" id="logo" href="${ cablevision }">Cablevision</a>
		</div>
		<jsp:scriptlet>
			com.bea.portlet.PageURL registro = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_registro");
			registro.setTemplate("https");
			registro.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			registro.setEncodeSession(false);
			pageContext.setAttribute("registro",registro.toString());
		</jsp:scriptlet>
		<div class="span-7 last dontIndex">
			<ul class="bk-opacity no-log">
				<span class="color-orange" id="tit-login"><strong>Mi cuenta CABLEVISION &reg;</strong></span>
				<li class="user-logins">
					<span class="fright">&iquest;Ya eres miembro?</span><br>
					<!--<jsp:scriptlet>
						String pageLabel = request.getParameter("_pageLabel");
					</jsp:scriptlet> -->
					<netui:anchor title="Ingresa" styleClass="fright thickbox" href="${pageContext.request.contextPath}/com/cablevision/controller/login/showLogin.do?modal=true&amp;height=430&amp;width=520">
						<strong>Ingresa</strong>
						<netui:parameter name="pageLabel" value='<%= request.getParameter("_pageLabel") %>'/>
					</netui:anchor>
					<!-- <a title="Ingresa" class="fright thickbox" href="${pageContext.request.contextPath }/com/cablevision/controller/login/showLogin.do?modal=true&amp;height=430&amp;width=520&amp;pageLabel=<jsp:expression>pageLabel</jsp:expression>"><strong>Ingresa</strong></a>  -->
				</li>
				<li class="user-btn-reg">
					<a title="reg&iacute;strate" class="btn-registrate hidden-text" href="${registro }"> reg&iacute;strate </a>
				</li>
			</ul>
		</div>
	</c:otherwise>
	</c:choose>
</div>