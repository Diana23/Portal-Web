<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>

<!--
<link media="screen, projection" type="text/css" href="${pageContext.request.contextPath}/framework/skins/cablevision/css/ui.css" rel="stylesheet"></link>
<link media="screen, projection" type="text/css" href="${pageContext.request.contextPath}/framework/skins/cablevision/css/layout.css" rel="stylesheet"></link>
-->

<div id="wrap-programacion">
	<netui:form action="programacionPPV" tagId="formaProgPPV">
	<h2 class="side ico-paq-prog padding-left45 margin-bot8">Listado de contenidos de Pago Por Evento</h2>
	<ul class="date-nav">
		<li>
			<c:if test="${pageInput.left_arrow==true}">
				<a title="${pageInput.title_left_arrow}" id="left_arrow" class="prog-arrow arrow-ante-date hidden-text" href="#">Anterior</a>
			</c:if>
		</li>
		<li>
			<div class="middle-navdate color-bluedark ">
				<strong>
					<netui:span value="${pageInput.fhoy}">
						<netui:formatDate pattern="d 'de' MMMM 'de' yyyy" country="${pageInput.country}" language="${pageInput.lang}"/>
					</netui:span>
				</strong>
			</div>
		</li>
		<li>
			<a title="${pageInput.title_right_arrow}" id="right_arrow" class="prog-arrow arrow-sigu-date hidden-text" href="#">Siguiente</a>
		</li>
	</ul>
	<div class="tab-entre-head">
		<div class="padd-tb-small">
			<strong> Buscar: </strong>
			
			<netui:select 
				dataSource="actionForm.fechaProgramacionPPV" 
				optionsDataSource="${pageInput.diasMap}" 
				tagId="selectDays" 
				styleClass="select-prog" 
				onChange="onChangeDaySelectHandler()" 
				nullable="true" 
				nullableOptionText="D&Iacute;A"/>
			
			<select id="selectHour" name="horaProgramacionPPV" class="select-prog" onchange="onChangeHourHandler(this)">
				<option value="">HORARIO</option>
				<c:forEach items="${requestScope.horas}" var="hora">
					<option value="${hora}">
						${pageInput.horasMap[hora]}
					</option>
				</c:forEach>
			</select>

		</div>
		<div class="padd-tb-small">
			<div id="dv_cbs_categorias">
				<strong class="fix-1paddleft"> Filtrar por: </strong>
				<input type="checkbox" id="cb_todos" onclick="progPPVFilterCategories(this)" checked="checked" class="cbs-all"/><label for="cb_todos">Todos</label>
				<c:forEach items="${requestScope.categorias}" var="categoria">
					<input type="checkbox" onclick="progPPVFilterCategories(this)" id="cb-${pageInput.categoriasMap[categoria]}" class="cbs-cats" title="${pageInput.categoriasMap[categoria]}"/>
					<label for="cb-${pageInput.categoriasMap[categoria]}">${pageInput.categoriasMap[categoria]}</label>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="wrap-table-entreond" id="dv_content_categorias">
		<div class="block block-header" style="background-color: #fff">
			<div class="prog-header nombre">
				<a href="javascript:;" onclick="sortByName(this)" class="sorter tit-tbl-entreond">NOMBRE</a>
			</div>
			<div class="prog-header categoria">
				<a href="javascript:;" onclick="sortByCat(this)" class="sorter tit-tbl-entreond">CANAL</a>
			</div>
		</div>
		<div class="block block-header" style="background-color: #fff">
			<div class="prog-header nombre">
				<a href="javascript:;" onclick="sortByName(this)" class="sorter tit-tbl-entreond">NOMBRE</a>
			</div>
			<div class="prog-header categoria">
				<a href="javascript:;" onclick="sortByCat(this)" class="sorter tit-tbl-entreond">CANAL</a>
			</div>
		</div>
		<c:forEach items="${pageInput.programacion}" var="item" varStatus="rowCounter">
			<div class="block block-cell">
				<div class="prog-cell nombre">${item.titulo}
					<br/>
					<netui:span value="${item.fechaini}">
						<netui:formatDate pattern="EEEE HH:mm 'hs'" country="${pageInput.country}" language="${pageInput.lang}"/>
					</netui:span>
					<em class="hidden-ppv-cat" style="display:none">${item.categoria}</em>
				</div>
				<div class="prog-cell categoria">${item.canal}</div>
			</div>
		</c:forEach>
		<div class="clear"></div>
	</div>
	<div class="tab-entre-foot"></div>
	<input type="hidden" id="mDia" value=""/>
	</netui:form>
</div>

<!--<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery-1.4.2.min.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery.equalheights.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery.tinysort.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".block").equalHeights();
		$('.prog-arrow').bind("click", function(){
			$('#selectDays').val($(this).attr('title'));
			$('#formaProgPPV').submit();
			return false;
		});
		
		$('#mDia').val("2000/12/12");
		
		$('#selectDays').children().each(function(){
    			if($(this).val()!=null && !isNaN(Date.parse($(this).val()))){
        			if(Date.parse($(this).val())>Date.parse($('#mDia').val()))
            				$('#mDia').val($(this).val());
    			}
		});
		
		if(Date.parse($('#right_arrow').attr('title'))>Date.parse($('#mDia').val())){
			$('#right_arrow').hide();
		}
		else{
			$('#right_arrow').show();
		}
		
	});
	
	function onChangeDaySelectHandler(){
		$('#formaProgPPV').submit();
		return false;
	}
	
	function onChangeHourHandler(element){
		progPPVFilter($(element));
	}
</script>