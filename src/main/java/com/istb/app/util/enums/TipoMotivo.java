package com.istb.app.util.enums;

public enum TipoMotivo {
	
	/**
	 * {@enum Licencia}.
	 * 
	 */
	LICENCIA("Licencia"),
	
	/**
	 * {@enum Permiso}.
	 * 
	 */
	PERMISO("Permiso"),
	
	/**
	 * {@enum Sin categoria}.
	 * 
	 */
	OTROS("Otros");
	
	private final String value;
	
	private TipoMotivo (String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
