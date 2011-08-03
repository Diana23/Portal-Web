var campoBusqueda ="";
var campoOrden = "";
var campoFecha = "";
	
$(function(){
	
	
	
	$('#'+getJqId(newsLetterId)+' p.esCliente input').live('click',function(){
		var node = $(this);
		if(node.val() == 'NO'){
			$('#'+getJqId(noContratoId)).addClass('hidden');	
		}else{
			$('#'+getJqId(noContratoId)).removeClass('hidden');
		}
	});
	
	/*$('a.color-orange').live('click', function () {
		elementClick = '#anchorTarget';
		//elementClick = $(this).attr("href");
		destination = $(getJqId(elementClick)).offset().top;
		$("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination}, 1100 );
		return false;
	});*/
	
	$('a.busqueda').live('click', function(){
		campoBusqueda = $(this).parent().find('input[name=buscar]').val();
		
		$.ajax({
			url: contextPath+'/com/cablevision/controller/newsLetter/getNewsletters.do',
			data: {busqueda: campoBusqueda},
			success: function(data){
				$('#'+getJqId(divNoticias)).html(data);
			}
		});
		
		$.ajax({
			url: contextPath+'/com/cablevision/controller/newsLetter/getUltimosNewsletters.do',
			data: {busqueda: campoBusqueda},
			success: function(data){
				$('#'+getJqId(divMeses)).html(data);
			}
		});
		
	});
	
	$('#'+getJqId(divMeses)+' a').live('click', function(){
		campoFecha = $(this).find('input[name=fechaCorta]').val();
		
		$.ajax({
			url: contextPath+'/com/cablevision/controller/newsLetter/getNewsletters.do',
			data: {fecha: campoFecha, busqueda: campoBusqueda, orden: campoOrden },
			success: function(data){
				$('#'+getJqId(divNoticias)).html(data);
			}
		});
	});
})


function ordenar(scope){
	campoOrden = document.getElementById("ordenSelect").value;
	
	$.ajax({
		url: contextPath+'/com/cablevision/controller/newsLetter/getNewsletters.do',
		data: { fecha: campoFecha, busqueda: campoBusqueda, orden: campoOrden  },
		success: function(data){
			$('#'+getJqId(divNoticias)).html(data);
		}
	});
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}