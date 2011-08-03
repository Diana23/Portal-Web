package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_MCAFEEDOWNLOADS database table.
 * 
 * @author BEA Workshop
 */
public class CvMcafeeDownload  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long mdlId;
	private java.sql.Timestamp mdlDate;
	private long mdlIduser;
	private String mdlRefer;
	private String mdlStatus;
	private String mdlTypeaccess;

    public CvMcafeeDownload() {
    }

	public Long getMdlId() {
		return this.mdlId;
	}
	public void setMdlId(Long mdlId) {
		this.mdlId = mdlId;
	}

	public java.sql.Timestamp getMdlDate() {
		return this.mdlDate;
	}
	public void setMdlDate(java.sql.Timestamp mdlDate) {
		this.mdlDate = mdlDate;
	}

	public long getMdlIduser() {
		return this.mdlIduser;
	}
	public void setMdlIduser(long mdlIduser) {
		this.mdlIduser = mdlIduser;
	}

	public String getMdlRefer() {
		return this.mdlRefer;
	}
	public void setMdlRefer(String mdlRefer) {
		this.mdlRefer = mdlRefer;
	}

	public String getMdlStatus() {
		return this.mdlStatus;
	}
	public void setMdlStatus(String mdlStatus) {
		this.mdlStatus = mdlStatus;
	}

	public String getMdlTypeaccess() {
		return this.mdlTypeaccess;
	}
	public void setMdlTypeaccess(String mdlTypeaccess) {
		this.mdlTypeaccess = mdlTypeaccess;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafeeDownload)) {
			return false;
		}
		CvMcafeeDownload castOther = (CvMcafeeDownload)other;
		return new EqualsBuilder()
			.append(this.getMdlId(), castOther.getMdlId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMdlId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("mdlId", getMdlId())
			.toString();
	}
}