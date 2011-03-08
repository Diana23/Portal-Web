<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>

{
	"success": "${pageInput.success}",
	"msg": "${pageInput.msg}",
	"errores":[
			<netui-data:repeater dataSource="pageInput.errors" >
			{
				"name":"${container.item.field}",
				"msge":"${container.item.errorMsg}"
			},
			</netui-data:repeater>
			{"isLast": true}
	]
}
