package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_STATION_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvStationRecord  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long stnStationNum;
	private String stnDmaName;
	private Long stnDmaNum;
	private Integer stnFccChannelNum;
	private String stnStationAffil;
	private String stnStationCallSign;
	private String stnStationCity;
	private String stnStationCountry;
	private String stnStationLanguage;
	private String stnStationName;
	private String stnStationState;
	private String stnStationTimeZone;
	private String stnStationZipCode;
	private String stnCnlLogo;
	private java.util.Set<CvScheduleRecord> cvScheduleRecords;

    public CvStationRecord() {
    }

	public Long getStnStationNum() {
		return this.stnStationNum;
	}
	public void setStnStationNum(Long stnStationNum) {
		this.stnStationNum = stnStationNum;
	}

	public String getStnDmaName() {
		return this.stnDmaName;
	}
	public void setStnDmaName(String stnDmaName) {
		this.stnDmaName = stnDmaName;
	}

	public Long getStnDmaNum() {
		return this.stnDmaNum;
	}
	public void setStnDmaNum(Long stnDmaNum) {
		this.stnDmaNum = stnDmaNum;
	}

	public Integer getStnFccChannelNum() {
		return this.stnFccChannelNum;
	}
	public void setStnFccChannelNum(Integer stnFccChannelNum) {
		this.stnFccChannelNum = stnFccChannelNum;
	}

	public String getStnStationAffil() {
		return this.stnStationAffil;
	}
	public void setStnStationAffil(String stnStationAffil) {
		this.stnStationAffil = stnStationAffil;
	}

	public String getStnStationCallSign() {
		return this.stnStationCallSign;
	}
	public void setStnStationCallSign(String stnStationCallSign) {
		this.stnStationCallSign = stnStationCallSign;
	}

	public String getStnStationCity() {
		return this.stnStationCity;
	}
	public void setStnStationCity(String stnStationCity) {
		this.stnStationCity = stnStationCity;
	}

	public String getStnStationCountry() {
		return this.stnStationCountry;
	}
	public void setStnStationCountry(String stnStationCountry) {
		this.stnStationCountry = stnStationCountry;
	}

	public String getStnStationLanguage() {
		return this.stnStationLanguage;
	}
	public void setStnStationLanguage(String stnStationLanguage) {
		this.stnStationLanguage = stnStationLanguage;
	}

	public String getStnStationName() {
		return this.stnStationName;
	}
	public void setStnStationName(String stnStationName) {
		this.stnStationName = stnStationName;
	}

	public String getStnStationState() {
		return this.stnStationState;
	}
	public void setStnStationState(String stnStationState) {
		this.stnStationState = stnStationState;
	}

	public String getStnStationTimeZone() {
		return this.stnStationTimeZone;
	}
	public void setStnStationTimeZone(String stnStationTimeZone) {
		this.stnStationTimeZone = stnStationTimeZone;
	}

	public String getStnStationZipCode() {
		return this.stnStationZipCode;
	}
	public void setStnStationZipCode(String stnStationZipCode) {
		this.stnStationZipCode = stnStationZipCode;
	}

	//bi-directional many-to-one association to CvScheduleRecord
	public java.util.Set<CvScheduleRecord> getCvScheduleRecords() {
		return this.cvScheduleRecords;
	}
	public void setCvScheduleRecords(java.util.Set<CvScheduleRecord> cvScheduleRecords) {
		this.cvScheduleRecords = cvScheduleRecords;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvStationRecord)) {
			return false;
		}
		CvStationRecord castOther = (CvStationRecord)other;
		return new EqualsBuilder()
			.append(this.getStnStationNum(), castOther.getStnStationNum())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getStnStationNum())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("stnStationNum", getStnStationNum())
			.toString();
	}

	public String getStnCnlLogo() {
		return stnCnlLogo;
	}

	public void setStnCnlLogo(String stnCnlLogo) {
		this.stnCnlLogo = stnCnlLogo;
	}
}