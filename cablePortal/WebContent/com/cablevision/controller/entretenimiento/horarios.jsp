<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>

<style>
	.titular {
		margin: 0 0 15px 44px;
		float: left;
		display: block;
		width: 508px;
		height: 30px;
		background: transparent url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png) no-repeat -418px -1874px;
		border-bottom-style: solid;
		border-bottom-width: 1px;
		border-bottom-color: #e5e5e5;
		text-indent: -9999px;
	}
	
	
	.tbl-horario {
		padding-bottom: 2px;
		margin-bottom: 5px;
		padding-left: 0px;
		float: left;
		display: block;
		margin: 0 0 0 90px;
		width: 450px;
	}
	
	.tbl-horario li {
		border-style: solid;
		float: left;
		border-width: 1px;
		border-color: #e5e5e5;
		display: block;
		margin: 0 1px 1px 2px; /*padding:5px;*/
	}
	
	.tbl-horario .ui0 {
		width: 130px;
		height: 20px;
		padding: 5px 0 0 4px; *
		border-color: white;
		height: 26px; *
		width: 140px; *
		padding: 4px 0 0 4px;
	}
	
	.tbl-horario .ui1 {
		width: 130px;
		height: 20px;
		padding: 5px 0 0 4px; *
		height: 26px; *
		width: 140px; *
		padding: 4px 0 0 4px;
	}
	
	.tbl-horario .ui2 {
		width: 150px;
		height: 20px;
		padding: 5px 0 0 4px; *
		width: 105px; *
		height: 26px; *
		padding: 0 0 0 0px;
	}
	
	.tbl-horario .ui3 {
		width: 35px;
		height: 20px;
		padding: 5px 0 0 4px; *
		width: 40px; *
		height: 26px; *
		padding: 0 0 0 0px;
	}
	.btn-tiny:link,.btn-tiny:visited {
		display: block;
		width: 83px;
		height: 24px; *
		height: 25px;
		background: transparent
			url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png)
			no-repeat -505px -2040px;
		border: 0px;
		float: left;
		color: #fff;
		font-size: 10px;
		font-family: "Myriad pro", Times new roman;
		text-transform: uppercase;
		font-style: normal;
		text-align: center;
		letter-spacing: 0px;
		line-height: 22px;
		text-decoration: none;
		font-weight: none;
		padding-top: 1px;
		letter-spacing: 1px;
	}
	
	.btn-tiny:hover,.btn-tiny:active {
		color: #fff;
		text-decoration: none;
		background: transparent
			url(/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002159.png)
			no-repeat -505px -2074px;
	}
	
	.wr-botns {
		margin: 0 0 0 460px;
		float: left;
		display: block;
		width: 508px;
		height: 50px;
		align: right;
	}

	.cabecera{
		font-style: bold;
		text-align: center;
	}
</style>

<br />
<h3 class="titular">Selecciona el horario de tu pel&iacute;cula</h3>

<form id="formPpv" name="formPpv">
	<c:set var="diaTmp" value="" scope="request"/>
	<ul class="tbl-horario">
		<c:choose>
			<c:when test="${!empty pageInput.msg}">
				<div id="msgError" class="error-msg">${pageInput.msg}</div>
			</c:when>
			<c:otherwise>
				<div id="msgError" class="error-msg" style="display:none;"></div>			
			</c:otherwise>
		</c:choose>
	</ul>
	<div id="eventos">
			<ul class="tbl-horario">
				<li class="ui2" id="test" style="text-align: center;">
					<strong>Disponible apartir del</strong>
				</li>
				<li class="ui2"  style="text-align: center;">
					<strong>Hasta</strong>
				</li>
				<li class="ui3">
					<strong>Canal</strong>
				</li>
				<li class="ui3"></li>
			</ul>
		<c:forEach items="${requestScope.ppv}" var="ppv">
			<ul class="tbl-horario">
				<li class="ui2" id="test">
					<netui:label value="${ppv.fechaini}" tagId="fechainiLabel" style="text-transform:capitalize" >
						<netui:formatDate language="es" pattern="EEEE dd MMM yyyy hh:mm" country="MX" />
					</netui:label>
				</li>
				<li class="ui2">
					<netui:label value="${ppv.fechafin}" style="text-transform:capitalize" >
						<netui:formatDate language="es" pattern="EEEE dd MMM yyyy hh:mm" country="MX" />
					</netui:label>
				</li>
				<li class="ui3">${ppv.canal}</li>
				<li class="ui3">
					<input type="radio" class="radio" name="idSolr" id="idSolr" value="${ppv.id}">
				</li>
			</ul>
			<c:set var="diaTmp" value="${ppv.fechainiCorta}" scope="request"/>
		</c:forEach>
		<br>
		<div class="wr-botns">
			<a class="btn-tiny" title="CONTRATAR" href="" name="contLink" id="contLink">CONTRATAR</a>
		</div>
		<br />
		<h3 class="ui2">Nota: El horario mostrado esta en formato de 24 horas</h3>
	</div>
</form>