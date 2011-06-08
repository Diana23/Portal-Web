<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>


<script src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery-1.4.2.min.js" type="text/javascript"></script>

<style>
.ppv-body {
	font-family: Tahoma, arial, verdana, serif;
	font-size: 11px;
	color: #787878;
	background: #fff;
}

h3.ppv {
	font-size: 23px;
	font-weight: bold;
}


p.date {
	margin: 0 0 0 0;
	font-family: Tahoma, arial, verdana, serif;
	font-size: 10px;
	color: #787878;
}

#wrap-img {
	background:
		url(${pageContext.request.contextPath }/resources/images/back-logo-lgbox.png);
	float: Left;
	width: 128px;
	height: 112px;
	margin: 0 5px 0 0;
}

.grid-content-ppv {
	margin: 7px 0 7px 43px;
}

.boxd {
	display: block;
	width: 600px;
	height: 32px;
	margin: 5px 0 25px 0;
}

.blacker {
	color: #2a2a2a;
}


</style>
<a href="#" onClick="self.parent.tb_remove();" title="cerrar">
	<img src="${pageContext.request.contextPath }/resources/images/close2.png" border="0" alt="X" style="float: right" />
</a>
<c:choose>
	<c:when test="${pageInput.detalle.canalLogo!= null && pageInput.detalle.canalLogo!= ''}">
		<div id="wrap-img"><img style="margin: 5px 0 0 5px;" src="${pageInput.detalle.canalLogo	 }" /></div>
	</c:when>
</c:choose>
<h3 class="color-orange grid-content">${pageInput.detalle.titulo }</h3>
<c:choose>
	<c:when test="${pageInput.detalle.canal!= null && pageInput.detalle.canal!= ''}">
		<p class="date grid-content"><span class="blacker">Canal:</span> ${pageInput.detalle.canal }</p>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${pageInput.detalle.categoria!= null && pageInput.detalle.categoria!= ''}">
		<p class="date grid-content"><span class="blacker">Categoría:</span> ${pageInput.detalle.categoria }</p>
	</c:when>
</c:choose>
<p class="boxd grid-content">
	${pageInput.detalle.descripcion }
</p>
<br><br>
<div align="center">

	<c:choose>
		<c:when test="${pageInput.detalle.categoria!= null && pageInput.detalle.categoria!= ''}">
			<p class="date grid-content-ppv"><span class="blacker">Categoría:</span> ${pageInput.detalle.categoria }</p>
		</c:when>
	</c:choose>
	<p class="boxd grid-content-ppv">
		<c:choose>
			<c:when test="${pageInput.detalle.descripcion != null && pageInput.detalle.descripcion != ''}">
				${pageInput.detalle.descripcion}
			</c:when>
		</c:choose>
	</p>
	
	<div align="center">
		<c:choose>
			<c:when test="${pageInput.detalle.ligaYoutube!=null and pageInput.detalle.ligaYoutube!=''}">
				<object style="margin-left: 5px;" width="500" height="300">
					<param name="movie" value="${pageInput.detalle.ligaYoutube }"></param>
					<param name="allowFullScreen" value="true"></param>
					<param name="allowscriptaccess" value="always"></param>
					<embed src="${pageInput.detalle.ligaYoutube }" type="application/x-shockwave-flash"
						allowscriptaccess="always" allowfullscreen="true" width="500" height="300"></embed>
				</object>
			</c:when>
			<c:otherwise>
				<c:if test="${pageInput.detalle.rutaImagen != null && pageInput.detalle.rutaImagen != ''}">
					<img src="${pageInput.detalle.rutaImagen}" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${!empty requestScope.ppv}">
		<div id="ppvDiv">
			<jsp:include page="ppv/horarios.jsp"></jsp:include>
		</div>
	</c:if>
</div>

<script>
		$('#TB_ajaxContent').css('height','535px');
		$('#TB_ajaxContent').css('overflow-y','auto');
		$('#TB_ajaxContent').css('width','600px');
		$('#TB_window').css('width','630px');
		
		$('#contLink').click(function(){
			var id = $('#idIni').val();
			var tipo = $('#tipoIni').val();
			
			if(!$('#eventos input.radio').is(':checked')){
				$('#msgError').html('Favor de seleccionar un Evento');
				$('#msgError').css('display','block');
				return false;
			}
			
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/verificarContratar.do?modal=true&amp;height=430&amp;width=520&id="+id+"&tipo="+tipo,
				data: $('#formPpv').serialize(), 
				beforeSend: function(objeto){
					$('#detalleDiv').html();
					$('#detalleDiv').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
				},
				dataType: "html",
				success: function(datos){
					$('#detalleDiv').html(datos);
				},
				type: "POST"
			});
			return false;	
		});
	
</script>
