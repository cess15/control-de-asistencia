package com.istb.app.services.motivo;

import org.springframework.data.jpa.repository.Query;

import com.istb.app.entities.Motivo;
import com.istb.app.util.enums.TipoMotivo;

public interface MotivoService {
	void save(Motivo motivo);

	@Query("select m from Motivo m where m.tipo = ?1")
	Motivo findByTipo(TipoMotivo tipo);
}
