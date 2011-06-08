package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_MCAFEE database table.
 * 
 * @author BEA Workshop
 */
public class CvMcafee  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long mcaId;
	private long mcaAccount;
	private String mcaContactid;
	private String mcaCvstatus;
	private java.sql.Timestamp mcaDatesuscribe;
	private String mcaEmailaddress;
	private String mcaPassword;
	private String mcaReference;
	private String mcaFirstName;
	private String mcaLastName;
	private Boolean registroMultiple;

    public CvMcafee() {
    }

	public Long getMcaId() {
		return this.mcaId;
	}
	public void setMcaId(Long mcaId) {
		this.mcaId = mcaId;
	}

	public long getMcaAccount() {
		return this.mcaAccount;
	}
	public void setMcaAccount(long mcaAccount) {
		this.mcaAccount = mcaAccount;
	}

	public String getMcaContactid() {
		return this.mcaContactid;
	}
	public void setMcaContactid(String mcaContactid) {
		this.mcaContactid = mcaContactid;
	}

	public String getMcaCvstatus() {
		return this.mcaCvstatus;
	}
	public void setMcaCvstatus(String mcaCvstatus) {
		this.mcaCvstatus = mcaCvstatus;
	}

	public java.sql.Timestamp getMcaDatesuscribe() {
		return this.mcaDatesuscribe;
	}
	public void setMcaDatesuscribe(java.sql.Timestamp mcaDatesuscribe) {
		this.mcaDatesuscribe = mcaDatesuscribe;
	}

	public String getMcaEmailaddress() {
		return this.mcaEmailaddress;
	}
	public void setMcaEmailaddress(String mcaEmailaddress) {
		this.mcaEmailaddress = mcaEmailaddress;
	}

	public String getMcaPassword() {
		return this.mcaPassword;
	}
	public void setMcaPassword(String mcaPassword) {
		this.mcaPassword = mcaPassword;
	}

	public String getMcaReference() {
		return this.mcaReference;
	}
	public void setMcaReference(String mcaReference) {
		this.mcaReference = mcaReference;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafee)) {
			return false;
		}
		CvMcafee castOther = (CvMcafee)other;
		return new EqualsBuilder()
			.append(this.getMcaId(), castOther.getMcaId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMcaId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("mcaId", getMcaId())
			.toString();
	}

	public String getMcaFirstName() {
		return mcaFirstName;
	}

	public void setMcaFirstName(String mcaFirstName) {
		this.mcaFirstName = mcaFirstName;
	}

	public String getMcaLastName() {
		return mcaLastName;
	}

	public void setMcaLastName(String mcaLastname) {
		this.mcaLastName = mcaLastname;
	}

	public Boolean getRegistroMultiple() {
		return registroMultiple;
	}

	public void setRegistroMultiple(Boolean registroMultiple) {
		this.registroMultiple = registroMultiple;
	}

}