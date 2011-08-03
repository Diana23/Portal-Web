package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_BANCO database table.
 * 
 * @author BEA Workshop
 */
public class CvBanco  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long banId;
	private String banNombre;

    public CvBanco() {
    }

	public Long getBanId() {
		return this.banId;
	}
	public void setBanId(Long banId) {
		this.banId = banId;
	}

	public String getBanNombre() {
		return this.banNombre;
	}
	public void setBanNombre(String banNombre) {
		this.banNombre = banNombre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvBanco)) {
			return false;
		}
		CvBanco castOther = (CvBanco)other;
		return new EqualsBuilder()
			.append(this.getBanId(), castOther.getBanId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getBanId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("banId", getBanId())
			.toString();
	}
}