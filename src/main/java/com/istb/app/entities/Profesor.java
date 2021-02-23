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
@Table(name = "profesores")
public class Profesor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "cedula", length = 10)
	@NotEmpty(message = "La cédula es requerida.")
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Valid
	@JsonIgnoreProperties({ "profesor" })
	private Usuario usuario;

	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "profesor" })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Inasistencia> inasistencias;

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", correo=" + correo + ", fechaVacacionInicio=" + fechaVacacionInicio
				+ ", fechaVacacionFinal=" + fechaVacacionFinal + ", usuario=" + usuario + "]";
	}

}
