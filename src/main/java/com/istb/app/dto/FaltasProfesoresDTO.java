package com.istb.app.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaltasProfesoresDTO implements Serializable{

	private static final long serialVersionUID = -6432886508648110995L;

	private String apellidos;
	
	private String nombres;
	
	private int faltas;
	
	
	
}
