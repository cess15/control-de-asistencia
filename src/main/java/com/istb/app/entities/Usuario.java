package com.istb.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nombre_usuario")
	@NotEmpty(message = "El nombre de usuario es requerido")
	private String nombreUsuario;

	@NotEmpty(message = "La contraseña es requerida")
	private String contrasena;

	@Column(name = "imagen_perfil")
	private String imagenPerfil;

	@Column(name = "url_imagen_perfil", length = 400)
	private String url_imagen_perfil;

	private Boolean estado;

	@Column(name = "fecha_creacion", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_actualizacion")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaActualizacion;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable( name = "role_usuario", 
		joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	@JsonIgnoreProperties({ "usuarios" })
	private Collection<Role> roles;

	@OneToOne(mappedBy = "usuario")
	@Valid
	@JsonIgnoreProperties({ "usuario" })
	private Profesor profesor;

	@PrePersist
	public void preCreated() {
		this.fechaCreacion = LocalDateTime.now();
		this.fechaActualizacion = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdated() {
		this.fechaActualizacion = LocalDateTime.now();
	}

	public void addRol(Role rol) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(rol);
	}

	public void removeRol(Role rol) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.remove(rol);
	}

	public Usuario(int id, String nombreUsuario, String contrasena, String imagenPerfil, String url_imagen_perfil,
			Boolean estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, Profesor profesor) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.imagenPerfil = imagenPerfil;
		this.url_imagen_perfil = url_imagen_perfil;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.profesor = profesor;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena
				+ ", imagenPerfil=" + imagenPerfil + ", url_imagen_perfil=" + url_imagen_perfil + ", estado=" + estado
				+ ", fechaCreacion=" + fechaCreacion + ", fechaActualizacion=" + fechaActualizacion + ", profesor="
				+ profesor + "]";
	}

}
