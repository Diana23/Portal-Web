package com.cablevision.controller.realizarpago;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;

import com.cablevision.util.Constantes;
import com.cablevision.util.RespuestaToMyAccount;

/**
 * Page Flow para usar en el pago en linea
 * 
 * @author Luis Perez - JWMSolutions 28/09/2009
 *
 */
@Jpf.Controller(
		messageBundles = {
				@Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion")
		},
		loginRequired=true
)
@SuppressWarnings("unchecked")
public class RealizarPagoController extends PageFlowController {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Metodo que muestra la forma de pago en linea
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "parse") })
	public Forward begin() {
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Metodo que transforma la informacion del form de pago
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward parse() {
		Forward forward = new Forward("success");
		LinkedHashMap<Object,Object> aLSBParams = new LinkedHashMap<Object,Object>();
		// obtiene el detalle de la cuenta de usuario de cablevision
		RespuestaToMyAccount cvCuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String account = cvCuenta.getCvNumberAccount();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyHHmm");
		DecimalFormat formatNum = new DecimalFormat("##");
		Random r = new Random();
		String payReference = account+format.format(new Date())+formatNum.format(r.nextInt(100));
		
		aLSBParams.put("referencia", payReference);
		aLSBParams.put("id_company", getMessageResources("configuracion").getMessage("pagos.id_company"));
		aLSBParams.put("id_branch", getMessageResources("configuracion").getMessage("pagos.id_branch"));
		aLSBParams.put("country", getMessageResources("configuracion").getMessage("pagos.country"));
		aLSBParams.put("user", getMessageResources("configuracion").getMessage("pagos.user"));
		aLSBParams.put("pwduser", getMessageResources("configuracion").getMessage("pagos.pwduser"));
		aLSBParams.put("merchant", getMessageResources("configuracion").getMessage("pagos.merchant"));
		aLSBParams.put("account", account);
		aLSBParams.put("tp_operation", getMessageResources("configuracion").getMessage("pagos.tp_operation"));
		aLSBParams.put("type", getMessageResources("configuracion").getMessage("pagos.type"));
		aLSBParams.put("moneda", getMessageResources("configuracion").getMessage("pagos.moneda"));
		aLSBParams.put("urlResponse", getMessageResources("configuracion").getMessage("pagos.urlResponse"));
		
		String sLBURL = getMessageResources("configuracion").getMessage("pagos.url")+"?"+encode(aLSBParams);
		forward.addActionOutput("urlSrc", sLBURL);
		
		return forward;
	}
	
	public String encode(LinkedHashMap<Object,Object> params){
		int nPublicKey = 15245;
		int nModulus = 40991;
		int nNum =0;
		String sString = "";
		String sEncodedString = "";
		
		if(params != null){
			Iterator it = params.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Object, Object> e = (Map.Entry<Object, Object>)it.next();
				sString += e.getKey()+"="+e.getValue()+"&";
			}
			sString = sString.substring(0, sString.length()-1);
		}
		if(sString.length()>0){
			for(int i=0; i<= sString.length()-1; i++){
				nNum = mult((int)sString.charAt(i),nPublicKey, nModulus);
				sEncodedString = sEncodedString+Integer.toHexString(nNum)+"+";
			}
			sEncodedString = "enc="+sEncodedString;
		}
		
		return sEncodedString;
	}
	
	private int mult(int x, int nPublicKey, int nModulus){
		int y = 1;
		int x1 = x;
		for(float i=nPublicKey; i>0; i--){
			while((i/2)==(Math.floor(i/2))){
				x1 = modFunction((x1*x1), nModulus);
				i = (i/2);
			}
			y = modFunction((x1*y),nModulus);
		}
		return y;
	}
	
	private int modFunction(int nMain, int nModulus){
		double res = nMain - (Math.floor((nMain/nModulus))* nModulus);
		return ((Double)res).intValue();
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