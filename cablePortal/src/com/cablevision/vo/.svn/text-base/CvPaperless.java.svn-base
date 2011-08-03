package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_PAPERLESS database table.
 * 
 * @author BEA Workshop
 */
public class CvPaperless  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String pplContrato;
	private String pplEmail;
	private String pplEstatus;
	private String pplIdusuario;
	private java.sql.Date pplLastUpdate;
	private String pplNombre;
	private java.sql.Date pplFechaIngreso;

    public CvPaperless() {
    }

	public String getPplContrato() {
		return this.pplContrato;
	}
	public void setPplContrato(String pplContrato) {
		this.pplContrato = pplContrato;
	}

	public String getPplEmail() {
		return this.pplEmail;
	}
	public void setPplEmail(String pplEmail) {
		this.pplEmail = pplEmail;
	}

	public String getPplEstatus() {
		return this.pplEstatus;
	}
	public void setPplEstatus(String pplEstatus) {
		this.pplEstatus = pplEstatus;
	}

	public String getPplIdusuario() {
		return this.pplIdusuario;
	}
	public void setPplIdusuario(String pplIdusuario) {
		this.pplIdusuario = pplIdusuario;
	}

	public java.sql.Date getPplLastUpdate() {
		return this.pplLastUpdate;
	}
	public void setPplLastUpdate(java.sql.Date pplLastUpdate) {
		this.pplLastUpdate = pplLastUpdate;
	}

	public String getPplNombre() {
		return this.pplNombre;
	}
	public void setPplNombre(String pplNombre) {
		this.pplNombre = pplNombre;
	}
	
	public java.sql.Date getPplFechaIngreso() {
		return pplFechaIngreso;
	}

	public void setPplFechaIngreso(java.sql.Date pplFechaIngreso) {
		this.pplFechaIngreso = pplFechaIngreso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvPaperless)) {
			return false;
		}
		CvPaperless castOther = (CvPaperless)other;
		return new EqualsBuilder()
			.append(this.getPplContrato(), castOther.getPplContrato())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPplContrato())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("pplContrato", getPplContrato())
			.toString();
	}
}