package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ANSWER database table.
 * 
 * @author BEA Workshop
 */
public class Answer  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idanswer;
	private String answer;
	private long idquestion;
	private String why;

    public Answer() {
    }

	public Long getIdanswer() {
		return this.idanswer;
	}
	public void setIdanswer(Long idanswer) {
		this.idanswer = idanswer;
	}

	public String getAnswer() {
		return this.answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public long getIdquestion() {
		return this.idquestion;
	}
	public void setIdquestion(long idquestion) {
		this.idquestion = idquestion;
	}

	public String getWhy() {
		return this.why;
	}
	public void setWhy(String why) {
		this.why = why;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Answer)) {
			return false;
		}
		Answer castOther = (Answer)other;
		return new EqualsBuilder()
			.append(this.getIdanswer(), castOther.getIdanswer())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdanswer())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idanswer", getIdanswer())
			.toString();
	}
}