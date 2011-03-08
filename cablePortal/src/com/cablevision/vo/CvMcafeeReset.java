package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_MCAFEERESET database table.
 * 
 * @author BEA Workshop
 */
public class CvMcafeeReset  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long mreId;
	private long mreAccount;
	private String mreComments;
	private java.sql.Date mreDate;
	private long mreIntentos;

    public CvMcafeeReset() {
    }

	public Long getMreId() {
		return this.mreId;
	}
	public void setMreId(Long mreId) {
		this.mreId = mreId;
	}

	public long getMreAccount() {
		return this.mreAccount;
	}
	public void setMreAccount(long mreAccount) {
		this.mreAccount = mreAccount;
	}

	public String getMreComments() {
		return this.mreComments;
	}
	public void setMreComments(String mreComments) {
		this.mreComments = mreComments;
	}

	public java.sql.Date getMreDate() {
		return this.mreDate;
	}
	public void setMreDate(java.sql.Date mreDate) {
		this.mreDate = mreDate;
	}

	public long getMreIntentos() {
		return this.mreIntentos;
	}
	public void setMreIntentos(long mreIntentos) {
		this.mreIntentos = mreIntentos;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafeeReset)) {
			return false;
		}
		CvMcafeeReset castOther = (CvMcafeeReset)other;
		return new EqualsBuilder()
			.append(this.getMreId(), castOther.getMreId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMreId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("mreId", getMreId())
			.toString();
	}
}