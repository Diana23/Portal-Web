package com.cablevision.controller.login;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.beehive.netui.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.IUsuarioPortalService;
import com.cablevision.util.Blowfish;
import com.cablevision.util.Constantes;
import com.cablevision.util.LdapUtil;
import com.cablevision.util.LogFilter;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * Page Flow para usar con el login
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller()
public class LoginController extends ControllerBase {
	private static final Logger _log = Logger.getInstance( ControllerBase.class );
	private static final org.apache.log4j.Logger log = LogFilter.getLoggerInstance();
	private static final long serialVersionUID = 1L;
	private transient ToInterfase vitriaClient;
	private transient IUsuarioPortalService usuarioPortalService;

	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success",  path = "logininth.jsp")
	})
	public Forward showLogin() {
		getRequest().getSession().setAttribute("pageLabel", getRequest().getParameter("pageLabel"));
		return new Forward("success");
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "index", path = "selLogin.jsp") })
	protected Forward showSelLogin() {
		getSession().setAttribute("sel", "sel");
		return new Forward("index");
	}

	/**
	 * Metodo para enviar hacer el login
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success",  path = "success.jsp"),
			@Jpf.Forward(name = "sel",  path = "/com/cablevision/controller/micuenta/index.jsp"),
			@Jpf.Forward(name = "selLogin",  path = "selLogin.jsp"),
			@Jpf.Forward(name = "error",  path = "logininth.jsp"),
			@Jpf.Forward(name = "errorMain",  action= "showSelLogin")
	})
	public Forward begin(final LoginBean form) throws URISyntaxException{
		boolean fromSel = (getSession().getAttribute("sel") != null && getSession().getAttribute("sel").toString().equals("sel"));

		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());

		if(form.getPassword() == null)
			form.setPassword(request.getParameter("password"));
		if(form.getUsuario() == null)
			form.setUsuario(request.getParameter("usuario"));

		if(form.getUsuario()==null&&form.getPassword()==null){
			Forward forward = new Forward("error");
			forward.setName("selLogin");
			return forward;
		}
		PageURL urlError = PageURL.createPageURL(getRequest(), getResponse(), "cablevision_portal_login");
		urlError.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		urlError.setTemplate("https");
		Forward forward = null;
		int intentos = 1;
		try{
			if(form.getUsuario().trim().equals("") || form.getPassword().trim().equals("")){
				if(fromSel && StringUtils.isEmpty(request.getParameter("fromlogin"))){
					forward = new Forward("errorMain");
				}else{
					forward = new Forward("error");
				}
				
				forward.addActionOutput("msg", "Los campos Usuario y contrase\u00F1a son requeridos");
				LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, 0, Constantes.LOG_ESTADO_INGRESO_FALLO);

				return forward;
			}


			CvUsuarioPortal usuarioPortal = getUsuarioPortalService().findCvUsuarioPortalById(form.getUsuario().trim().toUpperCase());
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			if(usuarioPortal != null && usuarioPortal.getCupIntentos() != null){
				intentos = usuarioPortal.getCupIntentos();
			}

			boolean existeUsuario = LdapUtil.existUserInLdap(form.getUsuario().trim());
			String sessionId = Blowfish.encriptar(getRequest().getSession().getId(), Constantes.ENCRIPT_PASSWD).toString();
			try{
				form.setUsuario(form.getUsuario().trim());
//				usuarioPortal = null;
				if(usuarioPortal==null || StringUtils.isEmpty(usuarioPortal.getCupSessionId()) || usuarioPortal.getCupSessionId().equals(sessionId)){
					if(existeUsuario && usuarioPortal != null && usuarioPortal.getCupFechaUltimoIntentoLogin() != null){
						Long oldDate = usuarioPortal.getCupFechaUltimoIntentoLogin().getTime();
						Long minutos = (date.getTime() - oldDate) / 1000;//segundos
						minutos = minutos/60;//minutos
	
						if(usuarioPortal.getCupIntentos() >= 5 && (minutos<60)){
							if(fromSel && StringUtils.isEmpty(request.getParameter("fromlogin"))){
								forward = new Forward("errorMain");
							}else{
								forward = new Forward("error");
							}
							
							forward.addActionOutput("msg", "Tu cuenta se ha bloqueado temporalmente, podr\u00E1s volver a intentarlo dentro de "+(60-minutos)+" minutos");
							LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_BLOQUEO_CONTRASENIA);
							return forward;
						}
	
						if(minutos >= 60){
							usuarioPortal.setCupIntentos(0);
							intentos = 0;
							getUsuarioPortalService().persistCvUsuarioPortal(usuarioPortal);//update o insert con el mismo metodo lo hace
						}
					}
	
					login(form.getUsuario(),form.getPassword());
					
					_log.info("Usuario "+form.getUsuario()+" logueado con exito");
					LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_INGRESO_EXITOSO);
	
					getRequest().getSession().setAttribute(Constantes.SESSION_MI_PASSWD, Blowfish.encriptar(form.getPassword(), Constantes.ENCRIPT_PASSWD));
					Object pageLabel = getSession().getAttribute("pageLabel");
					form.setUrl((String)getSession().getAttribute(Constantes.SESSION_LOGIN_NEXT_URI));
					
					if(existeUsuario){
						if(usuarioPortal != null){
							Long oldPassword = usuarioPortal.getCupFechaUltimaContrasena().getTime();
							Long dias = (date.getTime() - oldPassword ) / 1000;//segundos
							dias = dias / 60;//minutos
							dias = dias / 60;//horas
							dias = dias / 24;//dias
	
							//pregunto si el usuario tiene la fecha de la ultima contrasena > a 90 dias
							if(dias > 90){
								getRequest().getSession().setAttribute(Constantes.CONTRASENA_EXPIRADA, "true");
							}
	
							usuarioPortal.setCupIntentos(0);//si el login es correcto pone el numero de intentos en 0
						}else{
							usuarioPortal = new CvUsuarioPortal();
							usuarioPortal.setCupIdusuario(form.getUsuario().trim().toUpperCase());
							usuarioPortal.setCupFechaUltimaContrasena(time);
							usuarioPortal.setCupFechaUltimoIntentoLogin(time);
							usuarioPortal.setCupIntentos(0);
						}
						//agrego el sessionId si el login es correcto
						usuarioPortal.setCupSessionId(sessionId);
						//guardo el usuario_portal
						getUsuarioPortalService().persistCvUsuarioPortal(usuarioPortal);
					}
					
					if(setCuentaEnSesion(form.getUsuario())){
						if(fromSel) {
							getSession().removeAttribute(Constantes.SESSION_LOGIN_NEXT_URI);
	
							if(StringUtils.isEmpty(form.getUrl())){
								final PageURL url = PageURL.createPageURL(getRequest(), getResponse(), "servicios_enlinea_inicio");
								url.setTemplate("defaultDesktop");
								url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
								url.setForcedAmpForm(false);
								form.setUrl(url.toString());
							}
						} else {
							
							final PageURL url = PageURL.createPageURL(getRequest(), getResponse(), (String)getRequest().getSession().getAttribute("pageLabel"));
							url.setTemplate("defaultDesktop");
							url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
							url.setForcedAmpForm(false);

							forward = new Forward("success");
							forward.addActionOutput("success", url.toString());
							getSession().setAttribute(Constantes.USUARIO_EN_VITRIA, "true");
						}
						if(!fromSel)
							forward.addActionOutput("currentPageLabel", pageLabel);
						
						getSession().setAttribute("fotoUsuario", usuarioPortal.getCupFoto());
						getSession().removeAttribute(Constantes.SESSION_LOGIN_NEXT_URI);
					}else{
//						El usuario esta en ldap pero no tiene cuenta en Sybel, redireccionar a home, no puede
//						accesar servicios en linea
						String pageLabelSel = getRequest().getSession().getAttribute("pageLabel")!=null?
												(String)getRequest().getSession().getAttribute("pageLabel"):"cablevision_portal_page_home";
						final PageURL url = PageURL.createPageURL(getRequest(), getResponse(), pageLabelSel);
						url.setTemplate("defaultDesktop");
						url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
						url.setForcedAmpForm(false);
						if(fromSel){
							forward = new Forward(new URI(url.toString()));
						}else{
							forward = new Forward("success");
							forward.addActionOutput("success", url.toString());
						}
						getSession().setAttribute(Constantes.USUARIO_EN_VITRIA, "false");
						return forward;
					}
					getSession().setAttribute("form", form);
				}else{
					forward = new Forward("error");
					forward.addActionOutput("msg", "El usuario ya tiene una sesi\u00F3n en uso, favor de esperar a que la sesi\u00F3n sea terminada para iniciar otra");
				}
			}catch(LoginException e){
				if(existeUsuario){
					if(usuarioPortal != null){
						usuarioPortal.setCupFechaUltimoIntentoLogin(time);//se manda la actual para que se cuente desde la ultima vez que intento entrar
						usuarioPortal.setCupIntentos(usuarioPortal.getCupIntentos()+1);
					}
					else{
						usuarioPortal = new CvUsuarioPortal();
						usuarioPortal.setCupIdusuario(form.getUsuario().trim().toUpperCase());
						usuarioPortal.setCupFechaUltimaContrasena(time);
						usuarioPortal.setCupFechaUltimoIntentoLogin(time);
						usuarioPortal.setCupIntentos(1);//si no esta registrado se pone 1 en numero  de intentos
					}
					getUsuarioPortalService().persistCvUsuarioPortal(usuarioPortal);//update o insert con el mismo metodo lo hace
				}

				if(usuarioPortal != null && usuarioPortal.getCupIntentos() != null){
					intentos = usuarioPortal.getCupIntentos();
				}

				_log.info("Usuario o contrase\u00F1a invalido");
				urlError.addParameter("msg", "Usuario o contrase\u00F1a incorrecta, por favor verifica tus datos");
				forward = new Forward("error");
				forward.addActionOutput("msg", "Usuario o contrase\u00F1a incorrecta, por favor verifica tus datos");

				LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_INGRESO_FALLO);

				if(existeUsuario){
					int intentosRestan = 5 - usuarioPortal.getCupIntentos();//cuantos intentos le restan al usuario
					if(intentosRestan > 0){
						if(intentosRestan == 1){
							forward.addActionOutput("msg", "Usuario o contrase\u00F1a incorrecta, por favor verifica tus datos<br/>Estimado suscriptor, te queda "+intentosRestan+" intento");
						}else{
							forward.addActionOutput("msg", "Usuario o contrase\u00F1a incorrecta, por favor verifica tus datos<br/>Estimado suscriptor, te quedan "+intentosRestan+" intentos");
						}
					}else{
						forward.addActionOutput("msg",null);
						forward.addActionOutput("popup", "mostrar");//variable que indica que se mostrara el popup
					}
				}

				if(fromSel && StringUtils.isEmpty(request.getParameter("fromlogin"))) {
					if(forward.getName().equals("error")) {
						forward.setName("errorMain");
					} else {
						getSession().removeAttribute("sel");
						forward.setName("sel");
						forward = new Forward(new URI(form.getUrl()));
						getSession().setAttribute(Constantes.USUARIO_EN_VITRIA, "true");
					}
				}

				return forward;
			}
		}catch (RemoteException e) {
			e.printStackTrace();
			forward = new Forward("error");
			urlError.addParameter("msg", "Hubo  un error, intente mas tarde");
			forward.addActionOutput("msg", "Hubo  un error, intente mas tarde");
			LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_INGRESO_FALLO);
		}catch(ServiceException e){
			doLogout(true, form.getUsuario());
			e.printStackTrace();
			forward = new Forward("error");
			urlError.addParameter("msg", "Hubo  un error, intente mas tarde");
			forward.addActionOutput("msg", "Hubo  un error, intente mas tarde");
			LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_INGRESO_FALLO);
		}catch(ErrorVitriaException e){
			doLogout(true, form.getUsuario());
			e.printStackTrace();
			forward = new Forward("error");
			urlError.addParameter("msg", "Hubo  un error, intente mas tarde");
			forward.addActionOutput("msg", "Hubo  un error, intente mas tarde");
			LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, intentos, Constantes.LOG_ESTADO_INGRESO_FALLO);
		}
		catch(Exception e){
			doLogout(true, form.getUsuario());
			e.printStackTrace();
			forward = new Forward("error");
			urlError.addParameter("msg", "Hubo  un error, intente mas tarde");
			forward.addActionOutput("msg", "Hubo  un error, intente mas tarde");
			LogFilter.log(log, form.getUsuario(), Constantes.LOG_ACCION_ENTRADA, 0, Constantes.LOG_ESTADO_INGRESO_FALLO);
		}
		if(fromSel && StringUtils.isEmpty(request.getParameter("fromlogin"))) {
			if(forward!= null && forward.getName().equals("error")) {
				forward.setName("errorMain");
			} else {
				forward = new Forward(new URI(form.getUrl()));
				forward.setRedirect(true);
				getSession().removeAttribute("sel");
			}
		}else if(fromSel && StringUtils.isNotEmpty(request.getParameter("fromlogin"))){
			forward = new Forward("success");
			forward.addActionOutput("success", form.getUrl());
			getSession().removeAttribute("sel");
		}
		if(forward==null){
			forward = new Forward(new URI(urlError.toString()),true);
		}
		return forward;
	}

	/**
	 * Va a vitria por la cuenta del usuario y la pone en sesion
	 * @return true si se encontr\u00F3 cuenta de usuario cablevision, de lo contrario false
	 * @throws RemoteException
	 * @throws ServiceException
	 * @throws ErrorVitriaException Si hubo un error en vitria
	 */
	private boolean setCuentaEnSesion(final String userAccount) throws RemoteException, ServiceException, ErrorVitriaException{
		try{
			getSession().setAttribute(Constantes.SESSION_ACCOUNT_ID, userAccount.trim());
			
			if(!isUserInRole("WEBPORTALADMINISTRATOR")&&!isUserInRole("CONTRIBUIDOR")
					&&!isUserInRole("REVIEWER")&&!isUserInRole("RH")){
				final RespuestaToMyAccount response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(userAccount.trim());
				if(response==null){
					_log.info("Sin usuario en siebel");
					//Si el usuario tiene algun rol dejar pasar excepcion, no es usuario siebel
					if(!isUserInRole("WEBPORTALADMINISTRATOR")&&!isUserInRole("CONTRIBUIDOR")
							&&!isUserInRole("REVIEWER")&&!isUserInRole("RH")){
						throw new RuntimeException();
					}else{
						return false;
					}
				}else{
					com.cablevision.util.Error error = response.getCvErrorCode();
					if(error!=null && error.getCvErrorCode()!= null && !error.getCvErrorCode().equals("") 
							&& !error.getCvErrorCode().equals("0") ){
						_log.info("Error en Siebel  "+error.getCvErrorMessage());
						if(!isUserInRole("WEBPORTALADMINISTRATOR")&&!isUserInRole("CONTRIBUIDOR")
								&&!isUserInRole("REVIEWER")&&!isUserInRole("RH")){
							throw new ErrorVitriaException(error.getCvErrorMessage());
						}
						//El usuario no esta en siebel
						return false;
					}
					_log.info("Con usuario en siebel");
					getSession().setAttribute(Constantes.SESSION_MI_CUENTA, response);
					
					return true;
				}
			}else{
				return false;
			}
		}catch (RemoteException e) {
			//Si el usuario tiene algun rol dejar pasar excepcion, no es usuario siebel
			if(!isUserInRole("WEBPORTALADMINISTRATOR")&&!isUserInRole("CONTRIBUIDOR")
					&&!isUserInRole("REVIEWER")&&!isUserInRole("RH")){
				throw e;
			}else{
				return false;
			}

		}catch (ServiceException e) {
			//Si el usuario tiene algun rol dejar pasar excepcion, no es usuario siebel
			if(!isUserInRole("WEBPORTALADMINISTRATOR")&&!isUserInRole("CONTRIBUIDOR")
					&&!isUserInRole("REVIEWER")&&!isUserInRole("RH")){
				throw e;
			}else{
				return false;
			}
		}


	}

	@Jpf.Action
	public Forward desconectar(LoginBean form) throws URISyntaxException {
		logout(true);
		PageURL url = PageURL.createPageURL(getRequest(), getResponse(), getRequest().getParameter("pageLabel"));
		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setForcedAmpForm(false);
		url.setTemplate("defaultDesktop");

		return new Forward(new URI(url.toString()),true);
	}

	private void doLogout(boolean invalidateSessions, String usuario) {
		LogFilter.log(log, usuario, Constantes.LOG_ACCION_SALIDA, 0, Constantes.LOG_ESTADO_SALIDA_EXISTOSO);
		logout(invalidateSessions);
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
	 * Bean Class para usar con el login
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	@Jpf.FormBean
	public static class LoginBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		private String usuario;
		private String password;
		private boolean recordar=false;
		private String url;

		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public boolean isRecordar() {
			return recordar;
		}
		public void setRecordar(boolean recordar) {
			this.recordar = recordar;
		}
	}

	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");
		}
		return vitriaClient;
	}

	private IUsuarioPortalService getUsuarioPortalService() {
		if (usuarioPortalService == null) {
			ApplicationContext context = (ApplicationContext) getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			usuarioPortalService = (IUsuarioPortalService) context.getBean("UsuarioPortalService");
		}
		return usuarioPortalService;
	}
}
