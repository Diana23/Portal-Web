function textLimit(obj,max){
	var result = true;
	if (obj.value.length >= max){
		var stripped = obj.value.substring(0, max);
		obj.value = stripped;
		result = false;
	}
	if (window.event)
		window.event.returnValue = result;
	return result;
}

function cargarSubCombo(scope){
	var contexto = document.getElementById(lookupIdByTagId('contexto',scope));
	var jsonrpc = new JSONRpcClient(contexto.value+"/JSON-RPC");
	var selectArea = document.getElementById(lookupIdByTagId('actionForm_area',scope));
	
	if(selectArea.value!=''){
		var motivos = jsonrpc.uploadCombos.getMotivos(selectArea.value);
		setDataToSelect('actionForm_motivo',motivos.list,scope);
	}
	
}

function setDataToSelect(id,data,scope){
	var o = document.getElementById(lookupIdByTagId(id,scope));
	o.options.length=0;
	if(data!=null){
		for(var i=0;i<data.length;i++){
			o.options[o.options.length]=new Option(data[i],data[i]);
		}
	}
}

function soloNumeros(evt){  
	evt = (evt) ? evt : event;   
	var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));    
	var respuesta = true;  
	  
	if (charCode > 31 && (charCode < 48 || charCode > 57))   {  
	    respuesta = false;  
	}  
	  
	return respuesta;  
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}