<div class="login-in-thbox">
	<div class="wrap-img-thbk2">
			<a href="#" onClick="self.parent.tb_remove();" title="cerrar" class="close-cross"><img src="${pageContext.request.contextPath }/images/close.png" tppabs="${pageContext.request.contextPath }/images/close.png" border="0" alt="X" /></a>
	
			<p>
				 Si olvidaste tu ID de usuario o contrase&ntilde;a selecciona alguna de las dos opciones: 
			</p>
			<br /><br />
			<a href="#" title="Olvido de Clave" id="forClave" class="btn-small bs-2-lines fleft">Recuperar<br>contrase&ntilde;a</a>
			<a href="#" title="Olvido de usuario" id="forUsuario" class="btn-small bs-2-lines fright">Recuperar<br>usuario</a>
	
			<div class="clear"></div>
		</div>
		
		<script>

		$('#forUsuario').click(function(){
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoUserForm.do",
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

		$('#forClave').click(function(){
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoClaveForm.do",
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
</div>