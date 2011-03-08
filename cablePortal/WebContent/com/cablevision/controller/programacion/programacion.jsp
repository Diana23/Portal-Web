<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
	"date": "<c:out value='${pageInput.dateToWrite}'/>",
	"start": "",
	"end": "",
	"programas":[
		<netui-data:repeater dataSource="pageInput.programacion">
		{
			"isLast": false,
			"logo": "${container.item.cvStationRecord.stnCnlLogo}",
			"canal": "${container.item.cvStationRecord.stnStationNum}",
			"canalTitle": "${container.item.cvStationRecord.stnStationCallSign}",
			"start": "${container.item.compId.skeAirDateTime}",
			"end": "${container.item.skeEndDateTime}",
			"duration": "${container.item.skeDuration}",
			"title":"${container.item.cvProgramRecord.proTitle}",
			"description":"${container.item.cvProgramRecord.proDesc}",
			"tags":"${container.item.cvProgramRecord.proGenreDesc1} ${container.item.cvProgramRecord.proGenreDesc2} ${container.item.cvProgramRecord.proGenreDesc3}"
		},
		</netui-data:repeater>
		{"isLast": true}
	]
}