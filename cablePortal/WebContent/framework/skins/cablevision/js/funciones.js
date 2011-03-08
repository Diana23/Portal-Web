$(document).ready(function() {	

	/* Accordeon Menu Izq */
	
	function menuAcc(thisObjectChange){ $(thisObjectChange).removeClass().addClass('menu-acc'); }
	function menuAccHover(thisObjectChange){ $(thisObjectChange).removeClass().addClass('menu-acc-hover'); }
	function menuAccSel(thisObjectChange){ $(thisObjectChange).removeClass().addClass('menu-acc-sel'); }
	function menuAccSelHover(thisObjectChange){ $(thisObjectChange).removeClass().addClass('menu-acc-sel-hover'); }
	
	function closeAllacc() {
		menuAcc( $(".acc-cont:visible").prev() );
		$(".acc-cont:visible").slideUp('normal');
	}
			
	function slideAcc(thisAcc){ 
			closeAllacc();
			$(thisAcc).next('.acc-cont').slideToggle('normal')
		};

	
	$('ul.accor-side li > a').mouseenter(function() {
		if ( $(this).attr('class') == "menu-acc" ) { menuAccHover(this); } 
		if ( $(this).attr('class') == "menu-acc-sel" ) { menuAccSelHover(this); }	
	})
		
	$('ul.accor-side li > a').mouseleave(function() {
		if ( $(this).attr('class') == "menu-acc-hover" ) { menuAcc(this); }
		if ( $(this).attr('class') == "menu-acc-sel-hover" ) { menuAccSel(this); }
	});

	$('ul.accor-side li > a').click(function() {
		if ( $(this).attr('class') == "menu-acc-sel-hover" ){ // si esta open, link a la landing
				return true;
			} else { // sino abre el accordeon 
					if ( $(this).attr('id').indexOf('lastPage') >= 0 ){ return true; }
				menuAccSelHover(this);
				slideAcc(this);
				return false;
			}			
	});

	/* Borrar palabra inputs */
		
		// Input comunes
		function cleanBoxWord(wordOriginal, thisBox){ $(thisBox).attr('value') == wordOriginal ? $(thisBox).attr('value', '') : $(thisBox).attr('value'); }
		function putBoxWord(wordOriginal, thisBox){	$(thisBox).attr('value') == " "	? $(thisBox).attr('value', wordOriginal) : $(thisBox).attr('value'); }	
						
		
		$('.search-box').focus(function () { cleanBoxWord('Buscar en',this); $('#changeSearch').val('true'); });
		$('.search-box').blur(function () { putBoxWord('Buscar en',this); $('#changeSearch').val('true'); });	
		
		$('#user-box').focus(function () { cleanBoxWord('Usuario',this);  });
		$('#user-box').blur(function () { putBoxWord('Usuario',this); });	
		
		$('#pass-box').focus(function () { $(this).attr('value',''); });

		
	/* Tabs productos-promociones */
		//Hover tabs home
		
		var activeTabsHome = "prod";
		
		$('ul.wraps-tabs li').hover( 
			function(){
				$(this).removeClass('stand').addClass('active');
			},
			function(){
				if ( activeTabsHome == "prod"){
					$('#tab-prom').removeClass('active').addClass('stand');
					$('#tab-prod').removeClass('stand').addClass('active'); 
				} else {
					$('#tab-prod').removeClass('active').addClass('stand');
					$('#tab-prom').removeClass('stand').addClass('active'); 				
				}	
			}
		)		
		
		$('#tab-prod').click(function(){
			$('#tab-prom').removeClass('active').addClass('stand');
			$(this).removeClass('stand').addClass('active');
			activeTabsHome = "prod";
			$('#cont-prod-home').css('display','block');
			$('#cont-prom-home').css('display','none');	
		})

		$('#tab-prom').click(function(){
			$('#tab-prod').removeClass('active').addClass('stand');
			$(this).removeClass('stand').addClass('active');
			activeTabsHome = "prom";
			$('#cont-prod-home').css('display','none');
			$('#cont-prom-home').css('display','block');	
		})
		
	/* Navbar y Menu desplegable */
		function cleanClaseHover(){ 
			$('.bk-item-1').removeClass('bk-item-1-hover'); 
			$('.bk-item-2').removeClass('bk-item-2-hover'); 
			$('.bk-item-3').removeClass('bk-item-3-hover'); 
			$('.bk-item-4').removeClass('bk-item-4-hover'); 
			$('.bk-item-5').removeClass('bk-item-5-hover'); 
			$('.bk-item-6').removeClass('bk-item-6-hover'); 		
		}
		
		var clickOnThisTab;
		
		$('.wraps-bk-items li').mouseenter(function(){
			 clickOnThisTab = $(this).attr('class').slice(-1); 
			 $(this).addClass('bk-item-'+clickOnThisTab+'-hover');
			 switch (clickOnThisTab){
					case "1": $('#wrap-despl, #soluciones').css('display','block');  break;
					case "2": $('#wrap-despl, #ventajas').css('display','block'); break;
					case "3": $('#wrap-despl, #entrenteni').css('display','block'); break;
					case "4": $('#wrap-despl, #atencion').css('display','block'); break;
					case "5": $('#wrap-despl, #contrata').css('display','block'); break;
					case "6": $('#wrap-despl, #acerca').css('display','block'); break;
				}
			}
		)
		
		$('.wraps-bk-items li, #wrap-despl').mouseleave(function(){
			cleanClaseHover();
			$('.desple-cont ul, #wrap-despl').css('display','none');
		})
		
		
		
		$('#wrap-despl').mouseenter(function(){
				switch (clickOnThisTab){
					case "1": $('#wrap-despl, #soluciones').css('display','block'); break;
					case "2": $('#wrap-despl, #ventajas').css('display','block'); break;
					case "3": $('#wrap-despl, #entrenteni').css('display','block'); break;
					case "4": $('#wrap-despl, #atencion').css('display','block'); break;
					case "5": $('#wrap-despl, #contrata').css('display','block'); break;
					case "6": $('#wrap-despl, #acerca').css('display','block'); break;
				}
		})
		
		$('#wrap-despl').mouseover(function(){
			$('.bk-item-'+clickOnThisTab).addClass('bk-item-'+clickOnThisTab+'-hover');			
		});

	/* Desplegable buscar */
		/*
		$('.search-select').hover(function(){ $('.box-buscar-en').css('display','block'); })	

		$('.box-buscar-en').mouseleave(function(){ $(this).css('display','none') })
		
		$('.box-buscar-en a').click(function(){ 
			$('.search-select').html( $(this).html() );
			$('.box-buscar-en').css('display','none'); 
			return false;
		})
		*/
		
		/* click buscar*/
		$('.search-select').toggle(
		function() {
			  $(this).html('Programaci&oacute;n');
			  $(this).attr("title", "Programación");
			  $('#tipoBusqueda').val("programacion");
			},
		function() {
			  $(this).html('Productos');
			  $(this).attr("title", "Productos");
			  $('#tipoBusqueda').val("productos");
			}, 
		function() {
			  $(this).html('Todo el Sitio');
			  $(this).attr("title", "Todo el Sitio");
			  $('#tipoBusqueda').val("");
			}			
		);
		
		/* TH-box : Canales*/
		var heightThBox;
		
		$('.bk-logos-canales').mouseenter(
			function(){
				$('.thbox-latin').css('display','block');
				if( $(this).attr('rel') != "0" ){ 
						heightThBox = $(this).attr('rel') * (90);
						heightThBox = heightThBox - heightThBox - heightThBox - 215;
					} else {
						heightThBox = -215;
					}
				$('.thbox-latin').css('display','block');
				$('.thbox-latin').css('top',heightThBox);
				$('#footer').css('position','relative');
				$('#footer').css('top','-204px');
			})
			
			$('.thbox-latin').mouseleave(function(){
				$(this).css('display','none');
				$('#footer').css('position','static');
			})
			
		
		/* Switch Youtube videos */
		function hideAllVideos() { 
			$('#video-yt-1, #video-yt-2, #video-yt-3').css('display','none');
		}		
		
		$('#thumb-yt-1, #thumb-yt-2, #thumb-yt-3').click(function(){ 
			hideAllVideos();
			$('#video-yt-'+$(this).attr('rel')).css('display','block'); 
			
			return false;
		})
		
		/* Box user */
		$('#items-users').click(function(){
			if( $('#wrap-userlog').css('width') != "532px") {
					$('#wrap-userlog').css('backgroundPosition','-336px -1515px');
					$('#wrap-userlog').animate({
						width: '532px'
					  }, 500, function() {
							$('.wrap-user-items').css('display','block');
							$('#items-users').removeClass().addClass('menos hidden-text');
					  });
				} else {
					$('.wrap-user-items').css('display','none');
					$('#wrap-userlog').animate({
						width: '264px'
					  }, 500, function() {
							$('#items-users').removeClass().addClass('mas hidden-text');
							$('#wrap-userlog').css('backgroundPosition','-121px -202px');
					  });				
				}
		})
		
	/* Accordeon Paquetes */

		function menuAccPaq(thisObjectChange){ $(thisObjectChange).removeClass().addClass('ap-wrap-title ap-title'); }
		function menuAccHoverPaq(thisObjectChange){ $(thisObjectChange).removeClass().addClass('ap-wrap-title ap-title-hov'); }
		function menuAccSelPaq(thisObjectChange){ $(thisObjectChange).removeClass().addClass('ap-wrap-title ap-title-sel'); }
		function menuAccSelHoverPaq(thisObjectChange){ $(thisObjectChange).removeClass().addClass('ap-wrap-title ap-title-sel-hov');}
		
		function closeAllaccPaq() {
			menuAccPaq( $(".ap-wrap-cont:visible").prev() );
			$(".ap-wrap-cont:visible").slideUp('normal');
		}
				
		function slideAccPaq(thisAcc){ 
				$(thisAcc).next('.ap-wrap-cont').slideToggle('normal')
			};

	
		$('.ap-wrap-title').mouseenter(function() {
			if ($(this).attr('class') == "ap-wrap-title ap-title" ) { 
					menuAccHoverPaq(this); 
				} else {
					if ($(this).attr('class') == "ap-wrap-title ap-title-sel" ) { menuAccSelHoverPaq(this); }	
				}
		})
			
		$('.ap-wrap-title').mouseleave(function() {
			if ($(this).attr('class') == "ap-wrap-title ap-title-hov" )  { menuAccPaq(this); }
			if ($(this).attr('class') == "ap-wrap-title ap-title-sel-hov" ) { menuAccSelPaq(this); }
		});

		$('.ap-wrap-title').click(function() {		
			if ( $(this).attr('class') == "ap-wrap-title ap-title-sel-hov" ){ 
					closeAllaccPaq();
				} else { 
					menuAccSelHoverPaq(this);
				    slideAccPaq(this);
				}			
			return false;
		});
		
		
			
		

});

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

//validacion de formulario
//letras y espacios 164 209
function isAlpha(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    //console.log("Alpha: "+(key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122)));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122));
}

//letras y numeros
function isAlphaNumeric(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    //console.log("AlphaNumeric: "+(key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57)));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57));
}

//numeros
function isNumeric(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    //console.log("Numeric: "+(key <= 13 || key == 32 || (key >= 48 && key <= 57)));
	return (key <= 13 || key == 32 || (key >= 48 && key <= 57));
}

//letras y numeros
function isAlphaNumericDash(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    //console.log("AlphaNumericDash: "+(key <= 13 || key == 32 || key == 241 || key == 209 || key == 95 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57)));
	return (key <= 13 || key == 32 || key == 241 || key == 209 || key == 95 || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key >= 48 && key <= 57));
}

// Evalua una expresion regular en el evento onKeyPress, onKeyUp, onKeyDown, cambia su comportamiento dependiendo del evento
function validateValue(evt, strMatchPattern){
	var objRegExp = new RegExp( strMatchPattern);
	//console.log("ValidValue("+strMatchPattern+"): "+objRegExp.test(String.fromCharCode(evt.charCode)));
	return objRegExp.test(String.fromCharCode(evt.charCode));
}

// Remueve caracteres potencialmente peligrosos al momento de enviar un formulario 
function removeBadChars(o){
	var objRegExp = new RegExp(/^\°|\||\¬|\%|\&|\/|\'|\\|\*|\~|\[|\]|\{|\}|\^|\`|\<|\>|<\/$/g);
	o.value = o.value.replace(objRegExp, "");
}

function progFilter(elemento){
	var str = "";
	if($(elemento).attr('id')=="cb_todos"){
		$('#dv_cbs_categorias input:checked').attr('checked', false);
		$(elemento).attr('checked', true);
		$('.block-cell').each(function(){$(this).show();});
	}else{
		$('#dv_cbs_categorias').children("input[id='cb_todos']").attr('checked', false);
		$('.cbs-cats').each(function(){
			if($(this).attr('checked'))
				str+=($(this).attr('title')+" ");
		});
		
		if(str == ""){
			$('#dv_cbs_categorias').children("input[id='cb_todos']").attr('checked', true);	
		}
		progBuscar('.prog-cell', str);
	}
}

function progBuscar(selector, query){
	query =   $.trim(query);
	query = query.replace(/ /gi, '|');
	$(selector).each(function(){
		($(this).text().search(new RegExp(query, "i")) < 0) ? $(this).parent('.block-cell').hide().removeClass('visible') : $(this).parent('.block-cell').show().addClass('visible');
	});
}

function sortByName(sorter){
	$(sorter).toggleClass('sorted');
	if($(sorter).hasClass('sorted')){
		$('.block-cell').tsort({order:"asc"});
	}else{
		$('.block-cell').tsort({order:"desc"});
	}
}

function sortByCat(sorter){
	$(sorter).toggleClass('sorted');
	if($(sorter).hasClass('sorted')){
		$('.block-cell').tsort(".prog-cell.categoria",{order:"asc"});
	}else{
		$('.block-cell').tsort(".prog-cell.categoria",{order:"desc"});
	}
}

function progPPVFilter(elemento){
	var str = $(elemento).val();
	if($.trim(str)==""){
		$('.block-cell').each(function(){$(this).show();});
	}else{
		progPPVBuscar('.prog-cell span', str);
	}
}

function progPPVBuscar(selector, query){
	query =   $.trim(query);
	query = query.replace(/ /gi, '|');
	$(selector).each(function(){
		($(this).text().search(new RegExp(query, "i")) < 0) ? $(this).parents('.block-cell').hide().removeClass('visible') : $(this).parents('.block-cell').show().addClass('visible');
	});
}
	
function progPPVFilterCategories(elemento){
	var str = "";
	if($(elemento).attr('id')=="cb_todos"){
		$('#dv_cbs_categorias input:checked').attr('checked', false);
		$(elemento).attr('checked', true);
		$('.block-cell').each(function(){$(this).show();});
	}else{
		$('#dv_cbs_categorias').children("input[id='cb_todos']").attr('checked', false);
		$('.cbs-cats').each(function(){
			if($(this).attr('checked'))
				str+=($(this).attr('title')+" ");
		});
		
		if(str == ""){
			$('#dv_cbs_categorias').children("input[id='cb_todos']").attr('checked', true);	
		}
		
		progPPVBuscar('.prog-cell em', str);
	}
}

/* Accordeon Form CV */		
	
	
		function menuAccCV(thisObjectChange){ $(thisObjectChange).removeClass().addClass('acv-wrap-title acv-title'); }
		function menuAccHoverCV(thisObjectChange){ $(thisObjectChange).removeClass().addClass('acv-wrap-title acv-title-hov'); }
		function menuAccSelCV(thisObjectChange){ $(thisObjectChange).removeClass().addClass('acv-wrap-title acv-title-sel'); }
		function menuAccSelHoverCV(thisObjectChange){ $(thisObjectChange).removeClass().addClass('acv-wrap-title acv-title-sel-hov');}
		
		function closeAllaccCV() {
			menuAccCV( $(".acv-wrap-cont:visible").prev() );
			$(".acv-wrap-cont:visible").slideUp('normal');
		}
				
		function slideAccCV(thisAcc){ 
				$(thisAcc).next('.acv-wrap-cont').slideToggle('normal')
			};

	
		$('.acv-wrap-title').mouseenter(function() {
			if ($(this).attr('class') == "acv-wrap-title acv-title" ) { 
					menuAccHoverCV(this); 
				} else {
					if ($(this).attr('class') == "acv-wrap-title acv-title-sel" ) { menuAccSelHoverCV(this); }	
				}
		});
		
/* Handler para los clics en los radios de la encuesta de satisfaccion en cada faq en Preguntas Frecuentes */
function clickRadioHandler(opt, id){
	if(opt=="si"){
		$('#pres_'+id).hide();
		$('#faq_answer_'+id).val(1);
	}else{
		$('#pres_'+id).show();
		$('#faq_answer_'+id).val(0);
	}
	$('#faq_answer_error_'+id).hide();
	$('#faq_error_'+id).hide();
}

/* Funcion para envio de encuesta de satisfaccion en Preguntas Frecuentes mediante ajax */
function sendEncuesta(element, id){
	if($('#rsi_'+id).is(":checked") || $('#rno_'+id).is(":checked")){
		var resp = $('#faq_answer_'+id).val();
		if($('#rno_'+id).is(":checked") && $.trim($('#porque_'+id).val())==""){
			$('#faq_answer_error_'+id).show();
			return false;
		}
		$('#faq_error_'+id).load($('#hdn_contextpath').val()+'/com/cablevision/controller/encuesta/guardarRespuesta.do',
			{
				answer:resp,
				idquestion:id,
				porque:$.trim($('#porque_'+id).val())},
				function(){
					$('#faq_error_'+id).css("color", "#0000ff").show();
					$('#faq_answer_error_'+id).hide();
					$('#rsi_'+id).attr("disabled", "disabled");
					$('#rno_'+id).attr("disabled", "disabled");
					$('#porque_'+id).attr("disabled", "disabled");
					$(element).attr("onclick","return false;");
			}
		);
	}else{
		$('#faq_error_'+id).show();
		return false;
	}
	return true;
}

//numeros sin espacios en blanco
function soloNumeros(evt){	
    evt = (evt) ? evt : event;
    var key= (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode : ((evt.which) ? evt.which : 0));
    //console.log("Numeric: "+(key <= 13 || key == 32 || (key >= 48 && key <= 57)));
	return (key <= 13 || (key >= 48 && key <= 57));
}