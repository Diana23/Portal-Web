package com.cablevision.controller.callcenter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.ILeadService;

/**
 * 
 * @author Crysfel
 *
 */

@Jpf.Controller()
public class ContactanosController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private transient ILeadService leadService;
	
	/**
	 * Metodo para mostrar el formulario de contacto
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward begin() throws Exception{
		Forward forward = new Forward("success");
		return forward;
	}
	
	
	/**
	 * Metodo para guardar un lead
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "response.jsp")})
	public Forward saveLead() throws Exception{
		Forward forward = new Forward("success");
		
		
		Enumeration ep  = getRequest().getParameterNames();
		Map<String,String> map = new HashMap<String,String>();
		
		while(ep.hasMoreElements()){
			String parametro = ep.nextElement().toString();
			map.put(parametro, getRequest().getParameter(parametro)!=null?getRequest().getParameter(parametro):"");
		}
		
		//1.- validaciones
		Map<String,String> errors = getLeadService().getValidatedFields(map);
		
		for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> e = (Entry<String, String>)it.next();
			if(errors.containsKey(e.getKey()) && !errors.get(e.getKey()).toString().equals(""))
				errors.put(e.getKey(), errors.get(e.getKey()));
			else
				errors.remove(e.getKey());
		}
		
		if(errors.size()==0){
			
			getLeadService().saveLead(map);
				
			//6.- Enviar correo
			
			//7.- guardar correo y cuenta de usuario
			
			getResponse().getWriter().write("ok");
		}else{
			forward.addActionOutput("success", false);
			forward.addActionOutput("errors", errors.values());
			
			Iterator<Entry<String, String>> it = errors.entrySet().iterator();
			StringBuffer errores = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				errores.append(e.getValue());
				errores.append("<br/>");
			}
			
			getResponse().getWriter().write(errores.toString());
		}
		//}
		
		return null;
		
	}
	
	public ILeadService getLeadService() {
		if(leadService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			leadService = (ILeadService)context.getBean("LeadService");
			
		}
		return leadService;
	}

	public void setLeadService(ILeadService leadService) {
		this.leadService = leadService;
	}

}
