package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_ESERVICE_N2 database table.
 * 
 * @author BEA Workshop
 */
public class CvEserviceN2  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long en2Id;
	private Long en2En1Id;
	private String en2Name;

    public CvEserviceN2() {
    }
    
	public Long getEn2Id() {
		return this.en2Id;
	}
	public void setEn2Id(Long en2Id) {
		this.en2Id = en2Id;
	}

	public Long getEn2En1Id() {
		return this.en2En1Id;
	}
	public void setEn2En1Id(Long en2En1Id) {
		this.en2En1Id = en2En1Id;
	}

	public String getEn2Name() {
		return this.en2Name;
	}
	public void setEn2Name(String en2Name) {
		this.en2Name = en2Name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvEserviceN2)) {
			return false;
		}
		CvEserviceN2 castOther = (CvEserviceN2)other;
		return new EqualsBuilder()
			.append(this.getEn2Id(), castOther.getEn2Id())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getEn2Id())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("en2Id", getEn2Id())
			.toString();
	}
}