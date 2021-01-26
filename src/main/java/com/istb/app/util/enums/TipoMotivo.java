package com.istb.app.util.enums;

public enum TipoMotivo {
	
	/**
	 * {@enum Enviada}.
	 * 
	 */
	LICENCIA("Licencia"),
	
	/**
	 * {@enum Permiso}.
	 * 
	 */
	PERMISO("Permiso");
	
	private final String value;
	
	private TipoMotivo (String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
