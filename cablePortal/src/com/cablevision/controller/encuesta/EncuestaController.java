package com.cablevision.controller.encuesta;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.IAnswerService;
import com.cablevision.vo.Answer;

/**
 * Page Flow para usar con encuestas
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller()
public class EncuestaController extends ControllerBase {
	
	private static final long serialVersionUID = 1L;
	private transient IAnswerService answerService;
	
	
	/**
	 * Metodo que muestra la encuesta
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws Exception 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp") })
	public Forward begin() throws Exception {
		Forward forward = new Forward("success");
		
		return forward;
	}
	
	/**
	 * Metodo que guarda la respuesta de la encuesta
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp") })
	public Forward guardarRespuesta(EncuestaForm form) throws Exception{
		Forward forward = new Forward("success");
		
		Answer answer = new Answer();
		answer.setAnswer(form.getAnswer());
		answer.setIdquestion(form.getIdquestion());
		answer.setWhy(form.getPorque());
		
		this.getAnswerService().persistAnswer(answer);
		
		return forward;
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
	 * Form bean para encuesta
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	public static class EncuestaForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String answer;
		private Long idquestion;
		private String porque;
		private String secction;
		
		public String getSecction() {
			return secction;
		}
		public void setSecction(String secction) {
			this.secction = secction;
		}
		public String getAnswer() {
			return answer;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		public Long getIdquestion() {
			return idquestion;
		}
		public void setIdquestion(Long idquestion) {
			this.idquestion = idquestion;
		}
		public String getPorque() {
			return porque;
		}
		public void setPorque(String porque) {
			this.porque = porque;
		}

		
	
	}

	public IAnswerService getAnswerService() {
		if(answerService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			answerService = (IAnswerService)context.getBean("AnswerService");
			
		}
		return answerService;
	}
	
	public void setAnswerService(IAnswerService answerService) {
		this.answerService = answerService;
	}
}