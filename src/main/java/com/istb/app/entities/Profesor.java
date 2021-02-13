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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

	@NotEmpty(message = "La cédula es requerida.")
	@Column(name = "cedula", length = 10)
	@Size(max = 10, min = 10, message = "La cédula debe tener 10 dígitos.")
	private String cedula;

	@NotEmpty(message = "Los nombres son requeridos.")
	private String nombres;

	@NotEmpty(message = "Los apellidos son requeridos.")
	private String apellidos;

	@Column(name = "telefono", length = 10)
	private String telefono;

	@NotEmpty(message = "El correo es requerido.")
	@Email(message = "El correo tiene un formato inválido")
	private String correo;

	@Column(name = "fecha_vacacion_inicio")
	private LocalDate fechaVacacionInicio;

	@Column(name = "fecha_vacacion_final")
	private LocalDate fechaVacacionFinal;

	@Valid
	@OneToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnoreProperties({ "profesor" })
	private Usuario usuario;

	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "profesor" })
	private Collection<PeriodoProfesor> profesorPeriodos;

	public Profesor(int id, String cedula, @NotEmpty(message = "Los nombres son requeridos.") String nombres,
			@NotEmpty(message = "Los apellidos son requeridos.") String apellidos, String telefono,
			@NotEmpty(message = "El correo es requerido.") String correo, LocalDate fechaVacacionInicio,
			LocalDate fechaVacacionFinal, Usuario usuario) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.correo = correo;
		this.fechaVacacionInicio = fechaVacacionInicio;
		this.fechaVacacionFinal = fechaVacacionFinal;
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", correo=" + correo + ", fechaVacacionInicio=" + fechaVacacionInicio
				+ ", fechaVacacionFinal=" + fechaVacacionFinal + ", usuario=" + usuario + "]";
	}

}
