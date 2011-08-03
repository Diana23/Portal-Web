<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:directive.page import="com.bea.portlet.PageURL" />
<jsp:directive.page import="com.bea.portlet.GenericURL" />
<jsp:directive.page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils" />

<c:set var="minRes" value="${pageInput.currentPage*pageInput.minRows-pageInput.minRows+1}" scope="request"/>

<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/easing.1.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/mousewheel.min.js"></script>

<div class="span-18 last">
	<div class="wrap-cont-simple wcs-padd">
		<div class="span-11">
			<h2 class="title-faqs hidden-text">Preguntas Frecuentes</h2>
			<div class="job-side-title marg-left0">Filtrar Por</div>
			<netui:form action="getPreguntasFrecuentes" tagId="formFAQ">
				<div class="job-side-link-wa marg-left0 bk-grey padd-fix-4">
					<netui:select 
					dataSource="actionForm.categoria" 
					optionsDataSource="${pageInput.categoriasMap}" 
					tagId="selectCategoria" 
					styleClass="width-select-faq" 
					onChange="onChangeCategoriaHandler()" 
					nullable="true" 
					nullableOptionText="Ver todas"/>
				</div>
				<netui:hidden dataSource="actionForm.start" tagId="hdnStart"/>
				<netui:hidden dataSource="actionForm.searchStr" tagId="hdnSearchStr"/>
				<input type="hidden" id="hdn_contextpath" value="${pageContext.request.contextPath }"/>
				<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
			</netui:form>
			
			<div class="clear"></div>
			<br/>
			<br/>
			<div class="contentEdit" style="display: none;">
				<c:if test="${requestScope.canEdit}">
					<div style="width: auto; text-align: right;">
						<a target="_blank" href="<%=request.getContextPath()%>/com/cablevision/controller/contenido/getContenidoEditable.do?folderId=272&estructuraId=CV002300" style="text-decoration: none;">
							<img src="images/icon-add-32.gif" /> 
						</a>
					</div>
				</c:if>
			</div>
			
			<c:choose> 
				<c:when test="${!empty pageInput.preguntas}">
					<div class="faqpager">
						<div class="faqpager-results">
							Resultado ${minRes} - ${pageInput.maxRes} de ${pageInput.numPreguntas}
						</div>
						<div class="faqpager-pages">
							<div class="faqpager-pages">
								<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-prev-page" title="Anterior">Anterior</a>
								<c:forEach items="${requestScope.paginas}" var="pagina">
									<c:choose> 
		  								<c:when test="${pagina==pageInput.currentPage}">
											<strong class="color-orange">${pageInput.mapaPaginas[pagina]}</strong>
		  								</c:when>
		  								<c:otherwise>
			  								<a href="javascript: ;" onclick="goToFAQPage(this)" title="Ir a p&aacute;gina ${pageInput.mapaPaginas[pagina]}" class="color-orange">${pageInput.mapaPaginas[pagina]}</a> 
		  								</c:otherwise>
									</c:choose>
								</c:forEach>
								<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-next-page" title="Siguiente">Siguiente</a>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<br/><strong>Su b&uacute;squeda no produjo resultado intente de nuevo.</strong>
				</c:otherwise>
			</c:choose>
			
			<ul id="cotiz-content" class="cotiz-content">
				<c:forEach items="${pageInput.preguntas}" var="pregunta">
					<c:set var="contenidoId" value="${pregunta.id}" scope="request"/>
					<c:set var="estructuraId" value="CV002300" scope="request"/>
					<c:set var="templateId" value="CV002335" scope="request"/>
					<c:set var="borrar" value="true" scope="request"/>
					<c:set value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>' var="_pageLabel" />
					<jsp:include page="/util/contenido/renderizarContenido.jsp?borrar=true" flush="true"/>
				</c:forEach>
			</ul>
			<input type="hidden" id="hdnMinRows" value="${pageInput.minRows}"/>
			<input type="hidden" id="hdnCurrentPage" value="${pageInput.currentPage}"/>
			
			<c:if test="${!empty pageInput.preguntas}">
				<a class="color-orange fright" title="subir" href="#cotiz-content">^ Subir</a>
				<br/><br/>
				<div class="faqpager">
					<div class="faqpager-results">
						Resultado ${minRes} - ${pageInput.maxRes} de ${pageInput.numPreguntas}
					</div>
					<div class="faqpager-pages">
						<div class="faqpager-pages">
							<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-prev-page" title="Anterior">Anterior</a>
							<c:forEach items="${requestScope.paginas}" var="pagina">
								<c:choose> 
	  								<c:when test="${pagina==pageInput.currentPage}">
										<strong class="color-orange">${pageInput.mapaPaginas[pagina]}</strong>
	  								</c:when>
	  								<c:otherwise>
		  								<a href="javascript: ;" onclick="goToFAQPage(this)" title="Ir a p&aacute;gina ${pageInput.mapaPaginas[pagina]}" class="color-orange">${pageInput.mapaPaginas[pagina]}</a> 
	  								</c:otherwise>
								</c:choose>
							</c:forEach>
							<a href="javascript: ;" onclick="goToFAQPage(this)" class="color-orange faqpager-next-page" title="Siguiente">Siguiente</a>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<div class="span-6 last">
			<ul>
				<li class="job-side-title margin-top79">Búsqueda</li>
				<li class="job-side-link-wa bk-grey margin-bot15 ">
					<div class="text-side-bsqfaq" style="height: 15px;">
						<div class="mini-lupa">&nbsp;</div>
						<input type="text" name="busq-faq" id="busq_faq" onkeyup="javascript:if(getKeyCode(event)==13) validateSearchField();" style="display: block;"/>
					</div>
				</li>
				<li class="job-side-title">Tutoriales</li>
				<li class="job-side-link-wa bk-grey margin-bot15 ">
					<a class="prev-fq hidden-text" href="#" id="prev_tutorial_faq">anterior</a>
					<a class="next-fq hidden-text" href="#" id="next_tutorial_faq">siguiente</a>
					<div id="tutoriales_carrusel" class="tutoriales-fq" style="overflow: hidden;">
						<c:set var="contenidoId" value="CV002449" scope="request"/>
						<c:set var="estructuraId" value="CV002447" scope="request"/>
						<c:set var="templateId" value="CV002448" scope="request"/>
						<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
					</div>
					<div class="clear"></div>
				</li>
				<li class="job-side-title">Descargables</li>
				<c:set var="contenidoId" value="CV002489" scope="request"/>
				<c:set var="estructuraId" value="CV002488" scope="request"/>
				<c:set var="templateId" value="CV002487" scope="request"/>
				<jsp:include page="/util/contenido/renderizarContenido.jsp" flush="true"/>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script>
	$(document).ready(function(){
		$("#tutoriales_carrusel").jCarouselLite({
			btnNext: 	"#next_tutorial_faq",
			btnPrev: 	"#prev_tutorial_faq",
			easing: 	"bouncein",
			mouseWheel: true,
			visible: 	1
		});
		tb_init('a.thickbox2');
	});
	
	function goToFAQPage(page){
		var pNum;
		var cPage = parseInt($('#hdnCurrentPage').val());
		if($(page).hasClass('faqpager-prev-page')){
			pNum = cPage-1;
		}else if($(page).hasClass('faqpager-next-page')){
			pNum = cPage+1;
		}else{
			 pNum = parseInt($(page).html());
		}
		
		if(isNaN(pNum) || pNum<=0 || pNum>${pageInput.numPages})
			return false;
		
		$('#hdnStart').val((pNum-1)*parseInt($('#hdnMinRows').val()));
		$('#formFAQ').submit();
	}
	
	function onChangeCategoriaHandler(){
		$('#hdnSearchStr').val("");
		$('#hdnStart').val(0);
		$('#formFAQ').submit();
	}
	
	function validateSearchField(){
		var searchStr = $.trim($('#busq_faq').val());
		$('#selectCategoria').val('netui_null');
		if(searchStr.length<=2)
			return false;
		else{
			$('#hdnSearchStr').val(searchStr);
			$('#hdnStart').val(0);
			$('#formFAQ').submit();
		}
	}
	
	function getKeyCode(e){
		e= (window.event)? event : e;
		intKey = (e.keyCode)? e.keyCode: e.charCode;
		return intKey;
	}
</script>