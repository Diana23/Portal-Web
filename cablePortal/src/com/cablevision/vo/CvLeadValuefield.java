package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the cvleadvaluefield database table.
 * 
 * @author BEA Workshop
 */
public class CvLeadValuefield  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long lvfIdLeadvalue;
	private String lvfValue;
	private CvLead lead;
	private CvField field;
	
    public CvLeadValuefield() {
    }

    public Long getLvfIdLeadvalue() {
		return this.lvfIdLeadvalue;
	}
	public void setLvfIdLeadvalue(Long lvfIdLeadvalue) {
		this.lvfIdLeadvalue = lvfIdLeadvalue;
	}

	public String getLvfValue() {
		return this.lvfValue;
	}
	public void setLvfValue(String lvfValue) {
		this.lvfValue = lvfValue;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvLeadValuefield)) {
			return false;
		}
		CvLeadValuefield castOther = (CvLeadValuefield)other;
		return new EqualsBuilder()
			.append(this.getLvfIdLeadvalue(), castOther.getLvfIdLeadvalue())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getLvfIdLeadvalue())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idLeadValue", getLvfIdLeadvalue())
			.toString();
	}

	public CvLead getLead() {
		return lead;
	}

	public void setLead(CvLead lead) {
		this.lead = lead;
	}

	public CvField getField() {
		return field;
	}

	public void setField(CvField field) {
		this.field = field;
	}
}