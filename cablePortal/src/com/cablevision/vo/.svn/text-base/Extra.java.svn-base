package com.cablevision.vo;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the EXTRA database table.
 * 
 * @author BEA Workshop
 */
public class Extra  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idExtra;
	private String description;
	private boolean hidden;
	private String name;
	private double normalPrice;
	private double normalPriceDv;
	private int number;
	private List<Integer> numbers;
	private double tcPrice;
	private double tcPriceDv;
	private Service service;
	private Integer selectedNumber;
	private String extGrupo;
	private String depende;

    public Extra() {
    }

    public Extra(Long idExtra) {
    	this.idExtra = idExtra;
    }
    
	public Long getIdExtra() {
		return this.idExtra;
	}
	public void setIdExtra(Long idextra) {
		this.idExtra = idextra;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getHidden() {
		return this.hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getNormalPrice() {
		return this.normalPrice;
	}
	public void setNormalPrice(double normalPrice) {
		this.normalPrice = normalPrice;
	}

	public double getNormalPriceDv() {
		return this.normalPriceDv;
	}
	public void setNormalPriceDv(double normalPriceDv) {
		this.normalPriceDv = normalPriceDv;
	}

	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public double getTcPrice() {
		return this.tcPrice;
	}
	public void setTcPrice(double tcPrice) {
		this.tcPrice = tcPrice;
	}

	public double getTcPriceDv() {
		return this.tcPriceDv;
	}
	public void setTcPriceDv(double tcPriceDv) {
		this.tcPriceDv = tcPriceDv;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Extra)) {
			return false;
		}
		Extra castOther = (Extra)other;
		return new EqualsBuilder()
			.append(this.getIdExtra(), castOther.getIdExtra())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdExtra())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idExtra", getIdExtra())
			.toString();
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public Integer getSelectedNumber() {
		return selectedNumber;
	}

	public void setSelectedNumber(Integer selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	public String getExtGrupo() {
		return extGrupo;
	}

	public void setExtGrupo(String extGrupo) {
		this.extGrupo = extGrupo;
	}

	public String getDepende() {
		return depende;
	}

	public void setDepende(String depende) {
		this.depende = depende;
	}

}