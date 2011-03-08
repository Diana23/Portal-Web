package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_MCAFEESUSCRIBED database table.
 * 
 * @author BEA Workshop
 */
public class CvMcafeesuscribed  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long mteId;
	private Long mteAccount;
	private Long mteAttemps;
	private String mteEmail;
	private Long mteIduser;
	private String mteSource;
	private String mteStatus;
	private java.sql.Timestamp mteSuscribedate;

    public CvMcafeesuscribed() {
    }

	public Long getMteId() {
		return this.mteId;
	}
	public void setMteId(Long mteId) {
		this.mteId = mteId;
	}

	public Long getMteAccount() {
		return this.mteAccount;
	}
	public void setMteAccount(Long mteAccount) {
		this.mteAccount = mteAccount;
	}

	public Long getMteAttemps() {
		return this.mteAttemps;
	}
	public void setMteAttemps(Long mteAttemps) {
		this.mteAttemps = mteAttemps;
	}

	public String getMteEmail() {
		return this.mteEmail;
	}
	public void setMteEmail(String mteEmail) {
		this.mteEmail = mteEmail;
	}

	public Long getMteIduser() {
		return this.mteIduser;
	}
	public void setMteIduser(Long mteIduser) {
		this.mteIduser = mteIduser;
	}

	public String getMteSource() {
		return this.mteSource;
	}
	public void setMteSource(String mteSource) {
		this.mteSource = mteSource;
	}

	public String getMteStatus() {
		return this.mteStatus;
	}
	public void setMteStatus(String mteStatus) {
		this.mteStatus = mteStatus;
	}

	public java.sql.Timestamp getMteSuscribedate() {
		return this.mteSuscribedate;
	}
	public void setMteSuscribedate(java.sql.Timestamp mteSuscribedate) {
		this.mteSuscribedate = mteSuscribedate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafeesuscribed)) {
			return false;
		}
		CvMcafeesuscribed castOther = (CvMcafeesuscribed)other;
		return new EqualsBuilder()
			.append(this.getMteId(), castOther.getMteId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMteId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("mteId", getMteId())
			.toString();
	}
}