package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_TIME_ZONE_RECORD database table.
 * 
 * @author BEA Workshop
 */
public class CvTimeZoneRecord  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String tzrTimeZoneName;
	private java.util.Date tzrDateTime1;
	private java.util.Date tzrDateTime2;
	private int tzrUtcStdOffset;

    public CvTimeZoneRecord() {
    }

	public String getTzrTimeZoneName() {
		return this.tzrTimeZoneName;
	}
	public void setTzrTimeZoneName(String tzrTimeZoneName) {
		this.tzrTimeZoneName = tzrTimeZoneName;
	}

	public java.util.Date getTzrDateTime1() {
		return this.tzrDateTime1;
	}
	public void setTzrDateTime1(java.util.Date tzrDateTime1) {
		this.tzrDateTime1 = tzrDateTime1;
	}

	public java.util.Date getTzrDateTime2() {
		return this.tzrDateTime2;
	}
	public void setTzrDateTime2(java.util.Date tzrDateTime2) {
		this.tzrDateTime2 = tzrDateTime2;
	}

	public int getTzrUtcStdOffset() {
		return this.tzrUtcStdOffset;
	}
	public void setTzrUtcStdOffset(int tzrUtcStdOffset) {
		this.tzrUtcStdOffset = tzrUtcStdOffset;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvTimeZoneRecord)) {
			return false;
		}
		CvTimeZoneRecord castOther = (CvTimeZoneRecord)other;
		return new EqualsBuilder()
			.append(this.getTzrTimeZoneName(), castOther.getTzrTimeZoneName())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTzrTimeZoneName())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("tzrTimeZoneName", getTzrTimeZoneName())
			.toString();
	}
}