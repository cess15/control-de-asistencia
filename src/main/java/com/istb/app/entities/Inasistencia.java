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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
@Table(name = "inasistencias")
public class Inasistencia implements Serializable {

	private static final long serialVersionUID = 1282929574362622885L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDate fecha;
	
	@Column(name = "justificacion_digital")
	private Boolean justificacionDigital;
	
	@Column(name = "justificacion_fisica")
	private Boolean justificacionFisica;
	
	@ManyToOne
	@JsonIgnoreProperties({"inasistencias"})
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Profesor profesor;
	
	@ManyToOne
	@JsonIgnoreProperties({"inasistencias"})
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Periodo periodo;
	
	@OneToMany(mappedBy = "inasistencia", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"inasistencia"})
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OrderBy("id desc")
	private Collection<Permiso> permisos;
	
	@Override
	public String toString() {
		return "Inasistencia [id=" + id + ", fecha=" + fecha + ", justificacionDigital=" + justificacionDigital
				+ ", justificacionFisica=" + justificacionFisica + ", profesor=" + profesor + ", periodo=" + periodo
				+ "]";
	}	
	
}
