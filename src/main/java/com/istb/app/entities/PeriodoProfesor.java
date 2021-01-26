package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"profesorPeriodos"})
	private Profesor profesor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"periodoProfesores"})
	private Periodo periodo;
	
	@Column(name = "dias_faltados")
	private int diasFaltados;
	
	@Column(name = "fecha_creacion", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_actualizacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaActualizacion;
	
	@OneToMany(mappedBy = "periodoProfesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"periodoProfesor"})
	private Collection<Inasistencia> inasistencias;
	
	@PrePersist
	public void preCreated () {
		this.fechaCreacion = LocalDateTime.now();
		this.fechaActualizacion = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdated () {
		this.fechaActualizacion = LocalDateTime.now();
	}
}
