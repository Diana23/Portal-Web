package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_TRANSLATION_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvTranslationRecord  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CvTranslationRecordPK compId;
	private String trlLanguageTrans;

    public CvTranslationRecord() {
    }

	public CvTranslationRecordPK getCompId() {
		return this.compId;
	}
	public void setCompId(CvTranslationRecordPK compId) {
		this.compId = compId;
	}

	public String getTrlLanguageTrans() {
		return this.trlLanguageTrans;
	}
	public void setTrlLanguageTrans(String trlLanguageTrans) {
		this.trlLanguageTrans = trlLanguageTrans;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvTranslationRecord)) {
			return false;
		}
		CvTranslationRecord castOther = (CvTranslationRecord)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}