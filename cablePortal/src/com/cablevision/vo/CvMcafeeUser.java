package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_MCAFEEUSERS database table.
 * 
 * @author BEA Workshop
 */
public class CvMcafeeUser  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long musId;
	private long musAccount;
	private String musAccountid;
	private String musContactid;
	private String musCvstatus;
	private java.sql.Timestamp musDatesuscribe;
	private String musEmailaddress;
	private String musPassword;
	private String musReference;

    public CvMcafeeUser() {
    }

	public Long getMusId() {
		return this.musId;
	}
	public void setMusId(Long musId) {
		this.musId = musId;
	}

	public long getMusAccount() {
		return this.musAccount;
	}
	public void setMusAccount(long musAccount) {
		this.musAccount = musAccount;
	}

	public String getMusAccountid() {
		return this.musAccountid;
	}
	public void setMusAccountid(String musAccountid) {
		this.musAccountid = musAccountid;
	}

	public String getMusContactid() {
		return this.musContactid;
	}
	public void setMusContactid(String musContactid) {
		this.musContactid = musContactid;
	}

	public String getMusCvstatus() {
		return this.musCvstatus;
	}
	public void setMusCvstatus(String musCvstatus) {
		this.musCvstatus = musCvstatus;
	}

	public java.sql.Timestamp getMusDatesuscribe() {
		return this.musDatesuscribe;
	}
	public void setMusDatesuscribe(java.sql.Timestamp musDatesuscribe) {
		this.musDatesuscribe = musDatesuscribe;
	}

	public String getMusEmailaddress() {
		return this.musEmailaddress;
	}
	public void setMusEmailaddress(String musEmailaddress) {
		this.musEmailaddress = musEmailaddress;
	}

	public String getMusPassword() {
		return this.musPassword;
	}
	public void setMusPassword(String musPassword) {
		this.musPassword = musPassword;
	}

	public String getMusReference() {
		return this.musReference;
	}
	public void setMusReference(String musReference) {
		this.musReference = musReference;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafeeUser)) {
			return false;
		}
		CvMcafeeUser castOther = (CvMcafeeUser)other;
		return new EqualsBuilder()
			.append(this.getMusId(), castOther.getMusId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMusId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("musId", getMusId())
			.toString();
	}
}