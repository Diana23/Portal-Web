<jsp:root
version="2.0"
xmlns:jsp="http://java.sun.com/JSP/Page"
xmlns:c="http://java.sun.com/jsp/jstl/core"
xmlns:skeleton="http://www.bea.com/servers/portal/tags/netuix/skeleton"
xmlns:render="http://www.bea.com/servers/portal/tags/netuix/render"
xmlns:netuix="http://www.bea.com/servers/netuix/xsd/controls/netuix/1.0.0" >
<jsp:directive.page import="com.bea.netuix.servlets.controls.page.BookPresentationContext"/>
<jsp:directive.page import="com.bea.netuix.servlets.controls.application.DesktopPresentationContext"/>

<jsp:directive.page session="false" />
<jsp:directive.page isELIgnored="false" />
<skeleton:context type="layoutpc">
	
	<!-- Background Navs -->
	<jsp:include page="/com/cablevision/controller/contenido/renderizarContenido.jsp?contenidoId=CV002114&estructuraId=CV002115&templateId=CV002116" flush="true"/>
	
	<div class="cont-head-bk">	
		<div class="container">

			<a href="#" class="head-prev hidden-text" title="Anterior"> Anterior </a>
			<a href="#" class="head-next hidden-text" title="Siguiente"> Siguiente</a>
		</div>	
	</div>

	<!-- Header -->
	<div class="container" id="header">
		<div class="span-24">

			<div class="span-24 last" id="logo-head">
			
				<jsp:include page="logoHead.jsp"/>
						
				<div class="span-12">&nbsp;</div>
				
				<div class="span-3 header-cont color-normal" rel="color-normal" id="head1" align="center" >
					<br /><br /><br /><br />
					<a href="#" id="btn-link-ruleta" title="Ver ahora" class="btn-small">Ver ahora</a>
				</div>

				<!-- slider navs -->
				<jsp:include page="/com/cablevision/controller/contenido/renderizarContenido.jsp?contenidoId=CV002114&estructuraId=CV002115&templateId=CV002117" flush="true"/>			
			
			</div>
			
			<!-- Navbar -->
			<div id="navBarHome">
				<div class="span-24 last" id="navbar">
					<jsp:include page="navbar_principal.jsp"/>
					
					<div class="breadcumbs-index">&nbsp;</div>
					
					<jsp:include page="navbar_hovers.jsp"/>
				</div>
			</div><!-- End: Navbar -->
		
		</div>		
	</div> <!-- End: Header -->
	
	<skeleton:control name="div" presentationContext="${layoutpc}" 
		presentationClass="container" presentationId="Content">
		<c:set var="ph" value="${layoutpc.placeholders}"/>
		<c:set var="ph1" value="${ph[0]}"/>
		<c:set var="ph2" value="${ph[1]}"/>
		<c:set var="ph3" value="${ph[2]}"/>
		<c:set var="ph4" value="${ph[3]}"/>

		<!-- slider navs -->
		<div class="wraps-slide-navs">
			<div class="slide-navs">
				<jsp:include page="/com/cablevision/controller/contenido/renderizarContenido.jsp?contenidoId=CV002114&estructuraId=CV002115&templateId=CV002117" flush="true"/>			
			</div>
			<a href="#" class="prev-sli-nav hidden-text">Previous</a>
			<a href="#" class="selected_play play-sli-nav hidden-text">Play</a>
            <a href="#" class="pause-sli-nav hidden-text">Pause</a>
			<a href="#" class="next-sli-nav hidden-text">Next</a>
			<span style="clear:both;"></span>
				<script type="text/javascript">		
					$('.pause-sli-nav').click(function(){
					if($('#preloader').is(':animated')){
						$('#preloader').stop();
						$(this).addClass('selected_pause');
						$('a.play-sli-nav').removeClass('selected_play');
						return false;
					}
					});
				
					$('.play-sli-nav').click(function(){
						var width=$('#preloader').width();
						var time = 8200-(width*3.5);
				
						if($('#preloader').is(':animated')){
							return false;
						}else{
							$('#preloader').animate({			
							width: '1200px' 
							}, time, function() {
							changeHead(activeNav); 			
							});
						}
						$(this).addClass('selected_play');
						$('a.pause-sli-nav').removeClass('selected_pause');
						return false;	
					});
				</script>
			
		</div>	
			<div class="span-24" style="margin-top:-88px">

				<!-- Wrap Main -->	
				<div class="margin-bot15">
					<!-- Main -->	
					<div class="span-18">
						<skeleton:child presentationContext="${ph1}"/>
					</div>
					<!-- Sidebar -->
					<div class="span-6 last">
						<skeleton:child presentationContext="${ph2}"/>
					</div><!-- End: Sidebar -->
				</div><!-- End: Main -->
				<div class="clear"></div>
				<!-- Comunidad Cablevisi&oacute;n -->
				<div class="span-24 last">
					<skeleton:child presentationContext="${ph3}"/>
					<skeleton:child presentationContext="${ph4}"/>
				</div>
			</div>
	</skeleton:control>
</skeleton:context>
</jsp:root>