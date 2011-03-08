var editorFuncName;
var scopeGuardado;

function saveXmlData(salirAfterSave){
	parent.showLoading(true);
	var evalue = document.getElementById("editores").value;
    if (evalue.replace(/^\s+|\s+$/g, "") != ""){
        var editores = evalue.split(",");
        for (i=0;i<editores.length;i++){
            $(editores[i]).value = CKEDITOR.instances[editores[i]].getData();
        }
    }
    var url = contextPath+"/com/cablevision/controller/contenido/saveContenido.do";
    new Ajax.Request(url,
        {   parameters : Form.serialize($("formaSave")),
            onComplete:function (resp){
                parent.showLoading(false);
                if (salirAfterSave){
                    window.opener.document.getElementById('pageContent').innerHTML = resp.responseText;
                    window.close();
                }
            },
            onFailure: function (resp){
                alert('Error al guardar los datos...');
            }
        });
}

function deleteContent(contenidoId, urlFinal){
	if(confirm("\u00BF Desea borrar este contenido ?")){
		$.ajax({
			  url: contextPath+'/com/cablevision/controller/contenido/deleteContenido.do',
			  data: {contenidoId: contenidoId},
			  success: function(data) {
			    document.location.href=urlFinal;
			  }
			});
	}
}

function previewXmlData(){
	document.getElementById("formaSave").submit();
}

function exeComando(comando){
    $("__Comando__").value = comando;
    cursor_wait();
    var evalue = document.getElementById("editores").value;
    if (evalue.replace(/^\s+|\s+$/g, "") != ""){
        var editores = evalue.split(",");
        for (i=0;i<editores.length;i++){
            $(editores[i]).value = CKEDITOR.instances[editores[i]].getData();
        }
    }
  	
    var url = contextPath+"/com/cablevision/controller/contenido/getHtml.do";
    new Ajax.Updater('htmlOfXml', url,
        {   parameters : Form.serialize($("formaSave")),
        	evalScripts:true,
            onComplete:function (resp){
                cursor_clear();
            },
            onFailure: function (resp){
                alert('Error al guardar los datos...');
                cursor_clear();
            }
        });
}

function showToolBarXml(a, show, nombre){
    if (show){
        var nomToolBar = "div"+nombre;
        var posX = findPosX(a);
        var posY = findPosY(a);
        $("toolBarXml").innerHTML = $(nomToolBar).innerHTML;
        $("toolBarXml").style.left = (posX-80)+"px";
        $("toolBarXml").style.top = (posY+15)+"px";
        $("toolBarXml").style.display = 'block';
        $("__Elemento__").value = nombre;
        
    }else $("toolBarXml").style.display = 'none';
}
   
function setTimeToolBarXml(setTimer){
    if (setTimer) {
        buttonTimer = setTimeout("showToolBarXml(null,false)", 1500);
    }else{
        if (buttonTimer!=null)
            clearTimeout(buttonTimer);
    }
}

function openExplorer(id, tipo, idArchivo){
	editorFuncName=null;
	document.getElementById('__idText__').value=id;
	popupURL=contextPath+"/com/cablevision/controller/contenido/autenticar.do?type="+tipo+"&idArchivo="+idArchivo;
	var popup=window.open(popupURL,"_explorerDialog",'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=900,height=500');
    popup.focus();
}

function openLinkExplorer(id, tipo, idArchivo){
	editorFuncName=null;
	$('__idText__').value=id;
	popupURL=contextPath+"/com/cablevision/controller/contenido/showLinks.do";
	var popup=window.open(popupURL,"_linkDialog",'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=900,height=500');
    popup.focus();
}



function setUrl(url){
	url = url.replace('/contentserver/',contextPath+'/servlet/content/lastversion/');
	if(editorFuncName != null){
		CKEDITOR.tools.callFunction(editorFuncName, url);
	}else{
		var label = document.getElementById('__idText__').value;
		
		if(scopeGuardado!=null){
			document.getElementById(lookupIdByTagId(label,scopeGuardado)).value = url;
		}else{
			document.getElementById(label).value = url;	
		}
	}
}

function setVariableEditor(variable){
	editorFuncName = variable;
}

function guardarScope(scope){
	scopeGuardado = scope;
}


function setHTMLInfo(url){
	url = url.replace('/contentserver/',contextPath+'/servlet/content/lastversion/');
	$($('__idText__').value).value = url;
}

function setLinkParent(label) {
	var link = "_pageLabel="+label;
	if(editorFuncName != null){
		CKEDITOR.tools.callFunction(editorFuncName, link);
	}else{
		$($('__idText__').value).value = link;
	}
}	

function showMotivo(){
	if($("opcionFlujo").value == '2'){
		$("divMotivo").style.display='block';
	}else{
		$("divMotivo").style.display='none';
	}
}
function changeWorkFlow(){
	var url = contextPath+"/com/cablevision/controller/contenido/changeWorkFlow.do";
   	parent.showLoading(true);
   	if($("opcionFlujo").value != ''){
			new Ajax.Request(url,
        	{parameters : Form.serialize($("formaWf")),
            onComplete:function (resp){
                parent.showLoading(false);
            },
            onFailure: function (resp){
                alert('Error al guardar los datos...');
            }
        });	
	}else{
		 alert('Seleccione la Accion');
	}
}

function getDocumentID(url, documentID) {
        url = url.replace('/idcdesa/','/contentserver/');
		window.opener.setHTMLInfo(url, documentID);
        window.close();
}
var config;

function crearEditor(editorId){
	if (CKEDITOR.instances[editorId]) {
		config = CKEDITOR.instances[editorId].config;
		CKEDITOR.remove(CKEDITOR.instances[editorId]);
	 }
	 
	 if(config!=null){
	 	CKEDITOR.replace(editorId,config);
	 }else{
		CKEDITOR.replace(editorId,{
				filebrowserBrowseUrl : contextPath+'/com/cablevision/controller/contenido/showLinks.do',
				filebrowserImageBrowseUrl : contextPath+'/com/cablevision/controller/contenido/autenticar.do?type=imagen',
				filebrowserFlashBrowseUrl :contextPath+'/com/cablevision/controller/contenido/autenticar.do?type=flash',
				contentsCss : [contextPath+'/framework/skins/cablevision/css/screenfix.css',contextPath+'/framework/skins/cablevision/css/layout.css',contextPath+'/framework/skins/cablevision/css/ui.css',contextPath+'/framework/skins/cablevision/css/thickbox.css'],
				toolbar : 'Full'
			});
	 }
}

/*funciones para seleccionar el folder en contenido nuevo*/
function OnReset(popUpWindow){
	var callback = new Object();
	callback.OnIDValue = function(collectionid,collectionpath){
		document.getElementById(lookupIdByTagId('folderId',scopeGuardado)).value = collectionid;
		document.getElementById("idFolder").value = collectionpath;
	};
	popUpWindow.callback=callback;
}

function validaDatosContenido(scope){
	try{
		var contenido = document.getElementById(lookupIdByTagId('contenidoId',scope)).value;
		var folder = document.getElementById("idFolder").value;
		var nombre = document.getElementById(lookupIdByTagId('nombre',scope)).value;
		
		if(contenido=='' && folder==''){
			alert("Introduce el contenido o la carpeta en la cual se creara");
			return false;
		}else if (folder!='' && nombre==''){
			alert("Da un nombre al contenido nuevo");
			return false;
		}else{
			return true;
		}
	}catch(err){
		return false;
	}
}

function validaDatosNuevoContenido(scope){
	try{
		var nombre = document.getElementById(lookupIdByTagId('nombre',scope)).value;
		var folder = document.getElementById("idFolder").value;
		var estructuraId = document.getElementById(lookupIdByTagId('estructuraId',scope)).value;
		
		if(nombre=='' || folder=='' || estructuraId==''){
			alert("Todos los datos son requeridos");
			return false;
		}else{
			return true;
		}
	}catch(err){
		return false; 
	}
}
