package com.cablevision.vo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The persistent class for the cvleadstatus database table.
 * 
 * @author BEA Workshop
 */
public class CvLeadStatus  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long clsIdLeadstatus;
	private String clsIcon;
	private String clsMessagestatus;
	private String clsNameleadstatus;
	
	private Set<CvLead> leads = new HashSet<CvLead>();

    public CvLeadStatus() {
    }

    public CvLeadStatus(Long clsIdLeadstatus) {
    	this.clsIdLeadstatus = clsIdLeadstatus;
    }
    
    public Long getClsIdLeadstatus() {
		return this.clsIdLeadstatus;
	}
	public void setClsIdLeadstatus(Long clsIdLeadstatus) {
		this.clsIdLeadstatus = clsIdLeadstatus;
	}

	public String getClsIcon() {
		return this.clsIcon;
	}
	public void setClsIcon(String clsIcon) {
		this.clsIcon = clsIcon;
	}

	public String getClsMessagestatus() {
		return this.clsMessagestatus;
	}
	public void setClsMessagestatus(String clsMessagestatus) {
		this.clsMessagestatus = clsMessagestatus;
	}

	public String getClsNameleadstatus() {
		return this.clsNameleadstatus;
	}
	public void setClsNameleadstatus(String clsNameleadstatus) {
		this.clsNameleadstatus = clsNameleadstatus;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvLeadStatus)) {
			return false;
		}
		CvLeadStatus castOther = (CvLeadStatus)other;
		return new EqualsBuilder()
			.append(this.getClsIdLeadstatus(), castOther.getClsIdLeadstatus())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getClsIdLeadstatus())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("clsIdLeadstatus", getClsIdLeadstatus())
			.toString();
	}

	public Set<CvLead> getLeads() {
		return leads;
	}

	public void setLeads(Set<CvLead> leads) {
		this.leads = leads;
	}
}