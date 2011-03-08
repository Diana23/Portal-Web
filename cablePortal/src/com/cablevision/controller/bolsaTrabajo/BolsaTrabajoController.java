package com.cablevision.controller.bolsaTrabajo;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.IBolsaTrabajoService;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.MailUtil;
import com.cablevision.util.PasswordUtil;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.util.UcmUtil;
import com.cablevision.util.ValidarPasswordUtil;
import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvUser;
import com.cablevision.vo.Vacante;

@Jpf.Controller(simpleActions = { @Jpf.SimpleAction(name = "begin", action = "verLogin")},
				messageBundles = { @Jpf.MessageBundle(bundlePath = "com.cablevision.controller.bolsaTrabajo.bolsaTrabajo", bundleName="bolsaTrabajoBundle"),
								   @Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion")})
public class BolsaTrabajoController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(BolsaTrabajoController.class);
	private transient IBolsaTrabajoService bolsaTrabajoService;
	private CvUser currentUser;

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "verVacantes"),
			 				 @Jpf.Forward(name = "new", path = "formularioRegistro.jsp")
						   },
				validationErrorForward = @Jpf.Forward(name="fail", path= "index.jsp"), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "email", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.emailpass")) }
	)
	public Forward iniciarSesion(com.cablevision.controller.bolsaTrabajo.BolsaTrabajoController.BolsaTrabajoForm form) {
		Forward forward = null;
		try{
			if(form==null || form.getEmail()==null){
				forward = new Forward("new");
				forward.addOutputForm(new BolsaTrabajoForm());
				forward.addActionOutput("new", true);
				return forward;
			}
			
			CvUser user = getBolsaTrabajoService().findCvUserById(form.getEmail());
			if(user == null){
				forward = new Forward("new");
				forward.addOutputForm(new BolsaTrabajoForm());
				forward.addActionOutput("errors", "Usuario no registrado, registra tus datos para iniciar sesi\u00F3n.");
				forward.addActionOutput("new", true);
			}else if(user !=null && !user.getPass().equals(PasswordUtil.getEncodedPassword(form.getPassword()))){
				BolsaTrabajoForm formOut = new BolsaTrabajoForm();
				formOut.setEmail(form.getEmail());
				forward = new Forward("fail");
				forward.addActionOutput("errors", "Contrase\u00F1a incorrecta.");
				forward.addOutputForm(formOut);
			}else{
				forward = new Forward("success");
				this.setCurrentUser(user);
				forward.addActionOutput("usuario", this.getCurrentUser());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward cerrarSesion() {
		Forward forward = new Forward("success");
		this.setCurrentUser(null);
		forward.addActionOutput("usuario", null);
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "verBienvenida")}, 
				validationErrorForward = @Jpf.Forward(name="fail", path="formularioRegistro.jsp"), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "confirmPassword", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.confirmapass")), 
										  @Jpf.ValidatableProperty(propertyName="email", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.email"), validateEmail=@Jpf.ValidateEmail(messageKey="error.formato.email")), 
										  @Jpf.ValidatableProperty(displayName="apellido materno", propertyName="materno", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.materno"),
												                   validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.materno")), 
										  @Jpf.ValidatableProperty(displayName="El nombre", propertyName="nombre", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.name"), 
												  				   validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.nombre")), 
										  @Jpf.ValidatableProperty(propertyName="password", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.pass"), validateValidWhen=@Jpf.ValidateValidWhen(messageKey="error.confirmacion.pass", condition="${actionForm.confirmPassword == actionForm.password}")), 
										  @Jpf.ValidatableProperty(displayName="apellido paterno", propertyName="paterno", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.paterno"),
												  				   validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.paterno")) })
	public Forward registrar(com.cablevision.controller.bolsaTrabajo.BolsaTrabajoController.BolsaTrabajoForm form) throws Exception {
		Forward forward = new Forward("success");
		Boolean isValid = true;
			  
	  //Valida que se teclee correcto lo del reCaptcha
	  if(!ReCaptchaUtil.isValido(getRequest())){
	   forward = new Forward("fail");
	   forward.addActionOutput("msg", "El Texto no coincide con la imagen");
	   isValid = false;
	  } else {
		
		//validar el formulario
		if (isValid){
			if(form.validate()){
				//Validar que no exista el usuario
				if(getBolsaTrabajoService().findCvUserById(form.getEmail())!=null){
					addActionError("email", "error.email.repetido", null);
					return new Forward("fail");
				}
				
				String pass = form.getPassword();
				boolean valid = ValidarPasswordUtil.validatePassword(pass);
				if(!valid) {
					addActionError("password", "error.formato.password", null);
					return new Forward("fail");
				}
				
				CvUser cvUser = new CvUser();
				cvUser.setId(Long.valueOf(getBolsaTrabajoService().getCvUserNextSeqValue()));
				cvUser.setName(form.getNombre().toUpperCase(Locale.ENGLISH));
				cvUser.setLastname(form.getPaterno().toUpperCase(Locale.ENGLISH));
				cvUser.setSecondlastname(form.getMaterno().toUpperCase(Locale.ENGLISH));
				cvUser.setEmail(form.getEmail());
				cvUser.setPass(PasswordUtil.getEncodedPassword(form.getPassword()));
				cvUser.setType("USER");
				cvUser.setFecha(new Date());
				try{
					getBolsaTrabajoService().persistCvUser(cvUser);
					this.setCurrentUser(cvUser);
					forward.addActionOutput("usuario", this.getCurrentUser());
					log.info("El nuevo Id de usuario es " + cvUser.getId());
				}catch(Exception e){
					log.error("registrar failed");
					e.printStackTrace();
				}
			}else{
				forward = new Forward("fail");
			}
		}
	  }
		
	  return forward;
	}

	

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "informacionCv.jsp"), @Jpf.Forward(name = "start", path = "index.jsp") })
	public Forward actualizaCv() {
		Forward forward = new Forward("success");
		try{
			if(getCurrentUser()==null){
				forward = new Forward("start");
				forward.addActionOutput("errors", "Necesitas registrarte para agregar tu CV.");
				return forward;
			}

			BolsaTrabajoForm formBean = new BolsaTrabajoForm();
			formBean.setEmail(getCurrentUser().getEmail());
			formBean.setMaterno(getCurrentUser().getSecondlastname());
			formBean.setNombre(getCurrentUser().getName());
			formBean.setPaterno(getCurrentUser().getLastname());
			forward.addOutputForm(formBean);
			
			//obtenemos datos de curriculum
			CvCurriculum curriculum = getBolsaTrabajoService().findCvCurriculumById(getCurrentUser().getEmail());
			
			if(curriculum!=null){
				getCurrentUser().setCurriculum(curriculum);
				
				//Datos Personales
				formBean.setDireccion(curriculum.getAddress());
				formBean.setEdoCivl(curriculum.getStatusCiv());
				formBean.setEscolaridad(curriculum.getSchool());
				
				if(curriculum.getDateBorn()!=null){
					String[] fecha = curriculum.getDateBorn().split("-");
					if(fecha!= null){
						formBean.setDiaNacimiento(fecha[0]);
						formBean.setMesNacimiento(fecha[1]);
						formBean.setAnioNacimiento(fecha[2]);
					}
				}
				
				formBean.setStatusEscolar(curriculum.getStatusSchool());
				formBean.setTelefono(curriculum.getPhone());
				forward.addOutputForm(formBean);
				
				//Experiencia Laboral
				ExperienciaLaboralForm explForm = new ExperienciaLaboralForm();
				explForm.setEmpresa1(curriculum.getNameEmp1());
				explForm.setPuesto1(curriculum.getPlaceEmp1());
				if(StringUtils.isNotEmpty(curriculum.getStartEmp1())){
					String[] fechaInicio1 = curriculum.getStartEmp1().split("-");
					if(fechaInicio1!= null){
						explForm.setDiaInicio1(fechaInicio1[0]);
						explForm.setMesInicio1(fechaInicio1[1]);
						explForm.setAnioInicio1(fechaInicio1[2]);
					}
				}
				
				if(StringUtils.isNotEmpty(curriculum.getEndEmp1())){
					String[] fechaFin1 = curriculum.getEndEmp1().split("-");
					if(fechaFin1!= null){
						explForm.setDiaFin1(fechaFin1[0]);
						explForm.setMesFin1(fechaFin1[1]);
						explForm.setAnioFin1(fechaFin1[2]);
					}
				}
				
				explForm.setResponsabilidades1(curriculum.getDescriptionEmp1());
				
				explForm.setEmpresa2(curriculum.getNameEmp2());
				explForm.setPuesto2(curriculum.getPlaceEmp2());
				if(StringUtils.isNotEmpty(curriculum.getEndEmp1())){
					String[] fechaInicio2 = curriculum.getStartEmp2().split("-");
					if(fechaInicio2!= null){
						explForm.setDiaInicio2(fechaInicio2[0]);
						explForm.setMesInicio2(fechaInicio2[1]);
						explForm.setAnioInicio2(fechaInicio2[2]);
					}
				}
				if(StringUtils.isNotEmpty(curriculum.getEndEmp1())){
					String[] fechaFin2 = curriculum.getEndEmp2().split("-");
					if(fechaFin2!= null){
						explForm.setDiaFin2(fechaFin2[0]);
						explForm.setMesFin2(fechaFin2[1]);
						explForm.setAnioFin2(fechaFin2[2]);
					}
				}
				explForm.setResponsabilidades2(curriculum.getDescriptionEmp2());
				forward.addOutputForm(explForm);
				
				//Info Adicional
				InfoAdicionalForm infoAdicionalForm = new InfoAdicionalForm();
				infoAdicionalForm.setIdioma1(curriculum.getIdiom1());
				infoAdicionalForm.setIdioma2(curriculum.getIdiom2());
				infoAdicionalForm.setIdioma3(curriculum.getIdiom3());
				infoAdicionalForm.setNivelIdioma1(curriculum.getIdiom1Level());
				infoAdicionalForm.setNivelIdioma2(curriculum.getIdiom2Level());
				infoAdicionalForm.setNivelIdioma3(curriculum.getIdiom3Level());
				infoAdicionalForm.setPaqueteria1(curriculum.getPack1());
				infoAdicionalForm.setPaqueteria2(curriculum.getPack2());
				infoAdicionalForm.setPaqueteria3(curriculum.getPack3());
				infoAdicionalForm.setNivelPaq1(curriculum.getPack1Level());
				infoAdicionalForm.setNivelPaq2(curriculum.getPack2Level());
				infoAdicionalForm.setNivelPaq3(curriculum.getPack3Level());
				String money = curriculum.getMoney();
				if(money!=null && !money.equals("")){
					StringTokenizer stMoney = new StringTokenizer(money,"-");
					infoAdicionalForm.setMoneyMax(stMoney.nextToken());
					infoAdicionalForm.setMoneyMin(stMoney.nextToken());
				}
				forward.addOutputForm(infoAdicionalForm);
			}
			forward.addActionOutput("usuario", getCurrentUser());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp")})
	public Forward actualizaDatosPersonales(com.cablevision.controller.bolsaTrabajo.BolsaTrabajoController.BolsaTrabajoForm form) {
		Forward forward = new Forward("success");
		
		//Valida que no haya caracteres especiales
		if(!form.getNombre().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getPaterno().matches("[a-zA-Z\u00F1\u00D1 ]*")
			|| !form.getMaterno().matches("[a-zA-Z\u00F1\u00D1 ]*") || !form.getEscolaridad().matches("[a-zA-Z\u00F1\u00D1 ]*")
			|| !form.getDireccion().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")){
			forward.addActionOutput("error", "El formulario solo acepta letras y n\u00FAmeros, llenar sin acentos ni caracteres especiales.");
			return forward;
		}
		
		try{
			if(getCurrentUser()==null){
				CvUser user = getBolsaTrabajoService().findCvUserById(form.getEmail());
				this.setCurrentUser(user);
			}
			
			CvCurriculum cvCurriculum = new CvCurriculum();
			if(getCurrentUser().getCurriculum()!=null){
				cvCurriculum = getCurrentUser().getCurriculum();
			}
			
			cvCurriculum.setAddress(form.getDireccion().toUpperCase(Locale.ENGLISH));
			cvCurriculum.setDateBorn(form.getDiaNacimiento()+"-"+
					form.getMesNacimiento()+"-"+
					form.getAnioNacimiento()
			);
			
			cvCurriculum.setEmail(form.getEmail());
			cvCurriculum.setPhone(form.getTelefono());
			cvCurriculum.setSchool(form.getEscolaridad().toUpperCase(Locale.ENGLISH));
			cvCurriculum.setStatusSchool(form.getStatusEscolar().toUpperCase(Locale.ENGLISH));
			cvCurriculum.setStatusCiv(form.getEdoCivl());
			
			getCurrentUser().setId(form.getId());
			getCurrentUser().setEmail(form.getEmail());
			getCurrentUser().setLastname(form.getPaterno().toUpperCase(Locale.ENGLISH));
			getCurrentUser().setName(form.getNombre().toUpperCase(Locale.ENGLISH));
			getCurrentUser().setSecondlastname(form.getMaterno().toUpperCase(Locale.ENGLISH));
			getCurrentUser().setCurriculum(cvCurriculum);
			cvCurriculum.setUser(getCurrentUser());
			getCurrentUser().setCurriculum(cvCurriculum);
			getBolsaTrabajoService().persistCvUser(getCurrentUser());
			getBolsaTrabajoService().persistCvCurriculum(cvCurriculum);
		}catch(Exception e){
			e.printStackTrace();
		}
		forward.addActionOutput("usuario", getCurrentUser());
		forward.addActionOutput("mensajeOk", "Tus datos se actualizaron correctamente.");
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp") })
	public Forward actualizaExperienciaLaboral(com.cablevision.controller.bolsaTrabajo.BolsaTrabajoController.ExperienciaLaboralForm form) {
		Forward forward = new Forward("success");
		forward.addActionOutput("usuario", getCurrentUser());
		
		//Valida que no haya caracteres especiales
		if(!form.getEmpresa1().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getPuesto1().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")
			|| !form.getResponsabilidades1().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getEmpresa2().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")
			|| !form.getPuesto2().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getResponsabilidades2().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")){
			forward.addActionOutput("error", "El formulario solo acepta letras y n\u00FAmeros, llenar sin acentos ni caracteres especiales.");
			return forward;
		}
		
		try{
			
			CvCurriculum cvCurriculum = new CvCurriculum();
			if(getCurrentUser().getCurriculum()!=null){
				cvCurriculum = getCurrentUser().getCurriculum();
			}
			
			cvCurriculum.setNameEmp1(form.getEmpresa1().toUpperCase(Locale.ENGLISH));
			cvCurriculum.setEmail(getCurrentUser().getEmail());
			cvCurriculum.setPlaceEmp1(form.getPuesto1().toUpperCase(Locale.ENGLISH));
			
			cvCurriculum.setStartEmp1(form.getDiaInicio1()+"-"+
					form.getMesInicio1()+"-"+
					form.getAnioInicio1()
			);
			
			cvCurriculum.setEndEmp1(form.getDiaFin1()+"-"+
					form.getMesFin1()+"-"+
					form.getAnioFin1()
			);
			cvCurriculum.setDescriptionEmp1(form.getResponsabilidades1().toUpperCase(Locale.ENGLISH));
			cvCurriculum.setNameEmp2(form.getEmpresa2()!=null?form.getEmpresa2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPlaceEmp2(form.getPuesto2()!=null?form.getPuesto2().toUpperCase(Locale.ENGLISH):"");
			
			cvCurriculum.setStartEmp2(form.getDiaInicio2()+"-"+
					form.getMesInicio2()+"-"+
					form.getAnioInicio2()
			);
			
			cvCurriculum.setEndEmp2(form.getDiaFin2()+"-"+
					form.getMesFin2()+"-"+
					form.getAnioFin2()
			);
			cvCurriculum.setDescriptionEmp2(form.getResponsabilidades2()!=null?form.getResponsabilidades2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setUser(getCurrentUser());
			getCurrentUser().setCurriculum(cvCurriculum);
			
			getBolsaTrabajoService().persistCvCurriculum(cvCurriculum);
		}catch(Exception e){
			e.printStackTrace();
		}
		forward.addActionOutput("mensajeOk", "Tus datos se actualizaron correctamente.");
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp") })
	public Forward actualizaInfoAdicional(com.cablevision.controller.bolsaTrabajo.BolsaTrabajoController.InfoAdicionalForm form) {
		Forward forward = new Forward("success");
		forward.addActionOutput("usuario", getCurrentUser());
		
		//Valida que no haya caracteres especiales
		if(!form.getIdioma1().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getIdioma2().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")
			|| !form.getIdioma3().matches("[a-zA-Z\u00F1\u00D1 ]*") || !form.getPaqueteria1().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")
			|| !form.getPaqueteria2().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getPaqueteria3().matches("[a-zA-Z0-9\u00F1\u00D1 ]*")
			|| !form.getMoneyMin().matches("[0-9 ]*") || !form.getMoneyMax().matches("[0-9 ]*")){
			forward.addActionOutput("error", "El formulario solo acepta letras y n\u00FAmeros, llenar sin acentos ni caracteres especiales.");
			return forward;
		}
		
		try{
			CvCurriculum cvCurriculum = new CvCurriculum();
			if(getCurrentUser().getCurriculum()!=null){
				cvCurriculum = getCurrentUser().getCurriculum();
			}
			
			cvCurriculum.setEmail(getCurrentUser().getEmail());
			cvCurriculum.setIdiom1(form.getIdioma1()!=null?form.getIdioma1().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setIdiom1Level(form.getNivelIdioma1()!=null?form.getNivelIdioma1().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setIdiom2(form.getIdioma2()!=null?form.getIdioma2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setIdiom2Level(form.getNivelIdioma2()!=null?form.getNivelIdioma2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setIdiom3(form.getIdioma3()!=null?form.getIdioma3().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setIdiom3Level(form.getNivelIdioma3()!=null?form.getNivelIdioma3().toUpperCase(Locale.ENGLISH):"");
			
			cvCurriculum.setPack1(form.getPaqueteria1()!=null?form.getPaqueteria1().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPack1Level(form.getNivelPaq1()!=null?form.getNivelPaq1().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPack2(form.getPaqueteria2()!=null?form.getPaqueteria2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPack2Level(form.getNivelPaq2()!=null?form.getNivelPaq2().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPack3(form.getPaqueteria3()!=null?form.getPaqueteria3().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setPack3Level(form.getNivelPaq3()!=null?form.getNivelPaq3().toUpperCase(Locale.ENGLISH):"");
			cvCurriculum.setMoney(form.getMoneyMin()+"-"+form.getMoneyMax());
			cvCurriculum.setUser(getCurrentUser());
			getCurrentUser().setCurriculum(cvCurriculum);	
			
			getBolsaTrabajoService().persistCvCurriculum(cvCurriculum);
		}catch(Exception e){
			e.printStackTrace();
		}
		forward.addActionOutput("mensajeOk", "Tus datos se actualizaron correctamente.");
		return forward;
	}
	
	/**
	 * muestra la lista de vacantes
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "vacantes.jsp") })
	public Forward verVacantes() throws Exception{
		Forward forward = new Forward("success");
		forward.addActionOutput("usuario", getCurrentUser());
		if(getRequest().isUserInRole("RH")){
			forward.addActionOutput("userRH", true);
		}
		Collection<Vacante> vacantes = UcmUtil.getVacantes(getRequest());
		getRequest().setAttribute("vacantes", vacantes);
		
		return forward;
	}
	
	/**
	 * mandar vacante seleccionada al mail del usuario 
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action="verVacantes"), @Jpf.Forward(name = "start", path = "index.jsp")})
	public Forward enviarVacantePorMail() throws Exception{
		Forward forward = new Forward("success");
		
		if(getCurrentUser()==null){
			forward = new Forward("start");
			forward.addActionOutput("errors", "Necesitas registrarte para enviarte una vacante.");
			return forward;
		}else{
			if(getCurrentUser().getCurriculum()!=null){
				String nombreCompleto = getCurrentUser().getName()+" "+getCurrentUser().getLastname()+" "+getCurrentUser().getSecondlastname();
				if(getRequest().getParameter("idVacante")!= null){
					UcmUtil.sendMailVacante(nombreCompleto, getCurrentUser().getEmail(), 
							getRequest().getParameter("idVacante"), getRequest());
					
					forward.addActionOutput("mailOk", getRequest().getParameter("idVacante"));
				}
			}else{
				forward = new Forward("success");
				forward.addActionOutput("errors", "Necesitas Actualizar tu Curiculum antes de enviarte una vacante");
			}
		}
		return forward;
	}
	
	/**
	 * postula a un usuario en la vacante elegida
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action="verVacantes"), @Jpf.Forward(name = "start", path = "index.jsp")})
	public Forward postularse() throws Exception{
		Forward forward = new Forward("success");
		
		if(getCurrentUser()==null){
			forward = new Forward("start");
			forward.addActionOutput("errors", "Necesitas registrarte para postularte a una vacante.");
			return forward;
		}else{
			if ( getCurrentUser().getCurriculum()!=null && 
				 ( StringUtils.isNotEmpty(getCurrentUser().getCurriculum().getStatusSchool()) &&
				   StringUtils.isNotEmpty(getCurrentUser().getCurriculum().getNameEmp1()) &&	
				   StringUtils.isNotEmpty(getCurrentUser().getCurriculum().getMoney())
				 )
			){
				String nombreCompleto = getCurrentUser().getName()+" "+getCurrentUser().getLastname()+" "+getCurrentUser().getSecondlastname();
				if(getRequest().getParameter("idVacante")!= null){
					UcmUtil.postularVacante(nombreCompleto, getCurrentUser().getEmail(), 
							getRequest().getParameter("idVacante"), getCurrentUser().getCurriculum(), getRequest());
					forward.addActionOutput("mensajeOk", getRequest().getParameter("idVacante"));
				}
			}else{
				forward = new Forward("success");
				forward.addActionOutput("errors", "Necesitas Actualizar tu Curiculum antes de postularte.");
			}
		}
		return forward;
	}
	
	/**
	 * muestra el formulario para agregar un nueva vacante
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "formularioVacante.jsp")})
	public Forward verAgregarVacante(VacanteForm form) throws Exception {
		Forward forward = new Forward("success");
		forward.addActionOutput("usuario", getCurrentUser());
		
		if(form.getIdVacante()!=null && !form.getIdVacante().equals("")){
			Collection<Vacante> vacantes = (Collection<Vacante>) UcmUtil.getVacantes(getRequest());
			Iterator<Vacante> it = vacantes.iterator();
			while(it.hasNext()){
				Vacante vacante = (Vacante)it.next();
				if(vacante.getIdVacante().equals(form.getIdVacante())){
					form.setIdVacante(vacante.getIdVacante());
					form.setDescripcion(vacante.getDescripcion());
					form.setTitulo(vacante.getTitulo());
				}
			}
		}

		return forward;
	}
	
	/**
	 * guarda una vacante nueva
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "verVacantes")},
				validationErrorForward = @Jpf.Forward(name="fail", path="formularioVacante.jsp"),
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "descripcion", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.discripcion")), @Jpf.ValidatableProperty(propertyName="titulo", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.titulo")) })
	public Forward guardarVacante(VacanteForm form) throws Exception{
		Forward forward = new Forward("success");
		Vacante vacanteNueva = new Vacante();
		
		//Valida que no haya caracteres especiales
		if(!form.getDescripcion().matches("[a-zA-Z0-9\u00F1\u00D1 ]*") || !form.getTitulo().matches("[a-zA-Z\u00F1\u00D1 ]*") ){
			addActionError("descripcion", "error.formato", null);
			return new Forward("fail");
		}
		
		vacanteNueva.setIdVacante(form.getIdVacante());
		vacanteNueva.setDescripcion(form.getDescripcion().toUpperCase(Locale.ENGLISH));
		vacanteNueva.setTitulo(form.getTitulo().toUpperCase(Locale.ENGLISH));
		UcmUtil.saveVacante(vacanteNueva, getMessageResources("configuracion"), getRequest());
		return forward;
	}
	
	/**
	 * borrar una vacante de ucm
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "verVacantes")})
	public Forward borrarVacante(VacanteForm form) throws Exception {
		Forward forward = new Forward("success");
		UcmUtil.removeVacante(form.getIdVacante(),getMessageResources("configuracion"), getRequest());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "bienvenida.jsp")})
	public Forward verBienvenida() {
		Forward forward = new Forward("success");
		forward.addActionOutput("usuario", getCurrentUser());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "sinSesion", path = "index.jsp"), @Jpf.Forward(name="conSesion", action="verBienvenida") })
	public Forward verLogin() {
		Forward forward;
		if(getCurrentUser()==null){
			forward = new Forward("sinSesion");
		}else{
			forward = new Forward("conSesion");
			forward.addActionOutput("usuario", getCurrentUser());
			forward.addActionOutput("style", "none");
		}

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
	
	public CvUser getCurrentUser() {
		
		return (CvUser)getSession().getAttribute("_BT_CURRENTUSER");
	}

	public void setCurrentUser(CvUser currentUser) {
		getSession().setAttribute("_BT_CURRENTUSER", currentUser);
	}

	public IBolsaTrabajoService getBolsaTrabajoService() {
		if(bolsaTrabajoService == null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			bolsaTrabajoService = (IBolsaTrabajoService)context.getBean("BolsaTrabajoService");
		}
		return bolsaTrabajoService;
	}

	public void setBolsaTrabajoService(IBolsaTrabajoService bolsaTrabajoService) {
		this.bolsaTrabajoService = bolsaTrabajoService;
	}

	@Jpf.FormBean
	public static class BolsaTrabajoForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private Long id;
		private String email;
		private String password;
		private String nombre;
		private String paterno;
		private String materno;
		private String confirmPassword;
		
		//CV
		private String direccion;
		private String edoCivl;
		private String telefono;
		private String diaNacimiento;
		private String mesNacimiento;
		private String anioNacimiento;
		private String escolaridad;
		private String statusEscolar;
		private String challenge;
		private String uresponse;

		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getPaterno() {
			return paterno;
		}
		public void setPaterno(String paterno) {
			this.paterno = paterno;
		}
		public String getMaterno() {
			return materno;
		}
		public void setMaterno(String materno) {
			this.materno = materno;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
		
		//CV
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getEdoCivl() {
			return edoCivl;
		}
		public void setEdoCivl(String edoCivl) {
			this.edoCivl = edoCivl;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getDiaNacimiento() {
			return diaNacimiento;
		}
		public void setDiaNacimiento(String diaNacimiento) {
			this.diaNacimiento = diaNacimiento;
		}
		public String getMesNacimiento() {
			return mesNacimiento;
		}
		public void setMesNacimiento(String mesNacimiento) {
			this.mesNacimiento = mesNacimiento;
		}
		public String getAnioNacimiento() {
			return anioNacimiento;
		}
		public void setAnioNacimiento(String anioNacimiento) {
			this.anioNacimiento = anioNacimiento;
		}
		public String getEscolaridad() {
			return escolaridad;
		}
		public void setEscolaridad(String escolaridad) {
			this.escolaridad = escolaridad;
		}
		public String getStatusEscolar() {
			return statusEscolar;
		}
		public void setStatusEscolar(String statusEscolar) {
			this.statusEscolar = statusEscolar;
		}
		
		public boolean validate(){
			if( ( nombre!=null && !"".equals(nombre) ) &&
				( paterno!=null && !"".equals(paterno) ) && 
				( materno!=null && !"".equals(materno) ) && 
				( email!=null && !"".equals(email) ) &&
				( password!=null && !"".equals(password) ) &&
				( confirmPassword!=null && !"".equals(confirmPassword) )	
			){
				return true;
			}
			return false;
		}
		public String getChallenge() {
			return challenge;
		}
		public void setChallenge(String challenge) {
			this.challenge = challenge;
		}
		public String getUresponse() {
			return uresponse;
		}
		public void setUresponse(String uresponse) {
			this.uresponse = uresponse;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
	}

	@Jpf.FormBean
	public static class ExperienciaLaboralForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String empresa1;
		private String puesto1;
		private String diaInicio1;
		private String mesInicio1;
		private String anioInicio1;
		private String diaFin1;
		private String mesFin1;
		private String anioFin1;
		private String responsabilidades1;
		private String empresa2;
		private String puesto2;
		private String diaInicio2;
		private String mesInicio2;
		private String anioInicio2;
		private String diaFin2;
		private String mesFin2;
		private String anioFin2;
		private String responsabilidades2;
		
		//Experiencia Laboral
		public String getEmpresa1() {
			return empresa1;
		}
		public void setEmpresa1(String empresa1) {
			this.empresa1 = empresa1;
		}
		public String getPuesto1() {
			return puesto1;
		}
		public void setPuesto1(String puesto1) {
			this.puesto1 = puesto1;
		}
		public String getDiaInicio1() {
			return diaInicio1;
		}
		public void setDiaInicio1(String diaInicio1) {
			this.diaInicio1 = diaInicio1;
		}
		public String getMesInicio1() {
			return mesInicio1;
		}
		public void setMesInicio1(String mesInicio1) {
			this.mesInicio1 = mesInicio1;
		}
		public String getAnioInicio1() {
			return anioInicio1;
		}
		public void setAnioInicio1(String anioInicio1) {
			this.anioInicio1 = anioInicio1;
		}
		public String getDiaFin1() {
			return diaFin1;
		}
		public void setDiaFin1(String diaFin1) {
			this.diaFin1 = diaFin1;
		}
		public String getMesFin1() {
			return mesFin1;
		}
		public void setMesFin1(String mesFin1) {
			this.mesFin1 = mesFin1;
		}
		public String getAnioFin1() {
			return anioFin1;
		}
		public void setAnioFin1(String anioFin1) {
			this.anioFin1 = anioFin1;
		}
		public String getResponsabilidades1() {
			return responsabilidades1;
		}
		public void setResponsabilidades1(String responsabilidades1) {
			this.responsabilidades1 = responsabilidades1;
		}
		public String getEmpresa2() {
			return empresa2;
		}
		public void setEmpresa2(String empresa2) {
			this.empresa2 = empresa2;
		}
		public String getPuesto2() {
			return puesto2;
		}
		public void setPuesto2(String puesto2) {
			this.puesto2 = puesto2;
		}
		public String getDiaInicio2() {
			return diaInicio2;
		}
		public void setDiaInicio2(String diaInicio2) {
			this.diaInicio2 = diaInicio2;
		}
		public String getMesInicio2() {
			return mesInicio2;
		}
		public void setMesInicio2(String mesInicio2) {
			this.mesInicio2 = mesInicio2;
		}
		public String getAnioInicio2() {
			return anioInicio2;
		}
		public void setAnioInicio2(String anioInicio2) {
			this.anioInicio2 = anioInicio2;
		}
		public String getDiaFin2() {
			return diaFin2;
		}
		public void setDiaFin2(String diaFin2) {
			this.diaFin2 = diaFin2;
		}
		public String getMesFin2() {
			return mesFin2;
		}
		public void setMesFin2(String mesFin2) {
			this.mesFin2 = mesFin2;
		}
		public String getAnioFin2() {
			return anioFin2;
		}
		public void setAnioFin2(String anioFin2) {
			this.anioFin2 = anioFin2;
		}
		public String getResponsabilidades2() {
			return responsabilidades2;
		}
		public void setResponsabilidades2(String responsabilidades2) {
			this.responsabilidades2 = responsabilidades2;
		}
	}

	@Jpf.FormBean
	public static class InfoAdicionalForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String idioma1;
		private String nivelIdioma1;
		private String idioma2;
		private String nivelIdioma2;
		private String idioma3;
		private String nivelIdioma3;
		private String paqueteria1;
		private String nivelPaq1;
		private String paqueteria2;
		private String nivelPaq2;
		private String paqueteria3;
		private String nivelPaq3;
		private String moneyMin;
		private String moneyMax;
		
		//info adicional
		public String getIdioma1() {
			return idioma1;
		}
		public void setIdioma1(String idioma1) {
			this.idioma1 = idioma1;
		}
		public String getIdioma2() {
			return idioma2;
		}
		public void setIdioma2(String idioma2) {
			this.idioma2 = idioma2;
		}
		public String getIdioma3() {
			return idioma3;
		}
		public void setIdioma3(String idioma3) {
			this.idioma3 = idioma3;
		}
		public String getPaqueteria1() {
			return paqueteria1;
		}
		public void setPaqueteria1(String paqueteria1) {
			this.paqueteria1 = paqueteria1;
		}
		public String getPaqueteria2() {
			return paqueteria2;
		}
		public void setPaqueteria2(String paqueteria2) {
			this.paqueteria2 = paqueteria2;
		}
		public String getPaqueteria3() {
			return paqueteria3;
		}
		public void setPaqueteria3(String paqueteria3) {
			this.paqueteria3 = paqueteria3;
		}
		public String getMoneyMin() {
			return moneyMin;
		}
		public void setMoneyMin(String moneyMin) {
			this.moneyMin = moneyMin;
		}
		public String getMoneyMax() {
			return moneyMax;
		}
		public void setMoneyMax(String moneyMax) {
			this.moneyMax = moneyMax;
		}
		public String getNivelIdioma1() {
			return nivelIdioma1;
		}
		public void setNivelIdioma1(String nivelIdioma1) {
			this.nivelIdioma1 = nivelIdioma1;
		}
		public String getNivelIdioma2() {
			return nivelIdioma2;
		}
		public void setNivelIdioma2(String nivelIdioma2) {
			this.nivelIdioma2 = nivelIdioma2;
		}
		public String getNivelIdioma3() {
			return nivelIdioma3;
		}
		public void setNivelIdioma3(String nivelIdioma3) {
			this.nivelIdioma3 = nivelIdioma3;
		}
		public String getNivelPaq1() {
			return nivelPaq1;
		}
		public void setNivelPaq1(String nivelPaq1) {
			this.nivelPaq1 = nivelPaq1;
		}
		public String getNivelPaq2() {
			return nivelPaq2;
		}
		public void setNivelPaq2(String nivelPaq2) {
			this.nivelPaq2 = nivelPaq2;
		}
		public String getNivelPaq3() {
			return nivelPaq3;
		}
		public void setNivelPaq3(String nivelPaq3) {
			this.nivelPaq3 = nivelPaq3;
		}
	}

	@Jpf.FormBean
	public static class VacanteForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String idVacante;
		private String descripcion;
		private String titulo;
		
		public String getIdVacante() {
			return idVacante;
		}
		public void setIdVacante(String idVacante) {
			this.idVacante = idVacante;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	}
}