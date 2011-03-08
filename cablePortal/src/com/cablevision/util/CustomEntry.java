package com.cablevision.util;

import java.util.Map.Entry;

/**
 * @author Jorge Ruiz Aquino
 * Clase usada para implementar Map.Entry 
 * y poder usar para instanciar pares K,V 
 * y generar los mapas de objetos para Ondemand
 */
public class CustomEntry implements Entry<String, String> {
	private String key;
	private String value;

	public CustomEntry() {
		key = null;
		value = null;
	}
	
	public CustomEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @see java.util.Map.Entry#getKey()
	 */
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	/**
	 * @see java.util.Map.Entry#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public String setValue(String value) {
		// TODO Auto-generated method stub
		return value;
	}

}
