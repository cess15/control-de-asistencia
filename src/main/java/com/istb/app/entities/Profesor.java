package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profesores")
public class Profesor implements Serializable {

	private static final long serialVersionUID = -3873128797329352879L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String cedula;
	
	@NotEmpty(message = "Los nombres son requeridos.")
	private String nombres;
	
	@NotEmpty(message = "Los apellidos son requeridos.")
	private String apellidos;
	
	@Column(name = "telefono", length = 10)
	private String telefono;
	
	@NotEmpty(message = "El correo es requerido.")
	private String correo;
	
	@Column(name = "fecha_vacacion_inicio")
	private LocalDate fechaVacacionInicio;
	
	@Column(name = "fecha_vacacion_final")
	private LocalDate fechaVacacionFinal;
	
	@OneToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({"profesor"})
	private Usuario usuario;
	
	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"profesor"})
	private Collection<PeriodoProfesor> profesorPeriodos;
}
