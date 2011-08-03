<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui" %>
<div id="wrap-programacion">
	<div class="tab-entre-head">&nbsp;</div>
	<div class="padd-tb-small" id="dv_cbs_categorias">
		<input type="checkbox" id="cb_todos" name="cb_todos" onclick="progFilter(this)" checked="checked" class="cbs-all"/>
		<label for="cb_todos">Todos</label>
		&nbsp;
		<c:forEach items="${requestScope.categorias}" var="categoria" varStatus="status">
			<input type="checkbox" id="cb_${categoria}" name="cb_${categoria}" onclick="progFilter(this)" class="cbs-cats" title="${pageInput.categoriasMap[categoria]}"/>
			<label for="cb_${categoria}">${pageInput.categoriasMap[categoria]}</label>
			&nbsp;
		</c:forEach>
	</div>
	<div class="wrap-table-entreond" id="dv_content_categorias">
		<div class="block block-header" style="background-color: #fff">
			<div class="prog-header nombre">
				<a href="javascript:;" onclick="sortByName(this)" class="sorter tit-tbl-entreond">NOMBRE</a>
			</div>
			<div class="prog-header categoria">
				<a href="javascript:;" onclick="sortByCat(this)" class="sorter tit-tbl-entreond">CATEGOR&Iacute;A</a>
			</div>
		</div>
		<div class="block block-header" style="background-color: #fff">
			<div class="prog-header nombre">
				<a href="javascript:;" onclick="sortByName(this)" class="sorter tit-tbl-entreond">NOMBRE</a>
			</div>
			<div class="prog-header categoria">
				<a href="javascript:;" onclick="sortByCat(this)" class="sorter tit-tbl-entreond">CATEGOR&Iacute;A</a>
			</div>
		</div>
		<c:forEach items="${pageInput.programacion}" var="item" varStatus="rowCounter">
			<div class="block block-cell">
				<div class="prog-cell nombre">${item.titulo}</div>
				<div class="prog-cell categoria">${item.categoria}</div>
			</div>
		</c:forEach>
		<div class="clear"></div>
	</div>
	<div class="tab-entre-foot"></div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery.equalheights.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/framework/skins/cablevision/js/jquery.tinysort.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".block").equalHeights();
	});
</script>