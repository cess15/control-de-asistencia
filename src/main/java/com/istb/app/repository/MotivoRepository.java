package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Motivo;
import com.istb.app.util.enums.TipoMotivo;

@Repository
public interface MotivoRepository extends JpaRepository<Motivo, Integer>{
	Motivo findByTipo(TipoMotivo tipo);
}
