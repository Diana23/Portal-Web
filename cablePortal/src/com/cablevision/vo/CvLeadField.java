package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the cvleadfields database table.
 * 
 * @author BEA Workshop
 */
public class CvLeadField  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private CvLeadFieldId id;
	private Long clfSend;
	private Long clfOrden;

    public CvLeadField() {
    }

    public Long getClfSend() {
		return this.clfSend;
	}
	public void setClfSend(Long clfSend) {
		this.clfSend = clfSend;
	}

	public Long getClfOrden() {
		return clfOrden;
	}
	public void setClfOrden(Long clfOrden) {
		this.clfOrden = clfOrden;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvLeadField)) {
			return false;
		}
		//CvLeadField castOther = (CvLeadField)other;
		return new EqualsBuilder()
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.toString();
	}

	public CvLeadFieldId getId() {
		return id;
	}

	public void setId(CvLeadFieldId id) {
		this.id = id;
	}
}