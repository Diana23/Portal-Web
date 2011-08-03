<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
	String nombreCompleto = nombreContacto + " " + account.getApellidoPaterno();
</jsp:scriptlet>
<netui:scriptContainer>
<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
<h3>Sé parte de Paperless </h3>
<p class="padding-top10">${pageInput.mensaje}</p>
<netui:form action="registro" tagId="form" styleClass="formulario">
	<div style="display:none;">
		<netui:rewriteName  name="form" forTagId="true" resultId="form"/>
	</div>
	<netui-data:declareBundle bundlePath="com.cablevision.controller.paperless.paperless" name="paperlessBundle"/>
	<font size="2" color="Red">
    	<c:if test="${!empty pageInput.errores}">
			<br/>
       		${pageInput.errores}
       	</c:if>
      	</font>
      	<c:if test="${empty pageInput.errores}">
		<netui:errors bundleName="paperlessBundle" />
      	</c:if>	
   	<ul class="mail">
       	<li>
			<label class="suscribe">
				<netui:checkBox dataSource="actionForm.check" onClick="verificaCheckBox();" style="border:0!important;margin-top:3px;" styleClass="checkPaper"/>
				<strong>${pageInput.textoCheck}</strong>
			</label>
		</li>
           <li>
           	<c:if test="${pageInput.paperlessBean.estado != 'baja'}">
				<netui:textBox dataSource="actionForm.email"  tagId="actionForm_email" styleClass="big"/> 
			</c:if>
		</li>
	</ul>
	<div class="login-in-thbox" id="confirmarDiv" style="display: none;">
			<div class="wrap-img-thbk7" align="center">
				<a href="#" onClick="self.parent.tb_remove();" title="cerrar" class="close-cross3">
					<img src="${pageContext.request.contextPath }/images/close.png" border="0" alt="X" />
				</a>
				${pageInput.textoPopup}
		        <p class="margin-top15">
		        	<netui:anchor action="begin" onClick="self.parent.tb_remove();submitForma('${form}',this);return false;" formSubmit="true" title="Cancelar" styleClass="btn btn-small fright">Cancelar</netui:anchor>
		        	<netui:anchor action="registro" onClick="self.parent.tb_remove();submitForma('${form}',this);return false;" formSubmit="true" title="Aceptar" styleClass="thickbox btn-small fleft marg-left20">Aceptar</netui:anchor>
		        </p>
			<div class="clear"></div>
	
		</div>
	</div>
    <p>
    	<netui:anchor action="begin" title="Cancelar" styleClass="btn btn-small margin-bot40 fright">Cancelar</netui:anchor>

    	<c:if test="${pageInput.paperlessBean.estado != 'alta'}">
			<c:if test="${pageInput.paperlessBean.check eq true}">
				<a id="botonSiguiente" class="thickbox btn-small fleft" title="Continuar" href="${pageContext.request.contextPath}/com/cablevision/controller/paperless/mostrarConfirmacionCambiarEmail.do#TB_inline?&amp;height=300&amp;width=400&amp;modal=true&inlineId=confirmarDiv" style="display:none;">Continuar</a>
			</c:if>
			
			<c:if test="${pageInput.paperlessBean.check ne true}">
				<a id="botonSiguiente" class="thickbox btn-small fleft" title="Continuar" href="${pageContext.request.contextPath}/com/cablevision/controller/paperless/mostrarConfirmacionCambiarEmail.do#TB_inline?&amp;height=300&amp;width=400&amp;modal=true&inlineId=confirmarDiv" style="display:none;">Continuar</a>
			</c:if>
			<c:set var="botonSiguiente" value="botonSiguiente" />
		</c:if>
		
		<c:if test="${pageInput.paperlessBean.estado eq 'alta'}">
			<c:if test="${pageInput.paperlessBean.check eq true}">
				<netui:anchor action="registro" formSubmit="true" styleClass="btn-small fleft" title="Continuar" tagId="botonSiguiente" style="display:none;" onClick="submitForma('${form}',this);return false;">
					Continuar
				</netui:anchor>
			</c:if>
			
			<c:if test="${pageInput.paperlessBean.check ne true}">
				<netui:anchor action="registro" formSubmit="true" styleClass="btn-small fleft" title="Continuar" tagId="botonSiguiente" style="display:none;" onClick="submitForma('${form}',this);return false;">
					Continuar
				</netui:anchor>
			</c:if>
			<div style="display:none;">
				<netui:rewriteName  name="botonSiguiente" forTagId="true" resultId="botonSiguiente"/>
			</div>
		</c:if>
    </p>
</netui:form>
</netui:scriptContainer>
<script type="text/javascript">
	$(document).ready(function(){   
		tb_init('a.thickbox, area.thickbox, input.thickbox');//pass where to apply thickbox
		imgLoader = new Image();// preload image
		imgLoader.src = tb_pathToImage;
		
		verificaCheckBox();
	});
	
	function verificaCheckBox(estado){
			var val = $('.checkPaper').attr('checked');
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
</script>