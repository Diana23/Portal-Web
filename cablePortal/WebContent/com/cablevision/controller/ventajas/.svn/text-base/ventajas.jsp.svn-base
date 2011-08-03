<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery.limit-1.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/easing.1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/mousewheel.min.js"></script>

<div id="thisistop" class="span-18 last">
	<div class="wrap-cont-simple wcs-padd">
		

		<h2 class="side ico-produ padd-long-left">Testimoniales</h2>
		<br><br>
		<div class="clear"></div>	
		
		<div class="span-18 last">
			<c:set var="contenidoId" value="CV002514" scope="request"/>
		    <c:set var="estructuraId" value="CV002512" scope="request"/>
		    <c:set var="templateId" value="CV002513" scope="request"/>
		    <jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
				
			<div class="clear"></div>
			<div style="border-top: 1px solid rgb(204, 204, 204); font-size: 11px;" class="margen_queopinan span-17" id="opinion">
				<h2 class="side ico-micta margin-bot15">D&eacute;janos tu opini&oacute;n</h2>
				<c:choose>
					<c:when test="${!empty pageInput.success}">
						<div class="successMsg">
							<br><br><br>
							<h1 class="color-orange" style="text-align: center;"><strong>Gracias!</strong></h1>
							<h3 style="text-align: center;">Su mensaje ha sido enviado con &eacute;xito.</h3>
							<br><br><br><br><br><br><br>
							<script type="text/javascript">
					       		$(document).ready(function(){ 
									window.scrollTo(0, $('#opinion').position().top);
								});
					       	</script>
						</div>
					</c:when>
					<c:otherwise>
						<font size="2" color="Red">
					    	<c:if test="${!empty pageInput.errors}">
					       		${pageInput.errors}
					       		<script type="text/javascript">
						       		$(document).ready(function(){ 
										window.scrollTo(0, $('#opinion').position().top);
									});
						       	</script>
					       	</c:if>
					       	<%
					       	org.apache.struts.action.ActionMessages errors = org.apache.struts.util.RequestUtils.getActionMessages(pageContext, org.apache.struts.Globals.ERROR_KEY);
					       		if(errors!=null&&!errors.isEmpty()){
					       	%>
					       	<script type="text/javascript">
					       		$(document).ready(function(){ 
									window.scrollTo(0, $('#opinion').position().top);
								});
					       	</script>
					       	<%
					       		}
					       	%>
					       	<netui:errors/>
						</font>
						<netui:form style="color: rgb(153, 153, 153);" method="post" action="saveComment" enctype="multipart/form-data" styleClass="formulario">					
							<table width="640" cellspacing="0" cellpadding="0" border="0" align="center">
								<tbody><tr>
									<td width="320" colspan="2"> 
										<label for="nombre_y_apellido">Nombre y Apellido(*)</label><br>
										<netui:textBox dataSource="actionForm.nombreCompleto" style="width: 250px;" onKeyPress="return isAlpha(event)" onblur="removeBadChars(this)"/>
									</td>
									<td width="320" rowspan="2" colspan="2">
										<label for="testimonio">Testimonio(*)</label><br>
										<netui:textArea dataSource="actionForm.testimonio" style="width: 320px;" onKeyPress="return !validateValue(event, /^\°|\||\¬|\\%|\&|\/|\'|\\\|\*|\~|\[|\]|\{|\}|\^|\`|\<|\\>|\\u0022$/)" onblur="removeBadChars(this)"/>
									</td>
								</tr>
								<tr>
									<td width="120">
										<label for="antiguedad">Antig&uuml;edad(*)</label><br>
										<netui:textBox dataSource="actionForm.antiguedad" style="width: 60px;" onKeyPress="return isAlphaNumeric(event)" onblur="removeBadChars(this)"/>
									</td>
									<td width="200">
										<label for="producto">Producto(*)</label><br>
										<netui:textBox dataSource="actionForm.producto" style="width: 140px;" onKeyPress="return isAlphaNumeric(event)" onblur="removeBadChars(this)"/>
									</td>
								</tr>
								<tr>
									<td width="320" colspan="2">
										<label for="foto">Sube tu foto</label><br>
										<netui:fileUpload dataSource="actionForm.foto" size="28"/>
									</td>
									<td width="320" colspan="2">
										<label for="video">Sube tu video</label><br>
										<netui:fileUpload dataSource="actionForm.video" size="42"/>
									</td>
								</tr>
							</tbody></table>
	
							(*) Campos obligatorios
								<netui:anchor title="Enviar" value="Enviar" action="saveComment" formSubmit="true" styleClass="btn-small marg-rig40 margin-bot15 fright">
							        <netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
							        <netui:parameter name="_nfto" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_nfto") %>'/>
							    </netui:anchor>
								<br><br><br>
							</netui:form>
						</c:otherwise>
					</c:choose>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
	
<script type="text/javascript">
	var testimonios=new Array();
	$(document).ready(function(){
		$.ajax({
			url: "/contentserver/groups/mercadotecnia/documents/archivo_cv/cv004532.xml",
			dataType: ($.browser.msie) ? "text" : "xml",
			error: function(data){
				alert('Error occurred loading the XML');
			},
			success: function(data){
				var xml;
				if (typeof data == "string") {
					xml = new ActiveXObject("Microsoft.XMLDOM");
					xml.async = false;
					xml.loadXML(data);
				} else {
					xml = data;
				}
				parseXml(xml);
			}
		 });
		 
		function parseXml(xml) {
			var num = 1;
			var displayV = "block";
			$(xml).find("video").each(
				function() {
					$('#divVideoYT').append('<div id="video-yt-'+num+'" style="display:'+displayV+'" class="videosYoutubeHide"><object width="500" height="400"><param name="movie" value="http://www.youtube.com/v/'+$(this).find("codigoYT").text()+'?fs=1&amp;hl=es_ES"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/'+$(this).find("codigoYT").text()+'?fs=1&amp;hl=es_ES" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="500" height="400"></embed></object></noscript></div>');
					$('.video-thumbs2').append('<li><a title="'+$(this).find("nombre").text()+'" rel="'+num+'" id="thumb-yt" onclick="cambiarVideo(\''+num+'\');return false;"><div style="cursor:pointer; width: 80px; height: 60px; display: block; overflow: hidden; position: relative; padding: 15px;"><img src="http://img.youtube.com/vi/'+$(this).find("codigoYT").text()+'/2.jpg" alt="'+$(this).find("nombre").text()+' style="top: -10px;"></div></a></li>');
					
					testimonios[num]=new Array();
					testimonios[num][0] = $(this).find("nombre").text();
					testimonios[num][1] = $(this).find("antiguedad").text();
					testimonios[num][2] = $(this).find("producto").text();
					testimonios[num][3] = $(this).find("texto").text();
					if(displayV == "block") {
						$('#nombre_test').html(testimonios[num][0]);
						$('#ant_test').html(testimonios[num][1]);
						$('#prod_test').html(testimonios[num][2]);
						$('#text_test').html(testimonios[num][3]);
					}
					num++;
					displayV = "none";
				}
			);
			armarCarrousell();
		}
		$(this).delay(200);
		
		function armarCarrousell() {
			$("#thumbsYouTubeCarrusel").jCarouselLite({
				btnNext: 	"#sigVideo",
				btnPrev: 	"#antVideo",
				vertical: 	true,
				easing: 	"bouncein",
				mouseWheel: true,
				visible: 	4
			});
		}
	});
		
	function cambiarVideo(num){
		$('.videosYoutubeHide').css('display','none');
		$('#video-yt-'+num).css('display','block');
		$('#nombre_test').html(testimonios[num][0]);
		$('#ant_test').html(testimonios[num][1]);
		$('#prod_test').html(testimonios[num][2]);
		$('#text_test').html(testimonios[num][3]);
		return false;
	}
</script>
	
