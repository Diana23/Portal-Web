$(document).ready(function() {
	verificaCheckBox();
});

$(document).ready(function(){   
	tb_init('a.thickbox, area.thickbox, input.thickbox');//pass where to apply thickbox
	imgLoader = new Image();// preload image
	imgLoader.src = tb_pathToImage;
});

function verificaCheckBox(){
	var val = $('.check').attr('checked');
	if(val){
		$('#' + getJqId('${botonSiguiente}')).css('display', 'inline');
	}else{
		$('#' + getJqId('${botonSiguiente}')).css('display', 'none');
	}
}

function submitForma(formaName,link){
	href = link.href.replace("javascript:bea.wlp.disc.xie._Service.update('","");
	href = href.substring(0,href.length-3);
	if(bea&&bea.wlp&&bea.wlp.disc&&bea.wlp.disc.xie&&bea.wlp.disc.xie._Service){
		bea.wlp.disc.xie._Service.update(href,formaName);
	}else{
		for (var i=0; i<document.forms.length; i++) {
	     if (document.forms[i].id == formaName) {
	        document.forms[i].method = "POST";
	        document.forms[i].action = href;
	        document.forms[i].submit();
	     }
	   }
		
	}
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}