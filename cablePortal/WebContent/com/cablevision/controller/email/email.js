
$(function(){
	var usuarioForm = $('#user').val();
	if(usuarioForm != ''){
		$("#user").css({
			"background": "none"
		});
	}
});

function filtrarUsuario(){
	var usuario = document.getElementById("user").value;
	var password = document.getElementById("password").value;

	if(usuario==''){
		document.getElementById("msgError").innerHTML = 'Falta el usuario';
		document.getElementById("msgError").style.display = 'block';
		document.getElementById("msgErrorPwd").style.display = 'none';
		return false;
	}else if(password == ''){
		document.getElementById("msgError").style.display = 'none';
		document.getElementById("msgErrorPwd").style.display = 'block';
		document.getElementById("msgErrorPwd").innerHTML = 'La contrase&ntilde;a no es v&aacute;lida.';
		return false;
	}
	
	$.getJSON(urlHttps,
		{user: usuario,password:password},
		function(data){
			if(data.success){
				if(data.urlGmail != ''){
					window.parent.location = data.urlGmail;
					return false;
				}else{
					submitGmailForm(usuario);
				}
			}else{
				if(data.msg!=''){
					document.getElementById("msgErrorPwd").innerHTML = '';
					document.getElementById("msgError").innerHTML = data.msg;
					return false;
				}
			}
	});
	
	return false;
}
function submitGmailForm(usuario){
	document.getElementById("EmailGoogle").value=usuario;
	document.getElementById("gaia_loginform").submit();
}
