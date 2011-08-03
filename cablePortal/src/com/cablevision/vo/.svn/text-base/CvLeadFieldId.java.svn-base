package com.cablevision.vo;

import java.io.Serializable;

public class CvLeadFieldId implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private CvLeadType leadType;
	private CvField field;
	
	public CvLeadType getLeadType() {
		return leadType;
	}

	public void setLeadType(CvLeadType leadType) {
		this.leadType = leadType;
	}

	public CvField getField() {
		return field;
	}

	public void setField(CvField field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result
				+ ((leadType == null) ? 0 : leadType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CvLeadFieldId other = (CvLeadFieldId) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (leadType == null) {
			if (other.leadType != null)
				return false;
		} else if (!leadType.equals(other.leadType))
			return false;
		return true;
	}

	public String toString() {
		return leadType.toString()+field.toString();
	}
}
