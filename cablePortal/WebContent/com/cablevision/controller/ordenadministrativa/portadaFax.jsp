<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>
<table width="741" border="0" align="center" cellpadding="0" cellspacing="0">
	<netui:form action="mostrarPortadaFax">
	  <tr>
	    <td></td>
	  </tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
	  <td><table border="0" cellpadding="0" cellspacing="0" width="100%">
	      <tbody><tr>
	        <td width="2%">
	        </td>
	        <td class="hdr_table" style="font-size:16px;" align="center" width="24%">Portada de Fax</td>
	        <td align="right" width="4%">
	        </td>
	      </tr>
	  </tbody></table></td>
	</tr>
	  <tr>
	    <td><table width="741" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="43"></td>
	        <td width="650" valign="top"><table width="655" border="0" cellspacing="0" cellpadding="0">
	
	          <tr>
	            <td width="650" height="35" align="right" class="titulos_internas3"><strong><br>No. de Orden Administrativa: </strong><netui:label value="${actionForm.noSolicitud}" styleClass="txt_naranja13pad" style="font-size:22px;"/></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td valign="top><strong><font color="#00539C">
	                <table width="650" border="0" cellpadding="0" cellspacing="0" style="font-family:Tahoma, Arial, Verdana; font-size:28px">
	                  <tr>
	                    <td width="300" height="50" class="titulos_internas3">No. de Fax:</td>
	                    <td width="350" class="txt_naranja13pad" style="font-size:22px;">91831605 (en la Ciudad de Mexico)</td>
	                  </tr>
	                  <tr>
	                    <td height="50" class="titulos_internas3">No. de Contrato:</td>
	                    <td class="txt_naranja13pad" style="font-size:22px;"><netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount }"/></td>
	                  </tr>
	                  <tr>
	                    <td height="50" class="titulos_internas3">Fecha:</td>
	                    <td class="txt_naranja13pad" style="font-size:22px;"><netui:label value="${actionForm.creado}"/></td>
	                  </tr>
	                  <tr>
	                    <td height="50" class="titulos_internas3">De:</td>
	                    <td class="txt_naranja13pad" style="font-size:22px;"><netui:label value="${actionForm.nombreCuenta}"/></td>
	                  </tr>
	                  <tr>
	                    <td height="37" valign="top" class="titulos_internas3">Asunto:</td>
	                    <td class="txt_naranja13pad" style="font-size:22px;">Numero de Solicitud: <netui:label value="${actionForm.noSolicitud}"/></td>
	                  </tr>
	              </table></td>
	          </tr>
	
	        </table></td>
	        <td></td>
	      </tr>
	    </table></td>
	  </tr>
	  <tr>
	    <td></td>
	  </tr>
	  <tr>
	    <td height="30" align="right"><font size="1" face="Arial, Helvetica, sans-serif">
	    	</font>
	    </td>
	  </tr>
	</table>
 </netui:form>
</netui:scriptContainer>