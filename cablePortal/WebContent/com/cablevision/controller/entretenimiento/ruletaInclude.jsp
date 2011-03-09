<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<ul>
	<c:forEach items="${pageInput.ruleta}" var="item">
		<li style="width: 140 px;">
			<c:url value="/com/cablevision/controller/entretenimiento/mostrarDetalle.do" var="urlDetalle">
				<c:param name="id" value="${item.id }"/>
				<c:param name="tipo" value="${item.tipo }"/>
			</c:url>
					<a class="thickbox text-nounder " href="${urlDetalle}&modal=true&height=540&width=600&scroll=auto">
						<img width="140" alt="" src="${item.rutaMiniatura }">
							<h3 >
								${item.resumen }
							</h3>
						${item.categoria }<br>
						<span class="vermas color-orange">Ver más</span> 
					</a>
			<div class="clear"></div>
		</li>
	</c:forEach>
</ul>

<script type="text/javascript">
	$(document).ready(function(){

			//accordeon
			$("#carrusel-${tipo}-${tipoRuleta}").jCarouselLite({
				btnNext: 	"#siguiente-${tipo}-${tipoRuleta}",
				btnPrev: 	"#anterior-${tipo}-${tipoRuleta}",
				easing: 	"bouncein",
				mouseWheel: true,
				visible: 	4
			});

		
		// Open accordeon
		$('#cabledigital').next().css('display','block');
		$('#cabledigital').removeClass().addClass('menu-acc-sel');
	})
</script>