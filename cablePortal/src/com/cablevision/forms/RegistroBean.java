package com.cablevision.forms;

import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.struts.upload.FormFile;

/**
 * Form bean para el registro de clientes de Cablevision
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.FormBean
public class RegistroBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private String nombre;
	private String segundoNombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String email;
	private String emailConfirmacion;
	private String idUsuario;
	private String password;
	private String passwordConfirmacion;
	private String nuevoPassword;
	private String preguntaConfirmacion;
	private String respuestaConfirmacion;
	private String noContrato;
	private String telefono;
	private String entiendo;
	private transient FormFile foto;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailConfirmacion() {
		return emailConfirmacion;
	}

	public void setEmailConfirmacion(String emailConfirmacion) {
		this.emailConfirmacion = emailConfirmacion;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}

	public String getPreguntaConfirmacion() {
		return preguntaConfirmacion;
	}

	public void setPreguntaConfirmacion(String preguntaConfirmacion) {
		this.preguntaConfirmacion = preguntaConfirmacion;
	}

	public String getRespuestaConfirmacion() {
		return respuestaConfirmacion;
	}

	public void setRespuestaConfirmacion(String respuestaConfirmacion) {
		this.respuestaConfirmacion = respuestaConfirmacion;
	}

	public String getNoContrato() {
		return noContrato;
	}

	public void setNoContrato(String noContrato) {
		this.noContrato = noContrato;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEntiendo() {
		return entiendo;
	}

	public void setEntiendo(String entiendo) {
		this.entiendo = entiendo;
	}

	public String getNuevoPassword() {
		return nuevoPassword;
	}

	public void setNuevoPassword(String nuevoPassword) {
		this.nuevoPassword = nuevoPassword;
	}

	public FormFile getFoto() {
		return foto;
	}
	public void setFoto(FormFile foto) {
		this.foto = foto;
	}
}