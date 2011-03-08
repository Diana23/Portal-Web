<script type="text/javascript">
	$.ajax({
		url: "${pageContext.request.contextPath }/com/cablevision/controller/errorGenerico.jsp?height=300&width=400",
		beforeSend: function(objeto){
			$('.login-in-thbox').html();
			$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
		},
		dataType: "html",
		success: function(datos){
			$('#TB_window').css('display','none');
			$('#TB_window').css('margin-top',-170);
			$('#TB_window').css('margin-left',-215);
			$('#TB_window').css('width',430);
			$('#TB_window').css('display','block');
			$('#TB_ajaxContent').css('height',295);
			$('#TB_ajaxContent').css('width',400);
			$('#TB_ajaxContent').html(datos);
		},
		/* timeout: 1000, */
		type: "POST"
	});

</script>