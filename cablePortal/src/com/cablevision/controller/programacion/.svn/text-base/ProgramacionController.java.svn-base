package com.cablevision.controller.programacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.service.IProgramScheduleService;
import com.cablevision.util.SchedulerJob;

/**
 * 
 * @author Crysfel
 *
 */

@Jpf.Controller(
		messageBundles={ @Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion")}
		)
public class ProgramacionController extends PageFlowController {
	private static final long serialVersionUID = 1L;
	private transient IProgramScheduleService programacionService;
	
	/**
	 * Muestra la p\u00E1gina inicial
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward begin(ProgramacionForm form) throws Exception{
		Forward forward = new Forward("success");  
		
		if(StringUtils.isEmpty(form.getCnlCategory())){
			form.setCnlCategory(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("categoria"));
		}
		
		forward.addActionOutput("cnlCategory", form.getCnlCategory());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "programacion.jsp") })
	public Forward findProgramacion(ProgramacionForm form) throws Exception{
		Forward forward = new Forward("success");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = sdf.parse(form.getDate()+" "+form.getTime());
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", LocaleUtils.toLocale("es_MX"));
		String dateToWrite = sdf2.format(date);
		forward.addActionOutput("dateToWrite", dateToWrite);
		forward.addActionOutput("programacion", getProgramacionService().findAllCvScheduleRecordsBetween(date, form.getCnlCategory()));
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "programacion.jsp") })
	public Forward findProgramacionByCategory(ProgramacionForm form) throws Exception{
		Forward forward = new Forward("success");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = sdf.parse(form.getDate()+" "+form.getTime());
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", LocaleUtils.toLocale("es_MX"));
		String dateToWrite = sdf2.format(date);
		forward.addActionOutput("dateToWrite", dateToWrite);
		forward.addActionOutput("programacion", getProgramacionService().findAllCvScheduleRecordsByCategory(date, form.getCategory(), form.getCnlCategory()));
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward findHDProgramacionByCategory(ProgramacionForm form) throws Exception{
		String categoria = getMessageResources("configuracion").getMessage("programacion.hd.categoria");
		if(categoria!=null)
			form.setCnlCategory(categoria);
		return begin(form);
	}
	
	@Jpf.Action(
			forwards = { @Jpf.Forward(name = "success", path = "successGenera.jsp") }
			
	)
	public Forward generaProgramacion() throws Exception{
		Thread t = new Thread(){
			@Override
			public void run() {
				try {
					new SchedulerJob().execute(null);
				} catch (JobExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		t.start();
		
		return new Forward("success");
	}
	
	@Jpf.FormBean
	public static class ProgramacionForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String date;
		private String time;
		private String category;
		private String cnlCategory;
		
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getCnlCategory() {
			return cnlCategory;
		}
		public void setCnlCategory(String cnlCategory) {
			this.cnlCategory = cnlCategory;
		}
	}
	
	public IProgramScheduleService getProgramacionService() {
		if(programacionService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			programacionService = (IProgramScheduleService)context.getBean("ProgramScheduleService");
			
		}
		return programacionService;
	}

	public void setProgramacionService(IProgramScheduleService programacionService) {
		this.programacionService = programacionService;
	}
		
}
