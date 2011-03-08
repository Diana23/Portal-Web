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

//letras y numeros
function isAlphaNumericDash(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || key == 95 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57));
}

function redirectPage(){
	window.location = contextPath+"/com/cablevision/controller/bolsaTrabajo/actualizaDatosPersonales.do";
}