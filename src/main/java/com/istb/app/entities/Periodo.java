package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "periodos")
public class Periodo implements Serializable {

	private static final long serialVersionUID = 1118264851184843828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_final")
	private LocalDate fechaFinal;
	
	private Boolean vigente;
	
	@OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({"periodo"})
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Inasistencia> inasistencias;

	@Override
	public String toString() {
		return "Periodo [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", vigente="
				+ vigente + "]";
	}
	
	

}
