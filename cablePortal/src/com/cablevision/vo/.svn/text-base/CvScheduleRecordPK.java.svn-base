package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CV_SCHEDULE_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvScheduleRecordPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private java.util.Date skeAirDateTime;
	private String skeDatabaseKey;
	private Long skeStationNum;

    public CvScheduleRecordPK() {
    }

	public java.util.Date getSkeAirDateTime() {
		return this.skeAirDateTime;
	}
	public void setSkeAirDateTime(java.util.Date skeAirDateTime) {
		this.skeAirDateTime = skeAirDateTime;
	}

	public String getSkeDatabaseKey() {
		return this.skeDatabaseKey;
	}
	public void setSkeDatabaseKey(String skeDatabaseKey) {
		this.skeDatabaseKey = skeDatabaseKey;
	}

	public Long getSkeStationNum() {
		return this.skeStationNum;
	}
	public void setSkeStationNum(Long skeStationNum) {
		this.skeStationNum = skeStationNum;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvScheduleRecordPK)) {
			return false;
		}
		CvScheduleRecordPK castOther = (CvScheduleRecordPK)other;
		return new EqualsBuilder()
			.append(this.getSkeAirDateTime(), castOther.getSkeAirDateTime())
			.append(this.getSkeDatabaseKey(), castOther.getSkeDatabaseKey())
			.append(this.getSkeStationNum(), castOther.getSkeStationNum())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSkeAirDateTime())
			.append(getSkeDatabaseKey())
			.append(getSkeStationNum())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("skeAirDateTime", getSkeAirDateTime())
			.append("skeDatabaseKey", getSkeDatabaseKey())
			.append("skeStationNum", getSkeStationNum())
			.toString();
	}
}