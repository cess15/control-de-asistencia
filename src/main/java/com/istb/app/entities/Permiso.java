package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "permisos")
public class Permiso implements Serializable {

	private static final long serialVersionUID = 8400576721684543337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "fecha_inicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio;

	@Column(name = "fecha_final")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFinal;

	@Column(name = "hora_inicio")
	private LocalTime horaInicio;

	@Column(name = "hora_final")
	private LocalTime horaFinal;

	private String observacion;

	@Column(name = "valor_descontar")
	private Double valorDescontar;

	@Column(name = "fecha_generacion")
	private LocalDate fechaGeneracion;

	@Column(name = "fecha_recepcion")
	private LocalDate fechaRecepcion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "permisos" })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Inasistencia inasistencia;

	@Column(name = "fecha_creacion", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_actualizacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaActualizacion;

	@Column(name = "dias_faltantes")
	private int dayDiference;

	@ManyToMany
	@JoinTable(name = "motivo_permiso", joinColumns = @JoinColumn(name = "permisos_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "motivo_id", referencedColumnName = "id"))
	@JsonIgnoreProperties({ "permisos" })
	private Collection<Motivo> motivos;

	@OneToMany(mappedBy = "permiso", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "permiso" })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Adjunto> adjuntos;

	public double addValorDescontar(int dias) {
		return Math.round(dias * 1.36363636363636 * 100.0) / 100.0;
	}

	public void addMotivo(Motivo motivo) {
		if (this.motivos == null) {
			this.motivos = new ArrayList<>();
		}
		this.motivos.add(motivo);
	}

	@Override
	public String toString() {
		return "Permiso [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", horaInicio="
				+ horaInicio + ", horaFinal=" + horaFinal + ", observacion=" + observacion + ", valorDescontar="
				+ valorDescontar + ", fechaGeneracion=" + fechaGeneracion + ", fechaRecepcion=" + fechaRecepcion
				+ ", inasistencia=" + inasistencia + ", fechaCreacion=" + fechaCreacion + ", fechaActualizacion="
				+ fechaActualizacion + ", dayDiference=" + dayDiference + "]";
	}

}
