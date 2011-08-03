var idNoticia ="";
var campoOrden = "";
var campoFecha = "";
	
$(function(){
	
	/*$('#'+getJqId(divUltimos)+' a').live('click', function(){
		idNoticia = $(this).find('input[name="idNoticia"]').val();
		
		$.ajax({
			url: contextPath+'/com/cablevision/controller/noticias/getNoticias.do',
			data: { id: idNoticia, orden: campoOrden },
			success: function(data){
				$('#'+getJqId(divNoticias)).html(data);
			}
		});
	});*/
	
	$('#'+getJqId(divArchivo)+' a').live('click', function(){
		campoFecha = $(this).find('input[name=fechaCorta]').val();
		
		$.ajax({
			url: contextPath+'/com/cablevision/controller/noticias/getNoticias.do',
			data: { fecha: campoFecha, orden: campoOrden },
			success: function(data){
				$('#'+getJqId(divNoticias)).html(data);
			}
		});
	});
})


function ordenarNoticias(scope){
	campoOrden = document.getElementById("ordenNoticias").value;
	
	$.ajax({
		url: contextPath+'/com/cablevision/controller/noticias/getNoticias.do',
		data: { fecha: campoFecha, id: idNoticia, orden: campoOrden  },
		success: function(data){
			$('#'+getJqId(divNoticias)).html(data);
		}
	});
}

function goToFAQPage(page){
	var pNum;
	var cPage = parseInt($('#hdnCurrentPage').val());
	var nPages = parseInt($('#hdnNumPages').val());
	var pageLabel = $('#_pageLabel').val();
	
	if($(page).hasClass('faqpager-prev-page')){
		pNum = cPage-1;
	}else if($(page).hasClass('faqpager-next-page')){
		pNum = cPage+1;
	}else{
		 pNum = parseInt($(page).html());
	}
	
	if(isNaN(pNum) || pNum<=0 || pNum>nPages)
		return false;
	
	var start = (pNum-1)*parseInt($('#hdnMinRows').val());
	
	$.ajax({
		url: contextPath+'/com/cablevision/controller/noticias/getNoticias.do',
		data: { fecha: campoFecha, id: idNoticia, orden: campoOrden, start: start, _pageLabel:pageLabel },
		success: function(data){
			$('#'+getJqId(divNoticias)).html(data);
		}
	});
	
}


function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}