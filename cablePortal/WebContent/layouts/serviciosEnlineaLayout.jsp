<jsp:root
version="2.0"
xmlns:jsp="http://java.sun.com/JSP/Page"
xmlns:c="http://java.sun.com/jsp/jstl/core"
xmlns:skeleton="http://www.bea.com/servers/portal/tags/netuix/skeleton"
xmlns:render="http://www.bea.com/servers/portal/tags/netuix/render"
xmlns:netuix="http://www.bea.com/servers/netuix/xsd/controls/netuix/1.0.0">
<jsp:directive.page import="com.bea.netuix.servlets.controls.page.BookPresentationContext"/>
<jsp:directive.page import="com.bea.netuix.servlets.controls.application.DesktopPresentationContext"/>

<jsp:directive.page session="false" />
<jsp:directive.page isELIgnored="false" />
<skeleton:context type="layoutpc">

	<div class="" id="backgroundContainer">
		<jsp:scriptlet>
			com.bea.netuix.servlets.controls.page.PagePresentationContext context = com.bea.netuix.servlets.controls.page.PagePresentationContext.getPagePresentationContext(request);
			String backgroundId = context.getProperty("backgroundId");
			
			if(org.apache.commons.lang.StringUtils.isNotEmpty(backgroundId)){
				pageContext.setAttribute("backgroundId",backgroundId);
		</jsp:scriptlet>
				<jsp:include page="/com/cablevision/controller/contenido/renderizarContenido.jsp?contenidoId=${backgroundId}&estructuraId=CV001993&templateId=CV001994"/>
		<jsp:scriptlet>
			}
		</jsp:scriptlet>
		<!-- Header -->
		<div class="container" id="header-int">
	
			<div class="span-24">
				<div class="span-24 last" id="logo-head">
					<jsp:include page="logoHead.jsp"/>
				</div>
							
				<!-- Navbar -->
				<div class="span-24 last" id="navbar">
					<jsp:include page="navbar_principal.jsp"/>
	
					<jsp:include page="navbar_breadcrumbs.jsp"/>
					
					<jsp:include page="navbar_hovers.jsp"/>
				</div><!-- End: Navbar -->
			
			</div>		
		</div> <!-- End: Header -->
	
	</div><!-- End: bk home -->

	<skeleton:control name="div" presentationContext="${layoutpc}" 
		presentationClass="margin-n-top80 container" presentationId="Content">
		<c:set var="ph" value="${layoutpc.placeholders}"/>
		<c:set var="menuServicios"  value="${ph[0]}"/>
		<c:set var="mainServiciosEnlinea" value="${ph[1]}"/>
			<div class="span-24">
            	<skeleton:child presentationContext="${menuServicios}"/>
            	<jsp:include page="/layouts/leftMenuServiciosEnlinea.jsp"/>
                <div class="span-18 last">
					<div class="wrap-cont-simple">
						<h2 class="servenlinea hidden-text">Portal de servicios en lÃÂ­nea</h2>
						<div class="wrap-srvline bord-toped-111">
							<skeleton:child presentationContext="${mainServiciosEnlinea}"/>
						</div>
					</div>
				</div>
			</div>
	</skeleton:control>
</skeleton:context>
</jsp:root>