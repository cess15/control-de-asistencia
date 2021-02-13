package com.istb.app.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	
	@Lob
	@Column(name = "tiempo_recuperar")
	private String tiempoRecuperar;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoMotivo tipo;
	
	@Lob
	private String descripcion;
	
	@ManyToMany(mappedBy = "motivos", fetch = FetchType.LAZY)
	private Collection<Permiso> permisos;

}
