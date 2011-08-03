package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_PAPERLESS_HISTORIAL database table.
 * 
 * @author BEA Workshop
 */
public class CvPaperlessHistorial  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long phIdHistorial;
	private String phContrato;
	private java.sql.Date phFechaModificacion;
	private String phTipoCambio;
	private String phUser;
	private String phNombre;
	private String phEmail;
	
    public CvPaperlessHistorial() {
    }

	public Long getPhIdHistorial() {
		return this.phIdHistorial;
	}
	public void setPhIdHistorial(Long phIdHistorial) {
		this.phIdHistorial = phIdHistorial;
	}

	public String getPhContrato() {
		return this.phContrato;
	}
	public void setPhContrato(String phContrato) {
		this.phContrato = phContrato;
	}

	public java.sql.Date getPhFechaModificacion() {
		return this.phFechaModificacion;
	}
	public void setPhFechaModificacion(java.sql.Date phFechaModificacion) {
		this.phFechaModificacion = phFechaModificacion;
	}

	public String getPhTipoCambio() {
		return this.phTipoCambio;
	}
	public void setPhTipoCambio(String phTipoCambio) {
		this.phTipoCambio = phTipoCambio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvPaperlessHistorial)) {
			return false;
		}
		CvPaperlessHistorial castOther = (CvPaperlessHistorial)other;
		return new EqualsBuilder()
			.append(this.getPhIdHistorial(), castOther.getPhIdHistorial())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPhIdHistorial())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("phIdHistorial", getPhIdHistorial())
			.toString();
	}

	public String getPhUser() {
		return phUser;
	}

	public void setPhUser(String phUser) {
		this.phUser = phUser;
	}

	public String getPhNombre() {
		return phNombre;
	}

	public void setPhNombre(String phNombre) {
		this.phNombre = phNombre;
	}

	public String getPhEmail() {
		return phEmail;
	}

	public void setPhEmail(String phEmail) {
		this.phEmail = phEmail;
	}
}