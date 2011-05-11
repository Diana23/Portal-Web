package com.cablevision.vo;

import java.io.Serializable;
import java.util.Date;

public class McaffeeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6649142807772292393L;
	private Long noCuenta;
	private String tipoSuspension;
	private String motivo;
	private Date fecha;
	
	public Long getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(Long noCuenta) {
		this.noCuenta = noCuenta;
	}
	public String getTipoSuspension() {
		return tipoSuspension;
	}
	public void setTipoSuspension(String tipoSuspension) {
		this.tipoSuspension = tipoSuspension;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String toString(){
		String desc="";
		desc = "noCuenta::"+noCuenta+"\n"+
				"tipoSuspension::"+tipoSuspension+"\n"+
				"motivo::"+motivo+"\n"+
				"fecha::"+fecha;
				
		return desc;
	}
	
}
