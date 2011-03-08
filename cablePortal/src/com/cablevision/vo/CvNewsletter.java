package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_NEWSLETTER database table.
 * 
 * @author BEA Workshop
 */
public class CvNewsletter implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long nwlId;
	private Long nwlAccount;
	private String nwlEmail;
	private String nwlIsclient;
	private String nwlName;

    public CvNewsletter() {
    }

	public Long getNwlId() {
		return this.nwlId;
	}
	public void setNwlId(Long nwlId) {
		this.nwlId = nwlId;
	}

	public Long getNwlAccount() {
		return this.nwlAccount;
	}
	public void setNwlAccount(Long nwlAccount) {
		this.nwlAccount = nwlAccount;
	}

	public String getNwlEmail() {
		return this.nwlEmail;
	}
	public void setNwlEmail(String nwlEmail) {
		this.nwlEmail = nwlEmail;
	}

	public String getNwlIsclient() {
		return this.nwlIsclient;
	}
	public void setNwlIsclient(String nwlIsclient) {
		this.nwlIsclient = nwlIsclient;
	}

	public String getNwlName() {
		return this.nwlName;
	}
	public void setNwlName(String nwlName) {
		this.nwlName = nwlName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvNewsletter)) {
			return false;
		}
		CvNewsletter castOther = (CvNewsletter)other;
		return new EqualsBuilder()
			.append(this.getNwlId(), castOther.getNwlId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getNwlId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("nwlId", getNwlId())
			.toString();
	}
}