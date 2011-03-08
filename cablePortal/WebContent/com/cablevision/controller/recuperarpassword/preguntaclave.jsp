<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.apache.beehive.netui.pageflow.PageFlowUtils"%>
	
	<div class="wrap-img-thbk4">
		<a href="#" onClick="self.parent.tb_remove();" title="cerrar" class="close-cross"><img src="${pageContext.request.contextPath }/images/close.png" tppabs="${pageContext.request.contextPath }/images/close.png" border="0" alt="X" /></a>
	
		<br />
		<c:if test="${not empty pageInput.critico}">
			<br />
			${pageInput.critico}
			<br /><br /><br />
		</c:if>
		<c:if test="${empty pageInput.critico}">
		${pageInput.errores}
		<br />
			Pregunta: <br />
			<strong>${pageInput.pregunta}</strong><br /><br />
			
			<div class="text-side">
				<label for="user-box-respuesta">Respuesta: </label>
				<input type="text" name="user-box-respuesta" id="user-box-respuesta" value="" size="35" />		
			</div><br /><br />
			
		</c:if>
		
		<div class="clear"></div>		
		<div class="wrap-int-acc">
			<a href="#" id="volverolvidepass" title="Volver" class="color-orange fx-thislink-th">
				<strong>Regresar</strong>
			</a>
			<c:if test="${empty pageInput.critico}"> 
				<span> | </span> 
				<a href="#" class="btn-ctrt" id="ingth" title="Ingresar">Continuar</a></div>
			</c:if>
			
			<input type="hidden" id="valOrigen" value="${pageInput.origen}" />
	</div>
	
	<script>

		$('#volverolvidepass').click(function(){
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoclave.jsp",
				beforeSend: function(objeto){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
				},
				dataType: "html",
				success: function(datos){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html(datos);
				},
				/* timeout: 1000, */
				type: "GET"
			});

		})
		
		$('#ingth').click(function(){
			var valOrgn = $('#valOrigen').val();
			var valor = $('#user-box-respuesta').val();
			valor = $.trim(valor);			
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/recuperarPassword.do",
				data: {"respuestaCliente":valor, "valorOrigen":valOrgn},				 
				beforeSend: function(objeto){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
				},
				dataType: "html",
				success: function(datos){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html(datos);
				},
				/* timeout: 1000, */
				type: "GET"
			});

		})		

	</script>		