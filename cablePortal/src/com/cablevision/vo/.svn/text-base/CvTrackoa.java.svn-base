package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_TRACKOAS database table.
 * 
 * @author BEA Workshop
 */
public class CvTrackoa  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long toaId;
	private String toaAccountid;
	private String toaContactid;
	private java.sql.Timestamp toaDate;
	private String toaDescripcion;
	private String toaErrorCode;
	private String toaErrorDescription;
	private String toaMotive;
	private String toaNumberOa;
	private String toaResponseAction;
	private String toaSubtype;
	private String toaType;
	private CvCommentoa cvCommentoa;

    public CvTrackoa() {
    }

	public Long getToaId() {
		return this.toaId;
	}
	public void setToaId(Long toaId) {
		this.toaId = toaId;
	}

	public String getToaAccountid() {
		return this.toaAccountid;
	}
	public void setToaAccountid(String toaAccountid) {
		this.toaAccountid = toaAccountid;
	}

	public String getToaContactid() {
		return this.toaContactid;
	}
	public void setToaContactid(String toaContactid) {
		this.toaContactid = toaContactid;
	}

	public java.sql.Timestamp getToaDate() {
		return this.toaDate;
	}
	public void setToaDate(java.sql.Timestamp toaDate) {
		this.toaDate = toaDate;
	}

	public String getToaDescripcion() {
		return this.toaDescripcion;
	}
	public void setToaDescripcion(String toaDescripcion) {
		this.toaDescripcion = toaDescripcion;
	}

	public String getToaErrorCode() {
		return this.toaErrorCode;
	}
	public void setToaErrorCode(String toaErrorCode) {
		this.toaErrorCode = toaErrorCode;
	}

	public String getToaErrorDescription() {
		return this.toaErrorDescription;
	}
	public void setToaErrorDescription(String toaErrorDescription) {
		this.toaErrorDescription = toaErrorDescription;
	}

	public String getToaMotive() {
		return this.toaMotive;
	}
	public void setToaMotive(String toaMotive) {
		this.toaMotive = toaMotive;
	}

	public String getToaNumberOa() {
		return this.toaNumberOa;
	}
	public void setToaNumberOa(String toaNumberOa) {
		this.toaNumberOa = toaNumberOa;
	}

	public String getToaResponseAction() {
		return this.toaResponseAction;
	}
	public void setToaResponseAction(String toaResponseAction) {
		this.toaResponseAction = toaResponseAction;
	}

	public String getToaSubtype() {
		return this.toaSubtype;
	}
	public void setToaSubtype(String toaSubtype) {
		this.toaSubtype = toaSubtype;
	}

	public String getToaType() {
		return this.toaType;
	}
	public void setToaType(String toaType) {
		this.toaType = toaType;
	}

	//bi-directional one-to-one association to CvCommentoa
	public CvCommentoa getCvCommentoa() {
		return this.cvCommentoa;
	}
	public void setCvCommentoa(CvCommentoa cvCommentoa) {
		this.cvCommentoa = cvCommentoa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvTrackoa)) {
			return false;
		}
		CvTrackoa castOther = (CvTrackoa)other;
		return new EqualsBuilder()
			.append(this.getToaId(), castOther.getToaId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getToaId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("toaId", getToaId())
			.toString();
	}
}