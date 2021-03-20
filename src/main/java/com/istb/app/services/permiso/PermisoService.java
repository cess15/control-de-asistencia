package com.istb.app.services.permiso;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.istb.app.entities.Permiso;
import com.istb.app.models.DataTableResponse;

public interface PermisoService {
	Map<String, String> save(int justificacionId, int motivoId, Permiso permiso);
	
	DataTableResponse findAllByInasistencia_Profesor_IdAndInasistencia_Periodo_VigenteIsTrue(int profesorId, Integer draw, Integer start, Integer length, Sort.Direction sort, String... properties) throws Exception;

	void update(Permiso permiso);

	ByteArrayOutputStream generatePDF(int permisoId);

	Permiso findById(int permisoId);

	Permiso findbyInasistenciaId(int inasistenciaId);

	String convertDateTime(LocalDateTime date);

}
