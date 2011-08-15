<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.bea.com/servers/portal/tags/netuix/render" prefix="render" %>

<netui:scriptContainer>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript" src="https://www.google.com/recaptcha/api/js/recaptcha_ajax.js?legacy"></script>
<jsp:directive.page import="com.bea.portlet.GenericURL" />
<jsp:directive.page import="com.bea.portlet.PageURL" />


<script src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/resources/css/texto.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
	.hidden{visibility: hidden;}
</style>
	
	<c:set var="windowLabel">
		<netui:rewriteName name="a_a_a" forTagId="true" />
	</c:set>
	<c:set var="windowLabel">
		${fn:replace(pageScope.windowLabel , "a_a_a", "")}
	</c:set>	
	<c:set var="windowLabel">
		${fn:replace(pageScope.windowLabel , ".", "")}
	</c:set>
${pageScope.a }


<jsp:scriptlet>
	GenericURL urlSave = GenericURL.createGenericURL(request, response);
	urlSave.setTemplate("https");
	urlSave.setContextualPath("/com/cablevision/controller/cotizador/saveInformation.do");
	pageContext.setAttribute("urlSave", urlSave.toString());
</jsp:scriptlet>


<script type="text/javascript">

(function($){

$.ajaxSetup({cache: false});
var Product = function(opts){
	$.extend(this,opts || {});
}

$('.container').ajaxStart(function() {
  $.blockUI({message:null,overlayCSS:{backgroundColor:'#FFFFFF'}});
});
$('.container').ajaxComplete(function() {
  $.unblockUI();
});

var Cotizador = {
	init: function(opts){
		var idService = opts.idService;
		var orden = opts.orden;
		var idProduct = opts.idProduct;
		var priceSelected = $('#'+getJqId('<netui:rewriteName name="priceSelect" forTagId="true" resultId="priceSelect"/>')).val();
		var pricenormalSelected = $('#'+getJqId('<netui:rewriteName name="pricenormalSelect" forTagId="true" resultId="pricenormalSelect"/>')).val();
			
		var sumatoria = $('#'+getJqId('<netui:rewriteName name="sumatoria" forTagId="true" resultId="sumatoria"/>'));
		var sumatoriaefectivo = $('#'+getJqId('<netui:rewriteName name="sumatoriaefectivo" forTagId="true" resultId="sumatoriaefectivo"/>'));
		var steps = $('#'+getJqId('<netui:rewriteName name="mainContent" forTagId="true" resultId="mainContent"/>')+' .steps');
		var back = $('#'+getJqId('<netui:rewriteName name="back" forTagId="true" resultId="back"/>'));
		var next = $('#'+getJqId('<netui:rewriteName name="next" forTagId="true" resultId="next"/>'));
		var start = $('#'+getJqId('<netui:rewriteName name="init" forTagId="true" resultId="init"/>'));
		
		var pasoUno = $('#'+getJqId('<netui:rewriteName name="pasoUno" forTagId="true" resultId="pasoUno"/>'));
		var pasoDos = $('#'+getJqId('<netui:rewriteName name="pasoDos" forTagId="true" resultId="pasoDos"/>'));
		var pasoTres = $('#'+getJqId('<netui:rewriteName name="pasoTres" forTagId="true" resultId="pasoTres"/>'));
		
		var product = null;
		var paso = 1;
		
		$.ajax({
			  url: '${pageContext.request.contextPath}/com/cablevision/controller/cotizador/searchProducts.do',
			  data: {idService: idService, idProduct:idProduct, _windowLabel:'${pageScope.windowLabel }'},
			  global: false,
			  success: function(data) {
			    $('#'+getJqId('<netui:rewriteName name="products" forTagId="true" resultId="products"/>')).html(data);
			  }
			});
		
		if(idProduct != ""){
			product = new Product({
					id: idProduct,
					idService: idService,
					idProduct: idProduct,
					idProductOriginal: idProduct,
					price: priceSelected,
					priceOriginal: priceSelected,
					pricenormal: pricenormalSelected,
					pricenormalOriginal: pricenormalSelected
				});
			sumatoria.text('$'+new Number(priceSelected).toFixed(2));
			sumatoriaefectivo.text('$'+new Number(pricenormalSelected).toFixed(2));
		}
		$('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).removeClass().addClass('cs-nav'+orden+'-active hidden-text');
		back.removeClass().addClass('hidden');
		
		
		$('#'+getJqId('<netui:rewriteName name="services" forTagId="true"/>')+' a').live('click',function(){
			var orden = $(this).find('input[name=orden]').val();
			idService = $(this).find('input[name=idService]').val();
			
			if(paso == 1){
				removeClassWhites();
				$(this).removeClass('cs-nav-'+orden).addClass('cs-nav'+orden+'-active hidden-text');
				
				$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/searchProducts.do',{
					idService: idService,_windowLabel:'${pageScope.windowLabel }'
				},function(){
					if(product != null){
						setTimeout(function(){
							$('#'+product.id).attr("checked", "checked");
						},100); 
					}
				});
				
			}
			return false;
		});
		
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+' input.radio').live('click',function(){
			var radio = $(this);
			var parent = radio.parent();
			var price = parent.parent().find('input[name=price]').val();
			var pricenormal = parent.parent().find('input[name=pricenormal]').val();
			var orden = parent.parent().find('input[name=serviceOrder]').val();
			
			if(paso==1){
				product = new Product({
					id: radio.attr('id'),
					idService: idService,
					idProduct: radio.val(),
					idProductOriginal: radio.val(),
					price: price,
					priceOriginal:price,
					pricenormal: pricenormal,
					pricenormalOriginal: pricenormal
				});
			}else{
				product = new Product({
					id: radio.attr('id'),
					idService: idService,
					idProduct: radio.val(),
					idProductOriginal: product.idProductOriginal,
					price: price,
					priceOriginal:product.priceOriginal,
					pricenormal: pricenormal,
					pricenormalOriginal: product.pricenormalOriginal
				});
			}
			sumatoria.text('$'+new Number(price).toFixed(2));
			sumatoriaefectivo.text('$'+new Number(pricenormal).toFixed(2));
			if(paso==2){
				$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findProduct.do',{
					idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
				});
				$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).css('display','block');
				
				//mostrar seleccionado el nuevo paquete en servico
				removeClassWhites();
				$('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).removeClass('cs-nav-'+orden).addClass('cs-nav'+orden+'-active hidden-text');
			}
		});
		
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+ ' input.check').live('click',function(){
			var radio = $(this);	
			addAndRemoveExtra(radio,radio.parent().find('select'));
		});
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+ ' select').live('change',function(){
			var cmb = $(this);			
			addAndRemoveExtra2(cmb.parent().find('input.check'),cmb);
		});
		
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+ ' input.check-solicitud').live('click',function(){
			var chk = $(this);
			var divFac = chk.next();
			
		 	if(!chk.is(':checked')){
		 		divFac.css('display','block');
		 		var txtInstalacion = chk.parent().parent().parent().find(".instalacion input[type='text']");
				var txtFactura = chk.parent().parent().parent().find(".factura input[type='text']");

				txtFactura.each(function(i){
					$(this).val('');
				})
		 	}else{
		 		divFac.css('display','none');
		 	}
		})
		
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true" resultId="products"/>')+' a.fright').live('click', function(){			
			var chk = $('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+ ' input.check-solicitud');
			if(chk.is(':checked')){
		 		var txtInstalacion = chk.parent().parent().parent().find(".instalacion input[type='text']");
				var txtFactura = chk.parent().parent().parent().parent().find(".factura input[type='text']");
				
				txtInstalacion.each(function(i){
					$(txtFactura[i]).val($(this).val());
				})
		 	}
			saveLead();
		});
		
		/*Nueva definicion de pasos paso 1: escoger producto, 2: crece paquete, 3: crece paquete (paso 2.1) 
		  4: extras, 5: formulario */
		next.click(function(){
			if(product != null && paso<6){
				paso++;
				if(paso == 2){
					showStep2(false);
				}else if(paso == 3){
					showStep2_1();
				}else if(paso == 4){
					showStep3();
				}else if(paso == 5){
					showStep4();
				}
			}else{
				$('#'+getJqId('<netui:rewriteName name="emptyMsg" forTagId="true" resultId="emptyMsg"/>')).css('display','block');
			}
			return false;
		});
		
		back.click(function(){
			if(paso>1){
				paso--;
				if(paso === 4){
					showStep3();
				}else if(paso === 3){
					showStep2(true);
				}else if(paso === 2){
					showStep2(true);
				}else if(paso === 1){
					showStep1();
				}
			}
			return false;
		});
		
		start.click(function(){
			paso = 1;
			idService = '${requestScope.primerServicio }';
			showStep1();
			if(idProduct == ""){
				product = null;
			}else{
				sumatoria.text('$'+new Number(priceSelected).toFixed(2));
				sumatoriaefectivo.text('$'+new Number(pricenormalSelected).toFixed(2));
			}
			return false;
		});
	
		
		//Banner de pasos
		pasoUno.click(function(){
			if(product != null){
				paso = 1;
				idService = '${requestScope.primerServicio }';
				product = null;
				showStep1();
			}else{
				$('#'+getJqId('<netui:rewriteName name="emptyMsg" forTagId="true" resultId="emptyMsg"/>')).css('display','block');
			}
		});
		
		pasoDos.click(function(){
			if(product != null){
				paso = 2;
				showStep2(false);
			}else{
				$('#'+getJqId('<netui:rewriteName name="emptyMsg" forTagId="true" resultId="emptyMsg"/>')).css('display','block');
			}
		});
		
		pasoTres.click(function(){
			if(product != null){
				paso = 4;
				showStep3();
			}else{
				$('#'+getJqId('<netui:rewriteName name="emptyMsg" forTagId="true" resultId="emptyMsg"/>')).css('display','block');
			}
		});
		
		var idExtra = $('#'+getJqId('<netui:rewriteName name="idExtra" forTagId="true" resultId="idExtra"/>'));
		var grupos = new Object();
		
		function addAndRemoveExtra(radio,cmb){
			var parent = radio.parent();
			var len = parent.find('input:checked').length;
			var valor = parent.find('input:checked').val();
			var nombreRadio = parent.find('input:checked').attr('name');
			dependencia(nombreRadio, valor, parent, false, cmb);
			
			//solo debe entrar cuando tenga un grupo, el len se agrego para que no entre un check deseleccionado
			if(nombreRadio != '' && nombreRadio != 'extraId' && len>0){
				jQuery(':radio').each(function(name){
					var grupoRadioEach = $(this).attr('name');
					//el radio seleccionado tiene el mismo grupo ke el radio en el ke estoy posicionado en el each
					if(nombreRadio == grupoRadioEach){
						if($(this).attr('checked')){
							var extraRadio = $(this).val();
							if(grupos[nombreRadio] != ""){
								//si estoy seleccionando un radio que ya habia seleccionado anteriormente resto su valor y limpio
								if(extraRadio == grupos[nombreRadio]){
									removeExtra(extraRadio, cmb);
									$(this).removeAttr('checked');
									dependencia(nombreRadio, valor, parent, true, cmb);
									cmb.val("1");
									grupos[nombreRadio]='';
								}
								else{
									//si el radio seleccionado tiene el mismo grupo que el grupo del hidden idextra para no 
									//sumar/restar cantidades del diferentes grupos
									if(grupoRadioEach == grupos[nombreRadio]){
										removeExtra(grupos[nombreRadio], cmb);
										grupos[nombreRadio] = extraRadio;
										addExtra(extraRadio, cmb);
									}
									else{
										if(grupos[nombreRadio] != undefined){
											if(grupos[nombreRadio] != extraRadio){
												removeExtra(grupos[nombreRadio], cmb);
											}
										}
										
										grupos[nombreRadio] = extraRadio;
										addExtra(extraRadio, cmb);
									}
								}
							}
							else{
								if(grupos[nombreRadio] != extraRadio){
									removeExtra(grupos[nombreRadio], cmb);
								}
								grupos[nombreRadio] = extraRadio;
								addExtra(extraRadio, cmb);
							}
						}
					}
				});
			}else{
				//si no tiene grupo hace lo que ya hacia antes de ponerles grupos a los extras
				dependencia(nombreRadio, valor, parent, false, cmb);
				if(len>0){
					addExtra(radio.val(), cmb);
				}else{
					cmb.val("1");
					removeExtra(radio.val(), cmb);
				}
			}
		}
		
		//esta funcion sera solo para los selects, no para los radios
		function addAndRemoveExtra2(radio,cmb){
			var parent = radio.parent();
			var len = parent.find('input:checked').length;
			
			if(len>0){
				addExtra(radio.val(), cmb);
			}else{
				removeExtra(radio.val(), cmb);
			}
		}
		
		function addExtra(radio, cmb){
			$.getJSON('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/addExtra.do',{idExtra:radio,number:cmb.val(),_windowLabel:'${pageScope.windowLabel }'},function(data){
				$('#'+getJqId('<netui:rewriteName name="descripcionExtras" forTagId="true"/>')).empty().append(data.extras);
				sumatoria.text('$'+new Number(data.price).toFixed(2));
				sumatoriaefectivo.text('$'+new Number(data.pricenormal).toFixed(2));
				var productTemp = product;
				product = new Product({
					id: productTemp.id,
					idService: data.idService,
					idProduct: data.idProduct,
					idProductOriginal: productTemp.idProductOriginal,
					price: data.price,
					priceOriginal: productTemp.priceOriginal,
					pricenormal: data.pricenormal,
					pricenormalOriginal: productTemp.pricenormalOriginal,
					extras: data.extras
				});
				$('#'+getJqId('<netui:rewriteName name="liExtras" forTagId="true"/>')).css('display','block');
			});
		}
		
		function removeExtra(radio, cmb){
			$.getJSON('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/removeExtra.do',{idExtra:radio,number:cmb.val(),_windowLabel:'${pageScope.windowLabel }'},function(data){
				$('#'+getJqId('<netui:rewriteName name="descripcionExtras" forTagId="true"/>')).empty().append(data.extras);
				sumatoria.text('$'+new Number(data.price).toFixed(2));
				sumatoriaefectivo.text('$'+new Number(data.pricenormal).toFixed(2));
				var productTemp = product;
				product = new Product({
					id: productTemp.id,
					idService: data.idService,
					idProduct: data.idProduct,
					idProductOriginal: productTemp.idProductOriginal,
					price: data.price,
					priceOriginal: productTemp.priceOriginal,
					pricenormal: data.pricenormal,
					pricenormalOriginal: productTemp.pricenormalOriginal,
					extras: data.extras
				});
				$('#'+getJqId('<netui:rewriteName name="liExtras" forTagId="true"/>')).css('display','block');
			});
		}
				
		/*
		esta funcion oculta o muestra los extras dependiendo si tienen valor en el campo depende
		nombreRadio es el name del radio seleccionado, valor es el value del radio seleccionado, 
		parent es el padre del radio seleccionado en la estructura de tags, y 
		dobleSel es un boolean que indica si se ha seleccionado dos veces el mismo radio
		*/
		function dependencia(nombreRadio, valor, parent, dobleSel, cmb){
			jQuery(':hidden').each(function(){
				var grupodelHidden = $(this).parent().find(':radio').attr('name');
				
				if($(this).attr('name') == 'depende'){
					//quitar la seleccion de un check o radio si tienen dependencia a un extra que fue des-seleccionado
					var extraHidden = $(this).parent().find('input[name=extraId]');
					
					if($(this).val() != ""){
						//el valor del hidden es igual al valor del radio o check seleccionado
						if($(this).val() == valor){
							if(dobleSel){
								$(this).parent().hide();
								
								if(extraHidden.attr('checked')){
									if(extraHidden.attr('name') == 'extraId'){
										extraHidden.removeAttr('checked');
										removeExtra(extraHidden.val(), cmb);
									}
								}
							}
							else{
								$(this).parent().show();
							}
						}
						else{
							var depSel = parent.find('input[name=depende]').val();
							var value = parent.find('input[name=extraId]').val();
							
							//si selecciono el extra 1 que tiene dependencia a otro extra 2, pero como el seleccionado 
							//es 1 y no 2 No debe ocultarlo por que se ocultaria el mismo y no es correcto
							if(depSel != $(this).val()){
								
								//si es el padre al que le dieron clic hago esto sino no
								if($(this).val() == value){
								
									//cuando el extra seleccionado es un check y otro check depende de el, al ocultar el dependiente
									// tengo que quitar la seleccion al check y restar su valor a la sumatoria
									if(extraHidden.attr('checked')){
										if(extraHidden.attr('name') == 'extraId'){
											extraHidden.removeAttr('checked');
											removeExtra(extraHidden.val(), cmb);
										}
									}
									
									$(this).parent().hide();
								}
							}
						}
					}
				}
			});
		}
		
		
		function showStep1(){
			back.removeClass().addClass('hidden');
			next.removeClass().addClass('coti-sigui hidden-text');
			
			sumatoria.text('$0.00');
			sumatoriaefectivo.text('$0.00');
			
			$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).css('display','none');
			
			$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/searchProducts.do',{
				idService: idService,idProduct:idProduct,_windowLabel:'${pageScope.windowLabel }'
			});
			$('#'+getJqId('<netui:rewriteName name="services" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/getServices.do',{
				idService: idService,_windowLabel:'${pageScope.windowLabel }'
			});
			
			//banner con pasos
			pasoUno.addClass('bread-cot1-act');
			pasoDos.removeClass('bread-cot2-act');
			pasoTres.removeClass('bread-cot3-act');
		}
		
		function showStep2(isBack){
			back.removeClass().addClass('coti-atras hidden-text');
			next.removeClass().addClass('coti-sigui hidden-text');
			menuDisable();
			
			$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findUpgradesProduct.do',{
				idProduct: product.idProductOriginal,_windowLabel:'${pageScope.windowLabel }'
			}, function(response, status, xhr){
				if(response!=null && response!=''){
					paso=2;
				}else{
					if(!isBack){
						paso=3;
					}else{
						paso=2;
					}
					$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findCreceProducts.do',{
						idProduct: product.idProductOriginal,_windowLabel:'${pageScope.windowLabel }'
					}, function(response, status, xhr){
						if(response==null || response==''){
							paso=4;
							showStep3();
						}
					});
				}
			});
			
			$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findProduct.do',{
				idProduct: product.idProductOriginal,_windowLabel:'${pageScope.windowLabel }'
			});
			$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).css('display','block');
			
			sumatoria.text('$'+new Number(product.priceOriginal).toFixed(2));
			sumatoriaefectivo.text('$'+new Number(product.pricenormalOriginal).toFixed(2));
			
			//banner con pasos
			pasoDos.addClass('bread-cot2-act');
			pasoUno.removeClass('bread-cot1-act');
			pasoTres.removeClass('bread-cot3-act');
		}	
		
		function showStep2_1(){
			back.removeClass().addClass('coti-atras hidden-text');
			next.removeClass().addClass('coti-sigui hidden-text');
			
			$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findCreceProducts.do',{
				idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
			}, function(response, status, xhr){
				if((response==null || response=='')){
					paso=4;
					showStep3();
				}
			});
			
			$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/showProductsSelected.do',{
				idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
			});
			$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).css('display','block');
			
			sumatoria.text('$'+new Number(product.price).toFixed(2));
			sumatoriaefectivo.text('$'+new Number(product.pricenormal).toFixed(2));
			
		}
		
		function showStep3(){
			grupos = new Object();//var global que sirve para controlar sumatoria de grupos de extras
			
			back.removeClass().addClass('coti-atras hidden-text');
			next.removeClass().addClass('coti-sigui hidden-text');
			
			$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/findExtrasProduct.do',{
				idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
			});
			
			//banner con pasos
			pasoTres.addClass('bread-cot3-act');
			pasoUno.removeClass('bread-cot1-act');
			pasoDos.removeClass('bread-cot2-act');
		}
		
		function showStep4(){
			back.removeClass().addClass('hidden');
			next.removeClass().addClass('hidden');
			
			$('#<netui:rewriteName name="services" forTagId="true"/>').empty();
			$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/showSolicitud.do',{
				idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
			});
		}
		
		function saveLead(){
			var data = $('#'+getJqId('formcot')).serialize();
			data = data.replace(/%7B/g,'');
			data = data.replace(/%7D/g,'');
			data = data.replace(/(\w)*pageInput\./g,'');
			data = data.replace(/(\w)*form\./g,'');
			

			$.getJSON('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/saveInformation.do',data,function(res){
				$('div.divError').addClass('hidden');
				$('#'+getJqId('divResponse')).css('display','none');
				
				if(res.success == "true"){
					$('#'+getJqId('response-msg')).html(res.msg);	
					$('#'+getJqId('divResponse')).css('display','block');
					$('#'+getJqId('divFormCot')).css('display','none');
				}else{
					Recaptcha.reload();
					var size = res.errores.length;
					for(var i=0;i<size;i++ ){
						var error = res.errores[i];
						if(error.isLast)break;
						$('#'+getJqId(error.name+'-msg')).html(error.msge);
						$('#'+getJqId(error.name+'-msg')).removeClass('hidden');	
					}
				}
			});
		}
		
		function removeClassWhites(){
	      $('#'+getJqId('<netui:rewriteName name="services" forTagId="true"/>')+' a').each(function(){
	          var orden = $(this).find('input[name=orden]').val();
	          $('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).removeClass().addClass('cs-nav-'+orden+' hidden-text');
	      });
		
		}
		
		function menuDisable(){
	      $('#'+getJqId('<netui:rewriteName name="services" forTagId="true"/>')+' a').each(function(){
	      	var orden = $(this).find('input[name=orden]').val();
	      	if( ! $('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).hasClass('cs-nav'+orden+'-active') ){
				$('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).removeClass().addClass('cs-nav'+orden+'-disable hidden-text');	          	
	      		$('#'+getJqId('<netui:rewriteName name="menu-cot" forTagId="true"/>')+orden).css('cursor', 'default');
	      	}	          
	      });
		
		}
		
		$('#'+getJqId('<netui:rewriteName name="products" forTagId="true"/>')+ ' input.radiom').live('click',function(){
			var radio = $(this);
			var parent = radio.parent();
			var price = parent.parent().find('input[name=price]').val();
			var pricenormal = parent.parent().find('input[name=pricenormal]').val();
			var group = parent.parent().find('input[name=group]').val();
			
			addMejora(radio.val(), group);
		});
		
		function addMejora(radio, group){
			$.getJSON('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/addMejora.do',{idProduct:radio,group:group,_windowLabel:'${pageScope.windowLabel }'},function(data){
				sumatoria.text('$'+new Number(data.price).toFixed(2));
				sumatoriaefectivo.text('$'+new Number(data.pricenormal).toFixed(2));
				$('#'+getJqId('<netui:rewriteName name="seleccionado" forTagId="true"/>')).load('${pageContext.request.contextPath}/com/cablevision/controller/cotizador/showProductsSelected.do',{
					idProduct: product.idProduct,_windowLabel:'${pageScope.windowLabel }'
				});
			});
			
		}
		
		
		//Ancla de subir
		$('a.color-orange').live('click', function () {
			elementClick = '#anchorTarget';
			destination = $(getJqId(elementClick)).offset().top;
			$("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination}, 1100 );
			return false;
		});
		
		function getJqId(myid){ 
			return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
		}
	}
	
};


$(function(){

	Cotizador.init({idService:${requestScope.primerServicio}, orden:${requestScope.ordenSelect}, idProduct: '${requestScope.productSelect}'});
	
});

})(jQuery);
</script>
	<c:set var="imagen">
		<netui:rewriteName name="imagen" forTagId="true"/>
	</c:set>
	<c:set var="descripcion">
		<netui:rewriteName name="descripcion" forTagId="true"/>
	</c:set>
	<c:set var="products">
		${fn:replace(pageScope.products , ".", "\\.")}
	</c:set>	
	<c:set var="mainContent">
		${fn:replace(pageScope.mainContent , ".", "\\.")}
	</c:set>
	<c:set var="imagen">
		${fn:replace(pageScope.imagen , ".", "\\.")}
	</c:set>	
	<c:set var="descripcion">
		${fn:replace(pageScope.descripcion , ".", "\\.")}
	</c:set>	
	<c:set var="sumatoria">
		${fn:replace(pageScope.sumatoria , ".", "\\.")}
	</c:set>
	<c:set var="sumatoriaefectivo">
		${fn:replace(pageScope.sumatoriaefectivo , ".", "\\.")}
	</c:set>
	<c:set var="emptyMsg">
		${fn:replace(pageScope.emptyMsg , ".", "\\.")}
	</c:set>	
	
	<c:set var="suma">
		${fn:replace(pageScope.suma , ".", "\\.")}
	</c:set>
	<c:if test="${!empty pageInput.product}">
		<input type="hidden" value="${pageInput.product.tcPrice}" id="<netui:rewriteName name="priceSelect" forTagId="true"/>" />
		<input type="hidden" value="${pageInput.product.normalPrice}" id="<netui:rewriteName name="pricenormalSelect" forTagId="true"/>" />
	</c:if>
<!-- Content -->	
	<!-- Sidebar: Accordeon -->
	<div class="span-6" id="<netui:rewriteName name="services" forTagId="true"/>"> 
		<h2 class="title-cotizador hidden-text">Cotizador</h2>
		
		<netui-data:repeater dataSource="pageInput.services">
			<a href="#" title="Combo" id="<netui:rewriteName name="menu-cot" forTagId="true"/>${container.item.orden}" class="cs-nav-${container.item.orden} hidden-text">
				${container.item.name}
				<input type="hidden" name="idService" value="${container.item.idService}" />
				<input type="hidden" name="orden" value="${container.item.orden}" />
			</a>
		</netui-data:repeater>
		
	</div><!--End: Sidebar: Accordeon -->

	<div class="span-18 last">
		<div class="wrap-cont-simple">
			<div class="span-11" >
				
				<img src="http://www.latinlabs.com.ar/cablevision/img/cuadroHead13.png" alt="box" class="cube-magique" />
				
				<a name="anchorTarget" id="anchorTarget"></a>
				<a name="cotiz-content"></a>
				<ul class="cotiz-bread" id="<netui:rewriteName name="pasosUl" forTagId="true"/>">
					<li><a href="#" title="El&iacute;ge tu paquete" class="bread-cot1 bread-cot1-act hidden-text" id="<netui:rewriteName name="pasoUno" forTagId="true"/>" >El&iacute;ge tu paquete</a></li>
					<li><a href="#" title="Crece tu paquete" class="bread-cot2 hidden-text" id="<netui:rewriteName name="pasoDos" forTagId="true"/>" >Crece tu paquete</a></li>
					<li><a href="#" title="Personalizalo" class="bread-cot3 hidden-text" id="<netui:rewriteName name="pasoTres" forTagId="true"/>" >Personalizalo</a></li>
				</ul>
				
				<div class="clear"></div>
				<div id="<netui:rewriteName name="products" forTagId="true"/>"></div>

				<a href="#anchorTarget" title="subir" class="color-orange padding-top40 fright">^ Subir</a>
			</div>
			
			<div class="span-6 last" >
				<ul class="side-cotiza">
					<li><h3 class="title-carro">Tu Cuenta es de:</h3></li>
					<li class="precio-carro">
						<img src="http://www.latinlabs.com.ar/cablevision/img/carrocompras.png" alt="$808.0" class="img-carro" />
						<span id="<netui:rewriteName name="sumatoria" forTagId="true"/>">$0.0</span></br>
						<span style='font-size: 9px;'>*Precio en efectivo <span id="<netui:rewriteName name="sumatoriaefectivo" forTagId="true"/>">$0.0</span> mensuales</span>
					</li>

					<li><div class="precio-legend">Precio mensual con tarjeta de crédito</div></li>
					<li><div class="precio-legend">Aplica precio de instalación<sup>[17]</sup></div></li>
					<li class="background-white" ><br /></li>
					
					<li><h3 class="title-carro">Haz Seleccionado:</h3></li>
					
					<div id="<netui:rewriteName name="seleccionado" forTagId="true"/>"></div>
					
					<li class="nav-coti-wrap">
						<a href="#" title="atras" class="coti-atras hidden-text" id="<netui:rewriteName name="back" forTagId="true"/>">atras</a>
						<a href="#" title="recotizar" class="coti-reco hidden-text" id="<netui:rewriteName name="init" forTagId="true"/>">recotizar</a>
						<a href="#" title="siguiente" class="coti-sigui hidden-text" id="<netui:rewriteName name="next" forTagId="true"/>">siguiente</a>
					</li>
				</ul>						
			</div>
			<div class="clear"></div>
		</div>
		<table width="715" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="35" align="right" class="navi2">&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td align="right" class="p-legales">
					CABLEVISION<sup style="font-size: 9px">&reg;</sup> 2007. Todos los derechos reservados. Aviso legal y políticas. Precios con cargo recurrente a tarjeta de crédito. 
					Precios y Servicios registrados ante la COFETEL e Incluyen I.V.A. Los precios señalados son mensuales. 
					Precios de instalación paquetes (pago único): paquete YOO 3 en 1 ó 2 en 1: $299; paquete YOO Total HD 3 en 1 ó 2 en 1: $399; 
					paquete YOO Premiere 3 en 1 ó 2 en 1: $499. 
					Tratándose de cualquier paquete YOO 2 en 1 en combinación de servicios de Internet + Telefonía, 
					el precio de instalación es de $1,199. 
					Precios de instalación servicios individuales: TV $399; Internet:$1,199; 
					Telefonía: $1,199; línea adicional de telefonía en segunda visita: $399. 
					Precios sujetos a cambio sin previo aviso. 
					[*]Telefonía local Ilimitada. Sujeto a cobertura. 
					[**]Cablevisión On Demand requiere la contratación de la Membresía de Acceso. 
					Algunos programas tienen un costo adicional a la Membresía de Acceso. 
					Los paquetes YOO 3 en 1 o 2 en 1 incluyen 12 meses de la Membresía de Cablevisión On Demand sin Costo. 
					[***] Paquetes de Alta Definición hasta 4 equipos sin importar combinación entre HD Pack Premium y/o HD Pack Value. 
				</td>
			</tr>
		</table>		
	</div>			
<!-- End: Content -->
</netui:scriptContainer>
