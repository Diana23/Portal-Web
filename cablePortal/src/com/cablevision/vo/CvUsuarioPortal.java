package com.cablevision.vo;
import java.io.Serializable;import org.apache.commons.lang.builder.EqualsBuilder;import org.apache.commons.lang.builder.HashCodeBuilder;import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_USUARIO_PORTAL database table.
 * 
 * @author BEA Workshop
 */
public class CvUsuarioPortal  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String cupIdusuario;
	private String cupContrato;
	private java.sql.Timestamp cupFechaUltimaContrasena;
	private java.sql.Timestamp cupFechaUltimoIntentoLogin;
	private Integer cupIntentos;	private String cupSessionId;		private String cupFoto;	
	private java.util.Set<CvContrasenaHistorial> cvContrasenaHistorials;
	
    public CvUsuarioPortal() {
    }

	public String getCupIdusuario() {
		return this.cupIdusuario;
	}
	public void setCupIdusuario(String cupIdusuario) {
		this.cupIdusuario = cupIdusuario;
	}

	public String getCupContrato() {
		return this.cupContrato;
	}
	public void setCupContrato(String cupContrato) {
		this.cupContrato = cupContrato;
	}

	public java.sql.Timestamp getCupFechaUltimaContrasena() {
		return this.cupFechaUltimaContrasena;
	}
	public void setCupFechaUltimaContrasena(java.sql.Timestamp cupFechaUltimaContrasena) {
		this.cupFechaUltimaContrasena = cupFechaUltimaContrasena;
	}

	public java.sql.Timestamp getCupFechaUltimoIntentoLogin() {
		return this.cupFechaUltimoIntentoLogin;
	}
	public void setCupFechaUltimoIntentoLogin(java.sql.Timestamp cupFechaUltimoIntentoLogin) {
		this.cupFechaUltimoIntentoLogin = cupFechaUltimoIntentoLogin;
	}

	public Integer getCupIntentos() {
		return this.cupIntentos;
	}
	public void setCupIntentos(Integer cupIntentos) {
		this.cupIntentos = cupIntentos;
	}	
	public String getCupSessionId() {		return cupSessionId;	}	public void setCupSessionId(String cupSessionId) {		this.cupSessionId = cupSessionId;	}	//bi-directional many-to-one association to CvContrasenaHistorial
	public String getCupFoto() {		return cupFoto;	}	public void setCupFoto(String cupFoto) {		this.cupFoto = cupFoto;	}	public java.util.Set<CvContrasenaHistorial> getCvContrasenaHistorials() {
		return this.cvContrasenaHistorials;
	}
	public void setCvContrasenaHistorials(java.util.Set<CvContrasenaHistorial> cvContrasenaHistorials) {
		this.cvContrasenaHistorials = cvContrasenaHistorials;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvUsuarioPortal)) {
			return false;
		}
		CvUsuarioPortal castOther = (CvUsuarioPortal)other;
		return new EqualsBuilder()
			.append(this.getCupIdusuario(), castOther.getCupIdusuario())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCupIdusuario())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("cupIdusuario", getCupIdusuario())
			.toString();
	}
}