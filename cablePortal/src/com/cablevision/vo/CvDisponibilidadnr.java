package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_DISPONIBILIDADNR database table.
 * 
 * @author BEA Workshop
 */
public class CvDisponibilidadnr  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String dispCp;
	private String dispDisponible;

    public CvDisponibilidadnr() {
    }

	public String getDispCp() {
		return this.dispCp;
	}
	public void setDispCp(String dispCp) {
		this.dispCp = dispCp;
	}

	public String getDispDisponible() {
		return this.dispDisponible;
	}
	public void setDispDisponible(String dispDisponible) {
		this.dispDisponible = dispDisponible;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvDisponibilidadnr)) {
			return false;
		}
		CvDisponibilidadnr castOther = (CvDisponibilidadnr)other;
		return new EqualsBuilder()
			.append(this.getDispCp(), castOther.getDispCp())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDispCp())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("dispCp", getDispCp())
			.toString();
	}
}