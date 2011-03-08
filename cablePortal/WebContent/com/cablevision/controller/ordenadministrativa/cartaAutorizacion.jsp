<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<netui:scriptContainer>
	<tbody>
	  <tr>
	    <td valign="top">
	    <table width="741" border="0" cellpadding="0" cellspacing="0">
	      <tbody>
	      <tr>
	        <td valign="top" width="43"></td>
	        <td valign="top" width="655">
	        <table width="655" border="0" cellpadding="0" cellspacing="0">
	          <tbody>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="right"><netui:label value="${pageInput.fechaHoy}"/></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="35"><font size="3">Cablevisión, S.A. de C.V.<br>
	                  <strong>Departamento de Tesorería: </strong></font></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">
	            Por medio de la presente autorizo a Cablevisión, S.A. de C.V. (“Cablevisión”) a realizar mensualmente el cargo 
	            recurrente a mi Tarjeta de Crédito  por el monto de la factura que corresponda pagar por los servicios 
	            del contrato: <netui:label value="${sessionScope._MI_CUENTA.cvNumberAccount}"/> a nombre de: 
	            ${sessionScope._MI_CUENTA.nombreContacto} ${sessionScope._MI_CUENTA.apellidoPaterno} ${sessionScope._MI_CUENTA.apellidoMaterno}</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">Marque con X en el recuadro el tipo de tarjeta.</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><strong>(En débito sólo aceptamos Banamex y HSBC)</strong></td>
	          </tr>
	          <tr>
	
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><table width="300" border="0" cellpadding="0" cellspacing="0">
	                <tbody><tr>
	                  <td width="40"></td>
	                  <td width="94"><font size="2">Crédito: </font></td>
	                  <td width="40"><font size="2"></font></td>
	                  <td width="75"><font size="2">Débito: </font></td>
	                </tr>
	            </tbody></table></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><strong>Marque con X en el recuadro correspondiente al tipo de tarjeta:</strong></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><table width="500" border="0" cellpadding="0" cellspacing="0">
	                <tbody><tr>
	                  <td width="40"><font size="2"><img src="${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/imagen_cv/cv000226.gif" width="30" height="31"></font></td>
	
	                  <td></td>
	                  <td width="40"><font size="2"></font></td>
	                  <td></td>
	                  <td width="40"><font size="2"></font></td>
	                  <td></td>
	                </tr>
	            </tbody></table></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><table width="655" border="0" cellpadding="0" cellspacing="0">
	              <tbody><tr>
	                <td width="269"><font size="2"><strong>Nombre del Banco o Institución emisora:</strong></font></td>
	                <td valign="bottom" width="386" align="left"><font color="#666666">_________________________________________</font></td>
	              </tr>
	
	            </tbody></table></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><strong>Nombre del titular de la tarjeta, tal como aparece en el plástico:</strong> <font color="#666666">_________________________________</font></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><strong>Número de Tarjeta:</strong></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="20"><font size="1">(Para tarjetas American Express 15 dígitos. Para  Visa y/o Master Card 16 dígitos)</font></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"><strong>Fecha de vencimiento</strong></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;"></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="20"><font size="1">(4 dígitos). </font></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="center"><span style="font-size: 20px;"></span></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="20"><hr style="color: rgb(198, 197, 196);" size="1" width="400"></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="center"><strong><font size="3">Nombre y Firma del Tarjetahabiente</font></strong></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">Asimismo, encontrará adjunto al presente una copia simple de mi identificación oficial.<br>
	              (Si el titular del contrato de Cablevisión,  no es el mismo que el de la tarjeta de crédito, deberá  anexar copia de ambas identificaciones.)<br>
	              <br>
	              Sin más por el momento.</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="center"><font size="3">Atentamente</font></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="70">&nbsp;</td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="center"><span style="font-size: 20px;">${sessionScope._MI_CUENTA.nombreContacto} ${sessionScope._MI_CUENTA.apellidoPaterno} ${sessionScope._MI_CUENTA.apellidoMaterno}</span></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" height="20"><hr style="color: rgb(198, 197, 196);" size="1" width="400"></td>
	          </tr>
	          <tr>
	            <td style="font-family: arial,helvetica,sans-serif; font-size: 13px;" align="center"><strong><font size="3">Nombre y Firma del Suscriptor</font></strong></td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	        </tbody></table></td>
	        <td valign="middle"></td>
	      </tr>
	    </tbody></table></td>
	  </tr>
	  <tr>
	    <td></td>
	  </tr>
	  <tr>
	    <td align="center" height="30"><font size="1" face="Arial, Helvetica, sans-serif">CABLEVISI&Oacute;N 2007.Todos los derechos reservados. </font></td>
	  </tr>
	</tbody>
</netui:scriptContainer>