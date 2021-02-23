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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

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
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	@Column(name = "fecha_inicio")
	@NotNull(message = "La fecha de inicio es requerida.")
	private LocalDate fechaInicio;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	@Column(name = "fecha_final")
	@NotNull(message = "La fecha de fin de periodo es requerida.")
	private LocalDate fechaFinal;
	
	private Boolean vigente;
	
	@OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"periodo"})
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Inasistencia> inasistencias;

	@Override
	public String toString() {
		return "Periodo [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", vigente="
				+ vigente + "]";
	}
	
}
