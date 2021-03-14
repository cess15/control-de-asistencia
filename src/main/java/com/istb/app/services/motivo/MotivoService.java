package com.istb.app.services.motivo;

import com.istb.app.entities.Motivo;

public interface MotivoService {
	void save(Motivo motivo);

	Motivo findById(int id);

}
