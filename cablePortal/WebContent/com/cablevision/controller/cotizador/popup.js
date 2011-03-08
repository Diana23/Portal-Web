//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup 
function loadPopup(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0",
			"background":"#000000",
			"display": "block",
			"position":"fixed",
			"top":"0",
			"width":"100%",
			"z-index":"1"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContact").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup 
function disablePopup(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContact").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopup(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContact").height();
	var popupWidth = $("#popupContact").width();
	//centering
	$("#popupContact").css({
		"z-index":"2", 
		"position": "fixed",
		"top": windowHeight/2-popupHeight/2+"px",
		"left": windowWidth/2-popupWidth/2+"px"
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight+"px"
	});
	
}

function showPopup(){
	//centering with css
	centerPopup();
	//load popup
	loadPopup();
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}


//validacion de formulario
//letras y espacios 164 209
function isAlpha(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122));
}

//letras y numeros
function isAlphaNumeric(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57));
}

//numeros
function isNumeric(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	return (key <= 13 || key == 32 || (key >= 48 && key <= 57));
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}