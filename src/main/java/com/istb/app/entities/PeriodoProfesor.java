package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "periodo_profesor")
public class PeriodoProfesor implements Serializable {

	private static final long serialVersionUID = 4156152968260269714L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@Valid
	@JsonIgnoreProperties({ "profesorPeriodos" })
	private Profesor profesor;

	@ManyToOne
	@Valid
	@JsonIgnoreProperties({ "periodoProfesores" })
	private Periodo periodo;

	@Column(name = "dias_faltados")
	private int diasFaltados;

	@Column(name = "fecha_creacion", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_actualizacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaActualizacion;

	@OneToMany(mappedBy = "periodo_profesor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "periodo_profesor" })
	private Collection<Inasistencia> inasistencias;

	@PrePersist
	public void preCreated() {
		this.fechaCreacion = LocalDateTime.now();
		this.fechaActualizacion = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdated() {
		this.fechaActualizacion = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "PeriodoProfesor [id=" + id + ", profesor=" + profesor + ", periodo=" + periodo + ", diasFaltados="
				+ diasFaltados + ", fechaCreacion=" + fechaCreacion + ", fechaActualizacion=" + fechaActualizacion
				+ "]";
	}

	
	

}
