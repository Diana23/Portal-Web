/**
 * 
 */
package com.cablevision.controller.distribuidores;

import java.util.HashMap;
import java.util.Map;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.util.MailUtil;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.util.UcmUtil;

/**
 * Page Flow para usar con Distribuidores
 * 
 * @author Yesenia Urias - JWMSolutions 31/01/2011
 *
 */
@Jpf.Controller(simpleActions = { @Jpf.SimpleAction(name = "begin", path = "index.jsp") },
		messageBundles={ 
		@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.distribuidores.distribuidores", bundleName="distribuidores"),
		@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )
	})
public class DistribuidoresController extends ControllerBase{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo para enviar un correo con los datos del distribuidor
	 * 
	 * @param form La forma llena con los datos a enviar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="index.jsp") },
			validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
			validatableProperties = { 
				@Jpf.ValidatableProperty(propertyName="nombreCompleto", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.name"),
						validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.name")),
				@Jpf.ValidatableProperty(propertyName="telefono", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.telefono"),
						validateType=@Jpf.ValidateType(messageKey="error.numero.telefono",type=long.class)),
				@Jpf.ValidatableProperty(propertyName="celular", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.celular"),
						validateType=@Jpf.ValidateType(messageKey="error.numero.celular",type=long.class)),
				@Jpf.ValidatableProperty(propertyName="email", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.email"), 
						validateEmail=@Jpf.ValidateEmail(messageKey="error.formato.email")), 
				@Jpf.ValidatableProperty(propertyName="horarioContactar", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.horarioContactar")),
				@Jpf.ValidatableProperty(propertyName="estado", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.estado")), 
				@Jpf.ValidatableProperty(propertyName="ciudad", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.ciudad")),
				@Jpf.ValidatableProperty(propertyName="municipio", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.municipio")),
				@Jpf.ValidatableProperty(propertyName="negocioPropio", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.negocioPropio")),
				@Jpf.ValidatableProperty(propertyName="tipoNegocio", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.tipoNegocio")),
				@Jpf.ValidatableProperty(propertyName="localComercial", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.localComercial")),
				@Jpf.ValidatableProperty(propertyName="vendedores", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.vendedores")),
				@Jpf.ValidatableProperty(propertyName="queVende", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.queVende")),
				@Jpf.ValidatableProperty(propertyName="comoVende", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.comoVende"))
				} )
	public Forward enviarCorreo(DistribuidoresBean form) throws Exception{
		Forward forward = new Forward("success");
		Boolean isValid = true;
		
		if(!ReCaptchaUtil.isValido(getRequest())){
			forward.addActionOutput("msgCapcha", "El Texto no coincide con la imagen");
			isValid = false;
		}
		
		if (isValid){
			Map<String, String> values = new HashMap<String, String>();
			values.put("nombre", form.getNombreCompleto());
			values.put("telefono", form.getTelefono());
			values.put("celular", form.getCelular());
			values.put("email",form.getEmail());
			values.put("horarioContactar", form.getHorarioContactar());
			values.put("estado", form.getEstado());
			values.put("ciudad", form.getCiudad());
			values.put("municipio", form.getMunicipio());
			values.put("negocioPropio", form.getNegocioPropio());
			values.put("tipoNegocio", form.getTipoNegocio());
			values.put("localComercial", form.getLocalComercial());
			values.put("vendedores", form.getVendedores());
			values.put("queVende", form.getQueVende());
			values.put("comoVende", form.getComoVende());
			
			String emails = UcmUtil.getContentByName(getMessageResources("distribuidores").getMessage("correo.distribuidores.listaCorreos",null), true);
			
			MailUtil.sendMail(getMessageResources("distribuidores").getMessage("correo.distribuidores.subject",null), 
					emails, 
					getMessageResources("distribuidores").getMessage("correo.distribuidores.from",null), 
					getMessageResources("distribuidores").getMessage("correo.distribuidores.templateId",null), values);
			
			forward.addActionOutput("exito", getMessageResources("distribuidores").getMessage("enviar.correo.exito"));
		}
		return forward;
	}
	
	/**
	 * Bean Class para usar con Distribuidores
	 * 
	 * @author Yesenia Urias - JWMSolutions 31/01/2011
	 *
	 */
	public static class DistribuidoresBean implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		
		private String nombreCompleto;
		private String telefono;
		private String celular;
		private String email;
		private String horarioContactar;
		private String estado;
		private String ciudad;
		private String municipio;
		private String negocioPropio;
		private String tipoNegocio;
		private String localComercial;
		private String vendedores;
		private String queVende;
		private String comoVende;
		
		public String getNombreCompleto() {
			return nombreCompleto;
		}
		public void setNombreCompleto(String nombreCompleto) {
			this.nombreCompleto = nombreCompleto;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getCelular() {
			return celular;
		}
		public void setCelular(String celular) {
			this.celular = celular;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getHorarioContactar() {
			return horarioContactar;
		}
		public void setHorarioContactar(String horarioContactar) {
			this.horarioContactar = horarioContactar;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getCiudad() {
			return ciudad;
		}
		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}
		public String getMunicipio() {
			return municipio;
		}
		public void setMunicipio(String municipio) {
			this.municipio = municipio;
		}
		public String getNegocioPropio() {
			return negocioPropio;
		}
		public void setNegocioPropio(String negocioPropio) {
			this.negocioPropio = negocioPropio;
		}
		public String getTipoNegocio() {
			return tipoNegocio;
		}
		public void setTipoNegocio(String tipoNegocio) {
			this.tipoNegocio = tipoNegocio;
		}
		public String getLocalComercial() {
			return localComercial;
		}
		public void setLocalComercial(String localComercial) {
			this.localComercial = localComercial;
		}
		public String getVendedores() {
			return vendedores;
		}
		public void setVendedores(String vendedores) {
			this.vendedores = vendedores;
		}
		public String getQueVende() {
			return queVende;
		}
		public void setQueVende(String queVende) {
			this.queVende = queVende;
		}
		public String getComoVende() {
			return comoVende;
		}
		public void setComoVende(String comoVende) {
			this.comoVende = comoVende;
		}
		
	}

}
