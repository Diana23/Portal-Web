<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>

<link media="screen, projection" type="text/css" href="${pageContext.request.contextPath}/framework/skins/cablevision/js/css/ui.css" rel="stylesheet"></link>

<div class="wcs-marg">
	<netui:form action="setCarga" enctype="multipart/form-data" tagId="formaCarga">
		<h2 class="side ico-micta margin-bot15">Carga de archivo</h2>
		<br/>
		<br/>
		<div class="span-8 ">
			<label class="label-solos">Tipo de carga</label>
			<br/>
			<br/>
			<div class="text-side marg-rig15 margin-bot0">
				<netui:select dataSource="actionForm.tipo" optionsDataSource="${pageInput.tiposMap}" tagId="cbTipoCarga"/>
			</div>
			<br/>
			<label class="label-solos">Archivo</label>
			<br/>
			<br/>
			<div class="text-side marg-rig15 margin-bot0">
				<netui:fileUpload dataSource="actionForm.archivo" tagId="ufArchivo" size="36" accept=""/>
			</div>
			<br/>
			<a class="btn-small fright marg-rig15" title="Enviar" href="#" onclick="cargaValidate(); return false;">Enviar</a>
			<div class="upload-file-error" id="msg-area">
				${pageInput.ufmsg}
			</div>
		</div>
		<div class="clear"></div>
	</netui:form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery-1.4.2.min.js"></script>
<script>
$(document).ready(function(){
	if($('#msg-area').text().length>0){
		$('#msg-area').css("color","#0000FF");
	}
});

function cargaValidate(){
	if($('#cbTipoCarga').val()==null || $('#cbTipoCarga').val()==""){
		$('#msg-area').css("color","#FF0000");
		$('#msg-area').text("Debe seleccionar un tipo de carga");
		return false;
	}else if($('#ufArchivo').val()==null || $('#ufArchivo').val()==""){
		$('#msg-area').css("color","#FF0000");
		$('#msg-area').text("Debe seleccionar un archivo a cargar");
		return false;
	}else{
		$('#formaCarga').submit();
	}
}
</script>