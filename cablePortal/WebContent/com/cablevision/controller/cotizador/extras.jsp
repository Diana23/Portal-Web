<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="jstl-c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:directive.page import="java.util.StringTokenizer"/>

<script type="text/javascript">

$(document).ready(function(){
	
	jQuery(':hidden').each(function(){
		if($(this).attr('name') == 'depende'){
			if($(this).val() != ""){
				$(this).parent().hide();
			}
		}
	});
	
});

</script>

<ul id="cotiz-content" class="cotiz-content">
								
	<li>
		<div class="color-orange add-pqt"><strong>Agrega a tu paquete:</strong></div>
	</li>
	
	<netui-data:repeater dataSource="pageInput.extras">
		<li class="wrap-paq-cot">
			<div class="span-5 height-fix-extras">
				<span class="color-orange"> &gt; </span>${container.item.description}<br />
			</div>
			<div class="clear"></div>
			<div class="precio-bordered">
				<label for="check3">Por sólo $ <netui:span value="${container.item.tcPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> mensuales</label> 
				
				<jstl-c:choose>
					<jstl-c:when test="${container.item.extGrupo!=null}">
						<input type="radio" name="extraId${container.item.extGrupo}" value="${container.item.idExtra}" class="fright check" />
					</jstl-c:when>
					<jstl-c:otherwise>
						<input type="checkbox" class="fright check" id="extraId" name="extraId" value="${container.item.idExtra}">
					</jstl-c:otherwise>
				</jstl-c:choose>
				<input type="hidden" name="productId" value="${container.item.normalPrice}" />
				<input type="hidden" name="price" value="${container.item.tcPrice}" />
				<input type="hidden" name="pricenormal" value="${container.item.normalPrice}" />
				<input type="hidden" name="depende" value="${container.item.depende}"/>
				
				<jstl-c:choose>
					<jstl-c:when test="${container.item.numbers!=null}">
						<select name="number" class="fright">
							<netui-data:repeater dataSource="container.item.numbers">
								<option>${container.item}</option>
							</netui-data:repeater>
						</select>
					</jstl-c:when>
				</jstl-c:choose>
				</br><label for="check1" style="font-size: 9px;">*Precio en efectivo $ <netui:span value="${container.item.normalPrice}"> <netui:formatNumber pattern="#,###.00"/> </netui:span> mensuales</label>
			</div>
			
			<!-- 
			
		<jstl-c:choose>
			<jstl-c:when test="${container.index <  (fn:length(pageInput.extras)-1) }">
				si el grupo del extra actual es diferente del grupo del siguiente extra pinto la linea que divide los grupos
		    	<jstl-c:if test="${container.item.extGrupo != pageInput.extras[container.index+1].extGrupo || empty container.item.extGrupo}">
		    		<img src="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000602.gif" class="linea" />
		    	</jstl-c:if>
			</jstl-c:when>
			<jstl-c:otherwise>
				<img src="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000602.gif" class="linea" />
			</jstl-c:otherwise>
		</jstl-c:choose> -->
		
			
		</li>
	</netui-data:repeater>					

</ul>
