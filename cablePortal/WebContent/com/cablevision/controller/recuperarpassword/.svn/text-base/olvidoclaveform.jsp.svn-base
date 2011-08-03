<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.cablevision.util.ConfigurationHelper"%>


<div class="wrap-img-thbk3">
    
    <a href="#" onClick="cierraOlvido();" title="cerrar" class="close-cross2">
        <img src="${pageContext.request.contextPath }/images/close.png" tppabs="${pageContext.request.contextPath }/images/close.png" border="0" alt="X" />
    </a>
    
    <div class="error-msg">
        <c:if test="${not empty pageInput.errores}">${pageInput.errores}</c:if>
    </div>
    <div class="text-side-sinmar">
        <label for="user-box-olvidoclave">Usuario: </label>
        <input type="text" name="user-box-olvidoclave" id="user-box-olvidoclave" size="25" value="${pageInput.idAccount}"/>
    </div>
    <script type="text/javascript">
    	var _tempRecaptcha;
    	var recaptchaKey = "<%=ConfigurationHelper.getPropiedad("recaptcha.key","null")%>"; 
    	
    	if(document.getElementById("recaptcha_widget")!=null){
    		_tempRecaptcha = document.getElementById("recaptcha_widget").innerHTML;
    		document.getElementById("recaptcha_widget").innerHTML = "";
    		Recaptcha.destroy();
    	}
    	
    	function cierraOlvido(){
    		if(document.getElementById("recaptcha_widget")!=null){
	    		//Restablece html del recaptcha de pagina principal
	    		document.getElementById("recaptcha_widget").innerHTML = _tempRecaptcha;
	    		Recaptcha.destroy();
	    		//Reinicia recaptcha
	        	Recaptcha.create(recaptchaKey,"recaptcha_widget", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget'});
    		}
    		//Cierra thickbox
    		self.parent.tb_remove();
    	}
        
        Recaptcha.create(recaptchaKey,"recaptcha_widget2", {theme: 'custom',lang : 'es', custom_theme_widget: 'recaptcha_widget2'});
    </script>
    <div class="wrap-captcha" id="recaptcha_widget2">
        <hr />
        <div class="captcha" id="recaptcha_image"></div>
        <br />
        
        <label for="captchaText" class="color-orange">Escribe ambas palabras:</label>
        <div class="text-side fix-top">
            <input type="text" id="recaptcha_response_field" name="recaptcha_response_field" value="" size="40" style="width:180px" />
        </div>
        <div>
            <a href="javascript:Recaptcha.reload();" title="Intentar otro" class="inteo-icon hidden-text">Intentar otro</a>
        </div>
        <div class="recaptcha_only_if_image">
            <a href="javascript:Recaptcha.switch_type('audio');" title="Discapacidad visual" class="discv-icon hidden-text" >Discapacidad visual</a>
        </div>
        <div class="recaptcha_only_if_audio">
            <a href="javascript:Recaptcha.switch_type('image');" title="Regresar a version de palabras" class="text-icon hidden-text" >Regresar a versión de palabras</a>
        </div>
        <div>
            <a href="javascript:Recaptcha.showhelp();" title="Ayuda" class="help-icon hidden-text">Ayuda</a>
        </div>
        
        
        <div class="clear"></div>
        <p class="captchaPar">La verificaci&oacute;n de c&oacute;digo nos permite confirmar que tu registro se realize de manera segura y confiable asi como para mantener la privacidad de tu informaci&oacute;n.</p>
    </div>
    
    <div class="clear"></div>        
    <div class="wrap-int-acc"><a href="#" id="volverolvidepass" title="Volver" class="color-orange fx-thislink-th"><strong>Regresar</strong></a> <span> | </span> <a href="#" class="btn-ctrt" id="ingth" title="Ingresar">Continuar</a></div>
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
            type: "POST"
        });

    })
    
    $('#ingth').click(function(){
        var valor = $('#user-box-olvidoclave').val();
        valor = $.trim(valor);
        var responseField = $('#recaptcha_response_field').val();
        var challengeField = $('#recaptcha_challenge_field').val();
        $.ajax({
            url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/enviar.do",
            data: "idAccount=" + valor+"&recaptcha_response_field=" + responseField + "&recaptcha_challenge_field=" + challengeField,
            beforeSend: function(objeto){
                $('.login-in-thbox').html();
                $('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
            },
            error: function(datos){
                self.parent.tb_remove();
            },
            dataType: "html",
            success: function(datos){
                $('.login-in-thbox').html();
                $('.login-in-thbox').html(datos);
            },
            /* timeout: 1000,*/
            type: "POST"
        });

    })        



</script>    