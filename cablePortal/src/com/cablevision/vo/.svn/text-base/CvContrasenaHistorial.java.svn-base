package com.cablevision.vo;
import java.io.Serializable;import org.apache.commons.lang.builder.EqualsBuilder;import org.apache.commons.lang.builder.HashCodeBuilder;import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_CONTRASENA_HISTORIAL database table.
 * 
 * @author BEA Workshop
 */
public class CvContrasenaHistorial  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long cchIdContrasenaHistorial;
	private String cchContrasena;
	private CvUsuarioPortal cvUsuarioPortal;

    public CvContrasenaHistorial() {
    }

	public Long getCchIdContrasenaHistorial() {
		return this.cchIdContrasenaHistorial;
	}
	public void setCchIdContrasenaHistorial(Long cchIdContrasenaHistorial) {
		this.cchIdContrasenaHistorial = cchIdContrasenaHistorial;
	}

	public String getCchContrasena() {
		return this.cchContrasena;
	}
	public void setCchContrasena(String cchContrasena) {
		this.cchContrasena = cchContrasena;
	}

	//bi-directional many-to-one association to CvUsuarioPortal
	public CvUsuarioPortal getCvUsuarioPortal() {
		return this.cvUsuarioPortal;
	}
	public void setCvUsuarioPortal(CvUsuarioPortal cvUsuarioPortal) {
		this.cvUsuarioPortal = cvUsuarioPortal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvContrasenaHistorial)) {
			return false;
		}
		CvContrasenaHistorial castOther = (CvContrasenaHistorial)other;
		return new EqualsBuilder()
			.append(this.getCchIdContrasenaHistorial(), castOther.getCchIdContrasenaHistorial())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCchIdContrasenaHistorial())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("cchIdContrasenaHistorial", getCchIdContrasenaHistorial())
			.toString();
	}
}