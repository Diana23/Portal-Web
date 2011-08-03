<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>

<div id="ruleta-${tipo}-${tipoRuleta}">
	<div align="right" class="span-8 margin-top28 last">
		<form action="${pageContext.request.contextPath }/com/cablevision/controller/entretenimiento/filtrarRuleta.do">
			<input type="hidden" value="${tipo }" name="tipo"/>
			<input type="hidden" value="${tipoRuleta }" name="tipoRuleta"/>
			
			<input type="checkbox" CHECKED class"checks-od" value="Todos" onClick="filtrarRuleta${tipo}${tipoRuleta}(this);">Todos</input>
			<c:forEach items="${requestScope.categorias}" var="categoria" varStatus="status">
				<input name="categoria" type="checkbox" class="checks-od" value="${ categoria}" onClick="filtrarRuleta${tipo}${tipoRuleta}(this);">${pageInput.categoriasMap[categoria]}</input>
			</c:forEach>
			
		</form>
		<script type="text/javascript">
			function filtrarRuleta${tipo}${tipoRuleta}(elemento){
				if(elemento.checked&&elemento.value=="Todos"){
					$(elemento).parent().children('input:checked').attr('checked', false);
					$(elemento).attr('checked', true);
				}else{
					$(elemento).parent().children("input[value='Todos']").attr('checked', false);
					if($(elemento).parent().children('input:checked').size() == 0){
						$(elemento).parent().children("input[value='Todos']").attr('checked', true);
					}
				}
				
				$.post(contextPath+'/com/cablevision/controller/entretenimiento/filtrarRuleta.do', 
					$(elemento).parent().serialize(),
					function(data) {
						$('#carrusel-${tipo}-${tipoRuleta}').html(data);
						
						$("#carrusel-${tipo}-${tipoRuleta}").jCarouselLite({
							btnNext: 	"#siguiente-${tipo}-${tipoRuleta}",
							btnPrev: 	"#anterior-${tipo}-${tipoRuleta}",
							easing: 	"bouncein",
							mouseWheel: true,
							visible: 	4
						});
						
						tb_init('a.thickbox, area.thickbox, input.thickbox');//pass where to apply thickbox
					}
				);
				
			}
			
		</script>
	</div>
	<div class="clear">
	</div>
	
	<a href="#" class="prev-od hidden-text" id="anterior-${tipo}-${tipoRuleta}">anterior</a>
	<a href="#" class="next-od hidden-text" id="siguiente-${tipo}-${tipoRuleta}">siguiente</a>
			
	<div class="carrousel-od" style="width: 640" id="carrusel-${tipo}-${tipoRuleta}">
		<jsp:include page="ruletaInclude.jsp"/>
	</div>
	<div class="clear"></div>

	
	<c:if test="${ empty pageInput.todos }">
		<br />
		<a href="${pageInput.url }" title="Ver programaci&oacute;n completa" class="vermas color-orange fright" >Ver programaci&oacute;n completa</a>
		<br />
	</c:if>
</div>
	

