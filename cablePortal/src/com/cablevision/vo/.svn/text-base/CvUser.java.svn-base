package com.cablevision.vo;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the cvusers database table.
 * 
 * @author BEA Workshop
 */
public class CvUser  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String email;
	private Date fecha;
	private String lastname;
	private String name;
	private String pass;
	private String secondlastname;
	private String type;
	private Long id;
	private CvCurriculum curriculum;

    public CvUser() {
    }

    public CvUser(String id) {
    	this.email = id;
    }
    
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFecha() {
		return this.fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLastname() {
		return this.lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSecondlastname() {
		return this.secondlastname;
	}
	public void setSecondlastname(String secondlastname) {
		this.secondlastname = secondlastname;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvUser)) {
			return false;
		}
		CvUser castOther = (CvUser)other;
		return new EqualsBuilder()
			.append(this.getEmail(), castOther.getEmail())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getEmail())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("email", getEmail())
			.toString();
	}

	public CvCurriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(CvCurriculum curriculum) {
		if(curriculum!=null)curriculum.setUser(this);
		this.curriculum = curriculum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}