package com.cablevision.controller.contactanos;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.ILeadService;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.vo.CvField;
import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadField;
import com.cablevision.vo.CvLeadType;

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
	public Forward begin(ContactanosForm form) throws Exception{
		Forward forward = new Forward("success");
		ContactanosForm f = new ContactanosForm();
		List<CvLeadType> types = getLeadService().findAllLeadTypes();

		Map<Long, String> leadTypesMap = new LinkedHashMap<Long, String>();
		for(CvLeadType type : types)
			leadTypesMap.put(type.getCltIdLeadtype(), type.getCltNameType());

		getSession().setAttribute("leadTypesMap", leadTypesMap);
		forward.addActionOutput("leadTypesMap", leadTypesMap);
		forward.addActionOutput("leadTypes", types);
		forward.addActionOutput("form", f);
		return forward;
	}

	/**
	 * Metodo para obtener los campos a mostrar de acuerdo al asunto seleccionado
	 * @return Un html con los campos para la opcion seleccionada
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "campos.jsp") })
	public Forward getCampos(ContactanosForm form) throws Exception{
		Forward forward = new Forward("success");
		ContactanosForm f = new ContactanosForm();

		Map<String, String> map = new HashMap<String, String>();
		map.put("leadType", form.getAsunto());
		List<CvLeadField> leadFields = getLeadService().findLeadFields(map);

		Map<Long, String> leadTypesMap = (Map<Long, String>)getSession().getAttribute("leadTypesMap");
		forward.addActionOutput("leadTypesMap", leadTypesMap);
		getSession().setAttribute("leadFields", leadFields);
		forward.addActionOutput("leadFields", leadFields);
		forward.addActionOutput("leadType", form.getAsunto());
		forward.addActionOutput("form", f);
		return forward;
	}

	/**
	 * Metodo para guardar el formulario de contacto
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "finalMsg.jsp"),
			@Jpf.Forward(name = "error", path = "campos.jsp")})
			public Forward saveContact(ContactanosForm form) throws Exception{
		Forward forward; 
		
		//1.- validaciones
		//Map<String,String> map = form.getFields();
		Map<String,String> map = new LinkedHashMap<String, String>();
		Map parameterMap = getRequest().getParameterMap();
		List<CvLeadField> leadFields = (List)getSession().getAttribute("leadFields");
		for(CvLeadField lf : leadFields) {
			CvField field = lf.getId().getField();
			Object fieldValueObj = parameterMap.get(field.getCfsNamefield());
			String fieldValue = "";
			if(fieldValueObj != null)
				fieldValue = ((String[])fieldValueObj)[0];
			map.put(field.getCfsNamefield(), fieldValue);
		}
		map.put("leadType", getRequest().getParameter("leadType"));
		map.put("idStatus","0");
		Map<String,String> errors = getLeadService().getValidatedFields(map);
		Map<String,String> errors2 = new HashMap<String, String>(map);


		//Valida que escribas correcto lo del captcha
		if(!ReCaptchaUtil.isValido(getRequest())){
			forward = new Forward("error");
			getRequest().setAttribute("errors", "El Texto no coincide con la imagen");
		} else {
			if(map.get("Cliente") != null && map.get("Cliente").equals("Si")) {
				form.setEsCliente("Si");
				form.setNumContrato(map.get("No_contrato"));
			}

			//2.- si es cliente validar el numero de contrato
			if("Si".equals(form.getEsCliente())){
				String contrato = form.getNumContrato();
				if(StringUtils.isBlank(contrato)){
					errors.put("No_contrato", "El campo N&uacute;mero de contrato es requerido");
				}else{
					if(!"1".equals(contrato.substring(0, 1)) && contrato.length()!=8 ){
						errors.put("No_contrato", "El campo N&uacute;mero de contrato es inv&aacute;lido");
					}else{
						//Account a = getaccounts
						//if(a != null)
						//contrato = a.getNumContrato
						//else
						//errors.add("El n&uacute;mero de cliente "+contrato+" no es v&aacute;lido");
					}
				}
				String telCV = map.get("Tel_cable");
				if(telCV==null || "".equals(telCV)){
					errors.put("Tel_cable", "El campo Tel&eacute;fono que registr&oacute; con Cablevision es requerido");
				}
			}

			for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> e = (Entry<String, String>)it.next();
				if(errors.containsKey(e.getKey()))
					errors2.put(e.getKey(), errors.get(e.getKey()));
				else
					errors2.remove(e.getKey());
			}

			if(errors2.size()==0){

				getLeadService().saveLead(map);

				//6.- Enviar correo

				//7.- guardar correo y cuenta de usuario

				forward = new Forward("success");
			}else{
				forward = new Forward("error");
				getRequest().setAttribute("errors", errors2.values());
			}
		}
		if(forward.getName().equals("error")) {
			Map<Long, String> leadTypesMap = (Map<Long, String>)getSession().getAttribute("leadTypesMap");
			forward.addActionOutput("leadTypesMap", leadTypesMap);
			forward.addActionOutput("leadFields", leadFields);
			forward.addActionOutput("leadType", map.get("leadType"));
			form.setAsunto(map.get("leadType"));
			forward.addOutputForm(form);
		}
		if(forward.getName().equals("error")) {
			for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> e = (Entry<String, String>)it.next();
//				forward.addActionOutput(e.getKey(), e.getValue());
				getRequest().getSession().setAttribute("contactanos_"+e.getKey(), e.getValue());
			}
		}
		return forward;

	}

	/**
	 * Metodo para mostrar el mensaje de \u00E9xito
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "finalMsg.jsp")})
	public Forward saveSuccess(ContactanosForm form) throws Exception{
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
		Map<String,String> errors2 = new HashMap<String,String>();

		for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> e = (Entry<String, String>)it.next();
			if(errors.containsKey(e.getKey()) && !errors.get(e.getKey()).toString().equals(""))
				errors2.put(e.getKey(), errors.get(e.getKey()));
			else
				errors2.remove(e.getKey());
		}

		if(errors2.size()==0){

			getLeadService().saveLead(map);

			//6.- Enviar correo

			//7.- guardar correo y cuenta de usuario

			getResponse().getWriter().write("ok");
		}else{
			forward.addActionOutput("success", false);
			getRequest().setAttribute("errors", errors2.values());

			Iterator<Entry<String, String>> it = errors2.entrySet().iterator();
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

	@Jpf.FormBean
	public static class ContactanosForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String idLeadType;
		private String nombre;
		private String apellido;
		private String esCliente;
		private String numContrato;
		private String email;
		private String telefono;
		private String telefonoOpcional;
		private String comentarios;
		private String asunto;
		private String contactadoPor;
		private String challenge;
		private String uresponse;

		public ContactanosForm(){

		}

		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		public String getEsCliente() {
			return esCliente;
		}
		public void setEsCliente(String esCliente) {
			this.esCliente = esCliente;
		}
		public String getNumContrato() {
			return numContrato;
		}
		public void setNumContrato(String numContrato) {
			this.numContrato = numContrato;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getTelefonoOpcional() {
			return telefonoOpcional;
		}
		public void setTelefonoOpcional(String telefonoOpcional) {
			this.telefonoOpcional = telefonoOpcional;
		}
		public String getComentarios() {
			return comentarios;
		}
		public void setComentarios(String comentarios) {
			this.comentarios = comentarios;
		}
		public String getAsunto() {
			return asunto;
		}
		public void setAsunto(String asunto) {
			this.asunto = asunto;
		}
		public String getContactadoPor() {
			return contactadoPor;
		}
		public void setContactadoPor(String contactadoPor) {
			this.contactadoPor = contactadoPor;
		}
		public String getIdLeadType() {
			return idLeadType;
		}

		public void setIdLeadType(String idLeadType) {
			this.idLeadType = idLeadType;
		}
		public CvLead getLead(){
			CvLead lead = new CvLead();

			lead.setLeadType(new CvLeadType(1l));

			return lead;
		}

		@SuppressWarnings("unchecked")
		public Map<String,String> getFields(){
			Map<String,String> map = new LinkedHashMap<String,String>();

			map.put("Nombre",this.nombre!=null?this.nombre.toUpperCase(Locale.ENGLISH):this.nombre);
			map.put("Apellido",this.apellido!=null?this.apellido.toUpperCase(Locale.ENGLISH):this.apellido);
			map.put("No_contrato",this.numContrato);
			map.put("Email",this.email);
			map.put("Tel_cable",this.telefono);
			map.put("Tel_opc",this.telefonoOpcional);
			map.put("Comentarios",this.comentarios!=null?this.comentarios.toUpperCase(Locale.ENGLISH):this.comentarios);
			map.put("Contacto",this.contactadoPor!=null?this.contactadoPor.toUpperCase(Locale.ENGLISH):this.contactadoPor); 
			map.put("Cliente",this.esCliente);
			map.put("leadType", this.idLeadType);
			map.put("idStatus","0");

			return map;
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
