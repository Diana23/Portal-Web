package com.cablevision.controller.disponibilidad;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.service.IDisponibilidadService;
import com.cablevision.vo.CvDisponibilidadnr;

/**
 * Controller para saber si hay disponibilidad de Internet 
 * mas rápido
 * @author luishpm
 *
 */
@Jpf.Controller()
public class DisponibilidadController extends PageFlowController {
	private static final long serialVersionUID = 1L;
	private transient IDisponibilidadService disponibilidadService;
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "disponibilidad.jsp") })
	protected Forward begin(DisponibilidadFormBean form) throws Exception {
		//Ir a buscar si hay disponibilidad utilizando disponibilidadService
		Forward forward = new Forward("success");
		
		if(StringUtils.isEmpty(form.getZipCode())){
			forward.addActionOutput("error", "Favor de ingesar CP");
			return forward;
		}
		
		CvDisponibilidadnr disponibilidad = new CvDisponibilidadnr();
		disponibilidad.setDispCp(form.getZipCode());		
		List<CvDisponibilidadnr> dispo =  getDisponibilidadService().findCvDisponibilidadnrsByExample(disponibilidad);
		
		forward.addActionOutput("listDisponibilidad", dispo );
		
		return forward;
	}
	
	/**
	 * Callback that is invoked when this controller instance is created.
	 */
	@Override
	protected void onCreate() {
	}

	public IDisponibilidadService getDisponibilidadService() {
		if(disponibilidadService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			disponibilidadService = (IDisponibilidadService)context.getBean("DisponibilidadService");
			
		}		
		return disponibilidadService;
	}
	
	/**
	 * Callback that is invoked when this controller instance is destroyed.
	 */
	@Override
	protected void onDestroy(HttpSession session) {
	}

	@Jpf.FormBean
	public static class DisponibilidadFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String zipCode;

		public String getZipCode() {
			return zipCode;
		}

		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
	
	}
}