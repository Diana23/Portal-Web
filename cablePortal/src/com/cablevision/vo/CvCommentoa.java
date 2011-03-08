package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_COMMENTOAS database table.
 * 
 * @author BEA Workshop
 */
public class CvCommentoa  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long coaId;
	private String coaComment;
	private java.sql.Timestamp coaDate;
	private long coaToaId;
	private CvTrackoa cvTrackoa;

    public CvCommentoa() {
    }

	public Long getCoaId() {
		return this.coaId;
	}
	public void setCoaId(Long coaId) {
		this.coaId = coaId;
	}

	public String getCoaComment() {
		return this.coaComment;
	}
	public void setCoaComment(String coaComment) {
		this.coaComment = coaComment;
	}

	public java.sql.Timestamp getCoaDate() {
		return this.coaDate;
	}
	public void setCoaDate(java.sql.Timestamp coaDate) {
		this.coaDate = coaDate;
	}

	public long getCoaToaId() {
		return this.coaToaId;
	}
	public void setCoaToaId(long coaToaId) {
		this.coaToaId = coaToaId;
	}

	//bi-directional one-to-one association to CvTrackoa
	public CvTrackoa getCvTrackoa() {
		return this.cvTrackoa;
	}
	public void setCvTrackoa(CvTrackoa cvTrackoa) {
		this.cvTrackoa = cvTrackoa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvCommentoa)) {
			return false;
		}
		CvCommentoa castOther = (CvCommentoa)other;
		return new EqualsBuilder()
			.append(this.getCoaId(), castOther.getCoaId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCoaId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("coaId", getCoaId())
			.toString();
	}
}