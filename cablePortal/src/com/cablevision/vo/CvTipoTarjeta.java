package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_TIPO_TARJETA database table.
 * 
 * @author BEA Workshop
 */
public class CvTipoTarjeta  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long ttaId;
	private String ttaNombre;

    public CvTipoTarjeta() {
    }

	public Long getTtaId() {
		return this.ttaId;
	}
	public void setTtaId(Long ttaId) {
		this.ttaId = ttaId;
	}

	public String getTtaNombre() {
		return this.ttaNombre;
	}
	public void setTtaNombre(String ttaNombre) {
		this.ttaNombre = ttaNombre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvTipoTarjeta)) {
			return false;
		}
		CvTipoTarjeta castOther = (CvTipoTarjeta)other;
		return new EqualsBuilder()
			.append(this.getTtaId(), castOther.getTtaId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTtaId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("ttaId", getTtaId())
			.toString();
	}
}