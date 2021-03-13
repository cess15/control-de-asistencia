package com.istb.app.services.permiso;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;

import com.istb.app.entities.Permiso;

public interface PermisoService {
	Map<String, String> save(int justificacionId, int motivoId, Permiso permiso);

	void update(Permiso permiso);

	ByteArrayOutputStream generatePDF(int permisoId);

	Permiso findById(int permisoId);

	Permiso findbyInasistenciaIdAndProfesorId(int inasistenciaId);

	String convertDateTime(LocalDateTime date);

}
