package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CV_TRANSLATION_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvTranslationRecordPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String trlEnglishTrans;
	private String trlProgramLanguage;

    public CvTranslationRecordPK() {
    }
    
    public CvTranslationRecordPK(String trlEnglishTrans, String trlProgramLanguage) {
    	this.trlEnglishTrans = trlEnglishTrans;
    	this.trlProgramLanguage = trlProgramLanguage;
    }

	public String getTrlEnglishTrans() {
		return this.trlEnglishTrans;
	}
	public void setTrlEnglishTrans(String trlEnglishTrans) {
		this.trlEnglishTrans = trlEnglishTrans;
	}

	public String getTrlProgramLanguage() {
		return this.trlProgramLanguage;
	}
	public void setTrlProgramLanguage(String trlProgramLanguage) {
		this.trlProgramLanguage = trlProgramLanguage;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvTranslationRecordPK)) {
			return false;
		}
		CvTranslationRecordPK castOther = (CvTranslationRecordPK)other;
		return new EqualsBuilder()
			.append(this.getTrlEnglishTrans(), castOther.getTrlEnglishTrans())
			.append(this.getTrlProgramLanguage(), castOther.getTrlProgramLanguage())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTrlEnglishTrans())
			.append(getTrlProgramLanguage())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("trlEnglishTrans", getTrlEnglishTrans())
			.append("trlProgramLanguage", getTrlProgramLanguage())
			.toString();
	}
}