<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<div class="login-in-thbox">
		<div class="wrap-img-thbk">
			<a href="#" onclick="self.parent.tb_remove();" title="cerrar" class="close-cross">
				<img src="${pageContext.request.contextPath }/images/close.png" alt="X" border="0">
			</a>
			
			<div class="error-msg">${pageInput.msg}</div>
			<div class="text-side">
				<label for="user-box-login">Usuario: </label>
				<input name="user-box-login" id="user-box-login" size="25" type="text"/>
			</div>
			<div class="text-side">
				<label for="pass-box-login">Clave: </label>
				<input name="pass-box-login" id="pass-box-login" value="" size="25" type="password"/>
			</div>
			
			<div class="span-4">
				<a href="#" id="ingresar" title="Ingresar" class="btn-small fleft">Ingresar</a>
			</div>
			<div class="span-3-fix last margin-top15">
				 <span class="color-orange">-</span> 
				 <jsp:scriptlet>
					String pageLabel = request.getParameter("_pageLabel");
				 </jsp:scriptlet>
				 <netui:anchor title="&iquest;Olvidaste tu Contrase&ntilde;a?" styleClass="thickbox thickbox2 color-orange" tagId="forget-pass" href="${pageContext.request.contextPath}/com/cablevision/controller/recuperarpassword/olvidoclave4sel.jsp?modal=true&amp;height=430&amp;width=520">
					 &iquest;Olvidaste tu Contrase&ntilde;a?
					 <netui:parameter name="pageLabel" value="${pageLabel}"/>
				</netui:anchor> 
				 <!--<a href="${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoclave4sel.jsp?modal=true&amp;height=430&amp;width=520&amp;pageLabel=<jsp:expression>pageLabel</jsp:expression>" title="&iquest;Olvidaste tu Contrase&ntilde;a?" class="thickbox thickbox2 color-orange" id="forget-pass">&iquest;Olvidaste tu Contrase&ntilde;a?</a>--><br>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	
	<script>
		if($("#TB_ajaxContent").length<1){
		  tb_init('a.thickbox');
		}
		
		$('#TB_ajaxContent').css('height','425px');
		$('#TB_ajaxContent').css('overflow-y','hidden');
		$('#TB_ajaxContent').css('width','520px');
		$('#TB_window').css('width','550px');
		$('#closeTabDiv').css('display','none');
		
		$('#ingresar').click(function(){
			
			var user = $('#user-box-login').val();
			user = $.trim(user);
			var pass = $('#pass-box-login').val();
			pass = $.trim(pass);
			
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/login/begin.do?modal=true&amp;height=430&amp;width=520",
				data:"usuario=" + user + "&password=" + pass + "&fromlogin=true", 
				beforeSend: function(objeto){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
				},
				dataType: "html",
				success: function(datos){
					if(datos.substring(0,4)=='http'){
						window.location = datos;
					}else{
						if(datos.substring(0,9)=='siguiente'){
							var tokens = datos.split( "," );
							$.ajax({
								url: tokens[1] ,
								beforeSend: function(objeto){
									$('.login-in-thbox').html();
									$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
								},
								dataType: "html",
								success: function(datos){
										$('.login-in-thbox').html();
										$('.login-in-thbox').html(datos);
										tb_init('a.thickbox2');
								},
								type: "POST"
							});
						}else{
							$('.login-in-thbox').html();
							$('.login-in-thbox').html(datos);
							tb_init('a.thickbox2');
						}
					}
				},
				/* timeout: 1000, */
				type: "POST"
			});
			
		});
	</script>
	
