package com.istb.app.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.istb.app.util.enums.TipoMotivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "motivos")
public class Motivo implements Serializable {

	private static final long serialVersionUID = -6386604383959539060L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	@Column(name = "tiempo_recuperar")
	private String tiempoRecuperar;

	@Column(name = "tipo")
	@Enumerated(value = EnumType.STRING)
	private TipoMotivo tipo;

	private String descripcion;

	@ManyToMany(mappedBy = "motivos")
	private Collection<Permiso> permisos;

	@Override
	public String toString() {
		return "Motivo [id=" + id + ", nombre=" + nombre + ", tiempoRecuperar=" + tiempoRecuperar + ", descripcion="
				+ descripcion + "]";
	}
}
