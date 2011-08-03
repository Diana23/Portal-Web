package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_ESERVICE_N3 database table.
 * 
 * @author BEA Workshop
 */
public class CvEserviceN3  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long en3Id;
	private Long en3En2Id;
	private String en3Name;

    public CvEserviceN3() {
    }

	public Long getEn3Id() {
		return this.en3Id;
	}
	public void setEn3Id(Long en3Id) {
		this.en3Id = en3Id;
	}

	public Long getEn3En2Id() {
		return this.en3En2Id;
	}
	public void setEn3En2Id(Long en3En2Id) {
		this.en3En2Id = en3En2Id;
	}

	public String getEn3Name() {
		return this.en3Name;
	}
	public void setEn3Name(String en3Name) {
		this.en3Name = en3Name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvEserviceN3)) {
			return false;
		}
		CvEserviceN3 castOther = (CvEserviceN3)other;
		return new EqualsBuilder()
			.append(this.getEn3Id(), castOther.getEn3Id())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getEn3Id())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("en3Id", getEn3Id())
			.toString();
	}
}