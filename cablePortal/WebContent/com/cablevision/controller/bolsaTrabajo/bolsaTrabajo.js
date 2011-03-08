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

function verifyEmail(email1){
	var status = false;     
	var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
     if (email1.search(emailRegEx) == -1) {
          status = false;
     }else {
          status = true;
     }
     return status;
}

function getRadioValue(name){
	var elementos = document.getElementsByName(name);
	var resultado = "";
	
	for(i=0;i<elementos.length;i++){
		if(elementos[i].checked){
			return elementos[i].value;
		}
	}
	return resultado;
}

//submit para los datos personales	        
function enviarDatosPersonales(scope){
	var edoCivilSelected = getRadioValue(lookupNameByTagId('edoSoltero',scope));						
	var statusEscolarSelected = getRadioValue(lookupNameByTagId('statusTit',scope));
	
	var htmlErrors = "";
	var txtNombre = $('#'+getJqId(lookupIdByTagId('actionForm_nombre',scope))).val();
	var txtPaterno = $('#'+getJqId(lookupIdByTagId('actionForm_paterno',scope))).val();
	var txtMaterno = $('#'+getJqId(lookupIdByTagId('actionForm_materno',scope))).val();
	var txtDireccion = $('#'+getJqId(lookupIdByTagId('actionForm_direccion',scope))).val();
	var txtTelefono = $('#'+getJqId(lookupIdByTagId('actionForm_telefono',scope))).val();
	var txtEmail = $('#'+getJqId(lookupIdByTagId('actionForm_email',scope))).val();
	var txtDiaNac = $('#'+getJqId(lookupIdByTagId('actionForm_diaNacimiento',scope))).val();
	var txtMesNac = $('#'+getJqId(lookupIdByTagId('actionForm_mesNacimiento',scope))).val();
	var txtAnioNac = $('#'+getJqId(lookupIdByTagId('actionForm_anioNacimiento',scope))).val();
	var txtEscolaridad = $('#'+getJqId(lookupIdByTagId('actionForm_escolaridad',scope))).val();
	
	//validaciones de campos vacios
	if(txtNombre=="" || txtNombre.length ==0 || trim(txtNombre)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El Nombre es requerido.</div><br>';
	}if(trim(txtPaterno) || txtPaterno=="" || txtPaterno.length==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El Apellido Paterno es requerido.</div><br>';
	}if(txtMaterno=="" || txtMaterno.length==0 || trim(txtMaterno)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El Apellido Materno es requerido.</div><br>';
	}if(txtDireccion=="" || txtDireccion.length==0 || trim(txtDireccion)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La Direcci&oacute;n es necesaria.</div><br>';
	}if(txtTelefono=="" || txtTelefono.length==0 || trim(txtTelefono)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El tel&eacute;fono es necesario.</div><br>';
	}if(txtEmail=="" || txtEmail.length==0 || trim(txtEmail)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El email es requerido.</div><br>';
	}if(txtDiaNac==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El d&iacute;a de nacimiento es requerido.</div><br>';
	}if(txtMesNac==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El mes de nacimiento es requerido.</div><br>';
	}if(txtAnioNac==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El a\u00F1o de nacimiento es requerido.</div><br>';
	}if(txtEscolaridad=="" || txtEscolaridad.length==0 || trim(txtEscolaridad)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La escolaridad es necesaria.</div><br>';
	}if(edoCivilSelected=="" || edoCivilSelected.length==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El estado civil es necesario.</div><br>';
	}if(statusEscolarSelected=="" || statusEscolarSelected.length==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El nivel de escolaridad es necesario.</div><br>';
	}

	if(txtEmail!="" && txtEmail.length!=0 && verifyEmail(txtEmail)==false){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El email tiene un formato inv\u00E1lido.</div><br>';
	}
	

	if(htmlErrors!=""){
		$("#"+getJqId(lookupIdByTagId('msg_error1',scope))+"").html(htmlErrors);
	}else{
		$("#"+getJqId(lookupIdByTagId('msg_error1',scope))+"").html("<br\><font size=\"1\" color=\"orange\">Espera un momento se esta verificando la informaci&oacute;n ...</font>");
		$("#"+getJqId(lookupIdByTagId('msg_error1',scope))).load(contextPath+"/com/cablevision/controller/bolsaTrabajo/actualizaDatosPersonales.do",{
			nombre: txtNombre,
			paterno: txtPaterno, 
			materno: txtMaterno,
			direccion: txtDireccion,
			edoCivl: edoCivilSelected,
			telefono: txtTelefono,
			email: txtEmail,
			diaNacimiento: txtDiaNac,
			mesNacimiento: txtMesNac,
			anioNacimiento: txtAnioNac,
			escolaridad: txtEscolaridad,
			statusEscolar: statusEscolarSelected
		});
	}
	return false;
}

//submit de la experiencia laboral
function enviarExpLaboral(scope){
	var htmlErrors = "";
	var txtEmp1 = $('#'+getJqId(lookupIdByTagId('actionForm_empresa1',scope))).val();
	var txtPuesto1 = $('#'+getJqId(lookupIdByTagId('actionForm_puesto1',scope))).val();
	var txtDiaInicio1 = $('#'+getJqId(lookupIdByTagId('actionForm_diaInicio1',scope))).val();
	var txtMesInicio1 = $('#'+getJqId(lookupIdByTagId('actionForm_mesInicio1',scope))).val();
	var txtAnioInicio1 = $('#'+getJqId(lookupIdByTagId('actionForm_anioInicio1',scope))).val();
	var txtDiaFin1 = $('#'+getJqId(lookupIdByTagId('actionForm_diaFin1',scope))).val();
	var txtMesFin1 = $('#'+getJqId(lookupIdByTagId('actionForm_mesFin1',scope))).val();
	var txtAnioFin1 = $('#'+getJqId(lookupIdByTagId('actionForm_anioFin1',scope))).val();
	var txtResponsabilidades1 = $('#'+getJqId(lookupIdByTagId('actionForm_responsabilidades1',scope))).val();
	
	var txtEmp2 = $('#'+getJqId(lookupIdByTagId('actionForm_empresa2',scope))).val();
	var txtPuesto2 = $('#'+getJqId(lookupIdByTagId('actionForm_puesto2',scope))).val();
	var txtDiaInicio2 = $('#'+getJqId(lookupIdByTagId('actionForm_diaInicio2',scope))).val();
	var txtMesInicio2 = $('#'+getJqId(lookupIdByTagId('actionForm_mesInicio2',scope))).val();
	var txtAnioInicio2 = $('#'+getJqId(lookupIdByTagId('actionForm_anioInicio2',scope))).val();
	var txtDiaFin2 = $('#'+getJqId(lookupIdByTagId('actionForm_diaFin2',scope))).val();
	var txtMesFin2 = $('#'+getJqId(lookupIdByTagId('actionForm_mesFin2',scope))).val();
	var txtAnioFin2 = $('#'+getJqId(lookupIdByTagId('actionForm_anioFin2',scope))).val();
	var txtResponsabilidades2 = $('#'+getJqId(lookupIdByTagId('actionForm_responsabilidades2',scope))).val();	
	
	
	//validaciones de campos vacios
	if(txtEmp1=="" || txtEmp1.length ==0 || trim(txtEmp1)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El nombre de la &uacute;ltima empresa es necesario.</div><br>';
	}if(txtPuesto1=="" || txtPuesto1.length ==0 || trim(txtPuesto1)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El puesto de la &uacute;ltima empresa es necesario.</div><br>';
	}if(txtDiaInicio1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El d&iacute;a de inicio del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtMesInicio1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El mes de inicio del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtAnioInicio1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El a\u00F1o de inicio del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtDiaFin1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El d&iacute;a de Terminaci&oacute;n del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtMesFin1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El mes de de Terminaci&oacute;n del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtAnioFin1==0){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El a\u00F1o de Terminaci&oacute;n del &uacute;ltimo empleo es requerido.</div><br>';
	}if(txtResponsabilidades1 =="" || txtResponsabilidades1.length==0 || trim(txtResponsabilidades1)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La descripci&oacute;n de responsabilidades en la &uacute;ltima empresa es necesaria.</div><br>';
	}
	//Validacion de fechas de Ultimo empleo
	var mesInicio1 = getMes(txtMesInicio1);
	var mesFin1 = getMes(txtMesFin1);
	//formato usa
	var dateIni1 = new Date(txtAnioInicio1, mesInicio1-1, txtDiaInicio1);
	var dateFin1 = new Date(txtAnioFin1, mesFin1-1, txtDiaFin1);
	if(dateIni1 > dateFin1){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La fecha de Inicio del &uacute;ltimo empleo debe ser menor a la fecha de Terminaci&oacute;n.</div><br>';
	}			

	if(txtEmp2!="" || txtPuesto2!="" || txtDiaInicio2!=0 || txtMesInicio2!=0 || txtAnioInicio2!=0 || txtDiaFin2!=0 || 
	   txtMesFin2!=0 || txtAnioFin2!=0 || txtResponsabilidades2 !=""){
	   //validaciones de campos vacios
		if(txtEmp2=="" || txtEmp2.length ==0 || trim(txtEmp2)){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El nombre de la empresa anterior es necesario.</div><br>';
		}if(txtPuesto2=="" || txtPuesto2.length ==0 || trim(txtPuesto2)){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El puesto de la empresa anterior es necesario.</div><br>';
		}if(txtDiaInicio2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El d&iacute;a de inicio del empleo anterior es requerido.</div><br>';
		}if(txtMesInicio2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El mes de inicio del empleo anterior es requerido.</div><br>';
		}if(txtAnioInicio2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El a\u00F1o de inicio del empleo anterior es requerido.</div><br>';
		}if(txtDiaFin2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El d&iacute;a de fin del empleo anterior es requerido.</div><br>';
		}if(txtMesFin2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El mes de fin del empleo anterior es requerido.</div><br>';
		}if(txtAnioFin2==0){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">El a\u00F1o de fin del empleo anterior es requerido.</div><br>';
		}if(txtResponsabilidades2 =="" || txtResponsabilidades2.length==0 || trim(txtResponsabilidades2)){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La descripci&oacute;n de responsabilidades en la empresa anterior es necesaria.</div><br>';
		}
		//Validacion de fechas de Empleo anterior
		var mesInicio2 = getMes(txtMesInicio2);
		var mesFin2 = getMes(txtMesFin2);
		//formato usa
		var dateIni2 = new Date(txtAnioInicio2, mesInicio2-1, txtDiaInicio2);
		var dateFin2 = new Date(txtAnioFin2, mesFin2-1, txtDiaFin2);
		
		if(dateIni2 > dateFin2){
			htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La fecha de Inicio del empleo anterior debe ser menor a la fecha de Terminaci&oacute;n.</div><br>';
		}
		
	}

	if(htmlErrors!=""){
		$("#"+getJqId(lookupIdByTagId('msg_error2',scope))+"").html(htmlErrors);
	}else{
		$("#"+getJqId(lookupIdByTagId('msg_error2',scope))+"").html("<br\><font size=\"1\" color=\"orange\">Espera un momento se esta verificando la informaci&oacute;n ...</font>");
		$("#"+getJqId(lookupIdByTagId('msg_error2',scope))).load(contextPath+"/com/cablevision/controller/bolsaTrabajo/actualizaExperienciaLaboral.do",{
			empresa1: txtEmp1,
			puesto1: txtPuesto1,
			diaInicio1: txtDiaInicio1,
			mesInicio1: txtMesInicio1,
			anioInicio1: txtAnioInicio1,
			diaFin1: txtDiaFin1,
			mesFin1: txtMesFin1,
			anioFin1: txtAnioFin1,
			responsabilidades1: txtResponsabilidades1,
			empresa2: txtEmp2,
			puesto2: txtPuesto2,
			diaInicio2: txtDiaInicio2,
			mesInicio2: txtMesInicio2,
			anioInicio2: txtAnioInicio2,
			diaFin2: txtDiaFin2,
			mesFin2: txtMesFin2,
			anioFin2: txtAnioFin2,
			responsabilidades2: txtResponsabilidades2
		});
	}
	
	return false;
}

function getMes(strMes){
	if (strMes == 'ENERO'){
		return '01';
	}
	if (strMes == 'FEBRERO'){
		return '02';
	}
	if (strMes == 'MARZO'){
		return '03';
	}
	if (strMes == 'ABRIL'){
		return '04';
	}
	if (strMes == 'MAYO'){
		return '05';
	}
	if (strMes == 'JUNIO'){
		return '06';
	}
	if (strMes == 'JULIO'){
		return '07';
	}
	if (strMes == 'AGOSTO'){
		return '08';
	}
	if (strMes == 'SEPTIEMBRE'){
		return '09';
	}
	if (strMes == 'OCTUBRE'){
		return '10';
	}
	if (strMes == 'NOVIEMBRE'){
		return '11';
	}
	if (strMes == 'DICIEMBRE'){
		return '12';
	}
}

function enviarInfoAdicional(scope){
	var htmlErrors = "";
	var txtIdioma1 = $('#'+getJqId(lookupIdByTagId('actionForm_idioma1',scope))).val();
	var txtIdioma2 = $('#'+getJqId(lookupIdByTagId('actionForm_idioma2',scope))).val();
	var txtIdioma3 = $('#'+getJqId(lookupIdByTagId('actionForm_idioma3',scope))).val();
	var nivelI1 = getRadioValue(lookupNameByTagId('nivelBasicoI1',scope));
	var nivelI2 = getRadioValue(lookupNameByTagId('nivelBasicoI2',scope));	
	var nivelI3 = getRadioValue(lookupNameByTagId('nivelBasicoI3',scope));	
	
	var txtPack1 = $('#'+getJqId(lookupIdByTagId('actionForm_paqueteria1',scope))).val();
	var txtPack2 = $('#'+getJqId(lookupIdByTagId('actionForm_paqueteria2',scope))).val();
	var txtPack3 = $('#'+getJqId(lookupIdByTagId('actionForm_paqueteria3',scope))).val();
	var nivelP1 = getRadioValue(lookupNameByTagId('nivelBasicoP1',scope));
	var nivelP2 = getRadioValue(lookupNameByTagId('nivelBasicoP2',scope));
	var nivelP3 = getRadioValue(lookupNameByTagId('nivelBasicoP3',scope));
	
	var txtMoneyMin = $('#'+getJqId(lookupIdByTagId('actionForm_moneyMin',scope))).val();
	var txtMoneyMax = $('#'+getJqId(lookupIdByTagId('actionForm_moneyMax',scope))).val();
	
	//validaciones de campos vacios
	if(txtIdioma1=="" || txtIdioma1.length ==0 || trim(txtIdioma1)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Al menos un idioma es necesario.</div><br>';
	}if(txtIdioma1!="" && (nivelI1=="" || nivelI1==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del primer idioma.</div><br>';
	}if(txtPack1 =="" || txtPack1.length==0 || trim(txtPack1)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Al menos un paquete es necesario.</div><br>';
	}if(txtPack1 !="" && (nivelP1 =="" || nivelP1.length==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del primer paquete.</div><br>';
	}if(txtMoneyMin =="" || txtMoneyMin.length==0 || trim(txtMoneyMin)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La pretensi&oacute;nn econ&oacute;nmica mensual m&iacute;nnima es necesaria.</div><br>';
	}if(txtMoneyMax =="" || txtMoneyMax.length==0 || trim(txtMoneyMax)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">La pretensi&oacute;nn econ&oacute;nmica mensual mayor es necesaria.</div><br>';
	}
	
	if((txtMoneyMin !="" && !trim(txtMoneyMin)) && (txtMoneyMax !="" && !trim(txtMoneyMax))){
		if(parseFloat(txtMoneyMin)>parseFloat(txtMoneyMax)){
			htmlErrors = htmlErrors + '<font size="1" color="Red">La pretensi&oacute;n econ&oacute;mica m&iacute;nima no debe ser mayor a la pretensi&oacute;n econ&oacute;mica mayor.</font><br>';
		}
	}
	
	
	//validaciones de seleccion de nivel obligatoria
	if((txtIdioma2!="" && !trim(txtIdioma2)) && (nivelI2=="" || nivelI2==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del segundo idioma.</div><br>';
	}if((txtIdioma3!="" && !trim(txtIdioma3)) && (nivelI3=="" || nivelI3==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del tercer idioma.</div><br>';
	}if(nivelI2!="" && (txtIdioma2=="" || txtIdioma2==0 || trim(txtIdioma2))){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el idioma para el segundo nivel.</div><br>';
	}if(nivelI3!="" && (txtIdioma3=="" || txtIdioma3==0 || trim(txtIdioma3))){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el idioma para el tercer nivel.</div><br>';
	}
	
	if((txtPack2!="" && !trim(txtPack2)) && (nivelP2=="" || nivelP2==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del segundo paquete.</div><br>';
	}if((txtPack3!="" && !trim(txtPack3)) && (nivelP3=="" || nivelP3==0)){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el nivel del tercer paquete.</div><br>';
	}if(nivelP2!="" && (txtPack2=="" || txtPack2==0 || trim(txtPack2))){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el paquete para el segundo nivel.</div><br>';
	}if(nivelP3!="" && (txtPack3=="" || txtPack3==0 || trim(txtPack3))){
		htmlErrors = htmlErrors + '<div class="error-msg" id="msgError">Debes seleccionar el paquete para el tercer nivel.</div><br>';
	}
	
	if(htmlErrors!=""){
		$("#"+getJqId(lookupIdByTagId('msg_error3',scope))+"").html(htmlErrors);
	}else{
		$("#"+getJqId(lookupIdByTagId('msg_error3',scope))+"").html("<br\><font size=\"1\" color=\"orange\">Espera un momento se esta verificando la informaci&oacute;n ...</font>");
		$("#"+getJqId(lookupIdByTagId('msg_error3',scope))).load(contextPath+"/com/cablevision/controller/bolsaTrabajo/actualizaInfoAdicional.do",{
			idioma1: txtIdioma1,
			nivelIdioma1: nivelI1,
			idioma2: txtIdioma2,
			nivelIdioma2: nivelI2,
			idioma3: txtIdioma3,
			nivelIdioma3: nivelI3,
			paqueteria1: txtPack1,
			nivelPaq1: nivelP1,
			paqueteria2: txtPack2,
			nivelPaq2: nivelP2,
			paqueteria3: txtPack3,
			nivelPaq3: nivelP3,
			moneyMin: txtMoneyMin,
			moneyMax: txtMoneyMax
		});
	}
	return false;
}

function postularseAvacante(scope,idVacante){
	//var idVacante = $('#'+getJqId(lookupIdByTagId('idVacante',scope))).val();
	
	
	if(idVacante!=""){
		$("#"+getJqId(lookupIdByTagId('respuesta',scope))).load(contextPath+"/com/cablevision/controller/bolsaTrabajo/postularse.do",{
			idVacante: idVacante
		});
	}
	return false;
}

		
function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}


/*funcion que quita los espacios en blanco de una cadena y regresa true si la cadena es vacia*/
function trim(str){
	str = str.replace(/^\s*|\s*$/g,"");
	if(str=="" || str.length ==0){
		return true;
	}
	return false;
}