<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils"%>
<netui:scriptContainer>
			<div id="thisistop" class="span-18 last">
				<div class="wrap-cont-simple wcs-padd">
					<netui:form action="begin">
						<div class="wcs-marg">
							<div class="span-10">
								
								<h2 class="side ico-micta margin-bot15">Ingresa a tu cuenta</h2>
								<c:if test="${not empty pageInput.msg}">
									<div class="clear">
										<font color="Red">
											<netui-data:repeater dataSource="pageInput.msg">${container.item}<br/></netui-data:repeater>
										</font>
										<p/>
									</div>
								</c:if>
								<div class="span-5 ">
									<div class="text-side marg-rig15">
										<label for="${user-box-login}">Usuario: </label>
										<netui:textBox dataSource="actionForm.usuario" size="15"/>
									</div>
								</div>
								<div class="span-5 last">
									<div class="text-side marg-rig15">
										<label for="pass-box">Contrase&ntilde;a: </label>
										<netui:textBox dataSource="actionForm.password" size="13" password="true"/>
									</div>
								</div>
								
								<div class="span-4">
									<jsp:scriptlet>
										String pageLabel = ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel");
										
										if(pageLabel != null){
											pageLabel = java.net.URLEncoder.encode(pageLabel.toString(),"UTF-8");
										}
										pageContext.setAttribute("pageLabel",pageLabel);
									</jsp:scriptlet>
									
									<a title="&iquest;Olvidaste tu clave?" class="thickbox color-orange" id="forget-pass" href="${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoclave4sel.jsp?modal=true&amp;height=430&amp;width=520&amp;pageLabel=<jsp:expression>pageLabel</jsp:expression>"> &gt; &iquest;Olvidaste tu contrase&ntilde;a?</a>
								</div>
								<netui:anchor formSubmit="true" styleClass="btn-small fright marg-rig15" title="Ingresar">
									<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
									<netui:parameter name="_nfto" value="false"/>
									Ingresar
								</netui:anchor>
								<div class="clear"></div>
								
							</div>
							<jsp:scriptlet>
								com.bea.portlet.PageURL registro = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_registro");
								registro.setTemplate("https");
								registro.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
								registro.setEncodeSession(false);
								pageContext.setAttribute("registro",registro.toString());
							</jsp:scriptlet>
							 
							<div class="span-6 last">
								<a title="" href="${registro}"><img alt="&iexcl;No esperes m&aacute;s! Registrarte es muy f&aacute;cil." src="<%=request.getContextPath()%>/images/registrer.png"></a>
							</div>						
							<div class="clear"></div>
						</div>	
					</netui:form>
					
					<c:set var="contenidoId" value="CV002238" scope="request"/>
					<c:set var="estructuraId" value="CV001950" scope="request"/>
					<c:set var="templateId" value="CV001951" scope="request"/>
								
					<jsp:include page="/util/contenido/renderizarContenido.jsp?contenidoId=CV002238&estructuraId=CV001950&templateId=CV001951" flush="true"/>
					
				</div>
				<div class="clear"></div>
			</div>
</netui:scriptContainer>

<script type="text/javascript">
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
	
	$('#forget-pass').click(function(){
			$.ajax({
				url: "${pageContext.request.contextPath }/com/cablevision/controller/recuperarpassword/olvidoclave.jsp",
				beforeSend: function(objeto){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html('<img src="${pageContext.request.contextPath }/images/preloader.gif" style="padding: 67px 135px" alt="Cargando..." />');
				},
				dataType: "html",
				success: function(datos){
					$('.login-in-thbox').html();
					$('.login-in-thbox').html(datos);
				},
				/* timeout: 1000, */
				type: "POST"
			});
		});
</script>