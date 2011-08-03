package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the EMAIL database table.
 * 
 * @author BEA Workshop
 */
public class Email  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String email;
	private java.sql.Timestamp createdAt;
	private String firstName;
	private String lastName;
	private String secretAnswer;
	private String secretQuestion;
	private Integer status;
	private String statusReason;
	private java.sql.Timestamp updatedAt;

    public Email() {
    }

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public java.sql.Timestamp getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecretAnswer() {
		return this.secretAnswer;
	}
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getSecretQuestion() {
		return this.secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusReason() {
		return this.statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public java.sql.Timestamp getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Email)) {
			return false;
		}
		Email castOther = (Email)other;
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
}