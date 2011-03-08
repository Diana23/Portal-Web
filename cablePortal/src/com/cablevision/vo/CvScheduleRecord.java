package com.cablevision.vo;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_SCHEDULE_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvScheduleRecord  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CvScheduleRecordPK compId;
	private Date skeEndDateTime;
	private Boolean skeCableInTheClassroom;
	private Boolean skeCc;
	private Boolean skeDialogRating;
	private String skeDolby;
	private Boolean skeDubbed;
	private String skeDubbedLanguage;
	private String skeDuration;
	private Boolean skeDvs;
	private String skeEi;
	private Boolean skeEnhanced;
	private Boolean skeFvRating;
	private Boolean skeHdtv;
	private Boolean skeJoinedInProgress;
	private Boolean skeLanguageRating;
	private Boolean skeLeftInProgress;
	private Boolean skeLetterbox;
	private String skeLiveTapeDelay;
	private String skeNetSynSource;
	private String skeNetSynType;
	private String skeNew;
	private Integer skeNumOfParts;
	private Integer skePartNum;
	private String skePremiereFinale;
	private Boolean skeSap;
	private String skeSapLanguage;
	private Boolean skeSexRating;
	private Boolean skeStereo;
	private String skeSubjectToBlackout;
	private Boolean skeSubtitled;
	private String skeSubtitledLanguage;
	private Boolean skeThreeD;
	private String skeTimeApproximate;
	private String skeTvRating;
	private Boolean skeViolenceRating;
	private CvProgramRecord cvProgramRecord;
	private CvStationRecord cvStationRecord;

    public CvScheduleRecord() {
    }

	public CvScheduleRecordPK getCompId() {
		return this.compId;
	}
	public void setCompId(CvScheduleRecordPK compId) {
		this.compId = compId;
	}

	public Boolean getSkeCableInTheClassroom() {
		return this.skeCableInTheClassroom;
	}
	public void setSkeCableInTheClassroom(Boolean skeCableInTheClassroom) {
		this.skeCableInTheClassroom = skeCableInTheClassroom;
	}

	public Boolean getSkeCc() {
		return this.skeCc;
	}
	public void setSkeCc(Boolean skeCc) {
		this.skeCc = skeCc;
	}

	public Boolean getSkeDialogRating() {
		return this.skeDialogRating;
	}
	public void setSkeDialogRating(Boolean skeDialogRating) {
		this.skeDialogRating = skeDialogRating;
	}

	public String getSkeDolby() {
		return this.skeDolby;
	}
	public void setSkeDolby(String skeDolby) {
		this.skeDolby = skeDolby;
	}

	public Boolean getSkeDubbed() {
		return this.skeDubbed;
	}
	public void setSkeDubbed(Boolean skeDubbed) {
		this.skeDubbed = skeDubbed;
	}

	public String getSkeDubbedLanguage() {
		return this.skeDubbedLanguage;
	}
	public void setSkeDubbedLanguage(String skeDubbedLanguage) {
		this.skeDubbedLanguage = skeDubbedLanguage;
	}

	public String getSkeDuration() {
		return this.skeDuration;
	}
	public void setSkeDuration(String skeDuration) {
		this.skeDuration = skeDuration;
	}

	public Boolean getSkeDvs() {
		return this.skeDvs;
	}
	public void setSkeDvs(Boolean skeDvs) {
		this.skeDvs = skeDvs;
	}

	public String getSkeEi() {
		return this.skeEi;
	}
	public void setSkeEi(String skeEi) {
		this.skeEi = skeEi;
	}

	public Boolean getSkeEnhanced() {
		return this.skeEnhanced;
	}
	public void setSkeEnhanced(Boolean skeEnhanced) {
		this.skeEnhanced = skeEnhanced;
	}

	public Boolean getSkeFvRating() {
		return this.skeFvRating;
	}
	public void setSkeFvRating(Boolean skeFvRating) {
		this.skeFvRating = skeFvRating;
	}

	public Boolean getSkeHdtv() {
		return this.skeHdtv;
	}
	public void setSkeHdtv(Boolean skeHdtv) {
		this.skeHdtv = skeHdtv;
	}

	public Boolean getSkeJoinedInProgress() {
		return this.skeJoinedInProgress;
	}
	public void setSkeJoinedInProgress(Boolean skeJoinedInProgress) {
		this.skeJoinedInProgress = skeJoinedInProgress;
	}

	public Boolean getSkeLanguageRating() {
		return this.skeLanguageRating;
	}
	public void setSkeLanguageRating(Boolean skeLanguageRating) {
		this.skeLanguageRating = skeLanguageRating;
	}

	public Boolean getSkeLeftInProgress() {
		return this.skeLeftInProgress;
	}
	public void setSkeLeftInProgress(Boolean skeLeftInProgress) {
		this.skeLeftInProgress = skeLeftInProgress;
	}

	public Boolean getSkeLetterbox() {
		return this.skeLetterbox;
	}
	public void setSkeLetterbox(Boolean skeLetterbox) {
		this.skeLetterbox = skeLetterbox;
	}

	public String getSkeLiveTapeDelay() {
		return this.skeLiveTapeDelay;
	}
	public void setSkeLiveTapeDelay(String skeLiveTapeDelay) {
		this.skeLiveTapeDelay = skeLiveTapeDelay;
	}

	public String getSkeNetSynSource() {
		return this.skeNetSynSource;
	}
	public void setSkeNetSynSource(String skeNetSynSource) {
		this.skeNetSynSource = skeNetSynSource;
	}

	public String getSkeNetSynType() {
		return this.skeNetSynType;
	}
	public void setSkeNetSynType(String skeNetSynType) {
		this.skeNetSynType = skeNetSynType;
	}

	public String getSkeNew() {
		return this.skeNew;
	}
	public void setSkeNew(String skeNew) {
		this.skeNew = skeNew;
	}

	public Integer getSkeNumOfParts() {
		return this.skeNumOfParts;
	}
	public void setSkeNumOfParts(Integer skeNumOfParts) {
		this.skeNumOfParts = skeNumOfParts;
	}

	public Integer getSkePartNum() {
		return this.skePartNum;
	}
	public void setSkePartNum(Integer skePartNum) {
		this.skePartNum = skePartNum;
	}

	public String getSkePremiereFinale() {
		return this.skePremiereFinale;
	}
	public void setSkePremiereFinale(String skePremiereFinale) {
		this.skePremiereFinale = skePremiereFinale;
	}

	public Boolean getSkeSap() {
		return this.skeSap;
	}
	public void setSkeSap(Boolean skeSap) {
		this.skeSap = skeSap;
	}

	public String getSkeSapLanguage() {
		return this.skeSapLanguage;
	}
	public void setSkeSapLanguage(String skeSapLanguage) {
		this.skeSapLanguage = skeSapLanguage;
	}

	public Boolean getSkeSexRating() {
		return this.skeSexRating;
	}
	public void setSkeSexRating(Boolean skeSexRating) {
		this.skeSexRating = skeSexRating;
	}

	public Boolean getSkeStereo() {
		return this.skeStereo;
	}
	public void setSkeStereo(Boolean skeStereo) {
		this.skeStereo = skeStereo;
	}

	public String getSkeSubjectToBlackout() {
		return this.skeSubjectToBlackout;
	}
	public void setSkeSubjectToBlackout(String skeSubjectToBlackout) {
		this.skeSubjectToBlackout = skeSubjectToBlackout;
	}

	public Boolean getSkeSubtitled() {
		return this.skeSubtitled;
	}
	public void setSkeSubtitled(Boolean skeSubtitled) {
		this.skeSubtitled = skeSubtitled;
	}

	public String getSkeSubtitledLanguage() {
		return this.skeSubtitledLanguage;
	}
	public void setSkeSubtitledLanguage(String skeSubtitledLanguage) {
		this.skeSubtitledLanguage = skeSubtitledLanguage;
	}

	public Boolean getSkeThreeD() {
		return this.skeThreeD;
	}
	public void setSkeThreeD(Boolean skeThreeD) {
		this.skeThreeD = skeThreeD;
	}

	public String getSkeTimeApproximate() {
		return this.skeTimeApproximate;
	}
	public void setSkeTimeApproximate(String skeTimeApproximate) {
		this.skeTimeApproximate = skeTimeApproximate;
	}

	public String getSkeTvRating() {
		return this.skeTvRating;
	}
	public void setSkeTvRating(String skeTvRating) {
		this.skeTvRating = skeTvRating;
	}

	public Boolean getSkeViolenceRating() {
		return this.skeViolenceRating;
	}
	public void setSkeViolenceRating(Boolean skeViolenceRating) {
		this.skeViolenceRating = skeViolenceRating;
	}

	//bi-directional many-to-one association to CvProgramRecord
	public CvProgramRecord getCvProgramRecord() {
		return this.cvProgramRecord;
	}
	public void setCvProgramRecord(CvProgramRecord cvProgramRecord) {
		this.cvProgramRecord = cvProgramRecord;
	}

	//bi-directional many-to-one association to CvStationRecord
	public CvStationRecord getCvStationRecord() {
		return this.cvStationRecord;
	}
	public void setCvStationRecord(CvStationRecord cvStationRecord) {
		this.cvStationRecord = cvStationRecord;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvScheduleRecord)) {
			return false;
		}
		CvScheduleRecord castOther = (CvScheduleRecord)other;
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

	public Date getSkeEndDateTime() {
		return skeEndDateTime;
	}

	public void setSkeEndDateTime(Date skeEndDateTime) {
		this.skeEndDateTime = skeEndDateTime;
	}
}