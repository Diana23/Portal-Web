package com.cablevision.vo;

import java.io.Serializable;

public class Archivo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String size;
	private String fecha;
	private String link;
	
	public Archivo(){}
	
	public Archivo(String nombre, String size, String fecha){
		this.nombre = nombre;
		this.size = size;
		this.fecha = fecha;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}	
}
