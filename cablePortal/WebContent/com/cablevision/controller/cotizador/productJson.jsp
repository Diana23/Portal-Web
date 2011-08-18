<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>

{
	idProduct:${pageInput.product.idProductService},
	idService:${pageInput.product.service.idService},
	price:${pageInput.product.total},
	pricenormal:${pageInput.product.totalnormal},
	extras:'${pageInput.product.descriptionFormato}<netui-data:repeater dataSource="pageInput.product.extras"><span class="color-orange"> > </span> ${container.item.name}<br/></netui-data:repeater>'
}