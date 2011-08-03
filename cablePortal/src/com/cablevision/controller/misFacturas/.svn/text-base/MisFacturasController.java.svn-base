package com.cablevision.controller.misFacturas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.util.Constantes;
import com.cablevision.util.FacturaListado;
import com.cablevision.util.PaymentsLine;
import com.cablevision.util.RespuestaToBillingLastQuery;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.Cargo;
import com.pb.e2.vault.beans.xsd.Attachment;
import com.pb.e2.vault.service.VaultService;
import com.pb.e2.vault.service.VaultServicePortType;
import com.pb.e2.vault.service.VaultService_Impl;

/**
 * Page Flow para usar en la seccion de mis pagos
 * 
 * @author Luis Perez - JWMSolutions 28/09/2009
 *
 */
@Jpf.Controller( messageBundles = { @Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )} )
public class MisFacturasController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	transient ToInterfase vitriaClient;

	/**
	 * Metodo para mostrar las facturas de un cliente
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward begin() {
		Forward forward = new Forward("success");
		return forward;
	}

	/**
	 * Metodo para mostrar el pdf de la factura
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "error", path = "error.jsp") })
	public Forward muestraFactura() throws Exception {
		Forward forward = new Forward("error");

		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.YEAR)==Integer.parseInt(getRequest().getParameter("anio"))){
			if(Integer.parseInt(getRequest().getParameter("mes"))>(cal.get(Calendar.MONTH)+1)){
				forward.addActionOutput("errores", "Aun no se factura este mes");
				return forward;
			}
		}

		String fechaFactura = getRequest().getParameter("mes")+"/"+getRequest().getParameter("anio");

		ByteArrayOutputStream baosPDF = null;
		try{
			baosPDF = this.generarReporte(fechaFactura,forward);

			if(baosPDF==null){
				return forward;
			}

			StringBuffer sbFilename = new StringBuffer();
			sbFilename.append("filename_");
			sbFilename.append(System.currentTimeMillis());
			sbFilename.append(".pdf");

			this.getResponse().setHeader("Cache-Control", "max-age=30");
			this.getResponse().setContentType("application/pdf");

			StringBuffer sbContentDispValue = new StringBuffer();
			sbContentDispValue.append("inline");
			sbContentDispValue.append("; filename=");
			sbContentDispValue.append(sbFilename);

			this.getResponse().setHeader("Content-disposition",sbContentDispValue.toString());
			this.getResponse().setContentLength(baosPDF.size());

			ServletOutputStream sos;
			sos = this.getResponse().getOutputStream();
			baosPDF.writeTo(sos);
			sos.flush();
		}catch(ErrorVitriaException e){
			forward.addActionOutput("errores", e.getMessage());
			return forward;
		}finally {
			if (baosPDF != null) {
				baosPDF.reset();
			}
		}
		return null;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "pagoEnLinea.jsp") })
	public Forward muestraFacturasPago() throws Exception {
		Forward forward = new Forward("success");
		
		String anio = getRequest().getParameter("anio");
		String mes = getRequest().getParameter("mes");
		if(StringUtils.isBlank(anio) && StringUtils.isBlank(mes) || StringUtils.equals(anio, "0") && StringUtils.equals(mes, "0")) {
			Calendar cal = Calendar.getInstance();
			anio = Integer.toString(cal.get(Calendar.YEAR));
			mes = Integer.toString(cal.get(Calendar.MONTH) + 1);
		}
		String fechaFactura = anio + "/" + mes;

		RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);

		RespuestaToBillingLastQuery response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toBillingLastQuery(account.getCvNumberAccount(), fechaFactura);
		ValidateErrors.validateErrorResponse(response.getCvError(), getMessageResources("mensajeError"));

		PaymentsLine[] arrayOfPayments = null;
		ArrayList<PaymentsLine> referencias = new ArrayList<PaymentsLine>();
		if(response != null && response.getPaymentsLine()!=null){
			arrayOfPayments = response.getPaymentsLine().getPaymentsLine();
			for(int i=0; i<arrayOfPayments.length;i++){
				referencias.add(arrayOfPayments[i]);
			}
		}

		forward.addActionOutput("listaRef", referencias);
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "error", path = "sinfactura.jsp") })
	public Forward imprimeFactura() {
		return getFactura("application/pdf");
	}


	@Jpf.Action(forwards = { @Jpf.Forward(name = "error", path = "sinfactura.jsp") })
	public Forward descargaFactura() {
		return getFactura("application/force-download");
	}

	private Forward getFactura(String contentType) {
		Forward forward = new Forward("error");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			VaultService service = new VaultService_Impl();
			VaultServicePortType servicePortType = service.getVaultServiceHttpSoap12Endpoint();

			RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
			String anio = getRequest().getParameter("anio");
			String mes = getRequest().getParameter("mes");
			if(StringUtils.isBlank(anio) && StringUtils.isBlank(mes) || StringUtils.equals(anio, "0") && StringUtils.equals(mes, "0")) {
				Calendar cal = Calendar.getInstance();
				anio = Integer.toString(cal.get(Calendar.YEAR));
				mes = Integer.toString(cal.get(Calendar.MONTH) + 1);
			}
			String statementDate = anio + "/" + mes;
			String accountNumber = account.getCvNumberAccount();

			// TODO borrar el hardcoded
//			String statementDate = "2010/06";
//			String accountNumber = "11703737";

//			String statementDate = "2010/10";
//			String accountNumber = "30139948";

			String dbName = "Invoices_db";

			Attachment attachment = servicePortType.getDocument(statementDate, accountNumber, dbName);
			String fileNameValue = attachment.getFileName();
			String contentTypeValue = attachment.getContentType();
			byte[] binaryDataValue = attachment.getBinaryData();

			if(StringUtils.isEmpty(fileNameValue)){
				return forward;
			}

			baos.write(binaryDataValue);
//			if(baos==null){
//			return forward;
//			}

			this.getResponse().setHeader("Cache-Control", "max-age=30");
			this.getResponse().setContentType(contentType);

			StringBuffer sbContentDispValue = new StringBuffer();
			sbContentDispValue.append("inline");
			sbContentDispValue.append("; filename=");
			sbContentDispValue.append(fileNameValue + ".pdf");

			this.getResponse().setHeader("Content-disposition",sbContentDispValue.toString());
			this.getResponse().setContentLength(baos.size());

			ServletOutputStream sos;
			sos = this.getResponse().getOutputStream();
			baos.writeTo(sos);
			sos.flush();
		} catch (IOException e) {
			forward.addActionOutput("errores", e.getMessage());
			return forward;
		}catch(Exception e){
			return forward;
		}finally {
			if (baos != null) {
				baos.reset();
			}
		}
		return null;

	}


	@SuppressWarnings("unchecked")
	private ByteArrayOutputStream generarReporte(String fechaFactura,Forward forward) throws IOException, JRException, Exception{
		//TODO cambiar la forma de obtener la url
		GenericURL urltmp = GenericURL.createGenericURL(getRequest(), getResponse());
		urltmp.setTemplate("urlApp");
		urltmp.setPath(getRequest().getContextPath());
		String url = urltmp.toString();

		InputStream is = null;
		String path = "/reportes/reportFactura.jasper";
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();

		RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);

		RespuestaToBillingLastQuery response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toBillingLastQuery(account.getCvNumberAccount(), fechaFactura);
		try{
			ValidateErrors.validateErrorResponse(response.getCvError(), getMessageResources("mensajeError"));
		}catch(ErrorVitriaException e){
			forward.addActionOutput("errores", e.getMessage());
			return null;
		}


		FacturaListado[] arrayFacturas = null;
		if(response!=null && response.getFacturaListado()!=null){
			arrayFacturas = response.getFacturaListado().getFacturaListado();
		}else{
			forward.addActionOutput("errores", "No se encontraron datos para generar la factura");
		}

		Collection col = new ArrayList();
		if(arrayFacturas!=null){
			for(int i = 0; i< arrayFacturas.length; i++){
				Cargo cargo = new Cargo(arrayFacturas[i].getConcepto(),arrayFacturas[i].getFecha(), arrayFacturas[i].getCantidad());
				col.add(cargo);
			}
		}

		HashMap parametros =  new HashMap(); 
		parametros.put("NOMBRE",account.getNombreContacto()+" "+account.getApellidoPaterno()+" "+account.getApellidoMaterno());
		parametros.put("DIRECCION",account.getCvMailAddres());
		parametros.put("NO_CONTRATO",account.getCvNumberAccount());
		parametros.put("APP_URL", url);

		parametros.put("TOTAL_PAGAR", response.getCvTotal()==null?"0":response.getCvTotal());
		parametros.put("SALDO_MES_ANTERIOR", response.getPIN_FLD_DUE()==null?"0":response.getPIN_FLD_DUE());
		parametros.put("TOTAL_CARGOS_MES", response.getTotalAmountCharges()==null?"0":response.getTotalAmountCharges());
		parametros.put("FAC_MES_ANTERIOR", response.getPrevAmtDue()==null?"0":response.getPrevAmtDue());

		parametros.put("FECHA_LIMITE_PAGO", response.getPaymentDueDate());

		PaymentsLine[] arrayOfPayments = null;
		if(response != null && response.getPaymentsLine()!=null){
			arrayOfPayments = response.getPaymentsLine().getPaymentsLine();
			for(int i=0; i<arrayOfPayments.length;i++){
				parametros.put("REFERENCIA_"+(i+1), 
						arrayOfPayments[i].getCV_FLD_BANK_NAME()+" "+arrayOfPayments[i].getCV_FLD_BANK_LINE());
			}

		}


		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(col);
		is = this.getServletContext().getResourceAsStream(path);
		if(is!= null ){
			JasperReport jr = (JasperReport)JRLoader.loadObject(is);
			byte[] pdfBytes = JasperRunManager.runReportToPdf(jr, parametros, ds);
			baosPDF.write(pdfBytes);
		}

		if(response == null || response.getCvTotal()== null){
			return null;
		}
		return baosPDF;
	}

	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");

		}
		return vitriaClient;
	}



	/**
	 * Callback that is invoked when this controller instance is created.
	 */
	@Override
	protected void onCreate() {
	}

	/**
	 * Callback that is invoked when this controller instance is destroyed.
	 */
	@Override
	protected void onDestroy(HttpSession session) {
	}


}