package com.istb.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
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
@Transactional
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 3, max = 100, message = "El rol debe ser mayor a 3 y menor a 100 caracteres.")
	@NotEmpty(message = "El nombre del rol es requerido.")
	private String nombre;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "roles" })
	private Collection<Usuario> usuarios;
		
	public void addUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			this.usuarios = new ArrayList<>();
		}
		this.usuarios.add(usuario);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
