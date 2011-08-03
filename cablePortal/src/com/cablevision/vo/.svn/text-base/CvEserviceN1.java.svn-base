package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_ESERVICE_N1 database table.
 * 
 * @author BEA Workshop
 */
public class CvEserviceN1  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long en1Id;
	private String en1Name;

    public CvEserviceN1() {
    }

	public Long getEn1Id() {
		return this.en1Id;
	}
	public void setEn1Id(Long en1Id) {
		this.en1Id = en1Id;
	}

	public String getEn1Name() {
		return this.en1Name;
	}
	public void setEn1Name(String en1Name) {
		this.en1Name = en1Name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvEserviceN1)) {
			return false;
		}
		CvEserviceN1 castOther = (CvEserviceN1)other;
		return new EqualsBuilder()
			.append(this.getEn1Id(), castOther.getEn1Id())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getEn1Id())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("en1Id", getEn1Id())
			.toString();
	}
}