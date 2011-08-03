package com.cablevision.controller.misPagos;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.util.ComparatorUtil;
import com.cablevision.util.Constantes;
import com.cablevision.util.PagosList;
import com.cablevision.util.ResponseToPaymentsQuery;
import com.cablevision.util.RespuestaToMyAccount;


/**
 * Page Flow para usar en el registro de clientes de cablevision
 * para usar los servicios en linea
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller(loginRequired=true)
public class MisPagosController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	transient ToInterfase vitriaClient; 
	
	/**
	 * Metodo para mostrar los pagos de un cliente
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward begin() throws Exception{
		Forward forward = new Forward("success");
		List<MisPagosBean> listaPagos = getMisPagos();
		if(listaPagos !=null && listaPagos.size()>0){
			forward.addActionOutput("pagosLista", getMisPagos());
		}
		return forward;
	}
	
	public List<MisPagosBean> getMisPagos() throws Exception {
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		Calendar cal = Calendar.getInstance();
		DecimalFormat formatNum = new DecimalFormat("00");
		String fecha = formatNum.format(cal.get(Calendar.MONTH)+1)+"/"+String.valueOf(cal.get(Calendar.YEAR));
		ResponseToPaymentsQuery response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toPaymentsQuery(cuenta.getCvNumberAccount(), fecha);
		PagosList[] pagosList = null;
		
		if(response!= null && response.getPagosList()!=null){
			pagosList = response.getPagosList().getPagosList();
		}
		DecimalFormat decFormat = new DecimalFormat("#.00");
		List<MisPagosBean> misPagos = new ArrayList<MisPagosBean>(); 
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if(pagosList!=null && pagosList.length>0){
			for(PagosList p: pagosList){
				MisPagosBean pagoBean = new MisPagosBean();
				pagoBean.setFechaPago(dateFormat.parse(p.getPIN_FLD_AUTH_DATE()));
				pagoBean.setFormaPago(p.getPIN_FLD_DESCR());
				pagoBean.setImporte(decFormat.format(Float.parseFloat(p.getPIN_FLD_AMOUNT())));
				pagoBean.setNoPago(p.getPIN_FLD_TRANS_ID());
				misPagos.add(pagoBean);
			}
		}
		
		Collections.sort(misPagos, new ComparatorUtil());
		
		return misPagos;
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

	/**
	 * llamada al cliente de Vitria
	 */
	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");
			
		}
		return vitriaClient;
	}
	
	/**
	 * Form bean con la informacion del pago
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	@Jpf.FormBean
	public static class MisPagosBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String noPago;
		private String estado;
		private Date fechaPago;
		private String formaPago;
		private String importe;
		
		public String getNoPago() {
			return noPago;
		}
		public void setNoPago(String noPago) {
			this.noPago = noPago;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public Date getFechaPago() {
			return fechaPago;
		}
		public void setFechaPago(Date fechaPago) {
			this.fechaPago = fechaPago;
		}
		public String getFormaPago() {
			return formaPago;
		}
		public void setFormaPago(String formaPago) {
			this.formaPago = formaPago;
		}
		public String getImporte() {
			return importe;
		}
		public void setImporte(String importe) {
			this.importe = importe;
		}
	}
}