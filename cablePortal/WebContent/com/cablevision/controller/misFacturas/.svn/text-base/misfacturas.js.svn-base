var frameError;
var mesGlobal;
var anioGlobal;
function mostrarFactura(scope){
	var mes = document.getElementById(lookupIdByTagId('mesSlct',scope)).value;
	var anio = document.getElementById(lookupIdByTagId('anioSlct',scope)).value;
	var anioServer = document.getElementById(lookupIdByTagId('anioServer',scope)).value;
	var mesServer = document.getElementById(lookupIdByTagId('mesServer',scope)).value;
	mesGlobal = mes;
	anioGlobal = anio;
	
	var d = new Date();
	var thisMonth = new Date(d.getFullYear(),d.getMonth(),1);
	var selectedMonth = new Date(anio,mes-1,1);
	
	if(mes!= "0" && anio != "0"){
		$("#"+getJqId(lookupIdByTagId('facturasPago',scope))).load(contextPath+"/com/cablevision/controller/misfacturas/muestraFacturasPago.do",{
			anio: anio,
			mes:mes
		});
		
		document.getElementById(lookupIdByTagId('facturaFrame',scope)).src = contextPath+'/com/cablevision/controller/misfacturas/muestraFactura.do?anio='+anio+"&mes="+mes;
		frameError = document.getElementById(lookupIdByTagId('facturaFrame',scope));
		frameError.setAttribute("height", "500")
		document.getElementById(lookupIdByTagId('facturaFrame',scope)).style.display = '';
		
		if(selectedMonth<=thisMonth){
			if(anioServer <= anio && mes-mesServer>=0) {
				document.getElementById(lookupIdByTagId('anioSlctTD',scope)).width = '';
				document.getElementById(lookupIdByTagId('anioSlctTD',scope)).style.width = 'auto';
				document.getElementById(lookupIdByTagId('btnImprimirFactura',scope)).style.display = '';
				return;
			}
		}
	}
	document.getElementById(lookupIdByTagId('anioSlctTD',scope)).width = '50%';
	document.getElementById(lookupIdByTagId('anioSlctTD',scope)).style.width = '50%';
	document.getElementById(lookupIdByTagId('btnImprimirFactura',scope)).style.display = 'none';
}

function reSize(){
	frameError.setAttribute("height", "70");
	frameError.style.display = '';
}

function getJqId(myid){ 
	return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
}

function facturaPopup() {
	var url = contextPath+'/com/cablevision/controller/misfacturas/imprimeFactura.do?anio='+anioGlobal+"&mes="+mesGlobal;
	window.open(url,'Factura','width=700,height=411,menubar=no,scrollbars=yes,toolbar=no,location=yes,directories=yes,resizable=yes,top=0,left=0');
}